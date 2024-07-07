package com.entrepidea.core.proxy;

import com.entrepidea.core.proxy.CglibProxy.AliSmsService;
import com.entrepidea.core.proxy.CglibProxy.CglibProxyFactory;
import com.entrepidea.core.proxy.dyncProxy.JdkProxyFactory;
import com.entrepidea.core.proxy.staticProxy.SmsProxy;
import com.entrepidea.core.proxy.common.SmsService;
import com.entrepidea.core.proxy.common.SmsServiceImpl;
import org.junit.Test;

/*
* Proxy is the technology behind Spring's AOP framework.
* https://zhuanlan.zhihu.com/p/707531459
*
* 07/07/24
*
* */

public class ProxyTests {
    @Test
    public void testStaticProxy(){
        SmsService smsService = new SmsServiceImpl();
        SmsProxy smsProxy = new SmsProxy(smsService);
        smsProxy.send("java");
    }

    @Test
    public void testDyncProxy(){
        SmsService smsService = (SmsService) JdkProxyFactory.getProxy(new SmsServiceImpl());
        smsService.send("java");
    }

    //TODO the test below didn't go through.
    @Test
    public void testCglibProxy(){
        AliSmsService aliSmsService = (AliSmsService) CglibProxyFactory.getProxy(AliSmsService.class);
        aliSmsService.send("java");
    }
}
