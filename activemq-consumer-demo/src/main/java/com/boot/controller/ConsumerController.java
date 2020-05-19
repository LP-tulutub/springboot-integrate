package com.boot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ConsumerController {

    /*@Autowired
    private HfnsTermService hfnsTermService;*/

    /**
     * 监听队列
     * @param message
     */
    @JmsListener(destination="queue")
    public void readActiveQueue(String message) {
        System.out.println("queue01模式接收：" + message);
    }

    @JmsListener(destination = "queue")
    public void readQueue(String message){
        System.out.println("queue02模式接收："+message);
    }

    @JmsListener(destination = "topic")
    public void receiveMessage(String message) throws InterruptedException {
        Thread.sleep(4000);
        /*System.out.println(hfnsTermService.getCount());*/
        System.out.println(new Date().toString()+"topic01接收:"+message);
    }

    @JmsListener(destination = "topic")
    public void readResponse(String message) throws InterruptedException {
        Thread.sleep(1000);
        /*for(HfnsTerm hfnsTerm:hfnsTermService.getHfnsTerm()){
            System.out.println(hfnsTerm.getId());
        }*/
        System.out.println(new Date().toString()+"topic02接收:"+message);
    }

}
