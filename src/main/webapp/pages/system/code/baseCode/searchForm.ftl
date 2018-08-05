  <table onkeypress="DWRUtil.onReturn(event, searchBaseCode)" width="100%">
    <tr>
      <td colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>基础代码查询</B>
      </td>
    <tr>
      <td colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
   </tr>
   <form name="baseCodeSearchForm" action="" method="post" target="basecodeListFrame" onsubmit="return false;">
   <tr><td><@bean.message key="entity.baseCode"/>:</td>
       <td>
      	  <select name="codeName" style="width:100px;">
      	  <#if localName?index_of("en")==-1>
        	  <#list coders?sort_by("name") as coder>
        	    <option value="${coder.className}" <#if RequestParameters['className']?if_exists==coder.className> selected </#if>>${coder.name}</option>
        	  </#list>
      	  <#else>
              <#list coders?sort_by("engName") as coder>
        	    <option value="${coder.className}" <#if RequestParameters['className']?if_exists==coder.className> selected </#if>>${coder.engName}</option>
              </#list>
      	  </#if>
     </td>
    </tr>
    <tr><td><@bean.message key="attr.code"/>:</td><td><input type="text" name="baseCode.code" style="width:100px;" maxlength="32"/></td></tr>
    <tr><td><@bean.message key="attr.infoname"/>:</td><td><input type="text" name="baseCode.name" style="width:100px;" maxlength="25"/></td></tr>
    <#include "../../base.ftl"/>
    <@stateSelect "baseCode"/>
    <tr height="50px"><td colspan="2" align="center"><button onclick="searchBaseCode()"><@bean.message key="action.query"/></button></td></tr>
    </form>
  </table>