package com.entrepidea.core.features.v8;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Desc:
 * Try-resource statement implicitly close the resource, thus rendering explicitly close() statement no more needed.
 *
 * @Source: https://zhuanlan.zhihu.com/p/124630036?utm_source=com.ideashower.readitlater.pro&utm_medium=social&utm_oi=809364293245075456
 *
 * @Date: 04/19/20
 *
 * */
public class TryResourceTests {
    private static Logger log = LoggerFactory.getLogger(TryResourceTests.class);

    @Before
    public void setup(){
        BasicConfigurator.configure();
    }

    @Test
    public void BufferReaderTest(){
        try(BufferedReader br = new BufferedReader(new FileReader("pom.xml"))){ //no need to explicitly invoke br.close()
            String line;
            while((line = br.readLine())!=null) {
                log.info(line);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
