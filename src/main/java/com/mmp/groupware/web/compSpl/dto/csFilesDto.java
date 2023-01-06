package com.mmp.groupware.web.compSpl.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class csFilesDto {

    private Long csFilesNo;

    private Long atcNo;

    private Long csNo;

    private String atcOriNm;

    private String atcUpNm;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
