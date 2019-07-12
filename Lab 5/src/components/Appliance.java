package components;

public class Appliance extends Component {

    //private String name;
    private int requiredCurrent;
    private int actcurrent;

    public Appliance(String name, Component parentID, int requiredCurrent, boolean state) {
        super(name, parentID, Type.APPLIANCE, state);
        this.requiredCurrent = requiredCurrent;
    }

    public boolean add(Component component) {
        return false;
    }

    public boolean remove() {
        return false;
    }

    public void turnOn() {
        System.out.println(this.getName() + " is turning on");
        this.setCurrent(this.requiredCurrent);
        this.getParentID().updateCurrent(this.requiredCurrent);
        this.setState(true);
    }

    public String toString() {
        int inUse;
        String state;
        if (this.isOn() == false) {
            state = "false";
            inUse = 0;
        } else {
            state = "true";
            inUse = this.requiredCurrent;
        }
        String string = this.getType().toString() + "(name=" + this.getName().toString() + ",   current="
                + inUse + "/" + this.requiredCurrent + ",   on=" + state + ")";
        return string;
    }
}
