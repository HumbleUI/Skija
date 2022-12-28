#! /usr/bin/env python3
import argparse, glob, os, subprocess, sys
sys.path.append(os.path.normpath(os.path.dirname(__file__) + '/../../../script'))
import common, build, build_utils

def main():
  parser = argparse.ArgumentParser()
  parser.add_argument('--skija-version')
  parser.add_argument('--verbose', action='store_true')
  (args, _) = parser.parse_known_args()

  # Javac
  classpath = common.deps_compile() + [
    build_utils.fetch_maven('com.google.code.gson', 'gson', '2.8.6')
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

  os.chdir(os.path.join(os.path.dirname(__file__), os.pardir))

  sources = build_utils.files('src_java/**/*.java', '../scenes/src/**/*.java')
  build_utils.javac(sources, 'target/classes', classpath = classpath, release = '16')

  # Rust
  subprocess.check_call(['cargo', 'build', '--release', '--lib'])

  # Java
  env = os.environ.copy()
  env['RUST_BACKTRACE'] = '1'
  # if 'windows' == build_utils.system:
  #   env['KWINIT_ANGLE'] = '1'

  subprocess.check_call([
    'java',
    '--class-path', build_utils.classpath_join(['target/classes'] + classpath)]
    + (['-XstartOnFirstThread'] if 'macos' == build_utils.system else [])
    + ['-Djava.awt.headless=true',
    '-enableassertions',
    '-enablesystemassertions',
    '-Xcheck:jni',
    '-Dskija.logLevel=DEBUG',
    'noria.kwinit.impl.Main'] + (["--verbose"] if args.verbose else []),
    env=env)

  return 0

if __name__ == '__main__':
  sys.exit(main())
