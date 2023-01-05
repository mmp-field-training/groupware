package com.mmp.groupware.domain.board.brdFile;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name="brdFiles")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class brdFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brdFilesNo;

    // 게시판 등록번호
    @Column(nullable=false)
    private Long brdNo;

    // 파일 등록번호
    @Column(nullable=false)
    private Long atcNo;

}
