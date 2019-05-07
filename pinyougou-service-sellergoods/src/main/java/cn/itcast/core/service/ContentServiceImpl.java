package cn.itcast.core.service;

import cn.itcast.core.dao.ad.ContentDao;
import cn.itcast.core.pojo.ad.Content;
import cn.itcast.core.pojo.ad.ContentQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private ContentDao contentDao;
	@Autowired
    private RedisTemplate redisTemplate;

	@Override
	public List<Content> findAll() {
		List<Content> list = contentDao.selectByExample(null);
		return list;
	}

	@Override
	public PageResult findPage(Content content, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<Content> page = (Page<Content>)contentDao.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void add(Content content) {
	    redisTemplate.boundHashOps("content").delete(content.getCategoryId());
	    content.setStatus("1");
	    contentDao.insertSelective(content);

	}

	@Override
	public void edit(Content content) {
        Content content1 = contentDao.selectByPrimaryKey(content.getId());
        if (content.getCategoryId()!=content1.getCategoryId()){
            redisTemplate.boundHashOps("content").delete(content1.getCategoryId());
        }
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());
        contentDao.updateByPrimaryKeySelective(content);
	}

	@Override
	public Content findOne(Long id) {
		Content content = contentDao.selectByPrimaryKey(id);
		return content;
	}

	@Override
	public void delAll(Long[] ids) {
		if(ids != null){
			for(Long id : ids){
                Content content = contentDao.selectByPrimaryKey(id);
                redisTemplate.boundHashOps("content").delete(content.getCategoryId());
                contentDao.deleteByPrimaryKey(id);
			}
		}
	}

    @Override
    public List<Content> findByCategoryId(Long categoryId) {
        List<Content> contentList = (List<Content>) redisTemplate.boundHashOps("content").get(categoryId);
        if (contentList==null || contentList.size()==0){
            ContentQuery contentQuery = new ContentQuery();
            ContentQuery.Criteria criteria = contentQuery.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);
			criteria.andStatusEqualTo("1");
            contentQuery.setOrderByClause("sort_order desc");
            contentList = contentDao.selectByExample(contentQuery);
            redisTemplate.boundHashOps("content").put(categoryId,contentList);
            redisTemplate.boundHashOps("content").expire(4,TimeUnit.HOURS);
        }

		return contentList;
    }

}