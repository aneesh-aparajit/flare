package io.flare.reranker;

import java.util.Map;
import java.util.Optional;

public class RerankerFactory {

    private final Map<RerankingAlgo, Reranker> rerankerMap;

    public RerankerFactory(Map<RerankingAlgo, Reranker> rerankerMap) {
        this.rerankerMap = rerankerMap;
    }

    public void register(RerankingAlgo algo, Reranker reranker) {
        Optional.ofNullable(rerankerMap.get(algo))
                .orElseGet(() -> {
                    rerankerMap.put(algo, reranker);
                    return reranker;
                });
    }

    public Reranker get(RerankingAlgo algo) {
        return Optional.ofNullable(rerankerMap.get(algo))
                .orElseThrow(RuntimeException::new);
    }

}
