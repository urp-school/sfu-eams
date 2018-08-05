  <#assign unitList=calendar.timeSetting.courseUnits>
  <table width="100%" align="center" class="listTable" style="font-size:${fontSize?default(12)}px">
   <#include "/pages/system/calendar/timeSetting/timeSettingBar.ftl"/>
    <tr class="darkColumn">
        <td width="7%"></td>
     	<#list unitList as unit>
		<td align="center" width="7%">${unit_index+1}</td>
		</#list>
    </tr>
	<#list setting.weekInfos as week>
	<tr>
	    <td class="darkColumn"><@i18nName week/></td>
  	    <#list 1..unitList?size as unit>
   		<td id="TD${week_index*unitList?size+unit_index}_${tableIndex}" style="backGround-Color:#ffffff;font-size:${fontSize?default(12)}px"></td>
		</#list>
	</tr>
    </#list>
</table> 
<#include "courseTableContent_script.ftl"/>