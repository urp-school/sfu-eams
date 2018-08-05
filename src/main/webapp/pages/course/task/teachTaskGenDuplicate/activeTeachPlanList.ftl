<#include "/templates/head.ftl"/>

<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="taskGenBar"></table>

  <#if result.teachPlanList?size!=0>
  <@table.table width="100%" id="teachPlan">
     <@table.thead>
        <@table.selectAllTd id="teachPlanId"/>
       <@table.td width="8%" name="entity.studentType"/></td>
       <@table.td width="6%" name="attr.enrollTurn"/></td>
       <@table.td width="18%" name="common.college"/></td>
       <@table.td width="18%" name="entity.speciality"/></td>
       <@table.td width="22%" name="entity.specialityAspect"/></td>
       <@table.td width="8%" text="是否确认"/>
     </@>
     <@table.tbody datas=result.teachPlanList;plan>
        <@table.selectTd id="teachPlanId" value=plan.id/>
        <td><@i18nName plan.stdType/></td>
        <td>${plan.enrollTurn}</td>
        <td><a href="teachPlanSearch.do?method=info&teachPlan.id=${plan.id}">${plan.department.name}</a></td>
        <td><@i18nName plan.speciality?if_exists/></td>
        <td><@i18nName plan.aspect?if_exists/></td>
        <td>${plan.isConfirm?string("确认","<font color='red'>未确认</font>")}</td>
     </@>
  </@>
   <#else>
   <br>
   <#assign calendarInfo><@i18nName calendar.studentType/>, ${calendar.year} <@bean.message key="attr.year2year"/>, ${calendar.term}</#assign>
   <@bean.message key="info.taskGen.noActiveScheme" arg0=calendarInfo/>
   </#if>
    <form name="planListForm" method="post" action="" onsubmit="return false;">
      <input type="hidden" name="task.calendar.id" value="${calendar.id}"/>
    </form>
  <script>
    var  bar =new ToolBar("taskGenBar","<@bean.message key="info.taskGen.activeSchemeList"/>（实践）",null,true,true);
    bar.setMessage("<#if result.teachPlanList?size!=0>共${result.teachPlanList?size}个活跃的培养计划</#if>");
    bar.addItem("生成实践任务","gen()");
    
    var form = document.planListForm;
    
    function gen(){
        var teachPlanIds = getSelectIds("teachPlanId");
        if (isEmpty(teachPlanIds)){
            window.alert('<@bean.message key="common.select"/>!');
            return;
        }
        if (confirm("<@bean.message key="prompt.taskGen.gen" arg0=calendar.year arg1=calendar.term/>")) {
            form.action = "teachTaskGenDuplicate.do?method=genSetting";
	        addInput(form, "reGen", "0", "hidden");
	        addInput(form, "teachPlanIds", teachPlanIds, "hidden");
            form.submit();
        }
    }
  </script>
</body>
<#include "/templates/foot.ftl"/>