package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private SeckillOrderDao seckillOrderDao;

    @Override
    public PageResult seckillorder(Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Page<SeckillOrder> p = (Page<SeckillOrder>) seckillOrderDao.selectByExample(null);
        return new PageResult(p.getTotal(), p.getResult());
    }
}
