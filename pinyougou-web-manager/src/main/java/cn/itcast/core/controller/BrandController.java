package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.BrandService;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2019/4/6.
 */
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService brandService;
    //查找全部品牌不带分页
    @RequestMapping("/findAll")

    public List<Brand> findAll(){
        return brandService.findAll();
    }
    //查找全部品牌带分页
    @RequestMapping("/findPage")
    public PageResult findPage(Integer pageNum,Integer pageSize){

        return brandService.findPage(pageNum,pageSize);
    }
    //添加新品牌功能
    @RequestMapping("/add")
    public Result addBrand(@RequestBody Brand brand){
        try {
            brandService.addBrand(brand);
            return new Result(true, "添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }
    //修改brand 数据回显功能
    @RequestMapping("/findOne")
    public Brand findById(Long id){
        Brand brand = brandService.findById(id);
        return brand;
    }
    //修改品牌信息
    @RequestMapping("/update")
    public Result updateBrand(@RequestBody Brand brand){
        try {
            brandService.updateBrand(brand);
            return new Result(true, "修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }
    //批量删除
    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            brandService.delete(ids);
            return new Result(true, "成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }

    @Reference
    private OrderService orderService;
    //关键字搜索功能带分页
    @RequestMapping("/search")
    public PageResult search(Integer pageNum,Integer pageSize,@RequestBody(required = false)Brand brand) {
        System.out.println();
        PageResult result = brandService.search(pageNum, pageSize, brand);
        return result;
    }
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }
}
