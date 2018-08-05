  <table align="center" class="listTable" width="100%">
    <tr class="darkColumn">
        <td width="50px"></td>
     	<#list unitList?sort_by("id") as unit>
		<td>
		 <@i18nName unit/>
		</td>
		</#list>
    </tr>	
	<#list weekList as week>
	<tr>
	    <td class="darkColumn"><@i18nName week/></td>
  	    <#list 0..unitList?size-1 as unit>
  	    	<#assign unitIndex=week_index*unitList?size + unit_index/>
   		<td <#if availTime.available[unitIndex..unitIndex]=="1">  style="backGround-Color:#94aef3" <#else> style="backGround-Color:yellow"</#if>></td>
		</#list>
	</tr>
    </#list>
</table>
<table align="center" width="100%" border="0">
	<tr class="infoTitle">
	 <td width="90px"><@bean.message key="info.availTime.legend"/></td>
	 <td style="backGround-Color:#94aef3" width="60px"><@bean.message key="info.avaliTime.available"/></td>
	 <td style="backGround-Color:yellow" width="60px"><@bean.message key="info.avaliTime.unavailable"/></td>
	 <td></td>
	</tr>
</table>
<table align="center" width="100%" border="0">
	<tr class="infoTitle">
	 <td width="90px">说明:</td>
	 <td>${availTime.remark?default("")}</td>
	</tr>
</table>