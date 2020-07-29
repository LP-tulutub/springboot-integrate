package com.boot.service;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class WebFluxService {

    public String success(){
        return "SUCCESS";
    }

    public String[] array(){
        List<String> list = new ArrayList<>();
        list.add("Flux");
        list.add("SUCCESS");
        String[] array =new String[list.size()];
        return list.toArray(array);
    }

    public String delaySuccess(){
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }

    public void publisher(Subscriber subscriber) {
        System.out.println(subscriber);
        subscriber.onSubscribe(new Subscription() {
            @Override
            public void request(long l) {
                System.err.println("request: " + l);
            }

            @Override
            public void cancel() {
                System.err.println("cancel: ");
            }
        });
        subscriber.onComplete();
    }
}
