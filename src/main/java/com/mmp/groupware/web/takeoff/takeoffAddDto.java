package com.mmp.groupware.web.takeoff;


import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class takeoffAddDto {


    private Long takeoffNo;

    private Long takeoffdeptNo;


    private Long takeoffStfNo;


    private String takeoffNm;

    private String takeoffCont;

    private LocalDate takeoffStartDt;

    private LocalDate takeoffEndDt;

    private String takeoffAppryn;

    private String takeoffTotal;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;
}

