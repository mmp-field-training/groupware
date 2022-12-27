package com.mmp.groupware.domain.calendar.calRefer;

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
@Table(name="calRefer")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class calRefer {

    // 일정 참조 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calRefNo;

    // 일정 등록번호
    @Column(nullable=true)
    private Long calNo;

    // 일정 등록 사원의 사원 등록번호
    @Column(nullable=true)
    private Long calRefStfNo;

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
