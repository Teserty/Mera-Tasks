package com.spring.services;

import com.spring.json.Range;
import com.spring.repos.Journal;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
public class JournalService {
    private final Journal entry;
    @Bean
    public Journal getEntry(){
        return new Journal();
    }
    public JournalService(Journal entry) {
        this.entry = entry;
    }

    public String getFromJournal(String time, String timezone){
        return entry.getText(time, timezone);
    }
    public ZonedDateTime addToJournal(String timezone, String text){
        return entry.addText(timezone, text);
    }
    public ConcurrentLinkedQueue<String> getFromJournalInRange(String time1, String time2, String timezone){
        Range range = new Range(time1, time2, timezone);
        ZonedDateTime zonedDateTime1 = range.getZonedDateTime1();
        ZonedDateTime zonedDateTime2 = range.getZonedDateTime2();
        List<ZonedDateTime> keys = entry.keySet().stream().filter((key) ->
                key.toLocalDate().isAfter(zonedDateTime1.withZoneSameInstant(ZoneId.of("UTC+0")).toLocalDate()) &&
                        key.toLocalDate().isBefore(zonedDateTime2.withZoneSameInstant(ZoneId.of("UTC+0")).toLocalDate())
        )
                .collect(Collectors.toList());
        ConcurrentLinkedQueue<String> vals = new ConcurrentLinkedQueue<>();
        keys.forEach((key)-> {
            vals.add(entry.map.get(key));
        });
        return vals;
    }
}
