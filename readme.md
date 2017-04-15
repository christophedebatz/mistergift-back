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
    "details": {
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

### Invalid fields
```
{
  "error": {
    "status": 400,
    "exception": "InvalidFieldValueException",
    "message": "The field firstName is invalid, null or empty.",
    "parameters": {
      "fields": "firstName,lastName,email"
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
| <kbd>Date</kbd> startDate | The event start creationDate  |
| <kbd>Date</kbd> endDate      | The event end creationDate   |
| <kbd>string</kbd> address      | The event address   |
| <kbd>FileMetadata</kbd> cover | The event cover picture  |
| <kbd>List&lt;UserEvent&gt;</kbd> participants | The event participants relationships  |

#### Retrieve a specific event

> **GET** /events/**{ event-id }**

#### Retrieve current-author events

Returns the logged-in author events. You can specify which types of events you want, each type must be separated by a coma.
If no filter is given, you will get all author events, grouped by type.

- **invitation**: returns all the event author invitations
- **admin**: returns all the events which the author is an administrator
- **removed**: returns all the author removed events 
- **cancelled**: returns all the author cancelled events
- **published**: returns all the author published events
- **unpublished**: returns all the author unpublished events

For instance:

> **GET** /me/events?filters=invitation,admin,published[&page=**{ page-no }**]

#### Retrieve event participant(s)

> **GET** /events/**{event-id}**/members[?page=**{ page-no }**]

#### Retrieve event invited author(s)

> **GET** /events/**{event-id}**/guests[?page=**{ page-no }**]

#### Retrieve event administrator(s)

> **GET** /events/**{ event-id }**/admins[?page=**{ page-no }**]

#### Retrieve event author whishlist

> **GET** /events/**{ event-id }**/users/**{ author-id }**/gifts[?page=**{ page-no }**]

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

> **PUT** /events/**{ author-id }**/status

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
| <kbd>User</kbd> senderUser    | The author who invites somebody |
| <kbd>User</kbd> targetUser      | The author who is invited to join an event      |
| <kbd>Event</kbd> event | The event     |
| <kbd>EventInvitationType</kbd> type | The target author event relationship     |
| <kbd>boolean</kbd> admin      | If the invited author will be an administrator     |

#### Invite somebody

Your can invite a author to join an event if you already takes part of this one. You have to pass the event as the body.

>  **POST** /users/**{ author-id }**/invitations
```
{
	"id": 2
}
```

#### Invite somebody who does not exists on MG

Your can invite another people who does not exists (at the moment) on MG.

>  **POST** /events/{event-id}/externals
```
[
    {
        "admin": true,
        "type": "target",
        "name": "Julien",
        "lastName": "Ducrot",
        "email": "pro@joulse.com"
    },
	{
	   "admin": false,
	   "type": "participant",
	   "firstName": "Christophe",
	   "lastName": "de Batz",
	   "email": "christophe.db@gmail.com"
	}
]
```

#### Cancel an invitation

To cancel an invitation you must be the sender of the invitation.

> **DELETE** /users/**{ author-id }**/invitations/**{ invitation-id }**

#### Accept an invitation

To accept a pending invitation you must be the target (the invited author) of the invitation.

> **POST** /me/events/**{ event-id }**

#### Refuse an invitation

To refuse a pending invitation you must be the target (the invited author) of the invitation.

> **DELETE** /me/invitations/**{ invitation-id }**


----------


### <i class="icon-file"></i> Auth

#### Authenticate author (get author token)

> **POST** /authenticate

```
// x-www-form-urlencoded
{
	"username": "hello",
	"password": "world
}
```

#### Destroy author token

> **DELETE** /token

#### Recover author password (not logged-in author only)

***Step #1: send email with token***

> **GET** /password/token?email=**{ author-email }**

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
| <kbd>string</kbd> name | The author name |
| <kbd>string</kbd> email | The author email |
| <kbd>string</kbd> password | The author password |
| <kbd>Role</kbd> role | The author role (admin, author) |
| <kbd>Token</kbd> token | The author token |
| <kbd>FileMetadata</kbd> picture | The author profile picture |
| <kbd>FileMetadata</kbd> thumbnail | The author profile picture thumbnail |
| <kbd>List&lt;UserEvent&gt;</kbd> usersEvents | The author events relationships |
| <kbd>string</kbd> locale | The author locale |

#### Retrieve the users

> **GET** /users[?page=**{ page-no }**]

#### Retrieve the logged-in author

> **GET** /me

#### Retrieve a specific author

> **GET** /users/**{ author-id }**

#### Create a author

> **POST** /users

You must provide an object with:
* The author first name ("firstName" node)
* The author last name  ("lastName" node)
* The author email ("email" node)
* The author raw password ("password" node)

#### Update the logged-in author

You must be the author that you attempt to update.

> **PUT** /me

#### Upload the logged-in profile picture

> **POST** /me/picture

#### Retrieve logged-in author whishlist

> **GET** /me/whishlist

#### Retrieve logged-in author timeline

> **GET** /me/timeline

----------


### <i class="icon-file"></i> Whishlist

#### Retrieve the public whishlist of a author

> **GET** /me/whishlist[?page=**{ page-no }**]

#### Participate to a author gift

> **POST** /gifts/**{gift-id}**/participations

To reserve a gift for a author, you have to post the gift (only its id is sufficient) into the author participations (it's a bag with all author participations for a specific event).

A partir du moment ou un cadeau a été réservé dans un event, c'est marqué dans le cadeau et ce cadeau est caché sur les autre events où le membre est inscrit (todo mettre un flag eventReservationId dans le gift)

Example 1: book an entire gift
```
// application/json
{
	"event": 38,
	"type": "fixed",
	"value": 100
}
```

Example 2: book a variable part of a gift (for instance 50% of the price)
```
// application/json
{
	"id": 38,
	"type": "relative",
	"value": 50
}
```

#### Retrieve all the participations for a gift

It retrieves the current author participation for a gift in particular.

> **GET** /me/gifts/**{gift-id}**/participations

#### Retrieve all the participations for a gift

It retrieves the current author participations, eventually for an event

> **GET** /me/participations[?**eventId=4**&page=2@limit=6]

### <i class="icon-file"></i> Gift comments

| Field        | Description           
| ------------- |:-------------:|
| <kbd>Gift</kbd> gift | The associated gift |
| <kbd>User</kbd> author | The user who writes the comment |
| <kbd>Long</kbd> parentId | The comment parent id |
| <kbd>string</kbd> text | The comment text |
| <kbd>Date</kbd> creationDate | The creationDate of creation |
| <kbd>Date</kbd> modificationDate | The creationDate of modification |

#### Retrieve all the comments for a gift

* You can pass a user id ("userId") to limit the results to a specific user (as the comments author)

**GET** /gifts/**{gift-id}**/comments[?userId=**{ user-id }**&page=**{ page-no }**&limit=**{ limit }**]

#### Post a new comment for a gift

You have to pass the parameter "text" on the body of your request. This will be the text of the comment.

> **POST** /gifts/**{gift-id}**/comments

#### Update a comment for a gift

Same as a new comment, you have to pass the new text under parameter "text" on the body of the request.

> **PUT** /comments/**{comment-id}**

#### Reply to a comment

* Mistergift supports multi-level comments

Same as previously, you have to pass the parameter "text" on the body of the request.

> **POST** /comments/**{comment-id}**

### <i class="icon-file"></i> User notifications

Notifications are a good way to maintains author informed of site dynamism and to creates move of users.
Notifications can be broadcasted by the MG administrators but are mainly automatic!


#### Retrieves author notifications

Notification
| Field        | Description           
| ------------- |:-------------:|
| <kbd>User</kbd> content | The target author|
| <kbd>Long</kbd> eventId | The event id |
| <kbd>Type</kbd> type | The notification type (gift, comment, participation) |
| <kbd>Date</kbd> readDate | The creationDate of read |
| <kbd>Date</kbd> creationDate | The creationDate |

> **GET** /me/notifications[?status=**{unread|read|all}**&from=**{from-creationDate}**&to=**{toDate}**&page=**{page-no}**]

By default, the interval between the from creationDate and the to creationDate is one month and the default notifications status is "unread" (that's all notifications that the author doesn't read yet).

### <i class="icon-file"></i> Products

The products are the base of the gifts. What's the difference between a product and a gift?
* A product represents an item that everyone can buy online (on Amazon or everywhere else)
* A gift is the link between a product and a user on MG

#### Retrieve the last added products

* By default, the limit is back-side capped at 10 items per request.
* The since parameter is an ISO date which represents the product adding date.

> **GET** /products/[?limit=**{ limit }**&since=**{ date }**]

#### Retrieve a product by its id

> **GET** /products/**{ product-id }**

#### Quick product search
It will search on product brand name, description and name.

> **GET** /products/search?q=**{ search }**[&page=**{ page }**&limit=**{ limit }**]

#### Advanced product search
You also want an advanced search with an object passed on the request body and named "search" which should contains:
* **name**: the name of the product
* **brand**: the brand of the product
* **description**: the description@ of the product
* **reference**: the reference of the product

All these parameters are optional.

> **GET** /products/search/advanced[?page=**{ page }**&limit=**{ limit }**]

An example of request:
```
{
  "brand": "Sony",
  "name": "PS",
  "description": "entertainment computer"
}
```
