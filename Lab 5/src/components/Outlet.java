package components;

public class Outlet extends Component {

    //    private String name;
    private int outlets;


    public Outlet(String name, Component parentID, int outlets, boolean state) {
        super(name, parentID, Type.OUTLET, state);
        this.outlets = outlets;
    }

    public boolean add(Component component) {
        if (this.getChildren().size() < this.outlets) {
            this.getChildren().add(component);
            return true;
        } else {
            return false;
        }
    }

    public boolean remove() {
        return true;
    }

    public String toString() {
        int inUse;
        String state;
        inUse = this.getChildren().size();
        if (this.isOn() == false) {
            state = "false";
        } else {
            state = "true";
        }
        String string = this.getType().toString() + "(name=" + this.getName().toString() + ",   outlets="
                + inUse + "/" + this.outlets + ",   on=" + state + ")";
        return string;
    }
}
