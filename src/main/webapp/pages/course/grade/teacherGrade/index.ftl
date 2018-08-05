<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="taskListBar" width="100%"> </table>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/grade/gradeSeg.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/grade/grade.js"></script>
<script>
    function inputGrade(taskId){
     var form=document.calendarForm;        
        url="teacherGrade.do?method=input&taskId="+taskId;
     window.open(url);
    }
   var bar = new ToolBar("taskListBar","<@msg.message key="info.courseList"/>",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="grade.adjustGradePercent"/>","editGradeState()","update.gif");
   bar.addItem("<@msg.message key="grade.viewGrades"/>","info(document.calendarForm)");
   bar.addItem("打印空白登分册","printEmptyGradeTable()");
   //bar.addItem("<@msg.message key="action.print"/>","printTeachClassGrade()");
   bar.addItem("<@msg.message key="grade.statReport"/>","stat('stat')","list.gif");
   bar.addItem("<@msg.message key="grade.analysisReport"/>","stat('reportForExam')","print.gif");
   //var menu = bar.addMenu("<@msg.message key="action.delete"/>...",null,"delete.gif");
   //menu.addItem("<@msg.message key="grade.deleteFinal"/>","removeGrade(${GAGrade.id},'<@msg.message key="grade.deleteFinalConfirm"/>')","delete.gif");
   <#list gradeTypes as gradeType>
   //menu.addItem("<@msg.message key="action.delete"/> <@i18nName gradeType/>","removeGrade(${gradeType.id},'确认删除成绩?')","delete.gif");
   </#list>
   bar.addItem("<@msg.message key="action.refresh"/>","refresh()");
   action="teacherGrade.do";
   /**
    * 找到老师已经录入期末两次的教学任务id串
    */
   function getPrintableTaskIds(){
      var taskIdSeq = getSelectIds("taskId");
      if(""==taskIdSeq){
          alert("<@msg.message key="action.selectOneObject"/>");
          return "";
      }
      var taskIds = taskIdSeq.split(",");
      for(var i=0;i<taskIds.length;i++){
        if(!printableTaskId[taskIds[i]]){
           alert("你选的任务里，还有没有完全录完的成绩，请选择别的任务或者录入这些成绩。")
           return "";
        }
      }
      return taskIds;
   }
   	var printableTaskId=new Object();
	<#list tasks as task>
	   printableTaskId['${task.id}']=${(task.gradeState.isConfirmed(GAGrade))?default(false)?string};
	</#list>
	
     printTeachClassGrade =function(taskId){
	    var form = document.calendarForm;
	    form.action="teacherGrade.do?method=report";
	    form.target='_blank';
	    var taskIds ="";
	    if(null==taskId){
	       taskIds =getPrintableTaskIds();
	    }else{
	       taskIds=taskId;
	    }
	    if(""!=taskIds){
	       form.action+='&taskIds='+taskIds;
	       form.submit();
	    }
	}
	 printEmptyGradeTable =function(taskId){
	    var form = document.calendarForm;
	    form.action="teacherGrade.do?method=printEmptyGradeTable";
	    form.target='_blank';
	    var taskIds ="";
	    if(null==taskId){
	       taskIds = getSelectIds("taskId")
	    }else{
	       taskIds=taskId;
	    }
	    if(""!=taskIds){
	       form.action+='&teachTaskIds='+taskIds;
	       form.submit();
	    }else{
	       alert("请选择课程.");
	    }
	}
	
    function stat(method){
      var form =document.calendarForm;
      form.target='_blank';
      var taskIds=getPrintableTaskIds();
      if(""==taskIds)return;
      
      for(var i=0;i<seg.length;i++){
         var segAttr="segStat.scoreSegments["+i+"]";
         addInput(form,segAttr+".min",seg[i].min);
         addInput(form,segAttr+".max",seg[i].max);
      }
      form.action="teacherGrade.do?method="+method+"&taskIds="+taskIds;
      form.submit();
    }
    function refresh(){
      changeCalendar(document.calendarForm);
    }
    function removeGrade(gradeTypeId,tip){
       var form =document.calendarForm;
       form.target="_self";
       submitId(form,"taskId",false,"teacherGrade.do?method=removeGrade&gradeTypeId="+gradeTypeId,tip);
    }
    function editGradeState(){
       var form =document.calendarForm;
       form.target="_blank";
       submitId(form,"taskId",false,"teacherGrade.do?method=editGradeState");
    }
    </script>
   <table class="frameTable_title" width="100%" border="0">
    <form name="calendarForm" action="teacherGrade.do?method=index" method="post" onsubmit="return false;">
    <input type="hidden" name="pageNo" value="1"/>
     <tr  style="font-size: 10pt;" align="left">
     <td width="30%"><@bean.message key="attr.yearAndTerm"/></td>
        <#include "/pages/course/calendar.ftl"/>
     </form>
     </tr>
    </table>
    <#include "taskList.ftl"/>
    <pre>
     操作提示:
         1)录入成绩,直接点击每个任务最后的<font color="blue">录入成绩</font>链接.每次录入完成一遍后,点击<button onclick="changeCalendar(document.calendarForm)">刷新</button>,按照提示进行下一次录入
         2)打印<font color="blue">成绩单</font>,直接点击每个任务最后的<font color="blue">打印</font>链接
         3)打印<font color="blue">分段统计表</font>,选择一个或多个任务,点击右上侧的【分段统计表】按钮
         4)打印<font color="blue">试卷分析表</font>,选择一个或多个任务,点击右上侧的【试卷分析表】按钮
         5)已经录入的成绩详细信息,可以通过选择任意任务后,点击【查看成绩】按钮
         6)<font color="blue">删除成绩</font>选择任意一个任务后,点击【删除成绩..】菜单,从下拉项中选择。
         7)<font color="blue">调整百分比</font>，在成绩<font color="blue">录入之前或录入之后</font>均可使用，选择单个任务,点击【调整百分比】按钮
    </pre>
</body>
<#include "/templates/foot.ftl"/>