<#include "/templates/head.ftl"/>
 <body>
 <table id="teachProject"></table>
 <@table.table width="100%" sortable="true" id="sortTable">
   <@table.thead>
          <@table.selectAllTd id="teachProjectId"/>
          <@table.sortTd width="10%" name="research.harvest.projectNO" id="teachProject.code"/>
          <@table.sortTd width="10%" name="research.harvest.projectName" id="teachProject.name"/>
          <@table.sortTd width="10%" text="项目类别" id="teachProject.teachProjectType.name"/>
          <@table.sortTd width="10%" name="research.harvest.principal" id="teachProject.principal"/>
          <@table.sortTd width="10%" name="attr.graduate.auditStatus" id="teachProject.teachProjectState.name"/>
 	      
   </@>
   <@table.tbody datas=teachProjects;teachProject>
      <@table.selectTd id="teachProjectId" value=teachProject.id/>
      <td>${(teachProject.code)?if_exists}</td>
	  <td>${(teachProject.name)?if_exists}</td>
	  <td>${(teachProject.teachProjectType.name)?if_exists}</td>
	  <td>${(teachProject.principal)?if_exists}</td>
	   <td>${(teachProject.teachProjectState.name)?if_exists}</td>
   </@>
 </@>
  <@htm.actionForm name="actionForm" action="teachProjectApply.do" entity="teachProject">
  </@>
 <script>
   var bar = new ToolBar('teachProject','&nbsp;项目信息列表',null,true,true);
   var form =document.actionForm;
   bar.setMessage('<@getMessage/>');
   bar.addItem("下载模板","downloadTemplate()");
   bar.addItem("项目文档","projectDocument()");
   bar.addItem("项目成员","projectMember()");
   bar.addItem("<@bean.message key="action.info"/>","info()");
   bar.addItem("<@bean.message key="action.add"/>","add()");
   bar.addItem("<@bean.message key="action.modify"/>","edit()");
   bar.addItem("<@bean.message key="action.delete"/>","remove()");
   bar.addBack();
   
   function downloadTemplate(){
	    form.action = "teachProjectApply.do?method=templateList";
	    form.submit();
   	}
   
   function projectDocument(){
		var ids = getSelectIds("teachProjectId");
	 	if (ids == null || ids == "") {
	 		alert("你没有选择要操作的记录！");
	 		return;
	 	}
	 	
	    form.action = "projectDocument.do?method=search";
	    addParamsInput(form, resultQueryStr);
	    submitId(form, 'teachProjectId', false);
   	}
   	
   	function projectMember(){
		var ids = getSelectIds("teachProjectId");
	 	if (ids == null || ids == "") {
	 		alert("你没有选择要操作的记录！");
	 		return;
	 	}
	 	
	    form.action = "projectMember.do?method=search";
	    addParamsInput(form, resultQueryStr);
	    submitId(form, 'teachProjectId', false);
   	}
 </script>

 </body>
 <#include "/templates/foot.ftl"/>