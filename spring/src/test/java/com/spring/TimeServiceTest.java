package com.spring;

import com.spring.controllers.TimeController;
import com.spring.json.Input2;
import com.spring.services.TimeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class TimeServiceTest {
    TimeService service;
    @Before
    public void setService(){
        service = new TimeService();
    }
    @Test
    public void convertToZonedDateTime(){
        Assert.assertEquals("2022-10-22T13:36+06:00[UTC+06:00]",
                            service.convertToZonedDateTime("2022-10-22 13:36", "UTC+6").toString());
    }
    @Test
    public void convertFromDifferentZone(){
        Assert.assertEquals("2022-10-22T13:36+06:00[UTC+06:00]",
                    service.convertFromDifferentZone("2022-10-22 10:36", "UTC+3", "UTC+6").toString()
                );
    }
    @Test
    public void dateTimeDifference(){
        Assert.assertEquals("0 0 6",
                service.dateTimeDifference(new Input2("2022-10-22 13:36", "UTC+3", "2022-10-22 14:42", "UTC+4"))
        );
    }
}
