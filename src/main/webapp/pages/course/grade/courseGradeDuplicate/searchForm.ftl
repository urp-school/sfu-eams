<#assign extraSearchTR>
	    <tr>
	     <td class="infoTitle">期末成绩:</td>
	     <td>
		     <select name="gradeState.confirmGA" value="" style="width:100px" value="${RequestParameters["gradeState.confirmGA"]?if_exists}">
		     	<option value="1">录入两次</option>
		     	<option value="0">两次以下</option>
		     </select>
	     </td>
	    </tr>
		<tr>
			<td>发布状态:</td>
			<td>
				<select name="publishStatus" style="width:100px" value="${RequestParameters["publishStatus"]?if_exists}">
					<option value="">全部</option>
					<option value="1">最终成绩已发布</option>
					<option value="0">最终成绩未发布</option>
				</select>
			</td>
		</tr>
		<input type="hidden" name="task.courseType.isPractice" value="1"/>
</#assign>
<#include "../taskBasicForm.ftl">