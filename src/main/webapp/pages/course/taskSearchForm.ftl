<#assign extraSearchTR>
	<tr>
	     <td class="infoTitle"><@msg.message key="course.week"/>:</td>
	     <td>
		     <select name="courseActivity.time.week" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list weeks as week>
		     	<option value=${week.id}><@i18nName week/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@msg.message key="course.unit"/>:</td>
	     <td>
		     <select name="courseActivity.time.startUnit" value="" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list 1..14 as unit>
		     	<option value=${unit}>${unit}</option>
		     	</#list>
		     </select>
	     </td>
	</tr>
    <tr>
     <td class="infoTitle" style="width:100%"><@msg.message key="course.affirmState"/>:</td>
     <td>
        <select name="task.isConfirm" style="width:100px">
           <option value=""><@bean.message key="common.all"/></option>
           <option value="1"><@bean.message key="action.affirm"/></option>
           <option value="0"><@msg.message key="action.negate"/></option>
        </select>
     </td>
    </tr>
    <tr>
     <td class="infoTitle"><@msg.message key="course.arrangeState"/>:</td>
     <td>
        <select name="task.arrangeInfo.isArrangeComplete" style="width:100px">
           <option value=""><@bean.message key="common.all"/></option>
           <option value="1"><@msg.message key="course.beenArranged"/></option>
           <option value="0"><@msg.message key="course.noArrange"/></option>
        </select>
     </td>
    </tr>
    <tr>
    	<td><@msg.message key="entity.schoolDistrict"/>:</td>
    	<td><@htm.i18nSelect datas=schoolDistricts selected="" name="task.arrangeInfo.schoolDistrict.id" style="width:100%"><option value=""><@msg.message key="common.all"/></option></@></td>
    </tr>
    <tr>
	    <td>学生性别:</td>
     	<td>
        	<select name="task.teachClass.gender.id" style="width:100px">      
        		<option value="">请选择...</option>
        		<#list sort_byI18nName(genderList) as gender>
   	           			<option value="${gender.id}" <#if (task.teachClass.gender.id)?exists && (gender.id)?if_exists == (task.teachClass.gender.id)?if_exists> selected </#if>><@i18nName gender/></option>
        		</#list>
        	</select>
     	</td>
    </tr>
    <tr>
    	<td>计划人数:</td>
    	<td><input type="text" name="planStdCountStart" value="" maxlength="3" style="width:30px"/>&nbsp;-&nbsp;<input type="text" name="planStdCountEnd" value="" maxlength="3" style="width:30px"/></td>
    </tr>
    <tr>
    	<td><@msg.message key="course.factNumberOf"/>:</td>
    	<td><input type="text" name="stdCountStart" value="" maxlength="3" style="width:30px"/>&nbsp;-&nbsp;<input type="text" name="stdCountEnd" value="" maxlength="3" style="width:30px"/></td>
    </tr>
</#assign>
<#include "/pages/course/taskBasicSearchForm.ftl"/> 