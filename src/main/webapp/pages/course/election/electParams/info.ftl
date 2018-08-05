<#include "/templates/head.ftl"/>
<body>
	<#assign labInfo><B><#if electParams.id?exists><@bean.message key="action.new"/> <#else><@bean.message key="action.modify"/> </#if><@bean.message key="entity.electParams"/></B></#assign>
	<#include "/templates/back.ftl"/>
  	<table style="width:80%" align="center" class="infoTable">
	   	<tr class="darkColumn">
	     	<td align="center" colspan="4"><@bean.message key="entity.electParams"/> <@bean.message key="common.baseInfo"/></td>
	   	</tr>
	   <tr>
	     <td class="title" width="25%" >&nbsp;<@bean.message key="attr.electTurn"/>：</td>	     
	     <td>${electParams.turn?if_exists}</td>
       	 <td class="title"  width="25%">&nbsp;<@bean.message key="attr.electState"/>：</td>
	     <td>${electParams.isOpenElection?string("开放","关闭")}</td>
	   </tr>
       <tr>
	   	 <td class="darkColumn"  id="f_name" colspan="4">&nbsp;<@bean.message key="attr.electParamsForStd"/>：</td>
       </tr>
       <tr>
         <td class="title"  id="f_enrollTurns">&nbsp;<@bean.message key="attr.enrollTurn"/>：</td> 	     
 	     <td  colspan="3"><#list electParams.enrollTurns as enrollTurn>${enrollTurn},</#list></td>
       </tr>
       
     <tr>
         <td class="title"  id="f_stdTypeIds">&nbsp;<@bean.message key="entity.studentType"/>：</td> 
         <td colspan="3"><#list electParams.stdTypes as stdType><@i18nName stdType/>&nbsp;</#list></td>
       </tr> 
       
       <tr>
         <td class="title" >&nbsp;<@bean.message key="entity.college"/>：</td> 
         <td colspan="3"><#list electParams.departs as depart><@i18nName depart/>&nbsp;</#list></td>
       </tr> 
       <tr>
         <td class="title" >&nbsp;<@bean.message key="entity.speciality"/>：</td> 
         <td colspan="3"><#list electParams.majors as major><@i18nName major/>&nbsp;</#list></td>
       </tr> 
       <tr>
         <td class="title" >&nbsp;<@bean.message key="entity.specialityAspect"/>：</td> 
         <td colspan="3"><#list electParams.majorFields as majorField><@i18nName majorField/>&nbsp;</#list></td>
       </tr> 
       <tr>
         <td class="title" >&nbsp;特殊学生：(${electParams.stds?size})</td> 
         <td colspan="3"><#list electParams.stds as std>${std.name}(${std.code})&nbsp;</#list></td>
       </tr> 
       <tr>
	   	 <td class="darkColumn"  colspan="4">&nbsp;<@bean.message key="entity.electDateTime"/>：</td>
       </tr>       
       <tr>
	   	 <td class="title"  id="f_startDate">&nbsp;<@bean.message key="attr.startDate"/>：</td>
	     <td>${electParams.startDate?if_exists?string("yyyy-MM-dd")} &nbsp;${electParams.startTime?if_exists}</td>
	   	 <td class="title"  id="f_startTime">&nbsp;<@bean.message key="attr.finishDate"/>：</td>
	     <td>${(electParams.finishDate?string("yyyy-MM-dd"))?if_exists}&nbsp;${electParams.finishTime?if_exists}</td>
       </tr>
       <tr>
	   	 <td class="darkColumn"   colspan="4">&nbsp;<@bean.message key="entity.electMode"/>：</td>
       </tr>       
       <tr>
	   	 <td class="title"  >&nbsp;<@bean.message key="attr.isOverMaxAllowed"/>：</td>	
	     <td >${electParams.isOverMaxAllowed?string("是","否")}</td>
	   	 <td class="title"  >&nbsp;<@bean.message key="attr.isRestudyAllowed"/>：</td>
	     <td >${electParams.isRestudyAllowed?string("是","否")}</td>
       </tr> 
       <tr>
         <td class="title" >&nbsp;退课时允许人数下限：</td>
	     <td >${electParams.isUnderMinAllowed?string("是","否")}</td>
         <td class="title" >&nbsp;重修是否限制选课范围：</td>
	     <td >${electParams.isCheckScopeForReSturdy?string("是","否")}</td>
       </tr>
       <tr>
         <td class="title"  >&nbsp;<@bean.message key="attr.isCancelAnyTime"/>：</td>
	     <td >${electParams.isCancelAnyTime?string("是","否")}</td>
	   	 <td class="title" >&nbsp;是否考虑奖励学分：</td>	
	     <td >${electParams.isAwardCreditConsider?string("是","否")}</td>
       </tr>
       <tr>
	   	 <td class="title" >&nbsp;<@bean.message key="attr.isCheckEvaluation"/>：</td>	
	     <td >${electParams.isCheckEvaluation?string("是","否")}</td>
	   	 <td class="title"  id="f_floatCredit">&nbsp;<@bean.message key="attr.floatCredit"/>：</td>
	     <td >${electParams.floatCredit?if_exists}</td>   
       </tr>
       <tr>
         <td class="title" >&nbsp;是否限制校区：</td>
	     <td colspan="3">${electParams.isSchoolDistrictRestrict?string("是","否")}</td>
       </tr>
       <tr>
	   	 <td class="title"  id="f_notice">&nbsp;<@bean.message key="attr.notice"/>：</td>	
	     <td  colspan="3">${electParams.notice?if_exists}</td>
       </tr>
  </table>
 </body>
<#include "/templates/foot.ftl"/>