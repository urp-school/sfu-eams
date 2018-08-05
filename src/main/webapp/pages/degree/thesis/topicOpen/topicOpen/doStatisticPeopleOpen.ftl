<#include "/templates/head.ftl"/>
<script>
function mergeTableTd(tableId,fromIndex,index){
		var tableObject = document.getElementById(tableId);
		var rowsArray =tableObject.rows;
		var value=rowsArray[1].cells[index];
		for(var i=fromIndex;i<rowsArray.length-1;i++){
			nextTd=rowsArray[i].cells[index];
			if(nextTd.innerHTML==value.innerHTML){
				rowsArray[i].removeChild(nextTd);
				var rowspanValue= new Number(value.rowSpan);
				rowspanValue++;
				value.rowSpan=rowspanValue;
			}else{
				value=nextTd;
			}
		}
	}
</script>
<body onload="mergeTableTd('tableId',2,0);">
<#assign labInfo>学生开题报告<@msg.message key="entity.department"/>学生类别统计表</#assign>
 <#include "/templates/back.ftl">
  <table id="tableId" cellpadding="0" cellspacing="0" width="100%" border="0"  class="listTable" align="center">
    <tr class="darkColumn">
      <td width="15%"><@msg.message key="entity.department"/></td>
      <td width="13%"><@msg.message key="entity.studentType"/></td>
      <td width="20%">总人数</td>
      <td width="24%">已开题人数</td>
      <td width="28%">开题比例%</td>
    </tr>
    <#list statisticList as statistic>
    	<#if statistic_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if statistic_index%2==0 ><#assign class="brightStyle" ></#if>
    <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
      <td>${statistic.department.name?if_exists}</td>
      <td>${statistic.stdType.name?if_exists}</td>
      <td>${statistic.personNo?if_exists}</td>
      <td>${statistic.hasFinishNo?if_exists}</td>
      <td>${statistic.hasFinishRate?if_exists?string("##0.0")}</td>
    </tr>
    </#list>
    <tr class="darkColumn">
    	<td colspan="5" height="25px;"></td>
    </tr>
  </table>
 </body>
<#include "/templates/foot.ftl"/>