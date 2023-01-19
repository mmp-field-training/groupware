package com.mmp.groupware.domain.takeoff;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;


@Entity
@Table(name="takeoff")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class takeoff {
    //연차 사용 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long takeoffNo;//

    //    연차 사용 사원 등록번호
    @Column(nullable = false)
    private Long takeoffStfNo;//
    //    연차 사용 사원 부서번호
    @Column(nullable = true)
    private Long takeoffdeptNo;//

    //연차 사용 명
    @Column(nullable = false)
    private String takeoffNm;//

    //  연차 내용
    @Column(columnDefinition="TEXT", nullable=false)
    private String takeoffCont;//

    //      연차 시작일자
    @Column(nullable=false, length=100)
    private LocalDate takeoffStartDt;//
    //    연차 종료일자
    @Column(nullable=false, length=100)
    private LocalDate takeoffEndDt;//

    //    승인 여부
    @Column(nullable=true)
    private String takeoffAppryn;//

    //    총 사용일수
    @Column(nullable=true)
    private String takeoffTotal;//

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
