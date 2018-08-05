<#include "/templates/head.ftl"/>
<body>
 <table id="taskBar"></table>
 <#include "courseGradeList.ftl"/>
 <script>   
     var bar=new ToolBar('taskBar','成绩列表(${grades?size}) 序号:${task.seqNo} 课程:<@i18nName task.course/>',null,true,true);
     //bar.addPrint("<@msg.message key="action.print"/>");
     bar.addBack();
  </script>
</body> 
<#include "/templates/foot.ftl"/> 