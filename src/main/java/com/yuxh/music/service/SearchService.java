package com.yuxh.music.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.yuxh.music.dto.SongInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Aizmr
 * @CreateTime: 2025-12-04
 * @Description:
 */
@Service
@Slf4j
public class SearchService {

    public List<SongInfo> list(String name) {
        List<SongInfo> list = new ArrayList<>();
        try {
            // 构建命令：假设 python3 在 PATH 中
            ProcessBuilder pb = new ProcessBuilder("python", "/home/app/search_music.py", name);
            pb.redirectErrorStream(true); // 合并 stderr 到 stdout

            Process process = pb.start();

            // 读取 Python 脚本的输出
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("搜索结果（JSON）：");
                System.out.println(output.toString());
                // 可用 Jackson/Gson 解析 JSON
                return JSON.parseArray(output.toString(), SongInfo.class);
            } else {
                throw new RuntimeException("搜索异常：" + output);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("搜索异常", e);
        }
    }

    public List<SongInfo> history(String name) {
        SongInfo aa = new SongInfo();
        aa.setSongName("111");
        // 1. 设置 .pkl 文件路径（注意 Windows 路径分隔符）
        String pklPath = "musicdl_outputs\\NeteaseMusicClient\\2025-12-04-10-02-32 尾戒\\search_results.pkl";

        // 2. 调用 Python 脚本并捕获其 stdout（即 JSON 内容）
        String jsonOutput = callPythonScript(pklPath);

        return JSON.parseArray(jsonOutput, SongInfo.class);
    }

    private static String callPythonScript(String pklPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "python",
                    "/home/app/read_pkl.py",
                    pklPath
            );
            pb.redirectErrorStream(false); // stderr 单独处理

            Process process = pb.start();

            // 读取 stdout（JSON）
            StringBuilder stdout = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stdout.append(line).append("\n");
                }
            }

            // 读取 stderr（错误信息）
            StringBuilder stderr = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stderr.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Python script failed:\n" + stderr.toString());
                return null;
            }

            return stdout.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
