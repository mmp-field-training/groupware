package com.mmp.groupware.domain.compSpl.csUsed;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="csUsed")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class csUsed {

    // 물품 사용이력 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long csUsedNo;

    // 물품 등록번호
    @Column(nullable = false)
    private Long csNo;

    // 물품 사용 사원 등록번호
    @Column(nullable = false)
    private Long csUsedStfNo;

    // 물품 사용이력 제목
    @Column(nullable = false, length = 100)
    private String csUsedTit;

    // 물품 사용이력 내용
    @Column(columnDefinition="TEXT", nullable = true)
    private String csUsedCont;

    // 물품 사용일자
    @Column(nullable = false)
    private LocalDateTime csUsedDt;

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
