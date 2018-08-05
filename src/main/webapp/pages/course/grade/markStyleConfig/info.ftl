<#include "/templates/head.ftl"/>
<BODY>
	<table id="bar"></table>
	<table width="90%" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td>
				<#include "baseInfo.ftl"/>
				<br>
				<#assign configItems = defaultConfig.converters/>
				<table class="listTable" width="100%" style="text-align:center" align="center">
					<tr>
						<td class="darkColumn" style="font-weight:bold" width="10%">分数名称</td>
						<td class="darkColumn" style="font-weight:bold">最大值</td>
						<td class="darkColumn" style="font-weight:bold">最小值</td>
						<td class="darkColumn" style="font-weight:bold">默认值</td>
					</tr>
					<@table.tbody datas=configItems;configItem>
						<td class="grayStyle" style="font-weight:bold">${configItem.grade?html}</td>
						<td class="brightStyle">${configItem.maxScore?string("0.##")}</td>
						<td class="brightStyle">${configItem.minScore?string("0.##")}</td>
						<td class="brightStyle">${configItem.defaultScore?string("0.##")}</td>
					</@>
				</table>
			</td>
		</tr>
	</table>
	<table height="200"><tr><td></td></tr></table>
	<script>
		var bar = new ToolBar("bar", "<font color=\"blue\"><@i18nName (defaultConfig.markStyle)?if_exists/></font>分数配置详细信息", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
	</script>
</body>
 <#include "/templates/foot.ftl"/>