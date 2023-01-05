package com.mmp.groupware.web.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class brdReplyDto {

    private Long brdReplyNo;

    private Long brdNo;

    private Long brdReplyWrtStfNo;

    private String brdReplyCont;

    private String brdReplyWrtNm;

    private LocalDateTime brdReplyWrtDt;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
