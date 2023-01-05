package com.mmp.groupware.domain.appr.apprDiv;

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
@Table(name="apprDiv")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class apprDiv {

    // 전자결재 구분번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apprDivNo;

    // 전자결재 구분 명
    @Column(nullable = false)
    private String apprDivNm;

    // 전자결재 구분 등록일자
    @Column(nullable = false)
    private LocalDateTime apprDivWrtDt;

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
