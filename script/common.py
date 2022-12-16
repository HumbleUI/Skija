#! /usr/bin/env python3
import argparse, build_utils, contextlib, functools, os, pathlib, platform, re, shutil, subprocess, sys, time, urllib.request, zipfile

basedir = os.path.abspath(os.path.dirname(__file__) + '/..')

classifier = build_utils.system + '-' + build_utils.arch
module = 'io.github.humbleui.skija.' + build_utils.system + '.' + build_utils.arch

@functools.lru_cache(maxsize=1)
def deps_run():
  return [build_utils.fetch_maven('io.github.humbleui', 'types', '0.2.0')]

@functools.lru_cache(maxsize=1)
def deps_compile():
  return [
    build_utils.fetch_maven('org.projectlombok', 'lombok', '1.18.22'),
    build_utils.fetch_maven('org.jetbrains', 'annotations', '20.1.0')
  ] + deps_run()

version = build_utils.get_arg("version") or build_utils.parse_ref() or build_utils.parse_sha() or "0.0.0-SNAPSHOT"