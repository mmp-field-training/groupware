package com.mmp.groupware.domain.compSpl.atclDiv;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.servlet.tags.form.TextareaTag;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="atclDiv")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class atclDiv {

    // 물품그룹 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atclDivNo;

    // 물품그룹 명
    @Column(nullable = false, length = 100)
    private String atclDivNm;

    // 물품그룹 사용여부
    @Column(nullable = false, length = 2)
    private String atclDivUsedYn;

    // 물품그룹 설명
    @Column(columnDefinition = "TEXT", nullable = false)
    private String atclDivComm;

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
