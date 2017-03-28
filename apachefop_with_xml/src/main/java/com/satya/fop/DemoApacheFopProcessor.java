/**
 * 
 */
package com.satya.fop;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
/**
 * @author satyaveer.yadav
 *
 */
public class DemoApacheFopProcessor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// demo.pdf: output pdf file name
        try(OutputStream  out = new java.io.FileOutputStream("demo.pdf");) {
        	
        	// xsl file which describe how pdf should look
        	File xsltFile = new File("demo.xsl");
        	
            // the XML file which provides the input
            StreamSource xmlSource = new StreamSource(new File("demo.xml"));
            
            // create an instance of fop factory
            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            
            // a user agent is needed for transformation
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
           
            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            // That's where the XML is first transformed to XSL-FO and then 
            // PDF is created
            transformer.transform(xmlSource, res);
            
        } catch (FOPException | IOException | TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
	}

}
