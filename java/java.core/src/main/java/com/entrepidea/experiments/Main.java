package com.entrepidea.experiments;

public class Main {
    private static void testProxy(){
        HelloService helloService = new HelloServiceImpl();
        // 按约定获取proxy
        HelloService proxy = (HelloService)ProxyBean.getProxyBean(helloService, new MyInterceptor());
        proxy.sayHello("zhangsan");
    }
    public static void main(String[] args){
        testProxy();

    }
}
