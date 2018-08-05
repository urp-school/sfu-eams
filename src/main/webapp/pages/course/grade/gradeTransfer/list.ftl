<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="gradeListBar" width="100%"> </table>
  <#include "../courseGradeListTable.ftl"/>
  <form name="gradeListForm" method="post" action="gradeTransfer.do?method=transfer" onsubmit="return false;"></form>
  <script>
    function transfer(){
      var params = getInputParams(parent.document.stdSearch,null,false);
	  addParamsInput(document.gradeListForm,params);
      submitId(gradeListForm,"courseGradeId",true,"gradeTransfer.do?method=transfer" ,"确定转移双转业成绩吗?\n如果转移操作失误,可以在[学生成绩]管理中更改过来.");
    }
    function gradeInfo(id) {
    	;
    }
    var bar = new ToolBar("gradeListBar","双专业成绩查询结果",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("转移到一专业","transfer()");
    bar.addPrint("<@msg.message key="action.print"/>");
  </script>
 </body>
<#include "/templates/foot.ftl"/>
