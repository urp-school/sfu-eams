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
   	     <@table.sortTd width="15%" id="register.state.name" text="注册状态"/>
	   </@>
	   <@table.tbody datas=registers;register>
	    <@table.selectTd  type="checkbox" id="registerId" value="${register.id}"/>
	    <td><A href="studentDetailByManager.do?method=detail&stdId=${register.std.id}" target="blank" >${register.std.code}</A></td>
        <td><A href="#" onclick="if(typeof stdIdAction !='undefined') stdIdAction('${register.std.id}');" title="${stdNameTitle?if_exists}">${register.std.name} </a></td>
        <td><@i18nName register.std.basicInfo?if_exists.gender?if_exists/></td>
        <td>${register.std.enrollYear}</td>
        <td><@i18nName register.std.department/></td>
        <td><@i18nName register.std.firstMajor?if_exists/></td>
		<td>${register.registerAt?string("yyyy-MM-dd")}</td> 
		<td>${register.state.name?default('')}</td>
	   </@>
     </@>
 <@htm.actionForm name="actionForm" action="register.do" entity="register">
	<input type="hidden" name="register.calendar.id" value="${RequestParameters['calendar.id']}"/>
	<input type="hidden" name="selectedStateId"/>
	<input type="hidden" name="registed" value="false"/>
</@>
 
 <script>
   var bar = new ToolBar("bar","未注册完成学生列表",null,true,true);
   bar.setMessage('<@getMessage/>');
   var registerMenu= bar.addMenu("注册",null);
     <#list registerStates as state>
   		registerMenu.addItem("${state.name}", "selectState(${state.id})");
     </#list>
   bar.addItem("<@msg.message key="action.export"/>","exportData()");
   bar.addPrint("<@bean.message key="action.print"/>");
   
 	function selectState(selectedStateId){
 		actionForm.selectedStateId.value=selectedStateId;
   		multiAction('updateRegister');
   }
   
   function exportData(){
     if(${totalSize}>10000){alert("数据量超过一万不能导出");return;}
     addInput(form,"keys","std.code,std.name,std.gender.name,std.enrollYear,std.department.name,registerAt,state.name");
     addInput(form,"titles","学号,姓名,性别,入学时间,院系,注册时间,注册状态");
     addInput(form,"fileName","未注册完成学生列表");
     exportList();
   }
 </script>
 </body>   
<#include "/templates/foot.ftl"/> 