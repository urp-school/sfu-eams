<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/duty/maintainRecordByTeachTask.js"></script>
<#assign cancelClassColor>#CD853F</#assign>
<#assign reStudyColor>#B0C4DE</#assign>
<#assign reExamColor>#6495ED</#assign>
<#assign batchColor>#6495ED</#assign>
<#assign changeColor>#9ACD32</#assign>
<#assign defaultColor>#FFFFFF</#assign>
<#assign presenceColor>#00BFFF</#assign>
<#assign absenteeismColor>#FF69B4</#assign>
<#assign lateColor>#FFA07A</#assign>
<#assign leaveEarlyColor>#FFE4E1</#assign>
<#assign askedForLeaveColor>#FFC0CB</#assign>
<#function dutyStatusColor(courseUnitStatusId)>
 	<#if courseUnitStatusId==1>
 		<#return presenceColor>
 	<#elseif courseUnitStatusId==2>
 		<#return absenteeismColor>
 	<#elseif courseUnitStatusId==3>
 		<#return lateColor>
 	<#elseif courseUnitStatusId==4>
 		<#return leaveEarlyColor>
 	<#elseif courseUnitStatusId==5>
 		<#return askedForLeaveColor>
 	</#if>
</#function>
<BODY  style="overflow-x:auto;" LEFTMARGIN="0" TOPMARGIN="0">
<table class="frameTableStyle">
	<tr>
		<td  align="center" class="infoTitle" style="height:22px;width:200px" class="padding"  style="background-color:${cancelClassColor};" >退课学生</td>
		<td  align="center" class="infoTitle" style="height:22px;width:200px" class="padding"  style="background-color:${reStudyColor};" >重修学生</td>
		<td  align="center" class="infoTitle" style="height:22px;width:300px" class="padding"  style="background-color:${reExamColor};" >免修不免试学生</td>
		<td  align="center" class="infoTitle" style="height:22px;width:100px;background-color:${presenceColor};" class="padding"  >1&nbsp;出勤</td>
		<td  align="center" class="infoTitle" style="height:22px;width:100px;background-color:${absenteeismColor};" class="padding"  >2&nbsp;缺勤</td>
		<td  align="center" class="infoTitle" style="height:22px;width:100px;background-color:${lateColor};" class="padding"  >3&nbsp;迟到</td>
		<td  align="center" class="infoTitle" style="height:22px;width:100px;background-color:${leaveEarlyColor};" class="padding"  >4&nbsp;早退</td>
		<td  align="center" class="infoTitle" style="height:22px;width:100px;background-color:${askedForLeaveColor};" class="padding"  >5&nbsp;请假</td>
		<td  align="center" class="infoTitle" style="height:22px;width:800px;" class="padding"  ><INPUT type=radio name="seriateRadio" id="seriate" value="1" CHECKED onclick="changeSeriateFlag(true);" >连续课输入&nbsp;<INPUT type=radio name="seriateRadio" id="disSeriate" value="0" onclick="changeSeriateFlag(false);">非连续课输入</td>
		<td  style="height:22px;width:1500px"/>
		<td  width="10%" style="height:22px;width:200px;font-size:10pt" class="padding" onclick="onClose()" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)"><img src="${static_base}/images/close.gif" class="iconStyle" alt="<@bean.message key="action.close"/>" />&nbsp;<@bean.message key="action.close"/></td>
		<td  width="10%"/>
	</tr>
</table>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">   
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B>考勤表</B>
    </td>
   </tr>
	<tr>		
		<table onkeydown="KeyNavigation.DoKeyDown(this,event)" align="center" class="listTable" id="recordTable">
		<form name="recordForm" action="inputDutyRecord.do" method="post" onSubmit="return false;">
	   		<tr align="center" class="darkColumn">
	   			<#assign colCount =0 />
				<td width="80px"><nobr><@bean.message key="attr.stdNo"/></nobr><#assign colCount = colCount + 1 /></td>
				<td width="60px"><nobr>学生姓名</nobr><#assign colCount = colCount + 1 /></td>
				<td width="50px" style="background-color:${batchColor};" ><nobr>批量输入</nobr><#assign colCount = colCount + 1 /></td>
				<#list result.dayMap?keys?sort as day>
				<td colspan="<#if ((result.dayMap[day])?size)?default(0)==0>1<#assign colCount = colCount + 1 /><#else>${result.dayMap[day]?size}<#assign colCount = colCount + result.dayMap[day]?size /></#if>" ><nobr>${day}(<#list result.dayMap[day] as cu>${cu.index}<#if cu_has_next>,</#if></#list>)</nobr></td>
				</#list>
			</tr>
			<tr style="background-color:${batchColor};" align="center" class="darkColumn" >
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td width="50px"><input type="text" style="width:100%;border:0 solid #000000;" name="" value="" maxLength="1" size="1" onfocusin="getOldValue(this)" onfocusout="changeAll(this)" /></td>
				<#list result.dayMap?keys?sort as day>					
					<#if (result.dayMap[day]?size)?default(0)==0 >
						<td></td>
					<#else>
					<#list 1..(result.dayMap[day]?size) as i>					
					<td width="50px"><input type="text" style="width:100%;border:0 solid #000000;" name="" value="" maxLength="1" size="1" onfocusin="getOldValue(this)" onfocusout="changeColumns(this)" /></td>
					</#list>
					</#if>
				</#list>
			</tr>
			<#assign stdIds = "" />
			<#list result.teachTask.teachClass.courseTakes?sort_by(["student","code"]) as courseTake>
			<#assign stdIds = stdIds + "," + courseTake.student.id />
			<#if courseTake_index%2==1 ><#assign class="grayStyle" ></#if>
	   		<#if courseTake_index%2==0 ><#assign class="brightStyle" ></#if>
	   		<#if (result.stdCourseUnitStatusMap[courseTake.student.id?string])?exists >
	   		<#assign stdCourseUnitStatusMap = result.stdCourseUnitStatusMap[courseTake.student.id?string] />
	   		<#else>
	   		<#assign stdCourseUnitStatusMap = {} />
	   		</#if>
	   		<#if (!courseTake.courseTakeType?exists)||(courseTake.courseTakeType.id==reStudy)||(courseTake.courseTakeType.id==reExam) >
	   		<tr align="center" class="${class}" align="center" <#if (!courseTake.courseTakeType?exists) > style="background-color:${cancelClassColor};"<#elseif (courseTake.courseTakeType.id==reStudy)>style="background-color:${reStudyColor};"<#elseif (courseTake.courseTakeType.id==reExam) >style="background-color:${reExamColor};"</#if> >
				<td>&nbsp;${courseTake.student?if_exists.code}</td>
				<td>&nbsp;<nobr><@i18nName courseTake.student?if_exists/></nobr></td>
				<td width="50px" style="background-color:${batchColor};" ><input type="text" style="width:100%;border:0 solid #000000;" name="" value="" maxLength="1" size="1" onfocusin="getOldValue(this)" onfocusout="changeRows(this)" /></td>
				<#list result.dayMap?keys?sort as day>
					<#assign courseUnitStatusMap = (stdCourseUnitStatusMap[day])?default({}) />
					<#if (result.dayMap[day]?size)?default(0)==0 >
						<td></td>
					<#else>
					<#list (result.dayMap[day]) as courseUnit>
					<#if (courseUnitStatusMap[(courseUnit.id?string)?default("-1")+"-"+(courseUnit.id?string)?default("-1")])?exists >
						<td width="50px" style="background-color:${dutyStatusColor((courseUnitStatusMap[(courseUnit.id?string)?default('-1')+'-'+(courseUnit.id?string)?default('-1')])?if_exists.id?if_exists)};"><input type="text" style="width:100%;border:0 solid #000000;background-color:${dutyStatusColor((courseUnitStatusMap[(courseUnit.id?string)?default('-1')+'-'+(courseUnit.id?string)?default('-1')])?if_exists.id?if_exists)};" name=",${courseTake.student.id},${day},${courseUnit.id}," value="${(courseUnitStatusMap[(courseUnit.id?string)?default('-1')+'-'+(courseUnit.id?string)?default('-1')])?if_exists.id?if_exists}" maxLength="1" size="1" onfocusin="getOldValue(this)" onfocusout="changeBatch(this)" /></td>
					<#else>
						<td width="50px"><input type="text" style="width:100%;border:0 solid #000000;" name=",${courseTake.student.id},${day},${courseUnit.id}," maxLength="1" size="1" onfocusin="getOldValue(this)" onfocusout="changeBatch(this)" /></td>
					</#if>
					</#list>
					</#if>
				</#list>
			</tr>
	   		<#else>
			<tr align="center" class="${class}"  align="center" >
				<td>&nbsp;${courseTake.student?if_exists.code}</td>
				<td>&nbsp;<nobr><@i18nName courseTake.student?if_exists/></nobr></td>
				<td width="50px" style="background-color:${batchColor};"><input type="text" style="width:100%;border:0 solid #000000;" name="" value="" maxLength="1" size="1" onfocusin="getOldValue(this)" onfocusout="changeRows(this)" /></td>
				<#list result.dayMap?keys?sort as day>
					<#assign courseUnitStatusMap = (stdCourseUnitStatusMap[day])?default({}) />
					<#if (result.dayMap[day]?size)?default(0)==0 >
						<td></td>
					<#else>
					<#list (result.dayMap[day]) as courseUnit>
					<#if (courseUnitStatusMap[(courseUnit.id?string+"-"+courseUnit.id?string)?default(-1)])?exists >
						<td width="50px" style="background-color:${dutyStatusColor((courseUnitStatusMap[(courseUnit.id?string)?default('-1')+'-'+(courseUnit.id?string)?default('-1')])?if_exists.id?if_exists)};"><input type="text" style="width:100%;border:0 solid #000000;background-color:${dutyStatusColor((courseUnitStatusMap[(courseUnit.id?string)?default('-1')+'-'+(courseUnit.id?string)?default('-1')])?if_exists.id?if_exists)};" name=",${courseTake.student.id},${day},${courseUnit.id}," value="${(courseUnitStatusMap[(courseUnit.id?string)?default('-1')+'-'+(courseUnit.id?string)?default('-1')])?if_exists.id?if_exists}" maxLength="1" size="1" onfocusin="getOldValue(this)" onfocusout="changeBatch(this)" /></td>
					<#else>
						<td width="50px"><input type="text" style="width:100%;border:0 solid #000000;" name=",${courseTake.student.id},${day},${courseUnit.id}," maxLength="1" size="1" onfocusin="getOldValue(this)" onfocusout="changeBatch(this)" /></td>
					</#if>						
					</#list>
					</#if>
				</#list>
			</tr>
			</#if>
			</#list>
			<#assign stdIds = stdIds + "," />
			<tr align="center" class="darkColumn">
				<td align="center" colspan="${colCount}" >
				<input type="button" onClick="updateOrSave()" value="<@bean.message key="system.button.submit" />" name="button1" class="buttonStyle" />&nbsp;
	    		<input type="button"  onClick="onReset()" value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
	    		<input type="hidden" name="method" value="doMaintainRecordByTeachTask" />
	    		<input type="hidden" name="teachTaskId" value="${result.teachTask.id}" />
	    		<input type="hidden" name="ids" value="${stdIds}" />
	    		<input type="hidden" name="dateAndCourseUnitString" value="${result.dateAndCourseUnitString}" />
	    		<input type="hidden" name="changeRecordString" value="" />
	    		</td>
			</tr>
			</form>
			</table>			
   </tr>
   
  </table>
  
 </body>
 <script>
	var changeColor="${changeColor}";
	var presenceColor="${presenceColor}";
	var absenteeismColor="${absenteeismColor}";
	var lateColor="${lateColor}";
	var leaveEarlyColor="${leaveEarlyColor}";
	var askedForLeaveColor="${askedForLeaveColor}";
	var defaultColor="${defaultColor}";
	var deletion="<@bean.message key="prompt.deletion"/>";
	var selectPlease="<@bean.message key="common.selectPlease" />";
	var saveDeletion="<@bean.message key="prompt.saveDeletion"/>";
	var seriateFlag=true;
 </script>
<#include "/templates/foot.ftl"/>