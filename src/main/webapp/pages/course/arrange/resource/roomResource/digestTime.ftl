<#include "/templates/head.ftl"/>
<#include "../statListHead.ftl"/>
  <table width="100%" align="center" class="listTable" id="occupyStatTable">
    <tr align="center" class="darkColumn">
      <td width="5%"><@bean.message key="attr.code"/></td>
      <td width="15%"><@bean.message key="attr.name"/></td>
  	  <td width="75%">时间分布</td>
    </tr>   
    <#list resources as room>
	<#if room_index%2==1><#assign class="grayStyle" ></#if>
	<#if room_index%2==0><#assign class="brightStyle" ></#if>
    <tr class="${class}" onmouseover="swapOverTR(this,this.className)"
     onmouseout="swapOutTR(this)">
      <td>&nbsp;${room.code}</td>
      <td>&nbsp;${room.name?if_exists}</td>
      <td>&nbsp;${occupyMap[room.id?string]}</td>
    </tr>
    </#list>  
  </table>  
<#include "../statListFoot.ftl"/>