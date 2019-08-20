package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemSearchServiceImpl implements ItemSearchService{
    @Autowired
    private SolrTemplate solrTemplate;
    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {
        Map<String, Object> resultMap=new HashMap<>();
        //创建查询对象
        SimpleHighlightQuery query = new SimpleHighlightQuery();

        //创建查询条件,is方法会对查询的句子进行分词
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        //设置高亮
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title");
        //高亮起始标签
        highlightOptions.setSimplePrefix("<font style='color:red'>");
        //高亮结束标签
        highlightOptions.setSimplePostfix("</font>");
        //添加高亮配置
        query.setHighlightOptions(highlightOptions);
        //搜索
        HighlightPage<TbItem> highlightPage = solrTemplate.queryForHighlightPage(query, TbItem.class);
        //处理高亮标题
        //获取高亮标题内容
        List<HighlightEntry<TbItem>> highlighted = highlightPage.getHighlighted();
        if (highlighted != null && highlighted.size() > 0) {
            for (HighlightEntry<TbItem> entry : highlighted) {
                List<HighlightEntry.Highlight> highlights = entry.getHighlights();
                if (highlights != null && highlights.size() > 0) {
                    //获取高亮标题；第一个get(0)获取高亮域，第二个get(0)是因为每个域可以对应有多个高亮标题
                    String title = highlights.get(0).getSnipplets().get(0);
                    entry.getEntity().setTitle(title);
                }
            }
        }
        resultMap.put("rows",highlightPage.getContent());
        return resultMap;
    }
}
