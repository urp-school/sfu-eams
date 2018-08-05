<#include "/templates/head.ftl"/>
<body>
 <table id="gradeBar"></table>
  <table width="100%" border="0" height="100%" class="frameTable">
  <tr >
     <td style="width:20%" valign="top" class="frameTable_view">
    <form name="stdSearch" method="post" action="" onsubmit="return false;">
		<#include "searchForm.ftl"/>
    </form>
     </td>
     <td valign="top">
	     <iframe src="#" id="gradeFrame" name="gradeFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
<script>
    var bar=new ToolBar("gradeBar","学生成绩管理（实践）",null,true,true);
    bar.addItem("统计不及格学生", "stat('incompetent')");
    bar.addHelp("<@msg.message key="action.help"/>");
    
   	var form = document.stdSearch;
    function search(pageNo,pageSize,orderBy){
       if(form['courseGrade.std.code'].value!=""&&null==orderBy){
          orderBy="courseGrade.calendar.start desc";
       }
	   form.action = "stdGradeDuplicate.do?method=search";
	   form.target="gradeFrame";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    search();
    
    function stat(statOption) {
    	form.action = "stdGradeDuplicate.do?method=stat";
	   	form.target = "gradeFrame";
	   	form.submit();
    }
</script> 
 </body>   
<#include "/templates/foot.ftl"/> 