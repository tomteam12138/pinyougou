package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wang on 2019/5/8.
 */
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService brandService;
    @RequestMapping("/save")
    public Result save(@RequestBody Brand brand){
        try {
            brand.setStatus(0);
            brandService.save(brand);
            return new Result(true, "申请成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"申请失败");
        }
    }
}
