package com.spring.controllers;

import com.spring.repos.Journal;
import com.spring.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@RestController
public class TimeController {
    Logger logger = LoggerFactory.getLogger(TimeController.class);

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
    public Response convert(@RequestBody TimeObject timeToConvert){
        ZonedDateTime time = convertFromZone(timeToConvert.getDate(), timeToConvert.getZone1(), timeToConvert.getZone2());
        logger.debug(time.toString() + " converted from "+timeToConvert.getDate());
        return new Response("200",time.toString().replaceFirst("T", " ").substring(0, 16)+"  "+time.toString().substring(22));
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
   public Response dateTimeDifference(@RequestBody Input2 input){
       DateTimeObject dateTimeObject1 = new DateTimeObject(input.getDate1(), input.getTimeZone1());
       DateTimeObject dateTimeObject2 = new DateTimeObject(input.getDate2(), input.getTimeZone2());
       Period a = Period.between(dateTimeObject1.getZonedDateTime().toLocalDate(), dateTimeObject2.getZonedDateTime().toLocalDate());
       Duration d = Duration.between( dateTimeObject1.getZonedDateTime() , dateTimeObject2.getZonedDateTime() );
       logger.debug("Duration: "+a.getDays()+" "+d.toHours()%24+" "+d.toMinutes()%60);
       return new Response("200",a.getDays()+" "+d.toHours()%24+" "+d.toMinutes()%60);
   }
   /*задача 3

   В данной версии не поддерживается возможность добавлять несколько записей в одно время. Решаемо.
   Пример:      {"text": "Adwfwafawfawfaw", "timezone": "UTC+6"}
                  journal/2021-06-21 12:24/UTC+5
   * */
    Journal entry = new Journal();
    @GetMapping("/journal")
    public Response getJournal(@RequestParam String time, @RequestParam String timezone){
        if(entry.getText(time, timezone) == null) {
            System.out.println(entry.map.keySet());
            System.out.println("Not founded");
            logger.warn(entry.map.keySet().toString() + " Not founded");
            return new Response("500", "Not founded");
        }
        logger.debug(entry.getText(time, timezone) + " founded with params "+ time +" "+timezone);
        return new Response("200",entry.getText(time, timezone));
    }
    @PostMapping("/journal")
    public Response addJournal(@RequestBody JournalAdd journalAdd){
        ZonedDateTime dateTime = entry.addText(journalAdd.getTimezone(),journalAdd.getText());
        if (dateTime == null){
            return new Response("200", "Value not found");
        }else{
            return new Response("200", dateTime.toString());
        }
    }
    /*Задача 4
    Пример пути
    /journalB?time1=2000-04-09 19:36&time2=2200-04-09 19:36&timezone=6

    * */
    @GetMapping("/journalB")
    public Response getJournalB(@RequestParam String time1, @RequestParam String time2, @RequestParam String timezone){
        Range range = new Range(time1, time2, timezone);
        ZonedDateTime zonedDateTime1 = range.getZonedDateTime1();
        ZonedDateTime zonedDateTime2 = range.getZonedDateTime2();
        List<ZonedDateTime> keys = entry.map.keySet().stream().filter((key) ->
               key.toLocalDate().isAfter(zonedDateTime1.withZoneSameInstant(ZoneId.of("UTC+0")).toLocalDate()) &&
               key.toLocalDate().isBefore(zonedDateTime2.withZoneSameInstant(ZoneId.of("UTC+0")).toLocalDate())
        )
                .collect(Collectors.toList());
        ConcurrentLinkedQueue<String> vals = new ConcurrentLinkedQueue<>();
        keys.forEach((key)-> {
            vals.add(entry.map.get(key));
        });
        logger.warn("Founded "+vals.toString() + ". Params: " + time1 + " " +time2 + " "+timezone);
        return new Response("200",vals.toString());
    }
}
