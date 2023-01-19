package com.mmp.groupware.web.takeoff;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class takeoffDto {
    private int rownum;

    private Long tofNo;

    private Long takeoffStfNo;

    private String takeoffStfNm;
    //private String takeoffWrtNm;

    private String takeoffdeptNm;
    private String takeoffNm;

    private String takeoffdeptNo;

    private String takeoffCont;

    private LocalDate takeoffStartDt;

    private LocalDate takeoffEndDt;

    private String takeoffAppryn;

    private String takeoffTotal;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
