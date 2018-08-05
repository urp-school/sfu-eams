<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <script>
 	var feeType = new Array();
 	function getFeeType(){
 		var departmentId = document.commonForm['stdAndFeeTypeDefault.department.id'].value;
 		var selectFee = document.commonForm['stdAndFeeTypeDefault.feeType.id'];
 		
 		for(var i=selectFee.length-1;i>=0;i--){
 			selectFee.remove(i);
 		}
 		if(departmentId==''){
 			return;
 		}
 		for(var i=0;i<feeType.length;i++){
 			if(departmentId==feeType[i][0]){
 				selectFee.add(new Option(feeType[i][1],feeType[i][2]));
 			}
 		}
 	}
 </script>
 <body>
<#assign labInfo><#if feeDefault?exists><@msg.message key="action.edit"/><#else>添加</#if>学生类别收费值</#assign>
<#include "/templates/back.ftl"/>
  <table cellpadding="0" cellspacing="0" width="100%" class="formTable">
   <form name="commonForm" method="post" action="" onsubmit="return false;">
    <td>
     <table width="100%" align="center" class="listTable">
          <tr align="center" class="darkColumn">
    		<td colspan="2" align="center"><B><#if feeDefault?exists><@msg.message key="action.edit"/><#else>添加</#if>学生类别收费值</B></td>
   		  </tr>		
	   <tr>
	     <td class="title" width="30%" id="f_stdTypeId"><font color="red">*</font>&nbsp;<@msg.message key="entity.studentType"/>:</td>
	     <td>
	     	<select name="feeDefault.studentType.id" id="stdTypeOfSpeciality" style="width:300px;">
	     	   <option value="${(feeDefault.studentType.id)?default('')}">...</option>
	     	</select>
         </td>
	   </tr>
       <tr>
         <td class="title" width="30%" id="f_departmentId"><font color="red">*</font>&nbsp;<@bean.message key="entity.department"/>:</td>
         <td>
            <select id="department" name="feeDefault.department.id" style="width:300px;">
                <option value="${(feeDefault.department.id)?default('')}">...</option>
            </select>
         </td>
       </tr>
	   <tr>
	      <td class="title" id="f_specialityId">&nbsp;<@bean.message key="entity.speciality"/>:</td>     
	      <td>         
	        <select id="speciality" name="feeDefault.speciality.id"  style="width:100px;">
	           <option value="${(feeDefault.speciality.id)?default('')}">...</option>
	        </select>
	      </td>
	   </tr>
	   <tr>
	     <td class="title" width="30%" id="f_feeTypeId"><font color="red">*</font>&nbsp;收费类型:</td>
	     <td>
			<select name="feeDefault.type.id" style="width:300px;">
				<#list feeTypeList?if_exists as feeType>
				  <#if feeDefault?exists&&feeDefault.type.id==feeType.id>
				  	<option value="${feeType.id}" selected>${feeType.name?if_exists}</option>
				  <#else>
					<option value="${feeType.id}">${feeType.name?if_exists}</option>
				  </#if>
				</#list>
			</select>
         </td>
	   </tr>
	   <tr>
	     <td class="title" width="30%" id="f_value"><font color="red">*</font>&nbsp;默认金额:</td>
	     <td>
			<input name="feeDefault.value" style="width:300px;" maxlength="10" value="<#if feeDefault?exists>${feeDefault.value?if_exists}<#else>0</#if>">
         </td>
	   </tr>
	   <tr>
	     <td class="title" width="30%" id="f_remark">&nbsp;备注:</td>
	     <td>
	     	<textarea name="feeDefault.remark" rows="5" cols="30" style="width:300px;"><#if feeDefault?exists>${feeDefault.remark?if_exists}</#if></textarea>
         </td>
	   </tr>
	   <tr class="darkColumn">
	     <td colspan="2" align="center" >
	       <#if feeDefault?exists><input type="hidden" name="feeDefault.id" value="${feeDefault.id}"></#if>
	       <input type="button" value="<@bean.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset"  name="reset1" value="<@bean.message key="system.button.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>  
     </table>
    </td>
   </tr>
   </form>
  </table>
  <#include "/templates/stdTypeDepart2Select.ftl"/>
  <script language="javascript" >
  	var form = document.commonForm;
   function doAction(){
     var a_fields = {
          'feeDefault.studentType.id':{'l':'<@msg.message key="entity.studentType"/>', 'r':true, 't':'f_stdTypeId'},
          'feeDefault.department.id':{'l':'<@msg.message key="entity.department"/>', 'r':true, 't':'f_departmentId'},
          'feeDefault.speciality.id':{'l':'<@msg.message key="entity.speciality"/>', 'r':false, 't':'f_specialityId'},
          'feeDefault.type.id':{'l':'收费类型', 'r':true, 't':'f_feeTypeId'},
          'feeDefault.value':{'l':'默认金额','f':'integer', 'r':true, 't':'f_value','mx':9},
          'feeDefault.remark':{'l':'备注', 't':'f_remark','mx':100}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.action="feeDefault.do?method=save";
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>