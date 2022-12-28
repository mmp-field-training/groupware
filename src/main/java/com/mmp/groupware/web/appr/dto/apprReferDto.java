package com.mmp.groupware.web.appr.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class apprReferDto {

    private int rownum;

    private Long apprRefNo;

    private Long apprNo;

    private Long apprRefStfNo;

    private String apprRefStfNm;

}
