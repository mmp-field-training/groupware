package com.mmp.groupware.domain.compSpl.cs;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="cs")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class cs {

    // 비품 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long csNo;

    // 비품그룹 등록번호
    @Column(nullable = false)
    private Long atclDivNo;

    // 물품 위치번호
    @Column(nullable = false)
    private Long atclPstNo;

    // 비품 명
    @Column(nullable = false, length = 100)
    private String csNm;

    // 비품 규격
    @Column(nullable = true, length = 100)
    private String csStd;

    // 비품 개수
    @Column(nullable = true)
    private Long csCnt;

    // 비품 구매일자
    @Column(nullable = true)
    private LocalDateTime csPcsDt;

    // 비품 구매 가격
    @Column(nullable = true)
    private Long csPcsPay;

    // 비품 코멘트
    @Column(columnDefinition="TEXT", nullable = true)
    private String csComm;

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
