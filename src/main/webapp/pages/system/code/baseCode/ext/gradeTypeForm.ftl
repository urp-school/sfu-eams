<#include "/templates/head.ftl"/>
<#assign extraTR>
    <tr>
      <td id="f_engName" class="title">教师可输入:</td>
      <td colspan="3"><@htm.radio2 name="baseCode.teacherCanInputGrade" value=baseCode.teacherCanInputGrade?default(false)/></td>
    </tr>
    <tr>
      <td id="f_shortName" class="title"><font color="red">*</font>简名:</td>
      <td><input type="text" name="baseCode.shortName" value="${baseCode.shortName?if_exists}"/></td>	   
      <td id="f_examType" class="title">考试类型:</td>
      <td>
          <@htm.i18nSelect datas=examTypes selected=(baseCode.examType.id)?default("")?string name="baseCode.examType.id">
            <option value="">无</option>
          </@>
      </td>
    </tr>
    <tr>
      <td id="f_mark" class="title"><font color="red">*</font>掩码(2<sup>n</sup>):</td>
      <td><input type="text" name="baseCode.mark" value="${baseCode.mark?if_exists}" maxlength="7"/></td>	   
      <td id="f_priority" class="title"><font color="red">*</font>优先级:</td>
      <td><input type="text" name="baseCode.priority" maxlength="5" value="${baseCode.priority?if_exists}"/></td>
    </tr>
    <tr><td colspan="4" class="title">
     成绩类型中的许多信息属于控制字段，尽量不用修改。掩码等字段含义，参见系统说明文档。
     </td>
  </tr>
</#assign>
<#assign saveAction>
    function save(form){        
         var a_fields = {
         'baseCode.name':{'l':'<@bean.message key="attr.personName" />', 'r':true, 't':'f_name'},
         'baseCode.shortName':{'l':'简名', 'r':true, 't':'f_shortName'},
         'baseCode.priority':{'l':'优先级', 'r':true,'f':'unsigned', 't':'f_priority'},
         'baseCode.mark':{'l':'掩码', 'r':true,'f':'unsigned', 't':'f_mark'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }  
</#assign>
<#include "commonForm.ftl"/>
