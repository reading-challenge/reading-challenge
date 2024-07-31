# reading-challenge

# API 데이터 규격

---

## 1. 회원

### 1. 로그인 POST /login

* 요청

```
{
    "username": ${username},
    "password": ${password}
}
```

* 응답

```
{
    "code": 200,
    "data": {
        "id": ${id},
        "userId": ${userId},
        "userPw": ${userPw},
        "email": ${email},
        "phone": ${phone},
        "birthday": ${birthday},
        "favoriteSub": ${favoriteSub},
        "profileSrc": ${profileSrc},
        "nickname": ${nickname},
        "authorities": [
            {
                "authority": ${ROLE}
            }
        ],
        "enabled": ${enabled},
        "password": ${userPw},
        "username": ${userid},
        "accountNonLocked": ${accountNonLocked},
        "accountNonExpired": ${accountNonExpired},
        "credentialsNonExpired": ${credentialsNonExpired}
    },
    "message": null
}
```

### 2. 로그아웃 POST /logout

* 요청

```
body 필요 없음.
```

* 응답

```
{
    "code": 200,
    "data": "로그아웃 성공",
    "message": null
}
```

### 3. 회원가입 POST /signup

* 요청

```
{
    "userId": ${userId},
    "userPw": ${userPw},
    "email": ${email},
    "phone": ${phone},
    "birthday": ${birthday},
    "favoriteSub": ${favoriteSub},
    "nickname": ${nickname}
}
```

* 응답

```
{
    "code": 200,
    "data": {
        "id": ${id},
        "userId": ${userId},
        "userPw": ${userPw},
        "email": ${email},
        "phone": ${phone},
        "birthday": ${birthday},
        "favoriteSub": ${favoriteSub},
        "profileSrc": ${profileSrc},
        "nickname": ${nickname},
    },
    "message": null
}
```

### 4. 회원탈퇴 DELETE /users

* 요청

```
body 필요 없음.
```

* 응답

```
{
    "code": 200,
    "data": null,
    "message": null
}
```

---

## 2. 챌린지

### 1. 챌린지 생성 POST

* 요청

```
{
    "subject": ${subject},
    "title": ${title},
    "intro": ${intro},
    "description": ${description},
    "recruitedCnt": ${recruitedCnt},
    "startDate": ${startDate},
    "endDate": ${endDate}
}
```

* 응답

```
{
    "code": 200,
    "data": {
        "id": ${id},
        "subject": ${subject},
        "title": ${title},
        "intro": ${intro},
        "description": ${description},
        "recruitedCnt": ${recruitedCnt},
        "hits": ${hits},
        "startDate": "2005-10-02T09:00:00",
        "endDate": "2022-10-10T09:00:00",
        "user": {user.nickname},
        "images": []
    },
    "message": null
}
```

### 2. 챌린지 조회 GET challenges

* 요청

```
body 필요 없음.
```

* 응답

```
{
    "code": 200,
    "data": {
        "content": [
            {
                "id": 1,
                "subject": "예술",
                "title": "도서 도전",
                "intro": "도전",
                "description": "규칙2",
                "recruitedCnt": 335,
                "hits": 48780,
                "startDate": "2005-10-02T09:00:00",
                "endDate": "2022-10-10T09:00:00",
                "user": ${user.nickname}
                "images": []
            }
        ],
        "pageable": {
            "pageNumber": 0,
            "pageSize": 1,
            "sort": {
                "empty": false,
                "sorted": true,
                "unsorted": false
            },
            "offset": 0,
            "unpaged": false,
            "paged": true
        },
        "last": false,
        "totalElements": 41,
        "totalPages": 41,
        "size": 1,
        "number": 0,
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "first": true,
        "numberOfElements": 1,
        "empty": false
    },
    "message": null
}
```

### 3. 챌린지 단건 조회 GET challenges/{challenge_id}

* 요청

```
body 필요 없음.
```

* 응답

```
{
    "code": 200,
    "data": {
        "id": ${id},
        "subject": ${subject},
        "title":  ${title},
        "intro":  ${intro},
        "description":  ${description},
        "recruitedCnt":  ${recruitedCnt},
        "hits":  ${hits},
        "startDate": ${startDate},
        "endDate":  ${endDate},
        "user":  ${user.nickname},
        "images": [],
        "auths": [],
        "challengeUsers" []
    },
    "message": null
}
```

### 4. 챌린지 수정 PATCH challenges/{challenge_id}

* 요청

```
{
    "subject": ${subject},
    "title": ${title},
    "intro": ${intro},
    "description": ${description},
    "recruitedCnt": ${recruitedCnt},
    "startDate": ${startDate},
    "endDate": ${endDate}
}
```

* 응답

```
{
    "code": 200,
    "data": {
        "id": ${subject},
        "subject": ${subject},
        "title": ${subject},
        "intro": ${subject},
        "description": ${subject},
        "recruitedCnt": ${subject},
        "hits": ${subject},
        "startDate": ${startDate},
        "endDate": ${endDate}
        "user": ${user.nickname},
        "images": [],
    },
    "message": null
}
```

### 5. 챌린지 삭제 DELETE challenges/{challenge_id}

* 요청

```
body 필요 없음.
```

* 응답

```
{
    "code": 200,
    "data": null,
    "message": null
}
```

## 3. 챌린지 이미지

### 1. 챌린지 이미지 저장 POST /challenge-images/{challenge-id}

* 요청

```
images : img 바이너리 데이터
```

* 응답

```
{
    "code": 200,
    "data": null,
    "message": null
}
```


