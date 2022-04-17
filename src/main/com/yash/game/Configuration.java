package com.yash.game;

/**
 * Created by gagan.ichake on 07-10-2016.
 */

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@Configurable
@ComponentScan(basePackages="com.yash")
@EnableAutoConfiguration
public class Configuration {


//    @Bean
//    public GameSession getGameSession(){
//        return new GameSession();
//    }
}
