#! /usr/bin/env python3
import build_utils, common, os, shutil, sys

def main():
  os.chdir(common.basedir)
  build_utils.rmdir("target")
  build_utils.rmdir("shared/target")
  build_utils.rmdir("platform/target")
  build_utils.rmdir("tests/target")
  build_utils.rmdir("examples/lwjgl/target")
  build_utils.rmdir("examples/kwinit/target")
  build_utils.rmdir("examples/jwm/target")
  build_utils.rmdir("examples/swt/target")
  return 0

if __name__ == '__main__':
  sys.exit(main())