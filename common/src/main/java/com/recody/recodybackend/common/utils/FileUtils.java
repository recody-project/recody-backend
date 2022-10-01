package com.recody.recodybackend.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
    public static String readResourceToString(Resource resource){
        try {
            return bytesToString(readBytesFrom(toPath(resource)));
        } catch (IOException e) {
            log.error("파일을 읽을 수 없습니다.: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public static byte[] readResourceToBytes(Resource resource){
        try {
            return readBytesFrom(toPath(resource));
        } catch (IOException e) {
            log.error("파일을 읽을 수 없습니다.: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    private static byte[] readBytesFrom(Path path) throws IOException {
        return Files.readAllBytes(path);
    }
    
    private static String bytesToString(byte[] bytes) throws IOException {
        return new String(bytes);
    }
    
    private static Path toPath(Resource resource) throws IOException {
        return resource.getFile().toPath();
    }
    
    private static Path asPath(String path) throws IOException {
        return getFile(path).toPath();
    }
    
    public static String readFileToString(String path) {
        try {
            return bytesToString(readBytesFrom(asPath(path)));
        } catch (FileNotFoundException e) {
            log.error("파일이 존재하지 않습니다. {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("파일을 읽을 수 없습니다.: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public static String readFileToString(Path path) {
        try {
            return bytesToString(readBytesFrom(path));
        } catch (FileNotFoundException e) {
            log.error("파일이 존재하지 않습니다. {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("파일을 읽을 수 없습니다.: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    public static String readFileToString(File file) {
        try {
            return bytesToString(readBytesFrom(file.toPath()));
        } catch (FileNotFoundException e) {
            log.error("파일이 존재하지 않습니다. {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("파일을 읽을 수 없습니다.: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    private static File getFile(String path) throws FileNotFoundException {
        return ResourceUtils.getFile(path);
    }
    
}
