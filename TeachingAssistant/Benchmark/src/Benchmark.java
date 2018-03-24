import java.util.function.Function;

public class Benchmark<T> {

    public Benchmark (Function<T,Void> f) {
        this.f = f;
    }

    public double run(T t, int n) {
        long x1 = System.nanoTime();
        while(n-- > 0){
            f.apply(t);
        }
        long x2 = System.nanoTime();
        return x2 - x1;
    }

    private final Function<T, Void> f;

    public static Comparable[] generate(int n){
        Comparable[] array = new Comparable[n];

        for (int i = 0; i < n; i++){

        }
        return array;

    }




    public static void main(String[] args) {
        Function<Integer,Void>sort = (x) -> {insertionSort.sort(Benchmark.generate(x)); return null; };
        Benchmark bm = new Benchmark(sort);
        double x = bm.run(20, 100);
        System.out.println(x+" millisecs");
    }
}