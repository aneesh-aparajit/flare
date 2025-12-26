package io.flare.index;

import io.flare.dto.DocumentDTO;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Data
@Builder
public class TermIndex {

    private final Map<String, DocumentIndex> index;

    /**
     * Adds a term to existing index, if not creates a new DocumentIndex
     *
     * @param term       term for which data is to be added
     * @param documentID the documentID where the term is found
     * @param offset     the offset of the document where the term is found
     */
    public void addTerm(String term, String documentID, Long offset) {
        DocumentIndex documentIndex = Optional.ofNullable(index.get(term))
                .orElseGet(() -> {
                    DocumentIndex docIndex = DocumentIndex.builder()
                            .index(new HashMap<>())
                            .build();
                    index.put(term, docIndex);
                    return docIndex;
                });
        documentIndex.addPosting(documentID, offset);
    }

    /**
     * Returns the DocumentIndex of a term if it exists, else throws an error.
     *
     * @param term the term for which documents are to be fetched.
     * @return instanceof DocumentIndex
     */
    public List<DocumentDTO> getDocuments(String term) {
        return Optional.ofNullable(index.get(term))
                .map(DocumentIndex::getIndex)
                .stream()
                .flatMap(innerMap -> innerMap.entrySet().stream())
                .map(entry -> DocumentDTO.builder()
                        .term(term)
                        .documentID(entry.getKey())
                        .termFrequency(entry.getValue().getTermFrequency())
                        .offsets(entry.getValue().getOffsets())
                        .build())
                .collect(Collectors.toList());
    }

}
