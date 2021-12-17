#! /usr/bin/env python3
import build_utils, common, os, sys

def package():
  os.chdir(common.basedir)
  version = common.version()

  build_utils.copy_replace(
    'shared/deploy/META-INF/maven/io.github.humbleui.skija/skija-shared/pom.xml',
    'shared/target/maven/META-INF/maven/io.github.humbleui.skija/skija-shared/pom.xml',
    {'${version}': version}
  )

  build_utils.copy_replace(
    'shared/deploy/META-INF/maven/io.github.humbleui.skija/skija-shared/pom.properties',
    'shared/target/maven/META-INF/maven/io.github.humbleui.skija/skija-shared/pom.properties',
    {'${version}': version}
  )

  build_utils.jar(f"target/skija-shared-{version}.jar",
                  ("shared/target/classes", "."),
                  ("shared/target/maven", "META-INF"))

  build_utils.delombok(["shared/java"], "shared/target/delomboked/io/github/humbleui/skija", modulepath=common.deps_compile())
  build_utils.jar(f"target/skija-shared-{version}-sources.jar",
                  ("shared/target/delomboked", "."),
                  ("shared/target/maven", "META-INF"))

  build_utils.javadoc(["shared/target/delomboked"], "shared/target/apidocs", modulepath=common.deps_compile())
  build_utils.jar(f"target/skija-shared-{version}-javadoc.jar",
                  ("shared/target/apidocs", "."),
                  ("shared/target/maven", "META-INF"))

  return 0

if __name__ == '__main__':
  sys.exit(package())
