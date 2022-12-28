package com.mmp.groupware.web.commute;

import lombok.*;

import javax.websocket.Decoder;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class commuteAddDto {

    private Long stfNo;
}
