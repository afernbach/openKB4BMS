<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE rdf:RDF [
        <!ENTITY EnergyResourceOntology "https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#" >
        <!ENTITY xsd "http://www.w3.org/2001/XMLSchema#" >
        ]>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:owl="http://www.w3.org/2002/07/owl#" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:knx="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
                xmlns:EnergyResourceOntology="https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#"
                version="2.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

    <xsl:output method="xml" indent="yes" />
    <xsl:strip-space elements="*"/>

    <xsl:param name="prefix" select="'http://localhost:8080/'"/>


    <xsl:template match="/">
        <xsl:element name="rdf:RDF" inherit-namespaces="no">
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>


    <xsl:template name="contains">
        <xsl:param name="building"/>
        <xsl:param name="value"/>
        <xsl:element name="EnergyResourceOntology:contains">
            <xsl:attribute name="rdf:resource">
                <xsl:choose>
                    <xsl:when test="$building">
                        <xsl:value-of select="concat($prefix, $value)"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="concat($prefix, substring($value, 2, string-length($value) - 3))"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
        </xsl:element>
    </xsl:template>


    <xsl:template match="obj[@is='knx:Part']">
        <xsl:element name="owl:NamedIndividual">
            <xsl:attribute name="rdf:about">
                <xsl:value-of select="concat($prefix, @href)"/>
            </xsl:attribute>

            <xsl:apply-templates select="@displayName|node()[not(self::list)]"/>
            <xsl:for-each select="list/obj[@is='knx:Part']">
                <xsl:call-template name="contains">
                    <xsl:with-param name="building">
                        <xsl:value-of select="true()"></xsl:value-of>
                    </xsl:with-param>
                    <xsl:with-param name="value">
                        <xsl:value-of select="@href"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
            <xsl:for-each select="list/obj[@is='knx:InstancePart']">
                <xsl:call-template name="contains">
                    <xsl:with-param name="value">
                        <xsl:value-of select="ref/@href"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:for-each>
        </xsl:element>

        <xsl:apply-templates select="list"/>

    </xsl:template>

    <xsl:template match="obj[@is='knx:ViewBuilding']">
        <xsl:apply-templates/>
    </xsl:template>


    <xsl:template name="instanceparts" match="obj[@is='knx:InstancePart']">
    </xsl:template>


    <xsl:template name="label" match="@displayName">
        <xsl:element name="rdfs:label" inherit-namespaces="yes">
            <xsl:attribute name="rdf:datatype">
                <xsl:text disable-output-escaping="yes">&xsd;string</xsl:text>
            </xsl:attribute>
            <xsl:value-of select="."></xsl:value-of>
        </xsl:element>
    </xsl:template>

    <xsl:template name="is" match="@is">
    </xsl:template>


    <xsl:template name="list" match="list">
        <xsl:for-each select="obj">

            <xsl:apply-templates select="."/>

        </xsl:for-each>
    </xsl:template>


    <xsl:template name="roomtype" match="enum[@name='type']">
        <xsl:element name="rdf:type">
            <xsl:attribute name="rdf:resource">
                <xsl:choose>
                    <xsl:when test="@val ='room'">
                        <xsl:text disable-output-escaping="yes">&EnergyResourceOntology;Room</xsl:text>
                    </xsl:when>
                    <xsl:when test="@val ='building'">
                        <xsl:text disable-output-escaping="yes">&EnergyResourceOntology;Building</xsl:text>
                    </xsl:when>
                    <xsl:when test="@val ='buildingpart'">
                        <xsl:text
                                disable-output-escaping="yes">&EnergyResourceOntology;BuildingPart</xsl:text>
                    </xsl:when>
                    <xsl:when test="@val ='floor'">
                        <xsl:text
                                disable-output-escaping="yes">&EnergyResourceOntology;BuildingStorey</xsl:text>
                    </xsl:when>
                    <xsl:when test="@val ='corridor'">
                        <xsl:text disable-output-escaping="yes">&EnergyResourceOntology;Corridor</xsl:text>
                    </xsl:when>
                    <xsl:when test="@val ='stairway'">
                        <xsl:text disable-output-escaping="yes">&EnergyResourceOntology;Stairway</xsl:text>
                    </xsl:when>
                    <xsl:when test="@val ='distributionboard'">
                        <xsl:text
                                disable-output-escaping="yes">&EnergyResourceOntology;DistributionBoard</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="@val"></xsl:value-of>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
        </xsl:element>

    </xsl:template>



</xsl:stylesheet>