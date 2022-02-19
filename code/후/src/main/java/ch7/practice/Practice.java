package ch7.practice;

import java.util.Arrays;
import java.util.Stack;

public class Practice {

    /**
     * 문자열 s에서 문자 c가 나타나는 수를 세어 반환한다.
     * @param s 문자열
     * @param c 몇 번 나타는지 세어볼 문자
     * @return 문자열 s에서 문자 c가 나타나는 수
     */
    public static int countOccurrences(String s, char c) {
        return (int) s.chars()
            .filter(character -> character == c)
            .count();
    }

    /**
     * BFS를 코드로 구현해 사용
     *
     * @param a 그래프 탐색을 수행할 구조. 인접 행렬 등
     * @param vertices 사용할 정점
     * @param source 소스
     */
    public static void bfsImplement(byte [][] a,int vertices,int source){  // 인접 행렬과 정점의 수를 전달
        byte []b=new byte[vertices];    // 각 정점의 상태를 저장할 플래그 컨테이너
        Arrays.fill(b,(byte)-1);   // 상태 초기화
       /*       code   status
                -1  =  ready
                 0  =  waiting
                 1  =  processed       */

        Stack<Integer> st = new Stack<>();     // 운영 스택
        st.push(source);                                     // 소스 할당
        while(!st.isEmpty()){
            b[st.peek()]=(byte)0;                      // 대기 상태 할당
            System.out.println(st.peek());
            int pop=st.peek();
            b[pop]=(byte)1;               // 처리된 상태로 썰정
            st.pop();                  // 큐의 헤드 제거
            for(int i=0;i<vertices;i++){
                if(a[pop][i]!=0 && b[i]!=(byte)0 && b[i]!=(byte)1 ){
                    st.push(i);
                    b[i]=(byte)0;                        // 대기 상태 할당
                }}}
    }
}
