<#include "/templates/head.ftl"/>
<#include "../statListHead.ftl"/>
  <table width="100%" align="center" class="listTable"  id="occupyStatTable">
    <tr align="center" class="darkColumn">
      <td width="5%"><@bean.message key="attr.code"/></td>
      <td width="20%"><@bean.message key="attr.name"/></td>
  	  <td width="70%">时间分布</td>
    </tr>   
    <#list resources as adminClass>
	<#if adminClass_index%2==1 ><#assign class="grayStyle" ></#if>
	<#if adminClass_index%2==0 ><#assign class="brightStyle" ></#if>
    <tr class="${class}" onmouseover="swapOverTR(this,this.className)"
     onmouseout="swapOutTR(this)">
      <td>&nbsp;${adminClass.code}</td>
      <td>&nbsp;${adminClass.name?if_exists}</td>
      <td>&nbsp;${occupyMap[adminClass.id?string]}</td>
    </tr>
    </#list>
  </table>
  </body>
<#include "../statListFoot.ftl"/>