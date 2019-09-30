package edu.cmu.cs.mvelezce.tool.analysis.taint.java.dynamic.varexj;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReader implements XMLCoverage {

  public Coverage readFromFile(File file)
      throws ParserConfigurationException, IOException, SAXException, UnsupportedCoverageException {

    String fileName = file.getPath();
    InputStream inputStream = null;
    inputStream = new FileInputStream(fileName);
    return readXML(inputStream);
  }

  public Coverage readXML(InputStream xmlContent)
      throws ParserConfigurationException, IOException, SAXException, UnsupportedCoverageException {
    Document doc = parse(xmlContent);
    return getCoverage(doc);
  }


  public Coverage readXML(String xmlContent)
      throws ParserConfigurationException, IOException, SAXException, UnsupportedCoverageException {
    Document doc = parse(xmlContent);
    return getCoverage(doc);
  }

  private Coverage getCoverage(Document doc)
      throws NumberFormatException, UnsupportedCoverageException {
    Coverage coverage = new Coverage();
    for (Element rootNode : getElements(doc.getElementsByTagName(ROOT))) {
      coverage.setType(rootNode.getAttribute(TYPE));
      coverage.setBaseValue(Integer.valueOf(rootNode.getAttribute(BASE)));
      break;
    }
    for (Element fileNode : getElements(doc.getElementsByTagName(FILE))) {
      getCoverage(coverage, fileNode);
    }
    return coverage;
  }


  private void getCoverage(Coverage coverage, Element fileNode)
      throws UnsupportedCoverageException {

    String fileName = fileNode.getAttribute(FILE_NAME);
    Element coverageNode = getElements(fileNode.getElementsByTagName(COVERAGE_KEY)).get(0);
    for (Element line : getElements(coverageNode.getElementsByTagName(COVERED_LINE))) {
      try {
        if (line.hasAttribute(THIS)) {
          coverage.setLineCovered(fileName, Integer.valueOf(line.getAttribute(THIS)),
              Integer.valueOf(line.getAttribute(INTERACTION)), line.getAttribute(TEXT));
        }
        else {
          throwError("Unknown attribute " + line.getNodeName(), line);
        }
      } catch (Exception e) {
        throwError("Parse error occured " + e.getMessage(), line);
      }
    }
  }

  private Document parse(String input)
      throws IOException, SAXException, ParserConfigurationException {
    InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    return parse(stream);
  }

  private Document parse(InputStream stream)
      throws IOException, SAXException, ParserConfigurationException {
    Document doc = PositionalXMLReader.readXML(stream);
    doc.getDocumentElement().normalize();
    return doc;
  }

  /**
   * @return The child nodes from type Element of the given NodeList.
   */
  private ArrayList<Element> getElements(NodeList nodeList) {
    ArrayList<Element> elements = new ArrayList<Element>(nodeList.getLength());
    for (int temp = 0; temp < nodeList.getLength(); temp++) {
      org.w3c.dom.Node nNode = nodeList.item(temp);
      if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
        Element eElement = (Element) nNode;
        elements.add(eElement);
      }
    }
    return elements;
  }

  /**
   * Throws an error that will be used for error markers
   *
   * @param message The error message
   * @param tempNode The node that causes the error. this node is used for positioning.
   */
  private void throwError(String message, org.w3c.dom.Node node)
      throws NumberFormatException, UnsupportedCoverageException {
    throw new UnsupportedCoverageException(message,
        Integer.parseInt(node.getUserData(PositionalXMLReader.LINE_NUMBER_KEY_NAME).toString()));
  }
}
