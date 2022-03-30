package com.batuhaniskr.vulnerablewebapp.service;

import com.batuhaniskr.vulnerablewebapp.config.ApplicationProperties;
import com.batuhaniskr.vulnerablewebapp.model.Course;
import com.batuhaniskr.vulnerablewebapp.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class XmlService {

    private static final String xmlFilePath = "src/main/resources/student-simple.xml";
    private static final String xstlFilePath = "src/main/resources/student-xml-html.xslt";
    private static final String outputHtmlFileName = "output.html";

    private final ApplicationProperties applicationProperties;

    public XmlService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    private final Logger logger = LoggerFactory.getLogger(XmlService.class);

    public Course getCourse(String url) {
        Course course = null;

        try {
            //String URL = "http://www.mocky.io/v2/5c8bdd5c360000cd198f831e";

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(url);

            // normalize XML response
            doc.getDocumentElement().normalize();

            //read course details first
            course = new Course(Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent()),
                    doc.getElementsByTagName("title").item(0).getTextContent(),
                    Double.parseDouble(doc.getElementsByTagName("price").item(0).getTextContent()),
                    new SimpleDateFormat("yyyy-MM-dd").parse(doc.getElementsByTagName("created").item(0).getTextContent())
            );

            NodeList nodeList = doc.getElementsByTagName("student");

            // create an empty list for students
            List<Student> students = new ArrayList<>();

            //loop all available student nodes
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    Student student = new Student(
                            Integer.parseInt(elem.getElementsByTagName("id").item(0).getTextContent()),
                            elem.getElementsByTagName("first_name").item(0).getTextContent(),
                            elem.getElementsByTagName("last_name").item(0).getTextContent(),
                            elem.getElementsByTagName("avatar").item(0).getTextContent());
                    students.add(student);
                }
            }
            //set students in course
            course.setStudents(students);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return course;
    }

    public void toHtml() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try (InputStream is = new FileInputStream(xmlFilePath)) {

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(is);

            // transform xml to html via a xslt file
            try (FileOutputStream output =
                         new FileOutputStream(outputHtmlFileName)) {
                transform(doc, output);
            }

        } catch (IOException | ParserConfigurationException |
                SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void transform(Document doc, FileOutputStream output) throws TransformerException {
            var transformerFactory = TransformerFactory.newInstance();

            // add XSLT in Transformer
            Transformer transformer = transformerFactory.newTransformer(
                    new StreamSource(new File(xstlFilePath)));

            transformer.transform(new DOMSource(doc), new StreamResult(output));
        }
}
