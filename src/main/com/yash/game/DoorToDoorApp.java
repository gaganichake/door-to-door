package com.yash.game;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by gagan.ichake on 07-10-2016.
 */
@Import(Configuration.class)
public class DoorToDoorApp {

    public static void main(String[] args) {
        SpringApplication.run(DoorToDoorApp.class, args);
    }
}
