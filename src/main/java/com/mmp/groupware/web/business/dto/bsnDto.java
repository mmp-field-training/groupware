package com.mmp.groupware.web.business.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mmp.groupware.web.staff.dto.staffDto;

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
public class bsnDto {

	private int rownum; 

	private Long bsnNo;

	private String bsnTit;

	private String bsnCont;
	
	private Long bsnStfNo;

	private LocalDate bsnWrtDt;
	
	private String bsnWrtNm;

	private LocalDate bsnStartDt;
	
	private LocalDate bsnEndDt;	

	private LocalDateTime createDt; 

	private LocalDateTime updateDt;

	private LocalDateTime deleteDt;
	
}
