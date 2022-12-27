package com.mmp.groupware.domain.commute;

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
@Table(name="commute")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate

public class commute {
    //출퇴근 여부 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atteNo;

    //사원등록번호
    @Column(nullable=false)
    private Long stfNo;

    //출근 시각
    @Column(nullable = true, length = 10)
    private String atteYn;

    //퇴근 시각
    @Column(nullable = true)
    private LocalDateTime atteTime;

    //퇴근시각
    @Column(nullable = true)
    private LocalDateTime leavedTime;

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

