# ApacheFOP
PDF reports with Apache FOP 

Apache FOP with xml as input
-----------------------------

Apache FOP (Formatting Objects Processor) is a Java application that reads a formatting objects tree and renders the resulting pages to a specified output (here, in our case, PDF). 

<img src="https://github.com/ssyadav/ApacheFOP/blob/master/img/apachefop_basics.png"> 

We have an XML that holds data and an XSLT that creates an XML containing formatting objects by taking data from the first XML. This resultant XML is de-serialized into Java objects. FOP creates a PDF file using these Java objects.



Apache FOP with Html as input
-----------------------------

When we pass the html as input then we need to covert html into xml and then flow become same as the able. 

<img src="https://github.com/ssyadav/ApacheFOP/blob/master/img/apachefop_basics_html.png"> 

Once HTML converted into xml then XML holds data and an XSLT that creates an XML containing formatting objects by taking data from the first XML. This resultant XML is de-serialized into Java objects. FOP creates a PDF file using these Java objects.


