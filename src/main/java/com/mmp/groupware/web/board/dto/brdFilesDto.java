package com.mmp.groupware.web.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class brdFilesDto {

    private Long brdFilesNo;

    private Long atcNo;

    private Long brdNo;

    private String atcOriNm;

    private String atcUpNm;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
