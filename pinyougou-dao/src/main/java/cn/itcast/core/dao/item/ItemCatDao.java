package cn.itcast.core.dao.item;

import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatNew;
import cn.itcast.core.pojo.item.ItemCatQuery;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ItemCatDao {
    int countByExample(ItemCatQuery example);

    int deleteByExample(ItemCatQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(ItemCat record);

    int insertSelective(ItemCatNew record);

    List<ItemCat> selectByExample(ItemCatQuery example);

    ItemCatNew selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ItemCat record, @Param("example") ItemCatQuery example);

    int updateByExample(@Param("record") ItemCat record, @Param("example") ItemCatQuery example);

    int updateByPrimaryKeySelective(ItemCatNew record);

    int updateByPrimaryKey(ItemCat record);

    List<Map> findItemList(Long parentId);

    List<ItemCatNew> findByParentId(Long parentId);

    ItemCatNew findOne(Long id);

    List<ItemCatNew> findAll();
}