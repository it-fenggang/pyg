package com.pinyougou.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.content.service.ContentService;
import com.pinyougou.mapper.ContentMapper;
import com.pinyougou.pojo.TbContent;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service(interfaceClass = ContentService.class)
public class ContentServiceImpl extends BaseServiceImpl<TbContent> implements ContentService {

    //广告内容列表在redis中的key的名称
    private static final String REDIS_CONTENT_KEY = "CONTENT_LIST";
    @Autowired
    private ContentMapper contentMapper;

    @Override
    public PageResult search(Integer page, Integer rows, TbContent content) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbContent.class);
        Example.Criteria criteria = example.createCriteria();
        /*if(!StringUtils.isEmpty(content.get***())){
            criteria.andLike("***", "%" + content.get***() + "%");
        }*/
        List<TbContent> list = contentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public List<TbContent> findContentListByCategoryId(Long categoryId) {
        /*TbContent content = new TbContent();
        content.setCategoryId(categoryId);
        List<TbContent> list = contentMapper.select(content);*/
        /**
         * 根据内容分类id查询该分类下所有有效的内容并且排序字段降序排序
         */
        Example example = new Example(TbContent.class);
        example.createCriteria()
                .andEqualTo("categoryId",categoryId)//内容分类id
                .andEqualTo("status","1");//有效
        //根据排序字段降序排序
        example.orderBy("sortOrder").desc();
        List<TbContent> list = contentMapper.selectByExample(example);
        return list;
    }
}
