package com.recody.recodybackend.drama.data.drama;

import com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator;
import com.recody.recodybackend.drama.data.title.DramaTitleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "drama")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DramaEntity {
    
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "drama_seq")
    @GenericGenerator( name = "drama_seq",
                       strategy = "com.recody.recodybackend.commonbootutils.data.CustomSequenceIdGenerator",
                       parameters = {@Parameter( name = CustomSequenceIdGenerator.INCREMENT_PARAM, value = "50"),// high-low 최적화
                                     @Parameter( name = CustomSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "dra-"),
                                     @Parameter( name = CustomSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%d")})
    private String id;
    
    @OneToOne(mappedBy = "drama")
    private DramaTitleEntity title;
    
    private String imageUrl;
    
}
