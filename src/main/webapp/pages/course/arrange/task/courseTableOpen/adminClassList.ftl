<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="backBar"></table>
 <#include "/pages/components/adminClassListTable.ftl"/>
 <#assign courseTableType="class">
 <#include "courseTableSetting.ftl"/>
 <script language="javascript">
   	var bar = new ToolBar('backBar','<@bean.message key="entity.adminClass"/> <@bean.message key="common.list"/>',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	bar.addItem("大课表", "courseTableOfTask()");
   	bar.addItem('<@msg.message key="action.printSet"/>','displaySetting()','setting.png');
   	bar.addItem('<@msg.message key="action.selectPreview"/>','printCourseTable()','print.gif');
    function adminClassIdAction(id){
       window.open("?method=courseTable&setting.kind=class&calendar.id=${calendar.id}&ids="+id);
    }
    
    function courseTableOfTask() {
    	var form = document.actionForm;
    	form.action = "?method=courseTableOfTask";
    	form.target = "_blank";
    	submitId(form, "adminClassId", true);
    }
  </script>
  </body>
<#include "/templates/foot.ftl"/>