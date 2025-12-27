package io.flare.manager;

import java.util.List;

import io.flare.dto.AddTermDTO;
import io.flare.dto.DocumentDTO;
import io.flare.reranker.RerankingAlgo;

public interface IndexManager {

    void createIndex(String index);

    void addTerm(String index, AddTermDTO addTermDTO);

    List<DocumentDTO> search(String index, String term, RerankingAlgo algo);

}
