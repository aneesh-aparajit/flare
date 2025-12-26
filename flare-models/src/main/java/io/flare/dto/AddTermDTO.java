package io.flare.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddTermDTO {

    private final String term;
    private final String documentID;
    private final Long offset;

}
