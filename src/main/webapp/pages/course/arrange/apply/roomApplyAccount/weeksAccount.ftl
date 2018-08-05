<table class="listTable">
	<tr>
		<#list weeksAccounts as weekAcc>
			<td class="darkColumn">
				<#assign keys = weekAcc?keys>
				<#list keys as key>${key}</#list>
			</td>
		</#list>
	</tr>
	<tr>
		<#list weeksAccounts as weekAcc>
			<td>
				<#assign keys = weekAcc?keys>
				<#list keys as key>${weekAcc[key]}</#list>
			</td>
		</#list>
	</tr>
</table>