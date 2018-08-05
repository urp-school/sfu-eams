<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body >
 <#assign labInfo><@bean.message key="page.teacherForm.label" /></#assign>     
<#include "/templates/back.ftl"/>
  
    <form action="teacher.do?method=edit" name="teacherForm" method="post" onsubmit="return false;">
    <@searchParams/>
    <#include "baseInfoForm.ftl"/>
    <#include "addressInfoForm.ftl"/>
    <#include "degreeInfoForm.ftl"/>
    
	 <table  width="90%" align="center" class="formTable">
	   <tr class="darkColumn">
          <td colspan="4"><input type="checkbox" name="otherDisplay" onClick="displayDiv(event,'otherInfo')"/><@bean.message key="attr.otherInfo"/></td>
       </tr>
     </table>
       
       <div id="otherInfo" style="display:none">
       <table  width="90%" align="center" class="formTable">
       <tr>
	     <td width="15%" class="title"><@bean.message key="teacher.dateOfJoin"/>: </td>
	     <td width="35%"> <input type="text" name="teacher.dateOfJoin" value="${teacher.dateOfJoin?if_exists}" onfocus='calendar()'></td>
	     <td width="15%" class="title"><@bean.message key="teacher.isTeaching"/>: </td>
	     <td width="35%">   
             <input type="radio" value="1" name="teacher.isTeaching" <#if teacher.isTeaching?if_exists == true>checked</#if> /><@bean.message key="common.yes"/>
             <input type="radio" value="0" name="teacher.isTeaching" <#if teacher.isTeaching?if_exists == false>checked</#if> /><@bean.message key="common.no"/>                                  
          </td>	     
	   </tr>	 
	   <tr>	   
	     <td width="15%" class="title"><@bean.message key="teacher.isEngageFormRetire"/>: </td>
	     <td width="35%">
             <input type="radio" value="1" name="teacher.isEngageFormRetire" <#if teacher.isEngageFormRetire?if_exists == true>checked</#if> /><@bean.message key="common.yes"/>
             <input type="radio" value="0" name="teacher.isEngageFormRetire" <#if teacher.isEngageFormRetire?if_exists == false>checked</#if> /><@bean.message key="common.no"/>	     
	      </td>	  
	     <td width="15%" class="title"><@bean.message key="teacher.isConcurrent"/>: </td>
	     <td width="35%">
	         <input type="radio" value="1" name="teacher.isConcurrent" <#if teacher.isConcurrent?if_exists == true>checked</#if> /><@bean.message key="common.yes"/>
             <input type="radio" value="0" name="teacher.isConcurrent" <#if teacher.isConcurrent?if_exists == false>checked</#if> /><@bean.message key="common.no"/>
	      </td>
	   </tr>
	   <tr>
	     <td class="title"><@bean.message key="attr.createAt" />: </td>
	     <td>${(teacher.createAt?string("yyyy-MM-dd"))?if_exists}</td>
	     <td id="f_remark" class="title"><@bean.message key="common.remark" />:</td>
	     <td><textarea name="teacher.remark" cols="20" rows="2">${teacher.remark?if_exists}</textarea></td>
	   </tr>
	   <tr>
	     <td class="title"><@bean.message key="attr.modifyAt" />:</td>
	     <td>${(teacher.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
	     <td class="title">在职状态:</td>
	     <td><@htm.i18nSelect datas=teacherWorkStateList selected=RequestParameters['teacher.workState.id']?default("") name="teacher.workState.id" style="width:100px;"/></td>
	   </tr>
        <tr>
            <td class="title" style="width:15%" id="f_experience">教学经历：<br>（可输入800字)</td>
            <td colspan="3"><textarea name="teacher.degreeInfo.experience" cols="50" rows="10">${(teacher.degreeInfo.experience)?default("")}</textarea></td>
        </tr>
        <tr>
            <td class="title" style="width:15%"id="f_achievements">科研成果：<br>（可输入800字)</td>
            <td colspan="3"><textarea name="teacher.degreeInfo.achievements" cols="50" rows="10">${(teacher.degreeInfo.achievements)?default("")}</textarea></td>
        </tr>
        <tr>
            <td class="title" style="width:15%"id="f_partTimeJob">学术兼职：<br>（可输入800字)</td>
            <td colspan="3"><textarea name="teacher.degreeInfo.partTimeJob" cols="50" rows="10">${(teacher.degreeInfo.partTimeJob)?default("")}</textarea></td>
        </tr>
       </table>
       </div>

      <table  width="90%" align="center" class="formTable">
	   <tr class="darkColumn" align="center">
	     <td colspan="5">
           <input type="button" onClick='save(this.form)' value="<@bean.message key="system.button.submit"/>" class="buttonStyle"/>
           <input type="button" onClick='save(this.form,"&addAnother=1")' value="保存并添加下一个" class="buttonStyle"/>
           <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>
	     </td>
	   </tr>
	   </form>
     </table>
 <script language="javascript" > 
     function reset(){
       	   document.teacherForm.reset();
      }
    function save(form,params){
     var a_fields = {
        'teacher.code':{'l':'<@bean.message key="teacher.code" />', 'r':true, 't':'f_code'},
        'teacher.name':{'l':'<@bean.message key="attr.personName" />', 'r':true, 't':'f_name'},
        'teacher.gender.id':{'l':'<@bean.message key="attr.gender" />', 'r':true, 't':'f_gender'},
        'teacher.country.id':{'l':'<@bean.message key="entity.country" />', 'r':true, 't':'f_country'},
        'teacher.department.id':{'l':'<@bean.message key="entity.department" />', 'r':true, 't':'f_department'},
        'teacher.teacherType.id':{'l':'<@bean.message key="entity.teacherType" />', 'r':true, 't':'f_teacherType'},
        'teacher.addressInfo.email':{'l':'<@bean.message key="common.email"/>','r':false,'t':'f_email','f':'email'},
        'teacher.addressInfo.postCodeOfFamily':{'l':'<@bean.message key="common.postCodeOfFamily" />','r':false,'t':'f_postCodeOfFamily','f':'unsigned'},
        'teacher.addressInfo.postCodeOfCorporation':{'l':'<@bean.message key="common.postCodeOfCorporation" />','r':false,'t':'f_postCodeOfCorporation','f':'unsigned'},
        'teacher.addressInfo.mobilePhone':{'l':'<@bean.message key="common.mobilePhone" />','r':false,'t':'f_mobilePhone','f':'unsigned'},
        'teacher.remark':{'l':'<@bean.message key="common.remark" />','r':false,'t':'f_remark','mx':100},
        'teacher.degreeInfo.experience':{'l':'教学经历','r':false,'t':'f_experience','mx':800},
        'teacher.degreeInfo.achievements':{'l':'科研成果','r':false,'t':'f_achievements','mx':800},
        'teacher.degreeInfo.partTimeJob':{'l':'学术兼职','r':false,'t':'f_partTimeJob','mx':800}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.action="teacher.do?method=save";
        if(null!=params)
          form.action+=params;
        form.submit();
     }
   }
   
   function displayDiv(event,divId){
       var div = document.getElementById(divId);
       var thisCheck = getEventTarget(event);
       if (thisCheck.checked==true){
          div.style.display="block";
          f_frameStyleResize(self)
       }
       else{
         div.style.display="none";
         f_frameStyleResize(self)  
       }
   }  
 </script>
 </body> 
</html>