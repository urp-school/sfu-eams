  <table width="100%" align="center" class="listTable">
  <tbody id='addGradeTable'>
  	<tr align="left">
  		<td class="grayStyle" colspan="5"><#if flag?exists&&"std"==flag>选择与学位论文相关的在校期间已公开发表的论文<#elseif flag?exists&&"admin"==flag>学生在校期间发布和毕业论文相关的文章和论文</#if></td>
  	</tr>
    <tr align="center" class="title" style="text-align:center">
      <td width="15%"><@bean.message key="filed.thesisTitle" /></td>
      <td width="15%"><@bean.message key="filed.publishName" /></td>
      <td width="10%"><@bean.message key="filed.publishSequence" /></td>
      <td width="7%"><@bean.message key="common.remark" /></td>
    </tr>
    <#list (thesisAnnotate.publishThesisSet)?if_exists as publishThesis>
	    <tr class="${class}" align="center">
      	<td>${publishThesis.name?if_exists}</td>
      	<td>${publishThesis.publicationName?if_exists}</td>
      	<td>${publishThesis.publicationNo?if_exists}</td>
      	<td>${publishThesis.remark?if_exists}</td>
      	</tr>
	</#list>
	</tbody>
  </table>