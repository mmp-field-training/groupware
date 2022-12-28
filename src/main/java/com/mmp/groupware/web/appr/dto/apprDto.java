package com.mmp.groupware.web.appr.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class apprDto {

    private int rownum;

    private Long apprNo;

    private Long apprDivNo;

    private Long apprStfNo;

    private String apprTit;

    private String apprCont;

    private int apprState;

    private String apprReject;

    private LocalDate apprWrtDt;

    private String apprWrtNm;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
