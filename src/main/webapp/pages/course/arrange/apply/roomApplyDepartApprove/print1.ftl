<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script>
<BODY>
	<table id="bar"></table>
	<h3><p align="center"><@i18nName systemConfig.school/>已批准教室使用表</p></h3>
	<#assign cycleName={'1':'天','2':'周','3':'月'}/>
	<#assign weekName={'1':'一', '2':'二', '3':'三', '4':'四', '5':'五', '6':'六', '7':'日'}/>
 	<table width="100%" align="center">
 		<tr>
 			<td>
				<@table.table id="listTable" style="width:100%; TABLE-LAYOUT: fixed; WORD-BREAK: break-all" align="center" sortable="true">
				    <@table.thead>
					    <@table.td text="教室名称" width="15%"/>
					    <@table.td text="申请人" width="15%"/>
					    <@table.td text="使用日期" width="20%"/>
					    <@table.td text="每天/周/月的时间点" width="15%"/>
					    <@table.td text="频率" width="10%"/>
					    <@table.td text="申请原因" width="25%"/>
				    </@>
				    <@table.tbody datas=printRoomApplieds?if_exists?sort_by(["applyTime","dateBegin"])?reverse;apply>
						<td>${apply.classroom.name}</td>
						<td>${apply.user.name}</td>
						<td>&nbsp;从 ${apply.applyTime.dateBegin?string("yyyy-MM-dd")} 到 ${apply.applyTime.dateEnd?string("yyyy-MM-dd")}</td>
						<td>${apply.applyTime.timeBegin} - ${apply.applyTime.timeEnd}</td>
						<td>&nbsp;${apply.applyTime.cycleCount} ${cycleName[apply.applyTime.cycleType?string]}</td>
						<td>${apply.description}</td>
				    </@>
				</@>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
 		<#list catalogues as catalogue>
	 		<tr>
	 			<td align="right">${nowDate?string("yyyy-MM-dd")}</td>
	 		</tr>
 		</#list>
 	</table>
	<br>
	<script>
		var bar = new ToolBar("bar"," 打印(A4纸)内容如下：",null,true,true);
		bar.addPrint("<@msg.message key="action.print"/>");
		bar.addClose("<@msg.message key="action.close"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>
