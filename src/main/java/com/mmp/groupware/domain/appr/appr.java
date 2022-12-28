package com.mmp.groupware.domain.appr;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="appr")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class appr {

    // 기안 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apprNo;

    // 전자결재 구분 번호
    @Column(nullable=false)
    private Long apprDivNo;

    // 기안 등록 사원 등록번호
    @Column(nullable=false)
    private Long apprStfNo;

    // 기안 제목
    @Column(nullable=false, length = 100)
    private String apprTit;

    // 기안 내용
    @Column(columnDefinition="TEXT", nullable=false)
    private String apprCont;

    // 기안 작성일자
    @Column(nullable=false, length=100)
    private LocalDateTime apprWrtDt;

    // 기안 결재 상태
    @Column(nullable=false)
    private int apprState;

    // 반려 사유
    @Column(nullable=false)
    private String apprReject;

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
