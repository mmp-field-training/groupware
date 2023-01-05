package com.mmp.groupware.web.appr.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class apprDivFilesDto {

    private Long apprDivFilesNo;

    private Long atcNo;

    private Long apprDivNo;

    private String atcOriNm;

    private String atcUpNm;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
