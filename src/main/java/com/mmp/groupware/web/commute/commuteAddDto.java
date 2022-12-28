package com.mmp.groupware.web.commute;

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

public class commuteAddDto {
    private Long atteNo;

    private Long stfNo;

    private String atteYn;

    private LocalDateTime atteTime;

    private LocalDateTime leavedTime;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}