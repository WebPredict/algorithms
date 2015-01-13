package alg.misc;

/**
 * An annotation for non-trivial (sometimes) interesting algorithms in this library
 */
public @interface InterestingAlgorithm {

    String timeComplexity() default "Unknown";
    String spaceComplexity() default "Unknown";

}
