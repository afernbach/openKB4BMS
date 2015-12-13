<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="yes"/>
    <xsl:strip-space elements="*" />

    <xsl:template match="obj">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()[not(self::obj[@is='knx:Part']|self::list[@of='knx:Part'])]" />
        </xsl:copy>
        <xsl:apply-templates select="obj[@is='knx:Part']" />
        <xsl:apply-templates select="list[@of='knx:Part']"/>
        
    </xsl:template>


<xsl:template match="obj[@is='knx:ViewBuilding']">
<xsl:apply-templates />
</xsl:template>


<xsl:template name="list" match="list">
	<xsl:for-each select="obj">
			
   		 	<xsl:apply-templates select="."/>
   		 	
   		 </xsl:for-each>   		 
</xsl:template>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>


</xsl:stylesheet>
