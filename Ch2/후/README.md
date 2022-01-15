# 2장_레퍼런 구현

### ✔️ check point

- 표준 컬렉션 사용
- 소프트웨어 설계를 표현하는 다이어그램 그리기
- big-O 표현법으로 성능 표현하기
- 클래스의 메모리 사용량 측정하기

---

## JCF(Java Collection Framework)

![https://s3.us-west-2.amazonaws.com/secure.notion-static.com/3c9d8617-ba15-459a-a54e-e279764f9177/CollectionsFramework.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220114%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220114T121351Z&X-Amz-Expires=86400&X-Amz-Signature=33444809839d5d0560bdce7ed20c07d900934a339704bb5a691bdd0504daedc2&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22CollectionsFramework.png%22&x-id=GetObject](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/3c9d8617-ba15-459a-a54e-e279764f9177/CollectionsFramework.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220114%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220114T121351Z&X-Amz-Expires=86400&X-Amz-Signature=33444809839d5d0560bdce7ed20c07d900934a339704bb5a691bdd0504daedc2&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22CollectionsFramework.png%22&x-id=GetObject)

## 소프트웨어 설계를 표현하는 다이어그램

- UML(Unified Modeling Language, 범용 모델링 언어) : 소프트웨어 시스템의 다양한 측면을 표현할 수 있는 풍부한 다이어그램을 제공하는 표준. 클래스
  다이어그램과 시퀀스 다이어그램이 가장 많이 쓰인다

### UML 클래스 다이어그램

- 상속과 포함을 비롯한 클래스 사이의 관계를 보여줌
- Class Box : 필드와 메서드, 가시성을 포함. 자기설명적
- Class Box 사이의 선 : 클래스 간 연관 관계를 나타냄
- 시스템의 모델을 기술할 뿐 시스템 자체를 기술하지는 X

### UML 객체 다이어그램

- 클래스 다이어그램과 비슷하지만, 클래스와 달리 각자 이름이 부여되며 실행한 후의 상태를 보여줌

### 메모리 레이아웃 다이어그램

- 각 메모리 표현을 이해할 수 있도록 데이터가 메모리에 실체화된 모습을 도식화하여 표현한 것
- UML 객체 다이어그램과 비슷하며, 두 다이어그램 모두 객체의 필드와 그들 사이의 관계를 포함한 객체의 스냅샷을 표현

## 메모리

- 객체의 정확한 크기를 계산하는 데 필요한 3가지 요소 → 아키텍처와 JDK 제공자마다 다름
    1. 참조의 크기
        - 언어 명세에 명시X. `32bit` 또는 `64bit`
        - Compressed OOPs(Ordinary Object Pointers, 일반 객체 포인터) 기술 때문에 64비트 프로세서에서도 32비트일 수 있음(이 경우
          해당 참조가 접근 가능한 힙 공간은 32GB로 제한)
    2. 객체 헤더(object header)
        - 모든 객체의 메모리 레이아웃은 JVM이 요구하는 표준 정보를 포함하기 때문에 필드가 없는 객체(빈 객체)라도 메모리 차지
        - 헤더의 구성은 리플렉션, 멀티스레딩, 가비지 컬렉션과 주로 관련 있음
    3. 패딩(padding)
        - 2의 제곱수의 배수(보통 4나 8)인 주소를 사용해야 하드웨어가 효율적으로 작동하기 때문에 객체의 각 필드와 객체 자체의 크기가 `word` 단위에 정렬되도록
          삽입하는 빈 공간
            - `word` : 컴퓨터가 한번 처리할 수 있는 명령단위
- 이 요소들이 객체의 크기에 영향을 미치는 방식도 JVM에 따라 다름
- 메모리 사용량을 분석할 때는 오라클의 표준 JVMdls HotSpot을 기준으로,
    - 참조 크기를 `32bit`로 가정
    - 객체 하나당 `12byte`의 공간을 차지한다고 가정. 배열은 `16byte`로 가정(배열의 길이 포함)
    -
        + 패딩(책에서는 무시)
- `JDK`는 주어진 클래스의 객체에 대해 객체 헤더를 포함한 내부적인 메모리 레이아웃을 조사할 수 있는 도구인 `JOL`(Java Object Layout)을 포함

## 시간 복잡도

- 실행 시간은 같은 프로그램이라도 어떠한 컴퓨터에서 실행되느냐에 따라 매우 다르게 측정되기 때문에 실제 실행 시간을 측정하는 대신 프로그램의 basic step을 실행하는 횟수를
  측정
    - basic step : 실행하는데 상수 시간이 소요되는 모든 연산(ex. 산술 연산, 비교 연산)
- 입력에 따라 같은 함수라도 수행해야 할 basic step 수가 달라질 수 있기 때문에 최대한 많은 단계가 실행되는 최악의 경우만 고려 → big-O
    - O(1) : 상수 시간
        - ex) 배열의 첫 번째 요소가 0인지 확인
    - O(log n) : 로그 시간
        - ex) 이진 탐색
    - O(n) : 선형 시간
        - ex) 정렬되지 않은 배열에서 최대값 찾기(배열 모든 요소를 한번 탐색해야함)
    - O(n log n) : 유사 선형 시간
        - ex) 병합 정렬로 배열 정렬하기
    - O(n^2) : 이차 시간
        - ex) 버블 정렬로 배열 정렬하기
