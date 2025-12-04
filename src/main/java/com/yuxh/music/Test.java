package com.yuxh.music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Aizmr
 * @CreateTime: 2025-12-04
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        try {
            // 构建命令：假设 python3 在 PATH 中
            ProcessBuilder pb = new ProcessBuilder("python", "search_music.py", "尾戒");
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
            } else {
                System.err.println("Python 脚本执行失败: " + output);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
