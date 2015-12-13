<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
                xmlns:owl="http://www.w3.org/2002/07/owl#"
                xmlns="http://www.w3.org/1999/xhtml"
                xmlns:first="http://www.w3.org/2002/03owlt/miscellaneous/consistent201#"
                xml:base="http://www.w3.org/2002/03owlt/miscellaneous/consistent201">

    <xsl:output method="xml" omit-xml-declaration="yes"  indent="yes"/>
    <!--<xsl:strip-space elements="*"/>-->

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="text()"/>

    <xsl:template match="list">
        <rdf:RDF
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
                xmlns:owl="http://www.w3.org/2002/07/owl#"
                xmlns="http://www.w3.org/1999/xhtml"
                xmlns:first="http://www.w3.org/2002/03owlt/miscellaneous/consistent201#"
                xml:base="http://www.w3.org/2002/03owlt/miscellaneous/consistent201">

            <xsl:apply-templates select="obj"/>
            <!--<xsl:for-each select="obj">-->
            <!--<xsl:apply-templates select="."/>-->
            <!--</xsl:for-each>-->
        </rdf:RDF>
    </xsl:template>

    <xsl:template match="obj">
        <rdf:Description rdf:about="http://10.10.10.6:8080/{@href}">
            <rdfs:label>
                <xsl:value-of select="@displayName"></xsl:value-of>
            </rdfs:label>
            <xsl:variable name="type">
                <xsl:choose>
                    <xsl:when test="enum/@val = 'buildingpart'">https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/BuildingOntology.owl#Zone</xsl:when>
                </xsl:choose>
            </xsl:variable>
            <rdf:type rdf:resource="{$type}"/>
            <rdf:type rdf:resource="http://www.w3.org/2002/07/owl#NamedIndividual"/>
        </rdf:Description>
    </xsl:template>

    <!--<xsl:template match="obj">-->
    <!--<owl:NamedIndividual rdf:about="{@href}">-->
    <!--<rdfs:label><xsl:value-of select="@displayName"></xsl:value-of></rdfs:label>-->
    <!--<xsl:variable name="type">-->
    <!--<xsl:choose>-->
    <!--<xsl:when test="enum/@val = 'buildingpart'">https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/BuildingOntology.owl#Zone</xsl:when>-->
    <!--</xsl:choose>-->
    <!--</xsl:variable>-->
    <!--<rdf:type rdf:resource="{$type}"/>-->
    <!--</owl:NamedIndividual>-->
    <!--</xsl:template>-->

</xsl:stylesheet>