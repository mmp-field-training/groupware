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
public class bsnFilesDto {

	private Long bsnFilesNo;

	private Long atcNo;

	private Long bsnNo;
	
	private String atcOriNm;

	private String atcUpNm;
	
	private LocalDateTime createDt; 

	private LocalDateTime updateDt;

	private LocalDateTime deleteDt;
	
}
