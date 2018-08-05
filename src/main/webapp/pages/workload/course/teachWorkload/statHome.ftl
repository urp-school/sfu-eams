<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">

  <table id="backBar" width="100%"></table>
	<script>
	   var bar = new ToolBar('backBar','统计教学工作量',null,true,true);
	   bar.setMessage('<@getMessage/>');
	</script>
  <table width="100%"  class="frameTable" height="25px;">
  <form name="buttonForm" method="post" action="" onsubmit="return false;">
  		<tr class="frameTable_view" align="right">
  			<td class="infoTitle" style="width:80px;"><@bean.message key="entity.studentType"/></td>
  <td  style="width:100px;">
      <input type="hidden" name="calendar.id" value="${calendar.id}" />
      <select id="stdType" name="calendar.studentType.id" style="width:100px;">
        <option value="<#if stdType?exists>${stdType.id}<#else>${calendar.studentType.id}</#if>"></option>
      </select>
   </td>
   <td class="infoTitle" align="right" style="width:80px;"><@bean.message key="attr.year2year"/></td>
   <td style="width:100px;">
     <select id="year" name="calendar.year" style="width:100px;">
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
  		<td class="darkColumn" colspan="2" align="center">统计教学工作量</td>
  	</tr>
  	
  	<tr>
  		<td  align="center" class="grayStyle" width="10%"><@msg.message key="entity.studentType"/></td>
  		<td class="grayStyle">
  			<table>
  			<tr>
  			<td>
  			<select name="StdTypes" MULTIPLE size="5" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['StdTypes'], this.form['SelectedStdType'])" >
        	<#list stdTypes?sort_by("name") as stdType>
        		<option value="${stdType.id}">${stdType.name}</option>
        	</#list>
        	</select>
        	</td>
           	<td>
            <input OnClick="JavaScript:moveSelectedOption(this.form['StdTypes'], this.form['SelectedStdType'])" type="button" value="&gt;"/>
            <br>
            <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedStdType'], this.form['StdTypes'])" type="button" value="&lt;"/>
            </td>
            <td>
         <select name="SelectedStdType" MULTIPLE size="5" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedStdType'], this.form['StdTypes'])">
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
  		<td align="center" class="grayStyle">部门名称</td>
  		<td class="brightStyle">
  			<table>
  			<tr>
  			<td>
  				<select name="departments" MULTIPLE size="5" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['departments'], this.form['Selecteddepartments'])" >
					<#list departmentList?sort_by("name") as department>
					<option value="${department.id}">${department.name}</option>
					</#list>
        		</select>
  			</td>
  			<td>
  				<input OnClick="JavaScript:moveSelectedOption(this.form['departments'], this.form['Selecteddepartments'])" type="button" value="&gt;"/>
            		<br>
            	<input OnClick="JavaScript:moveSelectedOption(this.form['Selecteddepartments'], this.form['departments'])" type="button" value="&lt;"/>
  			</td>
  			<td>
  				<select name="Selecteddepartments" MULTIPLE size="5" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['Selecteddepartments'], this.form['departments'])">
         		</select>
  			</td>
  			</tr>
  			</table>
  		</td>
  	</tr>
  	<tr>
  		<td align="center" colspan="2" class="darkColumn">
  			<input type="hidden" name="departIdSeq" value=""/>
  			<input type="hidden" name="stdTypeIdSeq" value=""/>
  			<input type="hidden" name="calendarIdSeq" value="${calendar.id}"/>
  			<button name="button1" onClick="doStatistic()" class="buttonStyle">统计</button>
  	</tr>
  	</form>
  </table>
  
  <script language="javascript">
  	var form=document.buttonForm;
 	function doStatistic(){
 		form.departIdSeq.value=getAllOptionValue(form.Selecteddepartments);
 		form.stdTypeIdSeq.value=getAllOptionValue(form.SelectedStdType);
 		if(""==form.stdTypeIdSeq.value){
 			alert("请选择学生类别");
 			return;
 		}
 		if(""==form.departIdSeq.value){
 			alert("请选择部门");
 			return 
 		}
 		if(confirm("你要统计教学工作量吗 如果统计过,相应的工作量会被覆盖.")){
 			form.action ="teachWorkload.do?method=statistic";
 			message.innerHTML+='<font color="red" size="5">数据统计中,请稍候。。</font>';
 			form.submit();
 		}
 	}
 </script>
</body>
<#include "/templates/foot.ftl"/>