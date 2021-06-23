package com.spring;

import com.spring.controllers.JournalController;
import com.spring.json.DateTimeObject;
import com.spring.repos.Journal;
import com.spring.services.JournalService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class JournalControllerTest {
    private MockMvc mockMvc;
    JournalService journalService;
    @Before
    public void setup() {
        journalService = Mockito.mock(JournalService.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new JournalController(journalService)).build();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse("2021-04-09 19:36", formatter);
        ZoneId currentZone = ZoneId.of("UTC+3");
        Mockito.when(journalService.addToJournal("UTC+3", "asa")).thenReturn(ldt.atZone(currentZone));
        Mockito.when(journalService.getFromJournal("2021-04-09 19:36","UTC+3")).thenReturn("asa");
        ConcurrentLinkedQueue<String> vals = new ConcurrentLinkedQueue<String>();
        vals.add("asa");
        vals.add("dad");
        Mockito.when(journalService.getFromJournalInRange("2021-04-08 19:36", "2021-04-09 19:36", "UTC+3")).thenReturn(vals);
    }

    @SneakyThrows
    @Test
    public void journalAdd(){
        mockMvc.perform(
                post("/journal").content("{\"text\":\"asa\", \"timezone\":\"UTC+3\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"status\":\"200\",\"text\":\"2021-04-09T19:36+03:00[UTC+03:00]\"}"));
    }
    @SneakyThrows
    @Test
    //content("{\"time\":\"2021-04-09 19:36\", \"timezone\":\"UTC+3\"}")
    public void journalGet(){
        mockMvc.perform(
                get("/journal").param("time", "2021-04-09 19:36").param("timezone", "3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"status\":\"200\",\"text\":\"asa\"}"));
    }
    @SneakyThrows
    @Test
    public void getJournalBetween(){
        mockMvc.perform(
                get("/journalB")
                        .param("time1", "2021-04-08 19:36")
                        .param("time2", "2021-04-09 19:36")
                        .param("timezone", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"status\":\"200\",\"text\":\"[asa, dad]\"}"));
    }
}
