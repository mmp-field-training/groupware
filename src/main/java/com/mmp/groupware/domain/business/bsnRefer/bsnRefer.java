package com.mmp.groupware.domain.business.bsnRefer;

import java.time.LocalDateTime;

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
@Table(name="bsnRefer")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class bsnRefer {

	// 업무 참조 등록번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bsnRefNo;

	// 업무 등록번호
	@Column(nullable=true)
	private Long bsnNo;
	
	// 업무 등록 사원의 사원 등록번호
	@Column(nullable=true)
	private Long bsnRefStfNo;

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


































