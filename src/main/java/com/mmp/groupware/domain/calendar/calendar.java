package com.mmp.groupware.domain.calendar;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
@Table(name="calendar")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class calendar {

    // 일정 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calNo;

    // 일정 명
    @Column(nullable=false, length=100)
    private String calNm;

    // 일정 내용
    @Column(nullable=true, length=500)
    private String calContent;

    // 일정 날짜
    @Column(nullable = false)
    private LocalDateTime calDate;

    // 일정 시작 시간
    @Column(nullable = false)
    private LocalTime calStartTime;

    // 일정 종료 시간
    @Column(nullable = false)
    private LocalTime calEndTime;

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
