package alg.machlearn.genetic;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/15/15
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Population {

    private List<Gene> individuals;

    public Population () {

    }

    public Population (List<Gene> individuals) {
        this.individuals = individuals;
    }

    public List<Gene> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<Gene> individuals) {
        this.individuals = individuals;
    }
}
