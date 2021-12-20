package Utils;

import java.util.Random;

public class RandomUtils {

    public static int uniform(Random random, int a, int b){
        if ((b <= a) || ((long) b - a >= Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform(random, b - a);
    }

    public static  int uniform(Random random, int a){
        if (a <= 0) {
            throw new IllegalArgumentException("argument must be positive: " + a);
        }
        return random.nextInt(a);
    }

    public static double uniform(Random random) {
        return random.nextDouble();
    }

    public static boolean bernoulli(Random random, double p) {
        if (!(p >= 0.0 && p <= 1.0)) {
            throw new IllegalArgumentException("probability p must be between 0.0 and 1.0: " + p);
        }
        return uniform(random) < p;
    }
}
