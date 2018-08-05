<#include "/templates/head.ftl"/>
<#include "../statListHead.ftl"/>
  <table width="100%" align="center" class="listTable" id="occupyStatTable">
    <tr align="center" class="darkColumn">
      <td width="10%"><@bean.message key="teacher.code"/></td>
      <td width="15%"><@bean.message key="attr.personName"/></td>
  	  <td width="80%">时间分布</td>
    </tr>   
    <#list resources as teacher>
	<#if teacher_index%2==1 ><#assign class="grayStyle" ></#if>
	<#if teacher_index%2==0 ><#assign class="brightStyle" ></#if>
    <tr class="${class}" onmouseover="swapOverTR(this,this.className)"
     onmouseout="swapOutTR(this)">
      <td>&nbsp;${teacher.code?if_exists}</td>
      <td>&nbsp;${teacher.name?if_exists}</td>
      <td>&nbsp;${occupyMap[teacher.id?string]}</td>
    </tr>
    </#list>
  </table>
<#include "../statListFoot.ftl"/>