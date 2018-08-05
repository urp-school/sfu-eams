       <#if result.student?exists&&result.student.studentStatusInfo?exists>
          <#assign graduateSchool=result.student.studentStatusInfo.graduateSchool?if_exists />
          <#if result.student.studentStatusInfo.educationBeforEnroll?exists>
            <#assign educationBeforEnrollId=result.student.studentStatusInfo.educationBeforEnroll.id />
          </#if>
          <#if result.student.studentStatusInfo.enrollMode?exists>
            <#assign enrollModeId=result.student.studentStatusInfo.enrollMode.id>
          </#if>
          <#if result.student.studentStatusInfo.enrollDate?exists>
          <#assign enrollDate=result.student.studentStatusInfo.enrollDate?string('yyyy-MM-dd') />
          </#if>
          <#assign originalAddress=result.student.studentStatusInfo.originalAddress?if_exists />
          <#if result.student.studentStatusInfo.leaveDate?exists>
          <#assign leaveDate=result.student.studentStatusInfo.leaveDate?string('yyyy-MM-dd') />
          </#if>
          <#assign gotoWhere=result.student.studentStatusInfo.gotoWhere?if_exists />
          <#if result.student.studentStatusInfo.leaveSchoolCause?exists>
            <#assign leaveSchoolCauseId=result.student.studentStatusInfo.leaveSchoolCause.id>
          </#if>
          <#if result.student.studentStatusInfo.educationMode?exists>
            <#assign educationModeId=result.student.studentStatusInfo.educationMode.id>
          </#if>
          <#if result.student.state?exists>
            <#assign statusId=result.student.state.id>
          </#if>
          <#assign examNumber=result.student.studentStatusInfo.examNumber?if_exists />
       </#if>
       <input type="hidden" name="statusInfo.id" value="${(result.student.studentStatusInfo.id)?default('')}">
	   <tr class="darkColumn">
	     <td align="center" colspan="2"><@bean.message key="std.detail"/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_graduateSchool">
	      &nbsp;<@bean.message key="std.graduateSchool"/>：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="statusInfo.graduateSchool" maxlength="40" size="40" value="${graduateSchool?if_exists}"/>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_educationBeforEnroll">
	      &nbsp;<@bean.message key="std.educationBeforEnroll"/>：
	     </td>
	     <td class="brightStyle">
	      <select name="statusInfo.educationBeforEnroll.id" style="width:100px;">
	        <option></option>
	        <#list result.eduDegreeList?if_exists as eduDegree>
	        <option value="${eduDegree.id}" <#if (student.studentStatusInfo.eduDegree.id)?default(0)==eduDegree.id>selected</#if>><@i18nName eduDegree?if_exists/></option>
	        </#list>
	      </select>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_enrollMode">
	      &nbsp;<@bean.message key="entity.enrollMode"/><font color="red">*</font>：
	     </td>
	     <td class="brightStyle">
	      <select name="statusInfo.enrollMode.id" style="width:100px;">
	        <option></option>
	        <#list result.enrollModeList?if_exists as enrollMode>
	        <option value="${enrollMode.id}" <#if (result.student.studentStatusInfo.enrollMode.id)?default(0)==enrollMode.id>selected</#if>><@i18nName enrollMode?if_exists/></option> 
	        </#list>
	      </select>
	     </td>
	   </tr>	   
	   <tr>
	     <td class="grayStyle" width="25%" id="f_enrollDate">
	      &nbsp;<@bean.message key="std.enrollDate"/><font color="red">*</font>：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="statusInfo.enrollDate" onfocus="calendar()" size="10" value="${enrollDate?if_exists}"/>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_feeOrigin">
	      &nbsp;费用来源：
	     </td>
	     <td class="brightStyle">
	      <select name="statusInfo.feeOrigin.id" style="width:100px;">
	        <option></option>
	        <#list result.feeOriginList?if_exists as feeOrigin>
	        <option value="${feeOrigin.id}" <#if (result.student.studentStatusInfo.feeOrigin.id)?default(0)==feeOrigin.id>selected</#if>><@i18nName feeOrigin?if_exists/></option> 
	        </#list>
	      </select>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_originalAddress">
	      &nbsp;<@bean.message key="std.originalAddress"/>：
	     </td>
	     <td class="brightStyle">
	      <textarea name="statusInfo.originalAddress" cols="40">${originalAddress?if_exists}</textarea>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_leaveDate">
	      &nbsp;<@bean.message key="std.leaveDate"/>：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="statusInfo.leaveDate" maxlength="10" size="10" value="${leaveDate?if_exists}"/>
	      <input type="button" value="日期" class="buttonStyle"  style="width:40px" onclick="calendar(this.form['statusInfo.leaveDate'])">
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_gotoWhere">
	      &nbsp;<@bean.message key="std.gotoWhere"/>：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="statusInfo.gotoWhere" maxlength="100" size="40" value="${gotoWhere?if_exists}"/>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_leaveSchoolCause">
	      &nbsp;<@bean.message key="entity.leaveSchoolCause"/>：
	     </td>
	     <td class="brightStyle">
	      <select name="statusInfo.leaveSchoolCause.id" style="width:100px;">
	       <option></option>
	       <#list result.leaveSchoolCauseList?if_exists as leaveSchoolCause>
	       <option value="${leaveSchoolCause.id}" <#if (result.student.studentStatusInfo.leaveSchoolCause.id)?default(0)==leaveSchoolCause.id>selected</#if>><@i18nName leaveSchoolCause?if_exists/></option>
	       </#list>
	      </select>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_studentState">
	      &nbsp;<@bean.message key="entity.studentState"/><font color="red">*</font>：
	     </td>
	     <td class="brightStyle">
	      <select name="student.state.id" style="width:100px;">
	       <option></option>
	       <#list result.studentStateList?if_exists as studentState>
	       <option value="${studentState.id}" <#if (result.student.state.id)?default(0)==studentState.id>selected</#if>><@i18nName studentState?if_exists/></option>
	       </#list>
	      </select>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_educationMode">
	      &nbsp;<@bean.message key="entity.educationMode"/>：
	     </td>
	     <td class="brightStyle">
	      <select name="statusInfo.educationMode.id" style="width:100px;">
	       <option></option>
	       <#list result.educationModeList?if_exists as educationMode>
	       <option value="${educationMode.id}" <#if (result.student.studentStatusInfo.educationMode.id)?default(0)==educationMode.id>selected</#if>><@i18nName educationMode?if_exists/></option>
	       </#list>
	      </select>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_examNumber">
	      &nbsp;<@bean.message key="std.examNumber"/>：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="statusInfo.examNumber" maxlength="40" value="${examNumber?if_exists}" />
	     </td>
	   </tr>