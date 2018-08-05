<#include "/templates/head.ftl"/>
<script>
function stat(form,propertyName){
	form.propertyName.value=propertyName;
	form.action="tutorStatistic.do?method=doStatisticTutorProperty";
	form.submit();
}
function statSpeciality(form){
	form.action="tutorStatistic.do?method=doStatisticSpecialityOfTutor";
	form.submit();
}
function  statTeacherTypeAge(form){
	form.action="tutorStatistic.do?method=doStatisticByAge";
	form.submit();
}
</script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','导师信息统计',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
   <table width="100%" class="frameTable"> 
		<tr>
			<td width="20%" class="frameTable_view" valign="top">
				<table width="100%" class="searchTable">
  				<form name="conditionForm" action="#" target="tutorStatisticFrame" method="post" action="" onsubmit="return false;">
  					<input type="hidden" name="propertyName" value="">
  					<tr>
  						<td align="center">查询方式</td>
  					</tr>
  					<tr>
  						<td class="padding" onclick="stat(document.conditionForm,'gender')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
								&nbsp;&nbsp;<image src="${static_base}/images/action/detail.gif"> 按性别
         				</td>
  					</tr>
  					<tr>
  						<td class="padding" onclick="statSpeciality(document.conditionForm)"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
								&nbsp;&nbsp;<image src="${static_base}/images/action/detail.gif"> 按专业职称
         				</td>
  					</tr>
  					<tr>
  						<td class="padding" onclick="statTeacherTypeAge(document.conditionForm)"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
								&nbsp;&nbsp;<image src="${static_base}/images/action/detail.gif"> 按职称年龄
         				</td>
  					</tr>
				</form>
				</table>
		  </td>
		  <td valign="top">
		  <iframe id="tutorStatisticFrame" name="tutorStatisticFrame" 
		 marginwidth="0" marginheight="0" scrolling="no"
		 frameborder="0" height="100%" width="100%">
		  </iframe>
  		</td>
  		</tr>
 </table>
 <script>
 stat(document.conditionForm,'gender');
 </script>
</body>
<#include "/templates/foot.ftl"/>