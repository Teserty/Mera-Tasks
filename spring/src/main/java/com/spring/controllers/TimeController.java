package com.spring.controllers;

import com.spring.Entry.JournalEntry;
import com.spring.json.DateTimeObject;
import com.spring.json.Input2;
import com.spring.json.JournalAdd;
import com.spring.json.TimeObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class TimeController {
    public ZonedDateTime convertFromZone(String dateInString, String zone1, String zone2){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(dateInString, formatter);
        ZoneId currentZone = ZoneId.of(zone1);
        ZonedDateTime currentTime = ldt.atZone(currentZone);
        ZoneId convertedZone = ZoneId.of(zone2);
        return currentTime.withZoneSameInstant(convertedZone);
    }
    /*
    Задача 1.
    конвертер дат - на вход дата-время плюс таймзона и желаемая таймзона - вернуть время в желаемой таймзоне
    Пример Json: {"date": "2200-04-08 12:35", "zone1": "UTC+8", "zone2":"UTC+5"}
     */
    @PostMapping("/convert")
    public String convert(@RequestBody TimeObject timeToConvert){
        ZonedDateTime time = convertFromZone(timeToConvert.getDate(), timeToConvert.getZone1(), timeToConvert.getZone2());
        return time.toString().replaceFirst("T", " ").substring(0, 16)+"  "+time.toString().substring(22);
    }

    /*
    Задача 2
     разницу между двумя датами - сколько дней, минут, секунд между двумя датами в разных таймзонах
    * Пример Json
    * {
        "date1": "2200-04-09 19:36",
        "timeZone1": "UTC+4",
        "date2": "2200-04-12 23:39",
        "timeZone2": "UTC+5"
    * }
    * */
   @PostMapping("/period")
   public String dateTimeDifference(@RequestBody Input2 input){
       DateTimeObject dateTimeObject1 = new DateTimeObject(input.getDate1(), input.getTimeZone1());
       DateTimeObject dateTimeObject2 = new DateTimeObject(input.getDate2(), input.getTimeZone2());
       Period a = Period.between(dateTimeObject1.getZonedDateTime().toLocalDate(), dateTimeObject2.getZonedDateTime().toLocalDate());
       Duration d = Duration.between( dateTimeObject1.getZonedDateTime() , dateTimeObject2.getZonedDateTime() );
       return a.getDays()+" "+d.toHours()%24+" "+d.toMinutes()%60;
   }
   /*задача 3

   В данной версии не поддерживается возможность добавлять несколько записей в одно время. Решаемо.
   Пример:      {"text": "Adwfwafawfawfaw", "timezone": "UTC+6"}
                  journal/2021-06-21 12:24/UTC+5
   * */
    JournalEntry entry = new JournalEntry();
    @GetMapping("/journal/{time}/{timezone}")
    public String getJournal(@PathVariable String time, @PathVariable String timezone){
        if(entry.getText(time, timezone) == null) {
            System.out.println(entry.map.keySet());
            System.out.println("Not founded");
        }
        return entry.getText(time, timezone);
    }
    @PostMapping("/journal")
    public ZonedDateTime addJournal(@RequestBody JournalAdd journalAdd){
        return entry.addText(journalAdd.getTimezone(),journalAdd.getText());
    }
}
