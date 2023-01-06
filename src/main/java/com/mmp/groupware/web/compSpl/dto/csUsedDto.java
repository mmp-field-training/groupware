package com.mmp.groupware.web.compSpl.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class csUsedDto {

    private int rownum;

    private Long csUsedNo;

    private Long csNo;

    private Long csUsedStfNo;

    private String csUsedStfNm;

    private String csUsedTit;

    private String csUsedCont;

    private LocalDateTime csUsedDt; // 물품 사용일자

    private LocalDateTime csUsedWrtDt; // 물품 사용이력 작성일자

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
