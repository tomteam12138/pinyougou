package cn.itcast.core.service;

import cn.itcast.core.dao.template.TypeTemplateDao;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.pojo.template.TypeTemplateQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.transaction.annotation.Transactional;


import javax.print.attribute.standard.Destination;


@Service
@Transactional
public class TypeTemplateCheckServiceImpl implements TypeTemplateCheckService {
    @Autowired
    private TypeTemplateDao typeTemplateDao;


    @Override
    public PageResult search(Integer page, Integer rows, TypeTemplate typeTemplate) {

        PageHelper.startPage(page,rows);
        TypeTemplateQuery typeTemplateQuery = new TypeTemplateQuery();
        TypeTemplateQuery.Criteria criteria = typeTemplateQuery.createCriteria();
        if(typeTemplate.getName()!=null){
            criteria.andNameLike("%"+typeTemplate.getName()+"%");
        }
        if(typeTemplate.getStatus().length()>0&&typeTemplate.getStatus()!=null){
            criteria.andStatusEqualTo(typeTemplate.getStatus());
        }
        Page<TypeTemplate> typeTemplates = (Page<TypeTemplate>) typeTemplateDao.selectByExample(typeTemplateQuery);

        return new PageResult(typeTemplates.getTotal(),typeTemplates.getResult());
    }

    @Override
    public void delete(Long[] ids) {
        TypeTemplate typeTemplate = new TypeTemplate();
        //TODO:修改状态
        typeTemplate.setStatus("1");
        if(ids!=null&&ids.length>0){
            for (Long id : ids) {
                typeTemplate.setId(id);
                typeTemplateDao.updateByPrimaryKeySelective(typeTemplate);

            }
        }
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        TypeTemplate typeTemplate=new TypeTemplate();
        typeTemplate.setStatus(status);
        if(ids!=null&&ids.length>0){
            for (Long id : ids) {
                typeTemplate.setId(id);
                typeTemplateDao.updateByPrimaryKeySelective(typeTemplate);
            }
        }
    }


}
