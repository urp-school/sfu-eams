<#include "/templates/head.ftl"/>
 <body>
 <table class="frameTable_title">
    <form name="searchForm" method="post" action="instructorStd.do?method=printMultiStdGrade">
    <input type="hidden" name="adminClassIds" value="${RequestParameters['adminClassId']}" /> 
    <tr>	
	   <td>
	     学年学期<select id="calendarId" name="calendar.id"  style="width:200px;" onchange="printMultiStdGrade(this.form)">                
			<option value="">请选择...</option>
	        <#list calendar?sort_by("start")?reverse as calendar>
	        <option value="${calendar.id}">${calendar.year +" "+ calendar.term}</option>
	        </#list>
	     </select>
	     <button style="width:80px" id="calendarSwitchButton" accessKey="W" onclick="printMultiStdGrade(this.form)">
	       提交(<U>W</U>)
	     </button>
	   </td>
   </tr>
   </form>
		<tr valign="top">
			<td ><iframe name="pageFrame" id="pageFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe></td>
		</tr>
  </table>
<script>
    function printMultiStdGrade(form){
    	if(form["calendar.id"].value==""){alert("请选择学年学期!"); return;}
   		form.action = "instructorStd.do?method=classGradeReport";
   		form.target="pageFrame";
   		form.submit();
    }
</script> 
 </body>   
<#include "/templates/foot.ftl"/> 