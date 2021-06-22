package com.spring;


import com.spring.controllers.TimeController;
import com.spring.json.DateTimeObject;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MockTests {
    private MockMvc mockMvc;
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TimeController()).build();
    }
    @SneakyThrows
    @Test
    public void test1(){
        mockMvc.perform(
                post("/convert")
                        .content("{\"date\": \"2200-04-08 12:35\", \"zone1\": \"UTC+8\", \"zone2\":\"UTC+5\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$").value("2200-04-08 09:35  [UTC+05:00]"));
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
                .andExpect(jsonPath("$").value("3 3 3"));
    }
    @SneakyThrows
    @Test
    public void test3(){
        MvcResult result = mockMvc.perform(
                post("/journal").content("{\"text\":\"Adwfwafawfawfaw\", \"timezone\":\"UTC+0\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeObject dateTimeObject = new DateTimeObject(localDateTime.toLocalDate().toString()+
                " "+localDateTime.getHour()%24+":"+localDateTime.getMinute()%60,"UTC+0");
        mockMvc.perform(
                get("/journal/"+dateTimeObject.getDate()+"/UTC+0")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$").value("Adwfwafawfawfaw"));
        //mockMvc.perform(
        //        get("/journal/2200-04-09 20:36/UTC+5")
        //                .contentType(MediaType.APPLICATION_JSON)
        //)
        //        .andExpect(jsonPath("$").value("awsa"));
    }
}
