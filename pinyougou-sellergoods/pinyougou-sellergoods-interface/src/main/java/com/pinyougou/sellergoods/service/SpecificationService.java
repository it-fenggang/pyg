package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Specification;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface SpecificationService extends BaseService<TbSpecification>{
    /**
     * 初始化分页+模糊查询
     * @param page
     * @param rows
     * @param tbSpecification
     * @return
     */
    @Override
    PageResult findPage(Integer page, Integer rows, TbSpecification tbSpecification);

    /**
     * 新增规格及规格选项
     * @param specification
     */
    void add(Specification specification);

    @Override
    void deleteByIds(Serializable[] ids);

    List<Map<String,String>> selectOptionList();


    Specification findOne(Long id);

    void update(Specification specification);
}
