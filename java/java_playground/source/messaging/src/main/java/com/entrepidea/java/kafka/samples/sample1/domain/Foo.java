package com.entrepidea.java.kafka.samples.sample1.domain;

public class Foo {

    private String foo;

    public Foo() {
    }

    public Foo(String foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return this.foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    @Override
    public String toString() {
        return "Foo [foo=" + this.foo + "]";
    }

}
