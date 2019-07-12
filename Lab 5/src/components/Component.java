package components;

import java.util.ArrayList;

public abstract class Component extends Object {
    public enum Type {
        CIRCUIT, OUTLET, APPLIANCE
    }

    private Component parentID;
    private ArrayList<Component> children;
    private String name;
    private Component.Type type;
    private boolean state;
    private int current;
//    private int actcurrent;

    public Component(String name, Component parentID, Component.Type type, boolean state) {
        this.name = name;
        this.type = type;
        this.parentID = parentID;
        this.children = new ArrayList<Component>(); // I think this is causing the problem with a null children list
        this.state = state;
        this.current = 0;
    }

    public abstract boolean add(Component component);

    public void display(int level ) {

        for (int i = 0; i < level; i++) {
            System.out.print("\t");
        }
        System.out.println(this.toString());
    //    this.children = new ArrayList<>();
        level += 1;
        for (int i = 0; i < children.size(); i++) {
            children.get(i).display(level);
        }

    }

    public boolean updateCurrent(int current) {

        return false;
    }

    public void turnOff() {
        this.setCurrent(0);
        this.state = false;
    }

    public void turnOn() {
//        this.actcurrent = this.current;
//        this.updateCurrent(this.actcurrent);
        System.out.println(this.name + " is turning on");
        for (int i = 0; i < this.getChildren().size(); i++) {
            children.get(i).turnOn();
        }
        this.setCurrent(this.getCurrent());

        this.state = true;

    }

    public boolean isOn() {
        return this.state;
    }

    public abstract boolean remove();

    public boolean equals(Object other) {
        return false;
    }

    public String getName() {
        return this.name;
    }

    public Component.Type getType() {
        return this.type;
    }

    public Component getParentID() {
        return this.parentID;
    }

    public ArrayList<Component> getChildren() {
        return this.children;
    }

    public int getCurrent() {
        int total =0;
        for (int i = 0; i < this.getChildren().size(); i++) {
            total =+ this.getChildren().get(i).getCurrent();
        }
//System.out.println("get current : " + total + "\t" + this.name);
        return (this.current + total);
    }

    public void setCurrent(int current) {
        this.current = current;
//        System.out.println("set curretn : " + + this.current + "\t" + this.name);
    }
    public void setState(boolean state){
        this.state = state;
    }

}

