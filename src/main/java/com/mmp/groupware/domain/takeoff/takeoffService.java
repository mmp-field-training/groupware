package com.mmp.groupware.domain.takeoff;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.attachment.attachment;
import com.mmp.groupware.domain.business.bsn.bsn;
import com.mmp.groupware.domain.business.bsnFile.bsnFiles;
import com.mmp.groupware.domain.business.bsnRefer.bsnRefer;
import com.mmp.groupware.domain.staff.staff;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.business.dto.bsnDto;
import com.mmp.groupware.web.staff.dto.staffDto;
import com.mmp.groupware.web.takeoff.takeoffAddDto;
import com.mmp.groupware.web.takeoff.takeoffDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class takeoffService {
    private final sessionUtil sess;
    private final takeoffMapper tofMapper;
    private final takeoffRepository tofRepo;

    public Map<String, Object> addTof(takeoffAddDto addForm, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            ObjectMapper mapper = new ObjectMapper();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            System.out.println("service");
            // 1. 연차 글 등록
            takeoff tf = takeoff.builder()
                    .takeoffNm(addForm.getTakeoffNm())
                    .takeoffCont(addForm.getTakeoffCont())
                    .takeoffStartDt(addForm.getTakeoffStartDt())
                    .takeoffEndDt(addForm.getTakeoffEndDt())
                    .takeoffAppryn(addForm.getTakeoffAppryn())
                    .createDt(LocalDateTime.now())
                    .updateDt(null)
                    .deleteDt(null)
                    .takeoffStfNo(Long.parseLong(sess.sessionGet(request, "stfNo")))
                    .build();

            Long takeoffNo = tofRepo.save(tf).getTakeoffNo();

            result.put("code", "success");
            result.put("msg", "연차를 등록하였습니다.");
            result.put("url", "/takeoff/detail?tofNo=" + takeoffNo);

        } catch (Exception e) {
            System.out.println(1 + e.getMessage());
            result.put("code", "fail");
            result.put("msg", "업무 등록에 실패하였습니다");
        }

        return result;
    }

    public takeoffDto getTofDetail(long tofNo) {
        takeoffDto tofDto = null;
        try {
            tofDto = tofMapper.getTakeoffDetail(tofNo);
        } catch (Exception e) {
            System.out.println(1 + e.getMessage());
            return null;
        }
        return tofDto;

    }

    public int getTofCnt(Map<String, Object> search) {
        return tofMapper.getTofCnt(search);
    }

    public List<takeoffDto> getTofList(Map<String, Object> search) {
        return tofMapper.getTofList(search);
    }

    /*public Map<String, Object> deleteTakeoff(Map<String, Object> param) {
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            Optional<staff> optStf = stfRepo.findById(Long.parseLong(param.get("stfNo").toString()));

            if(optStf.isPresent()) {
                staff stf = optStf.get();
                stf.setDeleteDt(LocalDateTime.now());
                stfRepo.save(stf);

                result.put("code", "success");
                result.put("url", "/staff/list");
            }

        }catch(Exception e) {
            result.put("code", "fail");
        }
        return result;
    }*/

    public Map<String, Object> updateTakeoff(takeoffDto updateForm, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            Optional<takeoff> optTof = tofRepo.findById(updateForm.getTofNo());
            // 존재하지 않을 시
            if (optTof.isEmpty()) {
                result.put("code", "fail");
                result.put("msg", "연차 정보 수정에 실패하였습니다.");
            }

            takeoff tof = optTof.get();

            tof.setUpdateDt(LocalDateTime.now());
            tof.setTakeoffNm(updateForm.getTakeoffNm());
            tof.setTakeoffNo(updateForm.getTofNo());
            tof.setTakeoffCont(updateForm.getTakeoffCont());
            tof.setTakeoffStartDt(updateForm.getTakeoffStartDt());
            tof.setTakeoffEndDt(updateForm.getTakeoffEndDt());
            tof.setTakeoffAppryn(updateForm.getTakeoffAppryn());
            tof.setCreateDt(updateForm.getCreateDt());
            tof.setDeleteDt(updateForm.getDeleteDt());


            tofRepo.save(tof);

            result.put("code", "success");
            result.put("url", "/staff/detail?tofNo=" + updateForm.getTofNo());

        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "fail");
            result.put("msg", "회원정보 수정에 실패하였습니다.");
        }

        return result;
    }
}

