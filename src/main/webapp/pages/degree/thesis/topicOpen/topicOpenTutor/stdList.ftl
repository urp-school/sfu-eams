<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="backBar"></table>
	<#if isOpen?exists && isOpen == true>
	<@table.table sortable="true" id="tOpenId">
		<@table.thead>
			<@table.selectAllTd id="topicOpenId"/>
			<@table.sortTd name="attr.stdNo" width="8%" id="student.code"/>
			<@table.sortTd name="attr.personName" width="5%" id="student.name"/>
			<@table.td name="entity.studentType" width="10%"/>
			<@table.td name="entity.speciality" width="8%"/>
			<@table.sortTd text="开题时间" width="8%" id="topicOpen.openReport.openOn"/>
			<@table.sortTd text="开题地点" width="10%" id="topicOpen.openReport.address"/>
			<@table.sortTd text="提交时间" width="6%" id="topicOpen.finishOn"/>
			<@table.sortTd text="是否通过"  width="5%"  id="topicOpen.isPassed"/>
		</@>
        <@table.tbody datas=studentPage;thesisManage>
			<@table.selectTd id="topicOpenId" value=thesisManage.topicOpen.id/>
			<td><input type="hidden" id="stdId${(thesisManage.topicOpen.id)?if_exists}" name="stdId${(thesisManage.topicOpen.id)?if_exists}" value="${(thesisManage.student.id)?if_exists}">${(thesisManage.student.code)?if_exists}</td>
			<td><#if (thesisManage.topicOpen.downloadName)?exists><a href="thesisDownload.do?method=doDownLoad&thesisManageId=${thesisManage.id}&storeId=01">${thesisManage.student.name?if_exists}</a><#else>${(thesisManage.student.name)?if_exists}</#if></td>
			<td>${(thesisManage.student.type.name)?if_exists}</td>
			<td>${(thesisManage.student.firstMajor.name)?if_exists}</td>
			<td><#if (thesisManage.topicOpen.openReport.openOn)?exists>${(thesisManage.topicOpen.openReport.openOn)?string("yyyy-MM-dd")}<#else>未设置</#if></td>
			<td><#if (thesisManage.topicOpen.openReport.address)?exists>${thesisManage.topicOpen.openReport.address?html}<#else>未设置</#if></td>
			<td><#if (thesisManage.topicOpen.finishOn)?exists>${thesisManage.topicOpen.finishOn?string("yyyy-MM-dd")}<#else>未完成</#if></td>
			<td><#assign isPassed = (thesisManage.topicOpen.isPassed?string)?default("0")><#if isPassed == "1">通过<#elseif isPassed == "2">修改<#elseif isPassed == "3">不通过<#else>未设置</#if></td>
		</@>
	</@>
	
	<#else>
	<@table.table sortable="true" id="tOpenId">
		<@table.thead>
			<@table.selectAllTd id="topicOpenId"/>
			<@table.td name="attr.stdNo" width="8%" />
			<@table.td name="attr.personName" width="5%"/>
			<@table.td name="entity.studentType" width="10%"/>
			<@table.td name="entity.speciality" width="12%"/>
			<@table.td name="common.adminClass" width="14%"/>
			<@table.td text="所在年级" width="8%"/>
		</@>
		<@table.tbody datas=studentPage;student>
			<@table.selectTd id="topicOpenId" value=student.id/>
	      	<td>${student.code?if_exists}</td>
			<td>${student.name?if_exists}</td>
	      	<td>${(student.type.name)?if_exists}</td>
	      	<td>${(student.firstMajor.name)?if_exists}</td>
	      	<td>
		      	<#list student.adminClasses?if_exists as adminClass>
		      		${(adminClass.name)?if_exists}&nbsp;
		      	</#list>
	      	</td>
	      	<td>${student.enrollYear?if_exists}</td>
		</@>
	</@>
   	</#if>
	<form name="actionForm" method="post">
	  	<input type="hidden" name="keys" value="code,name,type.name,department.name,firstMajor.name">
	  	<input type="hidden" name="titles" value="学号,姓名,学生类别,院系,专业">
	  	<@searchParams/>
	</form>
	<script>
   	var bar = new ToolBar('backBar', '<#if isOpen?exists && isOpen == true>开题学生列表<#else>学生列表</#if>', null, true, true);
   	bar.setMessage('<@getMessage/>');
   	<#if isOpen?exists && isOpen == true>
   		bar.addItem("查看论文开题报告", "stdInfo(document.actionForm)");
   	</#if>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("topicOpenId")));
    }
    function stdInfo(form){
   		var topicId = getIds();
   		if(""==topicId||topicId.indexOf(",")>-1){
   			alert("你选择提交");
   			return;
   		}
   		var stdId = document.getElementById("stdId"+topicId).value;
    	form.action='thesisTopicOpen_tutor.do?method=doLoadThesisTopic&stdId='+stdId;
    	form.submit();
    } 
   </script>
  </body>
<#include "/templates/foot.ftl"/>
