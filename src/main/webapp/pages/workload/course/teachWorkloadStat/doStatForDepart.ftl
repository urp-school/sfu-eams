<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="printBar" width="100%"></table>
 	<script>
 	  var bar = new ToolBar("printBar","教师教学工作量部门汇总",null,true,true);
 	  bar.setMessage('<@getMessage/>');
 	  bar.addPrint();
 	  bar.addBack();
 	</script>
<table width="100%" class="frameTable">
	<tr>
		<td style="width:20%" class="frameTable_view" valign="top">
			<table width="100%" class="searchTable">
				<form name="departQueryForm" method="post" target="departQueryFrame" action="" onsubmit="return false;">
				<tr><td colspan="2" align="center">查询条件</td>
				</tr>
				<tr>
				<td colspan="2" align="left">学年</td>
				<td>
				<select name="year" size="1" style="width:100px;">
				<#list yearList?if_exists as year>
				<option value="${year}">${year}</option>
				</#list>
				</select>
				</td>
				</tr>
				<tr>
				<td colspan="2" align="left">学期</td>
				<td>
				<select name="term" size="1" style="width:100px;">
				<#list termList?if_exists as term>
				<option value="${term}">${term}</option>
				</#list>
				</select>
				</td>
				</tr>
				<tr>
				<td colspan="2" align="left">院系</td>
				<td>
				<select name="department" size="1" style="width:100px;">
				<#list departmentList?if_exists as department>
				<option value="${department.id}">${department.name}</option>
				</#list>
				</select>
				</td>
				</tr>
				
				<tr>
					<td colspan="2" align="center">
						<button name="button1" onClick="doQuery()" class="buttonStyle"><@msg.message key="action.query"/></button>
					</td>
				</tr>
				</from>
				</table>
		</td>
		<td valign="top">
			<iframe name="departQueryFrame" width="100%" frameborder="0" scrolling="no"></iframe>
		</td>
	</tr>
	</table>
<script language="javascript">
	 var form =document.departQueryForm;
	 var action="teachWorkloadStat.do";
 	 function doQuery(){
 		form.action=action+"?method=departList";
 		form.submit();
 	}
</script>
</body>
<#include "/templates/foot.ftl"/>