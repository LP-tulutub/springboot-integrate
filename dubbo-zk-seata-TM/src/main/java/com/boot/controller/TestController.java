package com.boot.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.boot.pojo.Admin;
import com.boot.pojo.Goods;
import com.boot.pojo.OrderInfo;
import com.boot.service.AdminService;
import com.boot.service.GoodsService;
import com.boot.service.OrderInfoService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;

@Controller
public class TestController {

    @Autowired
    private AdminService adminServiceImpl;
    @Reference
    private OrderInfoService orderInfoServiceImpl;
    @Reference
    private GoodsService goodsServiceImpl;

    @RequestMapping("/test01")
    @ResponseBody
    public String test01(){
        Admin admin = new Admin();
        admin.setId(5555501);
        admin.setName("TM");
        admin.setPassword("d3b1294a61a07da9b49b6e22b2cbd7f9");

        Integer val = adminServiceImpl.insAdmin(admin);

        if (val == 1) return "SUCCESS";
        else return "ERROR";
    }

    @RequestMapping("/test02")
    @ResponseBody
    public String test02(){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(5555502L);
        orderInfo.setUserId(5555501L);
        orderInfo.setGoodsId(1L);
        orderInfo.setGoodsName("test02");
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(new BigDecimal(0.01));
        orderInfo.setOrderChannel((byte) 0);
        orderInfo.setStatus((byte) 0);
        orderInfo.setCreateDate(new Date());

        Integer val = orderInfoServiceImpl.insOrderInfo(orderInfo);

        if (val == 1) return "SUCCESS";
        else return "ERROR";
    }

    @RequestMapping("/test03")
    @ResponseBody
    public String test03(){
        Admin admin = new Admin();
        admin.setId(5555501);
        admin.setName("TM");
        admin.setPassword("d3b1294a61a07da9b49b6e22b2cbd7f9");
        Integer val1 = adminServiceImpl.insAdmin(admin);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(5555502L);
        orderInfo.setUserId(5555501L);
        orderInfo.setGoodsId(1L);
        orderInfo.setGoodsName("test02");
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(new BigDecimal(0.01));
        orderInfo.setOrderChannel((byte) 0);
        orderInfo.setStatus((byte) 0);
        orderInfo.setCreateDate(new Date());
        Integer val2 = orderInfoServiceImpl.insOrderInfo(orderInfo);

        Goods goods = new Goods();
        goods.setId(5555503L);
        goods.setGoodsName("test03");
        goods.setGoodsTitle("title");
        goods.setGoodsImg("null");
        goods.setGoodsDetail("detail");
        goods.setGoodsPrice(new BigDecimal(1000));
        goods.setGoodsStock(1000);
        Integer val3 = goodsServiceImpl.insGoods(goods);

        if (val1+val2+val3 == 3) return "SUCCESS";
        else return "ERROR";
    }

    /**
     * 全部正常插入
     * @return
     */
    @GlobalTransactional(name = "test04", timeoutMills = 60000, rollbackFor = Exception.class)
    @RequestMapping("/test04")
    @ResponseBody
    public String test04(){
        Admin admin = new Admin();
        admin.setId(5555501);
        admin.setName("TM");
        admin.setPassword("d3b1294a61a07da9b49b6e22b2cbd7f9");
        Integer val1 = adminServiceImpl.insAdmin(admin);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(5555502L);
        orderInfo.setUserId(5555501L);
        orderInfo.setGoodsId(1L);
        orderInfo.setGoodsName("test02");
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(new BigDecimal(0.01));
        orderInfo.setOrderChannel((byte) 0);
        orderInfo.setStatus((byte) 0);
        orderInfo.setCreateDate(new Date());
        Integer val2 = orderInfoServiceImpl.insOrderInfo(orderInfo);

        Goods goods = new Goods();
        goods.setId(5555503L);
        goods.setGoodsName("test03");
        goods.setGoodsTitle("title");
        goods.setGoodsImg("null");
        goods.setGoodsDetail("detail");
        goods.setGoodsPrice(new BigDecimal(1000));
        goods.setGoodsStock(1000);
        Integer val3 = goodsServiceImpl.insGoods(goods);

        if (val1+val2+val3 == 3) return "SUCCESS";
        else return "ERROR";
    }

    /**
     * 没有插入
     * @return
     */
    @GlobalTransactional(name = "test05", timeoutMills = 60000, rollbackFor = Exception.class)
    @RequestMapping("/test05")
    @ResponseBody
    public String test05(){
        Admin admin = new Admin();
        admin.setId(5555501);
        admin.setName("TM");
        admin.setPassword("d3b1294a61a07da9b49b6e22b2cbd7f9");
        Integer val1 = adminServiceImpl.insAdmin(admin);

        if (true) throw new RuntimeException("seata 分布式事务测试");

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(5555502L);
        orderInfo.setUserId(5555501L);
        orderInfo.setGoodsId(1L);
        orderInfo.setGoodsName("test02");
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(new BigDecimal(0.01));
        orderInfo.setOrderChannel((byte) 0);
        orderInfo.setStatus((byte) 0);
        orderInfo.setCreateDate(new Date());
        Integer val2 = orderInfoServiceImpl.insOrderInfo(orderInfo);

        Goods goods = new Goods();
        goods.setId(5555503L);
        goods.setGoodsName("test03");
        goods.setGoodsTitle("title");
        goods.setGoodsImg("null");
        goods.setGoodsDetail("detail");
        goods.setGoodsPrice(new BigDecimal(1000));
        goods.setGoodsStock(1000);
        Integer val3 = goodsServiceImpl.insGoods(goods);

        if (val1+val2+val3 == 3) return "SUCCESS";
        else return "ERROR";
    }

    /**
     * 没有插入
     * @return
     */
    @GlobalTransactional(name = "test06", timeoutMills = 60000, rollbackFor = Exception.class)
    @RequestMapping("/test06")
    @ResponseBody
    public String test06(){
        Admin admin = new Admin();
        admin.setId(5555501);
        admin.setName("TM");
        admin.setPassword("d3b1294a61a07da9b49b6e22b2cbd7f9");
        Integer val1 = adminServiceImpl.insAdmin(admin);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(5555502L);
        orderInfo.setUserId(5555501L);
        orderInfo.setGoodsId(1L);
        orderInfo.setGoodsName("test02");
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(new BigDecimal(0.01));
        orderInfo.setOrderChannel((byte) 0);
        orderInfo.setStatus((byte) 0);
        orderInfo.setCreateDate(new Date());
        Integer val2 = orderInfoServiceImpl.insOrderInfo(orderInfo);

        if (true) throw new RuntimeException("seata 分布式事务测试");

        Goods goods = new Goods();
        goods.setId(5555503L);
        goods.setGoodsName("test03");
        goods.setGoodsTitle("title");
        goods.setGoodsImg("null");
        goods.setGoodsDetail("detail");
        goods.setGoodsPrice(new BigDecimal(1000));
        goods.setGoodsStock(1000);
        Integer val3 = goodsServiceImpl.insGoods(goods);

        if (val1+val2+val3 == 3) return "SUCCESS";
        else return "ERROR";
    }


}
