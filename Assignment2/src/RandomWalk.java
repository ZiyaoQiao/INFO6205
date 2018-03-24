/*
 * Copyright (c) 2017. Phasmid Software
 */
import java.util.Random;

public class RandomWalk {
    private int x = 0;
    private int y = 0;

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
        System.out.println("x:"+x+"\t\t\t"+"y:"+y);
    }

    public void randomWalk(int n) {
        for(int i = 0; i<n ; i++) {
            Random random = new Random();
            int s = random.nextInt(4);
            if (s == 0)
                x++;
            else if (s ==1)
                y++;
            else if (s ==2)
                x--;
            else if (s ==3)
                y--;
          //  System.out.println("x:"+x+"\t\t\t"+"y:"+y);
        }
        // TODO perform n moves
    }

    /*
    return the distance
     */
    public double distance() {
        //double dist = x*x+y*y;
        double dist = Math.sqrt(x*x+y*y);
/*        System.out.println("dist:"+dist);
        System.out.println("-----------------------");*/
        return dist;  // TODO you need to implement this
    }

    /*
    return the distance^2
     */
    public double distance2(){
        double dist2 = (x*x + y*y);
        return dist2;
    }

    public static void main(String[] args){
        RandomWalk a = new RandomWalk();
        int i = 100000;
        int j;
        double R_10 = 0;
        double R2_10 = 0;
        double R_100 = 0;
        double R2_100 = 0;
        double R_1000 = 0;
        double R2_1000 = 0;
        double R_10000 = 0;
        double R2_10000 = 0;
        double R_100000 = 0;
        double R2_100000 = 0;
        for(j = 0; j<i;j++){
            a = new RandomWalk();
            a.randomWalk(10);
            R_10 += a.distance();
            R2_10 += a.distance2();
            a = new RandomWalk();
            a.randomWalk(100);
            R_100 += a.distance();
            R2_100 += a.distance2();
            a = new RandomWalk();
            a.randomWalk(1000);
            R_1000 += a.distance();
            R2_1000 += a.distance2();
            a = new RandomWalk();
            a.randomWalk(10000);
            R_10000 += a.distance();
        R2_10000 += a.distance2();
        a = new RandomWalk();
        a.randomWalk(100000);
        R_100000 += a.distance();
        R2_100000 += a.distance2();
    }

        System.out.println("Average Distance of RandomWalk(10) is: "+R_10/i);
        System.out.println("Average Distance^2 of RandomWalk(10) is: "+R2_10/i);
        System.out.println("Average Distance of RandomWalk(100) is: "+R_100/i);
        System.out.println("Average Distance^2 of RandomWalk(100) is: "+R2_100/i);
        System.out.println("Average Distance of RandomWalk(1000) is: "+R_1000/i);
        System.out.println("Average Distance^2 of RandomWalk(1000) is: "+R2_1000/i);
        System.out.println("Average Distance of RandomWalk(10000) is: "+R_10000/i);
        System.out.println("Average Distance^2 of RandomWalk(10000) is: "+R2_10000/i);
        System.out.println("Average Distance of RandomWalk(100000) is: "+R_100000/i);
        System.out.println("Average Distance^2 of RandomWalk(100000) is: "+R2_100000/i);
    }

}
