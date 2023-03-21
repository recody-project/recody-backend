package com.recody.recodybackend.insight.features.getusersrecordcount;

import com.recody.recodybackend.Monthly;
import lombok.*;

/**
 * 특정 유저가 특정 월에 작성한 감상평의 개수를 가져온다.
 * @author motive
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetUsersRecordCount {
   
   private Long userId;
   private Monthly monthly;
}
