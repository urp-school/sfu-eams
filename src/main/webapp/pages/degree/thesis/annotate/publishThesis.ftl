<#assign stringSeq>${annotateSeq?if_exists}</#assign>
  <table width="100%" align="center" class="listTable">
  <tbody id='addGradeTable'>
  	<tr align="left">
  		<td class="grayStyle" colspan="5"><#if flag?exists&&"std"==flag>选择与学位论文相关的在校期间已公开发表的论文<#elseif flag?exists&&"admin"==flag>学生在校期间发布和毕业论文相关的文章和论文</#if></td>
  	</tr>
    <tr align="center" class="grayStyle">
      <td width="2%"align="center" >
         <input type="checkBox" onClick="toggleCheckBox(document.getElementsByName('publishThesisId'),event);">
      </td>
      <td width="15%"><@bean.message key="filed.thesisTitle" /></td>
      <td width="15%"><@bean.message key="filed.publishName" /></td>
      <td width="10%"><@bean.message key="filed.publishSequence" /></td>
      <td width="7%"><@bean.message key="common.remark" /></td>
    </tr>
    <#if annotateSeq?exists>
    	<#assign publicshThesiss=result.publishThesis?if_exists/>
    <#else>
        <#assign publicshThesiss=result.thesisAnnotate?if_exists.publishThesisSet?if_exists/>
    </#if>
    <#list publicshThesiss?if_exists as publishThesis>
	  	<#if publishThesis_index%2==1 ><#assign class="grayStyle" ></#if>
	  	<#if publishThesis_index%2==0 ><#assign class="brightStyle" ></#if>
	    <tr class="${class}" id="row${publishThesis_index}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)"  align="center" >
	     <td><input type="checkBox" id="publishThesisId" name="publishThesisId" value="${publishThesis.id}" <#if annotateSeq?exists&&(","+stringSeq+",")?contains(","+publishThesis.id+",")>checked</#if>></td>
      	<td>${publishThesis.name?if_exists}</td>
      	<td>${publishThesis.publicationName?if_exists}</td>
      	<td>${publishThesis.publicationNo?if_exists}</td>
      	<td>${publishThesis.remark?if_exists}</td>
	  	</#list>
    <#if flag?exists&&flag=="std">
    <tr><td class="brightStyle" colspan="5">
    1.论文自评部分必须选择<font color="red">你发表的并且院系审核通过</font>的论文,如果没有可以选择的论文 请先到科研--我的科研里面添加.
    2.论文选项前面打勾说明这个论文在 论文盲评的时候会被参考
    </td></tr>
    </#if>
	</tbody>
  </table>