<#include "/templates/head.ftl"/>
<body >
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','进度详细信息',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack('<@bean.message key="action.back"/>');
</script>
<@table.table width="100%" align="center">
	<tr class="grayStyle">
		<td>学生类别:${schedule.studentType.name}</td>
		<td><@msg.message key="attr.enrollTurn"/>:${schedule.enrollYear}</td>
		<td>学制:${schedule.studyLength}年</td>
		<td></td>
	</tr>
	<@table.thead>
		<td>步骤名称</td>
		<td>规定完成时间节点</td>
		<td>文件与规章</td>
		<td>学生下载表格</td>
	</@>
	<@table.tbody datas=schedule.tacheSettings?sort_by(["tache","code"]);tacheSetting>
		<td>${tacheSetting.tache.name}</td>
		<td><#if (tacheSetting.planedTimeOn)?exists>${(tacheSetting.planedTimeOn)?string("yyyy-MM-dd")}</#if></td>
		<td>
			<#list tacheSetting.thesisDocuments?if_exists as document>
				<a href="thesisDocument.do?method=doDownLoad&document.id=${document.id}">${document.name}</a><br>
			</#list>
		</td>
		<td>
			<#list tacheSetting.thesisModels?if_exists as document>
				<a href="thesisDocument.do?method=doDownLoad&document.id=${document.id}">${document.name}</a><br>
			</#list>
		</td>
	</@>
</@>
<#include "/templates/foot.ftl"/>