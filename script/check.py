#!/usr/bin/env python3

import sys
from pathlib import Path

def main():
    script_dir = Path(__file__).parent
    sys.path.insert(0, str(script_dir))

    # build.py
    import build
    result = build.main()
    if result != 0:
        return result

    # test.py
    import test
    result = test.main()
    if result != 0:
        return result

    # package_shared.py
    import package_shared
    result = package_shared.package()
    if result != 0:
        return result

    # package_platform.py
    import package_platform
    result = package_platform.package()
    if result != 0:
        return result

    # examples/jwm/script/run.py
    sys.path.insert(0, str(script_dir.parent / 'examples' / 'jwm' / 'script'))
    import run
    result = run.main()
    if result != 0:
        return result

    return 0

if __name__ == '__main__':
    sys.exit(main())
