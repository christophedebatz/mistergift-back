package com.gvstave.mistergift.data.service;

import com.gvstave.mistergift.data.domain.Event;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.domain.UserEvent;
import com.gvstave.mistergift.data.domain.UserEventId;
import com.gvstave.mistergift.data.persistence.UserEventPersistenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Objects;

/**
 * The event service.
 */
@Service
public class EventService {

    /** The event persistence service. */
    @Inject
    private UserEventPersistenceService userEventPersistenceService;

    /**
     * Creates new event and add it to the given user.
     *
     * @param event The event to create.
     * @param user  The event user.
     * @return The created event.
     */
    @Transactional
    public Event createEvent(Event event, User user) {
        Objects.requireNonNull(event);
        Objects.requireNonNull(user);

        UserEventId userEventId = new UserEventId(null, user);
        UserEvent userEvent = new UserEvent(userEventId, true);
        userEvent = userEventPersistenceService.save(userEvent);

        return userEvent.getId().getEvent();
    }

    /**
     * Returns if the given user is an api of the given event id.
     *
     * @param user The user.
     * @param eventId The event id.
     * @return whether the user is part of the event admins.
     */
    public boolean isEventAdmin(User user, Long eventId) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(eventId);

//        // if update operation
//        Optional<Event> baseGroup = Optional.ofNullable(userEventPersistenceService.findOne(groupId));
//
//        // current event must be an api of the event
//        return baseGroup.isPresent() &&
//                baseGroup.get().getAdministrators().stream()
//                        .filter(admin -> Objects.equals(admin.getId(), user.getId()))
//                        .count() > 0;
        return true;
    }

}
