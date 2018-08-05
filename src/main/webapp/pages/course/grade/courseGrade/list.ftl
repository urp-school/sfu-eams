<#include "/templates/head.ftl"/>
<body>
 <table id="taskBar"></table>
 <#include "../taskListTable.ftl"/>
  <br><br><br><br>
  <form name="actionForm" method="post" onsubmit="return false;">
     <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}"/>
     <input type="hidden" name="params" value=""/>
  </form>
  <script language="JavaScript" type="text/JavaScript" src="scripts/course/grade/gradeSeg.js"></script>
  <script language="JavaScript" type="text/JavaScript" src="scripts/course/grade/grade.js"></script>
  <script> 
  	 var action="${action}";
     var bar=new ToolBar('taskBar','<@bean.message key="entity.teachTask"/> <@bean.message key="entity.list"/>',null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addItem('查看成绩','info(document.actionForm)');
     var stateMenu = bar.addMenu('成绩状态','editGradeStateInfo(document.actionForm)');
     stateMenu.addItem("设置四六体系课程","setMoralGrade()");
     stateMenu.addItem("取消四六体系课程","cancelMoralGrade()");
     <#if RequestParameters['gradeState.confirmGA']=="1">
     var publishMenu = bar.addMenu("发布最终成绩","publishGrade(document.actionForm,null,1,0)");
     publishMenu.addItem("发布所有成绩","publishGrade(document.actionForm,null,1,1)");
     publishMenu.addItem("取消发布成绩","publishGrade(document.actionForm,null,0,1)");
     
     var printMenu = bar.addMenu("<@msg.message key="action.print"/>..",null,"print.gif");
     printMenu.addItem("教学班成绩","printTeachClassGrade(document.actionForm)",'print.gif');
     printMenu.addItem('按任务分段统计',"printStatReport(document.actionForm,'task')");
     printMenu.addItem('按课程分段统计',"printStatReport(document.actionForm,'course')");
     printMenu.addItem('试卷分析',"printExamReport(document.actionForm)");    
     <#else>
     bar.addItem("录入成绩","inputGrade()",'new.gif');
     </#if>
     var gradeStateMenu = bar.addMenu("调整百分比","editGradeState()",'update.gif');
     gradeStateMenu.addItem("批量更新成绩","batchUpdateGrade()",'update.gif');
     var menu = bar.addMenu("删除成绩",null,"delete.gif");
     <#list gradeTypes?sort_by("priority") as gradeType>
     menu.addItem("<@i18nName gradeType/>","removeGrade(${gradeType.id})","delete.gif");
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