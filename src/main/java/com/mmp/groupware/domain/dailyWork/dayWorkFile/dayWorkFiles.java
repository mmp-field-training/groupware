package com.mmp.groupware.domain.dailyWork.dayWorkFile;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "dayWorkFiles")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class dayWorkFiles {

    // 일일보고 파일 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayWorkFilesNo;

    // 일일보고 등록번호
    @Column(nullable = false)
    private Long dayWorkNo;

    // 파일 등록번호
    @Column(nullable = false)
    private Long atcNo;
}
