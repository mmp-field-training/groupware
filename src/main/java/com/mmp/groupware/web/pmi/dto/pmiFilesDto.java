package com.mmp.groupware.web.pmi.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class pmiFilesDto {

    private Long pmiFilesNo;

    private Long atcNo;

    private Long pmiNo;

    private String atcOriNm;

    private String atcUpNm;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
