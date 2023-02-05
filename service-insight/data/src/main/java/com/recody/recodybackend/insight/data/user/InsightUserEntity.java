package com.recody.recodybackend.insight.data.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "insight_user")
@AllArgsConstructor
@NoArgsConstructor( access = lombok.AccessLevel.PROTECTED )
@Getter
@Builder
public class InsightUserEntity {

    @Id
    private Long id;
    
    private String nickname;
}
