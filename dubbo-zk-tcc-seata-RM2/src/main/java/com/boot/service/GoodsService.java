package com.boot.service;

import com.boot.pojo.Goods;

public interface GoodsService {

    Integer insGoods(Goods goods);

    Integer updGoodsById(Goods goods);

    Integer delGoodsByIdExist(Goods goods);

}
