package com.spring.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Input2 {
    String date1;
    String timeZone1;
    String date2;
    String timeZone2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Input2 input2 = (Input2) o;
        return Objects.equals(date1, input2.date1) &&
                Objects.equals(timeZone1, input2.timeZone1) &&
                Objects.equals(date2, input2.date2) &&
                Objects.equals(timeZone2, input2.timeZone2);
    }
}
