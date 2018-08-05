<#include "/templates/head.ftl"/>
<script>
function selfAdapte(){       	
   	    window.self.moveTo((screen.width-400)/3, (screen.height-(8*30+150))/5);
   	    window.self.resizeTo(400, screen.height*(3/4));
}
</script>     
     
 <BODY style="overflow-x:auto;" LEFTMARGIN="0" TOPMARGIN="0">
 	<table class="frameTableStyle">
 		<tr>
 			<td  width="80%"/>
 			<td  width="10%" class="padding" style="height:22px;width:300px;font-size:10pt" onclick="javascript:self.window.close();" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          		<img src="${static_base}/images/close.gif" class="iconStyle" alt="<@bean.message key="action.close"/>" />&nbsp;<@bean.message key="action.close"/>
      		</td>
      		<td  width="10%"/>
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
     <table align="center" class="listTable">
		<form name="listForm" action="" onSubmit="return false;">
			<tr align="center" class="darkColumn">
				<td colspan="2">求和项：出勤率</td>
				<#if ((result.weekEnd-result.weekBegin+1)>0) >
				<td colspan="${result.weekEnd-result.weekBegin+1}">周次</td>
				</#if>
				<td colspan="1">累计</td>
			</tr>

       		<tr align="center" class="darkColumn">
       		<td>代码</td>
       		<td>班级</td>       		
       		<#list result.weekBegin..result.weekEnd as week >
       			<td width="50">
       				第${week}周
       			</td>
       		</#list>
       		<td>累计出勤率</td>       		
       		</tr>
       		<#assign trIndex = 0 />
       		<#list result.adminClassMap?keys?sort?if_exists as adminClassMapKey>
       		<#if trIndex%2==1 ><#assign class="grayStyle" ></#if>
	   		<#if trIndex%2==0 ><#assign class="brightStyle" ></#if>
       			<#if adminClassMapKey=="0">
       			<#else>
       			<tr align="center" class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
       				<td>${result.adminClassMap[adminClassMapKey].code}</td>
       				<td><nobr><@i18nName result.adminClassMap[adminClassMapKey] /></nobr></td>
       				<#assign partClassResult = result.partResult[adminClassMapKey]?default({}) />
       				<#assign totalClassResult = result.totalResult[adminClassMapKey]?default({}) />
       				<#list result.weekBegin..result.weekEnd as week >
       					<#if week==0>
       					<#else>
       						<td>
       							<#if (!partClassResult[week?string]?exists)||partClassResult[week?string].totalCount==0><@bean.message key="attr.null" /><#else>${((partClassResult[week?string].presenceCount)?default(0)/(partClassResult[week?string].totalCount)?default(-1))?string.percent}</#if>
       						</td>
       					</#if>
       				</#list>
       				<td>
       					<#if (!totalClassResult['0']?exists)||totalClassResult['0'].totalCount==0><@bean.message key="attr.null" /><#else>${((totalClassResult['0'].presenceCount)?default(0)/(totalClassResult['0'].totalCount)?default(-1))?string.percent}</#if>       					
       				</td>       				
       			</tr>
       			<#assign trIndex = trIndex +1 />
       			</#if>
       		</#list>
       		<tr align="center" class="darkColumn">
				<td colspan="2">合计</td>
					<#list result.weekBegin..result.weekEnd as week >
       					<#if week==0>
       					<#else>
       						<td>
       							<#if (!result.totalClassReslutMap[week?string]?exists)||result.totalClassReslutMap[week?string].totalCount==0><@bean.message key="attr.null" /><#else>${((result.totalClassReslutMap[week?string].presenceCount)?default(0)/(result.totalClassReslutMap[week?string].totalCount)?default(-1))?string.percent}</#if>
       						</td>
       					</#if>
       				</#list>
       				<td>
       					<#if (!result.totalClassReslutMap['0']?exists)||result.totalClassReslutMap['0'].totalCount==0><@bean.message key="attr.null" /><#else>${((result.totalClassReslutMap['0'].presenceCount)?default(0)/(result.totalClassReslutMap['0'].totalCount)?default(-1))?string.percent}</#if>
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