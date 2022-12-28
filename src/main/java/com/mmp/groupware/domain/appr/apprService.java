package com.mmp.groupware.domain.appr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.appr.apprFile.apprFiles;
import com.mmp.groupware.domain.appr.apprFile.apprFilesRepository;
import com.mmp.groupware.domain.appr.apprRefer.apprRefer;
import com.mmp.groupware.domain.appr.apprRefer.apprReferMapper;
import com.mmp.groupware.domain.appr.apprRefer.apprReferRepository;
import com.mmp.groupware.domain.attachment.attachRepository;
import com.mmp.groupware.domain.attachment.attachment;
import com.mmp.groupware.util.fileUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.appr.dto.apprDto;
import com.mmp.groupware.web.appr.dto.apprReferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class apprService {

    private final fileUtil fileutil;
    private final sessionUtil sess;

    private final apprMapper apprMapper;
    private final apprRepository apprRepo;

    // 파일 관련
    private final attachRepository atcRepo;
    private final apprFilesRepository apprFileRepo;

    // 참조 관련
    private final apprReferMapper apprRefMapper;
    private final apprReferRepository apprRefRepo;

    // 기안 수 조회
    public int getApprCnt(Map<String, Object> search) {
        return apprMapper.getApprCnt(search);
    }

    // 기안 목록 조회
    public List<apprDto> getApprList(Map<String, Object> search){
        return apprMapper.getApprList(search);
    }

    // 기안 상세 조회
    public apprDto getApprDetail(long apprNo) {
        apprDto apprDto = null;
        try {
            apprDto = apprMapper.getApprDetail(apprNo);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return apprDto;
    }

    // 기안 삭제
    public Map<String, Object> deleteAppr(Map<String, Object> param){
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            Optional<appr> optAppr = apprRepo.findById(Long.parseLong(param.get("apprNo").toString()));

            if(optAppr.isPresent()) {
                appr ap = optAppr.get();
                ap.setDeleteDt(LocalDateTime.now());
                apprRepo.save(ap);

                result.put("code", "success");
                result.put("url", "/appr/list");
            }

        }catch(Exception e) {
            result.put("code", "fail");
        }
        return result;
    }

    // 기안 등록
    public Map<String, Object> addAppr(String insertForm, List<MultipartFile> files, HttpServletRequest request){
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> param = mapper.readValue(insertForm, Map.class);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            LocalDate apprDt = LocalDate.parse((CharSequence) param.get("apprDt"));

            // 1. 기안 글 등록
            appr ap = appr.builder()
                    .apprDivNo((Long) param.get("apprDivNo"))
                    .apprTit(param.get("apprTit").toString().trim())
                    .apprCont(param.get("apprCont").toString())
                    .apprDt(apprDt.atTime(0, 0, 0, 0))
                    .apprState((Integer) param.get("apprState"))
                    .apprReject(param.get("apprReject").toString())
                    .createDt(LocalDateTime.now())
                    .apprStfNo(Long.parseLong(sess.sessionGet(request, "stfNo")))
                    .build();

            Long apprNo = apprRepo.save(ap).getApprNo();

            // 2. 기안 글 파일 등록
            if(files != null) {
                for(int i=0; i<files.size(); i++) {
                    String ftpName = fileutil.uploadFile(files.get(i), "apprFiles");
                    attachment atc = attachment.builder()
                            .atcOriNm(files.get(i).getOriginalFilename())
                            .atcUpNm(ftpName)
                            .createDt(LocalDateTime.now())
                            .build();

                    Long atcNo = atcRepo.save(atc).getAtcNo();

                    apprFiles apprFile = apprFiles.builder()
                            .apprNo(apprNo)
                            .atcNo(atcNo)
                            .build();

                    apprFileRepo.save(apprFile);
                }
            }

            // 3. 참조인원 정보 등록
            List<Map<String,Object>> currRefList = (List<Map<String, Object>>) param.get("currRefList");

            for(int j=0; j<currRefList.size(); j++) {
                apprRefer apf = apprRefer.builder()
                        .apprNo(apprNo)
                        .apprRefStfNo(Long.parseLong(currRefList.get(j).get("stfNo").toString()))
                        .createDt(LocalDateTime.now())
                        .build();

                apprRefRepo.save(apf);
            }

            System.out.println(currRefList);

            result.put("code", "success");
            result.put("msg", "기안을 등록하였습니다.");
            result.put("url", "/appr/detail?apprNo="+apprNo);

        }catch (Exception e) {
            System.out.println(e.getMessage());
            result.put("code", "fail");
            result.put("msg", "기안 등록에 실패하였습니다");
        }

        return result;
    }

    // 기안 수정
    public Map<String, Object> editAppr(Map<String, Object> updateForm, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            Optional<appr> optAppr = apprRepo.findById(Long.parseLong(updateForm.get("apprNo").toString()));

            if(optAppr.isEmpty()) {
                result.put("code","fail");
                result.put("msg","기안 정보 수정에 실패하였습니다.");
            }

            appr ap = optAppr.get();
            LocalDate apprDt = LocalDate.parse((CharSequence) updateForm.get("apprDt"));

            ap.setUpdateDt(LocalDateTime.now());
            ap.setApprDivNo((Long) updateForm.get("apprDivNo"));
            ap.setApprTit(updateForm.get("apprTit").toString());
            ap.setApprCont(updateForm.get("apprCont").toString());
            ap.setApprDt(apprDt.atTime(0, 0, 0, 0));
            ap.setApprState((Integer) updateForm.get("apprState"));
            ap.setApprReject(updateForm.get("apprReject").toString());

            apprRepo.save(ap);

            if((boolean) updateForm.get("refChgYn")) {
                // 기존 참조 인원 정보 delete
                List<apprReferDto> prevRefList = apprRefMapper.getApprReferList(Long.parseLong(updateForm.get("apprNo").toString()));
                for(int i=0; i<prevRefList.size(); i++) {
                    Optional<apprRefer> optApf = apprRefRepo.findById(prevRefList.get(i).getApprRefNo());
                    if(optApf.isPresent()) {
                        apprRefer apf = optApf.get();
                        apf.setDeleteDt(LocalDateTime.now());
                        apprRefRepo.save(apf);
                    }
                }

                // 참조인원 정보 등록
                List<Map<String,Object>> currRefList = (List<Map<String, Object>>) updateForm.get("currRefList");

                for(int j=0; j<currRefList.size(); j++) {
                    apprRefer apf = apprRefer.builder()
                            .apprNo(Long.parseLong(updateForm.get("apprNo").toString()))
                            .apprRefStfNo(Long.parseLong(currRefList.get(j).get("stfNo").toString()))
                            .createDt(LocalDateTime.now())
                            .build();

                    apprRefRepo.save(apf);
                }
            }

            result.put("code", "success");
            result.put("url", "/appr/detail?apprNo=" + updateForm.get("apprNo"));

        }catch (Exception e) {
            e.printStackTrace();
            result.put("code","fail");
            result.put("msg","기안 정보 수정에 실패하였습니다.");
        }

        return result;
    }

}
