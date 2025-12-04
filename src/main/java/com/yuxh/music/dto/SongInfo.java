package com.yuxh.music.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Aizmr
 * @CreateTime: 2025-12-04
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongInfo {

    @JSONField(name = "singers")
    private String singers;

    @JSONField(name = "song_name")
    private String songName;

    @JSONField(name = "file_size")
    private String fileSize;

    @JSONField(name = "duration")
    private String duration;

    @JSONField(name = "album")
    private String album;

    @JSONField(name = "source")
    private String source;

    @JSONField(name = "ext")
    private String ext;
}
