package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("typeTemplate")
@RestController
public class TypeTemplateController {
    @Reference
    private TypeTemplateService typeTemplateService;
    /**
     * 根据id查询模版
     * @return
     */
    @GetMapping("findOne")
    public TbTypeTemplate findOne(@RequestParam Long id){
        return typeTemplateService.findOne(id);
    }

    /**
     * 根据分类模版id查询商品规格及其选项集合
     * @param id
     * @return
     */
    @GetMapping("findSpecList")
    public List<Map> findSpecList(Long id){
        return typeTemplateService.findSpecList(id);
    }
}
