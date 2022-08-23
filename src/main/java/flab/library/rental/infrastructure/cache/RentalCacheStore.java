package flab.library.rental.infrastructure.cache;

import flab.library.common.exception.BusinessException;
import flab.library.common.exception.BusinessExceptionDictionary;
import flab.library.infrastructure.cache.CacheStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RentalCacheStore {
    public static final String RENTAL_RANKING = "RENTAL_RANKING";
    private final CacheStore cacheStore;

    public void increaseRentalCount(String isbn) {
        if (isbn == null)
            BusinessException.createAndThrow(BusinessExceptionDictionary.BOOK_NOT_FOUND_EXCEPTION);
        cacheStore.incrementScore(RENTAL_RANKING, isbn, 1L);
    }

    Set<ZSetOperations.TypedTuple<String>> getRentalRanksWithScore(Pageable pageable) {
        return cacheStore.getRanksWithScore(RENTAL_RANKING, pageable);
    }
}
