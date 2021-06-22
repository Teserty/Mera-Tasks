package com.spring.json;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Range {
    String time1;
    String time2;
    String timezone;
    public ZonedDateTime getZonedDateTime1(){
        return getZonedDateTime(time1);
    }
    public ZonedDateTime getZonedDateTime2(){
        return getZonedDateTime(time2);
    }
    public ZonedDateTime getZonedDateTime(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(time, formatter);
        ZoneId currentZone = ZoneId.of("UTC+"+timezone);
        return ldt.atZone(currentZone);
    }
}
