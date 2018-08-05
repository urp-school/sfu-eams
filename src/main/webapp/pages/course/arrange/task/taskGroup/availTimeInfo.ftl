  <#assign unitList=calendar.timeSetting.courseUnits?sort_by("index")>
  <table width="100%" align="center" class="listTable" style="font-size:12px">
    <tr class="darkColumn">
        <td width="50px"></td>
     	<#list unitList as unit>
		<td><@i18nName unit/></td>
		</#list>
    </tr>	
	<#list weekList as week>
	<tr>
	    <td  class="darkColumn"><@i18nName week/></td>
	    <#list 0..unitList?size-1 as unit>
        <#assign unitIndex=week_index*unitList?size + unit_index>
   		<td align="center"  valign="middle" <#if availTime.available[unitIndex..unitIndex]=="0">style="backGround-Color:yellow"<#else>style="backGround-Color:#94aef3"</#if>>
   		${availTime.available[unitIndex..unitIndex]}
   		</td>
		</#list>
	</tr>
    </#list>
</table>
<table align="center" width="80%" border="0">
	<tr>
	 <td class="infoTitle"><@bean.message key="info.availTime.legend"/></td>
	 <td style="backGround-Color:#94aef3" width="60px"></td><td class="infoTitle"><@bean.message key="info.avaliTime.available"/></td>
	 <td style="backGround-Color:yellow" width="60px"></td><td class="infoTitle"><@bean.message key="info.avaliTime.unavailable"/></td>
	</tr>
</table>