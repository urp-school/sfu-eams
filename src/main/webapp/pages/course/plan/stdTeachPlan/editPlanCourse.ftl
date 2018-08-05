<#include "/templates/head.ftl">
<body   valign="top">
<#assign labelInfo><B>培养计划课程组管理[${teachPlan.enrollTurn}&nbsp;<@i18nName teachPlan.stdType/>&nbsp;<@i18nName teachPlan.department/>&nbsp;<@i18nName teachPlan.speciality?if_exists/>&nbsp;<@i18nName teachPlan.aspect?if_exists/>]</B></#assign>
<table id="courseGroupFormBar"></table>
<script>
  var bar= new ToolBar("courseGroupFormBar","${labelInfo}",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addBack("<@msg.message key="action.back"/>");
</script>
  <table width="100%" align="center" style="cellpadding:0;cellspacing:0;" LEFTMARGIN="0" TOPMARGIN="0">
     <tr valign="top">
         <td width="43%" >
		    <table width="100%" valign="top" class="formTable">
		       <form name="planCourseForm"  action="stdTeachPlan.do?method=savePlanCourse" method="post" onsubmit="return false;">
			      <input type="hidden" name="teachPlan.id" value="${teachPlan.id}">
			      <input type="hidden" name="planCourse.courseGroup.id" value="${courseGroup.id}" >
			   <tr class="darkColumn">
			     <td align="center" colspan="4"><@bean.message key="page.cultivateCourse.Form.lable" /></td>
			   </tr>
			   <tr>
			     <td class="grayStyle" width="23%">&nbsp;<@msg.message key="attr.courseName"/><font color="red">*</font></td>
			     <td class="brightStyle" id="f_planCourseId" colspan="3">
			      <#if planCourse.id?exists>
		          <input type="hidden" name="planCourse.id" value="${planCourse.id}" />
		          <input type="hidden" name="planCourse.course.id" value="${planCourse.course.id}" /><@i18nName planCourse.course/>
		          <#else>
		          <input type="hidden" name="planCourse.id" value="" />
		          <input type="hidden" name="planCourse.course.id" value="" />
                  <input type="text" name="planCourse.course.name" value="" readonly maxlength="20"/>
		          </#if>
		   	     </td>
			   </tr>
			   <tr>
			     <td class="grayStyle" id="f_term">&nbsp;开课学期<font color="red">*</font></td>
			     <td class="brightStyle" colspan="3">
			      <input type="text" id="planCourse_termSeq" name="planCourse.termSeq" size="10" maxlength="50" value="${planCourse.termSeq?if_exists}"/>
			      多个学期开课的课程，<br>开课学期可以写成:,1,2,3,或者1,2
		         </td>
			   </tr>
		       <tr>
			     <td class="grayStyle" id="f_department">&nbsp;开课院系<font color="red">*</font></td>
			     <td class="brightStyle" colspan="3">
			      <#if planCourse.teachDepart?exists>
			         <#assign teachDepartId>${planCourse.teachDepart?if_exists.id?if_exists?string}</#assign>
			      <#else>
			         <#assign teachDepartId>0</#assign>
			      </#if>
			      <select name="planCourse.teachDepart.id" style="width:150px">
			         <#list teachDepartList as teachDepart>
			         <option value="${teachDepart.id}" <#if teachDepart.id?string==teachDepartId>selected</#if>><@i18nName teachDepart/></option>
			         </#list>
			      </select>
                </td>
			   </tr>
		       <tr>
			     <td class="grayStyle" id="f_credit">&nbsp;<@msg.message key="attr.credit"/><font color="red">*</font></td>
			     <td class="brightStyle">
		 	      <input type="text" name="planCourse.course.credits" value="${(planCourse.course.credits)?if_exists}" style="width:50px" maxLength="4"/>
		 	     </td>
			     <td class="grayStyle" id="f_credit">&nbsp;学时<font color="red">*</font></td>
			     <td class="brightStyle">
		 	      <input type="text" name="planCourse.course.extInfo.period" value="${(planCourse.course.extInfo.period)?if_exists}" style="width:50px" maxLength="4"/>
		 	     </td>
			   </tr>
			   <tr>
			     <td class="grayStyle" >&nbsp;HSK约束</td>
			     <td class="brightStyle">
			      <select name="planCourse.HSKDegree.id" style="width:100px">
			      <option   value="">请选择...</option>
			      <#list HSKDegreeList as degree >
			        <option value="${degree.id}" <#if degree.id?string=planCourse.HSKDegree?if_exists.id?if_exists?string>selected</#if>><@i18nName degree/></option>
			      </#list>
			      </select>
		         </td>
			     <td class="grayStyle" id="f_credit">&nbsp;周课时<font color="red">*</font></td>
			     <td class="brightStyle">
		 	      <input type="text" name="planCourse.course.weekHour" value="${(planCourse.course.weekHour)?if_exists}" style="width:50px" maxLength="4"/>
		 	     </td>
			   </tr>
			   <tr>
			     <td class="grayStyle">&nbsp;可替代课程</td>
			     <td class="brightStyle" colspan="3">
			      <input type="hidden" name="planCourse.substitution.id" value="${planCourse.substitution?if_exists.id?if_exists}" />
			      <input type="text" name="planCourse.substitution.name" value="<@i18nName planCourse.substitution?if_exists/>" readonly />
			      <input type="button" value="<@bean.message key="action.select" />" onclick="displaySelectList('planCourse.substitution');"  class="buttonStyle"/>
			      <input type="button" value="<@bean.message key="action.clear" />" onclick="resetSelectList('planCourse.substitution');"  class="buttonStyle"/>	      
		         </td>
			   </tr>
			   <tr>
			     <td class="grayStyle">&nbsp;先修课程</td>
			     <td class="brightStyle" id="f_preCourses" colspan="3">
		          <input type="hidden" name="preCourses.id" value="<#list planCourse.preCourses as course>${course.id},</#list>"  />
			      <input type="text" name="preCourses.name" value="<@getBeanListNames planCourse.preCourses/>"  readonly  />
			      <input type="button" value="<@bean.message key="action.add" />" onclick="displaySelectList('preCourses');"  class="buttonStyle"/>
			      <input type="button" value="<@bean.message key="action.clear" />" onclick="resetSelectList('preCourses');"  class="buttonStyle"/>	      
		         </td>
			   </tr>
			   <tr>
			     <td class="grayStyle">&nbsp;<@bean.message key="attr.remark" /></td>
			     <td class="brightStyle" colspan="3">
			      <textarea name="planCourse.remark" cols="25" rows="2">${planCourse.remark?if_exists}</textarea>
		         </td>
			   </tr>
			   <tr>
			     <td class="grayStyle">&nbsp;其他</td>
			     <td class="brightStyle" colspan="3">
			       <input type="checkbox" name="autoStatCredit" <#if courseGroup.courseType.isCompulsory>checked</#if>/>自动统计组内学分、学时和学分分布
		         </td>
			   </tr>
			   <tr class="darkColumn">
			   	 <td colspan="4" align="center">
			     	<input type="button" value="<@bean.message key="action.submit" />" onclick="savePlanCourse(this.form);"  class="buttonStyle"/>
		         	<input type="reset" value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>
		         </td>
			   </tr>
		       </form>
			  </table>
         </td>
         <td>
	         <iframe  src="courseSelector.do?method=search&pageNo=1&pageSize=10&multi=false"
		     id="selectListFrame" name="selectListFrame"
		     marginwidth="0" marginheight="0" scrolling="no"
		     frameborder="0"  height="100%" width="100%">
		     </iframe>
         </td>
     </tr>
  </table>
  <script>
    <#if planCourse.id?exists>
    var selectedKind="planCourse.substitution";
    <#else>
    var selectedKind="course";
    </#if>
  </script>
  <script language="JavaScript" type="text/JavaScript" src="scripts/course/PlanCourse.js"></script> 
<#include "/templates/foot.ftl">
