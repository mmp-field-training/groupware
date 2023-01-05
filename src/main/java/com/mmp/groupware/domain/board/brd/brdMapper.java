package com.mmp.groupware.domain.board.brd;

import com.mmp.groupware.web.board.dto.brdDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface brdMapper {

    // 게시판 수 조회
    int getBrdCnt(Map<String, Object> search);

    // 게시판 목록 조회
    List<brdDto> getBrdList(Map<String, Object> search);

    // 게시판 상세 조회
    brdDto getBrdDetail(Long brdNo);

}

