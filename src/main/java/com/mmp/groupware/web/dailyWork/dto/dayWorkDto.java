package com.mmp.groupware.web.dailyWork.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class dayWorkDto {
    private int rownum;

    private Long dayWorkNo;

    private Long dayWorkWrtStfNo;

    private String dayWorkTit;

    private String dayWorkCont;

    private LocalDate dayWorkWrtDt;

    private String dayWorkWrtNm;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;
}
