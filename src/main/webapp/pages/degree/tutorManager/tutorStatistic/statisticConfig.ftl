<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<table width="100%" align="center" class="listTable">
	<form name="conditionForm" method="post" action="" onsubmit="return false;">
	<input type="hidden" name="doStatistic" value="statistic">
	<tr class="darkColumn">
		<td colspan="2" align="center">职称年龄统计配置</td>
	</tr>
	<tr>
		<td class="grayStyle" id="f_startAge"><font color="red">*</font>起始年龄</td>
		<td class="brightStyle"><input type="text" name="startAge" value="30" maxlength="3"/></td>
	</tr>
	<tr>
		<td class="grayStyle" id="f_jumpAge"><font color="red">*</font>年龄跨度</td>
		<td><input type="text" name="jumpAge" value="5" maxlength="3"/></td>
	</tr>
	<tr>
		<td class="grayStyle" id="f_cycleAge"><font color="red">*</font>显示数目</td>
		<td class="brightStyle"><input type="text" name="cycleAge" value="10" maxlength="3"/></td>
	</tr>
	<tr>
		<td colspan="2" align="center" class="darkColumn">
			<button onclick="statistic()">统计</button>
		</td>
	</tr>
	</form>
</table>
<body>
<script>
   	var bar = new ToolBar('backBar','职称年龄配置',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	bar.addHelp("<@msg.message key="action.help"/>");
   	
   	var form = document.conditionForm;
   	function statistic(){
   		var a_fields = {
   			'startAge':{'l':'起始年龄', 'r':true, 't':'f_startAge', 'f':'unsigned'},
   			'jumpAge':{'l':'年龄跨度', 'r':true, 't':'f_jumpAge', 'f':'unsigned'},
   			'cycleAge':{'l':'显示数目', 'r':true, 't':'f_cycleAge', 'f':'unsigned'}
   		};
   		
   		var v = new validator(form, a_fields, null);
   		if (v.exec()) {
   			if (parseInt(form["startAge"].value) > 150) {
   				alert("起始年龄不能超过150！");
   				form["startAge"].focus();
   				return;
   			}
   			if (parseInt(form["jumpAge"].value) > 100) {
   				alert("年龄跨度不能超过100！");
   				form["jumpAge"].focus();
   				return;
   			}
   			if (parseInt(form["cycleAge"].value) > 100) {
   				alert("显示数目不能超过100！");
   				form["cycleAge"].focus();
   				return;
   			}
   			form.action="tutorStatistic.do?method=doStatisticByAge";
	   		form.submit();
   		}
   	}
</script>
<#include "/templates/foot.ftl"/>