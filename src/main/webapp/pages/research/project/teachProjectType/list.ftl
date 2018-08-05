<#include "/templates/head.ftl"/>
 <body>
 <table id="teachProjectType"></table>
 <@table.table width="100%" sortable="true" id="sortTable">
   <@table.thead>
          <@table.selectAllTd id="teachProjectTypeId"/>
          <@table.sortTd width="10%" text="项目类别代码" id="teachProjectType.code"/>
          <@table.sortTd width="10%" text="项目类别名称" id="teachProjectType.name"/>
          <@table.sortTd width="10%" text="上级项目类别" id="teachProjectType.superType.name"/>
          <@table.sortTd width="10%" name="attr.engName" id="teachProjectType.engName"/>
          <@table.sortTd width="10%" name="attr.state" id="teachProjectType.state"/>
 	      
   </@>
   <@table.tbody datas=teachProjectTypes;teachProjectType>
      <@table.selectTd id="teachProjectTypeId" value=teachProjectType.id/>
      <td>${(teachProjectType.code)?if_exists}</td>
	  <td>${(teachProjectType.name)?if_exists}</td>
	  <td>${(teachProjectType.superType.name)?if_exists}</td>
	  <td>&nbsp;${teachProjectType.engName?if_exists}</td>
	  <td>
      	<#if teachProjectType.state?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if>
	  </td>	  	  
   </@>
 </@>
  <@htm.actionForm name="actionForm" action="teachProjectType.do" entity="teachProjectType">
  </@>
 <script>
   var bar = new ToolBar('teachProjectType','&nbsp;项目类别列表',null,true,true);
   var form =document.actionForm;
   bar.setMessage('<@getMessage/>');
   bar.addItem("管理项目类别模板","template()");
   bar.addItem("<@bean.message key="action.add"/>","add()");
   bar.addItem("<@bean.message key="action.modify"/>","edit()");
   bar.addItem("<@bean.message key="action.delete"/>","remove()");
   bar.addBack();
   
   function template(){
		var ids = getSelectIds("teachProjectTypeId");
	 	if (ids == null || ids == "") {
	 		alert("你没有选择要操作的记录！");
	 		return;
	 	}
	 	
	    form.action = "teachProjectTemplate.do?method=search";
	    addParamsInput(form, resultQueryStr);
	    submitId(form, 'teachProjectTypeId', false);
   	}
 </script>

 </body>
 <#include "/templates/foot.ftl"/>