package com.pinyougou.manage.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import com.pinyougou.vo.Specification;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RequestMapping("specification")
@RestController
public class SpecificationController {
    /**
     * 规格管理
     */
    @Reference
    private SpecificationService specificationService;
    //查询所有规格数据
    @GetMapping("findAll")
    public List<TbSpecification> findAll(){
        return specificationService.findAll();
    }
    /**
     * 初始化加载分页数据
     */
    @GetMapping("findPage")
    public PageResult findPage(@RequestParam(value="page", defaultValue = "1")Integer page,
                               @RequestParam(value="rows", defaultValue = "5")Integer rows){
        PageResult pageResult = specificationService.findPage(page, rows);
        return pageResult;
    }
    /**
     * 加载分页,条件查询
     */
    @PostMapping("search")
    public PageResult search(@RequestParam(value="page", defaultValue = "1")Integer page,
                             @RequestParam(value="rows", defaultValue = "5")Integer rows,
                             @RequestBody TbSpecification tbSpecification){
        return specificationService.findPage(page,rows,tbSpecification);
    }
    /**
     * 新增规格及规格选项
     */
    @PostMapping("add")
    public Result add(@RequestBody Specification specification){
        try {
            specificationService.add(specification);
            return Result.ok("新增成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.error("新增失败");
    }
    /**
     * 实现批量删除
     */
    @PostMapping("delete")
    public Result delete(@RequestBody Long[] ids){
        try {
            specificationService.deleteByIds(ids);
            return Result.ok("删除成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.error("删除失败");
    }
    @GetMapping("selectOptionList")
    public List<Map<String,String>>selectOptionList(){
        return specificationService.selectOptionList();
    }
    /**
     * 根据id查询一条记录
     */
    @GetMapping("findOne")
    public Specification findOne(@RequestParam(value = "id") Long id){
        return specificationService.findOne(id);
    }
    /**
     * 修改
     */
    @PostMapping("update")
    public Result update(@RequestBody Specification specification){
        try{
            specificationService.update(specification);
            return Result.ok("修改成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.ok("修改失败");
    }
}
