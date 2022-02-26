# 스레드 안전성

- 교착 상태(deadlock)와 경합 조건 인지하고 회피하기
- 명시적인 락 사용
- 락-프리 동기화 사용
- 클래스를 불변으로 설계하기

## 스레드 안전성 달성의 어려움

- 경합 조건
    - 동기화를 너무 수행하지 않아서 발생
- 교착 상태
    - 동기화를 지나치게 했을 때 발생

### 동시성 레벨

```java
sychronized (Container.class) {
    // 메서드 몸체
}
```

- 스레드 안정성을 위해
    - 전역적 락을 이용해 모든 메서드를 동기화하는 것
    - sycronized 블록으로 클래스를 감싸면 클래스 전체에 대한 모든 접근이 직렬화된다.
        - 이점 : 다른 메서드에서 서로 다른 객체의 메서드를 호출해도 동시에 한 번의 호출만 메서드 몸체로 진입할 수 있다.
        - 단점 : 전역적인 방식은 극단적이며 동시성에서 얻을 수 있는 모든 성능 향상을 가로막는다.
            - 락을 걸고 해제하는 연산 때문에 싱글 스레드 프로그램의 성능또한 떨어진다.

- 이상적으로는 최대한 동시성을 달성하면서 스레드 안전성을 보장해야한다.
    1. 명세 단계
        - 클래스가 동시성을 지원할 수 있는지를 명확하게 함.
        - 서로 다른 데이터를 사용하는 코드가 이에 해당
    2. 구현 단계
        - 불법적인 동시 접근 -> 직렬화
        - 합법적인 동시 접근만 허용하는 동기화 요소 추가
    
```java
public class Employee {
    private String name;
    private Date hireDate;
    private int monthlySalary;

    public synchronized increaseSalary(int bonus) {
        montlySalary += bounus;
    }
}
```

- 여러 스레드가 한 객체에 접근하지 않는 한 동시에 실행할 수 있다
- 모든 인스턴스 메서드에 `sychronized` 키워드를 넣는 것으로 스레드의 안전한 구현이 가능

```java
public class Employee {
    private String name;
    private Date hireDate;
    private int monthlySalary;
    private Object monitor = new Object();

    public synchronized increaseSalary(int bonus) {
        sychronized (monitor) {
            montlySalary += bounus;
        }
    }
}
```

- 동기화를 비공개 구현으로 감춰야한다.
- 메서드 전체를 `sychronized` 로 지정하지 않는다.

## 교착 상태 다루기




### 돌발 퀴즈 1

Q) 클래스의 사용자와 구현자 중 어느 쪽이 동시성 정책을 고려해야 할까? <br>
-> 그래서 동시성 정책이 뭔데라는 생각이 들어 답을 보겠읍니다. <br>
<br>
-> 클래스의 사용자는 클래스가 스레드 안전한지 여부만 알면 된다. 나머지 동시성 정책은 구현자 몫이다. 하지만 실용적 관점에서 사용자도 클래스 성능을 가늠할 목적으로 동시성 정책에 관심을 보일 수 있다. Ok! <br>
