package com.view.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * 清除NC缓存工具类
 */

public class NCCache {
    private NCCache(){}
    private static NCCache instance = new NCCache();
    public static NCCache getInstance(){
        return instance;
    }
//    public static void main(String[] args) throws IOException {
//        //new NCCache().clearNCCache("10.0.0.202","9064");
//        System.out.println(System.getProperties().getProperty("user.home"));
//    }

    /**
     * 清除NC的缓存
     * @param ip
     * @param port
     */
    public void clearNCCache(String ip, String port) {
        try {
            Files.list(Paths.get(System.getProperties().getProperty("user.home") + File.separator + "NCCACHE"))
                    .filter(p -> p.getFileName().toString().startsWith(ip) && p.getFileName().toString().endsWith(port))
                    .forEach(this::deleteDirectory);
        } catch (IOException e) {
            System.out.println("清除缓存失败！");
        }
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param path 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    private void deleteDirectory(Path path) {
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!path.toFile().exists() || !path.toFile().isDirectory()) {
            return;
        }
        //删除文件夹下的所有文件
        Arrays.asList(path.toFile().listFiles()).stream().filter(p -> p.isFile()).forEach(f -> f.delete());
        Arrays.asList(path.toFile().listFiles()).stream().filter(p -> p.isDirectory()).forEach(t -> {
            this.deleteDirectory(t.toPath());
        });
        path.toFile().delete();
    }
}