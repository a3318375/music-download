# search_music.py
import sys
import json
from musicdl import musicdl

def main():
    if len(sys.argv) < 2:
        print(json.dumps({"error": "Missing keyword"}))
        return

    keyword = sys.argv[1]
    try:
        music_client = musicdl.MusicClient(music_sources=['NeteaseMusicClient'])
        search_results = music_client.search(keyword=keyword)

        # 提取所有歌曲信息
        song_infos = []
        for songs in search_results.values():
            song_infos.extend(songs)

        # 输出为 JSON（确保 Java 能解析）
        print(json.dumps(song_infos, ensure_ascii=False))
    except Exception as e:
        print(json.dumps({"error": str(e)}))

if __name__ == "__main__":
    main()