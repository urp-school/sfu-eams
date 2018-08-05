<#include "/templates/head.ftl"/>
 <body >  
  <table id="bar"></table>
 <@table.table width="100%" sortable="true" id="sortTable">
	   <@table.thead>
	     <@table.selectAllTd id="registerId"/>
	     <@table.sortTd width="10%" id="std.code" name="attr.stdNo"/>
	     <@table.sortTd width="8%" id="std.name" name="attr.personName"/>
	     <@table.sortTd width="5%" id="std.basicInfo.gender.id" name="entity.gender"/>
	     <@table.sortTd width="8%" id="std.enrollYear" name="attr.enrollTurn"/>
	     <@table.sortTd width="15%"id="std.department.name" name="entity.college"/>
	     <@table.sortTd width="15%" id="std.firstMajor.name" name="entity.speciality"/>
   	     <@table.sortTd width="15%" id="register.registerAt" text="注册时间"/>
	   </@>
	   <@table.tbody datas=registers;register>
	    <@table.selectTd  type="checkbox" id="registerId" value="${register.id}"/>
	    <td><A href="studentDetailByManager.do?method=detail&stdId=${register.std.id}" target="blank">${register.std.code}</A></td>
        <td><A href="#" onclick="if(typeof stdIdAction !='undefined') stdIdAction('${register.id}');" title="${stdNameTitle?if_exists}">${register.std.name} </a></td>
        <td><@i18nName (register.std.basicInfo.gender)?if_exists/></td>
        <td>${register.std.enrollYear}</td>
        <td><@i18nName register.std.department/></td>
        <td><@i18nName register.std.firstMajor?if_exists/></td>
		<td>${register.registerAt?string("yyyy-MM-dd")}</td> 
	   </@>
     </@>
 <@htm.actionForm name="actionForm" action="register.do" entity="register">
 	<input type="hidden" name="registed" value="true"/>
  </@>
 <script>
   var bar = new ToolBar("bar","注册的学生列表",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("查看","stdIdAction()");
   bar.addItem("删除注册","remove()");
   bar.addItem("<@msg.message key="action.export"/>","exportData()");
   
   function exportData(){
     if (${totalSize} > 10000) {
     	alert("数据量超过一万不能导出");
     	return;
     }
     addInput(form,"keys","std.code,std.name,std.gender.name,std.enrollYear,std.department.name,registerAt");
     addInput(form,"titles","学号,姓名,性别,入学时间,院系,注册时间");
     addInput(form,"fileName","已经注册完成学生列表");
     exportList();
   }
   
   function stdIdAction(registerId) {
   	if (null == registerId || "" == registerId) {
   		info();
   	} else {
   		form.action = "register.do?method=info";
   		addInput(form, "registerId", registerId, "hidden");
   		form.submit();
   	}
   }
 </script>
</body>
<#include "/templates/foot.ftl"/> 