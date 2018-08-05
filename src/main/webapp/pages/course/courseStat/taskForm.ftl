<table width="100%" class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
	<form id="courseSearchForm" name="courseSearchForm" target="queryFrame" method="post" onsubmit="return false;">
		<tr>
			<td colspan="2" align="center"><b>查询条件</b></td>
		</tr>
	<tr> 
     <td class="infoTitle">学生类别:</td>
     <td >
          <@htm.i18nSelect name="teachTask.teachClass.stdType.id" style="width:100px;" datas=stdTypeList selected=""/>               
     </td>
	</tr>       
	<tr>
     <td class="infoTitle">开课院系:</td>
     <td ><@htm.i18nSelect name="teachTask.arrangeInfo.teachDepart.id" style="width:100px;" datas=departmentList selected="">
     	  <option value=""><@bean.message key="filed.choose" />...</option>
          </@>            
     </td>       
    </tr> 
		<tr>
			<td class="infoTitle">课程类别</td>
			<td colspan="3">
				<select name="teachTask.courseType.id" style="width:100px">
					<OPTION VALUE="">请选择</OPTION>
					<#list courseTypeList?if_exists as courseType>
						<option value="${courseType.id}">${courseType.name}</option>
					</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td class="infoTitle">从学年</td>
			<td>
				<select name="beginYear" size="1" style="width:100px;">
					<#list yearList?if_exists as year>
					<option value="${year}">${year}</option>
					</#list>
				</select>
			</td>
			<td>学期</td>
			<td>
				<select name="beginTerm" size="1" style="width:50px;">
					<#list termList?if_exists as term>
					<option value="${term}">${term}</option>
					</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td class="infoTitle">到学年</td>
			<td>
				<select name="endYear" size="1" style="width:100px;">
					<#list yearList?if_exists as year>
					<option value="${year}">${year}</option>
					</#list>
				</select>
			</td>
			<td>学期</td>
			<td>
				<select name="endTerm" size="1" style="width:50px;">
					<#list termList?if_exists as term>
					<option value="${term}">${term}</option>
					</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="4"  align="center">
				<button name="submitButton" onClick="search()">查询</button>&nbsp;
				<button name="submitButton1" onClick="stat()">统计</button>
			</td>
		</tr>
	</form>
</table>
<br><br><br><br><br><br><br><br>