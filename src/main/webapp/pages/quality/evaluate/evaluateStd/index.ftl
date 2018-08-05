<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="taskListBar" width="100%"> </table>
<script>
   var bar = new ToolBar("taskListBar","问卷评教",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
   <table class="frameTable_title" width="100%" border="0">
    <form name="calendarForm" action="evaluateStd.do?method=index" method="post">
	     <tr style="font-size: 10pt;" align="left">
	     <td>&nbsp;</td>
	        <#include "/pages/course/calendar.ftl"/>
	    </tr>
	</form>
    </table>
    <table width="100%" height="90%" class="frameTable">
       <tr><td valign="top">
	     <iframe  src="evaluateStd.do?method=list&calendar.id=${calendar.id}"
	     id="examTableFrame" name="examTableFrame" scrolling="no"
	     marginwidth="0" marginheight="0" frameborder="0"  height="100%" width="100%">
	     </iframe>
        </td>
     </tr>
    </table>
</body>
<#include "/templates/foot.ftl"/>