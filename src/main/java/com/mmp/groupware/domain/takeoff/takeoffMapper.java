package com.mmp.groupware.domain.takeoff;

import com.mmp.groupware.web.takeoff.takeoffDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
@Repository
public interface takeoffMapper {

    int getTofCnt(Map<String, Object> search);
    List<takeoffDto> getTofList(Map<String, Object> search);
    takeoffDto getTakeoffDetail(long tofNo);

}