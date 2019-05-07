package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationDao;

import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import cn.itcast.core.pojo.specification.SpecificationQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vo.SpecificationVo;

import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2019/4/9.
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService{
    @Autowired
    private SpecificationDao specificationDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    @Override
    public PageResult search(Integer pageNum, Integer pageSize, Specification specification) {
        PageHelper.startPage(pageNum,pageSize);
        SpecificationQuery specificationQuery = new SpecificationQuery();
        SpecificationQuery.Criteria criteria = specificationQuery.createCriteria();
        if (specification.getSpecName()!=null && !"".equals(specification.getSpecName().trim())){
            criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
        }
        List<Specification> specificationList = specificationDao.selectByExample(specificationQuery);
        Page<Specification> page = (Page<Specification>) specificationList;
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(SpecificationVo vo) {
        if (vo.getSpecification().getSpecName()!=null && !"".equals(vo.getSpecification().getSpecName().trim())){
            specificationDao.insertSelective(vo.getSpecification());

            if (vo.getSpecificationOptionList()!=null && vo.getSpecificationOptionList().size()!=0){
                for (SpecificationOption specificationOption : vo.getSpecificationOptionList()) {
                    specificationOption.setSpecId(vo.getSpecification().getId());
                    specificationOptionDao.insertSelective(specificationOption);
                }
            }
        }
    }

    @Override
    public SpecificationVo findOne(Long id) {
        Specification specification = specificationDao.selectByPrimaryKey(id);
        SpecificationVo specificationVo = new SpecificationVo();
        specificationVo.setSpecification(specification);
        SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
        SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<SpecificationOption> specificationOptionList = specificationOptionDao.selectByExample(specificationOptionQuery);
        specificationVo.setSpecificationOptionList(specificationOptionList);
        return specificationVo;
    }

    @Override
    public void update(SpecificationVo vo) {
        specificationDao.deleteByPrimaryKey(vo.getSpecification().getId());
        List<SpecificationOption> specificationOptionList = vo.getSpecificationOptionList();
        for (SpecificationOption specificationOption : specificationOptionList) {
            specificationOptionDao.deleteByPrimaryKey(specificationOption.getId());
        }
        if (vo.getSpecification().getSpecName()!=null && !"".equals(vo.getSpecification().getSpecName().trim())){
            specificationDao.insertSelective(vo.getSpecification());

            if (vo.getSpecificationOptionList()!=null && vo.getSpecificationOptionList().size()!=0){
                for (SpecificationOption specificationOption : vo.getSpecificationOptionList()) {
                    specificationOption.setSpecId(vo.getSpecification().getId());
                    specificationOptionDao.insertSelective(specificationOption);
                }
            }
        }

    }

    @Override
    public void delete(Long[] ids) {
        if (ids!=null && ids.length!=0){
            for (Long id : ids) {
                specificationDao.deleteByPrimaryKey(id);
                SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
                SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
                criteria.andSpecIdEqualTo(id);
                specificationOptionDao.deleteByExample(specificationOptionQuery);
            }
        }
    }

    @Override
    public List<Map> selectOptionList() {
        return specificationDao.selectOptionList();
    }
}
