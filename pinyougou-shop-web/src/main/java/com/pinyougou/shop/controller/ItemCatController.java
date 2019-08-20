package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.sellergoods.service.ItemCatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("itemCat")
@RestController
public class ItemCatController {
    @Reference
    private ItemCatService itemCatService;
    /**
     * 实现商品分类下拉框
     * @return
     */
    @GetMapping("findByParentId")
    public List<TbItemCat> findByParentId(@RequestParam Long parentId){
        //System.out.println("========================="+parentId);
        TbItemCat tbItemCat=new TbItemCat();
        tbItemCat.setParentId(parentId);
        return itemCatService.findByWhere(tbItemCat);
    }
    @RequestMapping("findAll")
    public List<TbItemCat> findAll() {
        return itemCatService.findAll();
    }
    /**
     *
     */
    @GetMapping("findOne")
    public TbItemCat findOne(@RequestParam Long id){
        return itemCatService.findOne(id);
    }
}
