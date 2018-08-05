<#include "/templates/head.ftl"/>
 <body > 
 <script>
    function getStd(){
     var stdCode=document.conditionForm['project.student.code'].value;
     if(stdCode==""){ alert("请输入学号"); }
     else{
       studentDAO.getBasicInfoName(setStdInfo,stdCode);
     }
    }
    function setStdInfo(data){
     if(null==data){
       document.getElementById("stdName").innerHTML="没有找到该学号所对应的学生";
     }else{
        $('stdName').innerHTML=data.name;
     }
  }
</script>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<#assign labInfo><#if studyProduct.id?exists>修改项目<#else>添加项目</#if></#assign>
<#include "/templates/back.ftl"/>
<table width="100%" align="center" class="formTable">
	<form name="conditionForm" method="post" action="" onsubmit="return false;">
	   <tr class="darkColumn" align="center">
	     <td colspan="2">项目详细信息</td>
	   </tr>
		<tr>
			<td class="title" width="25%" id="f_std">
				&nbsp;<@msg.message key="attr.stdNo"/><font color="red">*</font>
			</td>
			<td>
			  <#if student?exists>
			  <input type="hidden" name="project.student.id" value="${student?if_exists.id?if_exists}">${student.code}
			  <#elseif (studyProduct.id)?exists>
			   <input type="hidden" name="project.student.id" value="${studyProduct.student?if_exists.id?if_exists}">${studyProduct.student.code}
			  <#else>
			   <input type="text" name="project.student.code" maxlength="32" value="${studyProduct?if_exists.student?if_exists.code?if_exists}" onblur="getStd()"/>学生姓名:<span id="stdName"></span>
			  </#if>
	     	</td>
		</tr>
	   <tr>
	     <td class="title" width="25%" id="f_title">
	      &nbsp;项目名称<font color="red">*</font>：
	     </td>
	     <td>
	      <input type="text" name="project.name" maxlength="20" size="20" value="${studyProduct?if_exists.name?if_exists}"/>
	     </td>
	   </tr>
	    <tr>
	     <td class="title" width="25%" id="f_projectNo">
	      &nbsp;项目编号：
	     </td>
	     <td>
		    <input type="text" name="project.projectNo" maxlength="32" value="${studyProduct?if_exists.projectNo?if_exists}"/>           
	     </td>
	   </tr>
	    <tr>
	     <td class="title" width="25%" id="f_company">
	      &nbsp;立项单位<font color="red">*</font>：
	     </td>
	     <td>
		    <input type="text" name="project.company" maxlength="30" value="${studyProduct?if_exists.company?if_exists}"/>           
	     </td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_projectType">
	      &nbsp;项目类别<font color="red">*</font>：
	     </td>
	     <td>
	      <@htm.i18nSelect datas=projectTypeList selected="${(studyProduct.projectType.id)?default('')?string}" name="project.projectType.id"  style="width:150px;" /></td>
	   </tr>		   	
	   <tr>
	     <td class="title" id="f_startOn">&nbsp;立项日期<font color="red">*</font>： </td>
	     <td>
 		       <input type="text" name="project.startOn" maxlength="10" value="<#if studyProduct?if_exists.startOn?exists>${studyProduct?if_exists.startOn?string("yyyy-MM-dd")}</#if>" onfocus="calendar();f_frameStyleResize(self,0)"/>  
	     </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_endOn">
	      &nbsp;结项日期<font color="red">*</font>：
	     </td>
	     <td>
 		       <input type="text" name="project.endOn" maxlength="10" value="<#if studyProduct?if_exists.endOn?exists>${studyProduct?if_exists.endOn?string("yyyy-MM-dd")}</#if>" onfocus="calendar();f_frameStyleResize(self,0)"/>  
	     </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_bearTask">
	      &nbsp;承担任务<font color="red">*</font>：
	     </td>
	     <td><textarea name="project.bearTask" colspan="40" rowspan="3" style="width:100%">${studyProduct?if_exists.bearTask?if_exists}</textarea> 
	     </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_principal">
	      &nbsp;负责人<font color="red">*</font>：
	     </td>
	     <td>
 		       <input type="text" name="project.principal" maxlength="20" value="${studyProduct?if_exists.principal?if_exists}">
	     </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_researchSort">
	      &nbsp;科研排序<font color="red">*</font>：
	     </td>
	     <td><input type="text" style="width:30px" name="project.authorIndex" maxlength="5" value="${studyProduct?if_exists.authorIndex?if_exists}">/
	         <input type="text" style="width:30px" name="project.authorCount" maxlength="5" value="${studyProduct?if_exists.authorCount?if_exists}">例如 1/1(独立),1/2 斜杠前表示单个人 后表示作者个数 
	     </td>
	   </tr>
	   <tr>  
	     <td class="title" width="25%" id="f_wordCountByPerson">
	      &nbsp;分摊字数：
	     </td>
	     <td><input type="text" name="project.wordCount" maxlength="10" value="${studyProduct?if_exists.wordCount?if_exists}"/> 万字    
	     </td>
	   </tr>		   	   
	   <tr class="darkColumn" align="center">
	     <td colspan="2">
	       <input type="hidden" name="project.id" value="${studyProduct?if_exists.id?if_exists}">
	       <input type="hidden" name="productType" value="${RequestParameters['productType']}"> 
		   <input type="button" value="<@bean.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle"/>&nbsp;
	     </td>
	   </tr>
	   </form>
	 </table>
 </body>
 <script language="javascript" >
     function doAction(form){
      var a_fields = {
      	 <#if !(student?exists||(studyProduct.id)?exists)>
      	 	'project.student.code':{'l':'<@msg.message key="attr.stdNo"/>', 'r':true, 't':'f_std'},
      	 </#if>
         'project.name':{'l':'项目名称', 'r':true, 't':'f_title','mx':100},
         'project.company':{'l':'立项单位', 'r':true, 't':'f_company','mx':100},
         'project.startOn':{'l':'立项日期', 'r':true, 't':'f_startOn'},
         'project.endOn':{'l':'结项日期', 'r':true, 't':'f_endOn'},
         'project.bearTask':{'l':'承担任务', 'r':true, 't':'f_bearTask'},
         'project.principal':{'l':'承担人', 'r':true, 't':'f_principal'},
         'project.authorIndex':{'l':'本人排序', 'r':true, 'f':'integer','t':'f_researchSort'},
         'project.authorCount':{'l':'作者人数', 'r':true, 'f':'integer','t':'f_researchSort'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        setSearchParams(parent.document.searchForm,form);
        if(parseInt(form['project.authorCount'].value)<parseInt(form['project.authorIndex'].value)){
          alert("本人排序不能大于作者人数");return;
        }
     	form.action="<#if student?exists>studyProductStd.do<#else>studyProduct.do</#if>?method=save";
        form.submit();
     } 
    }
 </script>
<#include "/templates/foot.ftl"/>