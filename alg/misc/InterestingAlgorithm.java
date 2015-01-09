package alg.misc;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/9/15
 * Time: 2:29 PM
 * To change this template use File | Settings | File Templates.
 */
public @interface InterestingAlgorithm {

    String timeComplexity() default "Unknown";
    String spaceComplexity() default "Unknown";

}
