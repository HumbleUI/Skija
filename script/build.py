#! /usr/bin/env python3
import argparse, build_utils, common, glob, os, subprocess, sys, zipfile

def main():
  parser = argparse.ArgumentParser()
  parser.add_argument('--debug', action='store_true')
  parser.add_argument('--arch', default=build_utils.arch)
  parser.add_argument('--skia-dir')
  parser.add_argument('--skia-release', default='m109-664500fa93')
  parser.add_argument('--cmake-toolchain-file')
  (args, _) = parser.parse_known_args()

  # Fetch Skia
  build_type = 'Debug' if args.debug else 'Release'
  if args.skia_dir:
    skia_dir = os.path.abspath(args.skia_dir)
    os.chdir(common.basedir + '/platform')
  else:
    os.chdir(common.basedir + '/platform')
    skia_dir = "Skia-" + args.skia_release + "-" + build_utils.system + "-" + build_type + '-' + build_utils.arch
    if not os.path.exists(skia_dir):
      zip = skia_dir + '.zip'
      build_utils.fetch('https://github.com/HumbleUI/SkiaBuild/releases/download/' + args.skia_release + '/' + zip, zip)
      with zipfile.ZipFile(zip, 'r') as f:
        print("Extracting", zip)
        f.extractall(skia_dir)
      os.remove(zip)
    skia_dir = os.path.abspath(skia_dir)
  print("Using Skia from", skia_dir)

  # CMake
  native_build_dir = f'target/{common.classifier}/native'
  build_utils.makedirs(native_build_dir)
  subprocess.check_call([
    'cmake',
    '-G', 'Ninja',
    '-DCMAKE_BUILD_TYPE=' + build_type,
    '-DSKIA_DIR=' + skia_dir,
    '-DSKIA_ARCH=' + build_utils.arch]
    + (['-DCMAKE_OSX_ARCHITECTURES=' + {'x64': 'x86_64', 'arm64': 'arm64'}[build_utils.arch]] if build_utils.system == 'macos' else [])
    + [os.path.abspath('.')]
    + (['-DCMAKE_TOOLCHAIN_FILE=' + args.cmake_toolchain_file] if args.cmake_toolchain_file else []),
    cwd=os.path.abspath(native_build_dir))

  # Ninja
  subprocess.check_call(['ninja'], cwd=os.path.abspath(native_build_dir))

  # Codesign
  if build_utils.system == 'macos' and os.getenv('APPLE_CODESIGN_IDENTITY'):
    subprocess.call(['codesign',
                     # '--force',
                     # '-vvvvvv',
                     '--deep',
                     '--sign',
                     os.getenv('APPLE_CODESIGN_IDENTITY'),
                     f'{native_build_dir}/libskija.dylib'])
  # javac
  build_utils.javac(build_utils.files('../shared/java/**/*.java'),
                    '../shared/target/classes',
                    classpath = common.deps_compile(),
                    release = '8')
  build_utils.javac(build_utils.files('../shared/java9/**/*.java'),
                    '../shared/target/classes-java9',
                    classpath = common.deps_compile(),
                    modulepath = common.deps_compile(),
                    opts = ['--patch-module', 'io.github.humbleui.skija.shared=../shared/target/classes'],
                    release = '9')

  build_utils.copy_replace(
      'java/module-info.java',
      f'target/{common.classifier}/java/module-info.java',
      {'${system}': build_utils.system, '${arch}': build_utils.arch}
  )
  build_utils.javac(
      [f'target/{common.classifier}/java/module-info.java'],
      f'target/{common.classifier}/classes',
      release = '9',
      opts = ['-nowarn']
  )

  # Copy files
  target = f'target/{common.classifier}/classes/io/github/humbleui/skija/{build_utils.system}/{build_utils.arch}'

  if build_utils.system == 'macos':
    build_utils.copy_newer(f'{native_build_dir}/libskija.dylib', f'{target}/libskija.dylib')
  elif build_utils.system == 'linux':
    build_utils.copy_newer(f'{native_build_dir}/libskija.so', f'{target}/libskija.so')
  elif build_utils.system == 'windows':
    build_utils.copy_newer(f'{native_build_dir}/skija.dll', f'{target}/skija.dll')
    build_utils.copy_newer(f'{skia_dir}/out/{build_type}-{build_utils.arch}/icudtl.dat', f'{target}/icudtl.dat')

  return 0

if __name__ == '__main__':
  sys.exit(main())
