<table  width="100%"  class="frameTable"> 
	<tr>
		<td width="18%" class="frameTable_view" valign="top">
			<table width="100%" class="searchTable">
				<form name="listForm" method="post" target="answerFrame" action="" onSubmit="return false;">
				<#assign stdTypeName>stdTypeOfSpeciality</#assign>
				<#include "../../stdConditions.ftl"/>
			  <tr>
			  <td>是否申请：</td>
			  <td><select name="hasApply" style="width:100px">
					<option value="true">已申请</option>
					<option value="false">未申请</option>
					</select>
				</td>
				</tr>
				<tr>
				 <td>是否通过：</td>
				 <td><select name="preAnswer.isPassed" style="width:100px">
					   <option value="">全部</option>
					   <option value="1">通过</option>
					   <option value="0">未通过</option>
					   <option value="null">未设置</option>
					  </select>
				 </td>
				</tr>
		     	<tr>
		     		<td>起始时间：</td>
		     		<td ><input type="text" name="beginOn" value="" onfocus="calendar()" style="width:100px;" maxlength="10"/></td>
		     	</tr>
		     	<tr>
		     		<td>结束时间：</td>
		     		<td ><input type="text" name="endOn" value="" onfocus="calendar()" style="width:100px;" maxlength="10"/></td>
		     	</tr>
				<#if extraTr?exists>${extraTr}</#if>
				 <tr height="50px">
			     <td colspan="2" align="center">
			       <button name="button9" onClick="search()" class="buttonStyle"><@msg.message key="system.button.query"/></button>&nbsp; 
		         </td>
			   </tr>
			   <#assign stdTypeNullable=true>
			   <#include "/templates/stdTypeDepart3Select.ftl"/>
			</form>
			</table>
		</td>
	    <td valign="top">
			<iframe name="answerFrame" 
		     marginwidth="0" marginheight="0" scrolling="no"
		     frameborder="0"  height="100%" width="100%">
		  	</iframe>
		</td>
		</tr>
	</table>