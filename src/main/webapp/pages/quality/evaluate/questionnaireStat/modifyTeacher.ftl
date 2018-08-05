<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 	<table id="backBar"></table>
  	<table cellpadding="0" cellspacing="0" class="listTable" width="100%">
  		<form name="listForm" method="post" action="" onsubmit="return false;">
  		<tr class="darkColumn" align="center">
  			<td colspan="2">修改教师</td>
  		</tr>
  		<tr class="grayStyle">
  			<td width="30%">
  				现在教师:${questionnaireStat.teacher.name}
  			</td>
  			<td>
  				修改后教师:<select id="teachDepartment" name="teachDepartment" style="width:150px">
	       		</select>
   	       		<select id="teacher" name="newTeacherId" style="width:100px">
	       		</select>
	       		<#include "/templates/departTeacher2Select.ftl"/>
  			</td>
  		</tr>
  		<tr class="darkColumn">
  			<td colspan="2" align="center">
  				<input type="hidden" name="questionnaireStatId" value="${questionnaireStat.id}"/>
  				<input type="button" name="button" value="修改教师" onclick="doModity()" class="buttonStyle"/>
  			</td>
  		</tr>
  		</form>
  </table>
  <script>
   var bar = new ToolBar('backBar','修改教师',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack('<@bean.message key="action.back"/>');
   var form=document.listForm;
   function doModity(){
   		var selectTeacher = document.getElementById("teacher");
   		if(""==selectTeacher.value){
   			alert("请选择教师!");
   			return;
   		}else{
			form.action="questionnaireStat.do?method=saveTeacher";
			setSearchParams(parent.searchForm,form);
			form.submit(); 		
   		}
   }
</script>
</body>
<#include "/templates/foot.ftl"/>