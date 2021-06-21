package com.spring.json;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DateTimeObject {
    String date;
    String timezone;

    public ZonedDateTime getZonedDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(date, formatter);
        ZoneId currentZone = ZoneId.of(timezone);
        return ldt.atZone(currentZone);
    }
}
