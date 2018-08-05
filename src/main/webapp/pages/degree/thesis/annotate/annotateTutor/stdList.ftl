<#include "/templates/head.ftl"/>
<script language="JavaScript" src="scripts/Menu.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="f_frameStyleResize(self,0)">
  <table id="backBar" width="100%"></table>
	<script>
   	var bar = new ToolBar('backBar','<#if (result.hasWrite)?if_exists=="true">已填写自评表的学生<#else>尚未填写自评表的学生</#if>',null,true,true);
   	bar.setMessage('<@getMessage/>');
   function pageGo(pageNo){
       document.pageGoForm1.pageNo.value = pageNo;
       document.pageGoForm1.submit();
    }
    function stdInfo(stdNo){
    	document.pageGoForm.action='annotateTutor.do?method=annotateInfo&studentId='+stdNo;
    	document.pageGoForm.submit();
    }
	/*
	得到查询参数
	*/
	function setData(params){
		addHiddens(document.pageGoForm1,params);
		var parameter = document.getElementById("parameters");
		var myPageNo = document.getElementById("myPageNo");
		params+="&pageNo="+myPageNo.value;
		parameter.value = params;
	}
	</script>
  <table width="100%" align="center" class="listTable">
  	<form name="pageGoForm" method="post" action="" onsubmit="return false;">
    <tr align="center" class="darkColumn">
      <td width="2%"align="center" >
         &nbsp;<input type="checkBox" onClick="toggleCheckBox(document.getElementsByName('thesisManageId'),event);">
      </td>
      <td><@bean.message key="attr.stdNo" /></td>
      <td><@bean.message key="attr.personName" /></td>
      <td><@bean.message key="entity.college" /></td>
      <td><@bean.message key="entity.studentType" /></td>
      <td><@bean.message key="entity.speciality" /></td>
      <#if (result.hasWrite)?if_exists=="true">
      <td>论文评阅书编号</td>
      <td>是否双盲选中</td>
      <td><@bean.message key="filed.annotateThesis" /></td>
      </#if>
    </tr>
    <#list result.studentPage.items as thesisMange>
	  <#if thesisMange_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if thesisMange_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)"  align="center">
      <td align="center" bgcolor="#CBEAFF" >
        <input type="checkBox" name="thesisManageId" value="${thesisMange.id?if_exists}">
      </td> 
      <td>${thesisMange.student.code?if_exists}</td>
      <td>${thesisMange.student.name?if_exists}</td>
      <td>${thesisMange.student.department?if_exists.name?if_exists}</td>
      <td>${thesisMange.student.type?if_exists.name?if_exists}</td>    	 
      <td>${(thesisMange.student.firstMajor.name)?if_exists}</td>
      <#if result.hasWrite?exists&&result.hasWrite=="true">
	  <td>
	  <input type="hidden" id="${thesisMange.id?if_exists}serial" name="${thesisMange.id?if_exists}serial" value="<#if thesisMange.annotate?if_exists.annotateBooks?exists&&(thesisMange.annotate?if_exists.annotateBooks?size>0)>1<#else>0</#if>">
	  <#list (thesisMange.annotate.annotateBooks)?if_exists?sort_by("serial") as annotateBook>
	  		${annotateBook.serial?if_exists},
	  </#list>
	  </td>
	  <td><#if true==(thesisMange.annotate.isDoubleBlind)?if_exists>选中<#else>未选中</#if>
	  </td>
	  <td><a  href ="javascript:stdInfo('${thesisMange.student.id?if_exists}')">查看</a></td>
	  </#if>
    </tr>
    </#list>
   </form>
    <form name="pageGoForm1" method="post">
	    <#assign paginationName="studentPage" />
	    <#include "/templates/pageBar.ftl"/>
	    </form>	   
  </table>  
  </body>
<#include "/templates/foot.ftl"/>
