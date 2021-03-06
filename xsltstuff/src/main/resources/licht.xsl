<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE rdf:RDF [
        <!ENTITY EnergyResourceOntology "https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#" >
        <!ENTITY xsd "http://www.w3.org/2001/XMLSchema#" >
        ]>


<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:owl="http://www.w3.org/2002/07/owl#" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:knx="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
                xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
                xmlns:EnergyResourceOntology="https://www.auto.tuwien.ac.at/downloads/thinkhome/ontology/EnergyResourceOntology.owl#"
                version="2.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://www.w3.org/1999/02/22-rdf-syntax-ns# ">
    <xsl:output method="xml" indent="yes"/>
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

    <xsl:template match="obj">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="int|enum|ref">
    </xsl:template>


    <xsl:template match="obj[@is='knx:InstanceGroup']">
    </xsl:template>

    <xsl:template match="list[@of='knx:Group' or @of='knx:InstanceGroup']">
        <xsl:apply-templates/>
    </xsl:template>


    <xsl:template match="obj[contains(@href,'licht')and@is='knx:Group']">
            <xsl:for-each select="list/obj[@is='knx:InstanceGroup']">
                <xsl:if test="contains(ref/@href, 'load_switch')">
                    <xsl:call-template name="demand">
                        <xsl:with-param name="value">
                            <xsl:value-of select="concat($prefix, ref/@href)"/>
                        </xsl:with-param>
                        <xsl:with-param name="off">
                            <xsl:text>On</xsl:text>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="demand">
                        <xsl:with-param name="value">
                            <xsl:value-of select="concat($prefix, ref/@href)"/>
                        </xsl:with-param>
                        <xsl:with-param name="off">
                            <xsl:text>Off</xsl:text>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="command">
                        <xsl:with-param name="value">
                            <xsl:value-of select="concat($prefix, ref/@href)"/>
                        </xsl:with-param>
                        <xsl:with-param name="off">
                            <xsl:text>Off</xsl:text>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="command">
                        <xsl:with-param name="value">
                            <xsl:value-of select="concat($prefix, ref/@href)"/>
                        </xsl:with-param>
                        <xsl:with-param name="off">
                            <xsl:text>On</xsl:text>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="supply">
                        <xsl:with-param name="value">
                            <xsl:value-of select="concat($prefix, ref/@href)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="state">
                        <xsl:with-param name="value">
                            <xsl:value-of select="concat($prefix, ref/@href)"/>
                        </xsl:with-param>
                        <xsl:with-param name="off">
                            <xsl:text>Off</xsl:text>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="state">
                        <xsl:with-param name="value">
                            <xsl:value-of select="concat($prefix, ref/@href)"/>
                        </xsl:with-param>
                        <xsl:with-param name="off">
                            <xsl:text>On</xsl:text>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="stateOnOff">
                        <xsl:with-param name="value">
                            <xsl:value-of select="concat($prefix, ref/@href)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="funcOnOff">
                        <xsl:with-param name="value">
                            <xsl:value-of select="concat($prefix, ref/@href)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="lamp">
                        <xsl:with-param name="value">
                            <xsl:value-of select="concat($prefix, ref/@href)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                    <xsl:call-template name="switch">
                        <xsl:with-param name="value">
                            <xsl:value-of select="concat($prefix, ref/@href)"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:if>
            </xsl:for-each>
    </xsl:template>

    <!-- <owl:NamedIndividual rdf:about="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnDemand">
        <rdf:type rdf:resource="&EnergyResourceOntology;EnergyDemand"/> <hasNativeValue
        rdf:datatype="&xsd;double">15.0</hasNativeValue> <hasNativeUnit rdf:datatype="&xsd;string">WattHours</hasNativeUnit>
        <ofEnergyType rdf:resource="&EnergyResourceOntology;ElectricEnergy"/> </owl:NamedIndividual> -->
    <xsl:template name="demand">
        <xsl:param name="off"/>
        <xsl:param name="value"/>
        <xsl:element name="owl:NamedIndividual">
            <xsl:attribute name="rdf:about">
                <xsl:value-of select="concat($value, '#demand', $off)"/>
            </xsl:attribute>
            <xsl:element name="rdf:type">
                <xsl:attribute name="rdf:resource">&EnergyResourceOntology;EnergyDemand</xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasNativeValue">
                <xsl:choose>
                    <xsl:when test="$off = 'On'">
                        <xsl:attribute name="rdf:datatype"><xsl:text
                                disable-output-escaping="yes">&xsd;double</xsl:text>
                        </xsl:attribute>
                        <xsl:text disable-output-escaping="yes">15.0</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:attribute name="rdf:datatype"><xsl:text
                                disable-output-escaping="yes">&xsd;double</xsl:text>
                        </xsl:attribute>
                        <xsl:text disable-output-escaping="yes">0.0</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasNativeUnit">
                <xsl:attribute name="rdf:datatype"><xsl:text
                        disable-output-escaping="yes">&xsd;string</xsl:text>
                </xsl:attribute>
                <xsl:text disable-output-escaping="yes">WattHours</xsl:text>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:ofEnergyType">
                <xsl:attribute name="rdf:resource">&EnergyResourceOntology;ElectricEnergy</xsl:attribute>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <!-- <owl:NamedIndividual rdf:about="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OffCommand">
    <rdf:type rdf:resource="&EnergyResourceOntology;OffCommand"/>
</owl:NamedIndividual> -->
    <xsl:template name="command">
        <xsl:param name="off"/>
        <xsl:param name="value"/>
        <xsl:element name="owl:NamedIndividual">
            <xsl:attribute name="rdf:about">
                <xsl:value-of select="concat($value, '#command', $off)"/>
            </xsl:attribute>
            <xsl:element name="rdf:type">
                <xsl:choose>
                    <xsl:when test="$off = 'On'">
                        <xsl:attribute name="rdf:resource">&EnergyResourceOntology;OnCommand</xsl:attribute>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:attribute name="rdf:resource">&EnergyResourceOntology;OffCommand</xsl:attribute>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:webserviceRelativeUrl">
                <xsl:attribute name="rdf:datatype"><xsl:text
                        disable-output-escaping="yes">&xsd;string</xsl:text>
                </xsl:attribute>
                <xsl:text disable-output-escaping="yes">value</xsl:text>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:webservicePayload">
                <xsl:choose>
                    <xsl:when test="$off = 'On'">
                    <xsl:attribute name="rdf:datatype"><xsl:text
                            disable-output-escaping="yes">&xsd;string</xsl:text>
                    </xsl:attribute>
                    <xsl:text disable-output-escaping="yes">&lt;![CDATA[ </xsl:text>
                        <xsl:text disable-output-escaping="yes">&lt;bool name="value" href="value/" val="true" displayName="On / Off" writable="true"/&gt; </xsl:text>
                        <xsl:text disable-output-escaping="yes">]]&gt; </xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:attribute name="rdf:datatype"><xsl:text
                                disable-output-escaping="yes">&xsd;string</xsl:text>
                        </xsl:attribute>
                        <xsl:text disable-output-escaping="yes">&lt;![CDATA[ </xsl:text>
                        <xsl:text disable-output-escaping="yes">&lt;bool name="value" href="value/" val="false" displayName="On / Off" writable="true"/&gt; </xsl:text>
                        <xsl:text disable-output-escaping="yes">]]&gt; </xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <!--<owl:NamedIndividual rdf:about="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnSupply">
    <rdf:type rdf:resource="&EnergyResourceOntology;EnergySupply"/>
    <hasNativeValue rdf:datatype="&xsd;double">750.0</hasNativeValue>
    <hasNativeUnit rdf:datatype="&xsd;string">Lumen</hasNativeUnit>
    <ofEnergyType rdf:resource="&EnergyResourceOntology;Light"/>
</owl:NamedIndividual>
 -->
    <xsl:template name="supply">
        <xsl:param name="value"/>
        <xsl:element name="owl:NamedIndividual">
            <xsl:attribute name="rdf:about">
                <xsl:value-of select="concat($value, '#supply')"/>
            </xsl:attribute>
            <xsl:element name="rdf:type">
                <xsl:attribute name="rdf:resource">&EnergyResourceOntology;EnergySupply</xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasNativeUnit">
                <xsl:attribute name="rdf:datatype"><xsl:text
                        disable-output-escaping="yes">&xsd;string</xsl:text>
                </xsl:attribute>
                <xsl:text disable-output-escaping="yes">Lumen</xsl:text>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasNativeValue">
                <xsl:attribute name="rdf:datatype"><xsl:text
                        disable-output-escaping="yes">&xsd;string</xsl:text>
                </xsl:attribute>
                <xsl:text disable-output-escaping="yes">750.0</xsl:text>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:ofEnergyType">
                <xsl:attribute name="rdf:resource">&EnergyResourceOntology;Light</xsl:attribute>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <!-- <owl:NamedIndividual rdf:about="&EnergyResourceOntology;LivingRoomSimpleLampLight1OffStateValue">
         <rdf:type rdf:resource="&EnergyResourceOntology;OffStateValue"/>
         <hasEnergyDemand rdf:resource="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OffDemand"/>
     </owl:NamedIndividual>-->

    <xsl:template name="state">
        <xsl:param name="off"/>
        <xsl:param name="value"/>
        <xsl:element name="owl:NamedIndividual">
            <xsl:attribute name="rdf:about">
                <xsl:value-of select="concat($value, '#state', $off)"/>
            </xsl:attribute>
            <xsl:element name="rdf:type">
                <xsl:choose>
                    <xsl:when test="$off = 'On'">
                        <xsl:attribute name="rdf:resource">&EnergyResourceOntology;OnStateValue</xsl:attribute>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:attribute name="rdf:resource">&EnergyResourceOntology;OffStateValue</xsl:attribute>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasEnergyDemand">
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat($value, '#demand',$off)"></xsl:value-of>
                </xsl:attribute>
            </xsl:element>
            <xsl:if test="$off = 'On'">
                <xsl:element name="EnergyResourceOntology:hasEnergySupply">
                    <xsl:attribute name="rdf:resource">
                        <xsl:value-of select="concat($value,'#supply',$off)"></xsl:value-of>
                    </xsl:attribute>
                </xsl:element>
            </xsl:if>
        </xsl:element>
    </xsl:template>

    <!--
<owl:NamedIndividual rdf:about="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnStateValue">
	<rdf:type rdf:resource="&EnergyResourceOntology;OnStateValue"/>
	<hasEnergyDemand rdf:resource="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnDemand"/>
	<hasEnergySupply rdf:resource="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnSupply"/>
</owl:NamedIndividual>
-->

    <!-- <owl:NamedIndividual rdf:about="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnOffState">
	<rdf:type rdf:resource="&EnergyResourceOntology;OnOffState"/>
	<hasStateValue rdf:resource="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnStateValue"/>
	<hasStateValue rdf:resource="&EnergyResourceOntology;LivingRoomSimpleLampLight1OffStateValue"/>
    </owl:NamedIndividual> -->

    <xsl:template name="stateOnOff">
        <xsl:param name="value"/>
        <xsl:element name="owl:NamedIndividual">
            <xsl:attribute name="rdf:about">
                <xsl:value-of select="concat($value, '#stateOnOff')"/>
            </xsl:attribute>
            <xsl:element name="rdf:type">
                <xsl:attribute name="rdf:resource">&EnergyResourceOntology;OnOffState</xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasStateValue">
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat($value, '#stateOn')"></xsl:value-of>
                </xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasStateValue">
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat($value, '#stateOff')"></xsl:value-of>
                </xsl:attribute>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <!--
    <owl:NamedIndividual rdf:about="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnOffFunctionality">
	<rdf:type rdf:resource="&EnergyResourceOntology;OnOffFunctionality"/>
	<hasCommand rdf:resource="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OffCommand"/>
	<hasCommand rdf:resource="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnCommand"/>
    </owl:NamedIndividual>
    -->

    <xsl:template name="funcOnOff">
        <xsl:param name="value"/>
        <xsl:element name="owl:NamedIndividual">
            <xsl:attribute name="rdf:about">
                <xsl:value-of select="concat($value, '#funcOnOff')"/>
            </xsl:attribute>
            <xsl:element name="rdf:type">
                <xsl:attribute name="rdf:resource">&EnergyResourceOntology;OnOffFunctionality</xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasCommand">
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat($value, '#commandOn')"></xsl:value-of>
                </xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasCommand">
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat($value, '#commandOff')"></xsl:value-of>
                </xsl:attribute>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <!--
    <owl:NamedIndividual rdf:about="&EnergyResourceOntology;LivingRoom1SimpleLampLight1">
	<rdf:type rdf:resource="&EnergyResourceOntology;SimpleLamp"/>
	<producesEnergyType rdf:resource="&EnergyResourceOntology;Light"/>
	<isIn rdf:resource="&EnergyResourceOntology;LivingRoom1"/>
	<hasFunctionality rdf:resource="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnOffFunctionality"/>
	<hasState rdf:resource="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnOffState"/>
	<hasCurrentStateValue rdf:resource="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnStateValue"/>
	<actuallyProducesEnergy rdf:resource="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnSupply"/>
	<maxProducesEnergy rdf:resource="&EnergyResourceOntology;LivingRoom1SimpleLampLight1OnSupply"/>
    </owl:NamedIndividual>
    -->

    <xsl:template name="lamp">
        <xsl:param name="value"/>
        <xsl:element name="owl:NamedIndividual">
            <xsl:attribute name="rdf:about">
                <xsl:value-of select="concat($value, '#lamp')"/>
            </xsl:attribute>
            <xsl:element name="rdf:type">
                <xsl:attribute name="rdf:resource">&EnergyResourceOntology;SimpleLamp</xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:producesEnergyType">
                <xsl:attribute name="rdf:resource">&EnergyResourceOntology;Light</xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasFunctionality">
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat($value, '#funcOnOff')"></xsl:value-of>
                </xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasState">
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat($value, '#stateOnOff')"></xsl:value-of>
                </xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasCurrentStateValue">
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat($value, '#stateOff')"></xsl:value-of>
                </xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:actuallyProducesEnergy">
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat($value, '#supplyOff')"></xsl:value-of>
                </xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:maxProducesEnergy">
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat($value, '#supplyOff')"></xsl:value-of>
                </xsl:attribute>
            </xsl:element>
        </xsl:element>
    </xsl:template>

    <!--

    <owl:NamedIndividual rdf:about="&EnergyResourceOntology;LivingRoom1LightSwitch1CentralOnOff">
	<rdf:type rdf:resource="&EnergyResourceOntology;OnOffLightSwitch"/>
	<isIn rdf:resource="&EnergyResourceOntology;LivingRoom1"/>
	<hasCurrentStateValue rdf:resource="&EnergyResourceOntology;LivingRoom1LightSwitch1CentralOnStateValue"/>
	<hasState rdf:resource="&EnergyResourceOntology;LivingRoom1LightSwitch1OnOffState"/>
	<controlledObject rdf:resource="&EnergyResourceOntology;LivingRoom1SimpleLampLight1"/>
    </owl:NamedIndividual>
    -->

    <xsl:template name="switch">
        <xsl:param name="value"/>
        <xsl:element name="owl:NamedIndividual">
            <xsl:attribute name="rdf:about">
                <xsl:value-of select="substring-before($value, '/1/datapoints')"/>
            </xsl:attribute>
            <xsl:element name="rdf:type">
                <xsl:attribute name="rdf:resource">&EnergyResourceOntology;OnOffLightSwitch</xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasState">
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat($value, '#stateOnOff')"></xsl:value-of>
                </xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:hasCurrentStateValue">
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat($value, '#stateOff')"></xsl:value-of>
                </xsl:attribute>
            </xsl:element>
            <xsl:element name="EnergyResourceOntology:controlledObject">
                <xsl:attribute name="rdf:resource">
                    <xsl:value-of select="concat($value, '#lamp')"></xsl:value-of>
                </xsl:attribute>
            </xsl:element>
        </xsl:element>
    </xsl:template>

</xsl:stylesheet>