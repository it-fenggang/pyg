package com.pinyougou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
    //在spring 4.x版本之后引入泛型依赖注入
    @Autowired
    private Mapper<T> mapper;
    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    @Override
    public T findOne(Serializable id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(T t) {
        mapper.insert(t);
    }

    @Override
    public void update(T t) {
        //根据主键选择性更新
        //如果对象中的属性的值为null的话则不会在update语句中出现
        //update tb_brand set name=? where id=?
        mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void deleteByIds(Serializable[] ids) {
        if (ids != null && ids.length > 0) {
            for (Serializable id : ids) {
                mapper.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public List<T> findByWhere(T t) {
        return mapper.select(t);
    }

    @Override
    public PageResult findPage(Integer page, Integer rows) {
        //设置分页
        PageHelper.startPage(page,rows);
        //查询.返回page
        List<T> list = mapper.selectAll();
        //转换分页信息对象
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }
    @Override
    public PageResult findPage(Integer page, Integer rows, T t) {
        //设置分页
        PageHelper.startPage(page,rows);
        //查询.返回page
        List<T> list = mapper.select(t);
        //转换分页信息对象
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }
}
