package io.flare;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Data
@Builder
public class TermPosting {

    private final String documentID;
    private long termFrequency;
    private List<Long> offsets;

    /**
     * Adds the new occurrence to the offset and increases term frequency
     *
     * @param offset the offset from the document where the term was found.
     */
    public void addPosting(Long offset) {
        Optional.ofNullable(offsets)
                .map(list -> list.add(offset))
                .orElseGet(() -> {
                    offsets = new ArrayList<>();
                    offsets.add(offset);
                    return null;
                });
        termFrequency++;
    }

}
