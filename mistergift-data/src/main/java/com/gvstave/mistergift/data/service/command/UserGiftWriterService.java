package com.gvstave.mistergift.data.service.command;

import com.gvstave.mistergift.data.domain.jpa.*;
import com.gvstave.mistergift.data.domain.mongo.Product;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.ProductNotFoundException;
import com.gvstave.mistergift.data.service.AuthenticatedUser;
import com.gvstave.mistergift.data.service.dto.GiftDto;
import com.gvstave.mistergift.data.service.query.UserService;
import com.gvstave.mistergift.data.service.query.password.UserNotFoundException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by chris on 14/03/2017.
 */
@Service
public class UserGiftWriterService {

    @Inject
    private UserService userService;

    @Inject
    private GiftPersistenceService giftPersistenceService;

    @Inject
    private UserGiftPersistenceService userGiftPersistenceService;

    @Inject
    private MongoOperations mongoOperations;

    /**
     *
     * @param giftDto
     * @throws InvalidFieldValueException
     * @throws ProductNotFoundException
     */
    @Transactional
    public void createNewUserGift(GiftDto giftDto) throws InvalidFieldValueException, ProductNotFoundException {
        if (giftDto.getEvent().getId() == null) {
            throw new InvalidFieldValueException("event");
        }
        if (giftDto.getUser().getId() == null) {
            throw new InvalidFieldValueException("user");
        }
        if (giftDto.getProductId() == null) {
            throw new InvalidFieldValueException("productId");
        }

        Optional<User> user = userService.fromId(giftDto.getUser().getId());

        Query query = new Query();
        query.addCriteria(Criteria.where("productId").is(giftDto.getProductId()));
        Product product = mongoOperations.findOne(query, Product.class);

        if (product == null) {
            throw new ProductNotFoundException(giftDto.getProductId());
        }

        if (giftDto.getUser().getId().equals(AuthenticatedUser.getUserId())) {
            throw new InvalidFieldValueException("userId");
        }

        if (user.isPresent()) {
            Gift gift = new Gift();
            gift.setEvent(giftDto.getEvent());
            gift.setProductId(giftDto.getProductId());
            giftPersistenceService.save(gift);
            UserGift userGift = new UserGift();
            userGift.setId(new UserGiftId(user.get(), giftDto.getProductId()));
            userGiftPersistenceService.save(userGift);
        }
    }
}
