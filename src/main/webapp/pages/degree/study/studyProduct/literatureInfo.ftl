<table width="100%" align="center" class="infoTable">
	<tr>
		<td class="title" style="width:25%"><@msg.message key="attr.stdNo"/> ：</td>
		<td>${studyProduct.student?if_exists.code?if_exists}</td>
		<td class="title" style="width:25%"><@msg.message key="attr.personName"/> ：</td>
		<td>${studyProduct.student?if_exists.name}</td>
	</tr>
   <tr>
     <td class="title">著作名称：</td>
     <td>${studyProduct.name?if_exists} </td>
     <td class="title">出版社名称：</td>
     <td>${studyProduct.publishCompany?if_exists} </td>
   </tr>
   <tr>
     <td class="title" >著作类别：</td>
     <td><@i18nName studyProduct.literatureType/></td>
     <td class="title" id="f_publishDate">发表出版年月：</td>
     <td><#if studyProduct.publishOn?exists>${studyProduct.publishOn?string("yyyy-MM-dd")}</#if></td>
   </tr>
   <tr>
     <td class="title" >科研排序：</td>
     <td>${studyProduct.rate?if_exists}</td>
     <td class="title" >分摊字数：</td>
     <td>${studyProduct.wordCount?if_exists} 万字</td>
   </tr>  
   <tr>  
     <td class="title" >论文总字数：</td>
     <td>${studyProduct.totalCount?if_exists} 万字</td>
   </tr>		   	   
   </table>