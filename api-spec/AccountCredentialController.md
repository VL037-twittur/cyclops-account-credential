# Account Credential Controller

- [Get Account Credential By Username](#get-account-credential-by-username)
- [Get Account Credential By ID](#get-account-credential-by-id)
- [Get Account Credential By Email Address](#get-account-credential-by-email-address)
- [Update Account Username](#update-account-username)
- [Update Account Email Address](#update-account-email-address)
- [Update Account Phone Number](#update-account-phone-number)
- [Update Account Password](#update-account-password)

---

## <a name="get-account-credential-by-username"></a> Get Account Credential By Username

**Http Method**: `GET`

**Endpoint**: `/api/v1/acc-cred/@{username}`

**Request Param**: -

**Request Body**: -

**Response**:

- response sample &rarr; `/api/v1/acc-cred/@johndoe123`

```json
{
  "code": 200,
  "status": "OK",
  "error": null,
  "data": {
    "id": "997c11bb-ffc5-4265-a300-1f723c84c2f2",
    "createdDate": "2023-05-14T22:21:50.283874",
    "createdBy": "system",
    "updatedDate": "2023-05-14T22:21:50.283874",
    "updatedBy": "system",
    "username": "johndoe123",
    "emailAddress": "johndoe@gmail.com",
    "phoneNumber": "+621234567890"
  }
}
```

## <a name="get-account-credential-by-id"></a> Get Account Credential By ID

**Http Method**: `GET`

**Endpoint**: `/api/v1/acc-cred/{id}`

**Request Param**: -

**Request Body**: -

**Response**:

- response sample &rarr; `/api/v1/acc-cred/997c11bb-ffc5-4265-a300-1f723c84c2f2`

```json
{
  "code": 200,
  "status": "OK",
  "error": null,
  "data": {
    "id": "997c11bb-ffc5-4265-a300-1f723c84c2f2",
    "createdDate": "2023-05-14T22:21:50.283874",
    "createdBy": "system",
    "updatedDate": "2023-05-14T22:21:50.283874",
    "updatedBy": "system",
    "username": "johndoe123",
    "emailAddress": "johndoe@gmail.com",
    "phoneNumber": "+621234567890"
  }
}
```

## <a name="get-account-credential-by-email-address"></a> Get Account Credential By Email Address

**Http Method**: `POST`

**Endpoint**: `/api/v1/acc-cred/email/{emailAddress}`

**Request Param**: -

**Request Body**: -

**Response**:

- response sample &rarr; `/api/v1/acc-cred/email/johndoe@gmail.com`

```json
{
  "code": 200,
  "status": "OK",
  "error": null,
  "data": {
    "id": "997c11bb-ffc5-4265-a300-1f723c84c2f2",
    "createdDate": "2023-05-14T22:21:50.283874",
    "createdBy": "system",
    "updatedDate": "2023-05-14T22:21:50.283874",
    "updatedBy": "system",
    "username": "johndoe123",
    "emailAddress": "johndoe@gmail.com",
    "phoneNumber": "+621234567890"
  }
}
```

## <a name="update-account-username"></a> Update Account Username

**Http Method**: `PUT`

**Endpoint**: `/api/v1/acc-cred/{id}/username`

**Request Param**: -

**Request Body**:

```json
{
  "username": "new_username"
}
```

**Response**:

- response sample &rarr; `/api/v1/acc-cred/997c11bb-ffc5-4265-a300-1f723c84c2f2/username`

```json
{
  "code": 200,
  "status": "OK"
}
```

## <a name="update-account-email-address"></a> Update Account Email Address

**Http Method**: `PUT`

**Endpoint**: `/api/v1/acc-cred/{id}/emailAddress`

**Request Param**: -

**Request Body**:

```json
{
  "emailAddress": "new_email@email.com"
}
```

**Response**:

- response sample &rarr; `/api/v1/acc-cred/997c11bb-ffc5-4265-a300-1f723c84c2f2/emailAddress`

```json
{
  "code": 200,
  "status": "OK"
}
```

## <a name="update-account-phone-number"></a> Update Account Phone Number

**Http Method**: `PUT`

**Endpoint**: `/api/v1/acc-cred/{id}/phoneNumber`

**Request Param**: -

**Request Body**:

```json
{
  "phoneNumber": "+621234567890"
}
```

**Response**:

- response sample &rarr; `/api/v1/acc-cred/997c11bb-ffc5-4265-a300-1f723c84c2f2/phoneNumber`

```json
{
  "code": 200,
  "status": "OK"
}
```

## <a name="update-account-password"></a> Update Account Password

**Http Method**: `PUT`

**Endpoint**: `/api/v1/acc-cred/{id}/password`

**Request Param**: -

**Request Body**:

```json
{
  "password": "new password"
}
```

**Response**:

- response sample &rarr; `/api/v1/acc-cred/997c11bb-ffc5-4265-a300-1f723c84c2f2/password`

```json
{
  "code": 200,
  "status": "OK"
}
```
