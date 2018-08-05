<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','<@msg.message key="field.teachProduct.detailInfo"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack();
</script>
	<table width="100%" align="center" class="infoTable">
		<tr>
		    <td class="title" width="25%"><@msg.message key="field.teachProduct.department"/></td>
			<td>${teachProduct.department?if_exists.name?if_exists}</td>
		   	<td class="title" width="25%"><@msg.message key="field.teachProduct.teachers"/></td>
			<td id="f_teachers">${teachProduct.cooperateOfTeacher?if_exists}</td>
		</tr>
		<tr>
		    <td class="title"><@msg.message key="field.teachProduct.productName"/></td>
			<td id="f_productName">${teachProduct.productName?if_exists}</td>
		    <td class="title">获奖名称:</td>
			<td>${teachProduct.awardName?if_exists}</td>
		</tr>
		<tr>
		    <td class="title">颁奖机构</td>
			<td>${teachProduct.giveAwardPlace?if_exists}</td>
		    <td class="title"><@msg.message key="field.teachProduct.productType"/></td>
		    <td id="f_productionType">${teachProduct.productionType?if_exists.name?if_exists}</td>
		</tr>
		<tr>
		   <td class="title"><@msg.message key="field.teachProduct.productionAwardType"/></td>
		   <td >${teachProduct.productionAwardType?if_exists.name?if_exists}</td>
	      <td class="title"><@msg.message key="field.teachProduct.productionAwardLevel"/></td>
		  <td >${teachProduct.productionAwardLevel?if_exists.name?if_exists}</td>
	   </tr>
	   <tr>
	       <td class="title"><@msg.message key="field.teachProduct.awardTime"/></td>
		   <td><#if (teachProduct.awardTime)?exists>${teachProduct.awardTime?string("yyyy")}</#if></td>
		    <td class="title"><@msg.message key="field.teachProduct.remark"/></td>
			<td>${teachProduct.remark?if_exists}</td>
		</tr>
	</table>
  </body>
<#include "/templates/foot.ftl"/>