<#include "/templates/head.ftl"/>
<body LEFTMARGIN="0" TOPMARGIN="0">
<table id="taskConfirmBar"></table>
<table width="100%" class="frameTable">
	<tr>
		<td valign="top" class="frameTable_view" width="20%">
			<table width="100%" align="center" class="searchTable" style="width:100%;padding:0px;border-spacing">
				<form name="conditionForm" method="post" action="" onsubmit="return false;">
				<input type="hidden" name="statistic" value="statistic">
				<tr>
	  			<td align="center" colspan="2">条件</td>
	  			</tr>
	  			<tr>
 				<td id="f_belongToYear" width="30%">入学年份</td>
			  <td width="60%"><input type="text" style="width:100%" id="erollYear" maxlength="7" name="thesisManage.student.enrollYear" style="width:100px"/></td>
			 	</tr>
		   	<tr>
		   		<td><@msg.message key="entity.studentType"/>：</td>
			   	<td align="bottom">
			     <@htm.i18nSelect datas=stdTypeList id="stdTypeOfSpeciality" selected="" name="thesisManage.student.type.id" style="width:100%;">
						<option value=""><@msg.message key="common.selectPlease"/></option>
					</@>
			    </td>
			  </tr>
	  			<tr><td ><@msg.message key="entity.college"/>：</td>
		   		<td><select id="department" name="thesisManage.student.department.id" style="width:100%;">
		     	 <option value=""><@msg.message key="common.selectPlease"/>...</option>
		      </select> 
		      </td>
		    </tr>
		    <tr>
		   		<td><@msg.message key="entity.speciality"/>：</td>
		   		<td ><select id="speciality" name="thesisManage.student.firstMajor.id" style="width:100%;">
		     	 <option value=""><@msg.message key="common.selectPlease"/>...</option>
		      </select>
		      
		   		</td>
		   	</tr>
		   	<tr>
		   		<td><@msg.message key="entity.specialityAspect"/>：</td>
		   		<td >
		      		<select id="specialityAspect" name="thesisManage.student.firstAspect.id" style="width:100%;">
		     	 	 <option value=""><@msg.message key="common.selectPlease"/>...</option>
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
			<iframe src="#" id="contentFrame" name="contentFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
		</td>
	</tr>
</table>
	<script>
		var bar=new ToolBar("taskConfirmBar","论文进度统计",null,true,true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		function search(){
			 var form =document.conditionForm;
			 	  conditionForm.action="thesisStatistic.do?method=scheduleStatistic";
			  conditionForm.target="contentFrame"
			  form.submit();
		}
		search();
	</script>
</body>
<#include "/templates/foot.ftl"/>