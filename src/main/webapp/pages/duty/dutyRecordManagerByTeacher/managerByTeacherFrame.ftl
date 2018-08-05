<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="bar"></table>
   <table border="0" align="center" class="frameTable_title" style="width:85%;">
      <tr height="22px" align="center">
      <form name="calendarForm" target="teachTaskListFrame" method="post" action="dutyRecordManagerWithTeacher.do" onsubmit="return false;">
      <input type="hidden" name="method" value="managerByTeacherForm"/>      
      <#assign studentType=(result.stdTypeList?first)?if_exists/>
      <#include "calendar.ftl"/>
       <td align="center" style="width:50px;">
        <input type="button" name="query" onclick="javascript:taskSearch(this.form);"class="buttonStyle" value="切换学期"/>
       </td>
       </form>
      </tr>
     </table>  
     <table border="0" align="center" width="85.5%" >  
      <tr>
      <td valign="top" colspan="12">
	     <iframe  src="#"
	     id="teachTaskListFrame" name="teachTaskListFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="100%" width="100%">
	     </iframe>
      </td>
      </tr>
     </table>
<script>
DWREngine.setAsync(false);
</script>
<#include "/templates/calendarSelect.ftl"/>
<#--><script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script> -->
<script>
var bar = new ToolBar("bar", "学生考勤管理", null, true, true);
bar.setMessage('<@getMessage/>');
bar.addHelp("<@msg.message key="action.help"/>");
DWREngine.setAsync(true);
function taskSearch(){
	document.calendarForm.submit();
}
taskSearch();
</script>
<#assign tableWidth = "85%" />
<#include "/pages/duty/dutyRuleInf.ftl"/>

</body>
<#include "/templates/foot.ftl"/> 
  