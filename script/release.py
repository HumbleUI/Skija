#! /usr/bin/env python3
import build_utils, common, os, sys

def main():
  os.chdir(common.basedir)

  for classifier in ["", "-sources", "-javadoc"]:
      build_utils.deploy(f"target/skija-shared-{common.version}{classifier}.jar", tempdir = "shared/target/deploy")

  for system in ['windows-x64', 'linux-x64', 'macos-x64', 'macos-arm64']: # 'linux-arm64'
    for classifier in ["", "-sources", "-javadoc"]:
      build_utils.deploy(f"target/skija-{system}-{common.version}{classifier}.jar", tempdir = "platform/target/deploy")

  build_utils.release()

  return 0

if __name__ == "__main__":
  sys.exit(main())
