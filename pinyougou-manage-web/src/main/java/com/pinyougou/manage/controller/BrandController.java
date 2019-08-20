package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@Controller以前写法
@RequestMapping("brand")
@RestController//组合注解；@ResponseBody @Controller；对本来的所有方法生效
public class BrandController {
    @Reference(timeout = 10000)
    private BrandService brandService;
    /**
     * 查询所有品牌列表
     */
    //@RequestMapping(value = "/findAll", method = RequestMethod.GET)
    //@ResponseBody
    @GetMapping("findAll")
    public List<TbBrand> queryAll(){

        return brandService.queryAll();
    }
    /**
     * 测试
     * 根据分页页号、页大小分页查询品牌列表
     * @param page 页号
     * @param rows 页大小
     * @return 品牌列表
     */
    @GetMapping("testPage")
    public List<TbBrand> testPage(@RequestParam(value="page", defaultValue = "1")Integer page,
                                  @RequestParam(value="rows", defaultValue = "5")Integer rows){
        //System.out.println("========分页"+page+","+rows);
        //List<TbBrand> list = brandService.testPage(page, rows);

        PageResult result = brandService.findPage(page, rows);
        List<TbBrand> list= (List<TbBrand>) result.getRows();
        return list;
    }
    /**
     * 根据分页页号、页大小分页查询品牌列表
     * @param page 页号
     * @param rows 页大小
     * @return 品牌列表
     */
    @GetMapping("findPage")
    public PageResult findPage(@RequestParam(value="page", defaultValue = "1")Integer page,
                               @RequestParam(value="rows", defaultValue = "5")Integer rows){
        return brandService.findPage(page, rows);
    }
    /**
     * 模糊查询分页显示
     */
    @PostMapping("search")
    public PageResult search(@RequestParam(value="page", defaultValue = "1")Integer page,
                             @RequestParam(value="rows", defaultValue = "5")Integer rows,
                             @RequestBody TbBrand tbBrand){
        //System.out.println(tbBrand.getName()+"====================="+tbBrand.getFirstChar());
        return brandService.search(page,rows,tbBrand);
    }
    /**
     * 新增品牌
     */
    @PostMapping("add")
    public Result add(@RequestBody TbBrand tbBrand){
        try {
            brandService.add(tbBrand);
            return Result.ok("新增成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.error("新增失败");
    }
    /**
     * 修改品牌
     */
    @PostMapping("update")
    public Result update(@RequestBody TbBrand tbBrand){
        try {
            brandService.update(tbBrand);
            return Result.ok("修改成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.error("修改失败");
    }

    /**
     * 查询一条数据
     * @param id
     * @return
     */
    @GetMapping("findOne")
    public TbBrand findOne(@RequestParam(value ="id") Long id){
        TbBrand tbBrand = brandService.findOne(id);
        return tbBrand;
    }
    /**
     * 批量删除
     */
    @GetMapping("deleteByIds")
    public Result deleteByIds(@RequestParam(value = "ids") Long[] ids){
        try{
            brandService.deleteByIds(ids);
            return Result.ok("删除成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.error("删除失败");
    }
    /**
     *构建select2的品牌结构下拉框
     * 数据结构[{id:'1',text:'联想'},{id:'2',text:'华为'}]
     */
    @GetMapping("selectOptionList")
    public List<Map<String,String>> selectOptionList(){
        return brandService.selectOptionList();
    }
}
