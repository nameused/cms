package com.github.cms.service;

import com.github.cms.controller.RoleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Value("${upload.defaultFilePath}")
    private String defaultFilePath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    public String  fileUpload(MultipartFile file) {
        File FilePath = new File(defaultFilePath);
        if (!FilePath.exists()) {
            FilePath.mkdirs();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.format(new Date());
        File convertFile = new File(defaultFilePath  + file.getOriginalFilename());
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

}
