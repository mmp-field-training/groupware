package com.mmp.groupware.web.compSpl.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class csDto {

    private int rownum;

    private Long csNo;

    private Long atclDivNo;

    private Long atclPstNo;

    private String csNm;

    private String csStd;

    private Long csCnt;

    private LocalDateTime csPcsDt;

    private Long csPcsPay;

    private String csComm;

    private Long csWrtStfNo;

    private String csWrtNm;

    private LocalDateTime csWrtDt;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
