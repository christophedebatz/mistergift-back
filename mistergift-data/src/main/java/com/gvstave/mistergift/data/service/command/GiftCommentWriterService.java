package com.gvstave.mistergift.data.service.command;

import com.gvstave.mistergift.data.domain.jpa.*;
import com.gvstave.mistergift.data.exception.*;
import com.gvstave.mistergift.data.service.AuthenticatedUser;
import com.gvstave.mistergift.data.service.dto.CommentDto;
import com.gvstave.mistergift.data.service.dto.ExternalUserDto;
import com.gvstave.mistergift.data.service.dto.mapper.CommentMapper;
import com.gvstave.mistergift.data.service.query.UserEventService;
import com.gvstave.mistergift.service.mailing.ExternalUserEmailingService;
import com.gvstave.mistergift.service.mailing.exception.MailException;
import org.apache.commons.validator.routines.EmailValidator;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.*;

/**
 * The service that can write over gift comments.
 */
@Service
public class GiftCommentWriterService {

    /** The gift comment persistence service. */
    @Inject
    private GiftCommentPersistenceService giftCommentPersistenceService;

    /** The gift persistence service. */
    @Inject
    private GiftPersistenceService giftPersistenceService;

    /** The authenticated user. */
    @Inject
    private AuthenticatedUser authenticatedUser;

    /**
     * Creates a new comment.
     *
     * @param text   The text of the comment.
     * @param giftId The id of the commented gift.
     * @return The comment.
     */
    @Transactional(readOnly = false)
    public CommentDto createComment(String text, Long giftId) {
        if (giftId == null) {
            throw new InvalidFieldValueException("giftId");
        }
        if (text != null && !text.isEmpty()) {
            Gift gift = giftPersistenceService.findOne(giftId);
            if (gift == null) {
                throw new GiftNotFoundException(giftId);
            }
            GiftComment newComment = new GiftComment();
            newComment.setAuthor(authenticatedUser.getUser());
            newComment.setObject(gift);
            newComment.setParentId(null);
            newComment.setText(text);
            return CommentMapper.map(giftCommentPersistenceService.save(newComment));
        }
        throw new InvalidFieldValueException("text");
    }

    /**
     * Creates a new sub-comment.
     *
     * @param text The text of the comment.
     * @param parentId The new comment parent id.
     * @return The comment.
     */
    @Transactional(readOnly = false)
    public CommentDto createCommentReply(String text, Long parentId) {
        if (parentId == null) {
            throw new InvalidFieldValueException("parentId");
        }
        if (text != null && !text.isEmpty()) {
            GiftComment parent = giftCommentPersistenceService.findOne(parentId);
            if (parent == null) {
                throw new ObjectNotFoundException("comment", parentId);
            }
            GiftComment reply = new GiftComment();
            reply.setText(text);
            reply.setParentId(parentId);
            reply.setAuthor(authenticatedUser.getUser());
            reply.setObject(parent.getObject());
            return CommentMapper.map(giftCommentPersistenceService.save(reply));
        }
        throw new InvalidFieldValueException("text");
    }

    /**
     * Updates a comment.
     *
     * @param text The new text of the comment.
     * @param commentId The comment id to edit.
     * @return The comment.
     */
    @Transactional(readOnly = false)
    public CommentDto updateComment(String text, Long commentId) {
        if (commentId == null) {
            throw new InvalidFieldValueException("commentId");
        }
        if (text == null || text.isEmpty()) {
            throw new InvalidFieldValueException("text");
        }
        GiftComment comment = giftCommentPersistenceService.findOne(commentId);
        if (comment == null) {
            throw new ObjectNotFoundException("comment", commentId);
        }
        if (comment.getAuthor() != authenticatedUser.getUser()) {
            throw new UnauthorizedOperationException("updating comment");
        }
        comment.setText(text);
        comment.setModificationDate(new Date());
        return CommentMapper.map(giftCommentPersistenceService.save(comment));
    }

    /**
     *  Removes a gift comment.
     *
     * @param commentId The comment id.
     */
    public void removeComment(Long commentId) {
        if (commentId == null) {
            throw new InvalidFieldValueException("commentId");
        }
        GiftComment comment = giftCommentPersistenceService.findOne(commentId);
        if (comment == null) {
            throw new ObjectNotFoundException("comment", commentId);
        }
        if (comment.getAuthor() != authenticatedUser.getUser()) {
            throw new UnauthorizedOperationException("removing comment");
        }
        giftCommentPersistenceService.delete(commentId);
    }
}