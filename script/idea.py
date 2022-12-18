#! /usr/bin/env python3
import build_utils, common, os, sys
import xml.etree.ElementTree as et

def library(group, name, version):
  root = et.Element('component', {'name': 'libraryTable'})
  library = et.SubElement(root, 'library', {'name': f'{group}.{name}', 'type': 'repository'})

  mavenUrlBase = f'jar://$MAVEN_REPOSITORY$/{group.replace(".", "/")}/{name}/{version}/{name}-{version}'

  et.SubElement(library, 'properties', {'maven-id': f'{group}:{name}:{version}'})
  et.SubElement(et.SubElement(library, 'CLASSES'), 'root', {'url': f'{mavenUrlBase}.jar!/'})
  et.SubElement(library, 'JAVADOC')
  et.SubElement(et.SubElement(library, 'SOURCES'), 'root', {'url': f'{mavenUrlBase}-sources.jar!/'})

  build_utils.makedirs('.idea/libraries')
  et.ElementTree(root).write(f'.idea/libraries/{group.replace(".", "_")}_{name}.xml', encoding='UTF-8')
  return root

def modules():
  # Generate .idea/modules.xml
  root = et.Element('project', {'version': '4'})
  component = et.SubElement(root, 'component', {'name': 'ProjectModuleManager'})
  modules = et.SubElement(component, 'modules')

  et.SubElement(modules, 'module', {
    'fileurl': 'file://$PROJECT_DIR$/.idea/shared.iml',
    'filepath': '$PROJECT_DIR$/.idea/shared.iml'
  })
  et.SubElement(modules, 'module', {
    'fileurl': 'file://$PROJECT_DIR$/script/script.iml',
    'filepath': '$PROJECT_DIR$/script/script.iml'
  })

  et.ElementTree(root).write('.idea/modules.xml', encoding='UTF-8', xml_declaration=True)

def module_shared():
  # Generate .idea/shared.iml
  root = et.Element('module', {'type': 'JAVA_MODULE', 'version': '4'})
  component = et.SubElement(root, 'component', {'name': 'NewModuleRootManager', 'LANGUAGE_LEVEL': 'JDK_1_8', 'inherit-compiler-output': 'true'})

  et.SubElement(component, 'exclude-output')
  content = et.SubElement(component, 'content', {'url': 'file://$MODULE_DIR$'})
  et.SubElement(content, 'sourceFolder', {'url': 'file://$MODULE_DIR$/shared/java', 'isTestSource': 'false', 'packagePrefix': 'io.github.humbleui.skija'})
  et.SubElement(component, 'orderEntry', {'type': 'inheritedJdk'})
  et.SubElement(component, 'orderEntry', {'type': 'sourceFolder', 'forTests': 'false'})
  for dep in common.compile_deps + common.runtime_deps:
    et.SubElement(component, 'orderEntry', {'type': 'library', 'name': f'{dep["group"]}.{dep["name"]}', 'level': 'project'})

  et.ElementTree(root).write('.idea/shared.iml', encoding='UTF-8', xml_declaration=True)

def module_script():
  # Generate script/script.iml
  root = et.Element('module', {'version': '4'})
  component = et.SubElement(root, 'component', {'name': 'NewModuleRootManager', 'inherit-compiler-output': 'true'})

  et.SubElement(component, 'exclude-output')
  content = et.SubElement(component, 'content', {'url': 'file://$MODULE_DIR$'})
  et.SubElement(content, 'sourceFolder', {'url': 'file://$MODULE_DIR$', 'isTestSource': 'false'})
  et.SubElement(component, 'orderEntry', {'type': 'sourceFolder', 'forTests': 'false'})

  et.ElementTree(root).write('script/script.iml', encoding='UTF-8', xml_declaration=True)


def main():
  os.chdir(common.basedir)
  build_utils.makedirs('.idea')

  # Generate library definitions for all dependencies
  for dep in common.compile_deps + common.runtime_deps:
    library(dep['group'], dep['name'], dep['version'])

  modules()

  module_shared()
  module_script()

if __name__ == "__main__":
  sys.exit(main())
