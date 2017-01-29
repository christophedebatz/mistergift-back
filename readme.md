Mistergift.io API
===================

----------
Just a little brief for API endpoints for first version.

Exceptions handling
-------------

Exception format remains the same on the API. An exception has been thrown when the response only contains the "error" node. From this point, it's quite easy for the front-side to get more information about what has failed by browsing the "error" content.

```
{
  "error": {
    "status": "<status-code>",
    "exception": "<exception-name",
    "message": "<exception-message>",
    "parameters": {
      "<name>": "<value>
    }
  }
}
```
The "parameters" part is optional and allow the front-side to have some precisions about the exception.

### Too many requests

```
{
  "error": {
    "status": 429,
    "exception": "TooManyRequestException",
    "message": "Too many requests. Please try again in 6 s.",
    "parameters": {
      "waitingTime": 6
    }
  }
}
```
### Missing token
```
{
  "error": {
    "status": 401,
    "exception": "MissingTokenException",
    "message": "Missing authorization token."
  }
}
```
### Invalid token
```
{
  "error": {
    "status": 401,
    "exception": "InvalidTokenException",
    "message": "The authorization token is wrong."
  }
}
```

### Invalid field
```
{
  "error": {
    "status": 400,
    "exception": "InvalidFieldValueException",
    "message": "The field firstName is invalid, null or empty.",
    "parameters": {
      "fieldName": "firstName"
    }
  }
}
```

Endpoints
-------------


### <i class="icon-file"></i> Events

| Field        | Description           
| ------------- |:-------------:|
| <kbd>string</kbd> name    | The event name |
| <kbd>EventStatus</kbd> status      | The event status (removed, cancelled, published, unpublished)
| <kbd>string</kbd> description | The event description     |
| <kbd>Date</kbd> startDate | The event start date  |
| <kbd>Date</kbd> endDate      | The event end date   |
| <kbd>string</kbd> address      | The event address   |
| <kbd>FileMetadata</kbd> cover | The event cover picture  |
| <kbd>List&lt;UserEvent&gt;</kbd> participants | The event participants relationships  |

#### Retrieve a specific event

> **GET** /events/**{ event-id }**

#### Retrieve current-user events

> **GET** /me/events[?page=**{ page-no }**]

#### Retrieve event participant(s)

> **GET** /events/**{event-id}**/members[?page=**{ page-no }**]

#### Retrieve event invited user(s)

> **GET** /events/**{event-id}**/guests[?page=**{ page-no }**]

#### Retrieve event administrator(s)

> **GET** /events/**{ event-id }**/admins[?page=**{ page-no }**]

#### Retrieve event user whishlist

> **GET** /events/**{ event-id }**/users/**{ user-id }**/gifts[?page=**{ page-no }**]

#### Create an event

You will be an administrator.

> **POST** /events

#### Create an event for someone else

You and him will be an administrator, obviously if he comes and subscribe to MG.

> **POST** /events // to be clarified

#### Update an event

You must be the event administrator.

> **PUT** /events

#### Update event status

You must be the event administrator.
With that endpoint, you can:

- Cancel an event ("cancelled")
- Publish it ("published")
- Unpublish it ("unpublished")
- Remove it ("removed")

> **PUT** /events/**{ user-id }**/status

```
{
	"status": "cancelled"
}
```

#### Remove an event

You must be the event administrator.

> **DELETE** /events/**{ event-id }**


----------


### <i class="icon-file"></i> Event invitations

| Field        | Description           
| ------------- |:-------------:|
| <kbd>User</kbd> senderUser    | The user who invites somebody |
| <kbd>User</kbd> targetUser      | The user who is invited to join an event      |
| <kbd>Event</kbd> event | The event     |
| <kbd>EventInvitationType</kbd> type | The target user event relationship     |
| <kbd>boolean</kbd> admin      | If the invited user will be an administrator     |

#### Invite somebody

Your can invite a user to join an event if you already takes part of this one. You have to pass the event as the body.

>  **POST** /users/**{ user-id }**/invitations
```
{
	"id": 2
}
```

#### Invite somebody who does not exists on MG

Your can invite another people who does not exists (at the moment) on MG.

>  **POST** /events/{event-id}/externals
```
{
	[
	   "name": "Julien Ducrot",
	   "email": "pro@joulse.com"
	],
	[
	   "name": "Christophe de Batz",
	   "email": "christophe.db@gmail.com"
	]
}
```

#### Cancel an invitation

To cancel an invitation you must be the sender of the invitation.

> **DELETE** /users/**{ user-id }**/invitations/**{ invitation-id }**

#### Accept an invitation

To accept a pending invitation you must be the target (the invited user) of the invitation.

> **POST** /me/events/**{ event-id }**

#### Refuse an invitation

To refuse a pending invitation you must be the target (the invited user) of the invitation.

> **DELETE** /me/invitations/**{ invitation-id }**


----------


### <i class="icon-file"></i> Auth

#### Authenticate user (get user token)

> **POST** /authenticate

```
// x-www-form-urlencoded
{
	"username": "hello",
	"password": "world
}
```

#### Destroy user token

> **DELETE** /token

#### Recover user password (not logged-in user only)

***Step #1: send email with token***

> **GET** /password/token?email=**{ user-email }**

***Step #2: set new password***

> **PUT** /password?token=**{ token }**

Request:
```
// application/json
{
	"password": "chris@gvstave.io"
}
```

----------


### <i class="icon-file"></i> Users

| Field        | Description           
| ------------- |:-------------:|
| <kbd>string</kbd> name | The user name |
| <kbd>string</kbd> email | The user email |
| <kbd>string</kbd> password | The user password |
| <kbd>Role</kbd> role | The user role (admin, user) |
| <kbd>Token</kbd> token | The user token |
| <kbd>FileMetadata</kbd> picture | The user profile picture |
| <kbd>FileMetadata</kbd> thumbnail | The user profile picture thumbnail |
| <kbd>List&lt;UserEvent&gt;</kbd> usersEvents | The user events relationships |
| <kbd>string</kbd> locale | The user locale |

#### Retrieve the users

> **GET** /users[?page=**{ page-no }**]

#### Retrieve the logged-in user

> **GET** /me

#### Retrieve a specific user

> **GET** /users/**{ user-id }**

#### Create a user

> **POST** /users

#### Update the logged-in user

You must be the user that you attempt to update.

> **PUT** /me

#### Upload the logged-in profile picture

> **POST** /me/picture

#### Retrieve logged-in user whishlist

> **GET** /me/whishlist

#### Retrieve logged-in user timeline

> **GET** /me/timeline

----------


### <i class="icon-file"></i> Whishlist

#### Retrieve the public whishlist of a user

> **GET** /me/whishlist[?page=**{ page-no }**]

#### Participate to a user gift

> **POST** /gifts/**{gift-id}**/participations

To reserve a gift for a user, you have to post the gift (only its id is sufficient) into the user participations (it's a bag with all user participations for a specific event).

A partir du moment ou un cadeau a été réservé dans un event, c'est marqué dans le cadeau et ce cadeau est caché sur les autre events où le membre est inscrit (todo mettre un flag eventReservationId dans le gift)

Example 1: book an entire gift
```
// application/json
{
	"id": 38,
	"type": "dynamic",
	"value": 100
}
```

Example 2: book a variable part of a gift (for instance 50% of the price)
```
// application/json
{
	"id": 38,
	"type": "dynamic",
	"value": 50
}
```

Example 3: book a fixed part of a gift (for instance, only 25 euros)
```
// application/json
{
	"id": 38,
	"type": "fixed",
	"value": 25
}
```

#### Retrieve all the participations for a user for an event

It retrieves all participations for the given user gifts, in terms of the user who made the request. If it's the given user, it will get nothing etc. (because surprise is surprise !)

> **GET** /users/**{user-id}**/events/**{event-id}**/participations[?page=**{ page-no }**]

### <i class="icon-file"></i> Gift comments

| Field        | Description           
| ------------- |:-------------:|
| <kbd>Gift</kbd> gift | The associated gift |
| <kbd>User</kbd> autor | The user who writes the comment |
| <kbd>string</kbd> text | The comment text |
| <kbd>Date</kbd> creationDate | The date of creation |
| <kbd>Date</kbd> modificationDate | The date of modification |

#### Retrieve all the comments for a gift

> **GET** /gifts/**{gift-id}**/comments[?page=**{ page-no }**]

#### Post a comment for a gift

> **POST** /gifts/**{gift-id}**/comments

#### Update a comment for a gift

> **PUT** /gifts/**{gift-id}**/comments/**{comment-id}**


### <i class="icon-file"></i> User notifications

Notifications are a good way to maintains user informed of site dynamism and to creates move of users.
Notifications can be broadcasted by the MG administrators but are mainly automatic!


#### Retrieves user notifications

Notification
| Field        | Description           
| ------------- |:-------------:|
| <kbd>User</kbd> content | The target user|
| <kbd>Long</kbd> eventId | The event id |
| <kbd>Type</kbd> type | The notification type (gift, comment, participation) |
| <kbd>Date</kbd> readDate | The date of read |
| <kbd>Date</kbd> date | The date |

> **GET** /me/notifications[?status=**{unread|read|all}**&from=**{from-date}**&to=**{toDate}**&page=**{page-no}**]

By default, the interval between the from date and the to date is one month and the default notifications status is "unread" (that's all notifications that the user doesn't read yet).