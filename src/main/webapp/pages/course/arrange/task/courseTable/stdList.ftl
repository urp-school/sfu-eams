<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="backBar"></table>
    <#if RequestParameters['majorTypeId'] == "1">
       <#include "/pages/components/stdList1stTable.ftl"/>
    <#else>
      <#include "/pages/components/stdList2ndTable.ftl"/>
    </#if>
 <#assign courseTableType="std">
 <#include "courseTableSetting.ftl"/>
  <script language="javascript">
   	var bar = new ToolBar('backBar','<@bean.message key="entity.student"/> <@bean.message key="common.list"/>',null,true,true);
   	bar.setMessage('<@getMessage/>');
    function stdIdAction(id){
       window.open("?method=courseTable&setting.kind=std&setting.forCalendar=0&calendar.id=${calendar.id}&ids="+id);
    }
   	bar.addItem('<@msg.message key="action.printSet"/>','displaySetting()','setting.png');
   	bar.addItem('<@msg.message key="action.selectPreview"/>','printCourseTable()','print.gif');
  </script>
  </body>
<#include "/templates/foot.ftl"/>