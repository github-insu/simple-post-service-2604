# Post Domain Service
## 1] Overview
> Post Domain Service는 게시글(Post) 도메인의 비즈니스 로직을 담당하는 서비스입니다.  
> 외부와의 통신은 gRPC를 통해 이루어지며, BFF로부터 요청을 받아 도메인 규칙을 처리합니다.
- GraphQL BFF로부터 gRPC 요청을 수신합니다.  
- 게시글 도메인 로직을 처리합니다.  
- 데이터 저장 및 조회를 책임집니다.

## 2] Responsibilities
- 게시글 CRUD를 담당합니다.
- 도메인 규칙을 합니다.  
cf. 의도적 설계: 필드를 최소화했으며 VO를 사용하지 않고 간단하게 설계하려고 시도합니다.

## 3] Architecture
Hexagonal Architecture(Ports & Adapters)를 적용하여 도메인 로직과 외부 기술을 분리합니다.
```
 ┌────────────────────┐
 │   gRPC Adapter     │ (inbound)
 └─────────┬──────────┘
           ↓
 ┌────────────────────┐
 │    Application     │ (UseCase)
 └─────────┬──────────┘
           ↓
 ┌────────────────────┐
 │       Domain       │
 └─────────┬──────────┘
           ↓
 ┌────────────────────┐
 │ Persistence Adapter│ (JPA)
 └────────────────────┘
```

## 4] Key Design Decisions  
**Hexagonal Architecture**  
- 외부 의존성(gRPC, DB)을 Adapter로 분리합니다.  
- Domain Layer는 기술에 의존하지 않습니다.

**gRPC 기반 통신**
- BFF와의 명확한 계약 기반으로 통신합니다.
- 고성능 내부 서비스 통신을 합니다.

## 5] Tech Stack
- 언어: Java
- 프레임워크: Spring Boot
- 통신: gRPC
- ORM: JPA
- DB: PostgreSQL
