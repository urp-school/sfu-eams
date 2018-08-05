<#if  calendar.displayTimeDetail>
	<tr style="font-size:11px">
    <td width="6%" class="darkColumn"><@msg.message key="task.courseTable.time"/></td>
	<#list calendar.timeSetting.courseUnits?sort_by("index") as courseUnit>
	  <td  class="timeSeq${courseUnit.segNo}" width="7%">${courseUnit.startTime?string?left_pad(4,"0")[0..1]}:${courseUnit.startTime?string?left_pad(4,"0")[2..3]}-${courseUnit.finishTime?string?left_pad(4,"0")[0..1]}:${courseUnit.finishTime?string?left_pad(4,"0")[2..3]}</td>
	 </#list>
	</tr>
</#if>