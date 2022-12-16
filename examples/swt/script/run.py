#! /usr/bin/env python3
import argparse, glob, os, subprocess, sys
sys.path.append(os.path.normpath(os.path.dirname(__file__) + '/../../../script'))
import common, build, build_utils

def main():
  parser = argparse.ArgumentParser()
  parser.add_argument('--skija-version')
  (args, _) = parser.parse_known_args()

  # Javac
  swt_artifact = {'macos': 'org.eclipse.swt.cocoa.macosx.x86_64',
                  'windows': 'org.eclipse.swt.win32.win32.x86_64',
                  'linux': 'org.eclipse.swt.gtk.linux.x86_64'}[build_utils.system]
  classpath = common.deps_compile() + [
    # build_utils.fetch_maven('org.projectlombok', 'lombok', '1.18.20'),
    build_utils.fetch_maven('org.eclipse.platform', swt_artifact, '3.115.100')
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

  sources = build_utils.files('src/**/*.java')
  build_utils.javac(sources, 'target/classes', classpath = classpath, release = '16')

  # Java
  subprocess.check_call([
    'java',
    '--class-path', build_utils.classpath_join(['target/classes'] + classpath)]
    + (['-XstartOnFirstThread'] if 'macos' == build_utils.system else [])
    + ['-Djava.awt.headless=true',
    '-enableassertions',
    '-enablesystemassertions',
    '-Dskija.logLevel=DEBUG',
    'io.github.humbleui.skija.examples.swt.Main'])

  return 0

if __name__ == '__main__':
  sys.exit(main())