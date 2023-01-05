package com.mmp.groupware.domain.plcMnyInfo.pmi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.attachment.attachRepository;
import com.mmp.groupware.domain.attachment.attachment;
import com.mmp.groupware.domain.plcMnyInfo.pmiFile.pmiFiles;
import com.mmp.groupware.domain.plcMnyInfo.pmiFile.pmiFilesRepository;
import com.mmp.groupware.util.fileUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.pmi.dto.pmiDto;
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
public class pmiService {

    private final fileUtil fileutil;
    private final sessionUtil sess;

    private final pmiMapper pmiMapper;
    private final pmiRepository pmiRepo;

    // 파일 관련
    private final attachRepository atcRepo;
    private final pmiFilesRepository pmiFileRepo;

    // 공금정보 수 조회
    public int getPmiCnt(Map<String, Object> search) {
        return pmiMapper.getPmiCnt(search);
    }

    // 공금정보 목록 조회
    public List<pmiDto> getPmiList(Map<String, Object> search) {
        return pmiMapper.getPmiList(search);
    }

    // 공금정보 상세 조회
    public pmiDto getPmiDetail(long pmiNo) {
        pmiDto pmiDto = null;
        try {
            pmiDto = pmiMapper.getPmiDetail(pmiNo);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return pmiDto;
    }

    // 공금정보 삭제
    public Map<String, Object> deletePmi(Map<String, Object> param) {
        Map<String,Object> result = new HashMap<String, Object>();

        try {
            Optional<pmi> optPmi = pmiRepo.findById(Long.parseLong(param.get("pmiNo").toString()));

            if(optPmi.isPresent()) {
                pmi pm = optPmi.get();
                pm.setDeleteDt(LocalDateTime.now());
                pmiRepo.save(pm);

                result.put("code", "success");
                result.put("url", "/pmi/list");
            }

        }catch (Exception e) {
            result.put("code", "fail");
        }
        return result;
    }

    // 공금정보 등록
    public Map<String, Object> addPmi(String insertForm, List<MultipartFile> files, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> param = mapper.readValue(insertForm, Map.class);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            LocalDate pmiDt = LocalDate.parse((CharSequence) param.get("pmiDt"));
            LocalDate pmiWrtDt = LocalDate.parse((CharSequence) param.get("pmiWrtDt"));

            // 1. 공금정보 글 등록
            pmi pm = pmi.builder()
                    .pmiTit(param.get("pmiTit").toString().trim())
                    .pmiCont(param.get("pmiCont").toString())
                    .pmiDt(pmiDt.atTime(0, 0, 0, 0))
                    .pmiWrtDt(pmiWrtDt.atTime(0, 0, 0,0))
                    .pmiAddYn(param.get("pmiAddYn").toString())
                    .pmiPay((Integer) param.get("pmiPay"))
                    .createDt(LocalDateTime.now())
                    .pmiStfNo(Long.parseLong(sess.sessionGet(request, "stfNo")))
                    .build();

            Long pmiNo = pmiRepo.save(pm).getPmiNo();

            // 2. 공금정보 글 파일 등록
            if(files != null) {
                for(int i=0; i<files.size(); i++) {
                    String ftpName = fileutil.uploadFile(files.get(i), "pmiFiles");
                    attachment atc = attachment.builder()
                            .atcOriNm(files.get(i).getOriginalFilename())
                            .atcUpNm(ftpName)
                            .createDt(LocalDateTime.now())
                            .build();

                    Long atcNo = atcRepo.save(atc).getAtcNo();

                    pmiFiles pmiFile = pmiFiles.builder()
                            .pmiNo(pmiNo)
                            .atcNo(atcNo)
                            .build();

                    pmiFileRepo.save(pmiFile);
                }
            }

            result.put("code", "success");
            result.put("msg", "공금 정보를 등록하였습니다.");
            result.put("url", "/pmi/detail?pmiNo="+pmiNo);

        }catch (Exception e) {
            System.out.println(e.getMessage());
            result.put("code", "fail");
            result.put("msg", "공금정보 등록에 실패하였습니다");
        }

        return result;
    }

    // 공금정보 수정
    public Map<String, Object> editPmi(Map<String, Object> updateForm, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            Optional<pmi> optPmi = pmiRepo.findById(Long.parseLong(updateForm.get("pmiNo").toString()));

            if(optPmi.isEmpty()) {
                result.put("code","fail");
                result.put("msg","공금 정보 수정에 실패하였습니다.");
            }

            pmi pm = optPmi.get();
            LocalDate pmiDt = LocalDate.parse((CharSequence) updateForm.get("pmiDt"));

            pm.setUpdateDt(LocalDateTime.now());
            pm.setPmiTit(updateForm.get("pmiTit").toString());
            pm.setPmiCont(updateForm.get("pmiCont").toString());
            pm.setPmiDt(pmiDt.atTime(0, 0, 0, 0));
            pm.setPmiAddYn(updateForm.get("pmiAddYn").toString());
            pm.setPmiPay((Integer) updateForm.get("pmiPay"));

            pmiRepo.save(pm);

            result.put("code", "success");
            result.put("url", "/pmi/detail?pmiNo=" + updateForm.get("pmiNo"));

        }catch (Exception e) {
            e.printStackTrace();
            result.put("code","fail");
            result.put("msg","공금 정보 수정에 실패하였습니다.");
        }

        return result;
    }

}
