#! /usr/bin/env python3
import build_utils, common, os, sys, subprocess, zipfile

def package():
  os.chdir(common.basedir)
  artifact = f'skija-{common.classifier}'

  platform_target = f'platform/target/{common.classifier}'

  build_utils.copy_replace(
    'platform/deploy/META-INF/maven/io.github.humbleui/pom.xml',
    f'{platform_target}/maven/META-INF/maven/io.github.humbleui/{artifact}/pom.xml',
    {'${version}': common.version, '${artifact}': artifact}
  )

  build_utils.copy_replace(
    'platform/deploy/META-INF/maven/io.github.humbleui/pom.properties',
    f'platform/target/{common.classifier}/maven/META-INF/maven/io.github.humbleui/{artifact}/pom.properties',
    {'${version}': common.version, '${artifact}': artifact}
  )

  with open(f'{platform_target}/classes/io/github/humbleui/skija/{build_utils.system}/{build_utils.arch}/skija.version', 'w') as f:
    f.write(common.version)

  build_utils.jar(f'target/{artifact}-{common.version}.jar',
                  (f'{platform_target}/classes', '.'),
                  (f'{platform_target}/maven', 'META-INF'))

  build_utils.jar(f'target/{artifact}-{common.version}-sources.jar',
                  (f'{platform_target}/maven', 'META-INF'))

  build_utils.jar(f'target/{artifact}-{common.version}-javadoc.jar',
                  (f'{platform_target}/maven', 'META-INF'))

  jmod_file = f'target/{artifact}-{common.version}.jmod'

  if os.path.exists(jmod_file):
    os.remove(jmod_file)

  subprocess.check_call([
    'jmod', 'create',
    '--exclude', 'io/github/humbleui/skija/**/*.{dll,so,dylib,dat}',
    '--class-path', f'{platform_target}/classes',
    jmod_file
  ])

  native_libs_dir = f'{platform_target}/classes/io/github/humbleui/skija/{build_utils.system}/{build_utils.arch}'
  if build_utils.system == 'macos':
    lib_file = 'libskija.dylib'
  elif build_utils.system == 'linux':
    lib_file = 'libskija.so'
  elif build_utils.system == 'windows':
    lib_file = 'skija.dll'

  with zipfile.ZipFile(jmod_file, 'a') as zf:
    zf.write(f'{native_libs_dir}/{lib_file}', f'lib/{lib_file}')
    if build_utils.system == 'windows':
      zf.write(f'{native_libs_dir}/icudtl.dat', 'bin/icudtl.dat')

  return 0

if __name__ == '__main__':
  sys.exit(package())
