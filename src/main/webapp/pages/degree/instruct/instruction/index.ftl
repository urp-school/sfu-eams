<#include "/templates/head.ftl"/>
 <body >  
 <table id="gradeBar"></table>
    <table  class="frameTable_title">
      <tr>
       <td  style="width:50px" >
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|</td>
     <form name="stdSearch" method="post" action="instruction.do?method=index" onsubmit="return false;">
      <input type="hidden" name="instruction.calendar.id" value="${calendar.id}" />
      <#include "/pages/course/calendar.ftl"/>
     </tr>
   </table>
  <table width="100%" border="0" height="100%" class="frameTable">
  <tr>
   <td style="width:20%" valign="top" class="frameTable_view">
   <#assign stdTypeList=stdTypesOfCalendar/>
		<#include "/pages/components/initAspectSelectData.ftl"/>
		<#include "searchForm.ftl"/>
	  </form>
     </td>
     <td valign="top">
	     <iframe  src="#" id="contentFrame" name="contentFrame" 
	      marginwidth="0" marginheight="0"
	      scrolling="no" 	 frameborder="0"  height="100%" width="100%">
	     </iframe>
     </td>
    </tr>
  <table>
<script>
    var action="instruction.do";
    function search(pageNo,pageSize,orderBy){
       var form = document.stdSearch;
	   form.action = action+"?method=search";
	   form.target="contentFrame";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    var bar=new ToolBar("gradeBar","每学期指导学生信息管理",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addHelp("<@msg.message key="action.help"/>");
    search();
    function notPassed(){
       setSelected(document.getElementById("isPass"),3);
       search();
    }
</script> 
 </body>   
<#include "/templates/foot.ftl"/> 