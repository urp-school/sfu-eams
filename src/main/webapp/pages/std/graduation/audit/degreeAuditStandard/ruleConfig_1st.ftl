<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body >
<#assign labInfo>学位审核标准基本信息</#assign> 
<#include "/templates/back.ftl"/>
 <table width="95%"  align="center" class="formTable">
  <form action="degreeAuditStandard.do?method=ruleConfig_2nd" name="standardForm" method="post" onsubmit="return false;">
   <input name="standard.id" value="${(standard.id)?default('')}" type="hidden"/>
   <@searchParams/>
   <tr>
     <td width="20%" id="f_name" class="title"><font color="red">*</font><@msg.message key="attr.name" />:</td>
     <td colspan="3"><input name="standard.name" value="${(standard.name)?default("")}" maxlength="20" style="width:100%"/></td>
   </tr>
   <tr>
     <td width="20%" class="title"><font color="red">*</font><@msg.message key="entity.studentType"/>:</td>
     <td width="30%"><@htm.i18nSelect datas=stdTypes name="standard.stdType.id" selected=(standard.stdType.id?string)?default("") style="width:100%"/></td>
     <td width="20%" class="title">专业类别:</td>
   	 <td width="30%">
   	    <select name="standard.majorType.id" style="width:100%">
   	      <option value="1"<#if RequestParameters['standard.majorType.id']?if_exists="1"> selected</#if>>第一专业</option>
   	      <option value="2"<#if RequestParameters['standard.majorType.id']?if_exists="2"> selected</#if>>第二专业</option>
   	    </select>
   	 </td>
   </tr>
   <tr class="darkColumn" align="center">
     <td colspan="4">
       <input type="button" onClick='save(this.form)' value="下一步" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
       <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
     </td>
   </tr>
   </form>
 </table>
  <script language="javascript" > 
     var form=document.standardForm;
     function reset(){
       form.reset();
     }
     function save(form){
       var a_fields = {
         'standard.name':{'l':'名称', 'r':true, 't':'f_name'}
       };
       var v = new validator(form , a_fields, null);
       if (v.exec()) {
         form.action="degreeAuditStandard.do?method=ruleConfig_2nd";
         form.submit();
       }
     }
 </script>
 </body>
</html>