<@table.table id="listTable" width="100%" sortable="true">
  <@table.thead>
      <@table.selectAllTd id="teachPlanId"/>
      <@table.sortTd width="10%" id="teachPlan.enrollTurn" name="attr.enrollTurn"/>
      <@table.sortTd width="10%" id="teachPlan.stdType.name" name="entity.studentType"/>
      <@table.sortTd width="16%" id="teachPlan.department.name" name="entity.college"/>
      <@table.td width="20%" name="entity.speciality"/>
      <@table.td width="20%" name="entity.specialityAspect" />
      <@table.td width="10%" name="attr.state"/>
      <@table.td width="15%" text="开课数/总课数"/>
    </@>
    <@table.tbody datas=teachPlans;plan>
     <@table.selectTd id="teachPlanId" value=plan.id/>
     <input type="hidden" name="${plan.id}" id="${plan.id}" value="<#if plan.isConfirm == true>1<#else>0</#if>"/>
     <td>${plan.enrollTurn}</td>
     <td title="<@i18nName plan.stdType/>" nowrap><span style="display:block;width:40px;overflow:hidden;text-overflow:ellipsis"><@i18nName plan.stdType/></span></td>
     <td title="<@i18nName plan.department/>" nowrap><span style="display:block;width:130px;overflow:hidden;text-overflow:ellipsis"><@i18nName plan.department/></span></td>
     <td title="<@i18nName plan.speciality?if_exists/>" nowrap><span style="display:block;width:160px;overflow:hidden;text-overflow:ellipsis"><@i18nName plan.speciality?if_exists/></span></td>
     <td title="<@i18nName plan.aspect?if_exists/>" nowrap><span style="display:block;width:200px;overflow:hidden;text-overflow:ellipsis"><@i18nName plan.aspect?if_exists/></span></td>
     <td><#if plan.isConfirm><@msg.message key="plan.affirm1"/><#else><@msg.message key="plan.affirm0"/></#if></td>
     <#assign resultValue = statResult[plan.id?string]?default(0)?int/>
     <td><a href="#" onclick="infoData('${plan.id}')"><#if (resultValue > 0)><font color="blue">${resultValue}</font><#else>${resultValue}</#if>/<font color="black">${plan.planCourses?size}</font></a></td>
    </@>
</@>
