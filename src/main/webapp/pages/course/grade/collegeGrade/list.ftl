<#include "/templates/head.ftl"/>
<body >
 <table id="taskBar"></table>
 <#include "../taskListTable.ftl"/>
  <form name="actionForm" method="post" action="" onsubmit="return false;">
     <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}"/>
     <input type="hidden" name="params" value=""/>
  </form>
  <script language="JavaScript" type="text/JavaScript" src="scripts/course/grade/gradeSeg.js"></script>
  <script language="JavaScript" type="text/JavaScript" src="scripts/course/grade/grade.js"></script>
  <script> 
  	 var action="${action}";
     var bar=new ToolBar('taskBar','<@bean.message key="entity.teachTask"/> <@bean.message key="entity.list"/>',null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addItem('成绩状态','gradeStateInfo(document.actionForm)');
     bar.addItem('查看成绩','info(document.actionForm)');
     <#if RequestParameters['gradeState.confirmGA'] == "1">
     var printMenu = bar.addMenu("<@msg.message key="action.print"/>...",null,"print.gif");
     printMenu.addItem("教学班成绩","printTeachClassGrade(document.actionForm)",'print.gif');
     printMenu.addItem('任务分段统计',"printStatReport(document.actionForm,'task')");
     printMenu.addItem('课程分段统计',"printStatReport(document.actionForm,'course')");
     printMenu.addItem('试卷分析',"printExamReport(document.actionForm)");    
     <#else>
     bar.addItem("录入成绩","inputGrade()",'new.gif');
     </#if>
     bar.addItem("调整百分比","editGradeState()",'update.gif');
     var menu = bar.addMenu("删除成绩...",null,"delete.gif");
     <#list gradeTypes?sort_by("priority") as gradeType>
     menu.addItem("删除<@i18nName gradeType/>","removeGrade(${gradeType.id})","delete.gif");
     </#list>
    function pageGoWithSize(pageNo,pageSize){
        parent.searchTask(pageNo,pageSize,'${RequestParameters['orderBy']?default("null")}');
    }
    orderBy = function(what){
        parent.searchTask(1,${pageSize},what);
    }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 