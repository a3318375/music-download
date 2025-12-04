# read_pkl.py
import sys
import pickle
import json
import os

# 强制 stdout 使用 UTF-8（关键！）
if sys.stdout.encoding != 'UTF-8':
    import io
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

def main():
    if len(sys.argv) != 2:
        print("Usage: python read_pkl.py <path/to/search_results.pkl>", file=sys.stderr)
        sys.exit(1)

    pkl_path = sys.argv[1]
    if not os.path.isfile(pkl_path):
        print(f"Error: File not found: {pkl_path}", file=sys.stderr)
        sys.exit(1)

    try:
        with open(pkl_path, 'rb') as f:
            data = pickle.load(f)
        # 确保输出是 UTF-8
        json_str = json.dumps(data, ensure_ascii=False, indent=2)
        print(json_str)
    except Exception as e:
        print(f"Error: {e}", file=sys.stderr)
        sys.exit(1)

if __name__ == '__main__':
    main()