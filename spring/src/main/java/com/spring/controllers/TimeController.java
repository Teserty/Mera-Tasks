package com.spring.controllers;


import com.spring.json.*;
import com.spring.services.TimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import java.time.*;

@RestController
public class TimeController {
    Logger logger = LoggerFactory.getLogger(TimeController.class);

    /*
    Задача 1.
    конвертер дат - на вход дата-время плюс таймзона и желаемая таймзона - вернуть время в желаемой таймзоне
    Пример Json: {"date": "2200-04-08 12:35", "zone1": "UTC+8", "zone2":"UTC+5"}
     */
    private final TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }
    @Bean
    public TimeService getTimeService(){
        return new TimeService();
    }
    @PostMapping("/convert")
    public Response convert(@RequestBody TimeObject timeToConvert){
        ZonedDateTime time = timeService.convertFromDifferentZone(timeToConvert.getDate(), timeToConvert.getZone1(), timeToConvert.getZone2());
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
       String line = timeService.dateTimeDifference(input);
       Input2 a = new Input2("2200-04-09 19:36", "UTC+4","2200-04-12 23:39", "UTC+5");
       System.out.println(a.equals(input));
       logger.debug("Duration: "+line);
       return new Response("200",line);
   }
}
