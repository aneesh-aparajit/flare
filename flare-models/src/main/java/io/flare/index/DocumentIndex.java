package io.flare.index;

import io.flare.TermPosting;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;


@Data
@Builder
public class DocumentIndex {

    private final Map<String, TermPosting> index;

    /**
     * Adds a term to existing index, if not creates a new TermPosting
     *
     * @param documentID the ID of the document where the term was found.
     * @param offset     the offset of the document where the term was found.
     */
    public void addPosting(String documentID, Long offset) {
        TermPosting termPosting = Optional.ofNullable(index.get(documentID))
                .orElseGet(() -> {
                    TermPosting posting = TermPosting.builder()
                            .documentID(documentID)
                            .termFrequency(0)
                            .offsets(new ArrayList<>())
                            .build();
                    index.put(documentID, posting);
                    return posting;
                });
        termPosting.addPosting(offset);
    }

}
