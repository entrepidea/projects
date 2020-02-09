package com.entrepidea.books.spring_boot_dissected.support;

import org.springframework.context.annotation.ComponentScan;

//both below annotations work
//@ComponentScan(basePackages = "com.entrepidea.spring.core.ioc.supports.sprin_boot_book")
@ComponentScan(basePackageClasses = User2.class)
public class AppConfig2 {
}
