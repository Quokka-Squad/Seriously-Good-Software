# 8장 스레드

## 8장에서 다루는 내용

> - 교착 상태(deadlock)와 경합 조건 인지하고 회피하기
> - 명시적인 락 사용
> - 락-프리(lock-free) 동기화 사용
> - 클래스를 불변으로 설계하기
>

- 스레드 안정성은 효율성이나 가독성처럼 일반적인 품질 속성으로 간주되지는 않는다. 
  - 하지만 병렬 하드웨어가 대중화되면서 중요성이 커지고 있다.
- 다른 기능적 결함과 달리 스레드 안정성의 결함은 오랫동안 발견되지 않을 수 있다.
  - 특정 타이밍과 스케줄링 상황에서만 경합 조건이 발생해 객체 상태가 더럽혀지거나 교착 상태가 발생해 프로그램이 정지하는 등의 특수한 상황에서만 결함이 겉으로 드러난다.

## 경합 조건

- 동기화를 너무 수행하지 않아서 발생한다.
- 서로 다른 스레드에서 동시에 수행한 두 연산을 때문에 적어도 한 연산의 사후 조건이 깨질 때 경합 조건이 발생한다.
- 공유된 객체를 동기화 없이 조작하면 경합 조건을 쉽게 만들어낼 수 있다.

```java
public class Counter {
    private int n;

    public void increment() {
        n++;
    }
}
```

- 두 스레드가 increment를 거의 동시에 호출하면 n이 한 번만 증가할 수 있다.
- n++는 원자적 연산이 아니라 다음과 같은 3가지 원자적 연산으로 이뤄지기 때문이다.
  1. n의 현재 값을 (레지스터 머신의) 레지스터에 복사하거나 (JVM에서) 스택에 복사한다.
  2. 1을 더한다.
  3. 갱신된 n 값을 해당 Counter 객체에 복사한다.
- 두 스레드가 첫 번째 단계를 동시에 실행한다면(또는 두 스레드 중 하나가 갱신한 값을 저장하기 전에 다른 스레드가 동작한다면) 두 스레드 모두 같은 값 n을 읽어 1을 더한 후 결국 같은 값 n + 1을 두 번 저장한다.

### 원자적 연산이란?

- 더 이상 쪼갤 수 없는 연산이라는 뜻이다. 
- 따라서 여러 스레드, CPU코어에서 같은 변수(메모리)를 수정할 때 서로 영향을 받지 않고 안전하게 연산할 수 있다. 

## 교착 상태 

- 동기화를 지나치게 했을 때 발생한다.
- 둘 이상의 스레드가 서로 순환하는 형태로 영원히 기다리는 것이다.
- 아래의 교착 상태 다루기에서 자세히 설명하겠다.

## 자바의 멀티스레드 지원 

![KakaoTalk_20220226_202142646](https://user-images.githubusercontent.com/92966772/155841330-adc90b78-ca38-4b4d-9b6a-ec3eb466421f.jpg)

 ### 지난 몇 년간 보강된 멀티스레드 기능

#### 실행자(executor) 서비스(자바 5) 

- 사용자 정의 작업을 수행하는 적절한 수의 스레드를 생성하고 관리하는 몇 가지 클래스와 인터페이스다. 
- java.util.concurrent 패키지의 ExecutorService 인터페이스와 Executors 클래스를 확인하자.

#### 포크-조인(fork-join) 프레임워크(자바 7) 

- 복잡한 계산을 여러 스레드로 분산하고(fork) 그 결과를 한 값으로 병합하는(join) 영리한 방법이다.

#### 병렬 스트림(자바 8) 

- 순차적인 데이터 제공자에 복수의 단일(uniform) 연산을 적용하는 강력한 라이브러리다.

## 동시성 레벨

|    이름     |   동시 허용 연산   |        락의 개수         |
| :---------: | :----------------: | :----------------------: |
| 클래스 레벨 | 다른 클래스에 접근 |    클래스 당 락 하나     |
|  객체 레벨  |  다른 객체에 접근  |     객체 당 락 하나      |
| 메서드 레벨 | 다른 메서드에 접근 | 객체의 메서드 당 락 하나 |
|   무제한    |     모든 연산      |       락 필요 없음       |

### 명세 단계 

- 클래스가 동시성을 지원할 수 있는지를 명확하게 한다.
- 즉 경합 조건 없이 동시에 실행 가능한 메서드나 코드 조각을 알아낸다.
- 실용적으로는 서로 다른 데이터를 사용하는 코드 조각이 이에 해당한다. 

### 구현 단계

- 불법적인 동시 접근을 직렬화하고 합법적인 동시 접근만 허용하는 동기화 요소를 추가한다.

### 클래스 레벨 동시성

- 스레드 안정성이 진정으로 유일한 목표라면 전역적 락을 이용해 모든 메서드를 동기화하는 간단한 방법이 있다.
- 자바의 모든 객체에는 암묵적으로 락(모니터)이 제공된다.
- 예를 들어 모든 컨테이너에 대한 전역 락이 필요하다면 Container.class 객체의 락을 이용할 수 있다.
- Container 클래스의 모든 메서드의 몸체를 아래와 같이 synchronized 블록으로 감싸거나 메서드의 선언부에 static synchronized를 넣어주면 된다.

```java
public static void increment() {
    synchronized (Container.class) {
	    n++;
	}
}

public static synchronized void increment() {
    n++;
}
```

- 이렇게 하면 다른 메서드에서 서로 다른 객체의 메서드를 호출해도 동시에 한 번의 호출만 메서드에 진입할 수 있다.
- 이처럼 전역적인 방식은 극단적으로 서툴고 동시성에서 얻을 수 있는 모든 성능 향상을 가로막는다.
- 게다가 락을 획득하고 해제하는 연산 때문에 싱글 스레드 프로그램의 성능까지 떨어진다.
- 이러한 기법을 클래스 레벨 동시성이라고 하는데 아래 표에 요약한 동시성 레벨 중 한쪽 극단에 위치한다.
- 이상적으로는 최대한의 동시성을 달성하면서 스레드 안정성을 보장해야 한다.

### 객체 레벨 동시성

- 어떠한 클래스의 객체가 고립된 경우(객체가 서로 참조하거나 다른 타입의 공유 객체를 참조하지 않으면) 여러 스레드가 한 객체에 접근하지 않는 한 동시에 실행할 수 있으며 모든 인스턴스 메서드에 synchronized 키워드를 넣는 것만으로도 스레드의 안전한 구현이 가능하다.
- 이는 일반적인 객체 레벨 동시성에 해당하며 다음과 같은 평범한 클래스도 이에 속한다.

```java
public class Employee{
    private String name;
    private Date hireDate;
    private int monthSalary;

    public synchronized void increaseSalary(int bonus) {
        monthSalary += bonus;
    }
}
```

- 아래와 같은 코드로 더 개선할 수 있다고 한다. 

### 메서드 레벨 동시성

- Employee 객체를 모니터로 사용하는 게 아닌 monitor 필드를 직접 선언해줘서 모니터로 사용했다.

```java
public class Employee{
    private String name;
    private Date hireDate;
    private int monthSalary;
    private Object monitor = new Object();

    public void increaseSalary(int bonus) {
        synchronized (monitor) {
        	monthSalary += bonus;	
        }
    }
}
```

- 메서드 레벨 동시성은 한 객체의 모든 메서드가 서로 독립적이어야 하기 때문에 매우 이례적이다.
  - 여기서 메서드가 서로 독립적이라는 것은 메서드마다 접근하는 필드가 중복되지 않아야 한다는 뜻이다.
- 모든 메서드가 독립적이라면 이는 응집도가 낮다는 신호로 다른 클래스에 속해야 할 정보를 하나로 묵었을 수 있다.
- 동시성 정책을 논하기 전에 해당 클래스를 여러 클래스로 나누는 것을 고려해야 한다.

### 무제한 레벨 동시성

- 무상태 클래스나 불변 클래스에 적용되는데 두 클래스 모두 여러 스레드에서 동시에 사용해도 무방하다.

## 수조 시스템의 동시성 정책

- 계약을 지키기 위해서는 한 수조가 다른 수조를 참조해야 한다.
  - 구체적으로 말하면 connectTo 메서드와 addWater 메서드는 여러 수조의 상태를 변경해야 하기 때문에 객체 레벨 동시성으로는 스레드 안정성을 달성할 수 없다.

요약하면 Container 클래스의 동시성 정책은 다음과 같다.

1. 클래스는 반드시 스레드 안전해야 한다.
2. 수조 a와 b가 다른 그룹에 속하면 a와 b의 어떠한 메서드 호출도 동시에 수행할 수 있다.
3. 기타 모든 메서드 호출은 동기화해야 한다.

## 교착 상태 다루기

- 한 메서드에서 모니터가 2개 이상 필요할 때 교착 상태가 발생할 위험이 존재한다.
- 교착 상태는 둘 이상의 스레드가 서로 획득한 모니터를 기다려서 순환하는 형태로 영원히 대기하는 상태이다.

![image](https://user-images.githubusercontent.com/92966772/155842244-a801396b-ce98-417d-a5d1-4c31210d9a67.png)

![image](https://user-images.githubusercontent.com/92966772/155842226-78c7b6b2-4cfe-49f2-8a48-2e56a632a452.png)

### 원자적 락 시퀀스

- 원자적 락 시퀀스는 교착 상태를 유발하는 락 획득 시퀀스를 원자적으로 만들 수 있다.
- 두 시퀀스를 동시에 수행할 수 없게 막는 또 다른 락(globalLock)이 필요하다.
- 필요한 락(group 또는 other.group) 중 하나가 잠긴 상태라면 전역 락도 잠겨 있어 다른 시퀀스가 시작할 수 없고 교착 상태의 위험이 사라진다.
- 서로 다른 객체에서도 교착 상태를 막기 위해 static을 통해 전역 락을 선언해 주었다.(클래스 레벨 동시성) 

```java
// 아래 코드는 데드락이 발생할 수 있다.
public class Container {
    private static final ReentrantLock globalLock = new ReentrantLock();
    private Group group = new Group(this);

    public void connectTo(Container other) {
        synchronized (group) {
            synchronized (other.group) {
    //          실제 동작 수행
            }
        }
    }

    public void connectTo(Container other) {
        globalLock.lock();
        synchronized (group) {
            synchronized (other.group) {
                globalLock.unlock();
    //          실제 동작 수행
            }
        }
    }
	
    //생략
}
```

### 순서 있는 락 시퀀스

- 모니터 사이의 전역적인 순서를 정해 모든 스레드가 알 수 있게 하고 모든 스레드는 이 순서대로 락을 획득하게 한다.
- 클래스의 인스턴스를 생성할 때마다 유일한 정수 ID를 할당하면 이러한 전역 시퀀스를 만들 수 있다.
- ID를 할당하는 부분도 경합 조건이 발생할 수 있으니 동기화를 시켜 줘야 한다.
  - 책에서 사용한 AtomicInteger는 스레드 안전하면서도 가변적이다.
  - incrementAndGet 메서드를 이용하면 유일한 순차적 ID를 스레드 안전하게 만들어낼 수 있다.

```java
private static class Group {
        static final AtomicInteger nGroups = new AtomicInteger();
        double amountPerContainer;
        Set<Container> members = new HashSet<>();
        final int id = nGroups.incrementAndGet();
```

- Group 클래스에 전역 시퀀스(nGroups)를 추가했다.
- id 필드도 추가했는데 전역 시퀀스에서 유일한 정수 ID를 생성해서 넣어준다.

```java
public void connectTo(Container other) {
    if (group == other.group) {
        return;
    }

    Object firstMonitor, secondMonitor;
    if (group.id > other.group.id) {
        firstMonitor = other.group;
        secondMonitor = group;
    } else {
        firstMonitor = group;
        secondMonitor = other.group;
    } 
    // 2개의 스레드에서 같은 2개의 모니터를 얻으려고 할 때 
    //		Thread A : 1, 2 / Thread B : 1, 2
    
    // 동시에 반대 방향으로 락을 얻으려고 할 때 발생
    synchronized (firstMonitor) { // Thread A : 1, Thread B : 2
        synchronized (secondMonitor) { // Thread A : 2, Thread B : 1
    //      실제 동작 수행
        }
    }
}
```

- 바뀐 connectTo 메서드는 ID 순서대로 모니터 획득을 요청해서 교착 상태를 회피한다.
  - 교착 상태는 같은 모니터를 서로 다른 순서로 기다리면서 발생하기 때문에 모니터를 일정한 순서대로 요청한다면 발생하지 않는다.

## 숨은 경합 조건

- 순서있는 락 시퀀스를 통해 구현한 connectTo 메서드의 그룹 ID를 비교하고 순서를 정하는 과정은 동기화로 보호되지 않는다.
- 따라서 현재 스레드가 그룹에 해당하는 락을 얻기 전에 두 그룹이 수정될 수 있다.
- 원자적 락 시퀀스를 통해 구현한 connectTo 메서드도 경합 조건으로부터 자유롭지 않다.
- 사실 이 부분은 잘 이해가 안가서 건너뛰겠습니다 ㅎㅎ; 

![KakaoTalk_20220226_142823982](https://user-images.githubusercontent.com/92966772/155830547-2f962793-324d-424f-8ad6-c36023a2ef62.jpg)

## 스레드 안전한 수조

### connectTo 동기화

```java
public void connectTo(Container other) {
    while (true) {
        if (group == other.group) 
            return;
        
        Object firstMonitor, secondMonitor;
        if (group.id > other.group.id) {
            firstMonitor = other.group;
            secondMonitor = group;
        } else {
            firstMonitor = group;
            secondMonitor = other.group;
        }
        synchronized (firstMonitor) {
            synchronized (secondMonitor) {
                if ((firstMonitor == group && secondMonitor == other.group) ||
                    (secondMonitor == group && firstMonitor == other.group)) {
//     		        실제 동작 수행
                }
            }
        }
    }
}
```

### addAmount 동기화

### getAmount 동기화 

## 불변성

- 불변 클래스는 모든 필드를 final로 선언해야 하고 참조하는 다른 모든 클래스도 불변이어야 한다.
  - 모든 공유 객체를 불변 객체로 만든다면 어떠한 스레드도 공유 메모리에 쓰기를 할 수 없으므로 다른 스레드에 영향을 주지 않는다.
- 불변 클래스의 메서드는 필요한 내용을 담은 같은 타입의 객체를 새로 생성해 리턴한다.
  - 위와 같은 이유로 메서드가 값을 리턴하기 전에는 다른 스레드에서 접근할 수 없다.

## 불변 버전 API 설계

- 영속적 자료 구조 : 변경이 필요할 때 새로운 객체를 리턴하는 방식
- 임시적 자료 구조 : 자료 구조를 제자리에서 변경해 과거 이력을 제공하지 않는 방식 (사실 뭔지 잘 모르겠다.)
- 영속적 자료 구조가 임시적 자료 구조보다 많은 기능을 제공하기 때문에 일반적으로 공간 효율성과 시간 효율성이 떨어진다.

```java
ContainerSystem s1 = new ContainerSystem(10);
Container c = s1.getContainer(5);
ContainerSystem s2 = s1.addWater(c, 42);
```

- 위와 같은 코드로 불변 클래스를 만들어서 동시성 문제를 해결하려고 했지만 불변 클래스 특성상 `s2.addWater(c, 5)`를 수행할 수 없다. (수조 c는 s1에만 포함되어 있기 때문이다.)
- 그래서 책에서는 Container 대신 수조를 식별하는 ID를 사용하기로 했다.

### 불변 버전 API 설계 결과

```java
public class ContainerSystem {
    private final int group[];
    private final double amount[];

    public ContainerSystem(int containerCount) 
    public ContainerSystem addContainer() 
    public int containerCount() 
        
    public ContainerSystem connect(int containerID1, int containerID2)
    public double getAmount(int containerID)
    public ContainerSystem addWater(int containerID, double amount) 
}
```

`ContainerSystem ContainerSystem(int containerCount)` : containerCount 크기의 수조가 담긴 ContainerSystem을 생성한다.

`ContainerSystem addContainer()` : 수조 1개를 추가한 ContainerSystem을 반환한다.

`int containerCount()` : ContainerSystem에 들어있는 수조의 개수를 반환한다. 

`ContainerSystem connect(int containerID1, int containerID2)` : 입력받은 수조의 아이디를 통해 수조를 찾아서 연결한 ContainerSystem을 반환한다.

`double getAmount(int containerID)` : 입력받은 수조의 아이디를 통해 수조를 찾고 물의 양을 반환한다.

`ContainerSystem addWater(int containerID, double amount) ` :  입력받은 수조의 아이디를 통해 수조를 찾고 물을 추가한다.

### 불변 수조 구현

1. 가변 구현에서 모든 수조에 퍼져 있던 데이터를 수조 시스템으로 모은다.
2. (addWater와 connectTo를 비롯한) 변경 연산을 수행할 때 전체 자료 구조의 복사본을 수정해 리턴한다.

- 가변 구현을 불변으로 바꿀 때는 위와 같이 쓰기를 할 때 복사(copy--on-write) 기법을 적용할 수 있다.
- 이는 가변 클래스를 불변으로 바꾸는 가장 간단한 방법이지만 일반적으로 가장 효율적인 방법은 아니다.
- 더 세련된 방식은 변경 연산을 수행할 때 전혀 새로운 사본을 만드는 대신 이전 객체에서 최대한 많은 부분을 재활용 하는 것이다.
- 수조 연산에서 불변성을 더 영리하게 구현하려면 정말 필요한 수조만 복제할 수 있다.
- 즉 주어진 변경 연산(addWater와 connectTo)의 영향을 받은 수조 그룹만 복제하고 나머지 수조는 addWater나 connectTo로 변경될 때까지 재사용한다.

## 요약

- 합리적인 동시성 정책은 스레드 안정성에 지극히 필수적이다.
- 스레드 안정성의 가장 큰 적은 경합 조건과 교착 상태다.
- 전역 락이나 순서 있는 락 정책을 이용해 교착 상태를 회피할 수 있다.
- 암묵적 락과 달리 명시적 락은 원하는 순서대로 획득하고 해제할 수 있다.
- 불변성은 스레드 안정성의 대안적 방법이다.

## 돌발 퀴즈

### 돌발 퀴즈 1

Q) 클래스의 사용자와 구현자 중 어느 쪽이 동시성 정책을 고려해야 할까?

A) 구현자가 동시성 정책을 고려해야 한다고 생각한다.

### 돌발 퀴즈 2

Q) 각 스레드가 한 번에 하나의 락만 획득할 때도 교착 상태가 발생할 수 있을까?

A) 

### 돌발 퀴즈 3

Q) synchronized 블록 안에서 예외가 발생하면 어떻게 될까? ReentrantLock을 소유한 스레드가 예외를 던지면 어떻게 될까?

A) 

### 돌발 퀴즈 4

Q) 순서 있는 락 기법이 교착 상태를 방지하는 이유는 무엇인가?

A) 

### 돌발 퀴즈 5

Q) 불변 클래스가 자연적으로 스레드 안전한 이유는 무엇인가?

A) 불변 클래스의 상태(필드)가 항상 변하지 않기 때문에 외부에서 값을 읽어오는 행동밖에 하지 못하기 때문이다.

## 연습 문제

### 연습 문제 1

Q) 다음과 같은 Thread의 서브클래스는 정수 배열의 모든 요소를 1 증가시킨다. 

​	그리고 보다시피 이 클래스의 모든 인스턴스는 한 배열을 공유한다.

```java
class MyThread extends Thread {
    private static int[] array = new int[?] // 초기 값 지정

    public void run() {
        //          ____1____
        for (int i = 0; i < array.length; i++) {
            //              ____2____
            array[i]++;
            //              ____3____
        }
        //          ____4____
    }
}
```

MyThread의 인스턴스 2개를 생성하고 두 스레드에서 병렬로 실행해 배열의 각 요소를 정확하게 두 번 증가시키는 프로그램을 작성하려고 한다.

모든 경합 조건을 제거해 프로그램이 올바로 동작하도록 다음 중 빈칸을 제대로 채운 보기를 골라보라. (보기를 여러 개 선택할 수 있다.)

(a) 1 = "synchronized (this) {}"				4 = "}"

(b) 1 = "synchronized (\) {}"				  	4 = "}"

(c) 1 = "synchronized (array) {}"			  4 = "}"

(d) 2 = "synchronized (this) {}"				3 = "}"

(e) 2 = "synchronized (array) {}"			  3 = "}"

(f) 2 = "synchronized (array[i]) {}"			3 = "}"

A) 

### 연습 문제 2

Q) 두 객체를 저장하며 다음과 같은 메서드를 제공하는 스레드 안전한 클래스 AtomicPair를 설계하라.

```java
public class AtomicPair<S, T> {
    public void setBoth(S first, T second);
    public S getFirst();
    public T getSecond();
}
```

따라야 할 동시성 정책은 이렇다. setBoth 호출은 원자적이어야 한다. 

즉 어떠한 스레드에서 setBoth(a, b)를 호출한 후 getFirst나 getSecond를 호출하면 갱신된 값을 리턴해야 한다.

A) 

### 연습 문제 3

Q) 간단한 소셜 네트워크의 각 사용자는 친구의 집합을 저장하며 친구 관계는 대칭적이다.

​	그 구현은 다음과 같은 클래스를 바탕으로 한다.

```java
public class SocialUser {
    private final String name;
    private final Set<SocialUser> friends = new HashSet<>();

    public SocialUser(String name) {
        this.name = name;
    }

    public synchronized void befriend(SocialUser other) {
        friends.add(other);
        synchronized (other) {
            other.friends.add(this);
        }
    }

    public synchronized boolean isFriend(SocialUser other) {
        return friends.contains(other);
    }
}
```

​	불행하게도 여러 스레드가 동시에 친구 관계를 맺으려고 하면 시스템이 멈추고 재시작해야 한다. 

​	그 이유를 알겠는가? SocialUser를 리팩터링해 문제를 고칠 수 있는가?

A) 

### 연습 문제 4

Q) 하루 안의 시각을 시, 분, 초로 표현하는 가변 클래스 Time을 가정해보자.

- public void addNoWrapping(Time delta) : 주어진 지연 시간을 더하되 최대값은 자정 1초 전(23:59:59)으로 한다.
- public void addAndWrapAround(Time delta) : 주어진 지연 시간을 더하되 자정부터는 00:00:00부터 다시 순환한다.
- public void subtractNoWrapping(Time delta) : 주어진 지연 시간을 빼되 최소값은 00:00:00이다.
- public void subtractAndWrapAround(Time delta) : 주어진 지연 시간을 빼되 0시보다 작아지면 반대로 순환한다.

​	위의 API를 불변 버전으로 바꿔 구현하라.

A)
