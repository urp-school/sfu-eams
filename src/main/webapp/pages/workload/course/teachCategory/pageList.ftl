<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <script language="javascript">
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("teachCategoryId")));
    }

    function pageGo(pageNo){
       document.pageGoForm.pageNo.value = pageNo;
       document.pageGoForm.submit();
    }
    function updateObject(){
    	var id = getIds();
    	if(id==""||id.indexof(',')>0){
    		alert("请选择一个对象");
    		return;
    	}
    	document.listForm.action="teachCategoryAction.do?method=doUpdate&teachCategoryId="+id;
    	document.listForm.submit();
    }
    </script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
   <tr>
   		<td>
   			<#assign labInfo><@bean.message key="field.teachCategory.teachCategoryList"/></#assign>
   			<#include "/templates/back.ftl"/>
   		</td>
   </tr>
   <tr>
     <td>
     <table width="100%" align="center" class="listTable">
       <form name="listForm" method="post" action="" onsubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('teachCategoryId'),event);"></td>
	     <td><@bean.message key="field.teachCategory.teachCategoryCode"/></td>
	     <td><@bean.message key="field.teachCategory.teachCategoryName"/></td>
	     <td><@bean.message key="field.teachCategory.studentTypeName"/></td>
	     <td><@bean.message key="field.teachCategory.courseTypeName"/></td>
	     <td><@bean.message key="field.teachCategory.teachCategorystatus"/></td>
	     <td><@bean.message key="attr.remark"/></td>
	   </tr>
	   <#list result.teachCategoryPage.items?if_exists as teachCategory>
	   <#if teachCategory_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if teachCategory_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td width="5%" align="center" bgcolor="#CBEAFF"><input type="checkBox" name="teachCategoryId" value="${teachCategory.id}"></td>
	    <td width="5%" align="center">
	     &nbsp;${teachCategory.code}		
	    </td>
	    <td width="10%" align="center">
	     &nbsp;${teachCategory.name}		
	    </td>
	    <td width="15%" align="center">
	     &nbsp;${(teachCategory.studentType?if_exists).name}		
	    </td>
	    <td width="15%" align="center">
	     &nbsp;${(teachCategory.courseType?if_exists).name?if_exists}	
	    </td>
	    <td width="20%" align="center">
	     &nbsp;
	      <#if teachCategory.status==false><@bean.message key="field.evaluate.estate0"/></#if>
	      <#if teachCategory.status==true><@bean.message key="field.evaluate.estate1"/></#if>	     
	     		
	    </td>
	    <td width="15%" align="center">
	     &nbsp;${teachCategory.remark?if_exists}	
	    </td>
	   </tr>
	   </#list>
	   </form>
	   <form name="pageGoForm"  method="post">
	    <#assign paginationName="teachCategoryPage" />
	    <#include "/templates/pageBar.ftl"/>
	    </form> 
	  </table>
	  </td>
	  </tr>
	  </table>
	  </body>
<#include "/templates/foot.ftl"/>