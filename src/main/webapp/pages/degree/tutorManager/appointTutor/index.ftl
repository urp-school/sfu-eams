<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="appointTutorBar" width="100%"></table>
<table width="100%" class="frameTable">
	<tr>
		<form name="conditionForm" method="post" target="stdListFrame">
			<td align="left" width="18%" valign="top" class="frameTable_view">
				<#include "stdConditions.ftl"/>
			</td>
		</form>
		<td valign="top">
			<iframe name="stdListFrame" scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
		</td>
	</tr>
</table>
<script>
    var bar = new ToolBar('appointTutorBar','选择学生,指定导师',null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addBlankItem();
   
	var form = document.conditionForm;
	function search(pageNo,pageSize,orderBy){
	    form.action="appointTutor.do?method=search";
		goToPage(form,pageNo,pageSize,orderBy);
	}
	search(1);
</script>
</body>
<#include "/templates/foot.ftl"/>