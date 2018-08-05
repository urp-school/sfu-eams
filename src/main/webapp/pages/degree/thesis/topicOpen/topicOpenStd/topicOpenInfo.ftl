<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<#assign stdName><@i18nName student/></#assign>
<table width="100%">
<#if "openInfo"==topicOpenFlag>
<div align="center"><h2><@i18nName systemConfig.school/><br>申请研究生学位论文开题报告表</h2></div>
<tr>
	<td width="20%" align="right">学号</td>
	<td width="30%" align="left">${student.code}</td>
	<td width="20%" align="right">姓名</td>
	<td width="30%" align="left">${student.name}</td>
</tr>
<tr>
	<td width="20%" align="right">所在院系</td>
	<td width="30%" align="left">${(student.department.name)?if_exists}</td>
	<td width="20%" align="right">专业</td>
	<td width="30%" align="left">${(student.firstMajor.name)?if_exists}</td>
</tr>
</#if>
<tr>
<td colspan="4">
<#if "openInfo"==topicOpenFlag>
<#include "stdThesisOpen.ftl"/>
<#elseif "reportInfo"==topicOpenFlag>
<#include "stdReportInfo.ftl"/>
</#if>
<input type="hidden" name="topicOpenFlag" value="${topicOpenFlag}">
<input type="hidden" name="stdType" value="${stdType}">
</td>
</tr>
</table>
<script>
	var bar = new ToolBar('backBar','<@bean.message key="thesis.topicOpen.std.topicInfo.title" arg0=stdName/>',null,true,true);
	bar.addItem("修改","edit()");

	parent.form["topicOpenId"].value = "${(topicOpen.id)?if_exists}";

	var form = document.topicOpenInfoForm;
	function edit(){
		form.action="loadThesisTopic_std.do?method=editInfo";
		form.submit();
	}
</script>
</body>
<#include "/templates/foot.ftl"/>