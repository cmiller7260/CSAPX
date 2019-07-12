import java.util.*;
import java.io.*;

import components.*;

public class HomeWiring extends Object {
    static String ADD = "add";
    static String APPLIANCE = "appliance";
    static String CIRCUIT = "circuit";
    static String COMENT = "#";
    static String DISPLAY = "d";
    static String MAIN = "main";
    static String OFF = "-";
    static String ON = "+";
    static String OUTLET = "outlet";
    static String PLUG = "p";
    static String PROMPT = "> ";
    static String QUIT = "q:";
    static String UNPLUG = "u";


    public static void main(String[] args) throws IOException {
        if (args[0] == null) {
            System.out.println("error: does not contain correct command arguments");
            System.exit(0);
        } else {
            new HomeWiring(args[0]);
//            Components.print();
//            Components.get(MAIN).display(0);
            run();
        }

    }

    public HomeWiring(String Filename) throws IOException {
        File file = new File(Filename);
        int Cost;
        String parentID;
        String CompName;
        String op = "";
        String type;
        boolean state = false;
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String i = sc.nextLine();
                String[] parts = i.split(" ");
                if (parts[0] != null) {
                    op = parts[0].toLowerCase();
                }
                if (op.equals(COMENT)) {
                } else if (op.equals(ADD)) {
//                    System.out.print(Arrays.toString(parts) + "\n");
                    Cost = Integer.parseInt(parts[4]);
                    parentID = parts[3].toLowerCase();
                    CompName = parts[2].toLowerCase();
                    type = parts[1].toLowerCase();
                    if (parentID.equals("null") && type.equals(CIRCUIT)) {
                        Component parent = null;
                        Components.add(new Circuit(CompName, parent, Cost, state));
                    } else if (Components.has(parentID) == true) {
                        Component parent = Components.get(parentID);
//                        System.out.print("\n" + parent + "\n");
//                        System.out.print("   THE CODE IS WORKING   ");
                        if (type.equals(APPLIANCE)) {
                            Appliance appliance = new Appliance(CompName, parent, Cost, state);
                            Components.add(appliance);
                            parent.add(appliance);
//                            Components.add(appliance);

                        } else if (type.equals(CIRCUIT)) {
                            Circuit circuit = new Circuit(CompName, parent, Cost, state);
                            Components.add(circuit);
                            parent.add(circuit);
//                            Components.add(circuit);

                        } else if (type.equals(OUTLET)) {
                            Outlet outlet = new Outlet(CompName, parent, Cost, state);
                            Components.add(outlet);
                            parent.add(outlet);
//                            Components.add(outlet);

                        }
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void run() {
        System.out.println(PROMPT);
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        String[] parts2 = s.split(" ");
//        System.out.print(Arrays.toString(parts2) + "\n");
        String opcode = parts2[0];

        if (opcode.equals(QUIT)){
            System.exit(0);
        }
        else if (opcode.equals(DISPLAY)){
            Components.get(parts2[1]).display(0);
            run();
        }
        else if (opcode.equals(OFF)){
            Components.get(parts2[1]).turnOff();
            run();
        }
        else if (opcode.equals(ON)){
            Components.get(parts2[1]).turnOn();
            run();
        }
        else if (opcode.equals(PLUG)){

        }
        else if (opcode.equals(UNPLUG)){

        }


    }
}

