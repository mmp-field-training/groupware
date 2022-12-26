package com.mmp.groupware.web.rnk;

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
public class rnkDto {

	private int rownum; 
	
	private Long rnkNo;
	
	private String rnkNm; 
	
	private LocalDateTime createDt; 
	
	private LocalDateTime updateDt;
	
	private LocalDateTime deleteDt;
}

