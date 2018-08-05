<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body >
<#assign labInfo>学位审核标准</#assign> 
<#include "/templates/back.ftl"/>
 <table width="95%"  align="center" class="formTable">
  <form action="degreeAuditStandard.do?method=save" name="standardForm" method="post" onsubmit="return false;">
   <input name="standard.id" value="${standard.id?default('')}" type="hidden"/>
   <@searchParams/>
   <tr class="darkColumn">
     <td align="left" colspan="4"><B>学位审核标准</B></td>
   </tr>
   <tr>
     <td  id="f_name" class="title"><font color="red">*</font><@msg.message key="attr.name" />:</td>
     <td colspan="3"><input name="standard.name" value="${standard.name?default("")}" maxlength="20"/></td>
   </tr>
   <tr>
     <td class="title"><font color="red">*</font><@msg.message key="entity.studentType"/>:</td>
     <td><@htm.i18nSelect datas=stdTypes name="standard.stdType.id" selected=(standard.stdType.id?string)?default("") style="width:100px"/></td>
     <td class="title">要求培养计划完成:</td>
     <td><@htm.radio2 value=(standard.isCompletePlan)?default(true) name="standard.isCompletePlan"/></td>	   
   </tr>
   <tr>
     <td  id="f_thesisScore" class="title">论文答辩分数:</td>
     <td><input name="standard.thesisScore" value="${standard.thesisScore?default("")}" style="width:100px" maxlength="3"/></td>
     <td  id="f_GPA" class="title">最低平均绩点:</td>
     <td><input name="standard.GPA" value="${standard.GPA?default("")}" style="width:30px" maxlength="5"/>(包含)</td>	   
   </tr>
   <tr>
     <td  id="f_lowestPunishType" class="title">最低处分级别:</td>
     <td><@htm.i18nSelect datas=punishTypes?sort_by("level") name="standard.lowestPunishType.id" selected=(standard.lowestPunishType.id?string)?default("") style="width:100px"/>(包含)</td>
     <td class="title">第一专业:</td>
 	 <td><@htm.select2 selected=(standard.majorType.id?string)?default("1") name="standard.majorType.id" hasAll=false style="width:100px"/></td>
   </tr>
   <tr>
     <td id="f_languageExam" class="title">外语考试:</td>
     <td colspan="3">
         <input type="hidden" name="standard.languageExamIds" value=",<#list standard.languageExams as exam>${exam.id},</#list>"/>
         <input type="text" name="languageExamNames" value="<@getBeanListNames standard.languageExams/>" style="border:0 solid #000000;" readOnly="true" maxlength="100"/>
         <@htm.i18nSelect datas=otherExamCategories id="languageExam" selected="" style="width:150px"/>
         <button onClick="this.form['standard.languageExamIds'].value='';this.form['languageExamNames'].value='';"><@bean.message key="action.clear"/></button>               
         <button onClick="addOtherExamCategory('languageExam');"><@bean.message key="action.add"/></button><br>
     </td>
   </tr>
   <tr>
     <td  id="f_computerExam" class="title">计算机考试:</td>
     <td colspan="3">
         <input type="hidden" name="standard.computerExamIds" value=",<#list standard.computerExams as exam>${exam.id},</#list>"/>
         <input type="text" name="computerExamNames" value="<@getBeanListNames standard.computerExams/>" style="border:0 solid #000000;" readOnly="true" maxlength="100"/>
         <@htm.i18nSelect datas=otherExamCategories id="computerExam" selected="" style="width:150px"/>
         <button onClick="this.form['standard.computerExamIds'].value='';this.form['computerExamNames'].value='';"><@bean.message key="action.clear"/></button>               
         <button onClick="addOtherExamCategory('computerExam');"><@bean.message key="action.add"/></button><br>
     </td>
   </tr>
   <tr>
     <td  id="f_isPassDoctorComprehensiveExam" class="title">通过学科综合考试:</td>
     <td><@htm.radio2 value=(standard.isPassDoctorComprehensiveExam)?default(false) name="standard.isPassDoctorComprehensiveExam"/></td>
     <td  id="f_thesisInCoreMagazine" class="title">核心期刊的论文最低数（或折合数）:</td>
     <td><input name="standard.thesisInCoreMagazine" value="${standard.thesisInCoreMagazine?default("")}" maxlength="3"/></td>
   </tr>
   <tr class="darkColumn" align="center">
     <td colspan="4">
       <input type="hidden" name="useDepartIds" value=""/>
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