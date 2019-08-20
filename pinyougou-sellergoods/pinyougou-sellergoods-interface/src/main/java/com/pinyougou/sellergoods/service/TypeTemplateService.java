package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.service.BaseService;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService extends BaseService<TbTypeTemplate>{
    /**
     * 根据分类模版id查询商品规格及其选项集合
     * @param
     * @return
     */
    List<Map> findSpecList(Long id);
}
