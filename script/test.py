#! /usr/bin/env python3
import argparse, build, build_utils, common, glob, os, subprocess, sys

def main():
  parser = argparse.ArgumentParser()
  parser.add_argument('--skija-version')
  (args, _) = parser.parse_known_args()

  classpath = common.deps_run()
  if args.skija_version:
    classpath += [
      common.fetch_maven('io.github.humbleui', 'skija-shared', args.skija_version),
      common.fetch_maven('io.github.humbleui', 'skija-' + common.classifier, args.skija_version)
    ]
  else:
    build.main()
    classpath += [
      '../shared/target/classes-java9',
      '../shared/target/classes',
      f'../platform/target/{common.classifier}/classes'
    ]

  os.chdir(common.basedir + '/tests')
  sources = build_utils.files('java/**/*.java')
  build_utils.javac(sources, 'target/classes', classpath = classpath)

  subprocess.check_call(['java',
    '--class-path', build_utils.classpath_join(classpath + ['target/classes']),
    *(['-XstartOnFirstThread'] if 'macos' == build_utils.system else []),
    '-Djava.awt.headless=true',
    '-enableassertions',
    '-enablesystemassertions',
    '-Xcheck:jni',
    '-Dskija.logLevel=DEBUG',
    'io.github.humbleui.skija.test.TestSuite'])

  return 0

if __name__ == '__main__':
  sys.exit(main())
