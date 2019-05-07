package cn.itcast.core.controller;


import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.service.SpecificationService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import jdk.nashorn.internal.runtime.RewriteException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.SpecificationVo;

import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2019/4/9.
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Reference
    private SpecificationService specificationService;
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody(required = false) Specification specification){
        return specificationService.search(page,rows,specification);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody SpecificationVo vo){
        try {
            specificationService.add(vo);
            return new Result(true, "添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }
    @RequestMapping("/findOne")
    public SpecificationVo findOne(Long id){
        return specificationService.findOne(id);
    }
    @RequestMapping("/update")
    public Result update(@RequestBody SpecificationVo vo){
        try {
            specificationService.update(vo);
            return new Result(true, "添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            specificationService.delete(ids);
            return new Result(true, "成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return specificationService.selectOptionList();
    }

}

