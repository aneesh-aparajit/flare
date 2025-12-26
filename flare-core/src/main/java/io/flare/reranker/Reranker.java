package io.flare.reranker;

import io.flare.dto.DocumentDTO;

import java.util.List;

public interface Reranker {

    List<DocumentDTO> rerank(List<DocumentDTO> documentDTOS);

}
