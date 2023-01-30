package task;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

public class XMLTesting {
    public static void main(String[] args) {
        try {
            File xmlContent = new File("content.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlContent);
            Scanner sc = new Scanner(System.in);
            
            // User input to search id
            System.out.println("Enter unit id:");
            String input = sc.nextLine();

            // Getting all units
            NodeList unitList = doc.getElementsByTagName("unit");

            for (int i = 0; i < unitList.getLength(); i++) {
                Node unitNode = unitList.item(i);

                // Getting units with specified id
                if (unitNode.getAttributes().getNamedItem("id")
                        .getNodeValue().equals(input)) {
                    NodeList childrenList = unitNode.getChildNodes();

                    System.out.println(unitNode.getAttributes().getNamedItem("id").getNodeValue());

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
                                    if (requiredNode.getAttributes().getNamedItem("namespace")
                                            .getNodeValue().equals("org.eclipse.equinox.p2.iu") ||
                                            requiredNode.getAttributes().getNamedItem("namespace")
                                                    .getNodeValue().equals("osgi.bundle")) {

                                        if (!requiredNode.getAttributes().getNamedItem("name")
                                                .getNodeValue().equals(input)) {
                                            System.out.print("--");
                                            System.out.println(requiredNode.getAttributes().getNamedItem("name").getNodeValue());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
