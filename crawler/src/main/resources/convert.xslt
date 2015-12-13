<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   
	<!--<xsl:param name="page" 				select="'1'" />-->
   <!---->
	<!--<xsl:variable name="vip" 			select="'0'" 	/>-->
    <!--<xsl:variable name="posFilter" 		select="'all'" 	/>-->
    <!--<xsl:variable name="planPos" 		select="1" 		/>-->
    <!--<xsl:variable name="vipFilter" 		select="'all'"	/>-->
    <!--<xsl:variable name="euFilter" 		select="'all'" 	/>-->
    <!--<xsl:variable name="airlineFilter" 	select="'all'" 	/>-->
    <!--<xsl:variable name="typeFilter" 	select="'all'" 	/>-->
    <!--<xsl:variable name="gacFilter" 		select="0" 		/>-->
	<!---->
	<!--<xsl:variable name="min" select="(1 + 24 * ($page - 1))" />-->
	<!--<xsl:variable name="max" select="(24 * ($page))" />-->
	<!---->
	<xsl:output method="xml" indent="yes"/>

	<!-- 
	FUNCTION: 
	wrap content with <blink>...</blink>, if a given boolean param is true.
	-->
	<!--<xsl:template name="blink">-->
		<!--<xsl:param name="blink"/>-->
		<!--<xsl:param name="content" />-->
		<!--<xsl:choose>-->
			<!--&lt;!&ndash;  -->
			<!--The primary target platform is FireFox 3.6.8.-->
			<!--On FireFox 3.6.8 the blink style is interpreted, but interferes with the <blink>..</blink> tags;-->
			<!--so, the <blink> tag is removed!-->
			<!--IMPORTANT: because of this, on IE 6, nothing will blinkm since IE 6 just interprets the <blink> tag, -->
			<!--but not the style!-->
			<!--&ndash;&gt;-->
			<!--&lt;!&ndash; <xsl:when test="$blink"><blink><xsl:value-of select="$content"/></blink></xsl:when> &ndash;&gt;-->
			<!--<xsl:when test="$blink"><xsl:value-of select="$content"/></xsl:when>-->
			<!--<xsl:otherwise><xsl:value-of select="$content"/></xsl:otherwise>-->
		<!--</xsl:choose>-->
	<!--</xsl:template>-->


	<!--&lt;!&ndash; -->
	<!--FUNCTION: -->
	<!--Check, if given string is set and has a length > 0; print a non-breaking space -->
	<!--if no valid string is present. -->
	<!--&ndash;&gt;-->
	<!--<xsl:template name="nbstr">-->
		<!--<xsl:param name="s" />-->
		<!--<xsl:choose>-->
			<!--<xsl:when test="$s and string-length(normalize-space($s)) > 0">-->
				<!--<xsl:value-of select="normalize-space($s)"/>-->
			<!--</xsl:when>-->
			<!--<xsl:otherwise>&#160;</xsl:otherwise>-->
		<!--</xsl:choose>-->
	<!--</xsl:template>-->



	<!--<xsl:template match="/wrkmon">-->
	<!---->
		<!--<xsl:apply-templates />-->

		<!--&lt;!&ndash; button bar rendering &ndash;&gt;-->
		<!--&lt;!&ndash; DISABLED, since no touch screen is available on cute terminals &ndash;&gt;-->
		<!--&lt;!&ndash; -->
		<!--<xsl:variable name="isPage1" select="$page = 0"/>-->
		<!--<xsl:variable name="isPage2" select="$page = 1"/>-->
		<!--<xsl:variable name="isPage3" select="$page = 2"/>-->
		<!--<xsl:variable name="isPage4" select="$page = 3"/>-->
		<!---->
		<!--<div class="button_bar">-->
			<!--<a href="workmon.jsp?page=0"><div class="button_{$isPage1}">1</div></a>-->
			<!--<a href="workmon.jsp?page=1"><div class="button_{$isPage2}">2</div></a>-->
			<!--<a href="workmon.jsp?page=2"><div class="button_{$isPage3}">3</div></a>-->
			<!--<a href="workmon.jsp?page=3"><div class="button_{$isPage4}">4</div></a>-->
		<!--</div>-->
		<!--&ndash;&gt;-->
	<!--</xsl:template>-->
	<!---->

    <!--<xsl:template match="/wrkmon/in">-->
        <!--<xsl:call-template name="inpage">-->
            <!--<xsl:with-param name="min" select="$min"/>-->
            <!--<xsl:with-param name="max" select="$max"/>-->
            <!--<xsl:with-param name="page" select="$page"/>-->
        <!--</xsl:call-template>-->
    <!--</xsl:template>-->

    <!--<xsl:template match="/wrkmon/out">-->
        <!--<xsl:call-template name="outpage">-->
            <!--<xsl:with-param name="min" select="$min"/>-->
            <!--<xsl:with-param name="max" select="$max"/>-->
            <!--<xsl:with-param name="page" select="$page"/>-->
        <!--</xsl:call-template>-->
    <!--</xsl:template>-->

    <xsl:template match="/list">
        <xsl:value-of select="@name"/>
    </xsl:template>


	<!--&lt;!&ndash; ================================================ &ndash;&gt;-->
	<!--&lt;!&ndash; IN PAGE 										  &ndash;&gt;-->
	<!--&lt;!&ndash; ================================================ &ndash;&gt;-->
	<!--<xsl:template name="inpage">-->

		<!--<xsl:param name="min"/>-->
		<!--<xsl:param name="max"/>-->
		<!--<xsl:param name="page"/>-->

		<!--<xsl:for-each select="r[($posFilter 	= 'all' or pgrp		= $posFilter)        		and-->
        	                    <!--($vipFilter     = 'all' or flt/@vip = $vipFilter)     			and-->
            		            <!--($euFilter      = 'all' or flt/@eu  = $euFilter)      			and-->
                    	        <!--($gacFilter     = 'all' or flt/@gac = $gacFilter)     			and-->
                        	    <!--($airlineFilter = 'all' or contains($airlineFilter, flt/@air)) 	and-->
                            	<!--($typeFilter    = 'all' or contains($typeFilter, flt/type))]"> -->
                            <!---->
			<!--<xsl:sort data-type="number" select="@nr"/>-->
			<!--<xsl:if test="(position() &lt;= $max) and (position() &gt;= $min)">-->
            	<!--<xsl:variable name="line" select="((position() - 1) mod 24) + 1"/>-->
                <!--&lt;!&ndash; xsl:variable name="y" select="$line * 19.5 + ($page * 480)"/ &ndash;&gt;-->
                <!--<xsl:variable name="y" select="$line * 16"/>-->
                <!--<xsl:call-template name="inline">-->
                	<!--<xsl:with-param name="ycoord" select="$y"/>-->
				<!--</xsl:call-template>-->
			<!--</xsl:if>-->
			<!---->
		<!--</xsl:for-each>-->
		<!---->
	<!--</xsl:template>-->


	<!--&lt;!&ndash; ================================================ &ndash;&gt;-->
	<!--&lt;!&ndash; OUT PAGE 										  &ndash;&gt;-->
	<!--&lt;!&ndash; ================================================ &ndash;&gt;-->
	<!--<xsl:template name="outpage">-->

		<!--<xsl:param name="min"/>-->
		<!--<xsl:param name="max"/>-->
		<!--<xsl:param name="page"/>-->

		<!--<xsl:for-each select="r[($posFilter     = 'all' or pgrp = $posFilter)         			and-->
                                <!--($vipFilter     = 'all' or flt/@vip = $vipFilter)     			and-->
                                <!--($euFilter      = 'all' or flt/@eu  = $euFilter)      			and-->
                                <!--($airlineFilter = 'all' or contains($airlineFilter, flt/@air)) 	and-->
                                <!--($gacFilter     = 'all' or flt/@gac = $gacFilter)     			and-->
                                <!--($typeFilter    = 'all' or contains($typeFilter, flt/type))]">-->
			<!---->
			<!--<xsl:sort data-type="number" select="@nr"/>-->
			<!--<xsl:if test="(position() &lt;= $max) and (position() &gt;= $min)">-->
				<!--<xsl:variable name="line" select="((position() - 1) mod 24) + 1"/>-->
                <!--&lt;!&ndash; xsl:variable name="y" select="$line * 19.5 + ($page * 480)"/ &ndash;&gt;-->
                <!--<xsl:variable name="y" select="$line * 16"/>-->
                <!--<xsl:call-template name="outline">-->
                	<!--<xsl:with-param name="ycoord" select="$y"/>-->
				<!--</xsl:call-template>-->
			<!--</xsl:if>-->
		<!--</xsl:for-each>-->

	<!--</xsl:template>-->


	<!--&lt;!&ndash; ================================================ &ndash;&gt;-->
	<!--&lt;!&ndash; IN LINE 										  &ndash;&gt;-->
	<!--&lt;!&ndash; ================================================ &ndash;&gt;-->
    <!--<xsl:template name="inline">-->
    <!---->
		<!--<xsl:param name="ycoord"/>-->
		<!---->
        <!--<xsl:variable name="area"	select="flt/@area"/>-->
        <!--<xsl:variable name="mod2"><xsl:value-of select="position() mod 2" /></xsl:variable>-->

		<!--<div class="line_in bg_{$mod2}" style="top:{$ycoord}">-->
        <!--<xsl:choose>-->
			<!--<xsl:when test="flt/@gac='0'">-->

				<!--&lt;!&ndash; AIRLINE &ndash;&gt;-->
                <!--<xsl:variable name="blink_flt_in" select="flt/@bl = '1'"/>-->
                <!--<div class="field air_in area_{$area} blink_{$blink_flt_in}">-->
                    <!--<xsl:call-template name="blink">-->
                        <!--<xsl:with-param name="blink" select="$blink_flt_in"/>-->
                        <!--<xsl:with-param name="content">-->
                            <!--<xsl:call-template name="nbstr"><xsl:with-param name="s" select="flt/@air" /></xsl:call-template>-->
                        <!--</xsl:with-param>-->
                    <!--</xsl:call-template>-->
                <!--</div>-->

                <!--&lt;!&ndash; FLIGHT NUMBER &ndash;&gt;-->
                <!--<div class="field nr_in area_{$area} blink_{$blink_flt_in}">-->
                    <!--<xsl:call-template name="blink">-->
                        <!--<xsl:with-param name="blink" select="$blink_flt_in"/>-->
                        <!--<xsl:with-param name="content">-->
                            <!--<xsl:call-template name="nbstr"><xsl:with-param name="s" select="flt/@nr" /></xsl:call-template>-->
                        <!--</xsl:with-param>-->
                    <!--</xsl:call-template>-->
                <!--</div>-->
                <!---->
                <!--&lt;!&ndash; CHARTER &ndash;&gt;-->
                <!--<div class="field cha_in">-->
                	<!--<xsl:choose>-->
                		<!--<xsl:when test="flt/@cha='1'">+</xsl:when>-->
                		<!--<xsl:otherwise>&#160;</xsl:otherwise>-->
                	<!--</xsl:choose>-->
                <!--</div>-->
                <!---->
                <!--&lt;!&ndash; REGISTRATION &ndash;&gt;-->
                <!--<div class="field reg_in"><xsl:call-template name="nbstr"><xsl:with-param name="s" select="flt/@reg" /></xsl:call-template></div>-->
                <!---->
                <!--&lt;!&ndash; POSITION &ndash;&gt;-->
                <!--<xsl:variable name="blink_pos_in" select="(flt/@stat = 'HDG') or ($planPos='1' and ppos/@bl='1') or ($planPos='0' and pos/@bl='1') "/>-->
                <!--<xsl:variable name="color_pos_in">-->
                	<!--<xsl:choose>-->
                		<!--<xsl:when test="$planPos = '1' and ppos/@pl = '1' and flt/@stat != 'HDG'">grey</xsl:when>-->
                		<!--<xsl:otherwise>yellow</xsl:otherwise>-->
                	<!--</xsl:choose>-->
                <!--</xsl:variable>-->
                <!--<div class="field pos_in blink_{$blink_pos_in} pos_in_color_{$color_pos_in}">-->
					<!--<xsl:choose>-->
		                <!--<xsl:when test="flt/@stat='HDG'"><blink>H</blink></xsl:when>-->
		                <!--<xsl:otherwise>-->
							<!--<xsl:choose>-->
								<!--<xsl:when test="$planPos = '1'">-->
									<!--<xsl:call-template name="blink">-->
										<!--<xsl:with-param name="blink" select="$blink_pos_in"/>-->
										<!--<xsl:with-param name="content">-->
											<!--<xsl:call-template name="nbstr"><xsl:with-param name="s" select="ppos" /></xsl:call-template>-->
										<!--</xsl:with-param>-->
									<!--</xsl:call-template>-->
								<!--</xsl:when>-->
								<!--<xsl:otherwise>-->
									<!--<xsl:call-template name="blink">-->
										<!--<xsl:with-param name="blink" select="$blink_pos_in"/>-->
										<!--<xsl:with-param name="content">-->
											<!--<xsl:call-template name="nbstr"><xsl:with-param name="s" select="pos" /></xsl:call-template>-->
										<!--</xsl:with-param>-->
									<!--</xsl:call-template>-->
								<!--</xsl:otherwise>-->
							<!--</xsl:choose>-->
		                <!--</xsl:otherwise>-->
	                <!--</xsl:choose>-->
                <!--</div>-->
                <!---->
                <!--&lt;!&ndash; EU/VIP &ndash;&gt;-->
                <!--<xsl:variable name="euvip">-->
                	<!--<xsl:choose>-->
                		<!--<xsl:when test="flt/@eu = '1' and flt/@vip = '0'">E</xsl:when>-->
                		<!--<xsl:when test="flt/@eu = '1' and flt/@vip = '1'">V</xsl:when>-->
                		<!--<xsl:otherwise>NONE</xsl:otherwise>-->
                	<!--</xsl:choose>-->
                <!--</xsl:variable>-->
                <!--<div class="field euvip_in euvip_{$euvip}">-->
                	<!--<xsl:choose>-->
                		<!--<xsl:when test="flt/@eu = '1' and flt/@vip = '0'">E</xsl:when>-->
                		<!--<xsl:when test="flt/@eu = '0' and flt/@vip = '1'">=</xsl:when>-->
                		<!--<xsl:when test="flt/@eu = '1' and flt/@vip = '1'">V</xsl:when>-->
                		<!--<xsl:otherwise>&#160;</xsl:otherwise>-->
                	<!--</xsl:choose>-->
                <!--</div>-->
                <!---->
                <!--&lt;!&ndash; TYPE &ndash;&gt;-->
				<!--<xsl:variable name="type_in_clr_6" select="flt/type/@clr='6'"/>-->
				<!--<div class="field type_in type_{$type_in_clr_6}">-->
					<!--<xsl:call-template name="nbstr">-->
						<!--<xsl:with-param name="s" select="substring(flt/type, 1, 3)"/>-->
					<!--</xsl:call-template>-->
				<!--</div>-->

				<!--&lt;!&ndash; CARGOTYPE (added cargotype by scm at 21.5.2007) &ndash;&gt;-->
				<!--&lt;!&ndash; -->
				<!--Sometimes, 'type' has a length of 4, with a 'C' as 4th char to mark a cargo flight.-->
				<!--Due to this cases, we print only the first 3 digits in type, BUT reproduce the 'C' in the next column 'CARGOTYPE'! -->
			 	<!--&ndash;&gt;-->
			 	<!--<xsl:variable name="ctype_in_clr_7" select="flt/ctype/@clr='7'"/>-->
			 	<!--<xsl:variable name="ctype_in">-->
			 		<!--<xsl:choose>-->
				 		<!--<xsl:when test="string-length(flt/type) = 4"><xsl:value-of select="substring(flt/type, 4, 1)" /></xsl:when>-->
				 		<!--<xsl:when test="flt/ctype"><xsl:value-of select="flt/ctype"/></xsl:when>-->
				 		<!--<xsl:otherwise>&#160;</xsl:otherwise>-->
			 		<!--</xsl:choose>-->
			 	<!--</xsl:variable>-->

                <!--<xsl:choose>-->
                    <!--<xsl:when test="$ctype_in = substring(flt/type, 4, 1)">-->
                        <!--&lt;!&ndash; cargotype 'C'  (from type) should be added to the 'type' in the same color&ndash;&gt;-->
                        <!--<div class="field ctype_in type_{$type_in_clr_6}">-->
                            <!--<xsl:value-of select="$ctype_in" />-->
                        <!--</div>-->
                    <!--</xsl:when>-->
                    <!--<xsl:otherwise>-->
                        <!--&lt;!&ndash; cargotype &ndash;&gt;-->
                        <!--<div class="field ctype_in ctype_{$ctype_in_clr_7}">-->
                            <!--<xsl:value-of select="$ctype_in" />-->
                        <!--</div>-->
                    <!--</xsl:otherwise>-->
                <!--</xsl:choose>-->
                <!---->
                <!--&lt;!&ndash; SCHEDULE &ndash;&gt;-->
                <!--<div class="field sched_in"><xsl:value-of select="time/@sched"/></div>-->

                <!--&lt;!&ndash; CHG &ndash;&gt;-->
				<!--<div class="field chg_in">-->
					<!--<xsl:call-template name="nbstr">-->
                		<!--<xsl:with-param name="s" select="time/@chg"/>-->
                	<!--</xsl:call-template>-->
                <!--</div>-->
                <!---->
                <!--&lt;!&ndash; ESTIMATE (change by scm at 08/2009  due to astos/estimated TOUCHDOWN ) &ndash;&gt;-->
                <!--<xsl:variable name="color_est_in">-->
			        <!--<xsl:choose>-->
			        	<!--<xsl:when test="(time/@eblo and (flt/@stat='APP' or flt/@stat='LDG' or flt/@stat='BLI')) or (not(time/@eblo) and (flt/@stat='LDG' or flt/@stat='BLI'))">yellow</xsl:when>-->
			            <!--<xsl:when test="(flt/@stat='CNX')">yellow</xsl:when>-->
			            <!--<xsl:otherwise>grey</xsl:otherwise>-->
			        <!--</xsl:choose>		-->
                <!--</xsl:variable>-->
                <!--<div class="field est_in est_in_color_{$color_est_in}">-->
					<!--<xsl:call-template name="nbstr">-->
                		<!--<xsl:with-param name="s" select="time/@est"/>-->
                	<!--</xsl:call-template>                -->
                <!--</div>-->
                <!---->
                <!--&lt;!&ndash; LAND &ndash;&gt;-->
                <!--<div class="field land_in">-->
               		<!--<xsl:call-template name="nbstr">-->
                		<!--<xsl:with-param name="s" select="time/@land"/>-->
                	<!--</xsl:call-template>                  -->
                <!--</div>-->

                <!--&lt;!&ndash; BLO &ndash;&gt;-->
                <!--<xsl:variable name="blo_in_value">-->
                	<!--<xsl:choose>-->
                		<!--<xsl:when test="time/@blo and string-length(normalize-space(time/@blo)) > 0">-->
                			<!--<xsl:value-of select="time/@blo" />-->
                		<!--</xsl:when>-->
                		<!--<xsl:when test="ifo and string-length(normalize-space(ifo)) > 0">-->
                			<!--<xsl:value-of select="substring(ifo, 1, 2)" />-->
                		<!--</xsl:when>-->
                		<!--<xsl:otherwise>&#160;</xsl:otherwise>-->
                	<!--</xsl:choose>-->
                <!--</xsl:variable>-->
                <!--<div class="field blo_in">-->
                	<!--<xsl:call-template name="nbstr"><xsl:with-param name="s" select="$blo_in_value"/></xsl:call-template>                  -->
                <!--</div>-->
               	<!---->
				<!--&lt;!&ndash; LOAD/PASS &ndash;&gt;-->
                <!--<div class="field pass_in">-->
               		<!--<xsl:call-template name="nbstr">-->
                		<!--<xsl:with-param name="s" select="load/@pass"/>-->
                	<!--</xsl:call-template>                  -->
                <!--</div>-->

				<!--&lt;!&ndash; LOAD/SPL &ndash;&gt;-->
                <!--<div class="field spl_in">-->
               		<!--<xsl:call-template name="nbstr">-->
                		<!--<xsl:with-param name="s" select="load/@spl"/>-->
                	<!--</xsl:call-template>                  -->
                <!--</div>-->

				<!--&lt;!&ndash; LOAD/WGT &ndash;&gt;-->
                <!--<div class="field wgt_in">-->
               		<!--<xsl:call-template name="nbstr">-->
                		<!--<xsl:with-param name="s" select="load/@wgt"/>-->
                	<!--</xsl:call-template>                  -->
                <!--</div>-->
                <!---->
			<!--</xsl:when>-->
		<!--</xsl:choose>-->
        <!--</div>-->
		<!---->
	<!--</xsl:template>-->


	<!--&lt;!&ndash; ================================================ &ndash;&gt;-->
	<!--&lt;!&ndash; OUT LINE										  &ndash;&gt;-->
	<!--&lt;!&ndash; ================================================ &ndash;&gt;-->
    <!--<xsl:template name="outline">-->
    <!---->
		<!--<xsl:param name="ycoord"/>-->
		<!---->
        <!--<xsl:variable name="blink"	select="flt/@bl='1'"/>-->
        <!--<xsl:variable name="area"	select="flt/@area"/>-->
        <!--<xsl:variable name="mod2"><xsl:value-of select="position() mod 2" /></xsl:variable>-->

		<!--<div class="line_out bg_{$mod2}" style="top:{$ycoord}">-->
        <!--<xsl:choose>-->
			<!--<xsl:when test="flt/@gac='0'">-->
			<!---->
				<!--&lt;!&ndash; AIRLINE &ndash;&gt;-->
                <!--<div class="field air_out area_{$area}"><xsl:value-of select="flt/@air"/></div>-->
                <!---->
                <!--&lt;!&ndash; FLIGHT NUMBER &ndash;&gt;-->
                <!--<div class="field nr_out area_{$area}"><xsl:value-of select="flt/@nr"/></div>-->
                <!---->
                <!--&lt;!&ndash; CHARTER &ndash;&gt;-->
                <!--<div class="field cha_out">-->
                	<!--<xsl:choose>-->
                		<!--<xsl:when test="flt/@cha='1'">+</xsl:when>-->
                		<!--<xsl:otherwise>&#160;</xsl:otherwise>-->
                	<!--</xsl:choose>-->
                <!--</div>-->
                <!---->
                <!--&lt;!&ndash; REGISTRATION &ndash;&gt;-->
                <!--<div class="field reg_out"><xsl:call-template name="nbstr"><xsl:with-param name="s" select="flt/@reg" /></xsl:call-template></div>-->
                <!---->
               	<!--&lt;!&ndash; POST &ndash;&gt;-->
               	<!--<div class="field pst_out"><xsl:call-template name="nbstr"><xsl:with-param name="s" select="flt/@pst" /></xsl:call-template></div>-->
                <!---->
                <!--&lt;!&ndash; POSITION &ndash;&gt;-->
                <!--<xsl:variable name="blink_pos_out" select="pos/@bl = '1'"/>-->
                <!--<xsl:variable name="color_pos_out">-->
                	<!--<xsl:choose>-->
                		<!--<xsl:when test="$planPos = '1' and ppos/@pl = '1'">grey</xsl:when>-->
                		<!--<xsl:otherwise>yellow</xsl:otherwise>-->
                	<!--</xsl:choose>-->
                <!--</xsl:variable>-->
				<!--<div class="field pos_out blink_{$blink_pos_out} pos_out_color_{$color_pos_out}">-->
                	<!--<xsl:choose>-->
                		<!--<xsl:when test="$planPos = '1'">-->
               				<!--<xsl:call-template name="blink">-->
								<!--<xsl:with-param name="blink" select="$blink_pos_out"/>-->
								<!--<xsl:with-param name="content">-->
									<!--<xsl:call-template name="nbstr"><xsl:with-param name="s" select="ppos" /></xsl:call-template>-->
								<!--</xsl:with-param>-->
							<!--</xsl:call-template>-->
						<!--</xsl:when>-->
						<!--<xsl:otherwise>-->
               				<!--<xsl:call-template name="blink">-->
								<!--<xsl:with-param name="blink" select="$blink_pos_out"/>-->
								<!--<xsl:with-param name="content">-->
									<!--<xsl:call-template name="nbstr"><xsl:with-param name="s" select="pos" /></xsl:call-template>-->
								<!--</xsl:with-param>-->
							<!--</xsl:call-template>-->
						<!--</xsl:otherwise>-->
					<!--</xsl:choose>-->
				<!--</div>-->
				<!---->
				<!--&lt;!&ndash; EU/VIP &ndash;&gt;-->
                <!--<xsl:variable name="euvip">-->
                	<!--<xsl:choose>-->
                		<!--<xsl:when test="flt/@eu = '1' and flt/@vip = '0'">E</xsl:when>-->
                		<!--<xsl:when test="flt/@eu = '1' and flt/@vip = '1'">V</xsl:when>-->
                		<!--<xsl:otherwise>NONE</xsl:otherwise>-->
                	<!--</xsl:choose>-->
                <!--</xsl:variable>-->
                <!--<div class="field euvip_out euvip_{$euvip}">-->
                	<!--<xsl:choose>-->
                		<!--<xsl:when test="flt/@eu = '1' and flt/@vip = '0'">E</xsl:when>-->
                		<!--<xsl:when test="flt/@eu = '0' and flt/@vip = '1'">=</xsl:when>-->
                		<!--<xsl:when test="flt/@eu = '1' and flt/@vip = '1'">V</xsl:when>-->
                		<!--<xsl:otherwise>&#160;</xsl:otherwise>-->
                	<!--</xsl:choose>-->
                <!--</div>	-->
				<!---->
				<!--&lt;!&ndash; TYPE &ndash;&gt;-->
				<!--<xsl:variable name="type_out_clr_6" select="flt/type/@clr='6'"/>-->
				<!--<div class="field type_out type_{$type_out_clr_6}">-->
					<!--<xsl:call-template name="nbstr">-->
						<!--<xsl:with-param name="s" select="substring(flt/type, 1, 3)"/>-->
					<!--</xsl:call-template>-->
				<!--</div>-->
				<!---->
				<!--&lt;!&ndash; CARGOTYPE (added cargotype by scm at 21.5.2007) &ndash;&gt;-->
				<!--&lt;!&ndash; -->
				<!--Sometimes, 'type' has a length of 4, with a 'C' as 4th char to mark a cargo flight.-->
				<!--Due to this cases, we print only the first 3 digits in type, BUT reproduce the 'C' in the next column 'CARGOTYPE'! -->
			 	<!--&ndash;&gt;-->
				<!--<xsl:variable name="ctype_out_clr_7" select="flt/ctype/@clr='7'"/>-->
			 	<!--<xsl:variable name="ctype_out">-->
			 		<!--<xsl:choose>-->
				 		<!--<xsl:when test="string-length(flt/type) = 4"><xsl:value-of select="substring(flt/type, 4, 1)" /></xsl:when>-->
				 		<!--<xsl:when test="flt/ctype"><xsl:value-of select="flt/ctype"/></xsl:when>-->
				 		<!--<xsl:otherwise>&#160;</xsl:otherwise>-->
			 		<!--</xsl:choose>-->
			 	<!--</xsl:variable>-->

                <!--<xsl:choose>-->
                    <!--<xsl:when test="$ctype_out = substring(flt/type, 4, 1)">-->
                        <!--&lt;!&ndash; cargotype 'C'  (from type) should be added to the 'type' in the same color&ndash;&gt;-->
                        <!--<div class="field ctype_out type_{$type_out_clr_6}">-->
                            <!--<xsl:value-of select="$ctype_out" />-->
                        <!--</div>-->
                    <!--</xsl:when>-->
                    <!--<xsl:otherwise>-->
                        <!--&lt;!&ndash; cargotype &ndash;&gt;-->
                        <!--<div class="field ctype_out ctype_{$ctype_out_clr_7}">-->
                            <!--<xsl:value-of select="$ctype_out" />-->
                        <!--</div>-->
                    <!--</xsl:otherwise>-->
                <!--</xsl:choose>-->

				<!---->
				<!--&lt;!&ndash; SCHEDULE &ndash;&gt;-->
				<!--<div class="field sched_out"><xsl:value-of select="time/@sched"/></div>-->
				<!---->
				<!--&lt;!&ndash; CHG &ndash;&gt;-->
                <!--<div class="field chg_out">-->
					<!--<xsl:call-template name="nbstr">-->
                		<!--<xsl:with-param name="s" select="time/@chg"/>-->
                	<!--</xsl:call-template>-->
                <!--</div>-->
				<!---->
				<!--&lt;!&ndash; ESTIMATE &ndash;&gt;-->
				<!--<div class="field est_out">-->
					<!--<xsl:call-template name="nbstr">-->
                		<!--<xsl:with-param name="s" select="time/@est"/>-->
                	<!--</xsl:call-template>-->
				<!--</div>-->
						<!---->
				<!--&lt;!&ndash; LAND &ndash;&gt;-->
                <!--<div class="field land_out">-->
               		<!--<xsl:call-template name="nbstr">-->
                		<!--<xsl:with-param name="s" select="time/@land"/>-->
                	<!--</xsl:call-template>                  -->
                <!--</div>-->
                <!---->
				<!--&lt;!&ndash; AIR &ndash;&gt;-->
                <!--<xsl:variable name="air_out_value">-->
                	<!--<xsl:choose>-->
                		<!--<xsl:when test="time/@air and string-length(normalize-space(time/@air)) > 0">-->
                			<!--<xsl:value-of select="time/@air" />-->
                		<!--</xsl:when>-->
                		<!--<xsl:when test="ifo and string-length(normalize-space(ifo)) > 0">-->
                			<!--<xsl:value-of select="substring(ifo, 1, 2)" />-->
                		<!--</xsl:when>-->
                		<!--<xsl:otherwise>&#160;</xsl:otherwise>-->
                	<!--</xsl:choose>-->
                <!--</xsl:variable>-->
                <!--<div class="field air_out">-->
                	<!--<xsl:call-template name="nbstr"><xsl:with-param name="s" select="$air_out_value"/></xsl:call-template>                  -->
                <!--</div>-->

                <!--&lt;!&ndash; gate visible start &ndash;&gt;-->
                <!--<div class="field gt_area_out">-->
                    <!--<xsl:call-template name="nbstr">-->
                        <!--<xsl:with-param name="s" select="substring(gt,1,1)"/>-->
                    <!--</xsl:call-template>-->
                <!--</div>-->

                <!--<xsl:variable name="color_gt_out">-->
                    <!--<xsl:choose>-->
                        <!--<xsl:when test="gt/@vis='1'">yellow</xsl:when>-->
                        <!--<xsl:otherwise>grey</xsl:otherwise>-->
                    <!--</xsl:choose>-->
                <!--</xsl:variable>-->
                <!--<div class="field gt_nr_out gt_nr_out_color_{$color_gt_out}">-->
                    <!--<xsl:call-template name="nbstr">-->
                        <!--<xsl:with-param name="s" select="substring(gt,2)"/>-->
                    <!--</xsl:call-template>-->
                <!--</div>-->
                <!--&lt;!&ndash; gate visible end &ndash;&gt;-->

                <!--&lt;!&ndash; gate open time start &ndash;&gt;-->
                <!--<xsl:variable name="blink_gto_out" select="time/@bl = '1'"/>-->
                <!--<div class="field gto_out blink_{$blink_gto_out}">-->
                    <!--<xsl:call-template name="blink">-->
                        <!--<xsl:with-param name="blink" select="$blink_gto_out"/>-->
                        <!--<xsl:with-param name="content">-->
                            <!--<xsl:call-template name="nbstr"><xsl:with-param name="s" select="time/@gto" /></xsl:call-template>-->
                        <!--</xsl:with-param>-->
                    <!--</xsl:call-template>-->
                <!--</div>-->
                <!--&lt;!&ndash; gate open time end &ndash;&gt;-->

                <!--&lt;!&ndash; status + ground handling prepare start &ndash;&gt;-->
                <!--<xsl:choose>-->
                    <!--<xsl:when test="flt/@ghprep='1' and flt/@stat!='AIR'">-->
                        <!--<div class="field pldcall_out_bg_color">-->
                            <!--<div class="field pldcall_out">-->
                                <!--<xsl:call-template name="nbstr">-->
                                    <!--<xsl:with-param name="s" select="pldcall"/>-->
                                <!--</xsl:call-template>-->
                            <!--</div>-->
                        <!--</div>-->
                    <!--</xsl:when>-->
                    <!--<xsl:otherwise>-->
                        <!--<div class="field pldcall_out">-->
                            <!--<xsl:call-template name="nbstr">-->
                                <!--<xsl:with-param name="s" select="pldcall"/>-->
                            <!--</xsl:call-template>-->
                        <!--</div>-->
                    <!--</xsl:otherwise>-->
                <!--</xsl:choose>-->
                <!--&lt;!&ndash; status + ground handling prepare end &ndash;&gt;-->

				<!--&lt;!&ndash; LOAD/PASS &ndash;&gt;-->
                <!--<div class="field pass_out">-->
               		<!--<xsl:call-template name="nbstr">-->
                		<!--<xsl:with-param name="s" select="load/@pass"/>-->
                	<!--</xsl:call-template>                  -->
                <!--</div>-->

				<!--&lt;!&ndash; LOAD/SPL &ndash;&gt;-->
                <!--<div class="field spl_out">-->
               		<!--<xsl:call-template name="nbstr">-->
                		<!--<xsl:with-param name="s" select="load/@spl"/>-->
                	<!--</xsl:call-template>                  -->
                <!--</div>-->

				<!--&lt;!&ndash; LOAD/WGT &ndash;&gt;-->
                <!--<div class="field wgt_out">-->
               		<!--<xsl:call-template name="nbstr">-->
                		<!--<xsl:with-param name="s" select="load/@wgt"/>-->
                	<!--</xsl:call-template>                  -->
                <!--</div>-->
								<!---->
			<!--</xsl:when>-->
		<!--</xsl:choose>-->
		<!--</div>-->

	<!--</xsl:template>-->


</xsl:stylesheet>