package com.mmp.groupware.domain.board.brd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.attachment.attachRepository;
import com.mmp.groupware.domain.attachment.attachment;
import com.mmp.groupware.domain.board.brdFile.brdFiles;
import com.mmp.groupware.domain.board.brdFile.brdFilesRepository;
import com.mmp.groupware.util.fileUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.board.dto.brdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class brdService {

    private final fileUtil fileutil;
    private final sessionUtil sess;

    private final brdMapper brdMapper;
    private final brdRepository brdRepo;

    // 파일 관련
    private final attachRepository atcRepo;
    private final brdFilesRepository brdFilesRepo;

    // 게시판 수 조회
    public int getBrdCnt(Map<String, Object> search) {
        return brdMapper.getBrdCnt(search);
    }

    // 게시판 목록 조회
    public List<brdDto> getBrdList(Map<String, Object> search) {
        return brdMapper.getBrdList(search);
    }

    // 게시판 상세 조회
    public brdDto getBrdDetail(Long brdNo) {
        brdDto brdDto = null;
        try {
            brdDto = brdMapper.getBrdDetail(brdNo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return brdDto;
    }

    // 게시판 삭제
    public Map<String, Object> deleteBrd(Map<String, Object> param) {
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            Optional<brd> optBrd = brdRepo.findById(Long.parseLong(param.get("brdNo").toString()));

            if(optBrd.isPresent()) {
                brd brd = optBrd.get();
                brd.setDeleteDt(LocalDateTime.now());
                brdRepo.save(brd);

                result.put("code", "success");
                result.put("url", "/brd/list");
            }
        } catch (Exception e) {
            result.put("code", "fail");
        }
        return result;
    }

    // 게시판 등록
    public Map<String, Object> addBrd(String insertForm, List<MultipartFile> files, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> param = mapper.readValue(insertForm, Map.class);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            // 1. 게시판 글 등록
            brd bd = brd.builder()
                    .brdTit(param.get("brdTit").toString().trim())
                    .brdCont(param.get("brdCont").toString())
                    .brdNtcYn(param.get("brdNtcYn").toString())
                    .brdWrtDt(LocalDateTime.now())
                    .createDt(LocalDateTime.now())
                    .brdWrtStfNo(Long.parseLong(sess.sessionGet(request, "stfNo")))
                    .build();

            Long brdNo = brdRepo.save(bd).getBrdNo();

            // 2. 게시판 글 파일 등록
            if(files != null) {
                for(int i=0; i<files.size(); i++) {
                    String ftpName = fileutil.uploadFile(files.get(i), "brdFiles");
                    attachment atc = attachment.builder()
                            .atcOriNm(files.get(i).getOriginalFilename())
                            .atcUpNm(ftpName)
                            .createDt(LocalDateTime.now())
                            .build();

                    Long atcNo = atcRepo.save(atc).getAtcNo();

                    brdFiles brdFile = brdFiles.builder()
                            .brdNo(brdNo)
                            .atcNo(atcNo)
                            .build();

                    brdFilesRepo.save(brdFile);
                }
            }

            result.put("code", "success");
            result.put("msg", "게시판을 등록하였습니다.");
            result.put("url", "/brd/detail?brdNo="+brdNo);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.put("code", "fail");
            result.put("msg", "게시판 등록에 실패하였습니다");
        }

        return result;
    }

    // 게시판 수정
    public Map<String, Object> editBrd(Map<String, Object> updateForm, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            Optional<brd> optBrd = brdRepo.findById(Long.parseLong(updateForm.get("brdNo").toString()));

            if(optBrd.isEmpty()) {
                result.put("code", "fail");
                result.put("msg", "게시판 정보 수정에 실패하였습니다.");
            }

            brd bd = optBrd.get();

            bd.setUpdateDt(LocalDateTime.now());
            bd.setBrdTit(updateForm.get("brdTit").toString().trim());
            bd.setBrdCont(updateForm.get("brdCont").toString());
            bd.setBrdNtcYn(updateForm.get("brdNtcYn").toString());
            bd.setBrdWrtDt(LocalDateTime.now());

            brdRepo.save(bd);

            result.put("code", "success");
            result.put("url", "brd/detail?brdNo=" + updateForm.get("brdNo"));

        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "fail");
            result.put("msg", "게시판 정보 수정에 실패하였습니다.");
        }

        return result;
    }

}
