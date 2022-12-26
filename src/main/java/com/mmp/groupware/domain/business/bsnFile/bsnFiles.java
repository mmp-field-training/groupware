package com.mmp.groupware.domain.business.bsnFile;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.mmp.groupware.domain.business.bsn.bsn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="bsnFiles")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class bsnFiles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bsnFilesNo;
	
	// 업무 등록번호
	@Column(nullable=false)
	private Long bsnNo;

	// 파일 등록번호
	@Column(nullable=false)
	private Long atcNo;
	
	
}



















