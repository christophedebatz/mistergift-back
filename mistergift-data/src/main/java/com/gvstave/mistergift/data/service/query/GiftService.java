package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.jpa.Gift;
import com.gvstave.mistergift.data.repositories.other.GiftPersistenceService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * The {@link Gift} service.
 */
@Service
public class GiftService {

    /** The gift repositories service. */
    @Inject
    private GiftPersistenceService giftPersistenceService;

//    /**
//     * Returns a pageable response of user gift.
//     *
//     * @param user The user.
//     * @param pageable The page request.
//     * @return A page of {@link Gift}.
//     */
//    public Page<Gift> getUserGifts(User user, Pageable pageable) {
//        Objects.requireNonNull(user);
//        Objects.requireNonNull(pageable);
//
//        return giftPersistenceService.findAll(QGift.gift.owners.any().eq(user), pageable);
//    }

}
