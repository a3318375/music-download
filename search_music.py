# search_music.py
import sys
import json
import os
from musicdl import musicdl

def main():
    if len(sys.argv) < 2:
        print(json.dumps([]))
        return

    keyword = sys.argv[1]

    # 保存原始 stdout
    original_stdout = sys.stdout

    try:
        # 临时将 stdout 重定向到 /dev/null（丢弃所有打印）
        with open(os.devnull, 'w') as devnull:
            sys.stdout = devnull
            # 现在调用 musicdl 不会输出进度条
            music_client = musicdl.MusicClient(music_sources=['NeteaseMusicClient'])
            search_results = music_client.search(keyword=keyword)

        # 恢复 stdout
        sys.stdout = original_stdout

        # 提取结果
        song_infos = []
        for songs in search_results.values():
            song_infos.extend(songs)

        # 安全输出纯 JSON
        print(json.dumps(song_infos, ensure_ascii=False))

    except Exception as e:
        # 恢复 stdout（防止异常时卡住）
        sys.stdout = original_stdout
        # 错误信息走 stderr
        print(f"Error: {str(e)}", file=sys.stderr)
        print(json.dumps([]))

if __name__ == "__main__":
    main()