<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body> 
<#assign labInfo><#if studyProduct.id?exists>修改论文<#else>添加论文</#if></#assign>
<script>
    function getStd(){
     var stdCode=document.conditionForm['studyThesis.student.code'].value;
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
<#include "/templates/back.ftl"/>
<table width="100%" align="center" class="formTable">
	 <form name="conditionForm" method="post" action="" onsubmit="return false;">
	   <tr class="darkColumn" align="center">
	     <td colspan="2">论文详细信息</td>
	   </tr>
		<tr>
			<td class="title" width="25%" id="f_std">
				&nbsp;<@msg.message key="attr.stdNo"/><font color="red">*</font>
			</td>
			<td>
			  <#if student?exists>
			  	  <input type="hidden" name="studyThesis.student.id" value="${student?if_exists.id?if_exists}">${student.code}
			  <#elseif (studyProduct.id)?exists>
			  	   <input type="hidden" name="studyThesis.student.id" value="${studyProduct.student?if_exists.id?if_exists}">${studyProduct.student.code}
			  <#else>
			   <input type="text" name="studyThesis.student.code" maxlength="32" value="${studyProduct?if_exists.student?if_exists.code?if_exists}" onblur="getStd()"/>学生姓名:<span id="stdName"></span>
			  </#if>
	     	</td>
		</tr>
	   <tr>
	     <td class="title" width="25%" id="f_title">
	      &nbsp;论文题目<font color="red">*</font>：
	     </td>
	     <td>
	      <input type="text" name="studyThesis.name" maxlength="100" size="100" value="${studyProduct?if_exists.name?if_exists}"/>
	     </td>
	   </tr>
	    <tr>
	     <td class="title" width="25%" id="f_publication">
	      &nbsp;刊物名称<font color="red">*</font>：
	     </td>
	     <td>
		    <input type="text" name="studyThesis.publicationName" maxlength="30" value="${studyProduct?if_exists.publicationName?if_exists}"/>           
	     </td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_researchType">
	      &nbsp;刊物等级<font color="red">*</font>：
	     </td>
	     <td><@htm.i18nSelect datas=publicationLevelList?sort_by("code") selected="${(studyProduct.publicationLevel.id)?default('')?string}"  name="studyThesis.publicationLevel.id"  style="width:150px;"/></td>
	   </tr>		   		   
	   <tr>
	     <td class="title" id="f_publicationNo">
	      &nbsp;期刊号<font color="red">*</font>：
	     </td>
	     <td>   
		      <input type="text" name="studyThesis.publicationNo" maxlength="20" value="${studyProduct?if_exists.publicationNo?if_exists}"/>
	     </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_publishDate">
	      &nbsp;发表年月<font color="red">*</font>：
	     </td>
	     <td>
 		       <input type="text" name="studyThesis.publishOn" maxlength="10" size="20" value="${studyProduct?if_exists.publishOn?if_exists}" onfocus="calendar();f_frameStyleResize(self,0)"/>  
	     </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_researchSort">
	      &nbsp;科研排序<font color="red">*</font>：
	     </td>
	     <td><input type="text" style="width:30px" name="studyThesis.authorIndex" maxlength="5" value="${studyProduct?if_exists.authorIndex?if_exists}">/
	         <input type="text" style="width:30px" name="studyThesis.authorCount" maxlength="5" value="${studyProduct?if_exists.authorCount?if_exists}">例如 1/1(独立),1/2 斜杠前表示单个人 后表示作者个数 
	     </td>
	   </tr>		   
	   <tr>  
	     <td class="title" width="25%" id="f_wordCountByPerson">
	      &nbsp;分摊字数<font color="red">*</font>：
	     </td>
	     <td>
 		       <input type="text" name="studyThesis.wordCount" maxlength="10" value="${studyProduct?if_exists.wordCount?if_exists}"/> 万字    
	     </td>
	   </tr>  
	   <tr>  
	     <td class="title" width="25%" id="f_totalCount">
	      &nbsp;论文总字数<font color="red">*</font>：
	     </td>
	     <td>
 		       <input type="text" name="studyThesis.totalCount" maxlength="10" value="${studyProduct?if_exists.totalCount?if_exists}"/> 万字    
	     </td>
	   </tr>
	   <tr>
	   		<td class="title" width="25%" id="f_totalCount">
	      		&nbsp;论文索引收录：
	     	</td>
	     	<td>
	     		<table class="formTable">
	     		<#list thesisIndexList?sort_by("code") as index>
	     		   <tr>
	     			<td>${index.name?if_exists}收录检索号:</td><td><input type="text" name="index${index.id}" value="${studyProduct.getIndexNo(index.id)}" maxlength="10"/></td><#if index_index==0><td rowspan="${index?size}">如果被收录,请在对应的<br>收录后面填写检索号.</td></#if>
	     			</tr>
	     		</#list>
	     		</table>
	     	</td>
	   </tr> 		   	   
	   <tr class="darkColumn" align="center">
	     <td colspan="2">
	       <input type="hidden" name="studyThesis.id" value="${studyProduct?if_exists.id?if_exists}">
	       <input type="hidden" name="productType" value="${RequestParameters['productType']}"> 
		   <input type="button" value="<@bean.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle"/>&nbsp;
	     </td>
	   </tr>
	   </form>
		 </table>
 </body>
 <script language="javascript">
     function doAction(form){
      var a_fields = {
      	<#if !((studyProduct.id)?exists||student?exists)>
      		'studyThesis.student.code':{'l':'<@msg.message key="attr.stdNo"/>', 'r':true, 't':'f_std'},
      	</#if>
         'studyThesis.name':{'l':'论文题目', 'r':true, 't':'f_title','mx':100},
         'studyThesis.publicationName':{'l':'刊物名称', 'r':true, 't':'f_publication','mx':100},
         'studyThesis.publicationNo':{'l':'期刊号', 'r':true, 't':'f_publicationNo','mx':50},
         'studyThesis.publishOn':{'l':'发表年月', 'r':true, 't':'f_publishDate'},
         'studyThesis.wordCount':{'l':'分摊字数', 'r':true, 't':'f_wordCountByPerson','f':'real','mx':10},     
         'studyThesis.totalCount':{'l':'论文总字数', 'r':true, 't':'f_totalCount','f':'real','mx':10},
         'studyThesis.authorIndex':{'l':'本人排序', 'r':true, 'f':'integer','t':'f_researchSort'},
         'studyThesis.authorCount':{'l':'作者人数', 'r':true, 'f':'integer','t':'f_researchSort'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        if(parseInt(form['studyThesis.authorCount'].value)<parseInt(form['studyThesis.authorIndex'].value)){
          alert("本人排序不能大于作者人数");return;
        }
     	form.action="<#if student?exists>studyProductStd.do<#else>studyProduct.do</#if>?method=save";
     	setSearchParams(parent.document.searchForm,form);
        form.submit();
     } 
    }
 </script>
<#include "/templates/foot.ftl"/>