package com.spring;


import com.spring.controllers.TimeController;
import com.spring.json.DateTimeObject;
import com.spring.json.Input2;
import com.spring.repos.Journal;
import com.spring.services.TimeService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TimeControllerTests {
    private MockMvc mockMvc;
    TimeService timeService;
    @Before
    public void setup() {
        timeService = Mockito.mock(TimeService.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TimeController(timeService)).build();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse("2021-04-09 19:36", formatter);
        ZoneId currentZone = ZoneId.of("UTC+3");
        Mockito.when(timeService.convertFromDifferentZone("2021-04-09 19:36", "UTC+8", "UTC+3"))
                .thenReturn(ldt.atZone(currentZone));
        Mockito.when(timeService.dateTimeDifference(new Input2("2200-04-09 19:36", "UTC+4","2200-04-12 23:39", "UTC+5"))).thenReturn("3 1 0");

    }
    @SneakyThrows
    @Test
    public void test1(){

        mockMvc.perform(
                post("/convert")
                        .content("{\"date\": \"2021-04-09 19:36\", \"zone1\": \"UTC+8\", \"zone2\":\"UTC+3\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(content().string("{\"status\":\"200\",\"text\":\"2021-04-09 19:36  [UTC+03:00]\"}"));
    }
    @SneakyThrows
    @Test
    public void test2(){
        mockMvc.perform(
                post("/period")
                        .content("{\n" +
                                "        \"date1\": \"2200-04-09 19:36\",\n" +
                                "        \"timeZone1\": \"UTC+4\",\n" +
                                "        \"date2\": \"2200-04-12 23:39\",\n" +
                                "        \"timeZone2\": \"UTC+5\" \n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(content().string("{\"status\":\"200\",\"text\":\"3 1 0\"}"));
    }
}
