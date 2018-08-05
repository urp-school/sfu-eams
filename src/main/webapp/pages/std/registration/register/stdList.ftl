<#include "/templates/head.ftl"/>

 <body >  
 <table id="bar"></table>
 <#include "/pages/components/stdList1stTable.ftl"/>
 
 <@htm.actionForm name="actionForm" action="register.do" entity="std">
   <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}"/>
   <input type="hidden" name="selectedStateId"/>
   <input type="hidden" name="type" value="stdList"/>
 </@>
 <script>
   var bar = new ToolBar("bar","没有注册学生列表",null,true,true);
   bar.setMessage('<@getMessage/>');
   var registerMenu= bar.addMenu("注册",null);
     <#list registerStates as state>
   		registerMenu.addItem("${state.name}", "selectState(${state.id})");
     </#list>
   bar.addItem("<@msg.message key="action.export"/>","exportData()");
   bar.addPrint("<@bean.message key="action.print"/>");
   
 	function selectState(selectedStateId){
 		actionForm.selectedStateId.value=selectedStateId;
   		multiAction('register');
   }
   
   function exportData(){
     if(${totalSize}>10000){alert("数据量超过一万不能导出");return;}
     addInput(form,"keys","code,name,gender.name,enrollYear,department.name");
     addInput(form,"titles","学号,姓名,性别,入学时间,院系");
     addInput(form,"fileName","没有注册学生列表");
     exportList();
   }
 </script>
 </body>   
<#include "/templates/foot.ftl"/> 