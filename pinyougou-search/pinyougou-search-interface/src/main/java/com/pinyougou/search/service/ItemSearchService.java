package com.pinyougou.search.service;

import java.util.Map;

public interface ItemSearchService {
    /**
     * 根据搜索关键字到solr搜索对应的数据并返回
     * @param searchMap 搜索对象
     * @return 搜索结果
     */
    Map<String,Object> search(Map<String, Object> searchMap);
}
