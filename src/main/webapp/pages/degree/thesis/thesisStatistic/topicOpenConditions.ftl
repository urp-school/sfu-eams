<#include "/templates/head.ftl"/>
<body LEFTMARGIN="0" TOPMARGIN="0">
<table id="taskConfirmBar"></table>
	<script>
		var bar=new ToolBar("taskConfirmBar","论文开题统计",null,true,true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		function search(){
		   	var form =document.conditionForm;
   			conditionForm.action="thesisStatistic.do?method=topicOpenStatistic&flag=list";
   			conditionForm.target="contentFrame"
   			form.submit();
		}
	</script>
<table width="100%" class="frameTable">
	<tr>
		<td valign="top" class="frameTable_view" width="20%">
			<table  width="100%" align="center" class="searchTable">
				<form name="conditionForm" action="thesisStatistic.do?method=topicOpenStatistic&flag=list" method="post" onsubmit="return false;">
				<tr>
	    			<td align="center" colspan="2">条件</td>
	   			</tr>
	   			<tr>
 				<td id="f_belongToYear">入学年份：</td>
			    <td><input type="text" style="width:100px" id="erollYear" maxlength="7" name="student.enrollYear">
			 	</td>
			 	</tr>
		     	<tr>
		     		<td><@bean.message key="entity.studentType"/>：</td>
			      	<td align="bottom" >
			          <select id="stdTypeOfSpeciality" name="student.type.id" style="width:100px">
			            <option value=""><@bean.message key="common.selectPlease"/></option>
			            <#list stdTypeList?if_exists as stdType>
			            	<option value="${stdType.id}">${stdType.name?if_exists}</option>
			            </#list>
			          </select>
			       </td>
			    </tr>
	   			<tr>
		     		<td><@bean.message key="entity.college"/>：</td>
		     		<td>
		           	<select id="department" name="student.department.id" style=="width:100px">
		         	  <option value=""><@bean.message key="common.selectPlease"/>...</option>
		           </select> 
		           </td>
		        </tr>
		        <tr>
		     		<td><@bean.message key="entity.speciality"/>：</td>
		     		<td>
		           	<select id="speciality" name="student.firstMajor.id" style=="width:100px">
		         	  <option value=""><@bean.message key="common.selectPlease"/>...</option>
		           </select>
		     		</td>
		     	</tr>
		     	<tr>
		     		<td><@bean.message key="entity.specialityAspect"/>:</td>
		     		<td>
		           		<select id="specialityAspect" name="student.firstAspect.id" style=="width:100px">
		         	 	 <option value=""><@bean.message key="common.selectPlease"/>...</option>
		           	</select>
		     		</td>
		     	</tr>
			    <tr height="50px">
			    	<td colspan="2" align="center">
			    		<button onclick="search();" class="buttonStyle">查询</button>
			    	</td>
			    </tr>
				<#include "/templates/stdTypeDepart3Select.ftl"/>	
				</form>
			</table>
		</td>
		<td valign="top">
			<iframe src="thesisStatistic.do?method=topicOpenStatistic&flag=list" id="contentFrame" name="contentFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"  height="100%" width="100%">
		</td>
	</tr>
</table>
</body>
<#include "/templates/foot.ftl"/>