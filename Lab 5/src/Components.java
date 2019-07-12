import components.Component;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.HashMap;
import java.util.Map;

/**
 * This class holds all the components in the system, by name.  This class
 * is a wrapper to a Java HashMap (a collection that will be covered in
 * the following week.  As such, this is meant to be provided to students.
 *
 * @author Sean Strout @ RIT CS
 */
public class Components {
    /**
     * components are stored here
     */
    private static Map<String, components.Component> components = new HashMap<>();

    /**
     * Add a new component to the system.
     *
     * @param component the new component to add
     * @throws AssertionError if component is not already in the system
     */
    public static void add(components.Component component) {
        assert !has(component.getName());
        components.put(component.getName(), component);
//        System.out.println(component.getName() + " added to the hash set");
    }

    /**
     * Check if a component is in the system.
     *
     * @param name the name of the component to check
     * @return whether the component exists in the system or not
     */
    public static boolean has(String name) {
        return components.containsKey(name);
    }

    /**
     * Get the component associated with the name.
     *
     * @param name the string name of the component
     * @return the component
     * @throws AssertionError if no component is associated with the name
     */
    public static components.Component get(String name) {
        assert has(name);
        return components.get(name);
    }

    public static void print() {
        for (String name : components.keySet()) {
            String value = "null";
            String key = name.toString();
            if (!components.get(name).getName().equals("main")) {
                value = (components.get(name).getParentID().getName());
            }
            System.out.println(key + " " + value);
        }
    }
}