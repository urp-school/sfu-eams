	   <#macro getTitleClass><#if titleClass?default('')!=''>class="${titleClass}"</#if></#macro>
	   <tr align="center" class="darkColumn">
	     <#if checkboxNeed?default(true)><td align="center" width="3%"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('stdId'),event);"></td></#if>
	     <td <@getTitleClass/>><@bean.message key="attr.stdNo"/></td>
	     <td><@bean.message key="attr.personName"/></td>	     
	     <#if collegeShow?default(true)><td width="25%"><@bean.message key="entity.college"/></td></#if>
	     <td><@bean.message key="entity.studentType"/></td>
	     <#if secondMajorShow?default(false)><td>双专业</td></#if>
	     <#if secondAspectShow?default(false)><td>双专业方向</td></#if>
	     <#if isSecondMajorStudyShow?default(false)><td>双专业是否就读</td></#if>
	     <#if isSecondMajorThesisNeedShow?default(false)><td>双专业是否写论文</td></#if>
	     <#if adminClassShow?default(false)><td><#if is2ndSpeciality?default(false)>双专业专业<#else>专业</#if>班级</td></#if>	     
	     <td>学籍状态</td>
	     <td>学籍有效性</td>
	     <#if registerShow?default(false)><td>注册状态</td></#if>
	   </tr>
	   <#if studentList?exists>
	   		<#assign resultList = studentList />
	   <#else>
	   		<#assign resultList = result.studentList.items />
	   </#if>
	   <#list (resultList)?if_exists as student>
	   <#if student_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if student_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <#if checkboxNeed?default(true)><td align="center"><input type="checkBox" name="stdId" value="${student.id}"></td></#if>
   	    <td>&nbsp;${student.code}</td>
	    <td>
	     <a href="searchStudent.do?method=detail&stdId=${student.id}">
	      &nbsp;<@i18nName student?if_exists/>
	     </a>
	    </td>
	    <#if collegeShow?default(true)><td>&nbsp;<@i18nName student.department?if_exists/></td></#if>
	    <td>&nbsp;<@i18nName student.type?if_exists/></td>
	    <#if secondMajorShow?default(false)><td>&nbsp;<@i18nName student.secondMajor?if_exists/></td></#if>
	    <#if secondAspectShow?default(false)><td>&nbsp;<@i18nName student.secondAspect?if_exists/></td></#if>
	    <#if isSecondMajorStudyShow?default(false)><td><#if student.isSecondMajorStudy?exists>&nbsp;${student.isSecondMajorStudy?string("在读","不在读")}</#if></td></#if>
	    <#if isSecondMajorThesisNeedShow?default(false)><td><#if student.isSecondMajorThesisNeed?exists>&nbsp;${student.isSecondMajorThesisNeed?string("需要","不需要")}</#if></td></#if>
	    <#if adminClassShow?default(false)><td><#if is2ndSpeciality?exists><#list student.getAdminClassSet(is2ndSpeciality)?if_exists as adminClass><@i18nName adminClass/></#list><#else><#list student.getAdminClassSet(is2ndSpeciality)?if_exists as adminClass><@i18nName adminClass/></#list></#if></td></#if>
	    <td>&nbsp;<@i18nName student.state?if_exists/></td>
	    <td>&nbsp;${student.isValid?string("有效","无效")}</td>
	    <#if registerShow?default(false)><td>&nbsp;<#assign info=student.getRegisterInfo(calendarId)?if_exists/><#if info?exists&&info!="">${info.registerStatus?string("<font color='#0000FF'>已注册</font>","<font color='FF0000'>未注册</font>")}<#else>无记录</#if></td></#if>
	   </tr>
	   </#list>