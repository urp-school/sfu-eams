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
<body LEFTMARGIN="0" TOPMARGIN="0" onload="mergeTableTd('taskConfirmBar',1,0);f_frameStyleResize(self);">
	<table id="taskConfirmBar" width="100%"></table>
		<script>
 		 	var bar=new ToolBar("taskConfirmBar","论文开题统计结果",null,true,true);
  		 	bar.setMessage('<@getMessage/>');
  		 	bar.addHelp("<@msg.message key="action.help"/>");
		</script>
	<table width="100%" align="center" class="listTable">
		<thead class="darkColumn"  align="center">
			<td>部门</td>
			<td><@msg.message key="entity.studentType"/></td>
			<td>应开题人数</td>
			<td>实际开题人数</td>
		</thead>
		<tbody>
		     <#assign overallNum=0>
		     <#assign topicOpenNum=0>
			 <#list stdDeparts as statistic>
    			<#if statistic_index%2==1 ><#assign class="grayStyle" ></#if>
	   			<#if statistic_index%2==0 ><#assign class="brightStyle" ></#if>
    			<tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)" align="center">
      				<td>${statistic[1].name}</td>
      				<td>${statistic[2].name}</td>
      				<td>${statistic[0]}<#assign overallNum=overallNum+statistic[0]/></td>
      				<#assign keyValue=statistic[1].id+","+statistic[2].id>
      				<td>${thesisMap[keyValue]?default(0)}(<#if statistic[0]?number!=0>${((thesisMap[keyValue]?default(0)/statistic[0])*100)?string("#0.0#")}<#else>0.0</#if>%)<#assign topicOpenNum=topicOpenNum+thesisMap[keyValue]?default(0)/></td>
    			</tr>
    		</#list>
    			<tr  class="darkColumn"  align="center">
      				<td colspan="2">总计</td>
      				<td>${overallNum}</td>
      				<td>${topicOpenNum}(<#if overallNum?number!=0>${((topicOpenNum/overallNum)*100)?string("#0.0#")}<#else>0.0</#if>%)</td>
    			</tr>
		</tbody>
	</table>
</body>
<#include "/templates/foot.ftl"/>