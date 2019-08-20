package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import com.pinyougou.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = TypeTemplateService.class)
public class TypeTemplateServiceImpl extends BaseServiceImpl<TbTypeTemplate> implements TypeTemplateService{
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
    @Override
    public List<Map> findSpecList(Long id) {
        List<Map>list=null;
        //根据分类模版id查询该分类模版的那些规格
        TbTypeTemplate tbTypeTemplate=findOne(id);
        //转换上述的规格json字符串为java集合对象
        list = JSONArray.parseArray(tbTypeTemplate.getSpecIds(),Map.class);
        //遍历每个规格,查询该规格所有规格选项
        for (Map map : list) {
            TbSpecificationOption param = new TbSpecificationOption();
            param.setSpecId(Long.parseLong(map.get("id").toString()));
            List<TbSpecificationOption> options = specificationOptionMapper.select(param);
            map.put("options",options);
        }
        return list;
    }
}
