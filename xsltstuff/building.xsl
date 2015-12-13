<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:owl="http://www.w3.org/2002/07/owl#"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:knx="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                exclude-result-prefixes="owl rdf knx">

    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>

    <xsl:param name="prefix" select="'http://localhost:8080/'"/>

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>


    <xsl:template match="obj[@is='knx:ViewBuilding']">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template name="parts" match="obj[@is='knx:Part']">
        <xsl:element name="owl:NamedIndividual">

            <xsl:attribute name="rdf:about">
                <xsl:value-of select="concat($prefix, @href)"/>
            </xsl:attribute>

            <xsl:apply-templates select="enum"/>

            <xsl:apply-templates select="@displayName"/>

            <xsl:apply-templates select="list"/>

        </xsl:element>
    </xsl:template>


    <xsl:template name="list" match="list">
        <xsl:for-each select="obj">

            <xsl:apply-templates select="."/>

        </xsl:for-each>
    </xsl:template>

    <xsl:template name="instanceparts" match="obj[@is='knx:InstancePart']">
        <xsl:element name="contains">
            <xsl:attribute name="rdf:reference">
                <xsl:value-of select="concat($prefix, ref/@href)"/>
            </xsl:attribute>
        </xsl:element>
    </xsl:template>

    <xsl:template name="label" match="@displayName">
        <xsl:element name="rdfs:label">
            <xsl:attribute name="rdf:datatype">
                <xsl:text disable-output-escaping="yes"><![CDATA[&]]>xsd;string</xsl:text>
            </xsl:attribute>
            <xsl:value-of select="."></xsl:value-of>
        </xsl:element>
    </xsl:template>


    <xsl:template name="roomtype" match="enum[@name='type']">
        <xsl:element name="rdf:type">
            <xsl:attribute name="rdf:reference">
                <xsl:choose>
                    <xsl:when test="@val ='room'">
                        <xsl:text disable-output-escaping="yes"><![CDATA[&]]>EnergyResourceOntology;Room</xsl:text>
                    </xsl:when>
                    <xsl:when test="@val ='building'">
                        <xsl:text disable-output-escaping="yes"><![CDATA[&]]>EnergyResourceOntology;Building</xsl:text>
                    </xsl:when>
                    <xsl:when test="@val ='buildingpart'">
                        <xsl:text
                                disable-output-escaping="yes"><![CDATA[&]]>EnergyResourceOntology;BuildingPart</xsl:text>
                    </xsl:when>
                    <xsl:when test="@val ='floor'">
                        <xsl:text
                                disable-output-escaping="yes"><![CDATA[&]]>EnergyResourceOntology;BuildingStorey</xsl:text>
                    </xsl:when>
                    <xsl:when test="@val ='corridor'">
                        <xsl:text disable-output-escaping="yes"><![CDATA[&]]>EnergyResourceOntology;Corridor</xsl:text>
                    </xsl:when>
                    <xsl:when test="@val ='stairway'">
                        <xsl:text disable-output-escaping="yes"><![CDATA[&]]>EnergyResourceOntology;Stairway</xsl:text>
                    </xsl:when>
                    <xsl:when test="@val ='distributionboard'">
                        <xsl:text
                                disable-output-escaping="yes"><![CDATA[&]]>EnergyResourceOntology;DistributionBoard</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="@val"></xsl:value-of>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:attribute>
        </xsl:element>

    </xsl:template>

</xsl:stylesheet>