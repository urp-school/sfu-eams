  <#if (calendarSchemes?size>0)>
  <td class="infoTitle" align="right" style="" width="15%"><@msg.message key="calendar.type"/></td>
  <td align="bottom"  style="width:100px;" >
      <select id="calendarSchemeId" name="calendarScheme.id" style="width:100px;">               
        <option value="${calendar.scheme.id}"></option>
      </select>
   </td>
   </#if>
   <td class="infoTitle" align="right" style="width:80px;"><@bean.message key="attr.yearAndTerm"/></td>
   <td style="width:150px;">
     <select id="calendarId" name="calendar.id"  style="width:100%;" onchange="changeCalendar(this.form)">                
        <option value="${calendar.id}"></option>
     </select>
   </td>
   <td style="width:80px" >
    <button style="width:80px" id="calendarSwitchButton" accessKey="W" onclick="javascript:changeCalendar(this.form);">
       <@bean.message key="action.changeCalendar"/>(<U>W</U>)
    </button>
   </td>
   <script>
      var calendarAction=document.getElementById('calendarSwitchButton').form.action;
      function changeCalendar(form){
	      if(form['calendar.id'].value="") {alert("请选择学年度学期\n");return;}
	      document.getElementById("calendarSwitchButton").disabled=true;
	      form.action=calendarAction;
	      form.submit();
      }
   </script>
   <#include "/templates/newCalendarSelect.ftl"/>