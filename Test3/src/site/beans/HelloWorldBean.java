package site.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;

import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;




@Named(value="helloworldBean")
@RequestScoped
@ManagedBean

public class HelloWorldBean {
	public static void main(String[] args) {
		try {
            String svgContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" id=\"Layer_1\" x=\"0px\" y=\"0px\" viewBox=\"0 0 305.8981934 352.1639099\" style=\"enable-background:new 0 0 305.8981934 352.1639099;\" xml:space=\"preserve\">\n" +
                    "...</svg>";

            Double[] dim = extractAttributeValue(svgContent);

            System.out.println("Width: " + dim[0] +","+ dim[1]  );

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	
	// Extract the value of the specified attribute from SVG content
    public static Double[] extractAttributeValue(String svgContent) throws SAXException, IOException, ParserConfigurationException  {
    	
    	Double[] erg = { null , null };
    	
    	// Create a DocumentBuilder to parse XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream input = new ByteArrayInputStream(svgContent.getBytes("UTF-8"));
        Document doc = builder.parse(input);

        // Get the root SVG element
        Element svgElement = doc.getDocumentElement();
        
        // Extract the attribute value
        String width = svgElement.getAttribute("width").replace("px", "");
        String height = svgElement.getAttribute("height").replace("px", "");
        
        if (!width.equals("") && !height.equals("")) {
        	
        	 erg[0] = Double.parseDouble(width);
             
        	 erg[1] = Double.parseDouble(height);
		}else{
            String[] parts = svgElement.getAttribute("viewBox").split(" ");

            if (parts.length >= 4) {
            	 erg[0] = Double.parseDouble(parts[2]);
            	 erg[1] = Double.parseDouble(parts[3]);
            }
		
		}
       
        return erg;
    }
    
}
