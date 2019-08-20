package com.pinyougou.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.pojo.TbContent;
import com.pinyougou.vo.PageResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("content")
@RestController
public class ContentController {

    @Reference
    private ContentService contentService;
    /**
     * 分页查询列表
     * @param content 查询条件
     * @param page 页号
     * @param rows 每页大小
     * @return
     */
    @PostMapping("search")
    public PageResult search(@RequestBody  TbContent content, @RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "10")Integer rows) {
        return contentService.search(page, rows, content);
    }

    /**
     * 根据categoryId查询广告列表
     * 操作TbContent表
     * @param categoryId
     * @return
     */
    @GetMapping("findContentListByCategoryId")
    public List<TbContent> findContentListByCategoryId(@RequestParam Long categoryId){
        return contentService.findContentListByCategoryId(categoryId);
    }
}
