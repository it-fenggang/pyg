package com.pinyougou.sellergoods.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;
import java.util.Map;

//来自阿里巴巴
@Service(interfaceClass = BrandService.class)
public class BrandServiceImpl extends BaseServiceImpl<TbBrand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<TbBrand> queryAll() {
        return brandMapper.queryAll();
    }

    @Override
    public List<TbBrand> testPage(Integer page, Integer rows) {
        //设置分页；参数1：页号，参数2：页大小
        //只针对紧接着执行的查询语句生效
        PageHelper.startPage(page, rows);
        List<TbBrand> list = brandMapper.selectAll();
        return list;
    }
    /**
     * 分页模糊条件查询
     * @param page
     * @param rows
     * @param tbBrand
     * @return
     */
    @Override
    public PageResult search(Integer page, Integer rows, TbBrand tbBrand) {
        //设置分页
        PageHelper.startPage(page,rows);
        //创建查询条件对象
        Example example = new Example(TbBrand.class);
        //创建查询对象
        Example.Criteria criteria = example.createCriteria();
        //判断如果不为空
        if(!StringUtils.isEmpty(tbBrand.getName())){
            criteria.andLike("name","%"+tbBrand.getName()+"%");
        }
        if(!StringUtils.isEmpty(tbBrand.getFirstChar())){
            criteria.andEqualTo("firstChar",tbBrand.getFirstChar());
        }
        List<TbBrand> list = brandMapper.selectByExample(example);
        PageInfo<TbBrand> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }
    /**
     *构建select2的品牌结构下拉框
     * 数据结构[{id:'1',text:'联想'},{id:'2',text:'华为'}]
     */
    @Override
    public List<Map<String, String>> selectOptionList() {
        return brandMapper.selectOptionList();
    }

}
