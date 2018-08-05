<#include "/templates/head.ftl"/>
<BODY> 
    <table id="taskBar"></table>
    <#if !calendar?exists>
    <pre>你还有带班,暂时不能管理德育成绩。</pre>
    <#else>
    <table  class="frameTable_title">
        <tr>
	        <td style="width:50px"><font color="blue"><@bean.message key="action.advancedQuery"/></font></td>
            <td>|</td>
          <form name="taskForm" target="classListFrame" method="post" action="moralGradeInstructor.do?method=index" onsubmit="return false;">
            <input type="hidden" name="calendar.id" value="${calendar.id}" />
            <#include "/pages/course/calendar.ftl"/>
        </tr>
    </table>
    <table class="frameTable">
        <tr>
            <td valign="top"  style="width:160px" class="frameTable_view">
                <#include "/pages/components/initAspectSelectData.ftl"/>
                <#include "/pages/components/adminClassSearchTable.ftl"/>
            </td>
          </form>
            <td valign="top" bgcolor="white">
                <iframe  src="#" id="classListFrame" name="classListFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0"  height="100%" width="100%"></iframe>
            </td>
        </tr>
    </table>
	<script>
		var bar=new ToolBar("taskBar","辅导员班级德育成绩管理",null,true,true);
		bar.addHelp("<@msg.message key="action.help"/>");
        searchClass();
        function searchClass(pageNo,pageSize,orderBy){
            var form = document.taskForm;
            form.target="classListFrame";        
            taskForm.action="moralGradeInstructor.do?method=classList";
            goToPage(form,pageNo,pageSize,orderBy);
        }
	</script>
	</#if>
</body>
<#include "/templates/foot.ftl"/> 
  