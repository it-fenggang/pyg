package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService extends BaseService<TbBrand>{
    List<TbBrand> queryAll();

    List<TbBrand> testPage(Integer page, Integer rows);
    /**
     * 分页模糊条件查询
     * @param page
     * @param rows
     * @param tbBrand
     * @return
     */
    PageResult search(Integer page, Integer rows, TbBrand tbBrand);
    /**
     *构建select2的品牌结构下拉框
     * 数据结构[{id:'1',text:'联想'},{id:'2',text:'华为'}]
     */
    List<Map<String,String>> selectOptionList();
}
