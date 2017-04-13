package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.annotation.UserRestricted;
import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.data.domain.jpa.GiftComment;
import com.gvstave.mistergift.data.service.command.GiftCommentWriterService;
import com.gvstave.mistergift.data.service.dto.CommentDto;
import com.gvstave.mistergift.data.service.dto.mapper.CommentMapper;
import com.gvstave.mistergift.data.service.query.GiftCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * API endpoint for {@link GiftComment }.
 */
@UserRestricted
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCommentController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(GiftCommentController.class);

    /** The gift comment service. */
    @Inject
    private GiftCommentService giftCommentService;

    /** The gift comment writer service. */
    @Inject
    private GiftCommentWriterService giftCommentWriterService;

    /**
     * Returns the comments for a gift, and, eventually with a user id.
     *
     * @param page The page.
     * @param limit The limit.
     * @param giftId The gift id.
     * @return The gift comments.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/gifts/{giftId}/comments")
    public PageResponse<CommentDto> getGiftComments(
            @PathVariable(value = "giftId") Long giftId,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit,
            @RequestParam(value = "userId", required = false) Long userId) {
        LOGGER.debug("Fetching all comments for gift-id={}, user-id={}, page={} and limit={}", giftId, userId, page, limit);
        Page<GiftComment> giftComments;
        PageRequest pageRequest = getPageRequest(page, limit);

        if (userId != null) {
            giftComments = giftCommentService.getUserGiftComments(giftId, userId, pageRequest);
        } else {
            giftComments = giftCommentService.getGiftComments(giftId, pageRequest);
        }

        return new PageResponse<>(giftComments.map(CommentMapper::map));
    }

    /**
     * Create a new comment for the given gift id and return it as a confirmation.
     *
     * @param giftId The gift id.
     * @param text The comment text.
     * @return The comment.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, path = "/gifts/{giftId}/comments")
    public CommentDto createComment(
            @PathVariable(value = "giftId") Long giftId,
            @RequestBody String text) {
        LOGGER.debug("Creating new comment for gift-id={}", giftId);
        return giftCommentWriterService.createComment(text, giftId);
    }

    /**
     * Create a new comment reply for the given comment parent id and return it as a confirmation.
     *
     * @param commentId The comment id (the comment parent id).
     * @param text The comment text.
     * @return The comment.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, path = "/comments/{commentId}")
    public CommentDto createCommentReply(
            @PathVariable(value = "commentId") Long commentId,
            @RequestBody String text) {
        LOGGER.debug("Creating new comment reply for comment-id={}", commentId);
        return giftCommentWriterService.createCommentReply(text, commentId);
    }

    /**
     * Update a comment by its id.
     *
     * @param commentId The comment id to update.
     * @param text The new comment text.
     * @return The updated comment.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT, path = "/comments/{commentId}")
    public CommentDto updateComment(
            @PathVariable(value = "commentId") Long commentId,
            @RequestBody String text) {
        LOGGER.debug("Updating existing comment-id={}", commentId);
        return giftCommentWriterService.updateComment(text, commentId);
    }

    /**
     * Remove a gift comment.
     *
     * @param commentId The comment id.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, path = "/comments/{commentId}")
    public void updateComment(@PathVariable(value = "commentId") Long commentId) {
        LOGGER.debug("Removing existing comment-id={}", commentId);
        giftCommentWriterService.removeComment(commentId);
    }

}
