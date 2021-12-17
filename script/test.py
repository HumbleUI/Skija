#! /usr/bin/env python3
import argparse, build, build_utils, common, glob, os, subprocess, sys

def main():
  parser = argparse.ArgumentParser()
  parser.add_argument('--skija-version')
  (args, _) = parser.parse_known_args()

  modulepath = common.deps_run()
  if args.skija_version:
    modulepath += [
      common.fetch_maven('io.github.humbleui.skija', 'skija-shared', args.skija_version),
      common.fetch_maven('io.github.humbleui.skija', 'skija-' + common.classifier, args.skija_version)
    ]
  else:
    build.main()
    modulepath += ['../shared/target/classes', '../platform/target/classes']

  os.chdir(common.basedir + '/tests')
  sources = build_utils.files('java/**/*.java')
  build_utils.javac(sources, 'target/classes', modulepath = modulepath, add_modules = [common.module])

  subprocess.check_call(['java',
    '--class-path', 'target/classes',
    '--module-path', build_utils.classpath_join(modulepath),
    '--add-modules', common.module,
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
