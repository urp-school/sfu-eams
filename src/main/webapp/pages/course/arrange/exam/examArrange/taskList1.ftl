<#include "/templates/head.ftl"/>
<body >
  <table id="taskBar"></table>
  <script>
     var bar=new ToolBar('taskBar','<@bean.message key="entity.teachTask"/> <@bean.message key="entity.list"/>',null,true,false);
     bar.addItem("选中排考","examArrange()");
     bar.addItem("<@msg.message key="action.export"/>","exportData()");
     bar.addItem("打印随堂考试卷带标签","printPageLabel()");
     function exportData(){
       var taskIds = getSelectIds("taskId");
	   if(""==taskIds){alert("请选择教学任务进行导出");return;}
	   var form =document.actionForm;
	   addInput(form,"taskIds",taskIds);
	   addInput(form,"keys","seqNo,course.code,course.name,teachClass.name,arrangeInfo.teachers,teachClass.stdCount,credit,arrangeInfo.weekUnits,arrangeInfo.weeks,arrangeInfo.overallUnits,arrangeInfo.teachDepart.name,arrangeInfo.activities,requirement.isGuaPai");
	   addInput(form,"titles","<@msg.message key="attr.taskNo"/>,<@msg.message key="attr.courseNo"/>,<@msg.message key="attr.courseName"/>,教学班,教师,上课人数,<@msg.message key="attr.credit"/>,周课时,周数,总课时,主考院系,时间安排,是否挂牌");
	   form.action="teachTask.do?method=export";
	   form.submit();
     }
     function printPageLabel(){
       var form =document.actionForm;
       var taskIds = getSelectIds("taskId");
	   if(""==taskIds){alert("请选择教学任务进行打印");return;}
	   addInput(form,"taskIds",taskIds);
	   var extraCount=prompt("需要给每个考场多加几份试卷？",0);
	   	if (extraCount == null || extraCount == "") {
	   		return;
       	} else if (!/^\d+$/.test(extraCount) || parseInt(extraCount) < 0) {
        	alert("输入不正确.");
        	return;
        }
        if(extraCount==null){
        	extraCount=0;
       }
       form.target="_blank";
	   form.action="examArrange.do?method=printPaperLabelForTask&extraCount="+extraCount;
	   form.submit();
     }
</script>
<#include "unarrangeExamList.ftl"/>
 	<form name="actionForm" method="post" action="" onsubmit="return false;"></form>
  <script>
	function examArrange(){
	   var taskIds = getSelectIds("taskId");
	   if(""==taskIds){alert("请选择教学任务进行排考");return;}
	   var form =document.actionForm;
	   addInput(form,"taskIds",taskIds);
	   addInput(form,"examParams.examType.id",parent.document.taskSearchForm['examType.id'].value);
	   addInput(form,"calendar.studentType.id",parent.document.taskSearchForm['calendar.studentType.id'].value);
	   addInput(form,"calendar.id",parent.document.taskSearchForm['calendar.id'].value);
       addParamsInput(form,queryStr);
	   form.action="examArrange.do?method=arrangeSetting";
	   form.submit();
	}
  </script>
</body> 
<#include "/templates/foot.ftl"/> 