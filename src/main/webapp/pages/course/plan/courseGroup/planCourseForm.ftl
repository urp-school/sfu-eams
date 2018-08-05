    <table width="100%" valign="top" class="formTable">
       <form name="planCourseForm"  action="planCourse.do?method=save" method="post" action="" onsubmit="return false;">
	   <tr class="darkColumn">
	     <td align="center" colspan="4"><@bean.message key="page.cultivateCourse.Form.lable"/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="23%">&nbsp;<@msg.message key="attr.courseName"/><font color="red">*</font></td>
	     <td class="brightStyle" id="f_planCourseId" colspan="3">
          <input type="hidden" name="planCourse.id" value=""/>
          <input type="hidden" name="planCourse.course.id" value=""/>
	      <input type="text" name="planCourse.course.name" value="请从课程列表中选择一门课程" readonly size="20" maxlength="20"/>
   	      <input type="button" id="selectCourseButton" value="<@bean.message key="action.select"/>" onclick="displaySelectList('course');"  class="buttonStyle"/>
	    </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" id="f_term">&nbsp;开课学期<font color="red">*</font></td>
	     <td class="brightStyle" colspan="3">
	      <input type="text" id="planCourse_termSeq" name="planCourse.termSeq" size="10" maxlength="50" value=""/>
	      多个学期开课的课程，<br>开课学期可以写成:,1,2,3,或者1,2
         </td>
	   </tr>
       <tr>
	     <td class="grayStyle" id="f_department">&nbsp;开课院系<font color="red">*</font></td>
	     <td class="brightStyle" colspan="3">
	      <input type="hidden" name="planCourse.teachDepart.id" value=""/>
	      <input type="text" name="planCourse.teachDepart.name" value=""  readonly maxlength="20"/>
	      <input type="button" value="<@bean.message key="action.select"/>" onclick="displaySelectList('planCourse.teachDepart');"  class="buttonStyle"/>
	      <input type="button" value="<@bean.message key="action.clear"/>" onclick="resetSelectList('planCourse.teachDepart');"  class="buttonStyle"/>	      
	     </td>
	   </tr>
       <tr>
	     <td class="grayStyle" id="f_credit">&nbsp;<@msg.message key="attr.credit"/></td>
	     <td class="brightStyle">
 	      <input type="text" name="planCourse.course.credits" maxlength="3" value="" style="width:50px" readonly/>
 	     </td>
	     <td class="grayStyle" id="f_credit">&nbsp;学时</td>
	     <td class="brightStyle">
 	      <input type="text" name="planCourse.course.extInfo.period" maxlength="3" value="" style="width:50px" readonly onChange="if(!confirm('确定要更改学时?')){this.value='';}"/>
 	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle">&nbsp;HSK约束</td>
	     <td class="brightStyle">
	      <select name="planCourse.HSKDegree.id" style="width:100px">
	      <option value="">请选择...</option>
	      <#list HSKDegreeList as degree >
	        <option value="${degree.id}"><@i18nName degree/></option>
	      </#list>
	      </select>
         </td>
	     <td class="grayStyle" id="f_credit">&nbsp;周课时</td>
	     <td class="brightStyle">
 	      <input type="text" name="planCourse.course.weekHour" value="" style="width:50px" readonly onChange="if(!confirm('确定要更改周课时?')){this.value='';}"/>
 	     </td>
	   </tr>
	   <#--
	   <tr>
	     <td class="grayStyle">&nbsp;可替代课程</td>
	     <td class="brightStyle" colspan="3">
	      <input type="hidden" name="planCourse.substitution.id" value=""/>
	      <input type="text" name="planCourse.substitution.name" value="" readonly maxlength="20"/>
	      <input type="button" value="<@bean.message key="action.select"/>" onclick="displaySelectList('planCourse.substitution');"  class="buttonStyle"/>
	      <input type="button" value="<@bean.message key="action.clear"/>" onclick="resetSelectList('planCourse.substitution');"  class="buttonStyle"/>	      
         </td>
	   </tr>
	   -->
	   <tr>
	     <td class="grayStyle">&nbsp;先修课程</td>
	     <td class="brightStyle" id="f_preCourses" colspan="3">
          <input type="hidden" name="preCourses.id" value=""/>
	      <input type="text" name="preCourses.name" value="" readonly maxlength="20"/>
	      <input type="button" value="<@bean.message key="action.add"/>" onclick="displaySelectList('preCourses');"  class="buttonStyle"/>
	      <input type="button" value="<@bean.message key="action.clear"/>" onclick="resetSelectList('preCourses');"  class="buttonStyle"/>	      
         </td>
	   </tr>
	   <tr>
	     <td class="grayStyle">&nbsp;<@bean.message key="attr.remark"/></td>
	     <td class="brightStyle" colspan="3">
	      <textarea name="planCourse.remark" cols="25" rows="2"></textarea>
         </td>
	   </tr>
	   <tr>
	     <td class="grayStyle">&nbsp;其他</td>
	     <td class="brightStyle" colspan="3">
	       <input type="checkbox" name="autoStatCredit" maxlength="5" <#if courseGroup.courseType.isCompulsory>checked</#if>/>自动统计组内学分、学时和学分分布
         </td>
	   </tr>
	   <tr class="darkColumn">
	   	 <td colspan="4" align="center">
	   	    <input type="hidden" name="teachPlan.id" value="${RequestParameters['teachPlan.id']}">
	   	    <input type="hidden" name="planCourse.courseGroup.id" value="${courseGroup.id}">
	     	<input type="button" value="<@bean.message key="action.submit"/>" onclick="savePlanCourse(this.form);" class="buttonStyle"/>
         	<input type="reset" value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>
         </td>
	   </tr>
       </form>
	  </table>

