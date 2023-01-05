package com.mmp.groupware.domain.plcMnyInfo.pmiFile;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name="pmiFiles")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class pmiFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pmiFilesNo;

    // 공금정보 등록번호
    @Column(nullable=false)
    private Long pmiNo;

    // 파일 등록번호
    @Column(nullable=false)
    private Long atcNo;
}
