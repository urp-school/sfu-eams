<#include "/templates/head.ftl"/>
<body>
 <table id="gradeBar"></table>
 <table class="frameTable_title">
    <tr>
       <td style="width:50px">
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|</td>
    <form name="stdSearchForm" target="reportFrame" method="post" action="stdTermGradeReport.do?method=index" onsubmit="return false;">
      <#include "/pages/course/calendar.ftl"/>
   </tr>
  </table>
  <table width="100%" border="0" height="100%" class="frameTable">
   <tr>
     <td style="width:20%" valign="top" class="frameTable_view">
        <#assign stdTypeList =calendarStdTypes/>
        <#include "/pages/components/initAspectSelectData.ftl"/>
		<#include "/pages/components/stdSearchTable.ftl"/>
     </td>
   </form>
     <td valign="top">
	     <iframe src="#" id="reportFrame" name="reportFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
<script>
   	var form =document.stdSearchForm;
    var action="stdTermGradeReport.do";
    function search(pageNo,pageSize,orderBy){
       var form = document.stdSearchForm;
	   form.action = action+"?method=stdList";
	   form.target="reportFrame";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    function personGrade(id){
       if(id==null||''==id){
          alert("没有选择学生");
          return;
       }
       form.target="_blank";
       form.action=action+"?method=report&stdIds="+id;
       form.submit();
    }
    search(1,null);
    var bar=new ToolBar("gradeBar","学生每学期成绩表",null,true,true);
    bar.addItem("统计学分不过半学生", "lessHalfStat('')");

    function lessHalfStat() {
    	form.action = "stdTermGradeReport.do?method=lessHalfStat";
	   	form.target = "reportFrame";
	   	form.submit();
    }
</script> 
</body>
<#include "/templates/foot.ftl"/>