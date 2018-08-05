<#include "/templates/head.ftl"/>
<body>
<table id="bookRequirementBar"></table>
<@table.table width="100%" id="listTable" sortable="true">
	<@table.thead>
		<@table.selectAllTd id="requireId"/>
		<@table.sortTd id="require.task.seqNo" name='attr.taskNo'  width="8%"/>
		<@table.sortTd id="require.task.course.name"  name="attr.courseName" width="15%"/>
		<@table.sortTd name='entity.teachClass' id="require.task.teachClass.name" width="15%"/>
        <@table.td width="8%" name='entity.teacher'/>
		<@table.sortTd id="require.textbook.name"  name='entity.textbook' width="20%"/>
		<@table.sortTd id="require.textbook.press.name"  name='entity.press' width="20%"/>
		<@table.sortTd id="require.countForStd" name='attr.count' width="5%"/>
		<@table.sortTd id="require.checked" name='attr.graduate.auditStatus' width="10%"/>
	</@>
	<@table.tbody datas=requires;bookReq>
	  <@table.selectTd type="checkbox" id="requireId" value="${bookReq.id}"/>	
      <td>${bookReq.task.seqNo?if_exists}</td>
      <td><A href="teachTaskSearch.do?method=info&task.id=${bookReq.task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName bookReq.task.course/></A></td>
      <td><#if bookReq.task.requirement.isGuaPai><@msg.message key="attr.GP"/><#else>${bookReq.task.teachClass.name}</#if>
      <td><@getTeacherNames bookReq.task.arrangeInfo.teachers?if_exists/></td>
      <td <#if bookReq.checked?exists><#if !bookReq.checked>style="color:red" title="该教材审核未通过"</#if></#if>>${bookReq.textbook.name?if_exists}</td>
      <td><@i18nName bookReq.textbook.press?if_exists/></td>
      <td>${bookReq.countForStd?if_exists}<#if (bookReq.countForTeacher?default(0)!=0)>(${bookReq.countForTeacher})</#if></td>
      <td><#if (bookReq.checked)?exists><#if bookReq.checked?string("true", "false") == "true">通过<#else><font color="red">未通过</font></#if></#if></td>
    </@>
</@>
	<@htm.actionForm name="requireForm" action="bookRequireAudit.do" entity="require">
	<input name="require.task.calendar.id" value="${RequestParameters['require.task.calendar.id']}" type="hidden"/>
	<input name="require.task.calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}" type="hidden"/>
    <input name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}" type="hidden"/>
	<input name="require.id" value="" type="hidden"/>
	</@>
	<script>
	 var form = document.requireForm;
	 action="bookRequireAudit.do";
     function exportData(){
       addInput(form,"keys","task.seqNo,task.course.code,task.course.name,task.courseType.name,task.teachClass.name,task.arrangeInfo.teachDepart.name,task.arrangeInfo.teacherNames,countForStd,countForTeacher,remark,textbook.name,textbook.auth,textbook.press.name,textbook.version,textbook.price,textbook.ISBN,textbook.description,textbook.remark,textbook.bookType.name,textbook.publishedOn,textbook.awardLevel.name");
       addInput(form,"titles","<@msg.message key="attr.taskNo"/>,<@msg.message key="attr.courseNo"/>,<@msg.message key="attr.courseName"/>,课程类别,面向班级,<@msg.message key="attr.teachDepart"/>,<@msg.message key="entity.teacher"/>,学生用书量,教师用书量,<@msg.message key="attr.remark"/>,教材名称,<@msg.message key="textbook.author"/>,<@msg.message key="entity.press"/>,<@msg.message key="textbook.version"/>,价格,ISBN,<@msg.message key="attr.description"/>,<@msg.message key="attr.remark"/>,类别,出版年月,<@msg.message key="entity.textbookAwardLevel"/>");
       exportList();
     }
     
	 var bar=new ToolBar('bookRequirementBar','已登记的教材需求',null,true,true);
	 bar.setMessage('<@getMessage/>');
	 bar.addItem("审核通过","audit(true)");
	 bar.addItem("审核为未通过","audit(false)");
	 bar.addItem("<@msg.message key="action.export"/>","exportData()");
	 function audit(pass){
	    addParamsInput(form,getInputParams(parent.document.requireSearchForm));
	    form.action=action+"?method=setPass&pass="+(pass?"1":"0");
	    submitId(form,"requireId",true,null,"确认审核状态?");
      }
    </script>
</body>
<#include "/templates/foot.ftl"/>
