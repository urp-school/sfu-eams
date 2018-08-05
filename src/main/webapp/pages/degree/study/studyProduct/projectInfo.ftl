
<table width="100%" align="center" class="infoTable">
	<tr>
		<td class="title" style="width:25%"><@msg.message key="attr.stdNo"/>:</td>
		<td>${studyProduct.student?if_exists.code?if_exists}</td>
		<td class="title" style="width:25%"><@msg.message key="attr.personName"/>:</td>
		<td>${studyProduct.student?if_exists.name}</td>
	</tr>
	   <tr>
	     <td class="title"> 项目名称:</td>
	     <td>${studyProduct.name?if_exists}</td>
	     <td class="title">项目编号:</td>
	     <td>${studyProduct.projectNo?if_exists}</td>
	   </tr>
	    <tr>
	     <td class="title" >立项单位:</td>
	     <td>${studyProduct.company?if_exists}</td>
	     <td class="title"> 项目类别:</td>
	     <td><@i18nName studyProduct.projectType/></td>
	   </tr>
	   <tr>
	     <td class="title">立项日期：</td>
	     <td><#if studyProduct.startOn?exists>${studyProduct.startOn?string("yyyy-MM-dd")}</#if></td>
	     <td class="title">结项日期：</td>
	     <td><#if studyProduct.endOn?exists>${studyProduct.endOn?string("yyyy-MM-dd")}</#if></td>
	   </tr>
	   <tr>
	     <td class="title">承担任务：</td>
	     <td>${studyProduct.bearTask?if_exists}</td>
	     <td class="title" id="f_principal">负责人：</td>
	     <td>${studyProduct.principal?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="title" id="f_researchSort">科研排序：</td>
	     <td>${studyProduct.rate?if_exists}</td>
	     <td class="title"  id="f_wordCountByPerson">分摊字数：</td>
	     <td>${studyProduct.wordCount?if_exists}万字</td>
	   </tr>
	 </table>
