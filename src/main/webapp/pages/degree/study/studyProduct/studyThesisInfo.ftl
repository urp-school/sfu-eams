<table width="100%" align="center" class="infoTable">
	<tr>
		<td class="title" style="width:25%"><@msg.message key="attr.stdNo"/> ：</td>
		<td>${studyProduct.student?if_exists.code?if_exists}</td>
		<td class="title" style="width:25%"><@msg.message key="attr.personName"/> ：</td>
		<td>${studyProduct.student?if_exists.name}</td>
	</tr>
	   <tr>
	     <td class="title">论文题目:</td>
	     <td>${studyProduct.name?if_exists}</td>
	     <td class="title">刊物名称:</td>
	     <td>${studyProduct.publicationName?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="title">刊物等级:</td>
	     <td><@i18nName studyProduct.publicationLevel/></td>
	     <td class="title">期刊号:</td>
	     <td>${studyProduct.publicationNo?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="title">发表年月：</td>
	     <td><#if studyProduct.publishOn?exists>${studyProduct.publishOn?string("yyyy-MM-dd")}</#if></td>
	     <td class="title">科研排序：</td>
	     <td>${studyProduct.rate?if_exists}</td>
	   </tr>	   
	   <tr>
	     <td class="title">分摊字数：</td>
	     <td>${studyProduct.wordCount?if_exists}万字</td>
	     <td class="title">论文总字数：</td>
	     <td>${studyProduct.totalCount?if_exists}万字  </td>
	   </tr>
	   <tr>
	   		<td class="title" >论文索引收录：</td>
	     	<td colspan="3"><#list studyProduct.indexes as index>${(index.thesisIndexType.name)?if_exists}收录&nbsp;检索号${index.thesisIndexNo?if_exists}<br></#list></td>
	   </tr>	   	   
	 </table>