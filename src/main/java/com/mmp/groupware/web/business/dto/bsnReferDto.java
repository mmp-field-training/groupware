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
public class bsnReferDto {

	private int rownum; 

	private Long bsnRefNo;

	private Long bsnNo;

	private Long bsnRefStfNo;

	private String bsnRefStfNm;
}
