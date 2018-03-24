package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
public class Main {

//    public static void main(String[] args) {
//
//        if (args.length>0) {
//            ParSort.cutoff = Integer.parseInt(args[0]);
//        }
//
//        Random random = new Random(0L);
//        int[] array = new int[2000];
//
//        for (int i = 0; i < array.length; i++) {
//            array[i] = random.nextInt(10000);
//        }
//
//        ParSort.sort(array, 0, array.length);
//
//        for (int i : array) {
//            System.out.println(i);
//        }
//
//        if (array[0]==11) {
//            System.out.println("Success!");
//        }
//    }

    public static void main(String[] args) {

        //if (args.length>0) ParSort.cutoff = Integer.parseInt(args[0]);
        Random random = new Random();
        int[] array = new int[2000000];
        ArrayList<Long> timeList = new ArrayList<>();
        for(int j = 0; j<300; j++) {
            ParSort.cutoff = 10000*(j+1);
            // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
            long time;
            long startTime = System.currentTimeMillis();
            for(int t = 0; t<10;t++) {
                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                ParSort.sort(array, 0, array.length);
            }
            long endTime = System.currentTimeMillis();
            time = (endTime-startTime);
            timeList.add(time);


            System.out.println("cutoff：" +(ParSort.cutoff) + "\t\t10times Time:"+ time + "ms");

            //for (int i : array) System.out.println(i);
            //if (array[0] == 11) System.out.println("Success!");
        }
        try {
            FileOutputStream fis = new FileOutputStream("./src/result.csv");
            //OutputStreamWriter isr = new OutputStreamWriter(fis, "Unicode");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            int j = 0;
            for(long i: timeList) {
                String content = (double)10000*(j+1)/2000000 + "," + (double)i/10 +"\n";
                j++;
                bw.write(content);
                bw.flush();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
