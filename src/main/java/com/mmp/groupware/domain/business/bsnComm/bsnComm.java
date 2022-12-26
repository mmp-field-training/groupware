package com.mmp.groupware.domain.business.bsnComm;

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
@Table(name="bsnComm")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class bsnComm {

	// 코멘트 등록번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bsnCommNo;

	// 부모 업무 글 등록번호
	@Column(nullable=false)
	private Long bsnNo;
	
	// 코맨트 등록 사원 등록번호
	@Column(nullable=false)
	private Long bsnCommWrtStfNo;

	// 코맨트 내용
	@Column(columnDefinition="TEXT", nullable=false) 
	private String bsnCommCont;
	
	// 코맨트 작성일자
	@Column(nullable=false)
	private LocalDateTime bsnCommWrtDt;
	
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


































