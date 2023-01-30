package task;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

            NodeList unitList = doc.getElementsByTagName("unit");

            for (int i = 0; i < unitList.getLength(); i++) {
                Node unitNode = unitList.item(i);

                if (unitNode.getAttributes().getNamedItem("id")
                        .getNodeValue().equals("org.eclipse.equinox.p2.director")) {
                    NodeList childrenList = unitNode.getChildNodes();

                    for (int j = 0; j < childrenList.getLength(); j++) {
                        Node child = childrenList.item(j);

                        if (child.getNodeName().equals("requires")) {
                            NodeList requiresList = child.getChildNodes();

                            for (int k = 0; k < requiresList.getLength(); k++) {
                                Node requiredNode = requiresList.item(k);

                                if (requiredNode.getAttributes().getNamedItem("namespace")
                                        .getNodeValue().equals("org.eclipse.equinox.p2.iu") ||
                                        requiredNode.getAttributes().getNamedItem("namespace")
                                                .getNodeValue().equals("osgi.bundle")) {
                                    if (!requiredNode.getAttributes().getNamedItem("name")
                                            .getNodeValue().equals("org.eclipse.equinox.p2.director")) {

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
