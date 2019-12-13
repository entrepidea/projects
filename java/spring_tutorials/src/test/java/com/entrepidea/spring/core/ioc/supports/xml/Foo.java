package com.entrepidea.spring.core.ioc.supports.xml;

public class Foo {

    public Foo(){}

    private int i;
    private String str;
    public Foo(int i, String str){
        this.i = i;
        this.str = str;
    }


    private Bar bar;
    private Baz baz;

    public Foo(Bar bar, Baz baz){
        this.bar = bar;
        this.baz = baz;
    }

    public void setBar(Bar bar){
        this.bar = bar;
    }

    public void setBaz(Baz baz){
        this.baz = baz;
    }

    public String getBar(){
        return bar.toString();
    }

    public String getBaz(){
        return baz.toString();
    }


    public String print(){
        String ret = String.format("i=%d,str=%s",i,str);
        return ret;
    }
}
