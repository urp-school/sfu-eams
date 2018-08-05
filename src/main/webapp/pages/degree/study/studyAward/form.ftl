<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <body > 
<#assign labInfo>获奖详细信息</#assign>
<#include "/templates/back.ftl"/>
<table width="100%" align="center" class="formTable">
	<form name="editForm" method="post"action="" onsubmit="return false;">
	<tr class="darkColumn" align="center">
		<td colspan="4">获奖详细信息</td>
	</tr>
	<tr  align="center">
		<td class="title"><@msg.message key="attr.stdNo"/></td>
		<td>${studyAward.studyProduct.student.code}</td>
		<td>学生姓名</td>
		<td>${studyAward.studyProduct.student.name}</td>
	</tr>
	<tr align="center">
		<td  class="title">成果名称</td>
		<td colspan="3">${studyAward.studyProduct.name}</td>
   </tr>
   <tr>
     <td class="title" width="25%" id="f_name">&nbsp;获奖名称<font color="red">*</font>：</td>
     <td><input type="text" name="studyAward.awardName" maxlength="20" size="20" value="${studyAward.awardName?default('')}"/></td>
     <td class="title" width="25%" id="f_awardedOn">&nbsp;获奖时间<font color="red">*</font>：</td>
     <td><input type="text" name="studyAward.awardedOn" value="${studyAward.awardedOn?string("yyyy-MM-dd")}" maxlength="10" onfocus="calendar();f_frameStyleResize(self,0)"></td>
   </tr>
    <tr>
     <td class="title" width="25%" id="f_departmentName">&nbsp;颁奖单位<font color="red">*</font>：</td>
     <td><input type="text" name="studyAward.departmentName" maxlength="20" value="${studyAward.departmentName?default('')}"></td>
     <td class="title" id="f_type">&nbsp;科研获奖等级<font color="red">*</font>：</td>
     <td><@htm.i18nSelect datas=awardTypeList?sort_by("code") selected="${(studyAward.type.id)?default('')?string}" name="studyAward.type.id" style="width:200px"/></td>
   </tr>
   <tr>
   	   <td class="title">备注</td>
   	   <td colspan="3"><textarea name="studyAward.remark" cols="30" rows="3" style="width:100%">${studyAward.remark?default("")}</textarea></td>
   </tr>	   	   
   <tr class="darkColumn" align="center">
     <td colspan="4">
       <input type="hidden" name="studyAward.id" value="${studyAward.id?if_exists}">
       <input type="hidden" name="studyProductId" value="${studyAward.studyProduct.id}">
       <input type="hidden" name="studyAward.student.id" value="${studyAward.studyProduct.student.id}">
       <input type="hidden" name="productType" value="${RequestParameters['productType']}"/>
       <input type="hidden" id="fromAction" name="fromAction" value=""/>
	   <button onClick="doAction(this.form)"><@bean.message key="system.button.submit"/></button>&nbsp;
     </td>
   </tr>
   </form>
 </table>
 </body>
 <script language="javascript" >
     var action="studyAward.do?method=save";
     function doAction(form){
      var a_fields = {
         'studyAward.awardName':{'l':'获奖名称', 'r':true, 't':'f_name','mx':100},
         'studyAward.awardedOn':{'l':'获奖名称', 'r':true, 't':'f_awardedOn','mx':100},
         'studyAward.departmentName':{'l':'颁奖单位', 'r':true, 't':'f_departmentName','mx':100},    
         'studyAward.type.id':{'l':'科研获奖等级', 'r':true, 't':'f_type'}         
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
     	form.action=action;
     	setSearchParams(parent.document.searchForm,form);
        form.submit();
     }
    }
    document.getElementById('fromAction').value=parent.action;
    //表明该页面是学生访问的子页面
    if(parent.action.indexOf("Std")!=-1){
       action="studyProductStd.do?method=saveAward";
       //alert(action);
    }
 </script>
<#include "/templates/foot.ftl"/>