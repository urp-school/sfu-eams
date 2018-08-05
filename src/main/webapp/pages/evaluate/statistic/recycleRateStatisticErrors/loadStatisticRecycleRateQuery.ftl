<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <script language="javascript">
 	function doStatistic(form){
 		form.departTempIdSeq.value=getAllOptionValue(form.Selecteddepartments);
 		form.stdTypeTempIdSeq.value=getAllOptionValue(form.SelectedStdType);
 		if(""==form.stdTypeTempIdSeq.value){
 			alert("请选择学生类别");
 			return;
 		}
 		if(""==form.teachCalendarTempIdSeq.value){
 			alert("请选择教学日历");
 			return;
 		}
 		if(""==form.departTempIdSeq.value){
 			alert("请选择部门");
 			return 
 		}
 		form.action ="questionnaireRecycleRateAction.do?method=doStatisticInCourseTask";
 		message.innerHTML+='<font color="red" size="0"><@bean.message key="field.questionnaire.select.initializeDate"/></font>'; 
 		forbidButton()
 		form.submit();
 	}
 	function doQuery(){
 		document.buttonForm.action="questionnaireRecycleRateAction.do?method=doQuery";
 		forbidButton()
 		document.buttonForm.submit();
 	}
 	function forbidButton(){
 		var button1 = document.getElementById("button1");
 		button1.disabled=true;
 	}
 </script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<form name="buttonForm" method="post" action="" onsubmit="return false;">
  <table id="backBar" width="100%"></table>
	<script>
	   var bar = new ToolBar('backBar','统计评教回收率',null,true,true);
	   bar.setMessage('<@getMessage/>');
	</script>
  <table width="100%"  class="frameTable" height="25px;">
  		<tr class="frameTable_view" align="right">
  			<td class="infoTitle" style="width:80px;"><@bean.message key="entity.studentType"/></td>
  <td  style="width:100px;">
      <input type="hidden" name="calendar.id" value="${calendar.id}" />
      <select id="stdType" name="calendar.studentType.id" style="width:100px;">               
        <option value="<#if studentType?exists>${studentType.id}<#else>${calendar.studentType.id}</#if>"></option>
      </select>
   </td>
   <td class="infoTitle" align="right" style="width:80px;"><@bean.message key="attr.year2year"/></td>
   <td style="width:100px;">
     <select id="year" name="calendar.year"  style="width:100px;">                
        <option value="${calendar.year}"></option>
      </select>
    </td>
    <td class="infoTitle" align="right" style="width:50px;"><@bean.message key="attr.term"/></td>
    <td style="width:60px;">     
     <select id="term" name="calendar.term" style="width:60px;">
        <option value="${calendar.term}"></option>
      </select>
   </td>
   <td style="width:80px" >
    <button style="width:80px" id="calendarSwitchButton" accessKey="W" onclick="javascript:changeCalendar(this.form);"class="buttonStyle">
       <@bean.message key="action.changeCalendar"/>(<U>W</U>)
    </button>
   </td>
   <script>
      var calendarAction=document.getElementById('calendarSwitchButton').form.action;
      function changeCalendar(form){
	      form['calendar.id'].value="";
	      var errorInfo="";      
	      if(form['calendar.year'].value=="") errorInfo+="请选择学年度\n";
	      if(form['calendar.term'].value=="") errorInfo+="请选择学期";
	      if(errorInfo!="") {alert(errorInfo);return;}
	      form.action=calendarAction;
	      form.target="";
	      form.submit();
      }   
   </script>
   <#include "/templates/calendarSelect.ftl"/>
</tr>
  </table>
  <table align="center" width="100%" class="listTable">
  	<tr>
  		<td class="darkColumn" colspan="2" align="center">统计评教结果</td>
  	</tr>
  	
  	<tr>
  		<td  align="center" class="grayStyle" width="10%"><@msg.message key="entity.studentType"/></td>
  		<td class="grayStyle">
  			<table>
  			<tr>
  			<td>
  			<select name="StdTypes" MULTIPLE size="8" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['StdTypes'], this.form['SelectedStdType'])" >
        	<#list stdTypes as stdType>
        		<option value="${stdType.id}">${stdType.name}</option>
        	</#list>
        	</select>
        	</td>
           	<td>
            <input OnClick="JavaScript:moveSelectedOption(this.form['StdTypes'], this.form['SelectedStdType'])" type="button" value="&gt;"> 
            <br>
            <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedStdType'], this.form['StdTypes'])" type="button" value="&lt;"> 
            </td>
            <td>
         <select name="SelectedStdType" MULTIPLE size="8" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedStdType'], this.form['StdTypes'])">
         </select>
         </td>
         </tr>
         </table>
         </td>
  	</tr>
  	<tr>
  		<td align="center" class="grayStyle">学年度学期</td>
  		<td class="brightStyle">
  			<#if stdType?exists>${stdType.name}<#else>${calendar.studentType.name}</#if>,${calendar.year},${calendar.term}
  		</td>
  	</tr>
  	<tr>
  		<td align="center" class="grayStyle">开课院系</td>
  		<td class="brightStyle">
  			<table>
  			<tr>
  			<td>
  				<select name="departments" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['departments'], this.form['Selecteddepartments'])" >
        				<#list departmentList?sort_by("name") as department>
        				<option value="${department.id}">${department.name}</option>
        				</#list>
        		</select>
  			</td>
  			<td>
  				<input OnClick="JavaScript:moveSelectedOption(this.form['departments'], this.form['Selecteddepartments'])" type="button" value="&gt;"> 
            		<br>
            	<input OnClick="JavaScript:moveSelectedOption(this.form['Selecteddepartments'], this.form['departments'])" type="button" value="&lt;"> 
  			</td>
  			<td>
  				<select name="Selecteddepartments" MULTIPLE size="10" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['Selecteddepartments'], this.form['departments'])">
         		</select>
  			</td>
  			</tr>
  			</table>
  		</td>
  	</tr>
  	<tr>
  		<td align="center" colspan="2" class="darkColumn">
  			<input type="hidden" name="departTempIdSeq" value="">
  			<input type="hidden" name="stdTypeTempIdSeq" value="">
  			<input type="hidden" name="teachCalendarTempIdSeq" value="${calendar.id}">
  			<input type="button" id="button1" value="<@bean.message key="field.recycleRate.statisticRecycleRate"/>" name="button1" onClick="doStatistic(this.form)" class="buttonStyle" />
  	</tr>
  </table>
  </form>
</body>
<#include "/templates/foot.ftl"/>