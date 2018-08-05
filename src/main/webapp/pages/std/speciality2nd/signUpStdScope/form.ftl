<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <body>
 <table id="myBar" width="100%"></table>
   <table class="formTable" align="center" width="80%">
   <form name="commonForm" action="" method="post" onsubmit="return false;">
   <@searchParams/>
   <input type="hidden" name="stdScope.setting.id" value="${RequestParameters['stdScope.setting.id']}"/>
   <input type="hidden" name="stdScope.id" value="${stdScope.id?default('')}"/>
   <tr> 
     <td class="title" id="f_enrollTurn"><@bean.message key="attr.enrollTurn"/><font color="red">*</font>:</td>
     <td colspan="4"><input name="stdScope.enrollTurn" type="text" value="${stdScope.enrollTurn?default('')}" maxlength="7" style="width:60px"/>格式(yyyy-p)</td>
    </tr>
    <tr>
      <td class="title"><@bean.message key="entity.studentType"/>:</td>
      <td align="left" id="f_specialityAspect">             
        <select name="stdScope.stdType.id"  id="stdTypeOfSpeciality" style="width:100px;">
         	<option value="${(stdScope.stdType.id)?default("")}"><@bean.message key="common.selectPlease"/></option>
        </select>
      </td>
   	 <td class="title" id="f_department"><@bean.message key="entity.college"/>:</td>
      <td>
        <select id="department" name="stdScope.department.id"  style="width:100px;">
           <option value="${(stdScope.department.id)?default("")}"><@bean.message key="common.selectPlease"/></option>
        </select>
      </td>
     </td>
    </tr>
    <tr>
      <td class="title"><@bean.message key="entity.speciality"/>:</td>	    
      <td align="left" id="f_speciality">         
        <select id="speciality" name="stdScope.speciality.id"  style="width:100px;">
           <option value="${(stdScope.speciality.id)?default("")}"><@bean.message key="common.selectPlease"/></option>
        </select>
      </td>
      <td class="title"><@bean.message key="entity.specialityAspect"/>:</td>
      <td align="left" id="f_specialityAspect">             
        <select id="specialityAspect" name="stdScope.aspect.id"  style="width:100px;">        
         <option value="${(stdScope.aspect.id)?default("")}"><@bean.message key="common.selectPlease"/></option>
        </select>
      </td>
    </tr>
    <#include "/templates/stdTypeDepart3Select.ftl">
 </form>

  <script language="javascript">
    var form=document.commonForm;
    function save(){
    	var a_fields = {
    		'stdScope.enrollTurn':{'l':'<@msg.message key="attr.enrollTurn"/>', 'r':true, 't':'f_enrollTurn', 'f':'yearMonth'}
    	};
    	
    	var v = new validator(form, a_fields, null);
    	if (v.exec()) {
	        form.action="speciality2ndSignUpStdScope.do?method=save"
	        form.submit();
        }
   }
   var bar = new ToolBar("myBar","指定辅修专业报名学生对象",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.save"/>","save()");
   bar.addBack("<@msg.message key="action.back"/>");
  </script>
 </body>
<#include "/templates/foot.ftl"/>