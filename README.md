# [Spring 5기] CH 3 일정 관리 앱 JPA   


-------
## API 명세
### 일정 API 명세서
| 기능      | Method | URL             | Request | Response | 상태코드 |
|---------|--------|-----------------|---------|----------|------|
| 일정 등록   | POST   | /api/schedules  | 요청 body | 등록 정보    | 200 OK |
| 일정 단건 조회 | GET    | /api/schedules/{id} |         | 단건 응답 정보 | 200 OK |
| 일정 전체 조회 | GET    | /api/schedules/ |         | 다건 응답 정보 | 200 OK |
| 일정 수정   | PATCH  | /api/schedules/{id} | 요청 body | 수정 정보    | 200 OK |
| 일정 삭제   | DELETE | /api/schedules/{id} |         |          | 200 OK |

###
<details>
    <summary>일정 등록</summary>   

요청: 

```json
{
    "userId": 3,
    "title": "title",
    "content": "content"
}
```

응답: 
```json
{
    "id": 1,
    "username": "name",
    "title": "title",
    "content": "content"
}
```
</details>  


###
<details>
    <summary>일정 단건 조회</summary>   

요청:

응답:
```json
{
  "id": 1,
  "username": "name",
  "title": "title",
  "content": "content"
}
```
</details>  

###
<details>
    <summary>일정 전체 조회</summary>   

요청:

응답:
```json
[
  {
    "id": 1,
    "username": "name",
    "title": "title",
    "content": "content"
  }
]
```
</details>  


###
<details>
    <summary>일정 수정</summary>   

요청:
```json
{
    "userId": 1,
    "title": "title2",
    "content": "content2"
}
```

응답:
```json
[
  {
    "id": 1,
    "username": "name",
    "title": "title",
    "content": "content2"
  }
]
```
</details>  


###
<details>
    <summary>일정 삭제</summary>   

요청: 

응답:

</details>  



##
### 유저 API 명세서
| 기능           | Method | URL               | Request | Response | 상태코드                         |
|--------------|--------|-------------------|---------|----------|------------------------------|
| 유저 회원가입      | POST   | /api/users/signup | 요청 body | 등록 정보    | 200 OK <br/>                 |
| 유저 로그인       | POST    | /api/login        | 요청 body    | 로그인 성공 <br/> 이메일 또는 비밀번호가 일치하지 않습니다.  | 200 OK <br/>401 Unauthorized |
| 유저 단건 조회     | GET    | /api/users/{id}   |         | 단건 응답 정보 | 200 OK                       |
| 유저 전체 조회     | GET    | /api/users/       |         | 다건 응답 정보 | 200 OK                       |
| 유저 수정        | PATCH  | /api/users/{id}   | 요청 body | 수정 정보    | 200 OK                       |
| 유저 삭제        | DELETE | /api/users/{id}   |         |          | 204 No Content               |

###
<details>
    <summary>유저 회원가입</summary> 

요청:

```json
{
    "username": "name",
    "email": "name@gmail.com",
    "password": "name1234"
}
```

응답:
```json
{
  "id": 1,
  "username": "name",
  "email": "name@gmail.com",
  "password": "name1234"
}
```
</details>


####
<details>  
    <summary>유저 로그인</summary>  

요청:

```json
{
    "email": "name@gmail.com",
    "password": "name1234"
}

```

응답:
```
로그인 성공
```
</details>


####
<details>  
    <summary>유저 단건 조회</summary>  

요청: 

응답:  

```json
{
    "id": 1,
    "username": "name",
    "email": "name@gmail.com",
    "password": "name1234"
}
```

</details>


####
<details>
    <summary>유저 전체 조회</summary>  

요청: 

응답:
```json
[
  {
    "id": 1,
    "username": "name",
    "email": "name@gmail.com",
    "password": "name1234"
  }
]
```
</details>


####
<details>
    <summary>유저 수정</summary>  

요청:
```json
{
    "username": "name2",
    "email": "name2@gmail.com",
    "password": "name1234"
}
```

응답:   
```json
{
    "id": 1,
    "username": "name2",
    "email": "name2@gmail.com",
    "password": "name1234"
}
```

</details>



####
<details>
    <summary>유저 삭제</summary> 

요청:  

응답:

</details>   



----------------------
#
## ERD 작성하기  
### 스케줄 ERD & 유저 ERD
![asd](/images/erd.png)

----------------------
#
## SQL 작성하기
```
create table schedule (
    created_at      datetime(6),
    id              bigint not null         auto_increment,
    modified_at     datetime(6),
    user_id         bigint not null,
    content         longtext,
    title           varchar(255) not null,
    primary key (id)
);
```

```
create table user (
    created_at      datetime(6),
    id              bigint not null       auto_increment,
    modified_at     datetime(6),
    email           varchar(255) not null,
    password        varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
);
```

----------------------
#
## 
### Lv 1. 일정 CRUD  `필수`

- [ ]  일정을 생성, 조회, 수정, 삭제할 수 있습니다.
- [ ]  일정은 아래 필드를 가집니다.
    - [ ]  `작성 유저명`, `할일 제목`, `할일 내용`, `작성일`, `수정일` 필드
    - [ ]  `작성일`, `수정일` 필드는 `JPA Auditing`을 활용합니다. → `3주차 JPA Auditing 참고!`
##
### Lv 2. 유저 CRUD  `필수`

- [ ]  유저를 생성, 조회, 수정, 삭제할 수 있습니다.
- [ ]  유저는 아래와 같은 필드를 가집니다.
    - [ ]  `유저명`, `이메일`, `작성일` , `수정일` 필드
    - [ ]  `작성일`, `수정일` 필드는 `JPA Auditing`을 활용합니다.
- [ ]  연관관계 구현
    - [ ]  일정은 이제 `작성 유저명` 필드 대신 `유저 고유 식별자` 필드를 가집니다.

##
### Lv 3. 회원가입  `필수`

- [ ]  유저에 `비밀번호` 필드를 추가합니다.
    - 비밀번호 암호화는 도전 기능에서 수행합니다.

##
### Lv 4. 로그인(인증)  `필수`

- 키워드

  **인터페이스**

    - HttpServletRequest / HttpServletResponse : 각 HTTP 요청에서 주고받는 값들을 담고 있습니다.
- [ ]  **설명**
    - [ ]  **Cookie/Session**을 활용해 로그인 기능을 구현합니다. → `2주차 Servlet Filter 실습 참고!`
    - [ ]  필터를 활용해 인증 처리를 할 수 있습니다.
    - [ ]  `@Configuration` 을 활용해 필터를 등록할 수 있습니다.
- [ ]  **조건**
    - [ ]  `이메일`과 `비밀번호`를 활용해 로그인 기능을 구현합니다.
    - [ ]  회원가입, 로그인 요청은 인증 처리에서 제외합니다.
- [ ]  **예외처리**
    - [ ]  로그인 시 이메일과 비밀번호가 일치하지 않을 경우 HTTP Status code 401을 반환합니다.