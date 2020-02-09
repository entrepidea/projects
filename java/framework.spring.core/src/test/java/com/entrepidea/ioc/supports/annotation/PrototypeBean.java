package com.entrepidea.ioc.supports.annotation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by jonat on 4/22/2017.
 */
@Component
@Scope("prototype")
public class PrototypeBean {
    public void showMe(){
        System.out.println("I am a prototype bean");
    }
}
