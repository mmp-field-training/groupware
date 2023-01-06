package com.mmp.groupware.web.compSpl.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class atclDivDto {

    private int rownum;

    private Long atclDivNo;

    private String atclDivNm;

    private String atclDivUsedYn;

    private String atclDivComm;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
