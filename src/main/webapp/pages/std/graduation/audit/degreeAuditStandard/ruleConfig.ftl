<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body >
<#assign labInfo>学位审核规则定义</#assign> 
<#include "/templates/back.ftl"/>
 <table width="95%"  align="center" class="formTable">
  <form action="degreeAuditStandard.do?method=save" name="standardForm" method="post" onsubmit="return false;">
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
   <tr class="darkColumn">
     <td align="left" colspan="4"><B>可使用规则</B></td>
   </tr>
   <tr class="darkColumn">
     <td align="center"><B>学位审核规则</B></td>
     <td align="center" colspan="2"><B>规则描述</B></td>
     <td align="center"><B>状态</B></td>
   </tr>
   <#list ruleList as rule>
   <div id="div_${rule.id}">
   <tr>
     <td><input type="checkbox" id="id${rule.id}" name="rule.id" value=""/>${rule.name?default("")}</td>
     <td colspan="2">${(rule.description)?default("")}</td>
     <td>${(rule.enabled)?string("启用","禁用")}</td>
   </tr>
   </#list>
   <tr class="darkColumn" align="center">
     <td colspan="4">
       <input type="button" onClick='select(this.form)' value="选定↓" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
       <input type="button" onClick='remove(this.form)' value="移除↑" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
     </td>
   </tr>
   <tr class="darkColumn">
     <td align="left" colspan="4"><B>已选定规则</B></td>
   </tr>
   <#list ruleList as rule>
   <tr>
     <td><input type="checkbox" name="standard.name" value=""/>${rule.name?default("")}</td>
     <td colspan="2">${(rule.description)?default("")}</td>
     <td>${(rule.enabled)?string("启用","禁用")}</td>
   </tr>
   </#list>
   <tr class="darkColumn" align="center">
     <td colspan="4">
       <input type="button" onClick='save(this.form)' value="<@bean.message key="system.button.submit"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
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
     function save(form, params){
       var a_fields = {
         'standard.thesisScore':{'l':'论文答辩分数', 'r':false,'t':'f_thesisScore', 't':'f_name', 'f':'unsignedReal'},
         'standard.thesisInCoreMagazine':{'l':'核心期刊的论文最低数（或折合数）', 'r':false, 't':'f_thesisInCoreMagazine', 'f':'unsignedReal'},
         'standard.GPA':{'l':'最低平均绩点', 'r':false, 't':'f_GPA', 'f':'unsignedReal'},
         'standard.name':{'l':'名称', 'r':true, 't':'f_name'}
       };
       var v = new validator(form , a_fields, null);
       if (v.exec()) {
         form.action="degreeAuditStandard.do?method=save";
         if(null!=params)
             form.action +=params;
         form.submit();
       }
   }
   function addOtherExamCategory(kind){
       var category = document.getElementById(kind);
       if(category.value!=""){
           if(form["standard."+kind+'Ids'].value.indexOf(','+category.value+',')==-1){
              form["standard."+kind+'Ids'].value+=category.value +",";
              form[kind+'Names'].value+=DWRUtil.getText(kind)+" ";
           }
       }
   }
 </script>
 </body>
</html>