/**
 * 
 */
package com.satya.fop;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;

import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;


/**
 * @author satyaveer.yadav
 *
 */
public class DemoApacheFopProcessor {

	public static void main(String[] args) {

		// String html = "everything.html";
		File html = new File("demo.html");
		byte[] data = null;
		try {
			data = FileUtils.readFileToByteArray(html);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ByteArrayInputStream input = new ByteArrayInputStream(data);

		// ByteArrayInputStream input = new
		// ByteArrayInputStream(html.getBytes());

		final HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();

		DomSerializer doms = new DomSerializer(props, true);

		org.w3c.dom.Document xmlDoc = null;

		try {
			TagNode node = cleaner.clean(input);
			xmlDoc = doms.createDOM(node);
		} catch (Exception e) {
			e.printStackTrace();
		}

		org.w3c.dom.Document foDoc = null;

		try {
			foDoc = xml2FO(xmlDoc);
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		}

		String pdfFileName = "demo.pdf";
		String stylesheet = "demo.xsl";
		try {
			File file = new File(pdfFileName);
			OutputStream pdf = new FileOutputStream(file);
			pdf.write(fo2PDF(foDoc, stylesheet));

			System.out.println(file.length());
		} catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Error creating PDF: " + pdfFileName);
		} catch (java.io.IOException e) {
			e.printStackTrace();
			System.out.println("Error writing PDF: " + pdfFileName);
		}
	}

	// convert xml to FO
	private static Document xml2FO(Document xml) throws Exception {
		DOMSource xmlDomSource = new DOMSource(xml);
		DOMResult domResult = new DOMResult();

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();

		if (transformer == null) {
			System.out.println("Error creating transformer");
			System.exit(1);
		}

		try {
			transformer.transform(xmlDomSource, domResult);
		} catch (javax.xml.transform.TransformerException e) {
			return null;
		}

		return (Document) domResult.getNode();
	}

	// convert FO to PDF
	private static byte[] fo2PDF(Document foDocument, String styleSheet) {
		FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
			Transformer transformer = getTransformer(styleSheet);

			Source src = new DOMSource(foDocument);
			Result res = new SAXResult(fop.getDefaultHandler());

			transformer.transform(src, res);

			return out.toByteArray();

		} catch (Exception ex) {
			return null;
		}
	}

	private static Transformer getTransformer(String styleSheet) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();

			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			dFactory.setNamespaceAware(true);

			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			Document xslDoc = dBuilder.parse(styleSheet);
			DOMSource xslDomSource = new DOMSource(xslDoc);

			return tFactory.newTransformer(xslDomSource);
		} catch (javax.xml.transform.TransformerException e) {
			e.printStackTrace();
			return null;
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return null;
		} catch (javax.xml.parsers.ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (org.xml.sax.SAXException e) {
			e.printStackTrace();
			return null;
		}
	}
}
