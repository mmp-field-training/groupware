package com.mmp.groupware.domain.business.bsn;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.mmp.groupware.domain.staff.staff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="bsn")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class bsn {

	// 업무 등록번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bsnNo;

	// 업무 등록 사원의 사원 등록번호
	@Column(nullable=true)
	private Long bsnStfNo;

	// 업무 제목
	@Column(nullable=false, length=100)
	private String bsnTit;

	// 업무 내용
	@Column(columnDefinition="TEXT", nullable=false) 
	private String bsnCont;
	
	// 업무 시작일자
	@Column(nullable=false, length=100)
	private LocalDateTime bsnStartDt;
	
	// 업무 종료일자
	@Column(nullable=false, length=100)
	private LocalDateTime bsnEndDt;	
	
	// 업무일지 작성일자
	@Column(nullable=false, length=100)
	private LocalDateTime bsnWrtDt;

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


































