<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <body>
  <#assign labInfo>制定问题类别详细信息</#assign>
  <#include "/templates/back.ftl"/>
  <table width="100%" class="formTable">
   <form name="commonForm" method="post" action="" onsubmit="return false;">
    <input type="hidden" name="questionType.id" value="${questionType.id?default('')}"/>
	   <tr>
	     <td class="title" width="20%" id="f_name"><font color="red">*</font><@bean.message key="attr.name"/>:</td>
	     <td width="30%"><input name="questionType.name" maxlength="30" value="${questionType.name?default('')}" style="width:100px"/></td>
	     <td class="title" width="20%" id="f_engName"><@bean.message key="attr.engName"/>:</td>
	     <td width="30%"><input type="text" name="questionType.engName" maxlength="30" value="${questionType.engName?default('')}" style="width:100px;"/></td>
	   </tr>
	   <tr>
	     <td id="f_priority" class="title">问题优先级<font color="red">*</font>:</td>
	     <td><input type="text" name="questionType.priority" maxlength="3" value="${(questionType.priority)?default(1)}" style="width:50px"/><font color="red">越高显示越靠前</font></td>
	     <td id="f_estate" class="title"><font color="red">*</font>是否可用:</td>
	     <td><@htm.radio2 name="questionType.state" value=questionType.state?default(true)/></td>
	   </tr>
	   <tr>
	     <td class="title"id="f_remark"><@bean.message key="field.evaluate.remark"/></td>
	     <td colspan="3"><input name="questionType.remark" style="width:300px;" value="${questionType.remark?default('')}" maxlength="200"/></td>
	   </tr>
	   <tr class="darkColumn">
	     <td colspan="4" align="center" >
	       <button onClick="save(this.form)"><@bean.message key="system.button.submit"/></button>&nbsp;
	       <input type="reset"  name="reset1" value="<@bean.message key="system.button.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
   </form>
  </table>
   <script language="javascript" >
   function save(form){     
     var a_fields = {
          'questionType.name':{'l':'问题类别名称', 'r':true, 't':'f_name', 'mx':250},
          'questionType.remark':{'l':'<@bean.message key="field.evaluate.remark"/>', 't':'f_remark'},
          'questionType.priority':{'l':'问题优先级', 'r':true,'t':'f_priority'}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
     	form.action="questionType.do?method=save";
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>