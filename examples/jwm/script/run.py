#! /usr/bin/env python3
import argparse, glob, os, subprocess, sys
sys.path.append(os.path.normpath(os.path.dirname(__file__) + '/../../../script'))
import common, build, build_utils

def main():
  parser = argparse.ArgumentParser()
  parser.add_argument('--skija-version')
  parser.add_argument('--jwm-version', default='0.4.15')
  parser.add_argument('--jwm-dir', default=None)
  (args, _) = parser.parse_known_args()

  # Javac
  classpath = common.deps_compile()

  if args.jwm_dir:
    jwm_dir = os.path.abspath(args.jwm_dir)
    classpath += [
      os.path.join(jwm_dir, 'target', 'classes'),
    ]
  else:
    classpath += [
      build_utils.fetch_maven('io.github.humbleui', 'jwm', args.jwm_version),
    ]

  if args.skija_version:
    classpath += [
      build_utils.fetch_maven('io.github.humbleui', 'skija-shared', args.skija_version),
      build_utils.fetch_maven('io.github.humbleui', 'skija-' + common.classifier, args.skija_version),
    ]
  else:
    build.main()
    classpath += [
      os.path.join('..', '..', 'platform', 'target', common.classifier, 'classes'),
      os.path.join('..', '..', 'shared', 'target', 'classes-java9'),
      os.path.join('..', '..', 'shared', 'target', 'classes')
    ]

  os.chdir(common.basedir + '/examples/jwm')

  sources = build_utils.files('src/**/*.java', '../scenes/src/**/*.java')
  build_utils.javac(sources, 'target/classes', classpath = classpath, release = '16', opts = ["-Xlint:deprecation"])

  # Java
  subprocess.check_call([
    'java',
    '--class-path', build_utils.classpath_join(['target/classes'] + classpath)]
    + (['-XstartOnFirstThread'] if 'macos' == build_utils.system else [])
    + ['-Djava.awt.headless=true',
    '-enableassertions',
    '-enablesystemassertions',
    '-Xcheck:jni',
    '-Dskija.logLevel=DEBUG',
    'io.github.humbleui.skija.examples.jwm.Main'])

  return 0

if __name__ == '__main__':
  sys.exit(main())