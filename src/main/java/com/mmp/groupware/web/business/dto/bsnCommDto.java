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
public class bsnCommDto {

	private Long bsnCommNo;

	private Long bsnNo;

	private Long bsnCommWrtStfNo;
	
	private String bsnCommWrtStfNm;

	private LocalDate bsnCommWrtDt;
	
	private String bsnCommCont;
	
	private LocalDateTime createDt; 

	private LocalDateTime updateDt;

	private LocalDateTime deleteDt;
}
