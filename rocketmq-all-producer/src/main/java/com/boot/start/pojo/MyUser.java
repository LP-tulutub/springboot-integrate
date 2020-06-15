package com.boot.start.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyUser {

    private long id;
    private String name;
    private int age;
    private String tag;

}
