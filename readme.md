Mistergift.io API
===================

----------
Just a little brief for API endpoints for first version.

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

> **POST** /users/**{user-id}**/events/**{event-id}**/participations

To reserve a gift for a user, you have to post the gift (only its id is sufficient) into the user participations (it's a bag with all user participations for a specific event).

Example 1: book an entire gift
```
// application/json
{
	"id": 38
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
| <kbd>Date</kbd> creationDate | The user role (admin, user) |
| <kbd>Date</kbd> modificationDate | The user token |

#### Retrieve all the comments for a gift

> **GET** /gifts/**{gift-id}**/comments[?page=**{ page-no }**]

#### Post a comment for a gift

> **POST** /gifts/**{gift-id}**/comments

#### Update a comment for a gift

> **PUT** /gifts/**{gift-id}**/comments/**{comment-id}**