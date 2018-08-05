<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="backBar" width="100%"></table>
	<@table.table width="100%" align="center" sortable="true" id="listTable">
		<@table.thead>
			<@table.selectAllTd id="thesisManageId"/>
			<@table.sortTd name="attr.stdNo" id="thesisManage.student.code"/>
			<@table.sortTd name="attr.personName" id="thesisManage.student.name"/>
			<@table.sortTd name="entity.studentType" id="thesisManage.student.type.name"/>
			<@table.sortTd name="entity.college" id="thesisManage.student.department.name"/>
			<@table.sortTd name="entity.speciality" id="thesisManage.student.firstMajor.name"/>
			<@table.sortTd text="导师姓名" id="thesisManage.student.teacher.name"/>
			<@table.td text="当前计划环节"/>
			<@table.td text="正在执行环节"/>
		</@>
		<@table.tbody datas=thesisManages;thesisManage>
			<@table.selectTd id="thesisManageId" value=thesisManage.id/>
			<td>${(thesisManage.student.code)?if_exists}</td>
			<td><A href="#" onclick="lookProcessOfStd(${thesisManage.id})"><@i18nName (thesisManage.student)?if_exists/></A></td>
			<td><@i18nName (thesisManage.student.type)?if_exists/></td>
			<td><@i18nName (thesisManage.student.department)?if_exists/></td>
			<td><@i18nName (thesisManage.student.firstMajor)?if_exists/></td>
			<td><@i18nName (thesisManage.student.teacher)?if_exists/></td>
			<td><#if (thesisManage.getTacheSetting().tache.name)?exists>${(thesisManage.getTacheSetting().tache.name)}<#else>无计划进度</#if></td>
			<td><#if (thesisManage.getProcess())?exists><#if thesisManage.getProcess()=="topicOpen">论文开题<#elseif thesisManage.getProcess()=="preAnswer">预答辩<#elseif thesisManage.getProcess()=="annotate">论文评阅<#elseif thesisManage.getProcess()=="formalAnswer">论文答辩<#else>完成</#if></#if></td>
		</@>
	</@>
	<form name="conditionForm" method="post" target="_parent" action="" onsubmit="return false;"></form>
	<script>
		var bar = new ToolBar('backBar','论文学生进度列表',null,true,true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("查看进度","lookProcessOfStd()", "detail.gif");
		
		var form = document.conditionForm;
		orderBy =function(what){
			parent.search(1,${pageSize},what);
		}
		function pageGoWithSize(pageNo,pageSize){
		    parent.search(pageNo,pageSize,'${RequestParameters['orderBy']?default('null')}');
		}
		function lookProcessOfStd(thesisManageId){
			form.action="thesisManage.do?method=loadProcessPage";
			if (null == thesisManageId || "" == thesisManageId) {
				submitId(form,"thesisManageId",false);
			} else {
			    addInput(form, "thesisManageId", thesisManageId, "hidden");
			    form.submit();
			}
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>