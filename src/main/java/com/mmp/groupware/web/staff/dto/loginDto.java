package com.mmp.groupware.web.staff.dto;

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
public class loginDto {
	
	// 사원 아이디
	private String stfId;
	
	// 사원 비밀번호
	private String stfPwd;

}
