<#include "/templates/head.ftl"/>
<#assign extraTR>
    <tr >
      <td  id="f_passScore" class="title"><font color="red">*</font>及格线:</td>
      <td colspan="3"><input type="text" name="baseCode.passScore" value="${baseCode.passScore?default("")}" maxLength="3" style="width:50px"></td>
    </tr>
</#assign>
<#assign saveAction>
    function save(form){        
         var a_fields = {
         'baseCode.passScore':{'l':'及格线', 'r':true, 't':'f_passScore','f':'unsigned'},
         'baseCode.name':{'l':'<@bean.message key="attr.name" />', 'r':true, 't':'f_name'},
         'baseCode.code':{'l':'<@bean.message key="attr.code" />', 'r':true, 't':'f_code'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }  
</#assign>
<#include "commonForm.ftl"/>