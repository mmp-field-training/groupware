package com.mmp.groupware.web.compSpl.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class atclPstDto {

    private int rownum;

    private Long atclPstNo;

    private String atclPstNm;

    private String atclPstUsedYn;

    private String atclPstComm;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
