#! /usr/bin/env python3
import argparse, build_shared, clean, common, glob, os, platform, re, subprocess, sys

def package():
  build_shared.main()

  version = common.version()
  os.chdir(f'{common.root}/shared')

  common.copy_replace(
    'deploy/META-INF/maven/io.github.humbleui.skija/skija-shared/pom.xml',
    'target/maven/META-INF/maven/io.github.humbleui.skija/skija-shared/pom.xml',
    {'${version}': version}
  )

  common.copy_replace(
    'deploy/META-INF/maven/io.github.humbleui.skija/skija-shared/pom.properties',
    'target/maven/META-INF/maven/io.github.humbleui.skija/skija-shared/pom.properties',
    {'${version}': version}
  )

  # skija-shared-{version}.jar
  print('Packaging skija-shared-' + version + ".jar")
  subprocess.check_call(["jar",
    "--create",
    "--file", "target/skija-shared-" + version + ".jar",
    "-C", "target/classes", ".",
    "-C", "target/maven", "META-INF"
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
    "java",
    "--module-path",
    common.classpath_separator.join(common.deps()),
    "-d", "target/generated-sources/delombok/io/github/humbleui/skija"
  ])

  print('Packaging skija-shared-' + version + "-sources.jar")
  subprocess.check_call(["jar",
    "--create",
    "--file", "target/skija-shared-" + version + "-sources.jar",
    "-C", "target/generated-sources/delombok", ".",
    "-C", "deploy", "META-INF"
  ])

  # skija-shared-{version}-javadoc.jar
  print(f'Packaging skija-shared-{version}-javadoc.jar')
  sources = glob.glob("target/generated-sources/delombok/**/*.java", recursive = True)
  javadoc = f"{common.root}/docs/apidocs"
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
