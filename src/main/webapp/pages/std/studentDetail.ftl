<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
 <body>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <form name="commonForm" action="studentOperation.do" method="post">
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="info.studentInfo"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <#--学籍基本信息-->
	   <#if result.student?exists>
	      <#assign stdId=result.student.id?if_exists/>
	      <#assign studentName=result.student.name?if_exists/>
	      <#assign engName=result.student.engName?if_exists/>
	      <#assign enrollYear=result.student.enrollYear?if_exists/>
	      <#if (enrollYear?length > 6)>
	      <#assign belongToYear=enrollYear?if_exists[0..3]/>
	      <#assign sequence=enrollYear?if_exists[5..result.student.enrollYear?length-1]/>
	      <#else>
	      </#if>
	      
	      <#if result.student.department?exists>
	         <#assign departmentDescriptionsValue=result.student.department.name/>
	      </#if>
	      
	      <#if result.student.firstMajor?exists>
	         <#assign specialityDescriptionsValue=result.student.firstMajor.name/>
	      </#if>
	      
	      <#if result.student.firstAspect?exists>
	         <#assign specialityAspectDescriptionsValue=result.student.firstAspect.name/>
	      </#if>
	      
	      <#if result.student.type?exists>
	         <#assign studentTypeDescriptionsValue=result.student.type.name/>
	      </#if>
	      <#if result.student.adminClasses?exists>
	      	 <#assign adminClasses=result.student.adminClasses/>
	      </#if>     
	      
	      
	      <#assign remark=result.student.remark?if_exists/>
	   </#if>
	   
	   <#--学籍基本信息-->
      <table  width="80%" align="center" class="listTable">
		    <tr class="darkColumn">
		     <td align="center" colspan="3"><@bean.message key="info.studentRecordBasicInfo"/></td>
		    </tr>
   			<tr>
 				<td class="grayStyle" width="25%" id="f_stdId">&nbsp;<@bean.message key="attr.stdNo"/>：</td>
 				<td class="brightStyle" >&nbsp;${(result.student.code)?default('')}</td>
				<td class="grayStyle"  id="f_studentPhoto">&nbsp;照片：</td>
   			</tr>
		   	<tr>
		     	<td class="grayStyle"  id="f_name">&nbsp;<@bean.message key="attr.personName"/>：</td>
		     	<td class="brightStyle">&nbsp;${studentName?if_exists}</td>
 				<td class="brightStyle"  align="center" rowspan="10" width="120px">
	 			<#assign spurl = "org.beangle.freemarker.StudentPhotoUrlMethod"?new()/>
 			       <img id="photo" src="${spurl(result.student.code)}" 
 			            width="120"  style="heignt:100%;vertical-align:top;margin-top:2px;border:2px" >
 				</td>
		   	</tr>
		   	<tr>
		     	<td class="grayStyle"  id="f_enName">&nbsp;<@bean.message key="attr.engName"/>：</td>
		     	<td class="brightStyle">&nbsp;${engName?if_exists}</td>
		   	</tr>	
		   	<tr>
		     	<td class="grayStyle"  id="f_enrollYear">&nbsp;<@bean.message key="filed.enrollYearAndSequence"/>：</td>
		     	<td class="brightStyle">&nbsp;${enrollYear?if_exists}</td>
		   	</tr>
		   	<tr>
		     	<td class="grayStyle"  id="f_department">&nbsp;<@bean.message key="entity.college"/>：</td>
		     	<td class="brightStyle">&nbsp;${departmentDescriptionsValue?if_exists}</td>
		   	</tr>
	       	<tr>
		     	<td class="grayStyle"  id="f_speciality">&nbsp;<@bean.message key="entity.speciality"/>：</td>
		     	<td class="brightStyle">&nbsp;${specialityDescriptionsValue?if_exists}</td>
		   	</tr>
	       	<tr>
		     	<td class="grayStyle"  id="f_specialityAspect">&nbsp;<@bean.message key="entity.specialityAspect"/>：</td>
		     	<td class="brightStyle">&nbsp;${specialityAspectDescriptionsValue?if_exists}</td>
		   	</tr>
	       	<tr>
		     	<td class="grayStyle"  id="f_studentType">&nbsp;<@bean.message key="entity.studentType"/>：</td>
		     	<td class="brightStyle">&nbsp;${studentTypeDescriptionsValue?if_exists}</td>
		   	</tr>
		   	<tr>
 				<td class="grayStyle"  id="f_adminClass">&nbsp;<@bean.message key="entity.adminClass"/>：</td>
 				<td class="brightStyle">&nbsp;<#list adminClasses?if_exists?sort_by("code") as adminClass><#if adminClass_has_next ><@i18nName adminClass/>(${adminClass["code"]})<#if (adminClass_index+1)%2==1>,&nbsp;</#if><#if (adminClass_index+1)%2==0><br></#if><#else><@i18nName adminClass/>(${adminClass["code"]})&nbsp;</#if></#list></td>
   				</tr>
   			<tr>
		     	<td class="grayStyle"  id="f_isStudentStatusAvailable">&nbsp;<@bean.message key="entity.isStudentStatusAvailable"/>：</td>
		     	<td class="brightStyle">&nbsp;
		     	<#if result.student.active?exists>
		     	<#if result.student.active>
		     	<@bean.message key="entity.available"/>
		     	<#elseif !result.student.active>
		     	<@bean.message key="entity.unavailable"/>
		     	</#if>
		     	</#if>
	     		</td>
	   		</tr>
	   		<tr>
		     	<td class="grayStyle"  id="f_languageAbility">&nbsp;<@bean.message key="common.languageAbility"/>：</td>
		     	<td class="brightStyle">&nbsp;<@i18nName (result.student.languageAbility)?if_exists/></td>
		   	</tr>
		   	<tr>
		     	<td class="grayStyle"  id="f_remark">&nbsp;<@bean.message key="common.remark"/>：</td>
		     	<td class="brightStyle" style="word-warp:break-word;word-break:break-all" >&nbsp;${remark?if_exists}</td>
		   	</tr>
     </table>
     
     <#--学籍详细信息-->
     <table width="80%" align="center" class="listTable" style="border-top-style:none">
	   
       <#if result.student?exists&&result.student.studentStatusInfo?exists>
          <#assign graduateSchool=result.student.studentStatusInfo.graduateSchool?if_exists/>
          
          <#if result.student.studentStatusInfo.educationBeforEnroll?exists>
            <#assign educationBeforEnrollName=result.student.studentStatusInfo.educationBeforEnroll.name/>
          </#if>
          
          <#if result.student.studentStatusInfo.enrollMode?exists>
            <#assign enrollModeName=result.student.studentStatusInfo.enrollMode.name>
          </#if>
          
          <#if result.student.studentStatusInfo.enrollDate?exists>
          	<#assign enrollDate=result.student.studentStatusInfo.enrollDate?string('yyyy-MM-dd')/>
          </#if>
          <#assign originalAddress=result.student.studentStatusInfo.originalAddress?if_exists/>
          <#if result.student.studentStatusInfo.leaveDate?exists>
          	<#assign leaveDate=result.student.studentStatusInfo.leaveDate?string('yyyy-MM-dd')/>
          </#if>
          <#assign gotoWhere=result.student.studentStatusInfo.gotoWhere?if_exists/>
          
          <#if result.student.studentStatusInfo.leaveSchoolCause?exists>
            <#assign leaveSchoolCauseName=result.student.studentStatusInfo.leaveSchoolCause.name>
          </#if>
          
          <#if result.student.studentStatusInfo.educationMode?exists>
            <#assign educationModeName=result.student.studentStatusInfo.educationMode.name>
          </#if>
          
          <#if result.student.active?exists>
            <#assign statusName=result.student.state.name>
          </#if>
          
          <#assign examNumber=result.student.studentStatusInfo.examNumber?if_exists/> 
       </#if>
	   <tr class="darkColumn">
	     <td align="center" colspan="2"><@bean.message key="std.detail"/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_graduateSchool">&nbsp;<@bean.message key="std.graduateSchool"/>：</td>
	     <td class="brightStyle">&nbsp;${graduateSchool?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_educationBeforEnroll">
	      &nbsp;<@bean.message key="std.educationBeforEnroll"/>：
	     </td>
	     <td class="brightStyle">&nbsp;${educationBeforEnrollName?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_enrollMode">&nbsp;<@bean.message key="entity.enrollMode"/>：</td>
	     <td class="brightStyle">&nbsp;${enrollModeName?if_exists}</td>
	   </tr>
	  
	   <tr>
	     <td class="grayStyle" width="25%" id="f_enrollDate">&nbsp;<@bean.message key="std.enrollDate"/>：</td>
	     <td class="brightStyle">&nbsp;${enrollDate?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_originalAddress">&nbsp;<@bean.message key="std.originalAddress"/>：</td>
	     <td class="brightStyle">&nbsp;${originalAddress?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_leaveDate">&nbsp;<@bean.message key="std.leaveDate"/>：</td>
	     <td class="brightStyle">&nbsp;${leaveDate?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_gotoWhere">&nbsp;<@bean.message key="std.gotoWhere"/>：</td>
	     <td class="brightStyle">&nbsp;${gotoWhere?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_leaveSchoolCause">&nbsp;<@bean.message key="entity.leaveSchoolCause"/>：</td>
	     <td class="brightStyle">&nbsp;${leaveSchoolCauseName?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_status">&nbsp;<@bean.message key="entity.studentState"/>：</td>
	     <td class="brightStyle">&nbsp;${statusName?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_educationMode">&nbsp;<@bean.message key="entity.educationMode"/>：</td>
	     <td class="brightStyle">&nbsp;${educationModeName?if_exists}</td>
	   </tr>	   
	   <tr>
	     <td class="grayStyle" width="25%" id="f_examNumber">&nbsp;<@bean.message key="std.examNumber"/>：</td>
	     <td class="brightStyle">&nbsp;${examNumber?if_exists}</td>
	   </tr>
	   <#--学生基本信息-->
       <#if result.student?exists&&result.student.basicInfo?exists>  
          <#if result.student.basicInfo.country?exists>
             <#assign countryName=result.student.basicInfo.country.name/>
          </#if>
          
          <#if result.student.basicInfo.nation?exists>
             <#assign nationName=result.student.basicInfo.nation.name/>
          </#if> 
          
          <#assign idCard=result.student.basicInfo.idCard?if_exists/>
          
          <#if result.student.basicInfo.gender?exists>
             <#assign genderName=result.student.basicInfo.gender.name/>
          </#if>
          
          <#if result.student.basicInfo.birthday?exists>
          	<#assign birthday=result.student.basicInfo.birthday?string("yyyy-MM-dd")/>
          </#if>
          <#assign homeAddress=result.student.basicInfo.homeAddress?if_exists/>
          <#assign postCode=result.student.basicInfo.postCode?if_exists/>
          <#assign phone=result.student.basicInfo.phone?if_exists/>
          <#assign ancestralAddress=result.student.basicInfo.ancestralAddress?if_exists/>
          <#assign mail=result.student.basicInfo.mail?if_exists/>
          
          <#if result.student.basicInfo.politicVisage?exists>
             <#assign politicVisageName=result.student.basicInfo.politicVisage.name/>
          </#if>
       </#if>
       <input type="hidden" name="basicInfo.id" value="${stdId?if_exists}">
	   <tr class="darkColumn">
	     <td align="center" colspan="2">
	       <@bean.message key="info.studentBasicInfo"/><br>
	      <input type="button" value="<@bean.message key="info.editBasicInfo"/>" name="button1" onClick="updateStudentBasicInfo()" class="buttonStyle"/>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_country">&nbsp;<@bean.message key="entity.country"/>：</td>
	     <td class="brightStyle">&nbsp;${countryName?default('')?html}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_nation">&nbsp;<@bean.message key="entity.nation"/>：</td>
	     <td class="brightStyle">&nbsp;${nationName?default('')?html}</td>
	   </tr>	
	   <tr>
	     <td class="grayStyle" width="25%" id="f_idCard">&nbsp;<@bean.message key="std.idCard"/>：</td>
	     <td class="brightStyle">&nbsp;${idCard?default('')?html}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_gender">&nbsp;<@bean.message key="attr.gender"/>：</td>
	     <td class="brightStyle">&nbsp;${genderName?default('')?html}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_maritalStatus">&nbsp;婚姻状况：</td>
	     <td class="brightStyle">&nbsp;<#if (result.student.basicInfo.maritalStatus)?exists><@i18nName result.student.basicInfo.maritalStatus/></#if></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_birthday">&nbsp;<@bean.message key="attr.birthday"/>：</td>
	     <td class="brightStyle">&nbsp;${birthday?default('')?html}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_homeAddress">&nbsp;<@bean.message key="attr.familyAddress"/>：</td>
	     <td class="brightStyle">&nbsp;${homeAddress?default('')?html}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_postCode">&nbsp;<@bean.message key="attr.postCode"/>：</td>
	     <td class="brightStyle">&nbsp;${postCode?default('')?html}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_phone">&nbsp;<@bean.message key="attr.phoneOfHome"/>：</td>
	     <td class="brightStyle">&nbsp;${phone?default('')?html}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_ancestralAddress">&nbsp;<@bean.message key="attr.ancestralAddress"/>：</td>
	     <td class="brightStyle">&nbsp;${ancestralAddress?default('')?html}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_parentName">&nbsp;家庭成员：</td>
	     <td class="brightStyle">&nbsp;${(result.student.basicInfo.parentName)?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_workAddress">&nbsp;工作地址：</td>
	     <td class="brightStyle">&nbsp;${(result.student.basicInfo.workAddress)?default('')?html}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_workPlace">&nbsp;工作单位：</td>
	     <td class="brightStyle">&nbsp;${(result.student.basicInfo.workPlace)?default('')?html}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_workPlacePostCode">&nbsp;工作地址<@bean.message key="attr.postCode"/>：</td>
	     <td class="brightStyle">&nbsp;${(result.student.basicInfo.workPlacePostCode)?default('')?html}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_workPhone">&nbsp;工作单位电话：</td>
	     <td class="brightStyle">&nbsp;${(result.student.basicInfo.workPhone)?default('')?html}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_mail">&nbsp;<@bean.message key="attr.email"/>：</td>
	     <td class="brightStyle">&nbsp;${mail?default('')?html}</td>
	   </tr>
       <tr>
	     <td class="grayStyle" width="25%" id="f_politicVisage">&nbsp;<@bean.message key="entity.politicVisage"/>：</td>
	     <td class="brightStyle">&nbsp;${politicVisageName?default('')?html}</td>
	   </tr>
	   
	   <#--留学生信息-->
	   <#if result.student.abroadStudentInfo?if_exists.id?exists>
	   <tr class="darkColumn">
	     <td align="center" colspan="2">留学信息</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;HSK等级</td>
	     <td class="brightStyle">&nbsp;<@i18nName (result.student.abroadStudentInfo.HSKDegree)?if_exists/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">
	      &nbsp;护照编号：
	     </td>
	     <td class="brightStyle">&nbsp;${(result.student.abroadStudentInfo.passportNo)?default('')}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">
	      &nbsp;护照到期时间：
	     </td>
	     <td class="brightStyle">&nbsp;${(result.student.abroadStudentInfo.passportDeadline)?default('')}
	     </td>
	   </tr>	
	   <tr>
	     <td class="grayStyle" width="25%" id="f_idCard">
	      &nbsp;护照类别：
	     </td>
	     <td class="brightStyle">&nbsp;${(result.student.abroadStudentInfo.passportType.id)?default("")}</td>
	   </tr>   	   
	   <tr>
	     <td class="grayStyle" width="25%">
	      &nbsp;签证编号：
	     </td>
	     <td class="brightStyle">&nbsp;${(result.student.abroadStudentInfo.visaNo)?default('')}</td>	     
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">
	      &nbsp;签证到期时间：
	     </td>
	     <td class="brightStyle">&nbsp;${(result.student.abroadStudentInfo.visaDeadline)?default('')}</td>
	   </tr>	
	   <tr>
	     <td class="grayStyle" width="25%" id="f_idCard">
	      &nbsp;护照类别：
	     </td>
	     <td class="brightStyle">&nbsp;${(result.student.abroadStudentInfo.visaType.id)?default("")}</td>
	   </tr>   
	   <tr>
	     <td class="grayStyle" width="25%">
	      &nbsp;居住许可证编号：
	     </td>
	     <td class="brightStyle">&nbsp;${(result.student.abroadStudentInfo.resideCaedNo)?default('')}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" >
	      &nbsp;居住许可证到期时间：
	     </td>
	      <td class="brightStyle">&nbsp;${(result.student.abroadStudentInfo.resideCaedDeadline)?default('')}</td>
	   </tr>  	  
	   </#if>
     </table>
    </td>
   </tr>
   </form>
   <form name="updateStudentBasicInfoForm" action="loadStudentBasicInfoUpdateForm.do" method="post">
      <input type="hidden" name="stdId" value="${stdId}"/>
      <input type="hidden" name="method" value="basicInfoUpdateForm"/>
   </form>
  </table>
 </body>
 <script language="javascript">
    function updateStudentBasicInfo(){
         document.updateStudentBasicInfoForm.submit();
    }
 </script>
<#include "/templates/foot.ftl"/>