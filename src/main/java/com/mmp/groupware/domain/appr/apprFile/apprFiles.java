package com.mmp.groupware.domain.appr.apprFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="apprFiles")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class apprFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apprFilesNo;

    // 기안 등록번호
    @Column(nullable=false)
    private Long apprNo;

    // 파일 등록번호
    @Column(nullable=false)
    private Long atcNo;

}
