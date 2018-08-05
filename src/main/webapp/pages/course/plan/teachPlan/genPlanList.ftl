<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table width="100%" align="center" class="listTable">
    <tr><td class="darkColumn" colspan="10" align="center">共生成${teachPlanList?size}个培养计划</td></tr>
    <tr align="center" class="darkColumn">
      <td width="2%"></td>
      <td width="10%"><@bean.message key="attr.enrollTurn"/></td>
      <#if RequestParameters['genPlanType']=="speciality">
      <td width="10%"><@bean.message key="entity.studentType"/></td>
      <#else>
      <td width="10%"><@bean.message key="std.code"/></td>
      <td width="10%"><@bean.message key="attr.personName"/></td>
      </#if>
      <td width="15%"><@bean.message key="entity.college"/></td>
      <td width="20%"><@bean.message key="entity.speciality"/></td>
      <td width="25%"><@bean.message key="entity.specialityAspect" /></td>
    </tr>   
    <#list teachPlanList as plan>
	  <#if plan_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if plan_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" align="center"
        onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)" 
        onclick="onRowChange(event)">
     <td  class="select">
        <input type="radio" name="teachPlanId" value="${plan.id?if_exists}">
        <input type="hidden" name="${plan.id}" id="${plan.id}" value="<#if plan.isConfirm == true>1<#else>0</#if>"/>
     </td>
     <td>&nbsp;${plan.enrollTurn}</td>
     <#if RequestParameters['genPlanType']=="speciality">
     <td>&nbsp;<@i18nName plan.stdType/></td>
     <#else>
     <td>${plan.std.code}</td>
     <td>${plan.std.name?if_exists}</td>
     </#if>
     <td>&nbsp;<a href="teachPlanSearch.do?method=info&teachPlan.id=${plan.id}" title="查看计划详细信息">
         <@i18nName plan.department/></a></td>
     <td>&nbsp;<@i18nName plan.speciality?if_exists/></td>
     <td>&nbsp;<@i18nName plan.aspect?if_exists/></td>
    </tr>
    </#list>
  </table>
  
  <script language="javascript">
    function pageGo(pageNo){
       self.parent.pageGo(pageNo);
    }
  </script>
  </body>
<#include "/templates/foot.ftl"/>