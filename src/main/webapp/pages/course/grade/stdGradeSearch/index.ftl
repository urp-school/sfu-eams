<#include "/templates/head.ftl"/>
<body>
 <table id="gradeBar"></table>
  <table width="100%" border="0" height="100%" class="frameTable">
  <tr >
   <td style="width:20%" valign="top" class="frameTable_view">
		<#include "searchForm.ftl"/>
     </td>
     <td valign="top">
	     <iframe src="#" id="gradeFrame" name="gradeFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
<script>
    function search(pageNo,pageSize,orderBy){
       var form = document.stdSearch;
       if(form['courseGrade.std.code'].value!=""&&null==orderBy){
          orderBy="courseGrade.calendar.start desc";
       }
	   form.action = "stdGradeSearch.do?method=search";
	   form.target="gradeFrame";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    var bar=new ToolBar("gradeBar","成绩查询",null,true,true);
    bar.addItem("不及格成绩学生名单","notPassed()");
    bar.addHelp("<@msg.message key="action.help"/>");
    search();
    function notPassed(){
       setSelected(document.getElementById("isPass"),3);
       search();
    }
</script> 
 </body>   
<#include "/templates/foot.ftl"/> 