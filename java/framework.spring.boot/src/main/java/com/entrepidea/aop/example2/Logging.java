package com.entrepidea.aop.example2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class Logging {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logging.class);

    /** Following is the definition for a PointCut to select
     *  all the methods available. So advice will be called
     *  for all the methods.
     */
    @Pointcut("execution(* com.entrepidea.aop.example2.Student.getAge(..))")
    private void selectGetAge(){}

    /**
     * This is the method which I would like to execute
     * around a selected method execution.
     */
    @Around("selectGetAge()")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        LOGGER.info("Around advice...");
        Object[] args = proceedingJoinPoint.getArgs();
        if(args.length>0){
            LOGGER.info("Arguments passed: " );
            for (int i = 0; i < args.length; i++) {
                LOGGER.info("arg: {}:{}",(i+1),args[i]);
            }
        }

        Object result = proceedingJoinPoint.proceed(args);
        LOGGER.info("Returning {} ", result);
    }
}
