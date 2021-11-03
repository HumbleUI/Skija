#! /usr/bin/env python3
import argparse, base64, build, clean, common, glob, json, os, platform, re, subprocess, sys, time, urllib, zipfile

def main():
  os.chdir(common.root)
  ossrh_username = os.getenv('OSSRH_USERNAME')
  ossrh_password = os.getenv('OSSRH_PASSWORD')
  version = common.version()
  if not re.fullmatch("\\d+\\.\\d+\\.\\d+", version):
    raise Exception("Expected version in a form of X.X.X, got: " + version)

  # Deploy

  mvn = "mvn.cmd" if common.system == "windows" else "mvn"
  mvn_settings = [
    '--settings', 'shared/deploy/settings.xml',
    '-Dossrh.username=' + ossrh_username,
    '-Dossrh.password=' + ossrh_password,
    '-Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/',
    '-DrepositoryId=ossrh'
  ]

  # Shared
  pom = "META-INF/maven/io.github.humbleui.skija/skija-shared/pom.xml"
  with zipfile.ZipFile(f'target/skija-shared-{version}.jar', 'r') as f:
    f.extract(pom, "shared/target/maven")

  print(f'Deploying skija-shared-{version}.jar')
  subprocess.check_call(
    [mvn, 'gpg:sign-and-deploy-file'] + \
    mvn_settings + \
    [f'-DpomFile=shared/target/maven/{pom}',
     f'-Dfile=target/skija-shared-{version}.jar'])

  print(f'Deploying skija-shared-{version}-sources.jar')
  subprocess.check_call(
    [mvn, 'gpg:sign-and-deploy-file'] + \
    mvn_settings + \
    [f'-DpomFile=shared/target/maven/{pom}',
     f'-Dfile=target/skija-shared-{version}-sources.jar',
     f'-Dclassifier=sources'])

  print(f'Deploying skija-shared-{version}-javadoc.jar')
  subprocess.check_call(
    [mvn, 'gpg:sign-and-deploy-file'] + \
    mvn_settings + \
    [f'-DpomFile=shared/target/maven/{pom}',
     f'-Dfile=target/skija-shared-{version}-javadoc.jar',
     f'-Dclassifier=javadoc'])

  # Platform
  for classifier in ['windows', 'linux', 'macos-x64', 'macos-arm64']:
    jar = f'target/skija-{classifier}-{version}.jar'
    pom = f'META-INF/maven/io.github.humbleui.skija/skija-{classifier}/pom.xml'
    with zipfile.ZipFile(jar, 'r') as f:
      f.extract(pom, "platform/target/maven")

    print(f'Deploying skija-{classifier}-{version}.jar')
    subprocess.check_call([mvn, 'gpg:sign-and-deploy-file'] + mvn_settings + [f'-DpomFile=platform/target/maven/{pom}', f'-Dfile={jar}'])

    print(f'Deploying skija-{classifier}-{version}-sources.jar')
    subprocess.check_call([mvn, 'gpg:sign-and-deploy-file'] + mvn_settings + [f'-DpomFile=platform/target/maven/{pom}', f'-Dfile=target/skija-{classifier}-{version}.jar', "-Dclassifier=sources"])

    print(f'Deploying skija-{classifier}-{version}-javadoc.jar')
    subprocess.check_call([mvn, 'gpg:sign-and-deploy-file'] + mvn_settings + [f'-DpomFile=platform/target/maven/{pom}', f'-Dfile=target/skija-{classifier}-{version}.jar', "-Dclassifier=javadoc"])

  # Release
  headers = {
    'Accept': 'application/json',
    'Authorization': 'Basic ' + base64.b64encode((ossrh_username + ":" + ossrh_password).encode('utf-8')).decode('utf-8'),
    'Content-Type': 'application/json',
  }

  def fetch(path, data = None):
    req = urllib.request.Request('https://s01.oss.sonatype.org/service/local/staging' + path,
                               headers=headers,
                               data = json.dumps(data).encode('utf-8') if data else None)
    resp = urllib.request.urlopen(req).read().decode('utf-8')
    # print(' ', path, "->", resp)
    return json.loads(resp) if resp else None

  print('Finding staging repo')
  resp = fetch('/profile_repositories')
  repo_id = resp['data'][0]["repositoryId"]
  
  print('Closing repo', repo_id)
  resp = fetch('/bulk/close', data = {"data": {"description": "", "stagedRepositoryIds": [repo_id]}})

  while True:
    print('Checking repo', repo_id, 'status')
    resp = fetch('/repository/' + repo_id + '/activity')
    close_events = [e for e in resp if e['name'] == 'close']
    close_events = close_events[0]['events'] if close_events else []
    fail_events = [e for e in close_events if e['name'] == 'ruleFailed']
    if fail_events:
      print(fail_events)
      return 1

    if close_events and close_events[-1]['name'] == 'repositoryClosed':
      break

    time.sleep(0.5)

  print('Releasing staging repo', repo_id)
  resp = fetch('/bulk/promote', data = {"data": {
              "autoDropAfterRelease": True,
              "description": "",
              "stagedRepositoryIds":[repo_id]
        }})

  return 0

if __name__ == "__main__":
  sys.exit(main())
