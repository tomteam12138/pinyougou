package cn.itcast.core.service;

import java.util.Map; /**
 * Created by wang on 2019/4/20.
 */
public interface ItemSearchService {
    Map<String,Object> search(Map<String, String> searchMap);
}
