<#include "/templates/head.ftl"/>
<body LEFTMARGIN="0" TOPMARGIN="0">
	<table id="taskConfirmBar" width="100%"></table>
	<script>
 		 var bar=new ToolBar("taskConfirmBar","论文进度统计",null,true,true);
  		 bar.setMessage('<@getMessage/>');
  		 bar.addHelp("<@msg.message key="action.help"/>");
	</script>
	<@table.table   width="100%" id="statTable">
		<@table.thead>
			<@table.td name="entity.department"/>
			<@table.td name="entity.speciality"/>
			<@table.td text="论文开题"/>
			<@table.td text="论文写作"/>
			<@table.td text="盲审论文提交"/>
			<@table.td text="博士论文预答辩"/>
			<@table.td text="论文答辩与学位申请"/>
		</@>
		<#assign sum00 = 0/>
		<#assign sum01 = 0/>
		<#assign sum10 = 0/>
		<#assign sum11 = 0/>
		<#assign sum20 = 0/>
		<#assign sum21 = 0/>
		<#assign sum30 = 0/>
		<#assign sum31 = 0/>
		<#assign sum40 = 0/>
		<#assign sum41 = 0/>
		<@table.tbody datas=thesisManages;manage>
			<td><@i18nName manage[0]/></td>
			<td><@i18nName manage[1]/></td>
			<#assign keyValue = manage[0].id + "-" + manage[1].id/>
			<td><#assign value00=(thesisOpenMap[keyValue][0])?default(0)><#assign sum00 = sum00 + value00>${value00}/<#assign value01=(thesisOpenMap[keyValue][1])?default(0)><#assign sum01 = sum01 + value01>${value01}</td>
			<td><#assign value10=(thesisWriteMap[keyValue][0])?default(0)><#assign sum10 = sum10 + value10>${value10}/<#assign value11=(thesisWriteMap[keyValue][1])?default(0)><#assign sum11 = sum11 + value11>${value11}</td>
			<td><#assign value20=(thesisAnnotateMap[keyValue][0])?default(0)><#assign sum20 = sum20 + value20>${value20}/<#assign value21=(thesisAnnotateMap[keyValue][1])?default(0)><#assign sum21 = sum21 + value21>${value21}</td>
			<td><#assign value30=(thesisPreAnswerMap[keyValue][0])?default(0)><#assign sum30 = sum30 + value30>${value30}/<#assign value31=(thesisPreAnswerMap[keyValue][1])?default(0)><#assign sum31 = sum31 + value31>${value31}</td>
			<td><#assign value40=(thesisFormalAnswerMap[keyValue][0])?default(0)><#assign sum40 = sum40 + value40>${value40}/<#assign value41=(thesisFormalAnswerMap[keyValue][1])?default(0)><#assign sum41 = sum41 + value41>${value41}</td>
		</@>
		<tr class="darkColumn" style="text-align:center; font-weight: bold">
			<td colspan="2">合计</td>
			<td>${sum00?default(0)}/${sum01?default(0)}</td>
			<td>${sum10?default(0)}/${sum11?default(0)}</td>
			<td>${sum20?default(0)}/${sum21?default(0)}</td>
			<td>${sum30?default(0)}/${sum31?default(0)}</td>
			<td>${sum40?default(0)}/${sum41?default(0)}</td>
		</tr>
	</@>
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
	//mergeTableTd(statTable,1,0);
</script>
</body>
<#include "/templates/foot.ftl"/>