# readme.md

# grip-assignment

## 0. 개발 환경

- JAVA 17
- Spring Boot v2.7.3
- Gradle v7.5

## 1. 프로젝트 구조

- 멀티 모듈 프로젝트

  ![SCRAP](resources/project_structure.png)

- root 모듈(open-api)는 core, web이라는 하위 프로젝트를 관리한다.
- core 모듈은 핵심 도메인에 관련된 내용이고, web은 core에 의존한다.
- web 모듈은 프리젠테이션 레이어에 관련된 모듈이다.


## 2. 애플리케이션 실행 방법

```bash
java -jar open-api.jar
```


## 3. 애플리케이션 구현 과정 및 선택 구현 사항

- 검색 랭킹
  - V1 레디스에 Atomic 자료형을 사용하여 동시성 처리
  - V2 레디스 zset 자료구조를 활용
  
- 컨텐츠 조회 서비스
  - 컨트롤러 입출력 모델
      - kakao developers 개발 가이드를 참고하여 searchRequest, searchResponse를 일반화하여 blog, book, cafe등에도 적용가능하도록 설계

        ![SCRAP](resources/search_request_response.png)
      
  - 비즈니스 플로우
    - request history를 DB에 저장
    - 레디스에 검색결과에 따른 count 추가
    - 트래픽이 많고, 저장되어 있는 데이터가 많음을 염두하여, 10초간 최근 검색한 결과내용 레디스에 캐싱
  
  - 설계시 고려사항
    - facade 계층을 추가하여, 컨트롤러단에서 내부 서비스 플로우를 숨겨서 컨트롤러단을 단순화시킴
    - 추후 카카오 API 이외에 새로운 검색 소스가 추가될 경우를 대비하여 '카카오 검색 서비스'를 인터페이스에 의존하여 구현 
            

## 4. API 명세 및 테스트 예시

아래의 엔드포인트와 HTTP 메서드, 요청 본문으로 테스트 가능

- api 명세 간단 요약

| Method | Request URI        | Description    |
|--------|--------------------|----------------|
| GET    | api/v1/search/blog | 블로그 조회 V1         |
| GET    | api/v1/search/rank | 검색 랭킹 조회 V1       |
| GET    | api/v2/search/blog | 블로그 조회 V2         |
| GET    | api/v2/search/rank | 검색 랭킹 조회 V2       |


# 

### **1) 블로그 조회 V1**

레디스에 Atomic 자료형을 사용하여 검색조회 카운트 동시성 처리

# 

```json
// POST  localhost:8080/api/v1/scraps

{
    "userId": 1,
    "imageUrl": "https://news.samsungdisplay.com/wp-content/uploads/2018/08/8.jpg",
    "title": "title123",
    "content": "content123"
}

----------------------- ↓ response ↓ -----------------------

{
    "statusCode": 201,
    "message": "create scrap success.",
    "data": {
        "scrapId": 4,
        "userId": 1,
        "imageUrl": "localhost:8080/resources/46507f97-8706-46fa-bd7b-3f0cb08c4fc5.jpg",
        "title": "title123",
        "content": "content123",
        "views": 0,
        "createdAt": "2022-08-24 00:07:00",
        "modifiedAt": "2022-08-24 00:07:00"
    }
}

```

# 

### **2) 검색 랭킹 조회 V1**

QueryDsl을 활용하여 History테이블의 검색어별로 Grouping하고 Ordering하여 결과 리턴 

# 

```json
GET  localhost:8080/api/v1/scraps?page=2&size=3

----------------------- ↓ response ↓ -----------------------

{
    "statusCode": 200,
    "message": "get all scrap summary success.",
    "data": [
        {
            "scrapId": 7,
            "userId": 1,
            "thumbnailUrl": "localhost:8080/resources/thumb_15ce1631-670c-4aaa-b4a3-7d3f989ff847.jpg",
            "title": "title123",
            "views": 0,
            "createdAt": "2022-08-24 00:08:26",
            "modifiedAt": "2022-08-24 00:08:26"
        },
        {
            "scrapId": 8,
            "userId": 1,
            "thumbnailUrl": "localhost:8080/resources/thumb_d5cb426a-6a0a-46ac-8030-818c5affc759.jpg",
            "title": "title123",
            "views": 0,
            "createdAt": "2022-08-24 00:08:27",
            "modifiedAt": "2022-08-24 00:08:27"
        },
        {
            "scrapId": 9,
            "userId": 1,
            "thumbnailUrl": "localhost:8080/resources/thumb_9419b82f-e3e0-4bd0-9869-8f39106b2e70.jpg",
            "title": "title123",
            "views": 0,
            "createdAt": "2022-08-24 00:08:28",
            "modifiedAt": "2022-08-24 00:08:28"
        }
    ]
}
```

# 

### **3) 블로그 조회 V2**

Redis Sorted Set에 검색어별 카운트 조회수 반영

# 

```json
GET  localhost:8080/api/v1/scraps/2

----------------------- ↓ response ↓ -----------------------

{
    "statusCode": 200,
    "message": "get scrap details success.",
    "data": {
        "scrapId": 2,
        "userId": 1,
        "imageUrl": "localhost:8080/resources/18ad4195-9588-4958-8b3f-43dfd3198ad4.jpg",
        "title": "title123",
        "content": "content123",
        "views": 2,
        "createdAt": "2022-08-24 00:01:11",
        "modifiedAt": "2022-08-24 00:01:11"
    }
}

```

# 

### **4) 검색 랭킹 조회 V2**

Redis Sorted Set을 활용하여 검색어 별 랭킹 제공

# 

```json
PATCH  localhost:8080/api/v1/scraps/2

{
    "userId": 1,
    "title": "after-title"
}

----------------------- ↓ response ↓ -----------------------

{
    "statusCode": 200,
    "message": "patch scrap success.",
    "data": {
        "scrapId": 2,
        "userId": 1,
        "imageUrl": "localhost:8080/resources/18ad4195-9588-4958-8b3f-43dfd3198ad4.jpg",
        "title": "after-title",
        "content": "content123",
        "views": 2,
        "createdAt": "2022-08-24 00:01:11",
        "modifiedAt": "2022-08-24 00:10:40"
    }
}
```

## TODO

- 블로그 조회 V1
- 검색 랭킹 조회 V1
- 블로그 조회 V2
- 검색 랭킹 조회 V2
