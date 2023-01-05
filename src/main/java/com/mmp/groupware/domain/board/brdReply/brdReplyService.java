package com.mmp.groupware.domain.board.brdReply;

import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.board.dto.brdReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class brdReplyService {

    private final sessionUtil sess;

    private final brdReplyMapper brMapper;
    private final brdReplyRepository brRepo;

    // 댓글 리스트 조회
    public List<brdReplyDto> getBrdReplyList(Long brdNo) {
        return brMapper.getBrdReplyList(brdNo);
    }

    // 댓글 등록
    public Map<String, Object> addBrdReply(Map<String, Object> param, HttpServletRequest request) {
        Map<String,Object> result = new HashMap<String, Object>();

        try {
            brdReply br = brdReply.builder()
                    .brdNo(Long.parseLong(param.get("brdNo").toString()))
                    .brdReplyCont(param.get("brdReplyCont").toString())
                    .brdReplyWrtDt(LocalDateTime.now())
                    .brdReplyWrtStfNo(Long.parseLong(sess.sessionGet(request, "stfNo")))
                    .createDt(LocalDateTime.now())
                    .build();

            brRepo.save(br);

            result.put("code", "success");
            result.put("msg", "댓글을 등록하였습니다.");
            result.put("url", "/brd/detail?brdNo="+Long.parseLong(param.get("brdNo").toString()));

        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "fail");
            result.put("msg", "댓글 등록에 실패하였습니다");
        }

        return result;

    }

    // 댓글 삭제
    public Map<String, Object> deleteBrdReply(Map<String, Object> param) {
        Map<String,Object> result = new HashMap<String, Object>();

        try {
            Optional<brdReply> optBr = brRepo.findById(Long.parseLong(param.get("brdReplyNo").toString()));

            if(!optBr.isEmpty()) {
                brdReply br = optBr.get();
                br.setDeleteDt(LocalDateTime.now());
                brRepo.save(br);

                result.put("code", "success");
                result.put("msg", "댓글을 등록하였습니다.");
                result.put("url", "/brd/detail?brdNo="+Long.parseLong(param.get("brdNo").toString()));
            }

        } catch(Exception e) {
            e.printStackTrace();
            result.put("code", "fail");
            result.put("msg", "댓글 삭제에 실패하였습니다");
        }

        return result;

    }

}
