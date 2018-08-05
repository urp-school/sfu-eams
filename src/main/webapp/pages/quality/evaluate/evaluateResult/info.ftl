<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="listTable" width="99%" align="center" id="resultTable" style="text-align:center">
		<tr>
			<td class="darkColumn">问题类型</td>
			<td class="darkColumn">问题内容</td>
			<td class="darkColumn">评教结果</td>
		</tr>
		<#list questions as question>
			<tr>
				<td>${question.questionType.name}</td>
				<td align="left">${question.question.content}</td>
				<td>${question.score}</td>
			</tr>
		</#list>
	</table>
	<script>
		var bar = new ToolBar("bar", "学生问卷评教详细情况", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addBack("<@msg.message key="action.back"/>");
		
		function mergeTableTd(tableId,index) {
			var rowsArray = document.getElementById(tableId).rows;
			var value = rowsArray[1].cells[index];
			for(var i = 2; i < rowsArray.length; i++) {
				nextTd = rowsArray[i].cells[index];
				if (nextTd.innerHTML == value.innerHTML) {
					rowsArray[i].removeChild(nextTd);
					var rowspanValue = new Number(value.rowSpan);
					rowspanValue++;
					value.rowSpan = rowspanValue;
				} else {
					value = nextTd;
				}
			}
		}
		mergeTableTd("resultTable", '0');
	</script>
</body>
<#include "/templates/foot.ftl"/>