<#include "/templates/head.ftl"/>
<script language="JavaScript" src="scripts/Menu.js"></script>
<script>
    function getIds(name){
       return(getCheckBoxValue(document.getElementsByName(name)));
    }
    function pageGo(pageNo){
       document.pageGoForm.pageNo.value = pageNo;
       document.pageGoForm.submit();
    }     
</script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="stdListBar" width="100%"></table>
  	<#if hasApplyFlag?exists && "true"==hasApplyFlag>
  	  <@table.table width="100%" id="listTable" sortable="true">
  		<@table.thead>
  			<@table.selectAllTd id="preAnswerId"/>
  			<@table.sortTd name="attr.stdNo"  width="12%" id="student.code"/>
  			<@table.sortTd name="attr.personName"  width="8%" id="student.name"/>
  			<@table.td name="entity.studentType" width="12%"/>
  			<@table.td name="entity.speciality" width="12%"/>
  			<@table.sortTd text="答辩时间" width="10%" id="preAnswer.answerTime"/>
  			<@table.sortTd text="答辩地点" id="preAnswer.answerAddress"/>
  			<@table.sortTd text="提交时间" width="10%" id="preAnswer.finishOn"/>
  			<@table.sortTd text="是否通过" width="8%" id="preAnswer.isPassed"/>
  		</@>
		<@table.tbody datas=preAnswerPage;perAnswer>
		  	<#assign thesisManage=perAnswer.thesisManage>
  			<@table.selectTd id="preAnswerId" value=perAnswer.id/>
      		<td>${thesisManage.student.code?if_exists}</td>
  			<td>${thesisManage.student.name?if_exists}</td>
      		<td>${(thesisManage.student.type.name)?if_exists}</td> 	 
      		<td>${thesisManage.student.firstMajor?if_exists.name?if_exists}</td>
  			<td><#if perAnswer.answerTime?exists>${perAnswer.answerTime?string("yyyy-MM-dd")}<#else>--</#if></td>
  			<td><#if (perAnswer.answerAddress)?exists>${(perAnswer.answerAddress?html)?if_exists}<#else>--</#if></td>
  			<td><#if (perAnswer.finishOn)?exists>${(perAnswer.finishOn)?string("yyyy-MM-dd")}<#else>未完成</#if></td>
  			<td>${((perAnswer.isPassed)?string("通过","未通过"))?default("--")}</td>
  		</@>
  	 </@>
  	 
  	 <#else>
  	 <@table.table width="100%">
  		<@table.thead>
  			<@table.selectAllTd id="preAnswerId"/>
  			<@table.td name="attr.stdNo"  width="12%"/>
  			<@table.td name="attr.personName" width="8%"/>
  			<@table.td name="entity.studentType" width="12%"/>
  			<@table.td name="entity.speciality" width="12%"/>
  			<@table.td name="common.adminClass"/>
  		</@>
  		<@table.tbody datas=preAnswerPage;perAnswer>
		  	<#assign thesisManage=perAnswer>
  			<@table.selectTd id="preAnswerId" value=perAnswer.id/>
      		<td>${thesisManage.student.code?if_exists}</td>
  			<td>${thesisManage.student.name?if_exists}</td>
      		<td>${(thesisManage.student.type.name)?if_exists}</td> 	 
      		<td>${thesisManage.student.firstMajor?if_exists.name?if_exists}</td>
      		<td><#list thesisManage.student.adminClasses as adminClass>${(adminClass.name?html)?if_exists}</#list></td>
  		</@>
  	 </@>
  	</#if>
<script>
   	var bar = new ToolBar('stdListBar','<#if hasApplyFlag?exists&&"true"==hasApplyFlag>申请预答辩学生列表<#else>学生列表</#if>',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	bar.addHelp("<@msg.message key="action.help"/>");
</script>
</body>
<#include "/templates/foot.ftl"/>
