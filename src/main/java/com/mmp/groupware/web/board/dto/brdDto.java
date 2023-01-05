package com.mmp.groupware.web.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class brdDto {

    private int rownum;

    private Long brdNo;

    private Long brdWrtStfNo;

    private String brdTit;

    private String brdCont;

    private String brdNtcYn;

    private LocalDateTime brdWrtDt;

    private String brdWrtNm;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
