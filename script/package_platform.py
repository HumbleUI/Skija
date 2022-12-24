#! /usr/bin/env python3
import build_utils, common, os, sys, zipfile

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
  print(f"Packaging {os.path.basename(jmod_file)}")

  platform_package_dir = f'io/github/humbleui/skija/{build_utils.system}/{build_utils.arch}'
  if build_utils.system == 'macos':
    lib_file = 'libskija.dylib'
  elif build_utils.system == 'linux':
    lib_file = 'libskija.so'
  elif build_utils.system == 'windows':
    lib_file = 'skija.dll'

  with open(jmod_file, mode='wb') as f:
    # Create empty jmod file with magic number
    f.write(b'JM\x01\x00PK\x05\x06\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00')

  with zipfile.ZipFile(jmod_file, 'a', compression=zipfile.ZIP_DEFLATED) as zf:
    zf.write(f'{platform_target}/classes/module-info.class', 'classes/module-info.class')
    zf.write(f'{platform_target}/classes/{platform_package_dir}/skija.version', f'classes/{platform_package_dir}/skija.version')
    zf.write(f'{platform_target}/classes/{platform_package_dir}/{lib_file}', f'lib/{lib_file}')
    if build_utils.system == 'windows':
      zf.write(f'{platform_target}/classes/{platform_package_dir}/icudtl.dat', 'bin/icudtl.dat')

  return 0

if __name__ == '__main__':
  sys.exit(package())
