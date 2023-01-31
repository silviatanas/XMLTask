package task;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

public class XMLTesting {
    public static void main(String[] args) {
        try {
            File xmlContent = new File("content.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlContent);
            Scanner sc = new Scanner(System.in);
            // HashSet to eliminate repeats
            HashSet<String> repeatsCheck = new HashSet<>();

            // Getting all units
            NodeList unitList = doc.getElementsByTagName("unit");

            // User input to search id and check if input is valid
            // Flag is true when input is valid
            for (boolean flag = false; flag == false; ) {
                System.out.println("Enter unit id:");
                String input = sc.nextLine();

                flag = printRecursion(unitList, input, 1, repeatsCheck);

                if (flag == false) {
                    System.out.println("Id not found");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Recursion for getting the requirements of requirements
    public static boolean printRecursion(NodeList unitList, String input, int counter, HashSet<String> repeatsCheck) {
        for (int i = 0; i < unitList.getLength(); i++) {
            Node unitNode = unitList.item(i);

            // Getting units with specified id
            if (unitNode.getAttributes().getNamedItem("id")
                    .getNodeValue().equals(input)) {
                NodeList childrenList = unitNode.getChildNodes();

                // Getting all children nodes of the unit with that id
                for (int j = 0; j < childrenList.getLength(); j++) {
                    Node child = childrenList.item(j);

                    // Getting requires
                    if (child.getNodeName().equals("requires")) {
                        NodeList requiresList = child.getChildNodes();

                        // This contains all the required nodes
                        for (int k = 0; k < requiresList.getLength(); k++) {
                            Node requiredNode = requiresList.item(k);

                            // Node includes a text and an element node,
                            // in order to avoid the null value of the text we filter
                            if (requiredNode.getNodeType() == Node.ELEMENT_NODE) {

                                // Getting the attributes with the corresponding namespaces and names
                                if (requiredNode.getAttributes().getNamedItem("namespace")
                                        .getNodeValue().equals("org.eclipse.equinox.p2.iu") ||
                                        requiredNode.getAttributes().getNamedItem("namespace")
                                                .getNodeValue().equals("osgi.bundle")) {

                                    if (!requiredNode.getAttributes().getNamedItem("name")
                                            .getNodeValue().equals(input)) {

                                        // Checking for repeats when we retrieve the additional requirements
                                        if (!repeatsCheck.contains(requiredNode.getAttributes().getNamedItem("name").getNodeValue())) {
                                            for (int l = 0; l < counter; l++) {
                                                System.out.print("--");
                                            }
                                            System.out.println(requiredNode.getAttributes().getNamedItem("name").getNodeValue());
                                            repeatsCheck.add(requiredNode.getAttributes().getNamedItem("name").getNodeValue());
                                        }

                                        printRecursion(unitList,
                                                requiredNode.getAttributes().getNamedItem("name").getNodeValue(),
                                                counter + 1, repeatsCheck);
                                    }
                                }
                            }
                        }
                    }
                }
                // Returns true when an element id was found
                return true;
            }
        }
        // Returns false when no more ids are to be found
        return false;
    }
}
