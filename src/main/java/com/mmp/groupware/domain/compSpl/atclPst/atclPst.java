package com.mmp.groupware.domain.compSpl.atclPst;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="atclPst")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class atclPst {

    // 물품위치 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atclPstNo;

    // 물품위치 명
    @Column(nullable = false, length = 100)
    private String atclPstNm;

    // 물품위치 사용여부
    @Column(nullable = false, length = 2)
    private String atclPstUsedYn;

    // 물품위치 설명
    @Column(columnDefinition = "TEXT", nullable = false)
    private String atclPstComm;

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
