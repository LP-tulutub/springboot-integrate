package com.boot.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.boot.mapper.GoodsMapper;
import com.boot.pojo.Goods;
import com.boot.service.GoodsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@Service(interfaceClass = GoodsService.class)
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Transactional
    @Override
    public Integer insGoods(Goods goods) {
        return this.goodsMapper.insertSelective(goods);
    }
}
