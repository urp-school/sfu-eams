<#include "/templates/head.ftl"/>
<body leftmargin="0" topmargin="0">
	<table width="90%" align="center" class="listTable">
	<form name="listForm" method="post" action="studentDutyRecord.do" onSubmit="return false;" target="dutyFrame">
		<tr align="center" class="darkColumn">
			<div style="position:relative; visibility: hidden; display:none;">
				<select id="stdType" name="calendar.studentType.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
					<option value="${result.student.type.id}"></option>
				</select>
			</div>
			<td align="left" width="30%">
				&nbsp;<@msg.message key="attr.year2year"/>:
				<select id="year" name="calendar.year"  style="width:100px;">
					<option value="${RequestParameters['calendar.year']?if_exists}"></option>
				</select>
			</td>
			<td align="left" width="30%">
				&nbsp;<@msg.message key="attr.term"/>:
				<select id="term" name="calendar.term" style="width:100px;">
		            <option value="${RequestParameters['calendar.term']?if_exists}"></option>
		        </select>
	        </td>
	        <td align="left">
	        	&nbsp;<input type="button" onClick="dutySearch()" value="<@bean.message key="system.button.submit" />" name="button1" class="buttonStyle" />
	        	<input type="hidden" value="studentRecord" name="method"/>
	        	<input type="hidden" value="${result.student.id}" name="stdId"/>
	        </td>
		</tr>
     </form>
		<#assign stdTypeList = [ result.student.type ] />
<script>
	DWREngine.setAsync(false);
</script>
        <#include "/templates/calendarSelect.ftl"/>
     </table>  
     <table border="0" align="center" width="100%"> 
		<tr align="center">
			<td colspan="3" valign="top">
				<iframe id="dutyFrame" name="dutyFrame" src="#" width="100%" height="100%" frameborder="0" scrolling="no">
				</iframe>
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
</body>