package gen;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 11/20/14
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Rel {

    private RelType relType;
    private Model model;
    private boolean dependent;
    private Model through;

    public Model getThrough() {
        return through;
    }

    public void setThrough(Model through) {
        this.through = through;
    }

    public boolean isDependent() {
        return dependent;
    }

    public void setDependent(boolean dependent) {
        this.dependent = dependent;
    }

    public Rel(RelType relType, Model model) {
        this.relType = relType;
        this.model = model;
    }

    public Rel(RelType relType, Model model, boolean dependent) {
        this.relType = relType;
        this.model = model;
        this.dependent = dependent;
    }

    public RelType getRelType() {
        return relType;
    }

    public void setRelType(RelType relType) {
        this.relType = relType;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String toString () {
        return (relType.toString() + " " + model.getName());
    }
}
