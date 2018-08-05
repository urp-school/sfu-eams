<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
 var bar = new ToolBar('backBar','${student.name}开题基本信息',null,true,true);
 bar.addPrint();
 bar.addBack();
</script>
<table width="100%">
<div align="center"><h3><@i18nName systemConfig.school/><br>申请研究生学位论文开题报告表</h3></div>
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
<tr>
<td colspan="4">
<#include "../topicOpenStd/stdThesisOpen.ftl"/>
</td>
</tr>
</table>
</body>
<#include "/templates/foot.ftl"/>