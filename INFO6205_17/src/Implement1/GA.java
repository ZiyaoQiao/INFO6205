package Implement1;

import java.util.concurrent.CompletableFuture;
import org.apache.log4j.Logger;

import java.util.*;

public class GA {


    public static final int populationSize = 2000;
    public static final double generation = 500;
    public static final double selfRate = 0.9;
    //public static final double[] mapXList = {17 , 2 , 3, 4, 5, 6, 7, 22, 9, 19, 11, 12, 13, 14, 15, 16};
    //public static final double[] mapYList = {17 , 2, 3, 4, 5, 6, 7, 22, 9,19,11,12,13,14,15,16};
    //public static final double[] mapXList = {60, 180, 80, 140, 20, 100, 200, 140, 40, 100, 180, 60, 120, 180, 20, 100, 200, 20, 60, 160};
    //public static final double[] mapYList = {200, 200, 180, 180, 160, 160, 160, 140, 120, 120, 100, 80, 80, 60, 40, 40, 40, 20, 20, 20};
    public static final double[] mapXList = {6734,
            2233,
            5530,
            401,
            3082,
            7608,
            7573,
            7265,
            6898,
            1112,
            5468,
            5989,
            4706,
            4612,
            6347,
            6107,
            7611,
            7462,
            7732,
            5900,
            4483,
            6101,
            5199,
            1633,
            4307,
            675,
            7555,
            7541,
            3177,
            7352,
            7545,
            3245,
            6426,
            4608,
            23,
            7248,
            7762,
            7392,
            3484,
            6271,
            4985,
            1916,
            7280,
            7509,
            10,
            6807,
            5185,
            3023,
    };
    public static final double[] mapYList = {1453,
            10,
            1424,
            841,
            1644,
            4458,
            3716,
            1268,
            1885,
            2049,
            2606,
            2873,
            2674,
            2035,
            2683,
            669,
            5184,
            3590,
            4723,
            3561,
            3369,
            1110,
            2182,
            2809,
            2322,
            1006,
            4819,
            3981,
            756,
            4506,
            2801,
            3305,
            3173,
            1198,
            2216,
            3779,
            4595,
            2244,
            2829,
            2135,
            140,
            1569,
            4899,
            3239,
            2676,
            2993,
            3258,
            1942,
    };
    final static Logger logger = Logger.getLogger(GA.class);
    static int listSize;


    static List<Person> list;
    ArrayList<ArrayList<Double>> map;

    public GA(){
        map = new ArrayList<>();
        for(int i = 0 ; i<Person.DNASize; i++){
            ArrayList<Double> tmp = new ArrayList<>();
            tmp.add(mapXList[i]);
            tmp.add(mapYList[i]);
            map.add(tmp);
        }
        list = new ArrayList<>();
        for(int i = 0 ; i<populationSize ; i++){
            list.add(new Person());
        }
        listSize = list.size();
    }


    public void sort(){
        for(int i = 0; i<this.list.size(); i++) {
            fitness(this.list.get(i));
        }
        listSize = this.list.size();
        Collections.sort(list);
    }

    public double fitness(Person person){
        if(person.distance == 0) {
            double distance = 0;
            int j = person.translateSingleDNA(person.DNA.get(0));
            int pre = 0;
            for (int i = 1; i < person.DNASize; i++) {
                pre = j;
                j = person.translateSingleDNA(person.DNA.get(i));
                distance += Math.sqrt(Math.pow(map.get(j).get(0) - map.get(pre).get(0), 2) + Math.pow(map.get(j).get(1) - map.get(pre).get(1), 2));
            }
            person.distance = distance;
        }
        return person.distance;
    }


    public void select(){
        sort();
        int end = (int)(list.size()*0.6);
        if(end > 4*populationSize)
            end = end/2;
        list = list.subList(0, end);
    }


    public void evolve(){
        select();
        Collections.shuffle(this.list);
        evolve(this.list, 0, list.size());
        sort();
    }

    public static void evolve(List<Person> list, int from, int to){
        Random rand = new Random();
        int size = list.size();
        if(size < listSize/4){
            for(int i = 0; i < size; i++){
                double pos = Math.random();
                int j = rand.nextInt(size);
                list.add(new Person(list.get(i),list.get(j)));

                if(pos > selfRate){
                    list.add(new Person(list.get(i)));
                }
            }
        }else {
            CompletableFuture<List<Person>> parEvolve1 = parEvolve(list, 0, size / 2 );
            CompletableFuture<List<Person>> parEvolve2 = parEvolve(list, size / 2 -1, size - 1);
            CompletableFuture<List<Person>> parEvolve = parEvolve1.
                    thenCombine(parEvolve2, (evolve1, evolve2) -> {
//                        List<Person> result = new ArrayList<>();
//
//                        result.addAll(evolve1);
//                        result.addAll(evolve2);
                        List<Person> result = new ArrayList<>(evolve1.size() + evolve2.size());
                        for (int i = 0; i < size; i++) {
                            double pos = Math.random();
                            int j = rand.nextInt(size);
                            list.add(new Person(list.get(i), list.get(j)));

                            if (pos > selfRate) {
                                list.add(new Person(list.get(i)));
                            }
                        }

                        return result;
                    });
            parEvolve.whenComplete((result, throwable) -> {
            });
            parEvolve.join();
        }
    }

    public static CompletableFuture<List<Person>> parEvolve(List<Person> array, int from, int to){
        return CompletableFuture.supplyAsync(
                () -> {
                    List<Person> result = new ArrayList<>(to - from);
                    for (int i = 0; i < result.size(); i++) {
                        result.set(i, array.get(from + i));
                    }
                    evolve(result, 0, to - from);
                    return result;
                }
        );
    }


    public void logging(int generation, GA gsp){
        if(logger.isInfoEnabled()){
            logger.info("This is generation " + generation);
            ArrayList<Integer> integers = selectMin().translate();
            StringBuffer points = new StringBuffer();
            StringBuffer locations = new StringBuffer();

            for(Integer i : integers){
                points.append(i + ";");
                locations.append(mapXList[i] + "," + mapYList[i] + " ; ");
            }
            logger.info("The best of this generation points: " + points);
            logger.info("The best of this generation locations: " + locations);
            logger.info("The best of this generation distance: " + gsp.list.get(0).distance);
            logger.info("The population is : "+ gsp.list.size());
            ArrayList<Integer> integers2 = selectMax().translate();
            StringBuffer points2 = new StringBuffer();
            StringBuffer locations2 = new StringBuffer();
            for(Integer i : integers2){
                points2.append(i + ";");
                locations2.append(mapXList[i] + "," + mapYList[i] + " ; ");
            }
            logger.info("The worst of this generation points: " + points2);
            logger.info("The worst of this generation locations: " + locations2);
            logger.info("The worst of this generation distance: " + gsp.list.get(list.size()-1).distance);
            logger.info("*******************************************************************************************************");
        }
    }

    public Person selectMin(){
        return list.get(0);
    }
    public Person selectMax(){
        return list.get(list.size()-1);
    }

    public static void main(String[] args){
        GA tsp = new GA();
        for(int i = 0; i < generation; i++) {
            tsp.evolve();
            tsp.logging(i , tsp);
        }
    }
}
