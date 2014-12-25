package gen;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/17/14
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Action {

    private String text;
    private ActionType actionType;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
}
