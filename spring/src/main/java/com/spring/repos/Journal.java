package com.spring.repos;

import com.spring.json.DateTimeObject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class Journal {
    public ConcurrentHashMap<ZonedDateTime, String> map = new ConcurrentHashMap<>();
    public String getText(String time, String timeZone){
        DateTimeObject dateTimeObject= new DateTimeObject(time, timeZone);
        System.out.println(dateTimeObject.getZonedDateTime().toLocalDateTime());
        return map.get(dateTimeObject.getZonedDateTime().withZoneSameInstant(ZoneId.of("UTC+0")));
    }
    public ZonedDateTime addText(String timeZone, String text){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeObject dateTimeObject = new DateTimeObject(localDateTime.toLocalDate().toString()+
                " "+localDateTime.getHour()%24+":"+localDateTime.getMinute()%60,timeZone);
        map.put(dateTimeObject.getZonedDateTime().withZoneSameInstant(ZoneId.of("UTC+0")), text);
        return dateTimeObject.getZonedDateTime().withZoneSameInstant(ZoneId.of("UTC+0"));
    }
}
