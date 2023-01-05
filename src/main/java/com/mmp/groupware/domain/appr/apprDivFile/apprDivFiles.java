package com.mmp.groupware.domain.appr.apprDivFile;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name="apprFiles")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class apprDivFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apprDivFilesNo;

    // 전자결재 구분 등록번호
    @Column(nullable=false)
    private Long apprDivNo;

    // 파일 등록번호
    @Column(nullable=false)
    private Long atcNo;

}
