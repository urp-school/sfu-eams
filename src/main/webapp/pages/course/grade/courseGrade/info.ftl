<#include "/templates/head.ftl"/>
<body >
 <table id="taskBar"></table>
 <#include "../collegeGrade/courseGradeList.ftl"/>
 <form name="actionForm" method="post" action="${action}?method=info" onsubmit="return false;">
 <input name="taskId" type="hidden" value="${RequestParameters['taskId']}"/>
 <input type="hidden" name="params" value="&taskId=${RequestParameters['taskId']}"/>
 </form>
 <script>
     var bar=new ToolBar('taskBar','成绩列表(${grades?size}) 序号:${task.seqNo} 课程:<@i18nName task.course/>',null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addItem("<@msg.message key="action.edit"/>","edit()");
     bar.addItem("<@msg.message key="action.delete"/>","remove()");
     bar.addPrint("<@msg.message key="action.print"/>");
     bar.addItem("返回列表","parent.searchTask()","backward.gif");
     function edit(){
        submitId(actionForm,"courseGradeId",false,"courseGrade.do?method=edit");
     }
     function remove(){
        submitId(actionForm,"courseGradeId",true,"courseGrade.do?method=remove","确认删除选择的成绩吗?\n删除成绩后，如果这们课程未发布。教师可以再次录入");
     }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 