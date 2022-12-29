#! /usr/bin/env python3
import argparse, glob, os, subprocess, sys
sys.path.append(os.path.normpath(os.path.dirname(__file__) + '/../../../script'))
import common, build, build_utils

def main():
  parser = argparse.ArgumentParser()
  parser.add_argument('--skija-version')
  parser.add_argument('--lwjgl-version', default='3.3.1')
  (args, _) = parser.parse_known_args()

  # Javac
  if (build_utils.arch == 'x64'):
    lwjgl_classifier = "natives-" + build_utils.system
  else:
    lwjgl_classifier = "natives-" + common.classifier

  classpath = common.deps_compile() + [
    build_utils.fetch_maven('org.lwjgl', 'lwjgl', args.lwjgl_version),
    build_utils.fetch_maven('org.lwjgl', 'lwjgl-glfw', args.lwjgl_version),
    build_utils.fetch_maven('org.lwjgl', 'lwjgl-opengl', args.lwjgl_version),
    build_utils.fetch_maven('org.lwjgl', 'lwjgl', args.lwjgl_version, classifier=lwjgl_classifier),
    build_utils.fetch_maven('org.lwjgl', 'lwjgl-glfw', args.lwjgl_version, classifier=lwjgl_classifier),
    build_utils.fetch_maven('org.lwjgl', 'lwjgl-opengl', args.lwjgl_version, classifier=lwjgl_classifier)
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

  os.chdir(common.basedir + '/examples/lwjgl')

  sources = build_utils.files('src/**/*.java', '../scenes/src/**/*.java')
  build_utils.javac(sources, 'target/classes', classpath = classpath, release = '16')

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
    'io.github.humbleui.skija.examples.lwjgl.Main'])

  return 0

if __name__ == '__main__':
  sys.exit(main())
