package com.entrepidea.spring.book.spring_boot_dissected.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class User2 {

        public int getId() {
        return id;
    }

        public String getName() {
        return name;
    }

        @Value("2")
        private  int  id;
        @Value("Cathy")
        private  String  name;
    }

