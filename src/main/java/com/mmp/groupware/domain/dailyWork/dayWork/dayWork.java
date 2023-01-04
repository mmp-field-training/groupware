package com.mmp.groupware.domain.dailyWork.dayWork;

import lombok.*;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dayWork")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class dayWork {

    // 일일보고 등록 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayWorkNo;

    // 일일보고 작성 사원 등록 번호
    @Column(nullable = false)
    private Long dayWorkWrtStfNo;

    // 일일보고 제목
    @Column(nullable = false, length = 100)
    private String dayWorkTit;

    // 일일보고 내용
    @Column(columnDefinition="TEXT", nullable = false)
    private String dayWorkCont;

    // 일일보고 작성일자
    @Column(nullable = false, length = 100)
    private LocalDateTime dayWorkWrtDt;

    // 생성일자
    @Column(nullable = false)
    private LocalDateTime createDt;

    // 수정일자
    @Column(nullable = true)
    private LocalDateTime updateDt;

    // 삭제일자
    @Column(nullable = true)
    private LocalDateTime deleteDt;
}
