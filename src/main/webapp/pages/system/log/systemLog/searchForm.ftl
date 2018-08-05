<table width="99%" cellpadding="0" cellspacing="1"> 
	<form name="searchSystemLogForm" action="systemLog.do?method=search" method="post" onsubmit="return false;">
		<tr>
			<td align="right">操作内容:</td>
			<td width="70%"><input type="text" name="sysLog.operation" style="width:100%" maxlength="100" value="${RequestParameters['sysLog.operation']?default('')}"/></td>
		</tr>
		<tr>
			<td align="right">用户名:</td>
			<td><input type="text" name="sysLog.user.userName" maxlength="50" style="width:100%" value="${RequestParameters['sysLog.user.userName']?default('')}"/></td>
		</tr>
		<tr>
			<td align="right">URI:</td>
			<td><input type="text" name="sysLog.URI" style="width:100%" maxlength="200" value="${RequestParameters['sysLog.URI']?default('')}"/></td>
		</tr>
		<tr>
			<td align="right">操作参数:</td>
			<td><input type="text" name="sysLog.params" style="width:100%" maxlength="400" value="${RequestParameters['sysLog.params']?default('')}"/></td>
		</tr>
		<tr>
			<td align="right">主机:</td>
			<td><input type="text" name="sysLog.host" maxlength="100" style="width:100%" value="${RequestParameters['sysLog.host']?default('')}"/></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<fieldSet align=center> 
				 	<legend>日志时间</legend>
						从：<input type="text" name="logBeginDate" onfocus="calendar()" maxlength="10" value="${(RequestParameters['logBeginDate']?string('yyyy-MM-dd'))?default('')}" style="width:150"/><br>
						到：<input type="text" name="logEndDate" onfocus="calendar()" maxlength="10" value="${(RequestParameters['logEndDate']?string('yyyy-MM-dd'))?default('')}" style="width:150"/>
					</legend>
				</fieldSet>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button onclick="search()">查询</button>
				<button onclick="this.form.reset()">重置</button>
			</td>
		</tr>
	</form>
</table>
