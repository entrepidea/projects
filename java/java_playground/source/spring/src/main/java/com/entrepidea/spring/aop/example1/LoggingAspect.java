package com.entrepidea.spring.aop.example1;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.entrepidea.spring.aop.example1.EmployeeManagerImpl.*(..))")
    public void logAroundAllMethods(ProceedingJoinPoint joinPoint) throws Throwable
    {
        LOGGER.info("****LoggingAspect.logAroundAllMethods() : {} : Before Method Execution.", joinPoint.getSignature().getName() );
        try {
            joinPoint.proceed();
        } finally {
            //Do Something useful, If you have
        }
        LOGGER.info("****LoggingAspect.logAroundAllMethods() : {} : After Method Execution", joinPoint.getSignature().getName());
    }

    @Around("execution(* com.entrepidea.spring.aop.example1.EmployeeManagerImpl.getEmployeeById(..))")
    public void logAroundGetEmployee(ProceedingJoinPoint joinPoint) throws Throwable
    {
        LOGGER.info("****LoggingAspect.logAroundGetEmployee() : {} : Before Method Execution", joinPoint.getSignature().getName());
        try {
            joinPoint.proceed();
        } finally {
            //Do Something useful, If you have
        }
        LOGGER.info("****LoggingAspect.logAroundGetEmployee() : {} : After Method Execution", joinPoint.getSignature().getName() );
    }

    @Around("execution(* com.entrepidea.spring.aop.example1.EmployeeManagerImpl.createEmployee(..))")
    public void logAroundCreateEmployee(ProceedingJoinPoint joinPoint) throws Throwable
    {
        LOGGER.info("****LoggingAspect.logAroundCreateEmployee() : {} : Before Method Execution", joinPoint.getSignature().getName());
        try {
            joinPoint.proceed();
        } finally {
            //Do Something useful, If you have
        }
        LOGGER.info("****LoggingAspect.logAroundCreateEmployee() : {} : After Method Execution", joinPoint.getSignature().getName());
    }
}
