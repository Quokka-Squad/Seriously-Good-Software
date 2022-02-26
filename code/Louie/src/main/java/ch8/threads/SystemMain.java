package ch8.threads;

public class SystemMain {
    public static void main(String ... args) {
        ContainerSystem s = new ContainerSystem(10);
        System.out.println(s);
        int a = 1, b = 4;
        s = s.addWater(a, 7);
        System.out.println(s);
        s = s.connect(a, b);
        System.out.println(s);
    }
}
