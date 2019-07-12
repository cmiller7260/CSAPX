package components;

public class Circuit extends Component {

    //private String name;
    private int maxCurrent;
    private int current;


    public Circuit(String name, Component parentID, int maxCurrent, boolean state) {
        super(name, parentID, Type.CIRCUIT, state);
        this.maxCurrent = maxCurrent;
    }

    public boolean add(Component component) {
        if (this.getChildren().add(component)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean remove() {
        return false; // change code
    }

    public boolean updateCurrent() {
//        int total =0;
//        for (int i = 0; i < this.getChildren().size(); i++) {
//            total =+ this.getChildren().get(i).getCurrent();
//        }
        if ((this.getCurrent()) > this.maxCurrent) {
            // print it is breaking
            // turn off all children
            return false;
        } else {
            this.current = (this.current + current);
            return true;
        }
    }

    public String toString() {
        String state;
        if (this.isOn() == false) {
            this.current = 0;
            state = "false";
        } else {
            this.current = this.getCurrent();
            state = "true";
        }
        String string = this.getType().toString() + "(name=" + this.getName().toString() + ",   current="
                + this.current + "/" + this.maxCurrent + ",   on=" + state + ")";
        return string;
    }
}
