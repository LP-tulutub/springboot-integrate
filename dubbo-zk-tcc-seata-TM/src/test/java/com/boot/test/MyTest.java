package com.boot.test;

import com.boot.pojo.Admin;
import com.boot.utils.JsonUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyTest {
    
    @Test
    public void test01(){
        Admin admin = new Admin();
        admin.setId(5555501);
        admin.setName("TM");
        admin.setPassword("d3b1294a61a07da9b49b6e22b2cbd7f9");

        String strAdmin = JsonUtils.objectToJson(admin);
        System.out.println(strAdmin);

        Admin pojo = JsonUtils.jsonToPojo(strAdmin, Admin.class);
        System.out.println(pojo);

    }
    
    
}
