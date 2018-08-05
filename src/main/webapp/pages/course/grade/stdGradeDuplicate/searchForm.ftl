<table width="100%">
	<tr>
		<td align="left" valign="bottom"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>详细查询(模糊输入)</B></td>
	</tr>
	<tr>
	    <td colspan="8" style="font-size:0px">
			<img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
		</td>
	</tr>
</table>
<table width="100%" onkeypress="DWRUtil.onReturn(event, search)">
	<input type="hidden" name="pageNo" value="1"/>
	<tr>
		<td width="40%"><@bean.message key="attr.stdNo"/>:</td>
	    <td><input type="text" name="courseGrade.std.code" maxlength="32" size="10" value="${RequestParameters['student.code']?if_exists}" style="width:100px"/></td>
	</tr>
    <tr>
		<td><@msg.message key="attr.personName"/>:</td>
		<td><input type="text" name="courseGrade.std.name" maxlength="20" size="10" value="${RequestParameters['student.name']?if_exists}" style="width:100px"/></td>
	</tr>
	<tr>
	    <td>就读年级:</td>
	    <td><input type="text" name="courseGrade.std.enrollYear" maxlength="7" id='student.enrollYear' style="width:100px;"></td>
	</tr>
	<tr>
	   	<td><@msg.message key="std.adminClass.baseInfo.name"/>:</td>
	   	<td><input type="text" name="stdAdminClass" value="" maxlength="50" style="width:100px"/></td>
    </tr>
    <input type="hidden" name="courseGrade.course.extInfo.courseType.isPractice" value="1"/>
    <tr>
	    <td><@bean.message key="entity.studentType"/>:</td>
	    <td>
			<select id="stdTypeOfSpeciality" name="courseGrade.std.type.id" style="width:100px;">
				<option value="${RequestParameters['student.type.id']?if_exists}"><@bean.message key="filed.choose"/></option>
			</select>
		</td>
	</tr>
	<tr>
		<td><@bean.message key="common.college"/>:</td>
		<td>
			<select id="department" name="department.id"  style="width:100px;">
				<option value=""><@bean.message key="filed.choose"/>...</option>
			</select>
		</td>
	</tr>
	<tr>
		<td><@bean.message key="entity.speciality"/>:</td>
		<td>
			<select id="speciality" name="speciality.id" style="width:100px;">
				<option value=""><@bean.message key="filed.choose"/>...</option>
			</select>
		</td>
	</tr>
	<tr>
		<td><@bean.message key="entity.specialityAspect"/>:</td>
		<td>
			<select id="specialityAspect" name="specialityAspect.id" style="width:100px;">
				<option value=""><@bean.message key="filed.choose"/>...</option>
			</select>
		</td>
	</tr>
	<tr>
		<td style="width:60px;"><@bean.message key="attr.year2year"/>:</td>
		<td style="width:100px;">
			<select id="year" name="courseGrade.calendar.year" style="width:100px;">
				<option value=""></option>
			</select>
		</td>
	</tr>
	<tr>
	    <td style="width:50px;"><@bean.message key="attr.term"/>:</td>
	    <td style="width:50px;">
			<select id="term" name="courseGrade.calendar.term" style="width:100px;">
				<option value=""></option>
			</select>
	   </td>
	</tr>
	<tr>
		<td><@msg.message key="attr.taskNo"/>:</td>
		<td>
			<input type="text" name="courseGrade.taskSeqNo" maxlength="32" value="" style="width:100px;"/>
		</td>
	</tr>
	<tr>
		<td><@msg.message key="attr.courseNo"/>:</td>
		<td>
			<input type="text" name="courseGrade.course.code" maxlength="32" value="" style="width:100px;"/>
		</td>
	</tr>
	<tr>
		<td><@msg.message key="attr.courseName"/>:</td>
		<td>
			<input type="text" name="courseGrade.course.name" maxlength="20" value="" style="width:100px;"/>
		</td>
	</tr>
	<tr>
		<td><@msg.message key="entity.courseType"/>:</td>
		<td><select name="courseGrade.courseType.name" style="width:100px" value=" value="${RequestParameters["task.courseType.id"]?if_exists}"">
                <option value=""><@bean.message key="common.all"/></option>
                <#list sort_byI18nName(courseTypes) as courseType>
                    <#if (courseType.isPractice)?default(false)>
                <option value=${courseType.id}><@i18nName courseType/></option>
                    </#if>
                </#list>
             </select></td>
	</tr>
	<tr>
		<td>状态:</td>
		<td>
			<select name="courseGrade.status" style="width:100px;">
				<option value="">全部</option>
				<option value="0"><@msg.message key="action.new"/></option>
				<option value="1">录入确认</option>
				<option value="2">已发布</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>分数范围:</td>
		<td><input name="scoreFrom" value="" maxlength="3" style="width:40px"/>-<input name="scoreTo" maxlength="3" value="" style="width:40px"/></td>
	</tr>
	<tr>
		<td>是否通过:</td>
		<td>
			<select name="isPass" id="isPass" style="width:100px;">
		        <option value="">全部</option>
		        <option value="1">通过</option>
		        <option value="0">未通过</option>
		        <option value="3">一直未通过</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>专业类别:</td>
		<td>
			<select name="courseGrade.majorType.id" onchange="changeSpecialityType(event)" style="width:100px">
		        <option value="1">第一专业</option>
		        <option value="2">第二专业</option>
			</select>
		</td>
	</tr>
	<tr align="center">
		<td colspan="2">
			<button onClick="search(1)" style="width:60px"><@bean.message key="action.query"/></button>
		</td>
	</tr>
</table> 
<#include "/templates/stdTypeDepart3Select.ftl"/>
<script src='dwr/interface/calendarDAO.js'></script>
<script src='scripts/common/CalendarSelect.js'></script>
<script>
    var stdTypeArray = new Array();
    <#list stdTypeList as stdType>
    stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    </#list>
    var dd = new CalendarSelect("stdTypeOfSpeciality","year","term",false,true,false);
    dd.init(stdTypeArray);
    sds.firstSpeciality=1;
    
    function changeSpecialityType(event){
       var select = getEventTarget(event);
       sds.firstSpeciality=select.value;
       fireChange($("department"));
    }
</script> 