<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="taskGenBar"></table>
  <script>
    var  bar =new ToolBar("taskGenBar","<@bean.message key="info.taskGen.unActiveSchemeList"/>",null,true,true);
    bar.setMessage("<#if result.teachPlanList?size!=0>共(${result.teachPlanList?size}个)已生成任务的培养计划</#if>");
    bar.addItem("<@bean.message key="teachTask.generation.look" />","loadTaskForScheme()");
    bar.addItem("重新生成","reGen()");
  </script>  
  <#if result.teachPlanList?size!=0>
  <@table.table id="listTable" width="100%">
   <@table.thead>
     <@table.selectAllTd id="teachPlanId"/>
       <td width="8%"><@bean.message key="entity.studentType" /></td>
       <td width="6%"><@bean.message key="attr.enrollTurn" /></td>
       <td width="18%"><@bean.message key="common.college"/></td>
       <td width="18%"><@bean.message key="entity.speciality"/></td>
       <td width="22%"><@bean.message key="entity.specialityAspect"/></td>
       <td width="8%">是否确认</td>
     </@>
     <@table.tbody datas=result.teachPlanList;plan>
       <@table.selectTd id="teachPlanId" value="${plan.id}"/>
       <td>&nbsp;<@i18nName plan.stdType/></td>
       <td align="center">&nbsp;${plan.enrollTurn}</td>
       <td align="center">
        <a href="teachPlanSearch.do?method=info&teachPlan.id=${plan.id}" >&nbsp;<@i18nName plan.department/></a>
       </td>
       <td align="center">&nbsp;<@i18nName plan.speciality?if_exists/></td>
       <td align="center">&nbsp;<@i18nName plan.aspect?if_exists/></td>
       <td>${plan.isConfirm?string("确认","<font color='red'>未确认</font>")}</td>
     </@>
    </@>   
   <#else>
   <br>
   <#assign calendarInfo><@i18nName calendar.studentType/>, ${calendar.year} <@bean.message key="attr.year2year"/>, ${calendar.term}</#assign>
	<@bean.message key="info.cultivateScheme.notFound" arg0=calendarInfo/>
   </#if>  
  <form name="planListForm" action="" method="post" onsubmit="return false;">
   <input type="hidden" name="task.calendar.id" value="${calendar.id}" />
   <input type="hidden" name="calendar.id" value="${calendar.id}" />
 </form>
  <script language="javascript">
     function getIds(){
       return(getCheckBoxValue(document.getElementsByName("teachPlanId")));
    }
    
    function loadTaskForScheme(){
     var form = document.planListForm;
     var teachPlanIds = getSelectId("teachPlanId");
     if(teachPlanIds ==""){
       window.alert('<@bean.message key="common.select"/>!');
       return;
     }
     form.action ="teachTaskGen.do?method=listTask&fromPlan.ids=" + teachPlanIds ;
     form.submit();
    }
    
    function reGen(){
     var schemeElems= document.getElementsByName('teachPlanId');
     if(schemeElems.length==0) return;
     var teachPlanIds = getIds();
     if(teachPlanIds ==""){
       window.alert('<@bean.message key="common.select"/>!');
       return;
     }
     if(confirm("重新生成教学任务，将会删除已经生成的教学任务，确认重新生成吗?")){
        var form = document.planListForm;
        form.action = "teachTaskGen.do?method=genSetting&teachPlanIds=" +teachPlanIds+"&reGen=1";
        form.submit();
     }
    }
  </script>
</body>
<#include "/templates/foot.ftl"/>