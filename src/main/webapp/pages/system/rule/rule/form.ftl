<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body>
<#assign labInfo>规则基本信息</#assign>
<#include "/templates/back.ftl"/>
  <table width="80%" align="center" class="formTable">
    <form action="rule.do?method=edit" name="ruleForm" method="post" onsubmit="return false;">
    <@searchParams/>
    <tr class="darkColumn">
      <td colspan="4"><B>规则基本信息</B></td>
    </tr>
    <tr>
      <td id="f_business" class="title" width="20%"><font color="red">*</font>适用业务:</td>
      <td width="30%">
      <input id="businessValue" type="text" name="rule.business" value="${(rule.business)?if_exists}" maxLength="32" style="width:100%;">
      <input type="hidden"  name="rule.id" value="${(rule.id)?if_exists}"/>
      </td>
      <td id="f_name" class="title" width="20%"><font color="red">*</font><@bean.message key="attr.infoname" />:</td>
      <td width="30%"><input type="text" name="rule.name" value="${(rule.name)?if_exists}" maxLength="20" style="width:100%;"></td>
    </tr>
    <tr>
      <td id="f_serviceName" class="title"><font color="red">*</font>服务名:</td>
      <td colspan="3">
      <input id="serviceName" type="text" name="rule.serviceName" value="${(rule.serviceName)?if_exists}" maxLength="32" style="width:100%;">
      </td>
    </tr>
    <tr>  
      <td id="f_description" class="title"><font color="red">*</font>规则描述:</td>
      <td colspan="3"><input id="description" type="text" name="rule.description" value="${(rule.description)?if_exists}" maxLength="20" style="width:100%;"></td>
    </tr>
    <tr>
      <td id="f_factory" class="title"><font color="red">*</font>管理容器:</td>
      <td><input type="text" name="rule.factory" value="${(rule.factory)?if_exists}" maxLength="20" style="width:100%;"></td>
      <td class="title">是否使用:</td>
      <td><@htm.radio2  name="rule.enabled" value=(rule.enabled)?default(true)/></td>       
    </tr>
    <tr>
      <td class="title">  <@bean.message key="attr.createAt" />: </td>
      <td>${((rule.createdAt)?string("yyyy-MM-dd"))?if_exists}</td>    
      <input type="hidden"  name="rule.createdAt" value="${(rule.createdAt)?if_exists}"/>
      <td class="title">  <@bean.message key="attr.modifyAt" />:</td>
      <td>${((rule.updatedAt)?string("yyyy-MM-dd"))?if_exists}</td>
      <input type="hidden"  name="rule.updatedAt" value="${(rule.updatedAt)?if_exists}"/>
    </tr>
    <tr class="darkColumn" align="center">
      <td colspan="4">
          <input type="button" onClick='save(this.form)' value="<@bean.message key="system.button.submit"/>" class="buttonStyle"/>
          <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>
      </td>
    </tr>
    </form> 
  </table>
  <script language="javascript" > 
     function reset(){
         document.ruleForm.reset();
     }
    function save(form,params){
         var a_fields = {
         'rule.business':{'l':'适用业务', 'r':true, 't':'f_business'},
         'rule.name':{'l':'<@bean.message key="attr.name" />', 'r':true, 't':'f_name'}
         };
		 var v = new validator(form , a_fields, null);
		 if (v.exec()) {
		 	form['rule.name'].value = cleanSpaces(form['rule.name'].value);
		    form.action="rule.do?method=save";
		    if(null!=params)
		       form.action+=params;
		    form.submit();
		 }
   }
 </script>
 </body> 
</html>