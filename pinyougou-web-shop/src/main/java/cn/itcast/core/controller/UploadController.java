package cn.itcast.core.controller;

import cn.itcast.core.util.FastDFSClient;
import entity.Result;
import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by wang on 2019/4/13.
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;
    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file){
        try {
            //工具类中的方法,指定fdfs_client.conf配置文件所在的位置,读取tracker_server的配置信息
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
            String originalFilename = file.getOriginalFilename();
            //使用io包提供的工具类来获取文件的后缀
            String extension = FilenameUtils.getExtension(originalFilename);
            //使用fastDFS客户端对象来往fastDFS传输图片以二进制的形式,并获取返回的图片路径
            String imgPath = fastDFSClient.uploadFile(file.getBytes(), extension, null);
            //FILE_SERVER_URL+imgPath 图片存在fastDFS的资源路径
            return new Result(true, FILE_SERVER_URL+imgPath);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }

}
