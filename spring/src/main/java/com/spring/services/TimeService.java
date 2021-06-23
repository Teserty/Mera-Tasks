package com.spring.services;

import com.spring.json.DateTimeObject;
import com.spring.json.Input2;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Service
public class TimeService {
    public ZonedDateTime convertToZonedDateTime(String time, String zone){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(time, formatter);
        ZoneId currentZone = ZoneId.of(zone);
        return ldt.atZone(currentZone);
    }
    public ZonedDateTime convertFromDifferentZone(String dateInString, String zone1, String zone2){
        ZonedDateTime currentTime = convertToZonedDateTime(dateInString, zone1);
        ZoneId convertedZone = ZoneId.of(zone2);
        return currentTime.withZoneSameInstant(convertedZone);
    }
    public String dateTimeDifference(Input2 input){
        DateTimeObject dateTimeObject1 = new DateTimeObject(input.getDate1(), input.getTimeZone1());
        DateTimeObject dateTimeObject2 = new DateTimeObject(input.getDate2(), input.getTimeZone2());
        Period a = Period.between(dateTimeObject1.getZonedDateTime().toLocalDate(), dateTimeObject2.getZonedDateTime().toLocalDate());
        Duration d = Duration.between( dateTimeObject1.getZonedDateTime() , dateTimeObject2.getZonedDateTime() );
        return a.getDays()+" "+d.toHours()%24+" "+d.toMinutes()%60;
    }
}
