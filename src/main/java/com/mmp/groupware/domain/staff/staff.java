package com.mmp.groupware.domain.staff;

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
@Table(name="staff")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class staff {

	// 사원 등록번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stfNo;
	
	// 권한 등록번호
	@Column(nullable=true)
	private Long authNo;
	
	// 부서 등록번호
	@Column(nullable=true)
	private Long deptNo;
	
	// 직급 등록번호
	@Column(nullable=true)
	private Long rnkNo;
	
	// 사원 아이디
	@Column(nullable=false, unique = true, length = 50)
	private String stfId;
	
	// 사원 비밀번호 
	@Column(nullable=false, length = 300)
	private String stfPwd; 
	
	// 사원 명
	@Column(nullable=false, length=50)
	private String stfNm;
	
	// 사원 이메일
	@Column(nullable=true, length=100)
	private String stfEmail;
	
	// 사원 휴대폰번호
	@Column(nullable=true, length = 15)
	private String stfPhone;
	
	// 사원 생년월일
	@Column(nullable=false)
	private LocalDateTime stfBirth; 
	
	// 사원 성별
	@Column(nullable=true, length = 15)
	private String stfGender;

	// 사원 연차 수
	@Column(nullable=true)
	private Float stfAnnual;

	// 사원 입사일자
	@Column(nullable = true)
	private LocalDateTime stfHiredDt;

	// 사원 퇴사일자
	@Column(nullable = true)
	private LocalDateTime stfLeavedDt;
	
	// 사원 프로필사진
	@Column(nullable=true, length = 300)
	private String stfProfile;
	
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










































