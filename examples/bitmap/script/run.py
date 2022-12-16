#! /usr/bin/env python3
import argparse, glob, os, subprocess, sys
sys.path.append(os.path.normpath(os.path.dirname(__file__) + '/../../../script'))
import common, build, build_utils

def main():
  parser = argparse.ArgumentParser()
  parser.add_argument('--skija-version')
  (args, _) = parser.parse_known_args()

  # Javac
  classpath = common.deps_compile()

  if args.skija_version:
    classpath += [
      build_utils.fetch_maven('io.github.humbleui', 'skija-shared'),
      build_utils.fetch_maven('io.github.humbleui', 'skija-' + common.classifier, args.skija_version),
    ]
  else:
    build.main()
    classpath += [
      os.path.join('..', '..', 'platform', 'target', common.classifier, 'classes'),
      os.path.join('..', '..', 'shared', 'target', 'classes-java9'),
      os.path.join('..', '..', 'shared', 'target', 'classes')
    ]

  # Java
  os.chdir(common.basedir + '/examples/bitmap')
  subprocess.check_call(['java',
    '--class-path', build_utils.classpath_join(classpath),
    'src/RenderToBitmap.java'])

  return 0

if __name__ == '__main__':
  sys.exit(main())