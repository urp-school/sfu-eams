<table class="listTable">
	<tr>
		<#list activitiesAccounts as activityAcc>
			<td class="darkColumn">
				<#assign keys = activityAcc?keys>
				<#list keys as key>${key}</#list>
			</td>
		</#list>
	</tr>
	<tr>
		<#list activitiesAccounts as activityAcc>
			<td>
				<#assign keys = activityAcc?keys>
				<#list keys as key>${activityAcc[key]}</#list>
			</td>
		</#list>
	</tr>
</table>