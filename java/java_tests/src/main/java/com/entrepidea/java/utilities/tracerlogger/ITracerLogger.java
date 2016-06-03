package com.entrepidea.java.utilities.tracerlogger;

public interface ITracerLogger 
{
    public void debug(Object message);
    public void debug(Object message, Throwable t);
    public void debug(Object message, Object source, Throwable t);

    public void error(Object message);
    public void error(Object message, Throwable t);
    public void error(Object message, Object source, Throwable t);

    public void info(Object message);
    public void info(Object message, Throwable t);
    public void info(Object message, Object source, Throwable t);

    public void warn(Object message);
    public void warn(Object message, Throwable t);
    public void warn(Object message, Object source, Throwable t);

    public void fatal(Object message);
    public void fatal(Object message, Throwable t);
    public void fatal(Object message, Object source, Throwable t);
    
    public void readLog();
    public void writeLog(Exception e);
    public void setLogLevel(int logLevel);
}