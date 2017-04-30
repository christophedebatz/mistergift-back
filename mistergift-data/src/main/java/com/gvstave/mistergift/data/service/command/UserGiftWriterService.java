package com.gvstave.mistergift.data.service.command;

import com.gvstave.mistergift.data.domain.jpa.*;
import com.gvstave.mistergift.data.domain.mongo.Product;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.ProductNotFoundException;
import com.gvstave.mistergift.data.service.AuthenticatedUser;
import com.gvstave.mistergift.data.service.dto.UserGiftDto;
import com.gvstave.mistergift.data.service.query.UserService;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
     * Create a new user gift.
     *
     * @param userGiftDto The gift dto.
     * @throws InvalidFieldValueException
     * @throws ProductNotFoundException
     */
    @Transactional
    public UserGiftDto createNewUserGift(UserGiftDto userGiftDto) throws InvalidFieldValueException, ProductNotFoundException {
        if (userGiftDto.getEvent().getId() == null) {
            throw new InvalidFieldValueException("event");
        }
        if (userGiftDto.getUser().getId() == null) {
            throw new InvalidFieldValueException("user");
        }
        if (userGiftDto.getProductId() == null) {
            throw new InvalidFieldValueException("productId");
        }

        Optional<User> user = userService.fromId(userGiftDto.getUser().getId());

        Query query = new Query();
        query.addCriteria(Criteria.where("productId").is(userGiftDto.getProductId()));
        Product product = mongoOperations.findOne(query, Product.class);

        if (product == null) {
            throw new ProductNotFoundException(userGiftDto.getProductId());
        }

        if (userGiftDto.getUser().getId().equals(AuthenticatedUser.getUserId())) {
            throw new InvalidFieldValueException("userId");
        }

        if (user.isPresent()) {
            Gift gift = new Gift();
            gift.setEvent(userGiftDto.getEvent());
            gift.setProductId(userGiftDto.getProductId());
            giftPersistenceService.save(gift);
            UserGift userGift = new UserGift();
            userGift.setId(new UserGiftId(user.get(), userGiftDto.getProductId()));
            userGiftPersistenceService.save(userGift);
            UserGiftDto dto = new UserGiftDto();
            dto.setEvent(userGiftDto.getEvent());
            dto.setProductId(userGiftDto.getProductId());
            dto.setUser(user.get());
            return dto;
        }
        return null;
    }
}
