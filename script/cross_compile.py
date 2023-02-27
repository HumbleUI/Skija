#! /usr/bin/env python3
import build_utils, subprocess
from typing import List

def extract_deb(url: str, name: str, native_build_dir: str):
  deb_file = f'{native_build_dir}/{name}.deb'
  target = f'{native_build_dir}/{name}'
  build_utils.fetch(url, deb_file)
  subprocess.check_call(['dpkg-deb', '--extract', deb_file, target])
  return target

def setup_linux_arm64(native_build_dir: str, cmake_args: List[str]):
  deps_dir = f'{native_build_dir}/deps'
  build_utils.makedirs(deps_dir)

  # libfreetype.so
  freetype = extract_deb(
    'http://ftp.us.debian.org/debian/pool/main/f/freetype/libfreetype6_2.10.4+dfsg-1+deb11u1_arm64.deb',
    'libfreetype',
    native_build_dir
  )
  build_utils.copy_newer(
    f'{freetype}/usr/lib/aarch64-linux-gnu/libfreetype.so.6.17.4',
    f'{deps_dir}/libfreetype.so'
  )

  # libGL.so
  libgl1 = extract_deb(
    'http://ftp.us.debian.org/debian/pool/main/libg/libglvnd/libgl1_1.3.2-1_arm64.deb',
    'libgl1',
    native_build_dir
  )
  build_utils.copy_newer(
    f'{libgl1}/usr/lib/aarch64-linux-gnu/libGL.so.1.7.0',
    f'{deps_dir}/libGL.so'
  )

  # libfontconfig.so
  libfontconfig1 = extract_deb(
    'http://ftp.us.debian.org/debian/pool/main/f/fontconfig/libfontconfig1_2.13.1-4.2_arm64.deb',
    'libfontconfig1',
    native_build_dir
  )
  build_utils.copy_newer(
    f'{libfontconfig1}/usr/lib/aarch64-linux-gnu/libfontconfig.so.1.12.0',
    f'{deps_dir}/libfontconfig.so'
  )

  cmake_args += [
    '-DCMAKE_SYSTEM_NAME=Linux',
    '-DCMAKE_SYSTEM_PROCESSOR=aarch64',
    '-DCMAKE_C_COMPILER=/usr/bin/aarch64-linux-gnu-gcc-9',
    '-DCMAKE_CXX_COMPILER=/usr/bin/aarch64-linux-gnu-g++-9',
    '-DDEPS_DIR=' + deps_dir
  ]
