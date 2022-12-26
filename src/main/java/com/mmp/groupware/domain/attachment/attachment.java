package com.mmp.groupware.domain.attachment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.mmp.groupware.domain.business.bsn.bsn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="attachment")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class attachment {
	// 파일 등록번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long atcNo;
	
	// 파일 원본명
	@Column(nullable=false, length=300)
	private String atcOriNm;
	
	// 파일 업로드 명
	@Column(nullable=false, length=300)
	private String atcUpNm;
	
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




































