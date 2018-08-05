		<table style="width:100%" cellspacing="0" cellpadding="0">
			<tr valign="top">
				<td align="right">
					<#assign gradePerColumn=(setting.pageSize/2)?int>
					<#assign style>style="font-size:${setting.fontSize}px" width="95%"</#assign>
					<#include "reportContent.ftl"/>
				</td>
			</tr>
		</table>
