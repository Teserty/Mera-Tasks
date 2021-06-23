package com.spring;

import com.spring.json.DateTimeObject;
import com.spring.repos.Journal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class JournalTest {
    Journal journal;
    @Before
    public void setJournal(){
        journal = new Journal();
    }
    @Test
    public void getText(){
        Assert.assertEquals("", journal.getText("", ""));
    }
    @Test
    public void addText(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeObject dateTimeObject = new DateTimeObject(localDateTime.toLocalDate().toString()+
                " "+localDateTime.getHour()%24+":"+localDateTime.getMinute()%60, "UTC+0");
        Assert.assertEquals(dateTimeObject.getZonedDateTime()
                , journal.addText("UTC+0", "ASa"));
    }
}
