package com.spring;


import com.spring.json.DateTimeObject;
import com.spring.json.Input2;
import com.spring.repos.Journal;
import com.spring.services.JournalService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class JournalServiceTest {
    JournalService service;
    ZonedDateTime time;
    @Before
    public void setService(){
        Journal journal = Mockito.mock(Journal.class);
        journal.map = new ConcurrentHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse("2021-04-09 19:36", formatter);
        ZoneId currentZone = ZoneId.of("UTC+3");
        journal.map.put(ldt.atZone(currentZone), "asd");
        time = ZonedDateTime.now();
        Mockito.when(journal.keySet()).thenReturn(journal.map.keySet());
        Mockito.when(journal.getText("2021-04-09 19:36","UTC+3")).thenReturn("asa");
        Mockito.when(journal.addText("UTC+0", "Test")).thenReturn(time);
        service = new JournalService(journal);
    }
    @Test
    public void getFromJournal(){
        Assert.assertEquals("asa", service.getFromJournal("2021-04-09 19:36","UTC+3"));
    }
    @Test
    public void addToJournal(){
        Assert.assertEquals(time, service.addToJournal("UTC+0", "Test"));
    }
    @Test
    public void getFromJournalInRange(){
        Assert.assertEquals(1, service.getFromJournalInRange("2000-04-09 19:36", "2022-04-09 19:36", "0").size());
    }
    //
}
