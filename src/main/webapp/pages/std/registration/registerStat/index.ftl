<#include "/templates/head.ftl"/>
 <body >  
 <table id="titleBar" width="100%"></table>
    <table  class="frameTable_title">
      <tr>
       <td  style="width:50px" >
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|</td>
    <form name="stdSearch" method="post" action="registerStat.do?method=index" target="contentFrame" onusubmit="return false;">
      <input type="hidden" name="register.calendar.id" value="${calendar.id}"/>
      <#include "/pages/course/calendar.ftl"/>
     </tr>
   </table>
 <table width="100%" border="0" height="100%" class="frameTable">
  <tr>
   <td style="width:20%" valign="top" class="frameTable_view">
   <#assign stdTypeList =calendarStdTypes/>
   <#include "/pages/components/initAspectSelectData.ftl"/>
		<#include "searchForm.ftl"/>
	  </form>
     </td>
     <td valign="top">
	     <iframe  src="#" id="contentFrame" name="contentFrame" marginwidth="0" marginheight="0" scrolling="auto" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
<script>
    var action="registerStat.do";
    function statDepart(){
       var form = document.stdSearch;
	   form.action = action+"?method=statDepart";
	   form.submit();
     }
    var bar = new ToolBar("titleBar","注册统计",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addHelp("<@msg.message key="action.help"/>");
    function notPassed(){
       setSelected(document.getElementById("isPass"),3);
    }
    statDepart();
</script> 
 </body>   
<#include "/templates/foot.ftl"/> 