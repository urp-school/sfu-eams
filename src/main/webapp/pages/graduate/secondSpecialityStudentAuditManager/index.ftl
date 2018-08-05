<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<BODY>
	<table id="topBar" width="100%" align="center"></table>
	<table width="100%" class="frameTable">
	   	<tr valign="top">
	   		<td class="frameTable_view" width="22%">
				<#include "indexContent.ftl"/><#include "searchForm.ftl"/>
	   		</td>
	   		<td>
	   			<iframe name="pageIFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
	   		</td>
	   	</tr>
   	</table>
	<script>
		var bar=new ToolBar("topBar","双专业毕业审核",null,true,true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		search();
	</script>
</body>
<#include "/templates/foot.ftl"/>