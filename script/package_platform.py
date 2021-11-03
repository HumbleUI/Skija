#! /usr/bin/env python3
import argparse, clean, common, glob, os, platform, re, subprocess, sys

def package():
  os.chdir(common.root + '/platform')
  artifact = "skija-" + common.classifier
  version = common.version()

  common.copy_replace(
    f'deploy/META-INF/maven/io.github.humbleui.skija/{artifact}/pom.xml',
    f'target/maven/META-INF/maven/io.github.humbleui.skija/{artifact}/pom.xml',
    {'${version}': version}
  )

  common.copy_replace(
    f'deploy/META-INF/maven/io.github.humbleui.skija/{artifact}/pom.properties',
    f'target/maven/META-INF/maven/io.github.humbleui.skija/{artifact}/pom.properties',
    {'${version}': version}
  )

  with open('target/classes/io/github/humbleui/skija/' + common.classifier.replace('-', '/') + '/skija.version', 'w') as f:
    f.write(version)


  print(f"Packaging {artifact}-{version}.jar")
  subprocess.check_call(["jar",
    "--create",
    "--file", f"target/{artifact}-{version}.jar",
    "-C", "target/classes", ".",
    "-C", "target/maven", "META-INF"
  ])

  return 0

if __name__ == '__main__':
  sys.exit(package())
