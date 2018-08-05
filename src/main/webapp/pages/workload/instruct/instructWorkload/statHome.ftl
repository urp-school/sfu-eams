<#include "/templates/head.ftl"/>
 <script language="javascript">
 	function doStatistic(form){
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
 		if(confirm("你要统计选择的指导工作量吗 如果统计过,相应的工作量会被覆盖.")){
 			form.action ="instructWorkload.do?method=statistic";
 			message.innerHTML+='<font color="red" size="5">数据统计中,请稍候。。</font>'; 
 			form.submit();
 		}
 	}
 </script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<form name="buttonForm" action="instructWorkload.do?method=statHome" method="post" onsubmit="return false;">
  <table id="backBar" width="100%"></table>
	<script>
	   var bar = new ToolBar('backBar','统计指导工作量',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addClose("<@msg.message key="action.close"/>");
	</script>
  <table width="80%"  class="frameTable_view" align="center" >
  		<tr align="right">
      <td>|</td>
      <#include "/pages/course/calendar.ftl"/>      
   </tr>
  </table>
  
  <table align="center" width="80%"  class="formTable">
  	<tr>
  	   <td class="title">工作量类别</td>
  	   <td>
       <select name="instructModulus.itemType" style="width:100px;">
			<option value="thesis" <#if RequestParameters['instructModulus.itemType']?default('thesis')="thesis">selected</#if>>毕业指导</option>
			<option value="practice" <#if RequestParameters['instructModulus.itemType']?default('thesis')="practice">selected</#if>>实习指导</option>	
			<option value="usual" <#if RequestParameters['instructModulus.itemType']?default('thesis')="usual">selected</#if>>平时指导</option>
	 </select>
	 </td>
	 </tr>
  	<tr>
  		<td  align="center" class="title" width="10%"><@msg.message key="entity.studentType"/></td>
  		<td>
  			<table>
  			<tr>
  			<td>
  			<select name="StdTypes" MULTIPLE size="8" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['StdTypes'], this.form['SelectedStdType'])" >
        	<#list calendarStdTypes as stdType>
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
  		<td align="center" class="title">学生所在部门：</td>
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
  			<input type="hidden" name="departIdSeq" value="">
  			<input type="hidden" name="stdTypeIdSeq" value="">
  			<input type="hidden" name="calendarId" value="${calendar.id}">
  			<button name="button1" onClick="doStatistic(this.form)" class="buttonStyle" >开始统计</button>
  	</tr>
  </table>
  </form>
</body>
<#include "/templates/foot.ftl"/>