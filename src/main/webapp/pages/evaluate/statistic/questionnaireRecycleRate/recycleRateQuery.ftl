<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','<@bean.message key="field.recycleRate.queryRate"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.print"/>","displayFrame.printCollege()");
</script>
  <table width="100%"  class="frameTable">
   		<tr>
   			<td width="20%" class="frameTable_view" valign="top">
   				<table width="100%" class="searchTable">
   					<form name="evaluateForm" method="post" target="displayFrame" action="" onsubmit="return false;">
   					<input type="hidden" name="query" value="query">
   					<input type="hidden" id="parameters" name="parameters" value="">
   					<tr>
						<td  style="width:50%"><@msg.message key="entity.studentType"/></td>
						<td >
							<select id="stdType" name="stdTypeId" style="width:120px">
							<#if teachCalendar?exists>
								<option value="${teachCalendar.studentType.id}" selected>${teachCalendar.studentType.name}</option>
							</#if>
							</select>
						</td>
					</tr>
					<tr>
						<td>学年度</td>
						<td >
							<select id="year" name="year" style="width:120px">
							<#if teachCalendar?exists>
								<option value="${teachCalendar.year}" selected>${teachCalendar.year}</option>
							</#if>
							</select>
						</td>
					</tr>
					<tr>
						<td>学期</td>
						<td >
							<select id="term" name="term" style="width:120px">
								<#if teachCalendar?exists>
									<option value="${teachCalendar.term}" selected>${teachCalendar.term}</option>
								</#if>
							</select>
						</td>
					</tr>
					<tr>
						<td>上课院系</td>
						<td>
							<@htm.i18nSelect datas=departmentList name="departmentId" selected="" style="width:120px">
							<option value=""><@bean.message key="common.all"/></option>
							</@>
						</td>
					</tr>
					<tr><td align="center" colspan="2">
							<button name="queryButton" class="buttonStyle" onClick="doQuery(null)"><@bean.message key="system.button.query"/></button>
						</td>
					</tr>
    			<#include "/templates/calendarSelect.ftl"/>
    			</form>
   				</table>
   			</td>
   			<td valign="top">
   				<iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
   			</td>
   		</tr>
   </table>
   <script>
   	var form = document.evaluateForm;
   	function doQuery(id){
   		var url="questionnaireRecycleRateAction.do?method=doQuery";
   		if(null!=id){
   			url+="&teachCalendarId="+id;
   		}
   		form.action=url;
   		form.submit();
   	}
   	doQuery(${teachCalendar.id});
   </script>
  </body>
<#include "/templates/foot.ftl"/>