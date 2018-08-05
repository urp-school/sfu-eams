<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body>
  <#assign labInfo>制定问题详细信息</#assign>
  <#include "/templates/back.ftl"/>
  <table  width="100%" class="formTable">
   <form name="commonForm" method="post" action="" onsubmit="return false;">
     <input type="hidden" name="question.id" value="${question.id?default('')}"/>
	   <tr>
	     <td class="title" width="20%" id="f_questionType"><font color="red">*</font><@msg.message key="field.questionType.choose"/>:</td>
	     <td width="30%"><@htm.i18nSelect datas=questionTypes?if_exists selected=(question.type.id)?default('')?string name="question.type.id" style="width:100px"/></td>
	     <td class="title" width="20%" id="f_mark"><font color="red">*</font><@msg.message key="field.question.mark"/>:</td>
	     <td width="30%"><input type="text" name="question.score" maxlength="3" style="width:50px;" value="${question.score?default('')}"/></td>
	   </tr>
	   <tr>
	     <td id="f_priority" class="title">问题优先级<font color="red">*</font>:</td>
	     <td><input type="text" name="question.priority" maxlength="5" value="${question.priority?default('1')}" style="width:50px"/><font color="red">越高显示越靠前</font></td>
	     <td id="f_estate" class="title"><font color="red">*</font>是否可用:</td>
	     <td><@htm.radio2 name="question.state" value=question.state?default(true)/></td>
	   </tr>
	   <tr>
	     <td class="title">创建部门</td>
	     <td><@htm.i18nSelect datas=departmentList selected=(question.department.id)?default(0)?string name="question.department.id" style="width:150px;"/></td>
	     <td></td>
	     <td></td>
	   </tr>
	   <tr>
	     <td class="title"  id="f_content"><font color="red">*</font><@msg.message key="field.question.questionContext"/><br>(勿包含回车等符号):</td>
	     <td colspan="3"><textarea name="question.content" rows="2" cols="50">${question.content?default('')}</textarea></td>
	   </tr>
	   <tr>
	     <td class="title"id="f_remark"><@msg.message key="field.evaluate.remark"/></td>
	     <td colspan="3"><input name="question.remark" style="width:300px;" value="${question.remark?default('')}" maxlength="200"/></td>
	   </tr>
	   <tr class="darkColumn">
	     <td colspan="4" align="center">
	       <button onClick="save(this.form)"><@msg.message key="system.button.submit"/></button>&nbsp;
	       <input type="reset"  name="reset1" value="<@msg.message key="system.button.reset"/>" class="buttonStyle"/>
	     </td>
	   </tr>
   </form>
  </table>
   <script language="javascript">
   function save(form){     
     var a_fields = {
          'question.content':{'l':'<@msg.message key="field.question.questionContext"/>', 'r':true, 't':'f_content', 'mx':250},
          'question.score':{'l':'<@msg.message key="field.question.mark"/>', 'r':true, 't':'f_mark','f':'unsigned', 'mx':10},
          'question.type.id':{'l':'<@msg.message key="field.question.selectQuestionType"/>', 'r':true, 't':'f_questionType'},
          'question.remark':{'l':'<@msg.message key="field.evaluate.remark"/>', 't':'f_remark', 'mx':250},
          'question.priority':{'l':'问题优先级', 'r':true,'t':'f_priority', 'mx':9}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
     	form.action="question.do?method=save";
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>