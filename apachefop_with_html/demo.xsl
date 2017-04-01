<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
  <xsl:param name="page-size" select="'ltr'"/>
	<xsl:attribute-set name="variantsBottomBorder">
     	<xsl:attribute name="background-color">#fafafa</xsl:attribute>
     	<xsl:attribute name="color">black</xsl:attribute>
     	<xsl:attribute name="border-bottom">2px solid black</xsl:attribute>
     	<xsl:attribute name="height">14px</xsl:attribute>
     	<xsl:attribute name="padding">.5em .5em</xsl:attribute>
     	<xsl:attribute name="font-weight">bold</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="tableCellStyle" >
     	<xsl:attribute name="text-align">left</xsl:attribute>
     	<xsl:attribute name="height">15px</xsl:attribute>
     	<xsl:attribute name="padding">.4em .5em</xsl:attribute>
    </xsl:attribute-set>
  <xsl:template match="html">
   		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
   			<fo:layout-master-set>
                <fo:simple-page-master master-name="A4"
                    page-height="32cm" page-width="28cm" margin-top="1cm"
                    margin-bottom="1cm" margin-left="1.3cm" margin-right="1.3cm">
                    <fo:region-body margin-top="1.8cm" margin-bottom="2cm" />
                    <fo:region-before extent="2cm" overflow="hidden" />
                    <fo:region-after extent="1cm" overflow="hidden" />
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="A4"
                initial-page-number="1" id="documentBody">
                
                <fo:static-content flow-name="xsl-region-before" >
                
                   <fo:block-container position="absolute" >
				        <fo:block text-align="left" border-bottom-style="solid" border-bottom-width="2.0pt" border-bottom-color="#ccc">
				            <fo:external-graphic src="url(/Users/satyaveer.yadav/Personal/Projects/Developed/Orbuy/11/images/home/logo.png)" content-width="100px"/>
				        </fo:block>
				    </fo:block-container>
				    
				    <fo:block-container position="absolute" min-height="21.1cm" max-height="41cm">
				        <fo:block text-align="right" color="#2d4782" font-size="10.0pt" font-weight="bold">
				            PDF Report 
				        </fo:block>
				    </fo:block-container>
                    
                </fo:static-content>
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block-container position="absolute">
                        <fo:block text-align="left"  font-size="7.0pt" border-top-style="solid" border-top-width="1.0pt">
                            This is footer
                        </fo:block>
                    </fo:block-container>
                    <fo:block-container position="absolute">
                        <fo:block text-align="right"  font-size="10.0pt">
                             <fo:block text-align="end"> <fo:page-number /> / <fo:page-number-citation-last ref-id="documentBody" /> </fo:block>
                        </fo:block>
                    </fo:block-container>

                </fo:static-content>
                  <xsl:apply-templates select="body"/>
               </fo:page-sequence>
   		</fo:root>
  
  </xsl:template>
  
   <xsl:template match="body">

  <!-- ============================================
    Start generating the content for the main page 
    area (xsl-region-body).
    =============================================== -->
        
    <fo:flow flow-name="xsl-region-body">
    	<fo:block font-weight="bold" font-size="20px" padding-top="20px" padding-bottom="20px">
               <xsl:value-of select="/html/head/title"/>
        </fo:block>
     		<fo:block >
                  	<fo:table table-layout="fixed" width="100%" border-collapse="separate" border-style="solid" border-width="1px"  border-color="#ccc">
		            <fo:table-header>
		                <fo:table-row  >
		                	<xsl:for-each select="/html/body/table/thead/tr/th">
		                		<fo:table-cell xsl:use-attribute-sets="variantsBottomBorder" border-right="1px solid gray">
			                        <fo:block > <xsl:value-of select="text()"/></fo:block>
			                    </fo:table-cell>
		                	</xsl:for-each>
		                </fo:table-row>
		            </fo:table-header>
		            <fo:table-body>
		            		<xsl:for-each select="/html/body/table/tbody/tr">
							   	<xsl:choose>
									<xsl:when test="position() mod 2 != 0">
										<xsl:call-template name="personInfo">
											<xsl:with-param name="color"> white </xsl:with-param>
										</xsl:call-template>
									</xsl:when>
									<xsl:otherwise>
										<xsl:call-template name="personInfo">
											<xsl:with-param name="color"> #CCC</xsl:with-param>
										</xsl:call-template>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:for-each>
		         		</fo:table-body>
		         	</fo:table>
		    </fo:block>
    </fo:flow>
  </xsl:template>
  
   <xsl:template name="personInfo">
     	<xsl:param name="color"/>
     	<fo:table-row background-color="{$color}">
     		<xsl:for-each select="*">
     			<fo:table-cell text-align="left" padding=".4em .5em" height="15px">
                    <fo:block> 
                    	<xsl:value-of select="text()"/>
                    </fo:block>
                </fo:table-cell>
     		</xsl:for-each>
		
		</fo:table-row>
     </xsl:template>
     
</xsl:stylesheet>



 	