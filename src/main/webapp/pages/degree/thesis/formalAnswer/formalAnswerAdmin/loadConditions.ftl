<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','论文答辩',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
<table width="100%" class="frameTable"> 
	<tr>
		<td width="21%" class="frameTable_view" valign="top">
			<table width="100%" class="searchTable">
				<form name="listForm" method="post" target="answerFrame">
				<#assign stdTypeName></#assign>
        <tr>
            <td align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>查询条件</B></td>
       </tr>
        <tr>
            <td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/></td>
        </tr>
	   <tr>
	   	  <td width="40%"><@msg.message key="attr.stdNo"/>：</td>
		  <td><input type="text" name="formalAnswer.student.code" maxlength="32" size="10" style="width:100px"/></td>
	   </tr>
	   <tr>
	   	  <td><@msg.message key="attr.personName"/>：</td>
		  <td><input type="text" name="formalAnswer.student.name" size="10" maxlength="20" style="width:100px"/></td>
	   </tr>
	   <tr>
		  <td><@msg.message key="entity.studentType"/>：</td>
		  <td align="bottom"  width="40%">
			  <select id="stdTypeOfSpeciality" name="formalAnswer.student.type.id" style="width:100px"></select>
		  </td>
	 </tr>
	   <tr>
	   	  <td width="30%"><@msg.message key="filed.enrollYearAndSequence"/>：</td>
		  <td><input type="text" id="erollYear" name="formalAnswer.student.enrollYear" maxlength="7" style="width:100px"/></td>
	   </tr>
	   <tr>
	   	  <td><@msg.message key="entity.college"/>：</td>
	      <td>
	     	<select id="department" name="formalAnswer.student.department.id" style="width:100%;"></select>
	      </td>
	   </tr>
	   <tr>
	   		<td><@msg.message key="entity.speciality"/>：</td>
		    <td><select id="speciality" name="formalAnswer.student.firstMajor.id" style="width:100px"></select></td>
	   </tr>
	   <tr>
	   	  <td><@msg.message key="entity.specialityAspect"/>：</td>
		  <td><select id="specialityAspect" name="formalAnswer.student.firstAspect.id" style="width:100px"></select></td>
	   </tr>
		<tr>
			<td>是否通过：</td>
			<td>
			 	<select name="formalAnswer.isPassed" style="width:100px">
			 		<option value="">全部</option>	
			 		<option value="true">通过</option>	
			 		<option value="false">不通过</option>	
			 	</select>
		 	</td>
		</tr>
		<tr>
  			<td>提交起始日：</td>
  			<td><input type="text" name="finishOnStart" style="width:100px;" onfocus="calendar()" maxlength="10"/></td>
		</tr>
		<tr>
  			<td>提交结束日：</td>
  			<td><input type="text" name="finishOnEnd" style="width:100px;" onfocus="calendar()" maxlength="10"/></td>
		</tr>
		<tr height="50px">
			<td colspan="2" align="center">
				<button name="button9" onClick="search()" class="buttonStyle"><@bean.message key="system.button.query"/></button>&nbsp; 
	        </td>
		</tr>
			   <#assign stdTypeNullable=true>
			   <#include "/templates/stdTypeDepart3Select.ftl"/>
			</form>
			</table>
		</td>
	    <td valign="top">
			<iframe name="answerFrame" marginwidth="0" marginheight="0" scrolling="no"
		     frameborder="0"  height="100%" width="100%">
		  	</iframe>
		</td>
		</tr>
	</table>
<script>
	var form =document.listForm;
    function search(){
    	 form.action ="formalAnswerAdmin.do?method=doQuery&flag=datas";
      	 form.submit();
    }
    search();
</script>
</body>     
<#include "/templates/foot.ftl"/>