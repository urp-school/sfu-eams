<#include "/templates/head.ftl"/>
<script>
function selfAdapte(){       	
   	    window.self.moveTo((screen.width-400)/3, (screen.height-(8*30+150))/5);
   	    window.self.resizeTo(400, screen.height*(3/4));
}
</script>     
<#assign cancelClassStyle>style="background-color:#CD853F"</#assign>
<#assign reStudyStyle>style="background-color:#B0C4DE"</#assign>
<#assign reExamStyle>style="background-color:#6495ED"</#assign>
 <BODY style="overflow-x:auto;" LEFTMARGIN="0" TOPMARGIN="0">
 	<table class="frameTableStyle">
 		<tr>
 			<td align="center" class="infoTitle" style="height:22px;width:200px" class="padding"  ${cancelClassStyle} >退课</td>
 			<td align="center" class="infoTitle" style="height:22px;width:200px" class="padding"  ${reStudyStyle} >重修</td>
 			<td align="center" class="infoTitle" style="height:22px;width:200px" class="padding"  ${reExamStyle} >免修不免试</td>
 			<td width="50%"/>
 			<td width="10%" class="padding" style="height:22px;width:300px;font-size:10pt" onclick="javascript:self.window.close();" onmouseover="MouseOver(event)" onmouseout="MouseOut()">
          		<img src="${static_base}/images/close.gif" class="iconStyle" alt="<@bean.message key="action.close"/>" />&nbsp;<@bean.message key="action.close"/>
      		</td>
      		<td width="10%"/>
      	</tr>
    </table>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B>统计页面</B>
    </td>
   </tr>   
   <tr>
    <td>
     <table  align="center" class="listTable">
		<form name="listForm" action="" onsubmit="return false;">
       		<#assign totalStd=totalStd />
       		<#assign totalCourse=totalCourse />
       		<#assign courseMapKeySet=result.courseMap?keys?sort />
			<tr align="center" class="darkColumn">
				<td colspan="2">求和项：出勤率</td>
				<#if (courseMapKeySet?size > 0)>
				<td colspan="${courseMapKeySet?size - 1}"><@msg.message key="attr.courseName"/></td>
				</#if>
				<td colspan="2">总计</td>
			</tr>
       		<tr align="center" class="darkColumn">
	       		<td><@msg.message key="attr.stdNo"/></td>
	       		<td><@msg.message key="attr.personName"/></td>
	       		<#list courseMapKeySet?if_exists as courseMapKey>
	       			<#if courseMapKey==totalCourse>
	       			<#else>
	       			<td>
	       				<@i18nName result.courseMap[courseMapKey]/>
	       			</td>
	       			</#if>
	       		</#list>
	       		<td>总出勤率</td>
       			<td>总缺勤率</td>
       		</tr>
       		<#assign stdMapIndex = 0/>
       		<#list result.stdMap?keys?if_exists as stdMapKey>
	       		<#if stdMapIndex%2==1><#assign class="grayStyle"/></#if>
		   		<#if stdMapIndex%2==0><#assign class="brightStyle"/></#if>
       			<#if stdMapKey!=totalStd>
       			<tr align="center" class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
       				<td>${result.stdMap[stdMapKey].code}</td>
       				<td><NOBR><@i18nName result.stdMap[stdMapKey]/></NOBR></td>       				
       				<#assign partStdResult = result.partResult[stdMapKey] />
       				<#assign totalStdResult = result.totalResult[stdMapKey] />
       				<#assign studentCourseTakeTypeMap = partStdResult[courseTakeType] />
       				<#list courseMapKeySet?if_exists as courseMapKey >
       					<#if courseMapKey==totalCourse>
       					<#else>
       						<#if (!studentCourseTakeTypeMap[courseMapKey]?exists)>
       						<#if (partStdResult[courseMapKey]?exists)&&(totalStdResult[courseMapKey]?exists) >
       						<td ${cancelClassStyle}>
   								<#if (!partStdResult[courseMapKey]?exists)||partStdResult[courseMapKey].totalCount==0><@bean.message key="attr.null"/><#else>${((partStdResult[courseMapKey].getPresenceRatio(true))?default(0))?string.percent}</#if>/<#if (!totalStdResult[courseMapKey]?exists)||totalStdResult[courseMapKey].totalCount==0><@bean.message key="attr.null" /><#else>${((totalStdResult[courseMapKey].getPresenceRatio(true))?default(0))?string.percent}</#if>       							
       						</td>
       						<#else>
       						<td>
   								<#if (!partStdResult[courseMapKey]?exists)||partStdResult[courseMapKey].totalCount==0><@bean.message key="attr.null"/><#else>${((partStdResult[courseMapKey].getPresenceRatio(true))?default(0))?string.percent}</#if>/<#if (!totalStdResult[courseMapKey]?exists)||totalStdResult[courseMapKey].totalCount==0><@bean.message key="attr.null" /><#else>${((totalStdResult[courseMapKey].getPresenceRatio(true))?default(0))?string.percent}</#if>
       						</td>
       						</#if>
       						<#else>
       						<td <#if studentCourseTakeTypeMap[courseMapKey].id==reStudy>${reStudyStyle}<#elseif studentCourseTakeTypeMap[courseMapKey].id==reExam>${reExamStyle}<#else></#if> >
       								<#if (!partStdResult[courseMapKey]?exists)||partStdResult[courseMapKey].totalCount==0><@bean.message key="attr.null" /><#else>${((partStdResult[courseMapKey].getPresenceRatio(true))?default(0))?string.percent}</#if>/<#if (!totalStdResult[courseMapKey]?exists)||totalStdResult[courseMapKey].totalCount==0><@bean.message key="attr.null" /><#else>${((totalStdResult[courseMapKey].getPresenceRatio(true))?default(0))?string.percent}</#if>
       							</#if>
       						</td>
       						</#if>
       				</#list>
       				<td>
       					<#if (!partStdResult[totalCourse]?exists)||partStdResult[totalCourse].totalCount==0><@bean.message key="attr.null" /><#else>${((partStdResult[totalCourse].getPresenceRatio(null))?default(0))?string.percent}</#if>/<#if (!totalStdResult[totalCourse]?exists)||totalStdResult[totalCourse].totalCount==0><@bean.message key="attr.null" /><#else>${((totalStdResult[totalCourse].getPresenceRatio(null))?default(0))?string.percent}</#if>
       				</td>
       				<td>
       					<#if (!partStdResult[totalCourse]?exists)||partStdResult[totalCourse].totalCount==0 ><@bean.message key="attr.null" /><#else>${((partStdResult[totalCourse].getAbsenteeismRatio(null))?default(0))?string.percent}</#if>/<#if (!totalStdResult[totalCourse]?exists)||totalStdResult[totalCourse].totalCount==0><@bean.message key="attr.null" /><#else>${((totalStdResult[totalCourse].getAbsenteeismRatio(null))?default(0))?string.percent}</#if>
       				</td>
       			</tr>
       			<#assign stdMapIndex = stdMapIndex +1 />
       			</#if>
       		</#list>
       		<#assign partStdResult = "" />
       		<#assign totalStdResult = "" />
       		<#assign partStdResult = result.partResult[totalStd] />
       		<#assign totalStdResult = result.totalResult[totalStd] />
       		<tr align="center" class="darkColumn">
				<td colspan="2">合计</td>
				<#list courseMapKeySet?if_exists as courseMapKey >
       					<#if courseMapKey==totalCourse>
       					<#else>
       						<td>
       							<#if (!partStdResult[courseMapKey]?exists)||partStdResult[courseMapKey].totalCount==0><@bean.message key="attr.null"/><#else>${((partStdResult[courseMapKey].getPresenceRatio(null))?default(0))?string.percent}</#if>/<#if (!totalStdResult[courseMapKey]?exists)||totalStdResult[courseMapKey].totalCount==0><@bean.message key="attr.null" /><#else>${((totalStdResult[courseMapKey].getPresenceRatio(null))?default(0))?string.percent}</#if>
       						</td>
       					</#if>
       				</#list>
       				<td>
       					<#if (!partStdResult[totalCourse]?exists)||partStdResult[totalCourse].totalCount==0><@bean.message key="attr.null" /><#else>${((partStdResult[totalCourse].getPresenceRatio(null))?default(0))?string.percent}</#if>/<#if (!totalStdResult[totalCourse]?exists)||totalStdResult[totalCourse].totalCount==0><@bean.message key="attr.null" /><#else>${((totalStdResult[totalCourse].getPresenceRatio(null))?default(0))?string.percent}</#if>
       				</td>
       				<td>
       					<#if (!partStdResult[totalCourse]?exists)||(partStdResult[totalCourse].totalCount==0)><@bean.message key="attr.null" /><#else>${((partStdResult[totalCourse].getAbsenteeismRatio(null))?default(0))?string.percent}</#if>/<#if (!totalStdResult[totalCourse]?exists)||totalStdResult[totalCourse].totalCount==0><@bean.message key="attr.null" /><#else>${((totalStdResult[totalCourse].getAbsenteeismRatio(null))?default(0))?string.percent}</#if>
       				</td>
			</tr>       		
	   </form>	   
     </table>
    </td>
   </tr>
  </table>  
    </td>
   </tr>
  </table>
  </form>  
 </body>
 
<#include "/templates/foot.ftl"/>