<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">

	<table width="90%" align="center">
		<tr>
			<td align="center" class="contentTableTitleTextStyle" bgcolor="#ffffff"><B><@i18nName result.student?if_exists/><@bean.message key="info.duty.manageDutyRecordList"/></B></td>
		</tr>
	</table>
	<form name="listForm" method="post" action="dutyRecordSearch.do" onSubmit="return false;" target="dutyFrame">
		<table width="90%" align="center" class="listTable">
		<tr align="center" class="darkColumn">
			<div style="position:relative; visibility: hidden; display:none;">
				<select id="stdType" name="calendar.studentType.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
					<option value="${result.student.type.id}"></option>
				</select>
			</div>
			<td align="left" width="30%">
				&nbsp;学年度:
				<select id="year" name="calendar.year"  style="width:100px;" >
					<option value="${RequestParameters['calendar.year']?if_exists}"><#--><@bean.message key="filed.choose" />--></option>
				</select>
			</td>
			<td align="left" width="30%">
				&nbsp;学期:
				<select id="term" name="calendar.term" >
		            <option value="${RequestParameters['calendar.term']?if_exists}"><#--><@bean.message key="filed.choose" />--></option>
		        </select>
	        </td>
	        <td align="left">
	        	&nbsp;<input type="button" onClick="dutySearch()" value="<@bean.message key="system.button.submit" />" name="button1" class="buttonStyle" />
	        	<input type="hidden" value="manageByStudent" name="method" />
	        	<input type="hidden" value="${result.student.id}" name="stdId" />
<script>
DWREngine.setAsync(false);
</script>
	        </td>
		</tr>
		</table>
		</form>
		<#assign stdTypeList = [ result.student.type ] />
		<#include "/templates/calendarSelect.ftl"/>
		<table width="100%" align="center" class="frameTableStyle">
		<tr align="center">
			<td colspan="3">
				<iframe id="dutyFrame" name="dutyFrame" src="#" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
			</td>
		</tr>	
		</table>
	
<script>
DWREngine.setAsync(true);
function dutySearch(){
	document.listForm.submit();
}
dutySearch();
</script>
<#include "/pages/duty/dutyRuleInf.ftl"/>
</BODY>