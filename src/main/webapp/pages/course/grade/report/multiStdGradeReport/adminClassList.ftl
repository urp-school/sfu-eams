<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar"></table>
 <#include "/pages/components/adminClassListTable.ftl"/>
  <form name="actionForm" method="post" action="" onsubmit="return false;">
	    <#include "../stdTermGradeReport/multiStdReportSetting.ftl"/>
	    <input name="calendar.id" type="hidden" value="${RequestParameters['calendar.id']}">
  </form>
 <script language="javascript">
    var bar = new ToolBar('myBar', '班级列表', null, true, true);
    bar.setMessage('<@getMessage/>');
    var menu2 = bar.addMenu("查看", "printMultiStdGrade()");
    menu2.addItem("打印设定", "displayMultiStdSetting()");
    function printMultiStdGrade(){
       actionForm.target="_blank";
       submitId(actionForm,"adminClassId",true,'multiStdGradeReport.do?method=classGradeReport');
     }
    function adminClassIdAction(adminClassId){
       actionForm.target="_blank";
       actionForm.action='multiStdGradeReport.do?method=classGradeReport';
       addInput(actionForm,"adminClassIds",adminClassId);
       actionForm.submit();
    }
  </script>
  </body>
<#include "/templates/foot.ftl"/>