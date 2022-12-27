package com.mmp.groupware.domain.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface calendarRepository extends JpaRepository<calendar, Long> {

    @Query(value="select * from calendar where delete_dt is null", nativeQuery = true)
    List<calendar> findAllWhereDeleteDtIsNull();

}
