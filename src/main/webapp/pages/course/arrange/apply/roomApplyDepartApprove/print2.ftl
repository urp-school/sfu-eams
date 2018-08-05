<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script>
<BODY>
	<table id="bar"></table>
				<#assign cycleName={'1':'天','2':'周','3':'月'}/>
				<#assign weekName={'1':'一', '2':'二', '3':'三', '4':'四', '5':'五', '6':'六', '7':'日'}/>
				<#assign bgcolor = "#EEEEEE"/>
				<#list printRoomApplieds as printRoom>
					<table style="font-size:12px" width="100%" bordercolor="white" border="1" cellpadding="10">
						<th align="center" style="font-size:18px" colspan="4"><@i18nName systemConfig.school/>已批准教室使用表</th>
						<tr>
							<td bgcolor="${bgcolor}" bordercolor="${bgcolor}" width="15%" style="font-weight:bold">&nbsp;教室名称：</td>
							<td bordercolor="${bgcolor}" width="25%">${printRoom.classroom.name}</td>
							<td bgcolor="${bgcolor}" bordercolor="${bgcolor}" width="15%" style="font-weight:bold">&nbsp;申请人 ID：</td>
							<td bordercolor="${bgcolor}" width="25%">${printRoom.user.name}</td>	     
					    </tr>
						<tr>
							<td bgcolor="${bgcolor}" bordercolor="${bgcolor}" style="font-weight:bold">&nbsp;使用日期：</td>
							<td bordercolor="${bgcolor}">从 ${printRoom.applyTime.dateBegin?string("yyyy-MM-dd")} 到 ${printRoom.applyTime.dateEnd?string("yyyy-MM-dd")}</td>
							<td bgcolor="${bgcolor}" bordercolor="${bgcolor}" style="font-weight:bold">&nbsp;每次时间点：</td>
							<td bordercolor="${bgcolor}">每 ${printRoom.applyTime.cycleCount} ${cycleName[printRoom.applyTime.cycleType?string]}<#if printRoom.applyTime.cycleType?string == "2">&nbsp;${cycleName[printRoom.applyTime.cycleType?string]}${weekName[printRoom.activities?first.time.weekId?default('0')?string]}</#if> ${printRoom.applyTime.timeBegin} ～ ${printRoom.applyTime.timeEnd}</td>
					    </tr>
					    <tr>
							<td bgcolor="${bgcolor}" bordercolor="${bgcolor}" style="font-weight:bold">&nbsp;申请原因：</td>
							<td bordercolor="${bgcolor}" colspan="3">${printRoom.description}</td>
					    </tr>
					</table>
					<br>
					<table align="center" width="100%">
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
				</#list>
	<br>
	<script>
		var bar = new ToolBar("bar"," 打印(A4纸)内容如下：",null,true,true);
		bar.addPrint("<@msg.message key="action.print"/>");
		bar.addClose("<@msg.message key="action.close"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>
