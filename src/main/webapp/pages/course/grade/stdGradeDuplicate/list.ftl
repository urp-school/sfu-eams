<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="gradeListBar" width="100%"> </table>
  <#include "../courseGradeListTable.ftl"/>
  <form name="gradeListForm" method="post" action="stdGradeDuplicate.do?method=info" onsubmit="return false;">
  </form>
  <script>
    var bar = new ToolBar("gradeListBar","成绩查询结果（实践）",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.info"/>","gradeInfo()");
    var menuBar = bar.addMenu("<@msg.message key="action.add"/>(按代码)","batchAddGrade()","new.gif");
    menuBar.addItem("<@msg.message key="action.add"/>(按序号)","addGrade()","new.gif");
    bar.addItem("<@msg.message key="action.edit"/>","editGrade()");
    bar.addItem("<@msg.message key="action.export"/>","exportData()");
    bar.addItem("<@msg.message key="action.delete"/>","removeGrade()");
    bar.addItem("批量修改","batchEdit()");
    bar.addPrint("<@msg.message key="action.print"/>");
    
    var form=document.gradeListForm;
    function editGrade(){
      form.target="_self";
	  addParamsInput(form,queryStr);
      submitId(form,"courseGradeId",false,"stdGradeDuplicate.do?method=edit");
    }
    function removeGrade(){
      form.target="_self";
	  addParamsInput(form,queryStr);
      submitId(form,"courseGradeId",true,"stdGradeDuplicate.do?method=removeGrade","确定删除成绩?");
    }
    function batchEdit(){
      form.target="_self";
	  addParamsInput(form,queryStr);
      submitId(form,"courseGradeId",true,"stdGradeDuplicate.do?method=batchEdit");
    }
    function gradeInfo(courseGradeId){
      form.action="stdGradeDuplicate.do?method=info";
      form.target="_self";
      if(null==courseGradeId){
        submitId(form,"courseGradeId",false);
      }else{
        addInput(form,"courseGradeId",courseGradeId);
        document.gradeListForm.submit();
      }
    }
    function exportData(){
       form.target="_self";
       if(confirm("是否导出已经查询出的所有成绩？")){
          transferParams(parent.document.stdSearch,form,null,false);
          addInput(form,"keys","calendar.year,calendar.term,std.code,std.name,std.firstMajorClass.name,taskSeqNo,course.code,course.name,teacherNames,credit,scoreDisplay,creditAcquired,GP,courseType.name,courseTakeType.name,std.type.name");
          addInput(form,"titles","学年度,学期,<@msg.message key="attr.stdNo"/>,<@msg.message key="attr.personName"/>,班级,<@msg.message key="attr.taskNo"/>,<@msg.message key="attr.courseNo"/>,<@msg.message key="attr.courseName"/>,授课教师,课程学分,分数,实得学分,绩点,<@msg.message key="entity.courseType"/>,修读类别,<@msg.message key="entity.studentType"/>");
          form.action="stdGradeDuplicate.do?method=export";
          form.submit();
       }
    }
    function batchAddGrade(){
       transferParams(parent.document.stdSearch,form,null,false);
       form.action="stdGradeDuplicate.do?method=batchAdd";
       form.target="_blank";
       form.submit();
    }
    
    function addGrade() {
       	transferParams(parent.document.stdSearch, form, null, false);
    	form.action = "stdGradeDuplicate.do?method=addGrade";
    	form.target = "_blank";
    	form.submit();
    }
  </script>
 </body>
<#include "/templates/foot.ftl"/>
