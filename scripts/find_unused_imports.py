#!/usr/bin/env python3
import re
import sys
from pathlib import Path

root = Path('src')
java_files = list(root.rglob('*.java'))

unused = {}

import_re = re.compile(r'^import\s+([^;]+);')

for f in java_files:
    text = f.read_text(encoding='utf-8')
    lines = text.splitlines()
    imports = []
    for i, line in enumerate(lines):
        m = import_re.match(line.strip())
        if m:
            imp = m.group(1).strip()
            # skip wildcard or static
            if imp.endswith('.*') or imp.startswith('static '):
                continue
            imports.append((i+1, imp))
    if not imports:
        continue
    unused_lines = []
    for lineno, imp in imports:
        # get simple name
        simple = imp.split('.')[-1]
        # search for simple name usage in file excluding import line
        occurrences = [i for i,l in enumerate(lines, start=1) if i!=lineno and simple in l]
        if not occurrences:
            # also check fully qualified usage
            fq = imp
            if fq not in text:
                unused_lines.append((lineno, imp))
    if unused_lines:
        unused[str(f)] = unused_lines

if not unused:
    print('No unused imports found')
    sys.exit(0)

for path, items in unused.items():
    print(path)
    for lineno, imp in items:
        print(f'  line {lineno}: {imp}')

sys.exit(0)
