package com.mmp.groupware.domain.business.bsnComm;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.business.dto.bsnCommDto;
import com.mmp.groupware.web.business.dto.bsnReferDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class bsnCommService {
	
	private final sessionUtil sess;
	
	private final bsnCommMapper bcMapper;
	private final bsnCommRepository bcRepo;
	
	// 댓글 리스트 조회
	public List<bsnCommDto> getBsnCommList(long bsnNo){
		return bcMapper.getBsnCommList(bsnNo);
	}
	
	// 댓글 등록
	public Map<String, Object> addBsnComm(Map<String, Object> param, HttpServletRequest request){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			bsnComm bc = bsnComm.builder()
					.bsnNo(Long.parseLong(param.get("bsnNo").toString()))
					.bsnCommCont(param.get("bsnCommCont").toString())
					.bsnCommWrtDt(LocalDateTime.now())
					.bsnCommWrtStfNo(Long.parseLong(sess.sessionGet(request, "stfNo")))
					.createDt(LocalDateTime.now())
					.build();
			
			bcRepo.save(bc);

			result.put("code", "success");
			result.put("msg", "댓글을 등록하였습니다.");
			result.put("url", "/bsn/detail?bsnNo="+Long.parseLong(param.get("bsnNo").toString()));
		}
		catch(Exception e) {
			e.printStackTrace();
			result.put("code", "fail");
			result.put("msg", "댓글 등록에 실패하였습니다");
		}
		return result;
	}
	
	
	// 댓글 삭제
	public Map<String, Object> deleteBsnComm(Map<String, Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		
		try {
			Optional<bsnComm> optBc = bcRepo.findById(Long.parseLong(param.get("bsnCommNo").toString()));
			
			if(!optBc.isEmpty()) {
				bsnComm bc = optBc.get();
				bc.setDeleteDt(LocalDateTime.now());
				bcRepo.save(bc);

				result.put("code", "success");
				result.put("msg", "댓글을 등록하였습니다.");
				result.put("url", "/bsn/detail?bsnNo="+Long.parseLong(param.get("bsnNo").toString()));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			result.put("code", "fail");
			result.put("msg", "댓글 삭제에 실패하였습니다");
		}
		
		return result;
	}
	
}

















