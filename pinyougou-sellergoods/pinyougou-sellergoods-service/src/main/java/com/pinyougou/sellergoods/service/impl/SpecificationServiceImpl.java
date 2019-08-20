package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.SpecificationMapper;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 规格分页加条件
 */
@Service(interfaceClass = SpecificationService.class)
public class SpecificationServiceImpl extends BaseServiceImpl<TbSpecification> implements SpecificationService{
    //注入规格
    @Autowired
    private SpecificationMapper specificationMapper;
    //注入规格选项
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
    /**
     * 初始化分页查询
     * @param page
     * @param rows
     * @param tbSpecification
     * @return
     */
    @Override
    public PageResult findPage(Integer page, Integer rows,TbSpecification tbSpecification) {
        //设置分页
        PageHelper.startPage(page, rows);
        //创建查询对象
        Example example = new Example(TbSpecification.class);
        //创建条件对象
        Example.Criteria criteria = example.createCriteria();
        //判空
        if(!StringUtils.isEmpty(tbSpecification.getSpecName())){
            criteria.andLike("specName","%"+tbSpecification.getSpecName()+"%");
        }
        List<TbSpecification> list = specificationMapper.selectByExample(example);
        PageInfo<TbSpecification> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }
    /**
     * 新增规格及规格选项
     * @param specification
     */
    @Override
    public void add(Specification specification) {
        //新增规格
        //specificationMapper.insert(specification.getTbSpecification());
        add(specification.getSpecification());
        //System.out.println("================="+specification.getSpecification().getSpecName());
        //新增规格选项
        List<TbSpecificationOption> list = specification.getSpecificationOptionList();
        //System.out.println("============"+list);
        if(list!=null&&list.size()>0) {
            for (TbSpecificationOption so : list) {
                //空指针异常
                //System.out.println("============"+specification.getSpecification().getId());
                so.setSpecId(specification.getSpecification().getId());
                specificationOptionMapper.insert(so);
            }
        }
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    public void deleteByIds(Serializable[] ids) {
        //删除规格选项
        for (Serializable id : ids) {
            Example example = new Example(TbSpecificationOption.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("specId",id);
            specificationOptionMapper.deleteByExample(example);
        }
        //删除规格
        super.deleteByIds(ids);
    }

    @Override
    public List<Map<String, String>> selectOptionList() {
        return specificationMapper.selectOptionList();
    }

    /**
     * 查询规格及规格选项
     * @param id
     * @return
     */
    @Override
    public Specification findOne(Long id) {
        //根据id查询规格
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        //根据specId查询规格选项
        Example example = new Example(TbSpecificationOption.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("specId",tbSpecification.getId());
        List<TbSpecificationOption> list = specificationOptionMapper.selectByExample(example);
        Specification specification = new Specification();
        specification.setSpecification(tbSpecification);
        specification.setSpecificationOptionList(list);
        return specification;
    }
    /**
     * 修改
     */
    @Override
    public void update(Specification specification) {
        //更新规格
        update(specification.getSpecification());
        //删除原有规格的规格选项
        TbSpecificationOption param = new TbSpecificationOption();
        param.setSpecId(specification.getSpecification().getId());
        specificationOptionMapper.delete(param);
        //更新规格选项
        if(specification.getSpecificationOptionList()!=null&&specification.getSpecificationOptionList().size()>0){
            for (TbSpecificationOption so : specification.getSpecificationOptionList()) {
                so.setSpecId(specification.getSpecification().getId());
                specificationOptionMapper.insertSelective(so);
            }
        }
    }
}
