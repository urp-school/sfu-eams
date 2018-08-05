<#include "/templates/head.ftl"/>
<body>
<table id="bookRequirementBar"></table>
<@table.table width="100%" sortable="true" id="sortTable" >
	<@table.thead>
		<@table.selectAllTd id="requireId"/>
		<@table.sortTd  width="20%" text="课程序号" id="require.task.seqNo"/>
		<@table.sortTd  width="20%" text="课程代码" id="require.task.course.code"/>
		<@table.sortTd  width="20%" name="attr.courseName" id="require.task.course.name"/>
		<@table.sortTd  width="20%" name='entity.teachClass' id="require.task.teachClass.name"/>
		<@table.td  width="10%" name='entity.teacher'/>
		<@table.sortTd  width="20%" name='entity.textbook' id="require.textbook.name"/>
		<@table.sortTd  width="8%" name="attr.count" id="require.countForStd"/>
		<@table.sortTd id="require.textbook.price" text='价格'  width="8%"/>
	</@>
	<@table.tbody datas=requires;bookReq>
	  <@table.selectTd id="requireId" value=bookReq.id/>	
      <td>${bookReq.task.seqNo?if_exists}</td>
      <td>${bookReq.task.course.code?if_exists}</td>
      <td>${bookReq.task.course.name?if_exists}</td>
      <td><#if bookReq.task.requirement.isGuaPai><@msg.message key="attr.GP"/><#else>${bookReq.task.teachClass.name?html}</#if></td>
      <td><@getTeacherNames bookReq.task.arrangeInfo.teachers?if_exists/></td>
      <td>${bookReq.textbook.name?if_exists}</td>
      <td>${bookReq.countForStd?if_exists}<#if bookReq.countForTeacher?exists>(${bookReq.countForTeacher})</#if></td>
      <td >${bookReq.textbook.price?if_exists}</td>
    </@>
</@>

	<@htm.actionForm name="requireForm" action="bookRequirement.do" entity="require">
		<input name="require.task.calendar.id" value="${RequestParameters['require.task.calendar.id']}" type="hidden">
		<input name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}" type="hidden">
		<input name="require.id" value="" type="hidden">
	</@>
	
	<script>
	 var bar=new ToolBar('bookRequirementBar','<@msg.message key="textbook.requireList"/>',null,true,true);
	 bar.setMessage('<@getMessage/>');
	 bar.addItem("<@msg.message key="action.edit"/>","editRequirement()");
	 bar.addItem("<@msg.message key="action.delete"/>","removeRequirement()");
	 bar.addItem("<@msg.message key="action.export"/>","exportData()");
	 var form = document.requireForm;
	 action="bookRequirement.do";
     function exportData(){
       addInput(form,"keys","task.seqNo,task.course.code,task.course.name,task.courseType.name,task.teachClass.name,task.arrangeInfo.teachDepart.name,task.arrangeInfo.teacherNames,countForStd,countForTeacher,remark,textbook.name,textbook.auth,textbook.press.name,textbook.version,textbook.price,textbook.ISBN,textbook.description,textbook.remark,textbook.bookType.name,textbook.publishedOn,textbook.awardLevel.name");
       addInput(form,"titles","<@msg.message key="attr.taskNo"/>,<@msg.message key="attr.courseNo"/>,<@msg.message key="attr.courseName"/>,课程类别,面向班级,<@msg.message key="attr.teachDepart"/>,<@msg.message key="entity.teacher"/>,学生用书量,教师用书量,<@msg.message key="attr.remark"/>,教材名称,<@msg.message key="textbook.author"/>,<@msg.message key="entity.press"/>,<@msg.message key="textbook.version"/>,价格,ISBN,<@msg.message key="attr.description"/>,<@msg.message key="attr.remark"/>,类别,出版年月,<@msg.message key="entity.textbookAwardLevel"/>");
       exportList();
     }
     function requireReport(){
        
     }
	 function editRequirement(){
	    addParamsInput(form,queryStr);
	    form.action=action+"?method=edit";
	    submitId(form,"requireId",false);
	 }
	 function removeRequirement(){
	    addParamsInput(form,queryStr);
	    form.action=action+"?method=remove";
	    submitId(form,"requireId",true,null,"<@msg.message key="common.confirmAction"/>");
	 }
    </script>
</body>
<#include "/templates/foot.ftl"/>
