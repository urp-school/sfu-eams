<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body   valign="top">
<table id="filePrintFormBar"></table>
<script>
  var bar= new ToolBar("filePrintFormBar","请印单维护",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addBack();
  

</script>
<table width="100%" valign="top" class="formTable">
       <form name="filePrintApplicationForm"  action="filePrintAudit.do?method=save" method="post" enctype="multipart/form-data" onsubmit="return false;">
	   <tr class="darkColumn">
	     <td align="center" colspan="4">请印单基本信息</td>
	   </tr>
	   <tr>
		   <td class="title" width="16%" id="f_name">&nbsp;请印单编号:</td>	     
		   <td class="content" >${(filePrintApplication.id)?default("暂未生成")}</td>
	 	   <td class="title" width="16%" id="f_name">&nbsp;<@bean.message key="attr.yearAndTerm"/>:</td> 	     
	 	   <td class="content"><#if (filePrintApplication.calendar)?exists>${(filePrintApplication.calendar.year)?if_exists}&nbsp;${(filePrintApplication.calendar.term)?if_exists}<#else>${(calendar.year)?if_exists}&nbsp;${(calendar.term)?if_exists}</#if></td>
	 	   <input type="hidden" value="${(calendar.id)?if_exists}" name="filePrintApplication.calendar.id"/>
	 	   <input type="hidden" value="${(calendar.id)?if_exists}" name="calendar.id"/>
	   </tr>

	   <tr>
	     <td class="title" id="f_depart">&nbsp;请印<@bean.message key="entity.college"/><font color="red">*</font>:</td>	
     	 <td class="content">
       		<select id="department" name="filePrintApplication.depart.id" style="width:155px">	       
   	         	<#list departs as depart>
   	         	<option value="${depart.id}" <#if (filePrintApplication.depart.id)?exists&&(filePrintApplication.depart.id)==depart.id>selected</#if> ><@i18nName depart?if_exists/></option>
   	         	</#list>
       		</select>
     	 </td>
	     <td class="title" id="f_number">&nbsp;请印数量<font color="red">*</font>:</td>
	     <td class="brightStyle" id="f_valueId" colspan="3">
	      <input type="text" name="filePrintApplication.value" value="${(filePrintApplication.value)?if_exists}" maxlength="20"/>
         </td>
	   </tr>
	   
	   <tr>
    	 <td class="title" width="25%" id="f_file">&nbsp;请印文档:</td>
	     <td colspan="3">
	     	<input type="file" name="formFile" size=50 class="buttonStyle" value=''>&nbsp;<br>
	     	<a href='filePrintAudit.do?method=download&filePrintApplicationId=${(filePrintApplication.id)?if_exists}'>${(filePrintApplication.fileName)?if_exists}</a>
	     </td>
	   </tr>
	   
	   <tr>
	   	 <td class="title" width="25%" id="f_printDate">&nbsp;请印日期<font color="red">*</font>:</td>
	     <td >
	     	<input type="text" maxlength="10" name="filePrintApplication.printAt" value="<#if (filePrintApplication.printAt)?exists>${(filePrintApplication.printAt)?string("yyyy-MM-dd")}</#if>" onfocus="calendar()"/>
	     </td>
	     <td class="title" width="25%" id="f_copyDate">&nbsp;复印日期:</td>
	     <td >
	     	<input type="text" maxlength="10" name="filePrintApplication.copyAt" value="<#if (filePrintApplication.copyAt)?exists>${(filePrintApplication.copyAt)?string("yyyy-MM-dd")}</#if>" onfocus="calendar()"/>
	     </td>
	   </tr>
	   
	   <tr>
	   	 <td class="title" width="25%" id="f_addAt">&nbsp;创建时间:</td>
	     <td ><#if (filePrintApplication.addAt)?exists>${(filePrintApplication.addAt)?string("yyyy-MM-dd")}</#if></td>
	     <td class="title" width="25%" id="f_auditAt">&nbsp;审核时间:</td>
	     <td ><#if (filePrintApplication.auditAt)?exists>${(filePrintApplication.auditAt)?string("yyyy-MM-dd")}</#if></td>
	   </tr>
	   
	   <tr>
    	 <td class="title" width="25%" id="f_auditState">&nbsp;审核状态:</td>
	     <td >${(filePrintApplication.auditState?string("审核通过","审核未通过"))?default("待审核")}</td>
	   	 <td class="title" width="25%" id="f_filePrintStatet">&nbsp;请印状态:</td>
	     <td >${(filePrintApplication.filePrintStatestring("已完成","未完成"))?default("未处理")}</td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_payed">&nbsp;总费用:</td>
	     <td >${(filePrintApplication.payed)?if_exists}</td>
	     <td class="title" width="25%" id="f_applyBy">&nbsp;请印人:</td>
	     <td >${(filePrintApplication.applyBy.name)?if_exists}</td>
	   </tr>
	   
	   <tr>
	     <td class="title">&nbsp;请印<@bean.message key="attr.remark"/>:</td>
	     <td class="brightStyle" colspan="3">
	      <textarea name="filePrintApplication.remark" cols="25" style="width:100%" rows="10">${(filePrintApplication.remark)?if_exists}</textarea>
         </td>
	   </tr>
	  
	   <tr class="darkColumn">
	   	 <td colspan="4" align="center">
	   	    <input type="hidden" name="filePrintApplication.id" value="${(filePrintApplication.id)?if_exists}">
	     	<input type="button" value="<@bean.message key="action.submit"/>" onclick="save(this.form)" class="buttonStyle"/>
         	<input type="reset" value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>   
         </td>
	   </tr>
       </form>
	  </table>
	  
 <script language="javascript" > 
     function save(form){
        var a_fields = {
         'filePrintApplication.depart.id':{'l':'请印院系', 'r':true, 't':'f_depart'},
         'filePrintApplication.value':{'l':'请印数量', 'r':true, 't':'f_number'},
         'filePrintApplication.printAt':{'l':'请印日期', 'r':true, 't':'f_printDate'},
         'filePrintApplication.copyAt':{'l':'复印日期', 'r':true, 't':'f_copyDate'}
        };
       var v = new validator(form , a_fields, null);
       if (v.exec()) {
        form.submit();
       }
   }
 </script>
	  
 </body>