<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="taskConfirmBar"></table>
	<script>
	  var bar=new ToolBar("taskConfirmBar","<@bean.message key="info.task.confirm.management" />",null,true,true);
	  bar.addHelp("<@msg.message key="action.help"/>","teachTaskConfirm/index");
	</script> 

     <table class="frameTable_title" width="100%">
      <tr>
      <td class="infoTitle"><@bean.message key="entity.college" /> <@bean.message key="common.list" />：</td>
      <td class="separator">|</td>
      <form name="taskForm" target="contentFrame" method="post" action="teachTaskConfirm.do?method=index" onsubmit="return false;">
      <#--
	      <#if (departmentList?size!=0)>
	      <input type="hidden" name="task.arrangeInfo.teachDepart.id" value="${departmentList?first.id?if_exists}" />
	      </#if>
      -->
	      <input type="hidden" name="task.calendar.id" value="${calendar.id}" />
	      <input type="hidden" name="active" value="" />
	      <#include "/pages/course/calendar.ftl"/>
         </tr>
    </table>
    <table class="frameTable">
        <tr>
            <td valign="top" width="19%" class="frameTable_view">
                <#include "../teachTaskSearchForm.ftl"/>
            </td>
          </form>
            <td valign="top" bgcolor="white">
                 <iframe  src="#" id="contentFrame" name="contentFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
            </td>
        </tr>
    </table>
 <script>
    search();
        function search(){
            var form = document.taskForm;
            taskForm.action = "?method=search";
            form.target="contentFrame";
            form.submit();
   } 
 </script>
 </body>
<#include "/templates/foot.ftl"/> 
  