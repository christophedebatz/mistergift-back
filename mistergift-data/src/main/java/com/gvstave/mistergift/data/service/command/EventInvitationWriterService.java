package com.gvstave.mistergift.data.service.command;

import com.gvstave.mistergift.data.domain.jpa.*;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.data.persistence.jpa.service.EventInvitationPersistenceServiceJpa;
import com.gvstave.mistergift.data.persistence.jpa.service.EventPersistenceServiceJpa;
import com.gvstave.mistergift.data.persistence.jpa.service.UserEventPersistenceServiceJpa;
import com.gvstave.mistergift.data.persistence.jpa.service.UserPersistenceServiceJpa;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;

/**
 * Service that write event invitations.
 */
@Service
public class EventInvitationWriterService {

    /** The event persistence service. */
    @Inject
    private EventPersistenceServiceJpa eventPersistenceService;

    /** The user persistence service. */
    @Inject
    private UserPersistenceServiceJpa userPersistenceService;

    /** The event invitation persistence service. */
    @Inject
    private EventInvitationPersistenceServiceJpa eventInvitationPersistenceService;

    /** The user event persistence service. */
    @Inject
    private UserEventPersistenceServiceJpa userEventPersistenceService;

    /**
     * Invites another user to join an event.
     *
     * @param event The event.
     * @param fromUser The user that invite.
     * @param toUserId The invited user id.
     * @return The created invitation.
     * @throws UnauthorizedOperationException If user cannot invite someone.
     * @throws InvalidFieldValueException If given args are not valid.
     */
    @Transactional(readOnly = false)
    public EventInvitation inviteUserToEvent (Event event, User fromUser, Long toUserId) throws
        UnauthorizedOperationException, InvalidFieldValueException {
        Objects.requireNonNull(event);
        Objects.requireNonNull(fromUser);
        Objects.requireNonNull(toUserId);

        if (event.getId() == null) {
            throw new InvalidFieldValueException("event id");
        }

        Optional<User> targetUser = Optional.ofNullable(userPersistenceService.findOne(toUserId));
        Optional<Event> targetEvent = Optional.ofNullable(eventPersistenceService.findOne(QEvent.event.eq(event)));

        if (targetEvent.isPresent() && targetUser.isPresent()) {
            boolean userCanInvit = targetEvent.get().getParticipants().stream()
                .filter(UserEvent::isAdmin)
                .map(UserEvent::getId)
                .map(UserEventId::getUser)
                .anyMatch(fromUser::equals);

            if (userCanInvit) {
                EventInvitation invitation = new EventInvitation();
                invitation.setSenderUser(fromUser);
                invitation.setTargetUser(targetUser.get());
                invitation.setEvent(targetEvent.get());
                invitation = eventInvitationPersistenceService.save(invitation);

                UserEvent userEvent = new UserEvent();
                userEvent.setInvitation(invitation);
                userEvent.setId(new UserEventId(targetEvent.get(), targetUser.get()));
                userEventPersistenceService.save(userEvent);

                return invitation;
            } else {
                throw new UnauthorizedOperationException("inviteUserToEvent user as a non-admin user");
            }
        } else {
            throw new EntityNotFoundException("Target user or target event has been not found");
        }
    }

    /**
     * Accepts the invitation for a user to join an event.
     *
     * @param user The user.
     * @param eventId The event id.
     */
    @Transactional(readOnly = false)
    public void acceptInvitation(User user, Long eventId) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(eventId);

        QUserEvent qUserEvent = QUserEvent.userEvent;
        Optional.ofNullable(
            userEventPersistenceService.findOne(
                qUserEvent.id.event.id.eq(eventId)
                    .and(qUserEvent.id.user.eq(user))
                    .and(qUserEvent.invitation.isNotNull())
            )
        )
        .ifPresent(userEvent -> {
            EventInvitation invitation = userEvent.getInvitation();
            userEvent.setAdmin(invitation.isAdmin());
            userEvent.setInvitation(null);

            switch (invitation.getType()) {
                case TARGET:
                    userEvent.setCanSeeMines(true);
                    userEvent.setCanSeeOthers(false);
                    break;

                case PARTICIPANT:
                    userEvent.setCanSeeMines(false);
                    userEvent.setCanSeeOthers(true);
                    break;

                default:
                    userEvent.setCanSeeMines(true);
                    userEvent.setCanSeeOthers(true);
                    break;
            }

            eventInvitationPersistenceService.delete(invitation);
            userEventPersistenceService.save(userEvent);
        });
    }

    /**
     * Refuses and cancel an invitation to join an event.
     *
     * @param user The user.
     * @param invitationId The invitation id.
     */
    @Transactional(readOnly = false)
    public void refuseInvitation (User user, Long invitationId) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(invitationId);

        Optional.ofNullable(eventInvitationPersistenceService.findOne(invitationId))
            .filter(filter -> filter.getTargetUser().equals(user))
            .ifPresent(invitation -> {
                eventInvitationPersistenceService.delete(invitation);
                QUserEvent qUserEvent = QUserEvent.userEvent;
                Optional.ofNullable(
                    userEventPersistenceService.findOne(
                        qUserEvent.id.user.eq(user)
                            .and(qUserEvent.id.event.eq(invitation.getEvent())
                                .and(qUserEvent.invitation.isNotNull()))))
                    .ifPresent(userEventPersistenceService::delete);
            }
        );
    }

    /**
     * Cancel an sent invitation to join an event.
     *
     * @param user The user.
     * @param invitationId The invitation id.
     */
    @Transactional(readOnly = false)
    public void cancelInvitation(User user, Long invitationId) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(invitationId);

        QUserEvent qUserEvent = QUserEvent.userEvent;
        Optional.ofNullable(
            userEventPersistenceService.findOne(
                qUserEvent.invitation.isNotNull()
                    .and(qUserEvent.invitation.id.eq(invitationId)))
        )
        .filter(filter -> filter.getInvitation().getSenderUser().equals(user))
        .ifPresent(userEvent -> {
            eventInvitationPersistenceService.delete(userEvent.getInvitation());
            userEventPersistenceService.delete(userEvent);
        });
    }

}
