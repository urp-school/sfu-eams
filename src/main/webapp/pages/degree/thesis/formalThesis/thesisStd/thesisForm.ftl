<table width="100%" class="listTable" align="center">
 <form name="listForm" method="post" action="" onsubmit="return false;">
  <tr>
  	<td colspan="7" align="center" class="darkColumn">论文内容</td>
  </tr>
  <#if !display>
  <tr>
    <td colspan="2" class="grayStyle">论文开题题目</td>
    <td colspan="5">${(thesisManage.topicOpen.topicOpenName)?if_exists}</td>
  </tr>
  </#if>
  <tr>
    <td colspan="2" class="grayStyle" id="f_name">论文题目<#if !display><font color="red">*</font></#if></td>
    <td colspan="5">
    <#if display>${(myThesis.name)?if_exists}<#else>
      <textarea cols="30" rows="1"  name="thesis.name" style="width:100%">${(myThesis.name)?if_exists}</textarea></td>
    </#if>
  </tr>
  <tr>
    <td colspan="2" class="grayStyle" id="f_keyWords">论文主题词<#if !display><font color="red">*</font></#if></td>
    <td colspan="5">
    <#if display>${(myThesis.keyWords)?if_exists}<#else>
    <textarea cols="30" rows="1"  name="thesis.keyWords" style="width:100%">${(myThesis.keyWords)?if_exists}</textarea>
    </#if>
    </td>
  </tr>
  <tr>
    <td colspan="2" class="grayStyle" id="f_abstract_cn">中文摘要<#if !display><font color="red">*</font></#if></td>
    <td colspan="5">
    <#if display>${(myThesis.abstract_cn)?if_exists}<#else>
    <textarea cols="30" rows="5"  name="thesis.abstract_cn" style="width:100%">${(myThesis.abstract_cn)?if_exists}</textarea>
    </#if>
    </td>
  </tr>
  <tr>
    <td colspan="2" class="grayStyle" id="f_abstract_en">英文摘要<#if !display><font color="red">*</font></#if></td>
    <td colspan="5">
    <#if display>${(myThesis.abstract_en)?if_exists}<#else>
    	<textarea cols="30" rows="5"  name="thesis.abstract_en" style="width:100%">${(myThesis.abstract_en)?if_exists}</textarea></td>
    </#if>  
  </tr>
  <tr>
  	<td colspan="2" class="grayStyle" id="f_remark">备注</td>
  	<td colspan="5">
  	<#if display>${(myThesis.remark)?if_exists}<#else>
  	<textarea cols="30" rows="2"  name="thesis.remark" style="width:100%">${(myThesis.remark)?if_exists}</textarea>
  	</#if>
  	</td>
   </tr>
     <tr>
    <td width="8%" class="grayStyle" id="f_thesisSource" colspan="2">论文来源<#if !display><font color="red">*</font></#if></td>
    <td width="16%">
    <#if display>${(myThesis.thesisSource.name)?if_exists}<#else>
    	<@htm.i18nSelect datas=thesisSources selected=(myThesis.thesisSource.id)?default(0)?string name="thesis.thesisSource.id" style="width:100%">
    		<option value="">请选择</option>
    	</@>
    </#if>
    </td>
    <td width="8%" class="grayStyle" id="f_startOn">论文开始时间<#if !display><font color="red">*</font></#if></td>
    <td width="12%">
    <#if display><#if (myThesis.startOn)?exists>${myThesis.startOn?string("yyyy-MM-dd")}</#if><#else>
        <input type="text" name="thesis.startOn" style="width:100px;" onfocus="calendar();f_frameStyleResize(self);" value="<#if (myThesis.startOn)?exists>${myThesis.startOn?string("yyyy-MM-dd")}</#if>"></td>
    </#if>
    <td width="8%" class="grayStyle" id="f_endOn">论文结束时间<#if !display><font color="red">*</font></#if></td>
    <td width="21%">
    <#if display><#if (myThesis.endOn)?exists>${myThesis.endOn?string("yyyy-MM-dd")}</#if><#else>
    	<input type="text" name="thesis.endOn" style="width:100px;" onfocus="calendar();f_frameStyleResize(self);" value="<#if (myThesis.endOn)?exists>${myThesis.endOn?string("yyyy-MM-dd")}</#if>"></td>
    </#if>  
  </tr>
  <tr>
  	 <td width="8%" class="grayStyle" id="f_thesisType" colspan="2">论文类型<#if !display><font color="red">*</font></#if></td>
  	 <td><#if display>${(myThesis.thesisType.name)?if_exists}<#else>
  	 <@htm.i18nSelect datas=thesisTypes selected=(myThesis.thesisType.id)?default(0)?string name="thesis.thesisType.id" style="width:100%"><option value="">请选择</option></@></td>
  	 </#if>
  	 <td class="grayStyle" id="f_thesisNum">论文字数<#if !display><font color="red">*</font></#if></td>
  	 <td>
  	 <#if display>${(myThesis.thesisNum)?if_exists}万字<#else>
  	 <input type="text" name="thesis.thesisNum" style="width:120px" value="${(myThesis.thesisNum)?if_exists}" maxlength="6">万字</td>
  	 </#if>
  	<#if (student.type.id)?exists && (student.type.id==5 || student.type.superType.id == 5)>
	  	 <td class="grayStyle">指导教师：</td>
	  	 <td>
	  	 <#if display>
	  	     <@i18nName student.teacher/>(<@i18nName student.teacher.department/>)
	  	 <#else>
	       <#if (student.teacher.id)?exists><#assign departmentId = student.teacher.department.id/><#else><#assign departmentId = student.department.id/></#if>
	  	    <select id="teachDepartment" style="width:150px">
	   	         	<option value="${departmentId}"></option>
	       	</select>
	   	    <select id="teacher" name="thesis.student.teacher.id" style="width:150px">
	   	         	<option value="${(student.teacher.id)?if_exists}"></option>
	       	</select>
	       </#if>	  
	  	 </td>
	 <#else>
	 	<td class="grayStyle"></td>
	 	<td></td>
  	 </#if>
  	 
  </tr>
  <#if display&&(myThesis.downloadName)?exists>
  <tr>
 	<td colspan="2">我的论文下载</td>
 	<td colspan="5"><a href="thesisStd.do?method=doDownLoad&thesisManageId=${thesisManage.id}&storeId=06">${myThesis.displayName}</a></td>
  </tr>
  </#if>
  <tr>
   		<td class="darkColumn" colspan="7" align="center" height="25px;">
   			<#if !display>
   		    <input type="hidden" name="thesis.id" value="${myThesis.id?if_exists}"/>
   			<input type="hidden" name="thesis.student.id" value="${student.id}"/>
   			<input type="hidden" name="thesis.thesisManage.id" value="${thesisManage.id}"/>
   			<button name="buttonName" class="buttonStyle" onclick="save(this.form)">提交</button>
   			</#if>
   		</td>
   </tr>
    </form>
    <#if !display>
<#assign departmentList = teachDepartList/><#include "/templates/departTeacher2Select.ftl"/></#if>
</table>