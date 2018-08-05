<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/grade/gradeSeg.js"></script>
<body>
 <table id="taskBar"></table>
 <#include "taskListTable.ftl"/>
  <form name="actionForm" method="post" action="" onsubmit="return false;"></form>
  <script>
	var bar=new ToolBar('taskBar','<@bean.message key="entity.teachTask"/> <@bean.message key="entity.list"/>',null,true,true);
    bar.setMessage('<@getMessage/>');
    var action="teachClassGradeReport.do";
    bar.addItem("教学班成绩","printTeachClassGrade(document.actionForm)",'print.gif');
    bar.addItem('任务分段统计',"printStatReport(document.actionForm,'task')");
    bar.addItem('课程分段统计',"printStatReport(document.actionForm,'course')");
    bar.addItem('试卷分析',"printExamReport(document.actionForm)");
    bar.addItem("导出","exportData()");
    function exportData(){
       var form =document.actionForm;
       form.action=action+"?method=export&template=teachClassGradeReport.xls";
       submitId(form,"taskId",true);
    }
    function pageGoWithSize(pageNo,pageSize){
        parent.searchTask(pageNo,pageSize,'${RequestParameters['orderBy']?default("null")}');
    }
    orderBy = function(what){
        parent.searchTask(1,${pageSize},what);
    }
    	 //打印分段统计
	 function printStatReport(form,kind){
	    form.target="_blank";
	    form.action=action+"?method=stat";
	    for(var i=0;i<seg.length;i++){
          var segAttr="segStat.scoreSegments["+i+"]";
          addInput(form,segAttr+".min",seg[i].min);
          addInput(form,segAttr+".max",seg[i].max);
        }
        if(null==kind){
           kind="task";
        }
        addInput(form,"kind",kind);
	    submitId(form,"taskId",true);
	    form.target="_self";
	 }
     //打印试卷分析
     function printExamReport(form){
        form.target="_blank";
        form.action=action+"?method=reportForExam";
	    for(var i=0;i<seg.length;i++){
          var segAttr="segStat.scoreSegments["+i+"]";
          addInput(form,segAttr+".min",seg[i].min);
          addInput(form,segAttr+".max",seg[i].max);
        }
        submitId(form,"taskId",true);
        form.target="_self";
     }
	 //打印教学班成绩
	 function printTeachClassGrade(form){
        form.target="_blank";
        submitId(form,"taskId",true,action+"?method=report");
        form.target="_self";
	 }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 