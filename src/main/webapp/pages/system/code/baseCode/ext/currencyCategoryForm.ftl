<#include "/templates/head.ftl"/>
<#assign extraTR>
    <tr >
      <td  id="f_rateToRMB" class="title" >对人民币汇率:</td>
      <td colspan="3"><input type="text" name="baseCode.rateToRMB"value="${baseCode.rateToRMB?default("")}" maxLength="8"></td>
    </tr>
</#assign>
<#assign saveAction>
    function save(form){        
         var a_fields = {
         'baseCode.code':{'l':'<@bean.message key="attr.code" />', 'r':true, 't':'f_code'},
         'baseCode.name':{'l':'<@bean.message key="attr.name" />', 'r':true, 't':'f_name'},
         'baseCode.rateToRMB':{'l':'对人民币汇率', 'r':true, 'f':'unsignedReal','t':'f_rateToRMB'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }  
</#assign>
<#include "commonForm.ftl"/>