# 3장 질주 본능 : 시간 효율성

### Speed1 - O(1)로 수조에 물 넣기

- 기존에는 addWater 메서드를 호출하면 연결된 모든 수조를 돌면서 값을 변경해줘야했다.
- 이런 문제점을 개선하기 위해서 Group 클래스를 만들어서 연결된 모든 Container들이 가진 물의 양을 저장하고 있으면 상수 시간 내에 가능해진다.

이렇게 변경할 경우 connectTo 메서드를 사용해서 연결하게 될 경우  
1. addAll 메서드로 그룹을 합친 뒤,
2. 나머지 한그룹에 속하는 수조들의 그룹을 변경해야한다.

즉, O(n)의 시간복잡도를 갖게 된다.



### Speed2 - O(1)로 수조 연결하기 

수조의 연결 구조를 순환 연결 리스트로 변경해야한다.  
이렇게 되면 연결하려는 2개의 수조의 참조만 변경해주면 되기 때문에 O(1)의 시간복잡도를 가질 수 있다. (_현재 코드는 서로 다른 그룹의 수조끼리 합칠 경우만 가능_)

#### 지연성, 지연 평가
- 필요한 시점까지 계산을 미루는 것.
- 표준 jdk는 즉시성을 띠는데 스트림은 지연성을 띤다. 중간 처리 메서드들은 최종 처리 메서드가 실행하기 전까지는 처리되지 않는다.


### Speed3 - 합집합 찾기 알고리즘
- 집합별로 하나의 대표를 갖는다.
- 두 집합을 병합하는 메서드, 소속된 집합의 대표를 찾는 메서드를 제공해야한다.

대표 수조에 물을 저장하고, getAmount 메서드를 호출하면 대표를 찾아서 물의 양을 반환하도록 만든다.  
핵심은 **부모 포인터 트리 형태**의 구조다. 루트가 대표 수조이며, 트리의 크기도 대표 수조에서 가지고 있어야한다.

1. find 연산 = 경로 압축 기법
   - 경로 압축 기법이란 트리 탐색 도중 만나는 모든 노드를 루트의 직계 자녀로 만드는 방식.
   - 재귀를 사용해서 부모 노드부터 현재 노드까지 모두 루트 노드를 바라보도록 변경하는 것.
2. union 연산 = 크기에 따른 연결 정책
   - 더 작은 트리를 더 큰 트리의 자식으로 병합.
   - 2개 수조의 루트를 비교한 뒤, 다를 경우 더 작은 트리를 더 큰 트리의 루트의 자식으로 연결한다.
   - 트리 높이가  노드 개수의 로그를 넘지 않는다는 것을 보장한다. -> 항상 같은 크기의 트리를 병합!

#### 부모 포인터 트리
- 각 노드가 부모 노드 하나만 가리키는 식의 자료구조.
- 루트 노드는 다른 노드를 가리키지 않는다.
- 자식이 없는 노드를 **리프**라고 부른다.
- 트리의 길이는 루트 <--> 리프 의 길이.


### 분할상환 시간 복잡도
- 일반적인 시간복잡도 분석은 알고리즘을 한번 실행하는 경우를 분석한다. **분할상환 시간 복잡도는 여러번 실행하는 경우를 분석한다.**
- 경로 압축을 하는 케이스가 해당되는데, 처음에는 O(logN) 이지만 이후 다시 사용할때는 O(1)이 된다.

분석 과정
n : 수조 개수
m : 연산 횟수

1. 각 메서드가 몇번 호출될것인지 정한다. (최소 n번 호출)
2. connectTo 메서드는 최대 n-1번


### 돌발 퀴즈

2. 단일 순환 연결 리스트에서 주어진 노드를 제거하는 연산의 복잡도는 어떠한가?
   - O(n) 이라고 생각한다. 주어진 노드를 찾아가야하기 때문에.

3. 삶에서 즉시 해야할 일 2가지, 최대한 지연시켜야 하는 일 2가지??

4. 자바 컴파일러를 만든다는 가정하에 클래스 사이의 상속 관계를 트리로 나타낸다면 **부모 포인터 트리**와 **자식 포인터 트리** 중 어느 방식을 사용해야할까?
   - 부모 포인터 트리. 자식 클래스가 부모를 참조하지만 부모 클래스가 자식을 참조하지는 않기 때문에.

### 연습문제

1-1.
Speed1 : O(1)
Speed2 : O(N)
Speed3 : O(logN)

1-2. 각 인스턴스마다 size값을 두고 연결할때마다 늘리면 groupsize 메서드 자체의 시간복잡도는 O(1)로 만들 수 있을듯.  
말고는 방법을 모르겠음.

2-1
Speed1 : O(1)
Speed2 : O(N)
Speed3 : O(logN)

2-2. 각 값마다 amount를 가지고 있는거라서 구조 자체를 바꾸지 않는 이상 안되지 않을까..?


3.