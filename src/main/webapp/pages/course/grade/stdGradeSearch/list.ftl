<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="gradeListBar" width="100%"></table>
  <#include "../courseGradeListTable.ftl"/>
  <form name="gradeListForm" method="post" action="stdGradeSearch.do?method=info" onsubmit="return false;"></form>
  <script>
    function gradeInfo(courseGradeId){
      document.gradeListForm.target = "_self";
      document.gradeListForm.action = "stdGradeSearch.do?method=info";
      if (null == courseGradeId) {
        submitId(document.gradeListForm,"courseGradeId",false);
      } else {
        addInput(document.gradeListForm,"courseGradeId",courseGradeId);
        document.gradeListForm.submit();
      }
    }
    function stdReport(){
    	document.gradeListForm.target = "_blank";
      	document.gradeListForm.action = "stdGradeSearch.do?method=stdReport";
        submitId(document.gradeListForm,"courseGradeId",false);
    }
    var bar = new ToolBar("gradeListBar","成绩查询结果",null,true,true);
    bar.addItem("打印成绩单","stdReport()");
    bar.addItem("查看其他成绩","gradeInfo()");
    bar.addPrint("<@msg.message key="action.print"/>");
  </script>
</body>
<#include "/templates/foot.ftl"/>
