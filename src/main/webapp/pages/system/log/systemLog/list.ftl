<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table sortable="true" id="sysLog" width="100%" style="word-break: break-all">
		<@table.thead>
			<@table.selectAllTd id="sysLogId"/>
			<@table.sortTd width="9%" text="日志编号" id="sysLog.id"/>
			<@table.sortTd width="12%" text="操作人员" id="sysLog.user.userName"/>
			<@table.sortTd width="10%" text="操作时间" id="sysLog.time"/>
			<@table.sortTd width="10%" text="访问资源" id="sysLog.URI"/>
			<@table.sortTd width="40%" text="内容" id="sysLog.operation"/>
		</@>
		<@table.tbody datas=sysLogs;sysLog>
			<@table.selectTd id="sysLogId" value=sysLog.id/>
			<td><a href="systemLog.do?method=info&sysLogId=${sysLog.id}">${sysLog.id}</a></td>
			<td><#if (sysLog.user.userName)?exists><a href="#" onclick="userInfo('${sysLog.id}')">${sysLog.user.userName}</a><input type="hidden" id="user_${sysLog.id}" value="${sysLog.user.id}"/></#if></td>
			<td>${sysLog.time?string('yyyy-MM-dd')}</td>
			<td title="${(sysLog.URI)?if_exists?html}" nowrap><span style="display:block;width:100px;overflow:hidden;text-overflow:ellipsis;">${(sysLog.URI)?if_exists}</span></td>
			<td title="${(sysLog.operation)?if_exists?replace("<br>", "&#13;")}" nowrap><span style="display:block;width:350px;overflow:hidden;text-overflow:ellipsis;">${(sysLog.operation)?if_exists?replace("<br>", "&nbsp;")}</span></td>
		</@>
	</@>
	<@htm.actionForm name="actionForm" action="systemLog.do" entity="sysLog"/>
	<script>
		var bar = new ToolBar("bar", "系统日志列表", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.info"/>", "info()", "detail.gif");
		bar.addItem("<@msg.message key="action.delete"/>", "remove()");
		bar.addItem("<@msg.message key="common.all"/><@msg.message key="action.delete"/>", "removeAll()");
		bar.addItem("<@msg.message key="action.export"/>", "exportData()");
		
		var form = document.actionForm;
		
		<#--查看用户信息-->
		function userInfo(sysLogId) {
			if ((sysLogId == null || sysLogId == "") && ((sysLogId = getSelectId("sysLogId")) == null || sysLogId == "")) {
				alert("请选择要查看用户的日志记录");
			} else if (sysLogId.lastIndexOf(',') >= 0) {
				alert("请选择一条你要查看用户的日志记录！");
			} else {
				form.action = "user.do?method=info&user.id=" + document.getElementById("user_" + sysLogId).value;
				form.submit();
			}
		}
		
		
		<#--清空数据表中的所有日志记录-->
		function removeAll() {
			form.action = "systemLog.do?method=removeAll";
			form.submit();
		}
		
		<#--系统日志导出-->
		function exportData() {
			form.action = "systemLog.do?method=export";
			addInput(form, "keys", "id,operation,user.userName,time,URI,params,host");
			addInput(form, "titles", "系统日志,日志内容/操作,操作人员,操作时间,访问资源路径(URI),操作参数,主机/服务器");
			addHiddens(form, queryStr);
			form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>