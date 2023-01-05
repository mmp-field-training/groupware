package com.mmp.groupware.domain.plcMnyInfo.pmi;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="pmi")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class pmi {

    // 공금정보 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pmiNo;

    // 공금정보 등록 사원의 사원 등록번호
    @Column(nullable=true)
    private Long pmiStfNo;

    // 공금정보 제목
    @Column(nullable=false, length = 100)
    private String pmiTit;

    // 공금정보 내용
    @Column(columnDefinition="TEXT", nullable=false)
    private String pmiCont;

    // 공금정보 추가일자
    @Column(nullable=false, length=100)
    private LocalDateTime pmiDt;

    // 공금정보 작성일자
    @Column(nullable=false, length=100)
    private LocalDateTime pmiWrtDt;

    // 공금 추가 여부 , 공금 추가시 금액을 +함
    @Column(nullable=false)
    private String pmiAddYn;

    // 공금 금액
    @Column(nullable = false)
    private int pmiPay;

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
