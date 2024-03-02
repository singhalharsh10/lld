package com.harsh.doctorappointment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TimeSlot {

    private Double startTime;
    private Double endTime;

    public TimeSlot(Double startTime, Double endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Double getStartTime() {
        return startTime;
    }

    public Double getEndTime() {
        return endTime;
    }

}
