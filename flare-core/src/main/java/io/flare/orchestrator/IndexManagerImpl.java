package io.flare.orchestrator;

import io.flare.dto.AddTermDTO;
import io.flare.dto.DocumentDTO;
import io.flare.index.TermIndex;
import io.flare.reranker.RerankerFactory;
import io.flare.reranker.RerankingAlgo;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This interface is used to manage multiple indexes for a user.
 */
@Data
public class IndexManagerImpl implements IndexManager {

    private final Map<String, TermIndex> multiIndexMap;
    private final RerankerFactory rerankerFactory;

    /**
     * Create new index if not exists, else throws exceptions.
     *
     * @param index name of the index to be created.
     */
    public void createIndex(String index) {
        Optional<TermIndex> termIndexOptional = Optional.ofNullable(multiIndexMap.get(index));
        if (termIndexOptional.isPresent()) {
            throw new RuntimeException();
        }
        TermIndex termIndex = TermIndex.builder()
                .index(new HashMap<>())
                .build();
        multiIndexMap.put(index, termIndex);
    }

    /**
     * If index doesn't exist, throws exception, else adds term to respective `TermIndex`.
     *
     * @param index      name of the index where the term is to be added
     * @param addTermDTO the DTO which contains all the details required for adding term.
     */
    public void addTerm(String index, AddTermDTO addTermDTO) {
        TermIndex termIndex = getTermIndex(index);
        termIndex.addTerm(addTermDTO.getTerm(), addTermDTO.getDocumentID(), addTermDTO.getOffset());
    }

    /**
     * @param index the index in which you want to search
     * @param term  the term for which you are trying to find documents
     * @param algo  the reranking algorithm by which you want rerank the documents
     * @return List<DocumentDTO> with the reranked terms and documents.
     */
    public List<DocumentDTO> search(String index, String term, RerankingAlgo algo) {
        TermIndex termIndex = getTermIndex(index);
        List<DocumentDTO> documentDTOS = termIndex.getDocuments(term);
        if (!documentDTOS.isEmpty() && documentDTOS.size() > 1) {
            documentDTOS = rerankerFactory.get(algo).rerank(documentDTOS);
        }
        return documentDTOS;
    }

    private TermIndex getTermIndex(String index) {
        return Optional.ofNullable(multiIndexMap.get(index))
                .orElseThrow(RuntimeException::new);
    }


}
