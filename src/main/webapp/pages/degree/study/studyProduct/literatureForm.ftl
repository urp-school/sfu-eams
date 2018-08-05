<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<body > 
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<#assign labInfo><#if studyProduct.id?exists>修改著作<#else>添加著作</#if></#assign>
<script>
    function getStd(){
     var stdCode=document.conditionForm['literature.student.code'].value;
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
	     <td colspan="2">著作详细信息</td>
	   </tr>
		<tr>
			<td class="title" width="25%" id="f_std">
				<@msg.message key="attr.stdNo"/><font color="red">*</font>：</td>
			<td>
			  <#if student?exists>
			  <input type="hidden" name="literature.student.id" value="${student?if_exists.id?if_exists}">${student.code}
			  <#elseif (studyProduct.id)?exists>
			  		<input type="hidden" name="literature.student.id" value="${studyProduct.student.id?if_exists}"/>${studyProduct.student.code?if_exists}
			  <#else>
			   <input type="text" name="literature.student.code" maxlength="32" value="${studyProduct?if_exists.student?if_exists.code?if_exists}" onblur="getStd()"/>学生姓名:<span id="stdName"></span>
			  </#if>
	     	</td>
		</tr>
	   <tr>
	     <td class="title" width="25%" id="f_title">
	      &nbsp;著作名称<font color="red">*</font>：
	     </td>
	     <td>
	      <input type="text" name="literature.name" maxlength="20" size="20" value="${studyProduct?if_exists.name?if_exists}"/>
	     </td>
	   </tr>
	    <tr>
	     <td class="title" width="25%" id="f_publication">
	      &nbsp;出版社名称<font color="red">*</font>：
	     </td>
	     <td>
		    <input type="text" name="literature.publishCompany" maxlength="50" value="${studyProduct?if_exists.publishCompany?if_exists}"/>           
	     </td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_researchType">
	      &nbsp;著作类别<font color="red">*</font>：
	     </td>
	     <td><@htm.i18nSelect datas=literatureTypeList selected="${(studyProduct.literatureType.id)?default('')?string}" name="literature.literatureType.id" style="width:150px;"/></td>
	   </tr>		   	
	   <tr>
	     <td class="title" id="f_publishDate">
	      &nbsp;发表出版年月<font color="red">*</font>：
	     </td>
	     <td>
 		       <input type="text" name="literature.publishOn" maxlength="10" size="20" value="${studyProduct?if_exists.publishOn?if_exists}" onfocus="calendar();f_frameStyleResize(self,0)"/>  
	     </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_researchSort">
	      &nbsp;科研排序<font color="red">*</font>：
	     </td>
	     <td><input type="text" style="width:30px" name="literature.authorIndex" maxlength="5" value="${studyProduct?if_exists.authorIndex?if_exists}">/
	         <input type="text" style="width:30px" name="literature.authorCount" maxlength="5" value="${studyProduct?if_exists.authorCount?if_exists}">例如 1/1(独立),1/2 斜杠前表示单个人 后表示作者个数 
	     </td>
	   </tr>		   
	   <tr>  
	     <td class="title" width="25%" id="f_wordCountByPerson">
	      &nbsp;分摊字数<font color="red">*</font>：
	     </td>
	     <td>
 		       <input type="text" name="literature.wordCount" maxlength="7" value="${studyProduct?if_exists.wordCount?if_exists}"/> 万字    
	     </td>
	   </tr>  
	   <tr>  
	     <td class="title" width="25%" id="f_totalCount">
	      &nbsp;论文总字数<font color="red">*</font>：
	     </td>
	     <td>
 		       <input type="text" name="literature.totalCount" maxlength="10" value="${studyProduct?if_exists.totalCount?if_exists}"/> 万字    
	     </td>
	   </tr>		   	   
	   <tr class="darkColumn" align="center">
	     <td colspan="2">
	       <input type="hidden" name="literature.id" value="${studyProduct?if_exists.id?if_exists}">
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
      	 <#if !((studyProduct.id)?exists||student?exists)>
      	 'literature.student.code':{'l':'<@msg.message key="attr.stdNo"/>', 'r':true, 't':'f_std'},
      	 </#if>
         'literature.name':{'l':'著作名称', 'r':true, 't':'f_title','mx':100},
         'literature.publishCompany':{'l':'出版社名称', 'r':true, 't':'f_publication','mx':100},
         'literature.publishOn':{'l':'发表年月', 'r':true, 't':'f_publishDate'},
         'literature.wordCount':{'l':'分摊字数', 'r':true, 't':'f_wordCountByPerson','f':'real','mx':10},     
         'literature.totalCount':{'l':'论文总字数', 'r':true, 't':'f_totalCount','f':'real','mx':10},
         'literature.authorIndex':{'l':'本人排序', 'r':true, 'f':'integer','t':'f_researchSort'},
         'literature.authorCount':{'l':'作者人数', 'r':true, 'f':'integer','t':'f_researchSort'}          
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        setSearchParams(parent.document.searchForm,form);
        if(parseInt(form['literature.authorCount'].value)<parseInt(form['literature.authorIndex'].value)){
          alert("本人排序不能大于作者人数");return;
        }
     	form.action="<#if student?exists>studyProductStd.do<#else>studyProduct.do</#if>?method=save";
        form.submit();
     } 
    }
 </script>
<#include "/templates/foot.ftl"/>