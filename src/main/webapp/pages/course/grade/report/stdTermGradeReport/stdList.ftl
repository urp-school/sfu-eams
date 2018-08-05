<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <table id="myBar" width="100%"></table>
    <#if RequestParameters['majorTypeId']=="1">
       <#include "/pages/components/stdList1stTable.ftl"/>
    <#else>
      <#include "/pages/components/stdList2ndTable.ftl"/>
    </#if>
  <form name="stdListForm" method="post" onsubmit="return false;" action="">
        <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}"/>
	    <#include "reportSetting.ftl"/>
  </form>
  <form name="multiStdForm" method="post" onsubmit="return false;" action="">
        <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}"/>
	    <#include "multiStdReportSetting.ftl"/>
  </form>
  <script>
    var bar = new ToolBar("myBar","学生列表",null,true,true);
    var menu1=bar.addMenu("分开打印","printGrade()");
    menu1.addItem("打印设定","displaySetting()",'setting.png');
    var menu2=bar.addMenu("合并打印","printMultiStdGrade()");
    menu2.addItem("打印设定",displayMultiStdSetting);
    
    function printGrade(stdId){
       var form=document.stdListForm;
       form.target="_blank";
       form.action="stdTermGradeReport.do?method=report";
       if(null!=stdId){
           form.action+="&stdIds="+stdId;
           form.submit();
       }else{
          submitId(form,"stdId",true);
       }
    }
    stdIdAction=printGrade;
    function printMultiStdGrade(){
       var form=document.multiStdForm;
       form.target="_blank";
       submitId(form,"stdId",true,"stdTermGradeReport.do?method=multiStdReport");
    }
  </script>
 </body>
<#include "/templates/foot.ftl"/>
