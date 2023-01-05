package com.mmp.groupware.web.pmi.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class pmiDto {

    private int rownum;

    private Long pmiNo;

    private Long pmiStfNo;

    private String pmiTit;

    private String pmiCont;

    private LocalDateTime pmiDt;

    private LocalDateTime pmiWrtDt;

    private String pmiWrtNm;

    private String pmiAddYn;

    private int pmiPay;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
