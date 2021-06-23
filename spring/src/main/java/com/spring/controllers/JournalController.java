package com.spring.controllers;

import com.spring.json.JournalAdd;
import com.spring.json.Response;
import com.spring.services.JournalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.ZonedDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;
@RestController
public class JournalController {
    Logger logger = LoggerFactory.getLogger(JournalController.class);
    /*задача 3

   В данной версии не поддерживается возможность добавлять несколько записей в одно время. Решаемо.
   Пример:      {"text": "Adwfwafawfawfaw", "timezone": "UTC+6"}
                  journal/2021-06-21 12:24/UTC+5
   * */

    private final JournalService journalService;
    @Autowired
    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping("/journal")
    public Response getJournal(@RequestParam String time, @RequestParam String timezone){
        String line = journalService.getFromJournal(time, "UTC+"+timezone);
        logger.debug(line + " founded with params "+ time +" "+timezone);
        return new Response("200",line);
    }
    @PostMapping("/journal")
    public Response addJournal(@RequestBody JournalAdd journalAdd){
        ZonedDateTime dateTime = journalService.addToJournal(journalAdd.getTimezone(),journalAdd.getText());
        return new Response("200", dateTime.toString());
    }
    /*Задача 4
    Пример пути
    /journalB?time1=2000-04-09 19:36&time2=2200-04-09 19:36&timezone=6
    * */
    @GetMapping("/journalB")
    public Response getJournalB(@RequestParam String time1, @RequestParam String time2, @RequestParam String timezone){
        ConcurrentLinkedQueue<String> vals = journalService.getFromJournalInRange(time1, time2, "UTC+"+timezone);
        logger.debug("Founded "+vals.toString() + ". Params: " + time1 + " " +time2 + " "+timezone);
        return new Response("200",vals.toString());
    }
}
