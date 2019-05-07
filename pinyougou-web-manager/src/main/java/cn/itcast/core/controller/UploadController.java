package cn.itcast.core.controller;

import cn.itcast.core.util.FastDFSClient;
import entity.Result;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by wang on 2019/4/16.
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    public void setFILE_SERVER_URL(String FILE_SERVER_URL) {
        this.FILE_SERVER_URL = FILE_SERVER_URL;
    }

    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file){
        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String imagePath = fastDFSClient.uploadFile(file.getBytes(), extension, null);

            return new Result(true,FILE_SERVER_URL+imagePath);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true,"失败");

        }
    }
}

