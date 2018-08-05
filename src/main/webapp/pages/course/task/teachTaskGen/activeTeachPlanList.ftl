<#include "/templates/head.ftl"/>

<BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="taskGenBar"></table>
  <script>
    var  bar =new ToolBar("taskGenBar","<@bean.message key="info.taskGen.activeSchemeList"/>",null,true,true);
    bar.setMessage("<#if result.teachPlanList?size!=0>共${result.teachPlanList?size}个活跃的培养计划</#if>");
    bar.addItem("<@bean.message key="action.genTeachTask" />","gen()");
  </script>
 
  <#if result.teachPlanList?size!=0>   
  <table width="100%" align="center" class="listTable" style="font-size:13px">
     <form name="planListForm" method="post" action="" onsubmit="return false;"> 
      <input type="hidden" name="task.calendar.id" value="${calendar.id}" />
  
     <tr align="center" class="darkColumn">
       <td align="center"  width="3%">
         <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('teachPlanId'),event);">
       </td>
       <td width="8%"><@bean.message key="entity.studentType" /></td>
       <td width="6%"><@bean.message key="attr.enrollTurn" /></td>
       <td width="18%"><@bean.message key="common.college"/></td>
       <td width="18%" ><@bean.message key="entity.speciality"/></td>
       <td width="22%" ><@bean.message key="entity.specialityAspect"/></td>
       <td width="8%">是否确认</td>
     </tr>

     <#list result.teachPlanList as plan>
   	  <#if plan_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if plan_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}"  onclick="onRowChange(event)">
       <td class="select" >
          <input type="checkBox" name="teachPlanId" value="${plan.id}">
       </td>
       <td>&nbsp;<@i18nName plan.stdType/></td>
       <td>&nbsp;${plan.enrollTurn}</td>
       <td><a href="teachPlanSearch.do?method=info&teachPlan.id=${plan.id}" >&nbsp;${plan.department.name}</a></td>
       <td>&nbsp;<@i18nName plan.speciality?if_exists/></td>
       <td>&nbsp;<@i18nName plan.aspect?if_exists/></td>
       <td>${plan.isConfirm?string("确认","<font color='red'>未确认</font>")}</td>
     </tr>
     </#list>
     </form>
  </table>
   <#else>
   <br>
   <#assign calendarInfo><@i18nName calendar.studentType/>, ${calendar.year} <@bean.message key="attr.year2year"/>, ${calendar.term}</#assign>
   <@bean.message key="info.taskGen.noActiveScheme" arg0=calendarInfo/>
   </#if>
  <script language="javascript">
     function getIds(){
       return(getCheckBoxValue(document.getElementsByName("teachPlanId")));
    }
    function gen(){
     var teachPlanIds = getIds();
     if(teachPlanIds ==""){
       window.alert('<@bean.message key="common.select"/>!');
       return;
     }
     if(confirm("<@bean.message key="prompt.taskGen.gen" arg0="${calendar.year}" arg1="${calendar.term}"/>")){
        var form = document.planListForm;
        form.action = "teachTaskGen.do?method=genSetting&reGen=0&teachPlanIds=" +teachPlanIds;        
        form.submit();
     }
    }
  </script>
</body>
<#include "/templates/foot.ftl"/>