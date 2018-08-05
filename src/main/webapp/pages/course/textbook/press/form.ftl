<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body >
<#assign labInfo>出版社信息</#assign>  
<#include "/templates/back.ftl"/>
  <table width="95%" align="center"  class="formTable">
    <form action="press.do?method=save" name="baseCodeForm" method="post" onsubmit="return false;">
    <@searchParams/>
    <tr class="darkColumn">
      <td align="left" colspan="4"><B>${labInfo}</B></td>
    </tr>
    <tr>
      <td  width="15%" id="f_code" class="title"><font color="red">*</font><@bean.message key="attr.code" />:</td>
      <td  width="35%">
      <input id="codeValue" type="text" maxlength="32" name="press.code" value="${press.code?if_exists}" style="width:80px;" />
      	  <#assign className>Press</#assign>
      	  <#include "/pages/system/checkCode.ftl"/>
      <input type="hidden" name="press.id" value="${press.id?if_exists}"/>
      </td>
      <td width="15%" class="title"><@bean.message key="attr.createAt"/>:</td>
      <td width="35%">
      ${(press.createAt?string("yyyy-MM-dd"))?if_exists}
      </td>
    </tr>
    <tr>
      <td id="f_name" class="title"><font color="red">*</font><@bean.message key="attr.infoname"/>:</td>
      <td><input type="text" name="press.name" maxlength="20" value="${press.name?if_exists}"></td>
      <td class="title">  <@bean.message key="attr.modifyAt"/>:</td>
      <td>${(press.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
    <tr>
      <td id="f_examType" class="title"><@msg.message key="entity.pressLevel"/>:</td>
      <td colspan="3">
          <@htm.i18nSelect datas=pressLevels selected=(baseCode.level.id)?default("")?string name="press.level.id">
            <option value="">无</option>
          </@>
      </td>
    </tr>  
    <tr>
      <td  id="f_engName" class="title"><@bean.message key="attr.engName" />:</td>
      <td ><input type="text" name="press.engName" maxlength="20" value="${press.engName?if_exists}"/></td>	   
      <td  id="f_engName" class="title">是否使用:</td>
      <td >
        <input type="radio" value="1" name="press.state" <#if press.state?if_exists == true>checked</#if> /><@bean.message key="common.yes" />
        <input type="radio" value="0" name="press.state" <#if press.state?if_exists == false>checked</#if> /><@bean.message key="common.no" />                 
      </td>
    </tr>
    <tr  class="darkColumn" align="center">
      <td colspan="6">
          <input type="hidden"  name="press.id" value="${press.id?if_exists}" />
          <input type="button" onClick='submit1(this.form)' value="<@bean.message key="system.button.submit"/>" class="buttonStyle"/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;           
      </td>
    </tr>
  </form> 
  </table>
  <#list 1..5 as i><br></#list>
  <script language="javascript"> 
    function reset(){
  	   document.baseCodeForm.reset();
    } 
    function submit1(form){        
         var a_fields = {
         'press.code':{'l':'<@bean.message key="attr.code" />', 'r':true, 't':'f_code','mx':'32'},
         'press.name':{'l':'<@bean.message key="attr.personName" />', 'r':true, 't':'f_name'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }  
  </script>
  </body> 
</html>