#!/bin/bash
set -o errexit -o nounset -o pipefail

apt-get update -y
apt-get install build-essential -y
apt-get install gcc-10-aarch64-linux-gnu g++-10-aarch64-linux-gnu -y

apt-get install git python3 wget -y
apt-get install ninja-build fontconfig libfontconfig1-dev libglu1-mesa-dev libegl1-mesa-dev libgles2-mesa-dev curl zip clang -y