<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<@getMessage/>
<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="teachPlanId"/>
      <@table.sortTd width="10%" id="teachPlan.enrollTurn" name="attr.enrollTurn"/>
      <@table.sortTd width="10%" id="teachPlan.stdType.name" name="entity.studentType"/>
      <@table.sortTd width="20%" id="teachPlan.department.name" name="entity.college"/>
      <@table.td width="20%" name="entity.speciality"/>
      <@table.td width="20%" name="entity.specialityAspect" />
      <@table.td name="attr.state"/>
    </@>
    <@table.tbody datas=teachPlans;plan>
     <@table.selectTd id="teachPlanId" value=plan.id/>
     <input type="hidden" name="${plan.id}" id="${plan.id}" value="<#if plan.isConfirm == true>1<#else>0</#if>"/>
     <td>${plan.enrollTurn}</td>
     <td title="<@i18nName plan.stdType/>" nowrap><span style="display:block;width:70px;overflow:hidden;text-overflow:ellipsis"><@i18nName plan.stdType/></span></td>
     <td title="<@i18nName plan.department/>" nowrap><span style="display:block;width:100px;overflow:hidden;text-overflow:ellipsis"><a href="teachPlanSearch.do?method=info&teachPlan.id=${plan.id}" title="<@i18nName plan.department/>"><@i18nName plan.department/></a></span></td>
     <td title="<@i18nName plan.speciality?if_exists/>" nowrap><span style="display:block;width:100px;overflow:hidden;text-overflow:ellipsis"><@i18nName plan.speciality?if_exists/></span></td>
     <td title="<@i18nName plan.aspect?if_exists/>" nowrap><span style="display:block;width:100px;overflow:hidden;text-overflow:ellipsis"><@i18nName plan.aspect?if_exists/></span></td>
     <td><#if plan.isConfirm><@msg.message key="plan.affirm1"/><#else><@msg.message key="plan.affirm0"/></#if></td>
    </@>
  </@>
</body>
<#include "/templates/foot.ftl"/>