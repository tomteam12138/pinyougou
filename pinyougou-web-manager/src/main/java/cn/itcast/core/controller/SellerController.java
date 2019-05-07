package cn.itcast.core.controller;

import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.service.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2019/4/12.
 */
@RestController
@RequestMapping("/seller")
public class SellerController {
    @Reference
    private SellerService sellerService;
    @RequestMapping("/search")
    public PageResult search(Integer page,Integer rows,@RequestBody Seller seller){
        return sellerService.search(page,rows,seller);
    }

    @RequestMapping("/findOne")
    public Seller findOne(String id ){
        return sellerService.findOne(id);
    }
    @RequestMapping("/updateStatus")
    public Result updateStatus(Seller seller){
        try {
            sellerService.updateStatus(seller);
            return new Result(true, "添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }


}
