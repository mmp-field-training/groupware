package com.mmp.groupware.domain.business.bsn;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.attachment.attachRepository;
import com.mmp.groupware.domain.attachment.attachment;
import com.mmp.groupware.domain.business.bsnFile.bsnFiles;
import com.mmp.groupware.domain.business.bsnFile.bsnFilesRepository;
import com.mmp.groupware.domain.business.bsnRefer.bsnRefer;
import com.mmp.groupware.domain.business.bsnRefer.bsnReferMapper;
import com.mmp.groupware.domain.business.bsnRefer.bsnReferRepository;
import com.mmp.groupware.domain.staff.staff;
import com.mmp.groupware.util.fileUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.business.dto.bsnDto;
import com.mmp.groupware.web.business.dto.bsnReferDto;
import com.mmp.groupware.web.staff.dto.staffDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class bsnService {
	
	private final fileUtil fileutil;
	private final sessionUtil sess;
	
	private final bsnMapper bsnMapper;
	private final bsnRepository bsnRepo;
	
	// 파일 관련
	private final attachRepository atcRepo;
	private final bsnFilesRepository bsnFileRepo;
	
	// 참조 관련
	private final bsnReferMapper bsnRefMapper;
	private final bsnReferRepository bsnRefRepo;
	
	
	// 업무 수 조회
	public int getBsnCnt(Map<String, Object> search) {
		return bsnMapper.getBsnCnt(search);
	}
	
	// 업무 목록 조회
	public List<bsnDto> getBsnList(Map<String, Object> search){
		return bsnMapper.getBsnList(search);
	}
	
	// 업무 상세 조회 
	public bsnDto getBsnDetail(long bsnNo) {
		bsnDto bsnDto = null;
		try {
			bsnDto = bsnMapper.getBsnDetail(bsnNo);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return bsnDto;
	}
	
	// 업무 삭제
	public Map<String, Object> deleteBsn(Map<String, Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Optional<bsn> optBsn = bsnRepo.findById(Long.parseLong(param.get("bsnNo").toString()));
			
			if(optBsn.isPresent()) {
				bsn bs = optBsn.get();
				bs.setDeleteDt(LocalDateTime.now());
				bsnRepo.save(bs);
				
				result.put("code", "success");
				result.put("url", "/bsn/list");
			}
			
		}catch(Exception e) {
			result.put("code", "fail");
		}
		return result;
	}
	
	// 업무 등록
	public Map<String, Object> addBsn(String insertForm, List<MultipartFile> files, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> param = mapper.readValue(insertForm, Map.class);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
			
			LocalDate bsnStartDt = LocalDate.parse((CharSequence) param.get("bsnStartDt"));
			LocalDate bsnEndDt = LocalDate.parse((CharSequence) param.get("bsnEndDt"));
			
			// 1. 업무 글 등록
			bsn bs = bsn.builder()
					.bsnTit(param.get("bsnTit").toString().trim())
					.bsnCont(param.get("bsnCont").toString())
					.bsnStartDt(bsnStartDt.atTime(0, 0, 0, 0))
					.bsnEndDt(bsnEndDt.atTime(0, 0, 0, 0))
					.bsnWrtDt(LocalDateTime.now())
					.createDt(LocalDateTime.now())
					.bsnStfNo(Long.parseLong(sess.sessionGet(request, "stfNo")))
					.build();
			
			Long bsnNo = bsnRepo.save(bs).getBsnNo();
			
			// 2. 업무 글 파일 등록
			if(files != null) {
				for(int i=0; i<files.size(); i++) {
					String ftpName = fileutil.uploadFile(files.get(i), "bsnFiles");
					attachment atc = attachment.builder()
							.atcOriNm(files.get(i).getOriginalFilename())
							.atcUpNm(ftpName)
							.createDt(LocalDateTime.now())
							.build();
					
					Long atcNo = atcRepo.save(atc).getAtcNo();
					
					bsnFiles bsnFile = bsnFiles.builder()
							.bsnNo(bsnNo)
							.atcNo(atcNo)
							.build();
				
					bsnFileRepo.save(bsnFile);
				}
			}
			
			// 3. 참조인원 정보 등록
			List<Map<String,Object>> currRefList = (List<Map<String, Object>>) param.get("currRefList");
			
			for(int j=0; j<currRefList.size(); j++) {
				bsnRefer brf = bsnRefer.builder()
						.bsnNo(bsnNo)
						.bsnRefStfNo(Long.parseLong(currRefList.get(j).get("stfNo").toString()))
						.createDt(LocalDateTime.now())
						.build();
				
				bsnRefRepo.save(brf);
			}
			
			System.out.println(currRefList);
			
			result.put("code", "success");
			result.put("msg", "업무를 등록하였습니다.");
			result.put("url", "/bsn/detail?bsnNo="+bsnNo);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			result.put("code", "fail");
			result.put("msg", "업무 등록에 실패하였습니다");
		}
		
		return result;
	}
	
	// 업무 수정
	public Map<String, Object> editBsn(Map<String, Object> updateForm, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Optional<bsn> optBsn = bsnRepo.findById(Long.parseLong(updateForm.get("bsnNo").toString()));
					
			if(optBsn.isEmpty()) {
				result.put("code","fail");
				result.put("msg","업무 정보 수정에 실패하였습니다.");
			}
			
			bsn bs = optBsn.get();
			LocalDate bsnStartDt = LocalDate.parse((CharSequence) updateForm.get("bsnStartDt"));
			LocalDate bsnEndDt = LocalDate.parse((CharSequence) updateForm.get("bsnEndDt"));
			
			bs.setUpdateDt(LocalDateTime.now());
			bs.setBsnCont(updateForm.get("bsnCont").toString());
			bs.setBsnTit(updateForm.get("bsnTit").toString());
			bs.setBsnStartDt(bsnStartDt.atTime(0, 0, 0, 0));
			bs.setBsnEndDt(bsnEndDt.atTime(0, 0, 0, 0));
			
			bsnRepo.save(bs);
			
			if((boolean) updateForm.get("refChgYn")) {
				// 기존 참조 인원 정보 delete
				List<bsnReferDto> prevRefList = bsnRefMapper.getBsnReferList(Long.parseLong(updateForm.get("bsnNo").toString()));				
				for(int i=0; i<prevRefList.size(); i++) {
					Optional<bsnRefer> optBrf = bsnRefRepo.findById(prevRefList.get(i).getBsnRefNo());
					if(optBrf.isPresent()) {
						bsnRefer brf = optBrf.get();
						brf.setDeleteDt(LocalDateTime.now());
						bsnRefRepo.save(brf);
					}
				}
				
				// 참조인원 정보 등록
				List<Map<String,Object>> currRefList = (List<Map<String, Object>>) updateForm.get("currRefList");
				
				for(int j=0; j<currRefList.size(); j++) {
					bsnRefer brf = bsnRefer.builder()
							.bsnNo(Long.parseLong(updateForm.get("bsnNo").toString()))
							.bsnRefStfNo(Long.parseLong(currRefList.get(j).get("stfNo").toString()))
							.createDt(LocalDateTime.now())
							.build();
					
					bsnRefRepo.save(brf);
				}
			}
			
			result.put("code", "success");
			result.put("url", "/bsn/detail?bsnNo=" + updateForm.get("bsnNo"));
		}catch(Exception e) {
			e.printStackTrace();
			result.put("code","fail");
			result.put("msg","업무 정보 수정에 실패하였습니다.");
		}
		
		return result;
	}
	
	
}
























