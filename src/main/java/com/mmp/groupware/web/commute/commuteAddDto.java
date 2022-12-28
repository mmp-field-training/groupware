package com.mmp.groupware.web.commute;

<<<<<<< HEAD
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


=======
import lombok.*;

import javax.websocket.Decoder;
import java.time.LocalDateTime;
>>>>>>> 09d969a84ad138b9948aea681f7ff5a86d008c69
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD

public class commuteAddDto {
    private Long atteNo;

    private Long stfNo;

    private String atteYn;

    private LocalDateTime atteTime;

    private LocalDateTime leavedTime;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    private LocalDateTime deleteDt;

}
=======
public class commuteAddDto {

    private Long stfNo;
}
>>>>>>> 09d969a84ad138b9948aea681f7ff5a86d008c69
