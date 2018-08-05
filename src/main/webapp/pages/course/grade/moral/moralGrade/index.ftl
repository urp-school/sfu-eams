<#include "/templates/head.ftl"/>
 <body >  
 <table id="gradeBar"></table>
  <table width="100%" border="0" height="100%" class="frameTable">
  <tr >
   <td style="width:20%" valign="top" class="frameTable_view">     
		<#include "searchForm.ftl"/>
     </td>
     <td valign="top">
	     <iframe  src="#" id="gradeFrame" name="gradeFrame" 
	      marginwidth="0" marginheight="0"
	      scrolling="no" 	 frameborder="0"  height="100%" width="100%">
	     </iframe>
     </td>
    </tr>
  <table>
<script>
    function search(pageNo,pageSize,orderBy){
       var form = document.stdSearch;
       if(form['moralGrade.std.code'].value!=""&&null==orderBy){
          orderBy="moralGrade.calendar.start desc";
       }
	   form.action = "moralGrade.do?method=search";
	   form.target="gradeFrame";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    var bar=new ToolBar("gradeBar","德育成绩管理",null,true,true);
    bar.addItem("成绩录入","inputGrade()");
    bar.addHelp("<@msg.message key="action.help"/>");
    search();
    function inputGrade(){
       window.open("moralGradeInstructor.do?method=classList");
    }
</script> 
 </body>   
<#include "/templates/foot.ftl"/> 