<#include "/templates/head.ftl"/>
<BODY topmargin=0 leftmargin=0>
  <table id="bar" width="100%"></table>
   <table  class="frameTable_title">
      <tr>
       <td  style="width:50px" >
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|</td>
      <form name="evaluateForm" method="post" action="textEvaluationSearch.do?method=index" onsubmit="return false;">
      <input type="hidden" name="textEvaluation.calendar.id" value="${calendar.id}" />
      <#include "/pages/course/calendar.ftl"/>
     </tr>
   </table>
	<table   width="100%"  class="frameTable"> 
		<tr>
			<td width="20%" class="frameTable_view" valign="top">
				<#include "searchTable.ftl"/>
				</form>
			</td>
			<td valign="top">
				<iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
			</td>
		</tr>
	</table>
	<script language="javascript">
		function query(form){
			form.action="textEvaluationSearch.do?method=search";
			form.target="displayFrame";
			form.submit();
		}
	   query(document.evaluateForm);
       var bar = new ToolBar('bar','<@bean.message key="textEvaluation.idea"/>',null,true,true);
       bar.addHelp("<@msg.message key="action.help"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>
