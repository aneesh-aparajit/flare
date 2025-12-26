package io.flare.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DocumentDTO {

    private String term;
    private String documentID;
    private Long termFrequency;
    private List<Long> offsets;

}
