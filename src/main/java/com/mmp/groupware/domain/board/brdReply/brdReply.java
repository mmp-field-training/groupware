package com.mmp.groupware.domain.board.brdReply;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="brdReply")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class brdReply {

    // 게시판 댓글 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brdReplyNo;

    // 부모 게시판 글 등록번호
    @Column(nullable=false)
    private Long brdNo;

    // 게시판 댓글 등록 사원 등록번호
    @Column(nullable=false)
    private Long brdReplyWrtStfNo;

    // 댓글 내용
    @Column(nullable=false)
    private String brdReplyCont;

    // 댓글 작성일자
    @Column(nullable=false)
    private LocalDateTime brdReplyWrtDt;

    // 데이터 생성일자
    @Column(nullable = false)
    private LocalDateTime createDt;

    // 데이터 수정일자
    @Column(nullable = true)
    private LocalDateTime updateDt;

    // 데이터 삭제일자
    @Column(nullable = true)
    private LocalDateTime deleteDt;

}
