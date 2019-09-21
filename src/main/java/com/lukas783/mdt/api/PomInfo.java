package com.lukas783.mdt.api;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A POJO that defines an XML POM file. An XML POM file is used by Maven to declare how to build
 * a project into an artifact. The purpose of this object is to map the node values and attributes
 * of an XML POM file into an easy-to-access map to retrieve particular values and attributes from
 * a given XML POM file.
 *
 * @author Lucas Carpenter
 */
public class PomInfo {

    // Declaration of logger for debug/error handling messages.
    private final static Logger logger = Logger.getLogger(PomInfo.class.getName());


    // Declaration of variables used by the class
    private Map<String, String> nodeValueMap;
    private Map<String, Map<String, String>> nodeAttributeMap;

    /**
     * Constructs a {@link PomInfo} object. Reads a file and attempts to map the values and attributes of the
     * nodes in the file to an internal mapping to be better accessed elsewhere.
     * @param pomFile The file to attempt to parse, should be in an XML format.
     */
    public PomInfo(File pomFile) {
        try {
            // Attempt to parse the file into a document
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            Document doc = builder.parse(pomFile);

            // normalize the parsed document
            doc.getDocumentElement().normalize();

            // get the list of root-level nodes.
            NodeList nodes = doc.getChildNodes();

            // build our internal map to store node values and attributes
            nodeValueMap = new HashMap<>();
            nodeAttributeMap = new HashMap<>();

            // Parse the list of root-level nodes into a mapping of values and attribute lists
            parseChildren("", nodes);
        } catch(ParserConfigurationException pce) {
            logger.log(Level.SEVERE, "POM file configuration couldn't be parsed.", pce);
        } catch(SAXException saxe) {
            logger.log(Level.SEVERE, "POM has invalid schema configuration.", saxe);
        } catch(IOException ioe) {
            logger.log(Level.SEVERE, "Unable to read POM file.", ioe);
        }
    }

    /**
     * Recursively parses the nodes of an XML file into a mapping of attributes and values.
     * The values parsed only parse if the node is reading the #text portion of the node.
     * Otherwise, it attempts to parse the attributes available, if none are available, we
     * retrieve a list of that node's children.
     * @param currentPath The current path to the node value stored currently.
     * @param node The {@link NodeList} item currently being processed.
     */
    private void parseChildren(String currentPath, NodeList node) {
        // Loop through the list of items in the NodeList
        for (int i = 0; i < node.getLength(); i++) {
            // Handle this only if we are parsing a given nodes #text field
            if(node.item(i).getNodeName().endsWith("#text")) {
                // Remove the '.' from the current path
                String newNodeName = currentPath.substring(0, currentPath.length() - 1);

                // Put the #text value of the node into the mapping of node values
                nodeValueMap.put(newNodeName, node.item(i).getNodeValue());

                // Retrieve the list of this node's children and parse them as well.
                NodeList childNode = node.item(i).getChildNodes();
                if (childNode != null) {
                    parseChildren(currentPath + ".", childNode);
                }
            // Handle this only if the current node being checked doesn't have a #text tag
            } else {
                // Get the mapping of attributes for the node and build a new hash map object.
                NamedNodeMap attributeNodeMap = node.item(i).getAttributes();
                Map<String, String> attributeMap = new HashMap<>();

                // If the node has attributes, populate the hashmap of attributes for the node with values
                if (attributeNodeMap != null) {
                    for (int j = 0; j < attributeNodeMap.getLength(); j++) {
                        attributeMap.put(attributeNodeMap.item(j).getNodeName(), attributeNodeMap.item(j).getNodeValue());
                    }
                }

                // Place our built hashmap into the table of attributes
                nodeAttributeMap.put(currentPath + node.item(i).getNodeName(), attributeMap);

                // Recurse the list of this node's children and parse them as well.
                NodeList childNode = node.item(i).getChildNodes();
                if (childNode != null) {
                    parseChildren(currentPath + node.item(i).getNodeName() + ".", childNode);
                }
            }
        }
    }

    /**
     * Retrieves the #text value of a given node.
     * @param mapId The node ID string to retrieve.
     * @return The value of the node's #text field, as a String.
     */
    public String getNodeValue(String mapId) {
        return nodeValueMap.get(mapId);
    }

    /**
     * Retrieves the particular attribute of a node given the node string ID and the attribute to look for.
     * @param nodeString The node ID string.
     * @param attributeString The attribute ID string.
     * @return The value of the node's attribute, as a String.
     */
    public String getAttributeValue(String nodeString, String attributeString) {
        return nodeAttributeMap.get(nodeString).get(attributeString);
    }

    /**
     * Retrieves the particular mapping of attributes for a given node string ID.
     * @param nodeString The node ID string.
     * @return The mapping of attribute ID strings to attribute value strings.
     */
    public Map<String, String> getAttributes(String nodeString) {
        return nodeAttributeMap.get(nodeString);
    }
}
