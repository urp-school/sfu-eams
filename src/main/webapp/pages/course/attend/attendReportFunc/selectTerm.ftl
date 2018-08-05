<#include "/templates/head.ftl"/>
<#assign hiddenCalendarStudentType=false>
  <#if result?if_exists.stdTypeList?exists>
    <#if (result.stdTypeList?size==1)><#assign hiddenCalendarStudentType=true></#if>
  <#elseif stdTypeList?exists>
    <#if (stdTypeList?size==1)><#assign hiddenCalendarStudentType=true></#if>
 </#if>
    <form method="post" action=""  name="actionForm" >
<table width="100%"   class="listTable">
  <tr>
  <td width="50%"  class="infoTitle" align="right" style="<#if hiddenCalendarStudentType>display:none</#if>" width="15%"><@msg.message key="calendar.type"/></td>
  <td colspan="2" align="bottom"  style="width:100px;<#if hiddenCalendarStudentType>display:none</#if>" >
      <input type="hidden" name="calendar.id" value="${calendar.id}"/>
      <select id="stdType" name="calendar.studentType.id" style="width:100px;">
        <option value="${studentType.id}"></option>
      </select>
  </td>
   </tr>
  <tr >
   <td width="50%"  align="right" style="width:80px;"><@bean.message key="attr.year2year"/></td>
   <td >
   <select id="year" name="calendar.year" style="width:100px;">
        <option value="${calendar.year}"></option>
   </select>
   </td>
   </tr>
  <tr >
   <td width="50%" align="right" style="width:50px;"><@bean.message key="attr.term"/></td>
   <td >
     <select id="term" name="calendar.term" style="width:60px;">
        <option value="${calendar.term}"></option>
      </select>
   </td>
   </tr>
  <tr >
   <td style="width:100%;text-align:center;"  colspan="2" >
    <button align="center" style="width:80px" id="calendarSwitchButton" accessKey="W" onclick="javascript:changeCalendar(this.form);"class="buttonStyle">
       	统计
    </button>
   </td>
	</tr>
		</table>
	</form>
    <script>
      var calendarAction=document.getElementById('calendarSwitchButton').form.action;
      function changeCalendar(form){
	      form['calendar.id'].value="";
	      var errorInfo="";
	      if(form['calendar.year'].value=="") errorInfo+="请选择学年度\n";
	      if(form['calendar.term'].value=="") errorInfo+="请选择学期";
	      if(errorInfo!="") {alert(errorInfo);return;}
	      form.action="attendReportFunc.do?method=reportStatic";
	      form.submit();
      }
   </script>
   <#include "/templates/calendarSelect.ftl"/>
<#include "/templates/foot.ftl"/>