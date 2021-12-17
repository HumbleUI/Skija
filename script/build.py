#! /usr/bin/env python3
import argparse, build_utils, common, glob, os, subprocess, sys, zipfile

def main():
  parser = argparse.ArgumentParser()
  parser.add_argument('--debug', action='store_true')
  parser.add_argument('--arch', default=build_utils.arch)
  parser.add_argument('--skia-dir')
  parser.add_argument('--skia-release', default='m96-2f1f21b8a9')
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
  build_utils.makedirs("build")
  subprocess.check_call([
    "cmake",
    "-G", "Ninja",
    "-DCMAKE_BUILD_TYPE=" + build_type,
    "-DSKIA_DIR=" + skia_dir,
    "-DSKIA_ARCH=" + build_utils.arch]
    + (["-DCMAKE_OSX_ARCHITECTURES=" + {"x64": "x86_64", "arm64": "arm64"}[build_utils.arch]] if build_utils.system == "macos" else [])
    + [".."],
    cwd=os.path.abspath('build'))

  # Ninja
  subprocess.check_call(["ninja"], cwd=os.path.abspath('build'))

  # Codesign
  if build_utils.system == "macos" and os.getenv("APPLE_CODESIGN_IDENTITY"):
    subprocess.call(["codesign",
                     # "--force",
                     # "-vvvvvv",
                     "--deep",
                     "--sign",
                     os.getenv("APPLE_CODESIGN_IDENTITY"),
                     "build/libskija_" + build_utils.arch + ".dylib"])

  # javac
  modulepath = []
  sources = build_utils.files('../shared/java/**/*.java')
  build_utils.javac(sources,
                    '../shared/target/classes',
                    classpath = common.deps_compile(),
                    modulepath = common.deps_compile(),
                    release = '9')
  modulepath += ['../shared/target/classes']

  sources = build_utils.files(f'java-{common.classifier}/**/*.java')
  build_utils.javac(sources, 'target/classes', modulepath = modulepath, release = '9')

  # Copy files
  target = 'target/classes/io/github/humbleui/skija'
  if common.classifier == 'macos-x64':
    build_utils.copy_newer('build/libskija_x64.dylib', target + '/macos/x64/libskija_x64.dylib')
  elif common.classifier == 'macos-arm64':
    build_utils.copy_newer('build/libskija_arm64.dylib', target + '/macos/arm64/libskija_arm64.dylib')
  elif common.classifier == 'linux':
    build_utils.copy_newer('build/libskija.so', target + '/linux/libskija.so')
  elif common.classifier == 'windows':
    build_utils.copy_newer('build/skija.dll', target + '/windows/skija.dll')
    build_utils.copy_newer(skia_dir + '/out/' + build_type + '-' + build_utils.arch + '/icudtl.dat',
                           target + '/windows/icudtl.dat')

  return 0

if __name__ == '__main__':
  sys.exit(main())
