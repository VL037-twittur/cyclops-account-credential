# Authentication Controller

- [Register](#register)
- [Login](#login)
- [Refresh Token](#refresh-token)

---

## <a name="register"></a> Register

**Http Method**: `POST`

**Endpoint**: `/api/v1/auth/register`

**Request Param**: -

**Request Body**:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "dateOfBirth": "1990-01-01",
  "username": "johndoe123",
  "accountName": "johndoe",
  "bio": "I'm John Doe",
  "emailAddress": "johndoe@gmail.com",
  "phoneNumber": "+621234567890",
  "password": "my password",
  "confirmPassword": "my password"
}
```

**Response**:

- response sample &rarr; `/api/v1/auth/register`

```json
{
  "code": 200,
  "status": "OK",
  "error": null,
  "data": {
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
  }
}
```

## <a name="login"></a> Login

**Http Method**: `POST`

**Endpoint**: `/api/v1/auth/register`

**Request Param**: -

**Request Body**:

```json
{
  "username": "johndoe123",
  "password": "my password"
}
```

```json
{
  "username": "johndoe@gmail.com",
  "password": "my password"
}
```

**Response**:

- response sample &rarr; `/api/v1/auth/login`

```json
{
  "code": 200,
  "status": "OK",
  "error": null,
  "data": {
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
  }
}
```

- response sample &rarr; wrong credential

```json
{
  "code": 403,
  "status": "FORBIDDEN",
  "error": "authentication failed"
}
```

## <a name="refresh-token"></a> Refresh Token

**Http Method**: `POST`

**Endpoint**: `/api/v1/auth/refresh-token`

**Request Param**: -

**Request Body**: -

**Headers**:

| Key           | Value                      |
|---------------|----------------------------|
| Authorization | Bearer `<<refresh_token>>` |

**Response**:

- response sample &rarr; `/api/v1/auth/refresh-token`

```json
{
  "code": 200,
  "status": "OK",
  "error": null,
  "data": {
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
  }
}
```