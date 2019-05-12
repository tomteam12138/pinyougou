package cn.itcast.core.controller;

import cn.itcast.core.pojo.ad.Content;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.ContentService;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wang on 2019/4/16.
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    @Reference
    private ItemCatService itemCatService;

    @RequestMapping("/findByCategoryId")
    public List<Content> findByCategoryId(Long categoryId){

        return contentService.findByCategoryId(categoryId);
    }

    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId){
        List<ItemCat> byParentId = itemCatService.findByParentId(parentId);
        return byParentId;
    }
}
