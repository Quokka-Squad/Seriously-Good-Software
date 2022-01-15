public class Container {

    double x;   // 수조에 담긴 물의 양
    Container[] group; // 연결된 수조의 그룹
    int n;   // 그룹의 실제 크기

    public Container() {
        group = new Container[1000];    // (?) 갑자기 주의 매직넘버
        group[0] = this;    // 수조를 그룹에 추가
        n = 1;
        x = 0;
    }

    public double getAmount () {
        return x;
    }

    public void addWater(double x) {
        double y = x / n;
        for (int i = 0; i < n; i++) {
            group[i].x = group[i].x + y;
        }
    }

    public void connectTo(Container c) {
        double z = (x * n + c.x * c.n) / (n + c.n); // 병합 후 수조 하나에 담길 물의 양

        for (int i = 0; i < n; i++) {   // 첫 번째 그룹의 수조 g[i]에 대해
            for (int j = 0; j < c.n; j++) { // 두 번째 그룹의 수조 c.g[j] 에 대 해
                group[i].group[n + j] = c.group[j]; // c.g[j]를 g[i]의 그룹에 추가
                c.group[j].group[c.n + i] = group[i]; // g[i] 를 c.g[j]의 그룹에 추가
            }
        }

        n += c.n;

        for (int i = 0; i < n; i++) {   // 그룹의 크기와 물의 양 갱신
            group[i].n = n;
            group[i].x = z;
        }
    }
}
