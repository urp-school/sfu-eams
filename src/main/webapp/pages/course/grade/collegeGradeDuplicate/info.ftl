<#include "/templates/head.ftl"/>
<body>
 <table id="taskBar"></table>
 <#include "courseGradeList.ftl"/>
 <script>
     var bar=new ToolBar('taskBar','实践课成绩列表(${grades?size}) 序号:${task.seqNo} 课程:<@i18nName task.course/>',null,true,true);
     bar.addPrint("<@msg.message key="action.print"/>");
     bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
 </script>
</body>
<#include "/templates/foot.ftl"/> 