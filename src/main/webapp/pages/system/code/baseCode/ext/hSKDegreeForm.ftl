<#include "/templates/head.ftl"/>
<#assign extraTR>
    <tr >
      <td  id="f_degree" class="title" ><font color="red">*</font>等级值:</td>
      <td colspan="3"><input type="text" name="baseCode.degree" value="${baseCode.degree?default("")}" style="width:50px" maxLength="3"></td>
    </tr>
</#assign>
<#assign saveAction>
    function save(form){        
         var a_fields = {
         'baseCode.degree':{'l':'等级值', 'r':true,'f':'unsigned', 't':'f_degree'},
         'baseCode.name':{'l':'<@bean.message key="attr.name" />', 'r':true, 't':'f_name'},
         'baseCode.code':{'l':'<@bean.message key="attr.name" />', 'r':true, 't':'f_code'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }  
</#assign>
<#include "commonForm.ftl"/>