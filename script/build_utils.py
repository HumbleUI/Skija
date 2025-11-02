#! /usr/bin/env python3
import argparse, base64, collections, functools, glob, hashlib, itertools, json, os, pathlib, platform, random, re, shutil, subprocess, tempfile, time, urllib.request, zipfile
from typing import List, Tuple

def get_arg(name):
  parser = argparse.ArgumentParser()
  parser.add_argument(f'--{name}')
  (args, _) = parser.parse_known_args()
  return vars(args).get(name.replace("-", "_"))

execdir = os.getcwd()
native_arch = {'AMD64': 'x64', 'x86_64': 'x64', 'arm64': 'arm64', 'aarch64': 'arm64'}[platform.machine()]
arch   = get_arg("arch")   or native_arch
system = get_arg("system") or {'Darwin': 'macos', 'Linux': 'linux', 'Windows': 'windows'}[platform.system()]
classpath_separator = ';' if platform.system() == 'Windows' else ':'
mvn = "mvn.cmd" if platform.system() == "Windows" else "mvn"
lombok_version = '1.18.42'

def classpath_join(entries):
  return classpath_separator.join(entries)

def parse_ref() -> str:
  ref = get_arg("ref") or os.getenv('GITHUB_REF')
  if ref and ref.startswith('refs/tags/'):
    return ref[len('refs/tags/'):]

def parse_sha() -> str:
  sha = get_arg("sha") or os.getenv('GITHUB_SHA')
  if sha:
    return sha[:10]

def release_notes(version: str):
  with open('CHANGELOG.md', 'r') as f:
    lines = f.readlines()

  # Find the header that starts with "# {version}"
  start_idx = None
  for i, line in enumerate(lines):
    if line.startswith(f'# {version}'):
      start_idx = i
      break

  if start_idx is None:
    raise Exception(f"Version {version} not found in CHANGELOG.md")

  # Extract lines after the header until the next header (line starting with #) or end of file
  content_lines = []
  for i in range(start_idx + 1, len(lines)):
    line = lines[i]
    if line.startswith('#'):
      break
    content_lines.append(line)

  # Write to RELEASE_NOTES.md
  content = ''.join(content_lines).strip() + '\n'
  with open('RELEASE_NOTES.md', 'w') as f:
    f.write(content)

  print(f"Wrote release notes for {version} to RELEASE_NOTES.md", flush=True)

def makedirs(path):
  os.makedirs(path, exist_ok=True)

def rmdir(path):
  shutil.rmtree(path, ignore_errors=True)

def cat(iterables):
  return list(itertools.chain(*iterables))

def files(*patterns):
  return cat([glob.glob(pattern, recursive=True) for pattern in patterns])

def slurp(path):
  if os.path.exists(path):
    with open(path, 'r') as f:
      return f.read()

def copy_replace(src, dst, replacements):
  original = slurp(src)
  updated = original
  for key, value in replacements.items():
    updated = updated.replace(key, value)
  makedirs(os.path.dirname(dst))
  if updated != slurp(dst):
    print("Writing", dst, flush=True)
    with open(dst, 'w') as f:
      f.write(updated)

def copy_newer(src, dst):
  if not os.path.exists(dst) or os.path.getmtime(src) > os.path.getmtime(dst):
    if os.path.exists(dst):
      os.remove(dst)
    makedirs(os.path.dirname(dst))
    shutil.copy2(src, dst)
    return True

def has_newer(sources, targets):
  mtime = time.time()
  if sources and not targets:
    return True
  for target in targets:
    if os.path.exists(target):
      mtime = min(mtime, os.path.getmtime(target))
    else:
      mtime = 0
      break
  for path in sources:
    if os.path.getmtime(path) > mtime:
      return True
  return False

def ninja(dir):
  error_summary_pattern = re.compile(r'(\d+) errors? generated\.')
  compile_pattern = re.compile(r'^/usr/bin/(?:c\+\+|clang\+\+|g\+\+)')
  define_pattern = re.compile(r'(?:-D\S+\s*)+')
  include_pattern = re.compile(r'(?:-I\S+\s*)+')
  failed_files = 0
  total_errors = 0

  process = subprocess.Popen(
    ['ninja', '-k', '0'],
    cwd=dir,
    stdout=subprocess.PIPE,
    stderr=subprocess.STDOUT,
    text=True,
    bufsize=1
  )

  for line in process.stdout:
    if compile_pattern.match(line):
      line = define_pattern.sub('-D[...] ', line)
      line = include_pattern.sub('-I[...] ', line)

    print(line, end='', flush=True)

    error_summary_match = error_summary_pattern.search(line)
    if error_summary_match:
      errors = int(error_summary_match.group(1))
      total_errors += errors
      failed_files += 1

  process.wait()
  if process.returncode != 0:
    print(f"\nBUILD FAILED, files: {failed_files}, errors: {total_errors}")
    sys.exit(process.returncode)

def fetch(url, file):
  if not os.path.exists(file):
    print('Downloading', url, flush=True)
    data = urllib.request.urlopen(url).read()
    if os.path.dirname(file):
      makedirs(os.path.dirname(file))
    with open(file, 'wb') as f:
      f.write(data)

def fetch_maven(group, name, version, classifier=None, repo='https://repo1.maven.org/maven2'):
  path = '/'.join([group.replace('.', '/'), name, version, name + '-' + version + ('-' + classifier if classifier else '') + '.jar'])
  file = os.path.join(os.path.expanduser('~'), '.m2', 'repository', path)
  fetch(repo + '/' + path, file)
  return file

def fetch_all_maven(deps, repo='https://repo1.maven.org/maven2'):
  return [fetch_maven(dep['group'], dep['name'], dep['version'], repo=dep.get('repo', repo)) for dep in deps]

@functools.lru_cache(maxsize=1)
def jdk_version() -> Tuple[int, int, int]:
  output = subprocess.run(['java', '-version'], capture_output=True, text=True).stderr
  match = re.search(r'"([^"]+)"', output)
  if not match:
    raise Exception(f"Could not parse java version from: {output}")
  version_str = match.group(1)
  if version_str.startswith('1.'):
    # Old format: 1.8.0_181 -> (8, 0, 181)
    parts = version_str.split('.')
    major = int(parts[1])
    if len(parts) > 2:
      minor_patch = parts[2].split('_')
      minor = int(minor_patch[0])
      patch = int(minor_patch[1]) if len(minor_patch) > 1 else 0
    else:
      minor = 0
      patch = 0
  else:
    # New format: 11.0.2 -> (11, 0, 2), 17.0.1+12 -> (17, 0, 1)
    parts = version_str.split('.')
    major = int(parts[0])
    minor = int(parts[1]) if len(parts) > 1 else 0
    if len(parts) > 2:
      patch_str = re.split(r'[+\-]', parts[2])[0]
      patch = int(patch_str) if patch_str else 0
    else:
      patch = 0
  return (major, minor, patch)

def javac_sources(sources):
  groups = collections.defaultdict(list)
  for path in sources:
    groups[os.path.dirname(path)].append(os.path.basename(path))
  sorted_keys = sorted(groups.keys(), key=str.lower)
  lines = []
  for key in sorted_keys:
    sorted_values = sorted(groups[key], key=str.lower)
    lines.append(f"  {key}/{' '.join(sorted_values)}")

  return '\n'.join(lines)

def javac(sources, target, classpath = [], modulepath = [], add_modules = [], release = '11', opts=[]):
  makedirs(target)
  classes = {path.stem: path.stat().st_mtime for path in pathlib.Path(target).rglob('*.class') if '$' not in path.stem}
  newer = lambda path: path.stem not in classes or path.stat().st_mtime > classes.get(path.stem)
  new_sources = sorted([path for path in sources if newer(pathlib.Path(path))], key=str.lower)
  if new_sources:
    print('Compiling', len(new_sources), 'java files to', target + ':\n' + javac_sources(new_sources), flush=True)
    subprocess.check_call([
      'javac',
      '-encoding', 'UTF8',
      '-Xlint:-options',
      '-Xlint:deprecation',
      *(['-proc:full', '-J--sun-misc-unsafe-memory-access=allow'] if jdk_version()[0] >= 23 else []),
      *opts,
      '--release', release,
      *(['--class-path', classpath_join(classpath + [target])] if classpath else []),
      *(['--module-path', classpath_join(modulepath)] if modulepath else []),
      *(['--add-modules', ','.join(add_modules)] if add_modules else []),
      '-d', target,
      *new_sources])

def jar(target: str, *content: List[Tuple[str, str]], opts=[]) -> str:
  if has_newer(files(*[dir + "/" + subdir + "/**" for (dir, subdir) in content]), [target]):
    print(f"Packaging {os.path.basename(target)}", flush=True)
    makedirs(os.path.dirname(target))
    subprocess.check_call(["jar",
      "--create",
      "--file", target,
      *cat([["-C", dir, file] for (dir, file) in content])] + opts)
  return target

@functools.lru_cache(maxsize=1)
def lombok():
  return fetch_maven('org.projectlombok', 'lombok', lombok_version)

def delombok(dirs: List[str], target: str, classpath: List[str] = [], modulepath: List[str] = []):
  sources = files(*[dir + "/**/*.java" for dir in dirs])
  if has_newer(sources, files(target + "/**")):
    print("Delomboking", *dirs, "to", target, flush=True)
    subprocess.check_call(["java",
      "-Dfile.encoding=UTF8",
      "-jar", lombok(),
      "delombok",
      *dirs,
      '--encoding', 'UTF-8',
      *(["--classpath", classpath_join(classpath)] if classpath else []),
      *(["--module-path", classpath_join(modulepath)] if modulepath else []),
      "--target", target
    ])

def javadoc(dirs: List[str], target: str, classpath: List[str] = [], modulepath: List[str] = []):
  sources = files(*[dir + "/**/*.java" for dir in dirs])
  if has_newer(sources, files(target + "/**")):
    print("Generating JavaDoc", *dirs, "to", target, flush=True)
    subprocess.check_call(["javadoc",
      "-encoding", "UTF-8",
      *(["--class-path", classpath_join(classpath)] if classpath else []),
      *(["--module-path", classpath_join(modulepath)] if modulepath else []),
      "-d", target,
      "-quiet",
      "-Xdoclint:all,-missing",
      *sources])

def collect_jars(group, name, version, jars, target_dir):
  # output_dir: target_dir/group/name/version
  group_path = group.replace('.', '/')
  output_dir = os.path.join(target_dir, group_path, name, version)
  makedirs(output_dir)

  all_files = []

  # Move all JARs to output directory
  print(f"Collecting {output_dir}")
  for jar in jars:
    jar_basename = os.path.basename(jar)
    dest_jar = os.path.join(output_dir, jar_basename)
    shutil.copy2(jar, dest_jar)
    all_files.append(dest_jar)
    print(f"  Copied {jar}")

  # Extract POM from first JAR
  pom_path = os.path.join(output_dir, f"{name}-{version}.pom")
  with zipfile.ZipFile(jars[0], 'r') as f:
    poms = [path for path in f.namelist() if re.fullmatch(r"META-INF/maven/.*/pom\.xml", path)]
    if not poms:
      raise Exception(f"  No pom.xml found in {jars[0]}")
    with f.open(poms[0]) as pom:
      with open(pom_path, 'wb') as out:
        out.write(pom.read())
    print(f"  Extracted {poms[0]}")
  all_files.append(pom_path)

  # Signing
  for file_path in all_files:
    basename = os.path.basename(file_path)

    # GPG
    asc_file = file_path + '.asc'
    if os.path.exists(asc_file):
      os.remove(asc_file)
    subprocess.check_call(['gpg', '--quiet', '--detach-sign', '--armor', file_path])
    print(f"  GPG  {basename}")

    # MD5
    with open(file_path, 'rb') as f:
      md5_hash = hashlib.md5(f.read()).hexdigest()
    with open(file_path + '.md5', 'w') as f:
      f.write(md5_hash)
    print(f"  MD5  {basename}")

    # SHA1
    with open(file_path, 'rb') as f:
      sha1_hash = hashlib.sha1(f.read()).hexdigest()
    with open(file_path + '.sha1', 'w') as f:
      f.write(sha1_hash)
    print(f"  SHA1  {basename}")

  print(f"  [ DONE ] Collected artifacts in {output_dir}")
  return output_dir

def release(zip_name, target_dir):
  parent_dir = os.path.dirname(target_dir)
  zip_path = os.path.join(parent_dir, zip_name)

  # Create zip archive
  with zipfile.ZipFile(zip_path, 'w', zipfile.ZIP_DEFLATED) as zipf:
    for root, dirs, files in os.walk(target_dir):
      for file in files:
        file_path = os.path.join(root, file)
        arc_path = os.path.relpath(file_path, target_dir)
        zipf.write(file_path, arc_path)

  print(f"Created {zip_path}")

  # Upload to Sonatype Central
  bearer_token = os.getenv('SONATYPE_BEARER_TOKEN')
  if not bearer_token:
    print("SONATYPE_BEARER_TOKEN not set, skipping upload")
    return 1

  print("Uploading to Sonatype Central Portal...")
  boundary = '----WebKitFormBoundary' + ''.join([str(random.randint(0, 9)) for _ in range(16)])
  with open(zip_path, 'rb') as f:
    zip_data = f.read()
  body = []
  body.append(f'--{boundary}'.encode())
  body.append(b'Content-Disposition: form-data; name="bundle"; filename="' + zip_name.encode() + b'"')
  body.append(b'Content-Type: application/octet-stream')
  body.append(b'')
  body.append(zip_data)
  body.append(f'--{boundary}--'.encode())

  url = f'https://central.sonatype.com/api/v1/publisher/upload?name={zip_name}&publishingType=AUTOMATIC'
  req = urllib.request.Request(url, data=b'\r\n'.join(body))
  req.add_header('Authorization', f'Bearer {bearer_token}')
  req.add_header('Content-Type', f'multipart/form-data; boundary={boundary}')

  try:
    response = urllib.request.urlopen(req)
    response_data = response.read().decode('utf-8')
    print(f"Upload successful: {response.status} - {response_data}")
  except urllib.error.HTTPError as e:
    error_data = e.read().decode('utf-8') if e.fp else str(e)
    print(f"Upload failed: {e.code} - {error_data}")
    raise Exception(f"Failed to upload to Sonatype Central: {e.code} - {error_data}")

  # Check deployment status
  deployment_id = response_data
  while True:
    status_url = f'https://central.sonatype.com/api/v1/publisher/status?id={deployment_id}'
    status_req = urllib.request.Request(status_url, data=b'')
    status_req.add_header('Authorization', f'Bearer {bearer_token}')

    try:
      status_response = urllib.request.urlopen(status_req)
      status_data = json.loads(status_response.read().decode('utf-8'))

      deployment_state = status_data.get('deploymentState', 'UNKNOWN')
      print(f"Deployment {deployment_id} is {deployment_state}")

      if deployment_state == 'PUBLISHED':
        return 0

      if deployment_state == 'FAILED':
        print('Deployment failed', status_data)
        return 1

    except urllib.error.HTTPError as e:
      error_data = e.read().decode('utf-8') if e.fp else str(e)
      print(f"Failed to check deployment status: {e.code} - {error_data}")
      break

    time.sleep(10)

  print('should not return here')
  return 1
