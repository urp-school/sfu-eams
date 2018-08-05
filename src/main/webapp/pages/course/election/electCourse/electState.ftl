<table width="100%" align="center" class="listTable">
	  <#assign  electParams = electState.params>
	   <tr class="darkColumn">
	     <td align="center" colspan="4"><@bean.message key="entity.electParams" /> <@bean.message key="common.baseInfo"/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.electTurn" />：</td>	     
	     <td class="brightStyle">${electParams.turn?if_exists}</td>
	     <td class="grayStyle" width="25%">&nbsp;<@msg.message key="common.switch"/>：</td>	     
	     <td class="brightStyle"><#if electParams.isOpenElection><@msg.message key="common.opened"/><#else><@msg.message key="common.closed"/></#if></td>
	   </tr>
       <tr>
	   	 <td class="darkColumn" colspan="4">&nbsp;<@bean.message key="entity.electDateTime" />：</td>
       </tr>       
       <tr>
	   	 <td class="grayStyle" id="f_startDate">&nbsp;<@bean.message key="attr.startDate" />：</td>
	     <td class="brightStyle"> ${electParams.startDate?if_exists} </td>
	   	 <td class="grayStyle" id="f_startTime">&nbsp;<@bean.message key="attr.startTime" />：</td>
	     <td class="brightStyle"> ${electParams.startTime?if_exists} </td>
       </tr>
       <tr>
	   	 <td class="grayStyle" id="f_finishDate">&nbsp;<@bean.message key="attr.finishDate" />：</td>	
	     <td class="brightStyle"> ${electParams.finishDate?if_exists} </td>
	   	 <td class="grayStyle"  id="f_finishTime">&nbsp;<@bean.message key="attr.finishTime" />：</td>
	     <td class="brightStyle"> ${electParams.finishTime?if_exists} </td>
       </tr>
       <tr>
	   	 <td class="darkColumn"  colspan="4">&nbsp;<@bean.message key="entity.electMode" />：</td>
       </tr>
       <tr>
	   	 <td class="grayStyle" >&nbsp;<@bean.message key="attr.isOverMaxAllowed" />：</td>	
	     <td class="brightStyle"><#if electParams.isOverMaxAllowed?if_exists==true><@bean.message key="common.yes" /><#else><@bean.message key="common.no" /></#if></td>
	   	 <td class="grayStyle" >&nbsp;<@bean.message key="attr.isRestudyAllowed" />：</td>
	     <td class="brightStyle"><#if electParams.isRestudyAllowed?if_exists==true><@bean.message key="common.yes" /><#else><@bean.message key="common.no" /></#if></td>	     
       </tr> 
       <tr>
         <td class="grayStyle" >&nbsp;<@msg.message key="course.elect.withdrawMinimumPeople"/>：</td>
	     <td class="brightStyle"><#if electParams.isUnderMinAllowed?if_exists==true><@bean.message key="common.yes" /><#else><@bean.message key="common.no" /></#if></td>
         <td class="grayStyle" >&nbsp;重修时是否限制选课对象：</td>
	     <td class="brightStyle"><#if electParams.isCheckScopeForReSturdy?if_exists==true><@bean.message key="common.yes" /><#else><@bean.message key="common.no" /></#if></td>	     
       </tr>
       <tr>
         <td class="grayStyle" >&nbsp;<@bean.message key="attr.isCancelAnyTime" />：</td>
	     <td class="brightStyle"><#if electParams.isCancelAnyTime?if_exists==true><@bean.message key="common.yes" /><#else><@bean.message key="common.no" /></#if></td>	     
	   	 <td class="grayStyle">&nbsp;<@msg.message key="course.elect.thinkToAwardCredit"/>：</td>	
	     <td class="brightStyle"><#if electParams.isAwardCreditConsider?if_exists==true><@bean.message key="common.yes" /><#else><@bean.message key="common.no" /></#if></td>      
       </tr>
       <tr>
	   	 <td class="grayStyle">&nbsp;<@bean.message key="attr.isCheckEvaluation" />：</td>	
	     <td class="brightStyle"><#if electParams.isCheckEvaluation?if_exists==true><@bean.message key="common.yes" /><#else><@bean.message key="common.no" /></#if>
	     </td>         
	   	 <td class="grayStyle">&nbsp;<@bean.message key="attr.floatCredit" />：</td>
	     <td class="brightStyle">${electParams.floatCredit?if_exists}</td> 	
       </tr>
       <tr>
	   	 <td class="grayStyle">&nbsp;是否限制校区：</td>	
	     <td class="brightStyle">${electParams.isSchoolDistrictRestrict?string("是","否")}</td>
	   	 <td class="grayStyle">&nbsp;是否计划内课程：</td>	
	     <td class="brightStyle">${electParams.isInPlanOfCourse?string("是","否")}</td>
	     <#--
	     <td class="brightStyle" colspan="2"><#if electState.std.HSKDegree?exists><@msg.message key="common.HSKDegree"/>:${electState.std.HSKDegree}</#if>
	     <#if electState.std.languageAbility?exists><#if electState.std.languageAbility?string == "3"><@msg.message key="course.EnglishA"/><#elseif electState.std.languageAbility?string == "2"><@msg.message key="course.EnglishB"/><#elseif electState.std.languageAbility?string == "1"><@msg.message key="course.EnglishC"/><#else>&nbsp;</#if></#if>
	     -->
	     </td>
       </tr>
       <tr>
	   	 <td class="grayStyle" >&nbsp;<@msg.message key="course.proposedCredit"/>：</td>
	     <td class="brightStyle" colspan="3">
			<#list electState.creditNeeds as creditNeed>
				<#assign creditNeedName><@i18nName creditNeed.type/></#assign>
               	<@bean.message key="course.getCredits" arg0=creditNeedName arg1=(creditNeed.credit)?string/><br>
            </#list>
	     </td> 
       </tr>
       <tr>
	   	 <td class="grayStyle" >&nbsp;<@bean.message key="attr.notice" />：</td>	
	     <td class="brightStyle" colspan="3"> ${electParams.notice?if_exists}
	     </td>
       </tr>
       <tr>
	   	 <td class="grayStyle" colspan="4" align="center"><button class="buttonStyle" onclick="displayElectState(true)"><@msg.message key="action.close1"/></button></td>
       </tr>
  </table>