#! /usr/bin/env python3
import argparse, build_utils, contextlib, functools, os, pathlib, platform, re, shutil, subprocess, sys, time, urllib.request, zipfile

basedir = os.path.abspath(os.path.dirname(__file__) + '/..')

classifier = build_utils.system + '-' + build_utils.arch
module = 'io.github.humbleui.skija.' + build_utils.system + '.' + build_utils.arch

runtime_deps = [
  {'group': 'io.github.humbleui', 'name': 'types', 'version': '0.2.0'},
]

compile_deps = [
  {'group': 'org.jetbrains', 'name': 'annotations', 'version': '20.1.0'},
  {'group': 'org.projectlombok', 'name': 'lombok', 'version': '1.18.28'},
]

@functools.lru_cache(maxsize=1)
def deps_run():
  return build_utils.fetch_all_maven(runtime_deps)

@functools.lru_cache(maxsize=1)
def deps_compile():
  return build_utils.fetch_all_maven(compile_deps) + deps_run()

version = build_utils.get_arg("version") or build_utils.parse_ref() or build_utils.parse_sha() or "0.0.0-SNAPSHOT"