<#include "/templates/head.ftl"/>
<#include "code.ftl"/>
<body>
	<table id="bar"></table>
	<h3 align="center">上海财经大学系统需求报告</h3>
	<table width="80%" align="center">
		<tr>
			<td>
				<#list requireStatue as statue>
					<li><h5>${statusMap[statue[0]]?string}</h5>
					<#assign index = 0/>
					<@table.table id="requirePreview" align="center" width="100%">
						<@table.thead>
							<@table.td text="序号"/>
							<@table.td text="模块"/>
							<@table.td text="意见内容"/>
							<@table.td text="建议人"/>
						</@>
						<#list requires as require>
							<#if (statue[0]?number)?default(-1) == require.status>
								<tr valign="top" align="center">
									<td width="10%"><#assign index = index + 1/>${index}</td>
									<td width="20%">${require.module}</td>
									<td align="left">${require.content?if_exists}</td>
									<td width="20%">${require.fromUser}</td>
								</tr>
							</#if>
						</#list>
					</@>
					<br>
					<br>
				</#list>
			</td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "系统需求打印预览（全部）", null, true, true);
		bar.addPrint();
		bar.addClose();
	</script>
</body>
<#include "/templates/foot.ftl"/>