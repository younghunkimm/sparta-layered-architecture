# Layered Architecture 실습

📌 Layered Architecture 실습은 [메모장 프로젝트](https://github.com/younghunkimm/sparta-spring-boot-memo-application)와 이어지는 실습입니다.

## 3계층 분리

`Controller` → `Service` → `Repository`

### ✅ 해결한 문제점

Controller의 책임을 Layer별로 분리하였다.

### 💥 문제점

1. 데이터베이스에 영구적으로 데이터가 저장되지 않는다. (Database 접근 기술)

2. 예외 발생시 공통적으로 처리가 불가능하다.
    - 각각의 모든 예외를 try-catch 하여 처리해야 한다.

3. RequestDto, ResponseDto를 공유하여 null값이 들어오기도 한다.
    - 필요없는 필드에 추가적인 null 검사를 해야한다.

4. Spring Bean, 생성자 주입 등 Spring의 동작 원리에 대해 이해하지 못했다.

5. 왜 Interface로 만들어서 구현하여 사용하는지 모른다.
