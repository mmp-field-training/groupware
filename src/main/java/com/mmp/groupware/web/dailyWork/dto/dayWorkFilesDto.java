package com.mmp.groupware.web.dailyWork.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class dayWorkFilesDto {

    private Long dayWorkFilesNo;

    private Long atcNo;

    private Long dayWorkNo;

    private String atcOriNm;

    private String atcUpNm;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;
}
