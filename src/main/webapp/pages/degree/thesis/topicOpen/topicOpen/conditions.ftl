<table width="100%" class="frameTable"> 
		<tr>
			<td width="18%" class="frameTable_view" valign="top">
				<table width="100%">
  				<form name="pageGoForm" action="#" target="thesisTopicOpenFrame" method="post" onsubmit="return false;">
  				<#assign stdTypeName>stdTypeOfSpeciality</#assign>
				<#include "../../stdConditions.ftl"/>
					<tr>
					<td>是否开题：</td>
					<td><select name="hasThesisOpen" style="width:100px">
						<option value="true">已开题</option>
						<option value="false">未开题</option>
						</select>
					</td>
					</tr>
					<tr>
					    <td>是否通过：</td>
						<td><select name="topicOpen.isPassed" style="width:100px">
							<option value="">全部</option>
							<option value="1">开题通过</option>
							<option value="2">开题修改后答辩</option>
							<option value="3">开题不通过</option>
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
				<tr height="50px">
				    <td colspan="2" align="center"> 
				       <button onClick="search()"><@msg.message key="system.button.query"/></button>&nbsp;  
				    </td>
				</tr>
				<#assign stdTypeNullable=true>
				<#include "/templates/stdTypeDepart3Select.ftl"/>
				</form>
				</table>
		  </td>
		  <td valign="top">
		  <iframe id="thesisTopicOpenFrame" name="thesisTopicOpenFrame" 
		 marginwidth="0" marginheight="0" scrolling="no"
		 frameborder="0"  height="100%" width="100%">
		  </iframe>
  		</td>
  		</tr>
 </table>