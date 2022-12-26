package com.mmp.groupware.web.dept;

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
public class deptDto {

	private int rownum; 
	
	private Long deptNo;
	
	private String deptNm; 
	
	private LocalDateTime createDt; 
	
	private LocalDateTime updateDt;
	
	private LocalDateTime deleteDt;
}

