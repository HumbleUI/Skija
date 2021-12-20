#! /usr/bin/env python3
import build_utils, common, os, sys

def package():
  os.chdir(common.basedir)
  artifact = "skija-" + common.classifier
  version = common.version()

  build_utils.copy_replace(
    f'platform/deploy/META-INF/maven/io.github.humbleui/{artifact}/pom.xml',
    f'platform/target/maven/META-INF/maven/io.github.humbleui/{artifact}/pom.xml',
    {'${version}': version}
  )

  build_utils.copy_replace(
    f'platform/deploy/META-INF/maven/io.github.humbleui/{artifact}/pom.properties',
    f'platform/target/maven/META-INF/maven/io.github.humbleui/{artifact}/pom.properties',
    {'${version}': version}
  )

  with open('platform/target/classes/io/github/humbleui/skija/' + common.classifier.replace('-', '/') + '/skija.version', 'w') as f:
    f.write(version)

  build_utils.jar(f"target/{artifact}-{version}.jar",
                  ("platform/target/classes", "."),
                  ("platform/target/maven", "META-INF"))

  build_utils.jar(f"target/{artifact}-{version}-sources.jar",
                  ("platform/target/maven", "META-INF"))

  build_utils.jar(f"target/{artifact}-{version}-javadoc.jar",
                  ("platform/target/maven", "META-INF"))

  return 0

if __name__ == '__main__':
  sys.exit(package())
