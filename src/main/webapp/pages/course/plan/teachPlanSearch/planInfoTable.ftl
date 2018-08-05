  <table cellpadding="0" cellspacing="0" border="0" width="100%" align="center" >
  <#assign courseCount=0>
   <tr>
    <td align="center" colspan="2" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B>
     <#assign stdTypeName><@i18nName teachPlan.stdType/></#assign>
     <#assign departmentName><@i18nName teachPlan.department/></#assign>
     <#assign specialityName><#if teachPlan.speciality?exists><@i18nName teachPlan.speciality/><#else></#if></#assign>
     <#assign aspectName><#if teachPlan.aspect?exists><@i18nName  teachPlan.aspect/><#else></#if></#assign>
     <@i18nName school/>&nbsp;<@bean.message key="plan.reportTitle" arg1=stdTypeName arg2=departmentName arg3=specialityName arg4=aspectName arg0=teachPlan.enrollTurn/>
     </B>
    </td>
   </tr>
   <tr>
    <td width="15">&nbsp;</td>
    <td>
     <table class="listTable" style="font-size:12px">
      <#assign maxTerm=teachPlan.termsCount />
      <tr align="center" class="darkColumn">
       <td rowspan="2" width="3%"><@bean.message key="attr.cultivateScheme.sort"/></td>
       <td rowspan="2" width="10%"><@bean.message key="attr.courseNo"/></td>
       <td rowspan="2" width="30%"><@bean.message key="attr.courseName"/></td>
       <td rowspan="2" width="5%"><@msg.message key="attr.courseLength"/></td>
       <td rowspan="2" width="5%"><@bean.message key="attr.credit"/></td>
       <td colspan="${maxTerm}" width="30%"><@bean.message key="attr.cultivateScheme.arrangeByTermAndAcademic"/></td>
       <td rowspan="2" width="20%"><@msg.message key="attr.teachDepart"/></td>
       <td rowspan="2"><@bean.message key="attr.remark"/></td>
      </tr>
      <tr class="darkColumn">       
       <#assign total_term_credit={} />
       <#list 1..maxTerm as i >
        <#assign total_term_credit=total_term_credit + {i:0} />
        <td width="2%"><p align="center">${i}</p></td>
       </#list>
      </tr>
      
      <#--开始绘制课程组-->
      <#list teachPlan.courseGroups?sort_by(["courseType","priority"]) as group>
      <#if group.planCourses?size!=0><#assign typeRowspan=group.planCourses?size+2><#else><#assign typeRowspan=2></#if>
      <tr>
      <#assign parentCourseType> </#assign>
      <#if group.parentCourseType?exists>
	      <#if group.parentCourseType.id!=group.courseType.id>
	      <#assign parentCourseType><@i18nName group.parentCourseType/>/</#assign>
	      </#if>
      </#if>
      <#if typeRowspan=2>
        <td rowspan="2"  colspan="3" align="left">&nbsp;${parentCourseType}<@i18nName group.courseType/></td>
      <#else>
        <td rowspan="${typeRowspan}" align="left">${parentCourseType}<@i18nName group.courseType/></td>
      </#if>
      </tr>
      
      <#list group.planCourses?sort_by(["course","code"]) as planCourse>
      <#assign courseCount = courseCount+1>
      <tr>
       <td align="center">${planCourse.course.code}</td>
       <td align="left">&nbsp;${courseCount}&nbsp;<@i18nName planCourse.course/></td>
       <td align="center">${(planCourse.course.extInfo.period)?default(0)}</td>
       <td align="center">${(planCourse.course.credits)?default(0)}</td>
       <#list 1..maxTerm as i>
       <td>
        <p align="center">
         <#if planCourse.termSeq?exists && (","+planCourse.termSeq+",")?contains(","+i+",")>√<#else>&nbsp;</#if>
        </p>
       </td>
       </#list>
       <td align="center"><@i18nName planCourse.teachDepart/></td>
       <td><#if planCourse.substitution?exists><@bean.message key="attr.cultivateScheme.courseCanbeReplace"/>：<@i18nName planCourse.substitution/><br></#if>
           <#if planCourse.remark?exists>${planCourse.remark}<#else>&nbsp;</#if>
       </td>
      </tr>     
      </#list>
      
      <#--开始绘制课程组小记-->
      <tr>
        <#if (typeRowspan>2)>
        <td colspan="2" align="center"><@msg.message key="attr.creditSubtotal"/></td>
        </#if>
        <td align="center">${group.creditHour}</td>
        <td align="center">${group.credit}</td>
       <#assign i=1>
       <#list group.creditPerTerms[1..group.creditPerTerms?length-2]?split(",") as credit>
       <td align="center"><#if credit!="0">${credit}<#else>&nbsp;</#if>
          <#assign current_totle=total_term_credit[i?string] />
          <#assign total_term_credit=total_term_credit + {i:current_totle+credit?number} />
          <#assign i=i+1>
       </td>
       </#list>
       <td>&nbsp;</td>
       <td align="center">&nbsp;${group.remark?if_exists}</td>
      </tr>
      <#--结束绘制课程组小记-->
      
      <#--开始绘制全程总计--> 
      </#list>
      <tr>
       <td align="center" colspan="3"><@bean.message key="attr.cultivateScheme.allTotle"/></td>
       <td align="center">${teachPlan.creditHour}</td>
       <td align="center">${teachPlan.credit}</td>
       <#list 1..maxTerm as i>
       <td><p align="center"><#if total_term_credit[i?string]!=0>${total_term_credit[i?string]}<#else>&nbsp;</#if></p></td>
       </#list>
       <td>&nbsp;</td>
       <td>&nbsp;</td>
      </tr>
      <tr>
       <td align="center" colspan="2"><@msg.message key="attr.guidedTeacher"/></td>
       <td align="center" colspan="${5+maxTerm}">&nbsp;${teachPlan.teacherNames?if_exists}</td>
      </tr>
      <tr class="darkColumn">
       <td align="center" colspan="2"><@msg.message key="attr.remark"/></td>
       <td align="center" colspan="${5+maxTerm}">&nbsp;${teachPlan.remark?if_exists}</td>
      </tr>
     </table>
    </td>
   </tr>
  </table>