package cn.itcast.core.service;

import cn.itcast.core.pojo.template.TypeTemplate;
import entity.PageResult;

public interface TypeTemplateCheckService {
    PageResult search(Integer page, Integer rows, TypeTemplate typeTemplate);

    void delete(Long[] ids);

    void updateStatus(Long[] ids, String status);
}
