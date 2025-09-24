#! /usr/bin/env python3
import build_utils, common, os, sys

def main():
  os.chdir(common.basedir)

  for name in ['skija-shared', 'skija-windows-x64', 'skija-linux-x64', 'skija-macos-x64', 'skija-macos-arm64']: # 'linux-arm64'
    jars = [f"target/{name}-{common.version}{classifier}.jar" for classifier in ["", "-sources", "-javadoc"]]
    build_utils.collect_jars('io.github.humbleui', name, common.version, jars, 'target/release')

  return build_utils.release2(f"skija-{common.version}.zip", 'target/release')

if __name__ == "__main__":
  sys.exit(main())
