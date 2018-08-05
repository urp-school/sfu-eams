<#assign extraSearchTR>
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
    <tr>
       <td class="infoTitle"  >是否排完:</td>
       <td class="infoTitle">
         <select name="task.arrangeInfo.isArrangeComplete" style="width:60px">
             <option value="1" selected><@bean.message key="common.alreadyArranged"/></option>
             <option value="0"><@bean.message key="common.notArranged"/></option>
             <option value=""><@bean.message key="common.all"/></option>         
             
         </select>
       </td>
    </tr>
    <tr>
       <td class="infoTitle"  >允许退课:</td>
       <td class="infoTitle">
         <select name="task.electInfo.isCancelable" style="width:60px">
             <option value="" selected><@bean.message key="common.all"/></option>
             <option value="1"><@bean.message key="common.yes"/></option>
             <option value="0"><@bean.message key="common.no"/></option>
         </select>
       </td>
    </tr>
    <tr>
	   <td>计划人数是否上限:</td>
	     <td>
	        <select name="task.teachClass.isUpperLimit" value="${RequestParameters["task.teachClass.isUpperLimit"]?if_exists}" style="width:100px">
	           <option value=""><@bean.message key="common.all"/></option>
	           <option value="1"><@bean.message key="common.yes"/></option>
	           <option value="0"><@bean.message key="common.no"/></option>
	        </select>
	     </td>
	</tr>
</#assign>
<#include "/pages/course/taskBasicSearchForm.ftl"/> 