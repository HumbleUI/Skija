#! /usr/bin/env python3
import os, subprocess, sys
sys.path.append(os.path.normpath(os.path.dirname(__file__) + '/../../../script'))
import build_utils, common

def main():
  os.chdir(os.path.join(os.path.dirname(__file__), os.pardir))
  subprocess.check_call([
    "clj",
    "-J--module-path=" + os.path.join('..', '..', 'platform', 'target', common.classifier, 'classes'),
    "-M:" + common.classifier,
    "-m", "snake.main"])
  return 0

if __name__ == '__main__':
  sys.exit(main())