import java.util.Random;

public class hashTest {

    public static void main(String[] args) {
        int m = 1000000;
        int runTime = 100;


        int c1Total = 0;
        int b0Total = 0;
        double[] hash = new double[m];
        for (int k = 0; k < runTime; k++) {
            int isFilled = 0;
            int c1 = 0;
            int b0 = 0;

            for (int i = 0; i < m; i++) {
                hash[i] = 0;
            }
            Random random = new Random();
            while (true) {
                c1++;
                b0++;
                double rand = random.nextDouble();
                int loc = Math.abs(Double.hashCode(rand)) % m;
                if (hash[loc] != 0)
                    break;
                hash[loc] = 1;
                isFilled++;
            }
            while (true) {
                b0++;
                double rand = random.nextDouble();
                int loc = Math.abs(Double.hashCode(rand)) % m;
                if (hash[loc] == 0) {
                    isFilled++;
                    hash[loc] = 1;
                }
                if (isFilled == m)
                    break;
            }
            c1Total += c1;
            b0Total += b0;
        }
        System.out.println("c1:" + c1Total / runTime + " cal:" + Math.sqrt(Math.PI * m / 2) + " ratio:" + (c1Total/runTime)/(Math.sqrt(Math.PI * m / 2)));
        System.out.println("b0:" + b0Total / runTime + " cal:" + m * Math.log(m) + " ratio:" + (b0Total/runTime)/(m * Math.log(m)));
    }
}

