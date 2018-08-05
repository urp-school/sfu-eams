<@table.table  width="100%" id="listTable" sortable="true">
		<@table.thead>
			<@table.selectAllTd id="instructWorkloadId"></@>
			<@table.sortTd name="workload.teacherName" id="instructWorkload.teacherInfo.teacherName"/>
			<@table.sortTd name="workload.teacherDepart" id="instructWorkload.teacherInfo.teachDepart.name"/>
  			<@table.sortTd text="工作量系数" id="instructWorkload.modulus.modulusValue"/>
  			<@table.sortTd text="带学生人数" id="instructWorkload.studentNumber"/>
  			<@table.sortTd name="instructWorkload.instructWorkload" id="instructWorkload.totleWorkload"/>
  			<@table.sortTd name="workload.payBill" id="instructWorkload.payReward"/>
  			<@table.sortTd name="workload.calcWorkload" id="instructWorkload.calcWorkload"/>
  			<@table.sortTd name="workload.teacherAffirm" id="instructWorkload.teacherAffirm"/>
  			<@table.td name="workload.academicTerm"/>
  		</@>
  		<@table.tbody datas=instructWorkloads;instructWorkload>
	   	  <td width="5%" align="center" bgcolor="#CBEAFF"><input type="checkBox" name="instructWorkloadId" value="${instructWorkload.id}"></td>
	   	  <td align="center">${instructWorkload.teacherInfo.teacherName?if_exists}</td>
	   	  <td align="center">${instructWorkload.teacherInfo.teachDepart?if_exists.name?if_exists}</td>
	   	  <td align="center">${instructWorkload.modulus.modulusValue?default(0)?string("###0.0")}</td>
	   	  <td align="center">${instructWorkload.studentNumber}</td>
	   	  <td align="center">${instructWorkload.totleWorkload?default(0)?string("###0.0")}</td>
	   	  <td align="center">
	   	  <#if instructWorkload.payReward==true><@bean.message key="workload.true"/>
	   	  <#else><@bean.message key="workload.false"/>
	   	  </#if></td>
	   	  <td align="center">
	   	  <#if instructWorkload.calcWorkload==true><@bean.message key="workload.true"/>
	   	  <#else><@bean.message key="workload.false"/>
	   	  </#if></td>
	   	  <td><#if instructWorkload.teacherAffirm==true><@bean.message key="workload.true"/>
	   		  <#else><@bean.message key="workload.false"/>
	   		  </#if></td>
	   	  <td align="center">${instructWorkload.teachCalendar?if_exists.year}&nbsp;${instructWorkload.teachCalendar?if_exists.term}</td>
	   	</@>
	<form name="selectForm" method="post" action="" onsubmit="return false;">
		<input type="hidden" name="titles" value="教师工号,姓名,所在部门,职称,性别,教职工类别,年龄,带学生类别,带学生人数,系数值,总工作量,教师确认,支付报酬确认,计工作量确认,学年度,学期,备注">
		<input type="hidden" name="keys" value="teacherInfo.teacher.code,teacherInfo.teacherName,teacherInfo.teachDepart.name,teacherInfo.teacherTitle.name,teacherInfo.gender.name,teacherInfo.teacherType.name,teacherInfo.teacherAge,studentType.name,studentNumber,modulus.modulusValue,totleWorkload,teacherAffirm,payReward,calcWorkload,teachCalendar.year,teachCalendar.term,remark">
 	</form>
</@>