package com.sanluna.commons.model;

import com.sanluna.commons.util.Converter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.sanluna.commons.util.Converter.formatTime;

public class HealthChecker {

    private LocalDateTime startTime;
    private String runningTime;
    private String message;

    public HealthChecker() {
        this.startTime = LocalDateTime.now();
        this.message = "up";
    }

    public String getStartTime() {
        return formatTime(startTime);
    }

    public HealthChecker setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public HealthChecker setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public HealthChecker setRunningTime() {
        Long days = ChronoUnit.DAYS.between(startTime, LocalDateTime.now());
        Long hours = ChronoUnit.HOURS.between(startTime, LocalDateTime.now()) % 24;
        Long minutes = ChronoUnit.MINUTES.between(startTime, LocalDateTime.now()) % 60;
        Long seconds = ChronoUnit.SECONDS.between(startTime, LocalDateTime.now()) % 60;
        this.runningTime = String.format("%d Days, %d Hours, %d Minutes, %d Seconds", days, hours, minutes, seconds);
        return this;
    }

    @Override
    public String toString(){
        return String.format("status: %s, started: %s, running: %s", getMessage(), Converter.formatTime(getStartTime()),getRunningTime());
    }

}
