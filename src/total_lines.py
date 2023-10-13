from pathlib import Path


def main() -> None:
    total_lines = 0
    for path in Path('.').rglob('*.java'):
        with path.open() as f:
            total_lines += len(f.readlines())
    print(f'Total lines of Java code: {total_lines}')


if __name__ == '__main__':
    main()
