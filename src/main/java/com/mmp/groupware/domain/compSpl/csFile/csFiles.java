package com.mmp.groupware.domain.compSpl.csFile;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name="csFiles")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class csFiles {

    // 물품파일 등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long csFilesNo;

    // 물품 등록번호
    @Column(nullable=false)
    private Long csNo;

    // 파일 등록번호
    @Column(nullable=false)
    private Long atcNo;

}
