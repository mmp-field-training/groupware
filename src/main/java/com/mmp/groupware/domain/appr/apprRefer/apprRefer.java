package com.mmp.groupware.domain.appr.apprRefer;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="apprRefer")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class apprRefer {

    // 기안 참조 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apprRefNo;

    // 기안 등록번호
    @Column(nullable=true)
    private Long apprNo;

    // 기안 등록 사원의 사원 등록번호
    @Column(nullable=true)
    private Long apprRefStfNo;

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
