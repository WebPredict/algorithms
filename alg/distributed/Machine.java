package alg.distributed;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/5/14
 * Time: 5:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Machine {

    private Object memory;
    private String id;
    private List<Machine> neighbors;

    public List<Machine> getNeighbors() {
        return neighbors;
    }

    public double   distanceToNeighbor (String neighborId) {
        return (0d); // TODO
    }

    public void setNeighbors(List<Machine> neighbors) {
        this.neighbors = neighbors;
    }

    public Object getMemory() {
        return memory;
    }

    public void setMemory(Object memory) {
        this.memory = memory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
