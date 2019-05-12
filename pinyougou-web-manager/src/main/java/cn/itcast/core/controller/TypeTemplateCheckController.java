package cn.itcast.core.controller;

import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.service.TypeTemplateCheckService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/typeTemplateCheck")
public class TypeTemplateCheckController {
    @Reference
    private TypeTemplateCheckService typeTemplateCheckService;
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows,@RequestBody TypeTemplate typeTemplate){
      return typeTemplateCheckService.search(page,rows,typeTemplate);
    }
    @RequestMapping("/delete")
    public Result delete(Long []ids){
        try {
            typeTemplateCheckService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long []ids,String status ){
        try {
            typeTemplateCheckService.updateStatus(ids,status);
            return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }

    }
}
