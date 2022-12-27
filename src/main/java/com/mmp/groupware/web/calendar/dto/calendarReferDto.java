package com.mmp.groupware.web.calendar.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class calendarReferDto {

    private Long calRefNo;

    private Long calNo;

    private Long calRefStfNo;

    private String calRefStfNm;

}
