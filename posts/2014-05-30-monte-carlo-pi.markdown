---
title: Monte-Carlo Method to Approximate PI
author: Kenny Cason
tags: haskell, java, monte-carlo, pi
---

Here are a couple simple examples illustrating how to use Monte-Carlo Method to calculate an approximation of PI. Straight from <a href="http://en.wikipedia.org/wiki/Monte_Carlo_method" target="_new">Wikipedia</a> the algorithm works per the following:

1. Draw a square on the ground, then inscribe a circle within it.
2. Uniformly scatter some objects of uniform size (grains of rice or sand) over the square.
3. Count the number of objects inside the circle and the total number of objects.
4. The ratio of the two counts is an estimate of the ratio of the two areas, which is π/4. Multiply the result by 4 to estimate π.

As to why the ratio is π/4:
```
A_circle = πr^2
A_square = (2r)^2
circle transcribed in a square, r_c = r_s = r
A_square / A_circle (r = 1) = ratio of areas between square and circle
= 4 / π
```

GitHub Links: <a href="https://github.com/kennycason/haskell/blob/master/montecarlo.hs" target="_new">Haskell Code</a> <a href="https://github.com/kennycason/montecarlo" target="_new">Java Code</a>

***Haskell Example***

```haskell
import System.Random
import Data.List

inCircle :: (Double,Double) -> Bool
inCircle p = x * x + y * y < 1.0
            where x = fst p 
                  y = snd p


monteCarloPi :: [(Double, Double)] -> Double
monteCarloPi ps = ratio * 4.0
            where ratio = (fromIntegral count) / (fromIntegral samples)
                count = length $ filter inCircle ps
                  samples = length ps


randList :: Int -> StdGen -> [Double]
randList n rng = scale $ take n (randoms rng :: [Double])
                          where scale l = map (\x -> 2 * x - 1) l -- scales doubles in range of 0,1 to random -1,1 


main = do
    rng <- newStdGen
    rng2 <- newStdGen

    let xs = randList 1000000 rng
    let ys = randList 1000000 rng2
    let ps = zip xs ys

    print $ monteCarloPi ps
```

***Java Example***
```java
import java.util.Random;

/**
 * Created by kenny on 5/29/14.
 */
public class EvaulatePie {

    private static final Random RANDOM = new Random();
    /*
        A_circle = πr^2
        A_square = (2r)^2

        circle transcribed in a square, r_c = r_s = r

        A_square / A_circle (r = 1) = ratio of areas between square and circle
        = 4 / π
      */
    public static void main(String[] args) {
        monteCarlo(10);
        monteCarlo(100);
        monteCarlo(1000);
        monteCarlo(10000);
        monteCarlo(100000);
        monteCarlo(1000000);
        monteCarlo(10000000);
        monteCarlo(100000000);
    }

    /*
        make n random guesses, take the ratio of guesses in the circle,
        and divide that by the total guesses (all of which will be in the square)
        this will give you the A_square / A_circle ratio, 4/π
    */
    public static void monteCarlo(final int randomSamples) {
        int inCircle = 0;
        for(int i = 0; i < randomSamples; i++) {
            // generate random x/y variables between -1, 1 (which are guaranteed to be within the square
            final double x = 2 * RANDOM.nextDouble() - 1;
            final double y = 2 * RANDOM.nextDouble() - 1;
            if(inCircle(x, y)) {
                inCircle++;
            }
        }
        final double ratio = (double) inCircle / randomSamples;
        final double piApprox = ratio * 4; // ratio is 4/π, so multiply by 4 to get π approximation

        System.out.println("Random Samples: " + randomSamples);
        System.out.println("In circle: " + inCircle + ", In square: " + randomSamples);
        System.out.println("PI Approximation: " + piApprox + ", Error: " + Math.abs(Math.PI - piApprox));
    }

    /*
        eq of circle centered at origin: x^2 + y^2 = r^2
        if (x)^2 + (y)^2 < r^2 then is within circle where r = 1
     */
    private static boolean inCircle(final double x, final double y) {
        // System.out.println(x + ", " + y);
        return x * x + y * y < 1.0;
    }

}
```