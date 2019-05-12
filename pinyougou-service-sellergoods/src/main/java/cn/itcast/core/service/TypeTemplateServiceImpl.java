package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.dao.template.TypeTemplateDao;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.pojo.template.TypeTemplateQuery;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2019/4/10.
 */
@Service
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService{
    @Autowired
    private TypeTemplateDao typeTemplateDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    @Autowired
    private RedisTemplate redisTemplate;
    //全数据显示以及模糊查询
    @Override
    public PageResult search(Integer page, Integer rows,TypeTemplate typeTemplate) {
        List<TypeTemplate> typeTemplates = typeTemplateDao.selectByExample(null);
        for (TypeTemplate template : typeTemplates) {
            String brandIds = template.getBrandIds();
            //JSON.parseArray 把brandIds字符串转换成map集合
            List<Map> maps = JSON.parseArray(brandIds, Map.class);
            redisTemplate.boundHashOps("brandList").put(template.getId().toString(),maps);
            redisTemplate.boundHashOps("specList").put(template.getId().toString(),findBySpecList(template.getId()));
        }
        PageHelper.startPage(page,rows);
        TypeTemplateQuery typeTemplateQuery = new TypeTemplateQuery();
        if (typeTemplate.getName()!=null && !"".equals(typeTemplate.getName().trim())){
            TypeTemplateQuery.Criteria criteria = typeTemplateQuery.createCriteria();
            criteria.andNameLike("%"+typeTemplate.getName()+"%");
        }
        Page<TypeTemplate> typeTemplateList = (Page<TypeTemplate>)typeTemplateDao.selectByExample(typeTemplateQuery);
        return new PageResult(typeTemplateList.getTotal(),typeTemplateList.getResult());
    }

    @Override
    public void add(TypeTemplate typeTemplate) {
        typeTemplateDao.insertSelective(typeTemplate);
    }

    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateDao.deleteByPrimaryKey(typeTemplate.getId());
        typeTemplateDao.insertSelective(typeTemplate);
    }

    @Override
    public void delete(Long[] ids) {
        if (ids!=null && ids.length!=0){
            for (Long id : ids) {
                typeTemplateDao.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public List<Map> findBySpecList(Long id) {
        TypeTemplate typeTemplate = typeTemplateDao.selectByPrimaryKey(id);
        String specIds = typeTemplate.getSpecIds();
        List<Map> mapList = com.alibaba.fastjson.JSON.parseArray(specIds, Map.class);
        for (Map map : mapList) {
            Object id1 = map.get("id");
            SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
            SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
            criteria.andSpecIdEqualTo(Long.valueOf(id1.toString()));
            List<SpecificationOption> options = specificationOptionDao.selectByExample(specificationOptionQuery);
            map.put("options",options);
        }
        return mapList;
    }
}
