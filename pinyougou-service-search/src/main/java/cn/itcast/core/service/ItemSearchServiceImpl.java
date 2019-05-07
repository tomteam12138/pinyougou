package cn.itcast.core.service;

import cn.itcast.core.pojo.item.Item;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.*;

/**
 * Created by wang on 2019/4/20.
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Map<String, Object> search(Map<String, String> searchMap) {
        Map<String,Object> resultMap = new HashMap<>();
        //1 封装高亮查询数据
        resultMap.putAll(searchHigtLight(searchMap));
        //2 封装商品分类数据
        resultMap.put("categoryList",searchCategory(searchMap));
        //3 封装商品品牌
        resultMap.put("brandList",searchBrandList(searchMap));
        //4 封装商品规格
        resultMap.put("specList",searchSpecList(searchMap));
        return resultMap;

    }
    //4 封装商品规格
    public List<Map> searchSpecList(Map<String, String> searchMap){
        List<String> categoryList = searchCategory(searchMap);
        String category = categoryList.get(0);
        Object typeId = redisTemplate.boundHashOps("itemCatMap").get(category);
        List<Map> specList = (List<Map>) redisTemplate.boundHashOps("specList").get(typeId.toString());
        return specList;
    }

    //3 封装商品品牌
    public List<Map> searchBrandList(Map<String, String> searchMap){
        //searchMap={'keywords':'','category':'','brand':'','spec':{},'price':'','pageNo':1,'pageSize':40,'sort':'','sortField':''};
        List<String> categoryList = searchCategory(searchMap);
        String category = categoryList.get(0);
        Object typeId = redisTemplate.boundHashOps("itemCatMap").get(category);
        List<Map> brandList = (List<Map>) redisTemplate.boundHashOps("brandList").get(typeId.toString());
        return brandList;
    }
    //查询商品分类
    public List<String> searchCategory(Map<String, String> searchMap) {
        //searchMap={'keywords':'','category':'','brand':'','spec':{},'price':'','pageNo':1,'pageSize':40,'sort':'','sortField':''};
        Map<String,Object> resultMap = new HashMap<>();
        String keywords1 = searchMap.get("keywords");
        String keywords = keywords1.replaceAll(" ", "");
        List<String> categoryList = new ArrayList<>();
        Criteria criteria = new Criteria("item_keywords");
        criteria.is(keywords);
        SimpleQuery query = new SimpleQuery(criteria);
        GroupOptions groupOptions = new GroupOptions();
        groupOptions.addGroupByField("item_category");
        query.setGroupOptions(groupOptions);
        GroupPage<Item> items = solrTemplate.queryForGroupPage(query, Item.class);
        GroupResult<Item> item_category = items.getGroupResult("item_category");
        List<GroupEntry<Item>> content = item_category.getGroupEntries().getContent();
        for (GroupEntry<Item> itemGroupEntry : content) {
            String category = itemGroupEntry.getGroupValue();
            categoryList.add(category);
        }
        return categoryList;
    }
    //高亮查询
    public Map<String, Object> searchHigtLight(Map<String, String> searchMap) {
        Map<String,Object> resultMap = new HashMap<>();
        String keywords1 = searchMap.get("keywords");
        String keywords = keywords1.replaceAll(" ", "");
        Criteria criteria = new Criteria("item_keywords");
        criteria.is(keywords);
        //searchMap={'keywords':'','category':'','brand':'','spec':{},'price':'','pageNo':1,'pageSize':40,'sort':'','sortField':''};
        SimpleHighlightQuery query = new SimpleHighlightQuery(criteria);

        //商品分类过滤
        SimpleFilterQuery simpleFilterQuery = new SimpleFilterQuery();
        if (searchMap.get("category")!=null && !"".equals(searchMap.get("category").trim())){
            simpleFilterQuery.addCriteria(new Criteria("item_category").is(searchMap.get("category")));
            query.addFilterQuery(simpleFilterQuery);
        }
        //商品价格过滤
        if (searchMap.get("price")!=null && !"".equals(searchMap.get("price").trim())){
            //SimpleFilterQuery simpleFilterQuery = new SimpleFilterQuery();
            String price = searchMap.get("price");
            String[] split = price.split("-");
            if ("*".equals(split[1])){
                simpleFilterQuery.addCriteria(new Criteria("item_price").greaterThanEqual(split[0]));

            }else {
                simpleFilterQuery.addCriteria(new Criteria("item_price").between(split[0],split[1]));
            }
            query.addFilterQuery(simpleFilterQuery);
        }
        //商品品牌过滤
        if (searchMap.get("brand")!=null && !"".equals(searchMap.get("brand").trim())){
            //SimpleFilterQuery simpleFilterQuery = new SimpleFilterQuery();
            simpleFilterQuery.addCriteria(new Criteria("item_brand").is(searchMap.get("brand")));
            query.addFilterQuery(simpleFilterQuery);
        }
        //商品规格过滤
        if (searchMap.get("spec")!=null && !"".equals(searchMap.get("spec").trim())){
            //SimpleFilterQuery simpleFilterQuery = new SimpleFilterQuery();
            String spec = searchMap.get("spec");
            Map<String,String> spec1 = (Map<String, String>) JSON.parse(spec);
            Set<Map.Entry<String, String>> entries = spec1.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                simpleFilterQuery.addCriteria(new Criteria("item_spec_"+entry.getKey()).is(entry.getValue()));

            }
            query.addFilterQuery(simpleFilterQuery);
        }
        //排序
        if (searchMap.get("sortField")!=null && !"".equals(searchMap.get("sortField"))){
            if ("updatetime".equals(searchMap.get("sortField"))){
                Sort sort = new Sort(Sort.Direction.DESC,"item_"+searchMap.get("sortField"));
                query.addSort(sort);
            }
            if ("price".equals(searchMap.get("sortField"))){
                if ("DESC".equals(searchMap.get("sort"))){
                    Sort sort = new Sort(Sort.Direction.DESC,"item_"+searchMap.get("sortField"));
                    query.addSort(sort);
                }
                if ("ASC".equals(searchMap.get("sort"))){
                    Sort sort = new Sort(Sort.Direction.ASC,"item_"+searchMap.get("sortField"));
                    query.addSort(sort);
                }
            }
        }

        Integer pageNo = Integer.parseInt(searchMap.get("pageNo"));
        Integer pageSize = Integer.parseInt(searchMap.get("pageSize"));
        query.setOffset((pageNo-1)*pageSize);
        query.setRows(pageSize);
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title");
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);
        HighlightPage<Item> items = solrTemplate.queryForHighlightPage(query, Item.class);
        List<HighlightEntry<Item>> highlighted = items.getHighlighted();
       // String s = highlighted.get(0).getHighlights().get(0).getSnipplets().get(0);
        if (highlighted!=null && highlighted.size()>0){
            for (HighlightEntry<Item> highlightEntry : highlighted) {
                String s= "";
                List<HighlightEntry.Highlight> highlights = highlightEntry.getHighlights();
                if (highlights!=null && highlights.size()>0){
                    s = highlights.get(0).getSnipplets().get(0);
                }
                highlightEntry.getEntity().setTitle(s);
            }
        }
        List<Item> content = items.getContent();
        resultMap.put("rows",content);
        resultMap.put("totalPages", items.getTotalPages());
        resultMap.put("total",items.getTotalElements());
        return resultMap;
    }
    //普通查询
    public Map<String, Object> searchKeyWords(Map<String, String> searchMap) {
        Map<String,Object> resultMap = new HashMap<>();
        String keywords = searchMap.get("keywords");
        Criteria criteria = new Criteria("item_keywords");
        criteria.is(keywords);
        //	$scope.searchMap={'keywords':'','category':'','brand':'','spec':{},'price':'','pageNo':1,'pageSize':40,'sort':'','sortField':''};
        SimpleQuery simpleQuery = new SimpleQuery(criteria);
        Integer pageNo = Integer.parseInt(searchMap.get("pageNo"));
        Integer pageSize = Integer.parseInt(searchMap.get("pageSize"));
        simpleQuery.setOffset((pageNo-1)*pageSize);
        simpleQuery.setRows(pageSize);
        ScoredPage<Item> itemScoredPage = solrTemplate.queryForPage(simpleQuery, Item.class);
        List<Item> content = itemScoredPage.getContent();
        resultMap.put("rows",content);
        resultMap.put("totalPages", itemScoredPage.getTotalPages());
        resultMap.put("total",itemScoredPage.getTotalElements());
        return resultMap;
    }
}
