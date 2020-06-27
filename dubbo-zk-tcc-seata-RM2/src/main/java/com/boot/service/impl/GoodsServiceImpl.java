package com.boot.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.boot.mapper.GoodsMapper;
import com.boot.pojo.Goods;
import com.boot.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service(interfaceClass = GoodsService.class)
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Transactional
    @Override
    public Integer insGoods(Goods goods) {
        return this.goodsMapper.insert(goods);
    }

    @Transactional
    @Override
    public Integer updGoodsById(Goods goods) {
        return this.goodsMapper.updateById(goods);
    }

    @Transactional
    @Override
    public Integer delGoodsByIdExist(Goods goods) {
        Goods exist = this.goodsMapper.selectById(goods.getId());
        if (exist == null) return 1;
        return this.goodsMapper.deleteById(goods.getId());
    }
}
