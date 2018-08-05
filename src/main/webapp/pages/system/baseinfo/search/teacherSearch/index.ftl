<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="frameTable" width="100%">
		<tr valign="top">
			<form method="post" action="" name="actionForm">
			<td class="frameTable_view" width="20%">
		      <#assign extraSearchOptions>
			     <tr><td><@bean.message key="teacher.isTeaching"/>:</td>
			         <td>
			        <select name="teacher.isTeaching" style="width:100px;">
				   		<option value="1" <#if RequestParameters['teacher.isTeaching']?if_exists=="1">selected</#if>><@bean.message key="common.yes" /></option>
				   		<option value="0" <#if RequestParameters['teacher.isTeaching']?if_exists=="0">selected</#if>><@bean.message key="common.no" /></option>
				   		<option value="" <#if RequestParameters['teacher.isTeaching']?if_exists=="">selected</#if>><@bean.message key="common.all" /></option>
			        </select>
			     </td>
			     </tr>
		      </#assign>
			  <#include "/pages/components/teacherSearchTable.ftl">
                <table><tr height="300px"><td></td></tr></table>
			</td>
			</form>
			<td><iframe name="pageFrame" src="#" width="100%" height="100%" frameborder="0" scrolling="no"></iframe></td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "教师基础信息查询", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		var form = document.actionForm;
		function searchTeacher() {
			form.action = "teacherSearch.do?method=search";
			form.target = "pageFrame";
			form.submit();
		}
    	searchTeacher();
	</script>
</body>
<#include "/templates/foot.ftl"/>