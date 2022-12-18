# recody-backend
Recody 서버 레포입니다. 

## API Doc
https://documenter.getpostman.com/view/16051246/VUjQoQwR

## 기술

- Java 11, Gradle
- Spring Boot, Spring Data JPA, QueryDsl
- JUnit, AssertJ, Mockito
- Git, Docker, Mapstruct, Lombok
- MySQL, AWS RDS
- Kafka, ECS
- Application CI/CD: Github Actions, AWS Beanstalk
- Monitering: AWS CloudWatch, Prometheus, Grafana

## Service Architecture
![monolith](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fc3f39c2f-4508-4d3e-b652-3d0a137a462e%2FRecody-Recody_Modular_Monolith.png?table=block&id=fb0df6c5-9260-49e1-83e4-f6efa1282a2e&spaceId=b64697ca-c3cf-4a8b-a2bc-34db0da0bef9&width=2000&userId=85676345-7838-4f4f-a5ab-a8781c6ff651)


## ERD
> 개발되는 대로 업데이트 중에 있습니다. 

#### Catalog
![catalog erd image](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F8183dcea-365c-4d3d-8ac7-e341002f0a48%2FUntitled.png?id=e32e1da6-8204-474a-ae0e-461665dd94a6&table=block&spaceId=b64697ca-c3cf-4a8b-a2bc-34db0da0bef9&width=2000&userId=85676345-7838-4f4f-a5ab-a8781c6ff651)


#### Movie
![movie erd image](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fbf377995-c444-43de-8226-a85de503dcb7%2FUntitled.png?id=54d5e51a-97a5-443e-bca6-8736c532eb22&table=block&spaceId=b64697ca-c3cf-4a8b-a2bc-34db0da0bef9&width=2000&userId=85676345-7838-4f4f-a5ab-a8781c6ff651)


#### Drama
![drama_erd_image](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F21a9fffe-540e-4794-a919-05d7a9b9dcc2%2FUntitled.png?table=block&id=7975f0d8-88b8-46e9-ab10-637875e39256&spaceId=b64697ca-c3cf-4a8b-a2bc-34db0da0bef9&width=2000&userId=85676345-7838-4f4f-a5ab-a8781c6ff651)


#### Users
![users_erd_image](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fbecb4ccb-424c-4305-a1e4-432dd089c65e%2FUntitled.png?table=block&id=b6ae95bb-9e2d-4c67-944c-2c6ae28b8f0a&spaceId=b64697ca-c3cf-4a8b-a2bc-34db0da0bef9&width=2000&userId=85676345-7838-4f4f-a5ab-a8781c6ff651)

