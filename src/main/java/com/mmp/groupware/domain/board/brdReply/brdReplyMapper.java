package com.mmp.groupware.domain.board.brdReply;

import com.mmp.groupware.web.board.dto.brdReplyDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface brdReplyMapper {

    List<brdReplyDto> getBrdReplyList(Long brdNo);

}
