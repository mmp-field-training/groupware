package com.mmp.groupware.domain.board.brd;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="brd")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class brd {

    // 게시판 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brdNo;

    // 게시판 등록 사원의 사원 등록번호
    @Column(nullable = false)
    private Long brdWrtStfNo;

    // 게시판 제목
    @Column(nullable = false, length = 100)
    private String brdTit;

    // 게시판 내용
    @Column(columnDefinition="TEXT", nullable = false)
    private String brdCont;

    // 게시판 공지 여부
    @Column(nullable = false)
    private String brdNtcYn;

    // 게시판 작성일자
    @Column(nullable=false, length=100)
    private LocalDateTime brdWrtDt;

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
