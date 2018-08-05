<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="frameTable">
		<tr valign="top">
			<td class="frameTable_view" width="24%">
				<table width="100%">
					<tr class="infoTitle"  valign="top">
						<td class="infoTitle" align="left" valign="bottom" colspan="2">
							<img src="${static_base}/images/action/info.gif" align="top"/>
							<b>系统日志查询</b>
						</td>
					</tr>
					<tr>
						<td style="font-size:0px" colspan="2">
							<img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
						</td>
					</tr>
				</table>
				<#include "searchForm.ftl"/>
			</td>
			<td>
				<iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="auto"></iframe>
			</td>
		</tr>
	</table>
	<script>
		
		var bar = new ToolBar("bar", "系统日志", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.query"/>", "search()", "find.gif");
		
		var sysLogForm = document.searchSystemLogForm;
		
		sysLogForm['logBeginDate'].value = "<#if logDefaultDate?exists>${logDefaultDate?string('yyyy-MM-dd')}<#else>${RequestParameters['logBeginDate']?string('yyyy-MM-dd')}</#if>";
		sysLogForm['logEndDate'].value = "<#if logDefaultDate?exists>${logDefaultDate?string('yyyy-MM-dd')}<#else>${RequestParameters['logEndDate']?string('yyyy-MM-dd')}</#if>";
		
		search();
		
		<#--查询日志-->
		function search() {
			var beginDate, endDate;
			if ((beginDate = sysLogForm['logBeginDate'].value) != null && beginDate != "" && (endDate = sysLogForm['logEndDate'].value) != null && endDate != "") {
				if (beginDate > endDate) {
					alert("开始时间不能超过结束时间！");
					return;
				}
			}
			sysLogForm.target = "displayFrame";
			sysLogForm.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>