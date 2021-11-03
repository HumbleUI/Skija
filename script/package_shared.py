#! /usr/bin/env python3
import argparse, clean, common, glob, os, platform, re, subprocess, sys

def package():
  version = common.version()
  os.chdir(common.root)
  os.makedirs("target", exist_ok = True)

  common.copy_replace(
    'shared/deploy/META-INF/maven/io.github.humbleui.skija/skija-shared/pom.xml',
    'shared/target/maven/META-INF/maven/io.github.humbleui.skija/skija-shared/pom.xml',
    {'${version}': version}
  )

  common.copy_replace(
    'shared/deploy/META-INF/maven/io.github.humbleui.skija/skija-shared/pom.properties',
    'shared/target/maven/META-INF/maven/io.github.humbleui.skija/skija-shared/pom.properties',
    {'${version}': version}
  )

  # skija-shared-{version}.jar
  print('Packaging skija-shared-' + version + ".jar")
  subprocess.check_call(["jar",
    "--create",
    "--file", "target/skija-shared-" + version + ".jar",
    "-C", "shared/target/classes", ".",
    "-C", "shared/target/maven", "META-INF"
  ])

  # skija-shared-{version}-sources.jar
  lombok = common.deps()[0]
  print('Delomboking sources')
  subprocess.check_call([
    "java",
    "-Dfile.encoding=UTF8",
    "-jar",
    lombok,
    "delombok",
    "shared/java",
    "--module-path",
    common.classpath_separator.join(common.deps()),
    "-d", "shared/target/generated-sources/delombok/io/github/humbleui/skija"
  ])

  print('Packaging skija-shared-' + version + "-sources.jar")
  subprocess.check_call(["jar",
    "--create",
    "--file", "target/skija-shared-" + version + "-sources.jar",
    "-C", "shared/target/generated-sources/delombok", ".",
    "-C", "shared/deploy", "META-INF"
  ])

  # skija-shared-{version}-javadoc.jar
  print(f'Packaging skija-shared-{version}-javadoc.jar')
  sources = glob.glob("shared/target/generated-sources/delombok/**/*.java", recursive = True)
  javadoc = "docs/apidocs"
  os.makedirs(javadoc, exist_ok = True)
  subprocess.check_call(["javadoc",
    "--module-path", common.classpath_separator.join(common.deps()),
    "-d", javadoc,
    "-quiet",
    "-Xdoclint:all,-missing"]
    + sources)

  subprocess.check_call(["jar",
    "--create",
    "--file", f"target/skija-shared-{version}-javadoc.jar",
    "-C", javadoc, ".",
  ])

  return 0

if __name__ == '__main__':
  sys.exit(package())
