<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body >
<#assign labInfo><#if localName?index_of("en")!=-1>${coder.engName}<#else>${coder.name}</#if>&nbsp;&nbsp;<@bean.message key="entity.baseCode"/></#assign>  
<#include "/templates/back.ftl"/>
  
  <table width="95%" align="center" class="formTable">
    <form action="baseCode.do?method=save" name="baseCodeForm" method="post" onsubmit="return false;">
    <@searchParams/>
    <tr class="darkColumn">
      <td align="left" colspan="4"><B>${labInfo}</B></td>
    </tr>
    <tr>
      <td width="15%" id="f_code" class="title"><font color="red">*</font><@bean.message key="attr.code"/>:</td>
      <td width="35%" >
      <input id="codeValue" type="text" name="baseCode.code" value="${baseCode.code?if_exists}" style="width:80px;" maxlength="32"/>
      	  <#assign className>${RequestParameters['codeName']}</#assign>
      	  <#include "/pages/system/checkCode.ftl"/>
      <input type="hidden" name="baseCode.id" value="${baseCode.id?if_exists}" >
      </td>
      <td width="15%" class="title"><@bean.message key="attr.createAt"/>: </td>
      <td width="35%" >${(baseCode.createAt?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
    <tr>
      <td id="f_name" class="title"><font color="red">*</font><@bean.message key="attr.infoname"/>:</td>
      <td><input type="text" name="baseCode.name" value="${baseCode.name?if_exists}" maxlength="25"/></td>	   
      <td class="title">  <@bean.message key="attr.modifyAt"/>:</td>
      <td>${(baseCode.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
    <tr >
      <td id="f_engName" class="title"><@bean.message key="attr.engName"/>:</td>
      <td><input type="text" name="baseCode.engName" value="${baseCode.engName?if_exists}" maxlength="50"/></td>	   
      <td id="f_engName" class="title">是否使用:</td>
      <td><@htm.radio2  name="baseCode.state" value=baseCode.state?default(true)/>
      </td>
    </tr>    
    <tr  class="darkColumn" align="center">
      <td colspan="6">
          <input type="hidden" name="baseCodeId" value="${baseCode.id?if_exists}"/>
          <input type="hidden" name="codeName" value="${RequestParameters['codeName']}"/>
          <#if baseCode.createAt?exists>
          <input type="hidden" name="baseCode.createAt" value="${baseCode.createAt?string("yyyy-MM-dd")}"/>
          </#if>
          <input type="button" onClick='submit1(this.form)' value="<@bean.message key="system.button.submit"/>" class="buttonStyle"/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;           
      </td>
    </tr>
  </form> 
  </table>
  <script language="javascript" > 
    function reset(){
  	   document.baseCodeForm.reset();
    } 
    function submit1(form){        
         var a_fields = {
         'baseCode.code':{'l':'<@bean.message key="attr.code"/>', 'r':true, 't':'f_code','mx':'32'},
         'baseCode.name':{'l':'<@bean.message key="attr.name"/>', 'r':true, 't':'f_name'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }  
  </script>
  </body> 
</html>