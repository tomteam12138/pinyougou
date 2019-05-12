package cn.itcast.core.controller;

import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatNew;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wang on 2019/4/12.
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {
    @Reference
    private ItemCatService itemCatService;
    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId){
        return itemCatService.findByParentId(parentId);
    }

    @RequestMapping("/findOne")
    public ItemCat findOne(Long id){
       return itemCatService.findOne(id);
    }

    @RequestMapping("/update")
    public Result update(ItemCat itemCat){
        try {
            itemCatService.update(itemCat);
            return new Result(true, "成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }

    @RequestMapping("/add")
    public Result add(ItemCat itemCat){
        try {
            itemCatService.add(itemCat);
            return new Result(true, "成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }
    @RequestMapping("/findAll")
    public List<ItemCat> findAll(){
        return itemCatService.findAll();
    }

    @RequestMapping("/findApply1")
    public Result findApply1(String name1){
        ItemCatNew apply2 = itemCatService.findApply2(name1);
            if (apply2 != null){
                return new Result(false,"一级目录已经存在");
            }else {
                return new Result(true,"可以添加一级目录");
            }

    }
    @RequestMapping("/findApply2")
    public Result findApply2(String name1,String name2,String name3){
        ItemCatNew apply2 = itemCatService.findApply2(name2);
        if(apply2==null){
            addApply2(name1,name2);
            if(name3!=null){
                if(itemCatService.findApply2(name3)==null){
                    addApply2(name2,name3);
                    return new Result(true,"添加三级成功");
                }
            }
            return new Result(true,"添加二级成功");
        }else {
            ItemCatNew apply3 = itemCatService.findApply2(name3);
            if(apply3 != null){
                return new Result(false,"该分类已存在");
            }else {
                itemCatService.addApply2(name2,name3);
                return new Result(true,"添加三级成功");
            }
        }

    }
    @RequestMapping("/addApply1")
    public void addApply1(String name1){
       itemCatService.addApply1(name1);
    }

    public void addApply2(String name1,String name2){
        itemCatService.addApply2(name1,name2);
    }

}
