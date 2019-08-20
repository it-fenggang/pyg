package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.sellergoods.service.GoodsService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.Goods;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Transactional
@Service(interfaceClass = GoodsService.class)
public class GoodsServiceImpl extends BaseServiceImpl<TbGoods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SellerMapper sellerMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult search(Integer page, Integer rows, TbGoods goods) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbGoods.class);
        Example.Criteria criteria = example.createCriteria();
        //过滤掉删除状态的数据
        criteria.andNotEqualTo("isDelete", "1");

        //根据商家查询
        if(!StringUtils.isEmpty(goods.getSellerId())){
            criteria.andEqualTo("sellerId", goods.getSellerId());
            //System.out.println("============="+goods.getSellerId());
        }
        //根据审核状态查询
        if(!StringUtils.isEmpty(goods.getAuditStatus())){
            criteria.andEqualTo("auditStatus", goods.getAuditStatus());
        }
        //根据商品名称模糊查询
        if(!StringUtils.isEmpty(goods.getGoodsName())){
            criteria.andLike("goodsName", "%" + goods.getGoodsName() + "%");
        }

        List<TbGoods> list = goodsMapper.selectByExample(example);
        PageInfo<TbGoods> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 保存商品基本信息,描述信息,sku
     * @param goods
     */
    @Override
    public void addGoods(Goods goods){
        goods.getGoods().setIsDelete("0");
        //保存基本信息
        add(goods.getGoods());
        //保存描述信息
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
        goodsDescMapper.insertSelective(goods.getGoodsDesc());
        //保存sku列表
        saveItemList(goods);
    }
    //保存sku
    private void saveItemList(Goods goods) {
        if("1".equals(goods.getGoods().getIsEnableSpec())){
            List<TbItem>list=goods.getItemList();
            if(list!=null&&list.size()>0){
                for (TbItem item : list) {
                    //sku的标题=spu的商品名称+所有规格选项
                    String title=goods.getGoods().getGoodsName();
                    Map<String,String> map = JSONObject.parseObject(item.getSpec(), Map.class);
                    Set<Map.Entry<String, String>> entries = map.entrySet();
                    for (Map.Entry<String, String> entry : entries) {
                        title+=" "+entry.getValue();
                    }
                    item.setTitle(title);
                    setItem(item,goods);

                    //保存sku
                    itemMapper.insertSelective(item);
                }
            }
        }else{
            //不启用规格将spu
            TbItem item = new TbItem();
            item.setTitle(goods.getGoods().getGoodsName());
            item.setIsDefault("1");
            item.setStatus("0");//未审核
            item.setNum(9999);
            item.setPrice(goods.getGoods().getPrice());
            item.setSpec("{ }");
            setItem(item,goods);
            //保存sku
            itemMapper.insertSelective(item);
        }
    }

    /**
     * 设置商品sku信息
     * @param item
     * @param goods
     */
    private void setItem(TbItem item, Goods goods) {
        if(!StringUtils.isEmpty(goods.getGoodsDesc().getItemImages())){
            List<Map> list2 = JSONArray.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
            if(list2!=null && list2.size()>0){
                item.setImage(list2.get(0).get("url").toString());
            }
        }
        item.setSellerId(goods.getGoods().getSellerId());
        TbSeller seller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
        item.setSeller(seller.getName());

        item.setCreateTime(new Date());
        item.setUpdateTime(item.getCreateTime());

        item.setGoodsId(goods.getGoods().getId());
        item.setCategoryid(goods.getGoods().getCategory3Id());
        TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(item.getCategoryid());
        item.setCategory(itemCat.getName());

        TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
        item.setBrand(brand.getName());
    }
}
