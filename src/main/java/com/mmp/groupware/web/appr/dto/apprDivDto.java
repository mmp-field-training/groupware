package com.mmp.groupware.web.appr.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class apprDivDto {

    private int rownum;

    private Long apprDivNo;

    private String apprDivNm;

    private LocalDateTime apprDivWrtDt;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
