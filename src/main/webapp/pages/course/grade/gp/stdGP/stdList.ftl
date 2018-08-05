<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <table id="myBar" width="100%"></table>
    <#assign stdNameTitle="查看每学期绩点">
    <#if RequestParameters['majorTypeId']=="1">
       <#include "/pages/components/stdList1stTable.ftl"/>
    <#else>
      <#include "/pages/components/stdList2ndTable.ftl"/>
    </#if>
  <form name="stdListForm" method="post" action="" onsubmit="return false;">
     <input type="hidden" name="majorTypeId" value="${RequestParameters['majorTypeId']}"/>
  </form>
  <script>
    var bar = new ToolBar("myBar","学生列表",null,true,true);
    bar.addItem("学生明细绩点","stdGPReport()");
    bar.addItem("学生学年绩点","stdCalendarGPReport()");
    
    function stdGPReport(){
       submitId(document.stdListForm,"stdId",true,"stdGP.do?method=stdGPReport");
    }
    
    function stdCalendarGPReport(){
       submitId(document.stdListForm,"stdId",true,"stdGP.do?method=stdCalendarGPReport");
    }
  </script>
 </body>
<#include "/templates/foot.ftl"/>
