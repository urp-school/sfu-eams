<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script language="JavaScript" src="<@bean.message key="menu.js.url"/>"></script>
<body leftmargin="0" topmargin="0" >
	<table id="bar"></table>
	<table cellpadding="0" cellspacing="0" width="100%" border="0">
		<tr>
    		<td><br></td>
   		</tr>
   		<tr>
    		<td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     			<B><@bean.message key="info.duty.record"/></B>
    		</td>
   		</tr>
     	<tr>
   	 		<table width="90%" align="center" class="listTable">
	   			<tr align="center" class="darkColumn">
			     	<td width="10%"><@bean.message key="attr.courseNo"/></td>
			     	<td width="20%"><@bean.message key="attr.courseName"/></td>
			     	<td width="14%"><@bean.message key="entity.courseType"/></td>
			     	<td width="11%"><@bean.message key="attr.year2year"/></td>
			     	<td width="10%"><@bean.message key="attr.term"/></td>
			     	<td width="10%"><@bean.message key="entity.teacher"/></td>
			     	<td width="19%"><@bean.message key="entity.teachClass"/></td>	     
	   			</tr>
	   			<#assign teachTask >${result.teachTask}</#assign>	      
			   	<tr class="brightStyle">
				    <td>&nbsp;${ result.teachTask.course.code?if_exists}</td>
				    <td>&nbsp;<@i18nName result.teachTask.course?if_exists/></td>
				    <td>&nbsp;<@i18nName result.teachTask.courseType?if_exists/></td>
				    <td>&nbsp;${result.teachTask.calendar.year}</td>
				    <td>&nbsp;${result.teachTask.calendar.term}</td>
				    <td>&nbsp;<@getTeacherNames result.teachTask.arrangeInfo.teachers?if_exists/><#--<@eraseComma teachTask.arrangeInfo.teacherNames?if_exists/>--></td>
				    <td>&nbsp;<@i18nName result.teachTask.teachClass?if_exists/></td>	    
			   	</tr>
     		</table>
   		</tr>
   		<tr>
		   	<td>
		   	&nbsp;
		   	</td>
   		</tr>
   		<tr>
    		<td>
     			<table width="90%" align="center" class="listTable">
       			<form name="listForm"action="" onSubmit="return false;">
			   	<tr align="center" class="darkColumn">
			     	<td align="center" width="5%"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('ids'),event);"></td>
			     	<td><@bean.message key="attr.stdNo"/></td>
			     	<td><@bean.message key="field.feeDetail.studentName"/></td>			     	
			     	<td>考勤</td>
			     	<td>出勤</td>
			     	<td>缺勤</td>
			     	<td>迟到</td>
			     	<td>早退</td>
			     	<td>请假</td>
			     	<td><@bean.message key="attr.graduate.attendClassRate"/></td>
			     	<td>缺勤率</td>
			     	<td>修读类别</td>
			   	</tr>	   
			   	<#list (result.recordList?sort_by(["student","code"]))?if_exists as record>
			   	<#if record_index%2==1 ><#assign class="grayStyle" ></#if>
			   	<#if record_index%2==0 ><#assign class="brightStyle" ></#if>
			   	<tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
				    <td align="center"><input type="checkBox" name="ids" value="${record.student.id}"></td>
				    <td>&nbsp;${record.student.code}</td>
				    <td>&nbsp;<@i18nName record.student?if_exists/></td>
				    <td>&nbsp;${record.totalCount?default(0)}</td>
				    <td>&nbsp;${record.dutyCount?default(0)}</td>
				    <td>&nbsp;${record.absenteeismCount?default(0)}</td>
				    <td>&nbsp;${record.lateCount?default(0)}</td>
				    <td>&nbsp;${record.leaveEarlyCount?default(0)}</td>
				    <td>&nbsp;${record.askedForLeaveCount?default(0)}</td>
				    <td>&nbsp;${record.dutyRatio?default(0)?string.percent}</td>
				    <td>&nbsp;${record.absenteeismRatio?default(0)?string.percent}</td>
				    <td>&nbsp;<@i18nName record.getCourseTakeType(false) /></td>
			   	</tr>
			   	</#list>
	   			</form>
     			</table>
    		</td>
   		</tr>
  	</table>
  	<script>
  		var bar = new ToolBar("bar", "学生考勤记录", null, true, true);
  		bar.setMessage('<@getMessage/>');
  		bar.addPrint("<@msg.message key="action.print"/>");
  		bar.addClose("<@msg.message key="action.close"/>");
  	</script>
</body>
<#include "/templates/foot.ftl"/>