#! /usr/bin/env python3
import argparse, clean, common, glob, os, platform, re, subprocess, sys

def package():
  os.chdir(common.root)
  os.makedirs("target", exist_ok = True)
  artifact = "skija-" + common.classifier
  version = common.version()

  common.copy_replace(
    f'platform/deploy/META-INF/maven/io.github.humbleui.skija/{artifact}/pom.xml',
    f'platform/target/maven/META-INF/maven/io.github.humbleui.skija/{artifact}/pom.xml',
    {'${version}': version}
  )

  common.copy_replace(
    f'platform/deploy/META-INF/maven/io.github.humbleui.skija/{artifact}/pom.properties',
    f'platform/target/maven/META-INF/maven/io.github.humbleui.skija/{artifact}/pom.properties',
    {'${version}': version}
  )

  with open('platform/target/classes/io/github/humbleui/skija/' + common.classifier.replace('-', '/') + '/skija.version', 'w') as f:
    f.write(version)

  print(f"Packaging {artifact}-{version}.jar")
  subprocess.check_call(["jar",
    "--create",
    "--file", f"target/{artifact}-{version}.jar",
    "-C", "platform/target/classes", ".",
    "-C", "platform/target/maven", "META-INF"
  ])

  return 0

if __name__ == '__main__':
  sys.exit(package())
