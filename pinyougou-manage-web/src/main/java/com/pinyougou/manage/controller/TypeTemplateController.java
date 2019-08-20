package com.pinyougou.manage.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("typeTemplate")
public class TypeTemplateController {
    /**
     * 商品类型模板管理
     */
    @Reference
    private TypeTemplateService typeTemplateService;
    @RequestMapping("findAll")
    public List<TbTypeTemplate> findAll() {
        return typeTemplateService.findAll();
    }
    /**
     * 初始化分页显示列表
     */
    @RequestMapping("findPage")
    public PageResult findPage(@RequestParam(value="page", defaultValue = "1")Integer page,
                               @RequestParam(value="rows", defaultValue = "5")Integer rows){
     return typeTemplateService.findPage(page,rows);
    }
    @PostMapping("search")
    public PageResult search(@RequestParam(value="page", defaultValue = "1")Integer page,
                             @RequestParam(value="rows", defaultValue = "5")Integer rows,
                             @RequestBody TbTypeTemplate tbTypeTemplate){
            return typeTemplateService.findPage(page,rows,tbTypeTemplate);
    }
    /**
     * 添加商品类型模版
     */
    @PostMapping("add")
    public Result add(@RequestBody TbTypeTemplate typeTemplate) {
        try {
            typeTemplateService.add(typeTemplate);
            return Result.ok("增加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("增加失败");
    }

    /**
     * 修改商品类型模版
     * @param typeTemplate
     * @return
     */
    @PostMapping("update")
    public Result update(@RequestBody TbTypeTemplate typeTemplate) {
        try {
            typeTemplateService.update(typeTemplate);
            return Result.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("修改失败");
    }
    /**
     * 修改数据回显,findOne,  delete
     */
    @GetMapping("findOne")
    public TbTypeTemplate findOne(@RequestParam Long id){
        return typeTemplateService.findOne(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @GetMapping("delete")
    public Result delete(@RequestParam(value = "ids") Long[] ids){
        try{
            typeTemplateService.deleteByIds(ids);
            return Result.ok("删除成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.error("删除失败");
    }
}
