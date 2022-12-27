package com.mmp.groupware.web.calendar.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class calendarDto {

    private Long calNo;

    private String calNm;

    private String calContent;

    private LocalDateTime calDate;

    private LocalTime calStartTime;

    private LocalTime calEndTime;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
