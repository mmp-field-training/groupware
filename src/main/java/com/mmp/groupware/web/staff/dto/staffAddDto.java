package com.mmp.groupware.web.staff.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class staffAddDto {

	private Long stfNo;
	
	private Long authNo;
	
	private Long deptNo;
	
	private Long rnkNo;
	
	private String stfId;
	
	private String stfPwd; 

	private String stfPwdConf; 
	
	private String stfNm;
	
	private String stfEmail;
	
	private String stfPhone;
	
	private LocalDate stfBirth; 
	
	private String stfGender;
	
	private Float stfAnnual;
	
	private LocalDate stfHiredDt;
	
	private LocalDate stfLeavedDt;
	
	private String stfProfile;
	
	private LocalDateTime createDt; 
	
	private LocalDateTime updateDt;
	
	private LocalDateTime deleteDt;
}

