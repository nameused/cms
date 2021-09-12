package com.github.cms.service;

import com.github.cms.controller.RoleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Value("${file.uploadPath}")
    private String uploadPath;
    @Value("${file.downloadPath}")
    private String downloadPath;


    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    public String fileUpload(MultipartFile file) {
        File FilePath = new File(uploadPath);
        if (!FilePath.exists()) {
            FilePath.mkdirs();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.format(new Date());
        File convertFile = new File(uploadPath + file.getOriginalFilename());
        try {
            convertFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(convertFile);
            fout.write(file.getBytes());
            fout.close();

        } catch (IOException e) {
            logger.error(e.getMessage());
            return "false";
        }
        return convertFile.getAbsolutePath();
    }


    /**
     * 加载文件
     *
     * @param fileName 文件名
     * @return 文件
     */
    public Resource loadFileAsResource(String fileName) throws IOException {
        try {
            Path filePath = Paths.get(downloadPath).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new IOException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new IOException("File not found " + fileName, ex);
        }
    }

}
