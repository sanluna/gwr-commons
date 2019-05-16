package com.sanluna.commons.util;

import org.springframework.beans.factory.annotation.Value;

public class Logger {

    public static int WARN = 1;
    public static int INFO = 2;
    public static int DEBUG = 3;

    @Value("${logging.GWR:0}")
    private int logLevel;

    public void log(String message, int level){
        String loggingLevelString = SetupLevelString(level);
        if(logLevel >= WARN && level >= WARN){
            System.out.println("Logger(" + loggingLevelString + "): " + message);
        }
        if(logLevel >= INFO && level >= INFO){
            System.out.println("Logger(" + loggingLevelString + "): " + message);
        }
        if(logLevel >= DEBUG && level >= DEBUG){
            System.out.println("Logger(" + loggingLevelString + "): " + message);
        }
    }

    private String SetupLevelString(int level) {
        String loggingLevelString = "NOT SET";
        if(level == 1){
            loggingLevelString = "WARN";
        }
        if(level == 2){
            loggingLevelString = "INFO";
        }
        if(level >= 3){
            loggingLevelString = "DEBUG";
        }
        return loggingLevelString;
    }

}
