<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<#if RequestParameters['moduleName']?exists>
<#assign moduleName=RequestParameters['moduleName']>
<#else>
<#assign moduleName="StudentStatistics">
</#if>
<#assign formName="pageGoForm"/>
<#assign formAction="studentChartStatistics.do"/>
<#assign tableTitle="统计学籍范围项"/>
<#assign isCalendarNeed=false/>
<#assign isSecondSpecialityNeed=true/>
<#assign isFirstSpecialityNeed=true/>
<#assign targetName="statFrame"/>
<#assign tableWidth="95%"/>
<#assign statMethod="studentInfoStat"/>
<#assign stateStatusShow=true/>
<#assign showInput>
		<#if moduleName=="StudentStatistics">
	  		<tr>
	     		<td class="grayStyle" id="f_auditStatus">
	      			&nbsp;一专业审核状态：
	     		</td>
	     		<td colSpan="1" class="brightStyle">
			     	<#if result.studentGraduateAuditStatus?exists>
			     		<#assign studentGraduateAuditStatus = result.studentGraduateAuditStatus/>
			     	<#elseif RequestParameters.studentGraduateAuditStatus?exists>
			     		<#assign studentGraduateAuditStatus = RequestParameters.studentGraduateAuditStatus/>
			     	<#else>
			     		<#assign studentGraduateAuditStatus = ""/>
			     	</#if>
		      		&nbsp;<input type="checkBox" name="studentGraduateAuditStatusCheckbox" value="1" <#if (studentGraduateAuditStatus?index_of(',1,')>=0)>checked</#if>/><@bean.message key="attr.graduate.outsideExam.auditPass"/>
		       		<input type="checkBox" name="studentGraduateAuditStatusCheckbox" value="0" <#if (studentGraduateAuditStatus?index_of(',0,')>=0)>checked</#if>/><@bean.message key="attr.graduate.outsideExam.noAuditPass"/>	       
		       		<input type="checkBox" name="studentGraduateAuditStatusCheckbox" value="" <#if (studentGraduateAuditStatus?index_of(',null,')>=0)>checked</#if>/><@bean.message key="attr.graduate.outsideExam.nullAuditPass"/>	       
	     		</td>	  		
	     		<td class="grayStyle" id="f_auditStatus">
	      			&nbsp;双专业审核状态：
	     		</td>
	     		<td colSpan="1" class="brightStyle">
			     	<#if result.secondGraduateAuditStatus?exists>
			     		<#assign secondGraduateAuditStatus = result.secondGraduateAuditStatus/>
			     	<#else>
			     		<#assign secondGraduateAuditStatus = ""/>
			     	</#if>
		      		&nbsp;<input type="checkBox" name="secondGraduateAuditStatusCheckbox" value="1" <#if (secondGraduateAuditStatus?index_of(',1,')>=0)>checked</#if>/><@bean.message key="attr.graduate.outsideExam.auditPass"/>
		       		<input type="checkBox" name="secondGraduateAuditStatusCheckbox" value="0" <#if (secondGraduateAuditStatus?index_of(',0,')>=0)>checked</#if>/><@bean.message key="attr.graduate.outsideExam.noAuditPass"/>	       
		       		<input type="checkBox" name="secondGraduateAuditStatusCheckbox" value="" <#if (secondGraduateAuditStatus?index_of(',null,')>=0)>checked</#if>/><@bean.message key="attr.graduate.outsideExam.nullAuditPass"/>	       
	     		</td>
	  		</tr>
	  	</#if>
</#assign>
<#assign showInput = ""/>
<#assign hiddenInput>
<input type="hidden" name="studentGraduateAuditStatus"/>
<input type="hidden" name="secondGraduateAuditStatus"/>
<input type="hidden" name="stdId"/>
<input type="hidden" name="status"/>
<input type="hidden" name="statElement"/>
</#assign>
<#assign resetFormHiddenInput>
<input type="hidden" name="firstMethod" value="${statMethod}"/>
</#assign>
<#assign method=statMethod />
<#assign resetMethod="studentInfoStatForm"/>
<#assign functionSearch>
function search(){
	var form=document.${formName};
	form.action="${formAction}";
	form.method.value="${statMethod}";
	form["studentGraduateAuditStatus"].value = ","+getCheckBoxValue(document.getElementsByName("studentGraduateAuditStatusCheckbox"))+",";
	form["secondGraduateAuditStatus"].value = ","+getCheckBoxValue(document.getElementsByName("secondGraduateAuditStatusCheckbox"))+",";	
	<#-->addAllParams(form);-->
	submit${formName}();
}
function submit${formName}(){
	document.${formName}.target="${targetName}";
	document.${formName}.submit();
	MM_changeSearchBarStyle('searchBar');
	var barObj = MM_findObj('searchBar')
	barObj.style.visibility = 'hidden';
  	barObj.style.display = 'none';
}
</#assign>
<table id="studentStatBar" align="center" width="${tableWidth}"></table>
<script>
   var bar = new ToolBar('studentStatBar','学籍状态统计查询',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("搜索栏","MM_changeSearchBarStyle('searchBar');","search.gif");
   function stat(td,kind){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      document['${formName}']['statElement'].value=kind;
      submit${formName}();
   }
</script>
<#include "/pages/selector/stdSearchForm.ftl"/>
<table class="frameTable" style="width:${tableWidth}" align="center">
   <tr>
   <td valign="top" class="frameTable_view" width="20%" style="font-size:10pt">
      <table  width="100%" id ="viewTables" style="font-size:10pt">
	    <tr>
	      <td class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>统计项</B>      
	      </td>
	    <tr>
	      <td colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr> 
	   <tr>
         <td class="padding"  onclick="stat(this,'')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/detail.gif"> 全部
         </td>
       </tr>   
       <tr>
         <td class="padding"  onclick="stat(this,'department')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/detail.gif"> 按院系
         </td>
       </tr>
       <tr >
         <td class="padding" onclick="stat(this,'type')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          &nbsp;&nbsp;<image src="${static_base}/images/action/detail.gif"> 按学生类别
         </td>
       </tr>
       <tr >
         <td class="padding" onclick="stat(this,'basicInfo.gender')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          &nbsp;&nbsp;<image src="${static_base}/images/action/detail.gif"> 按性别
         </td>
       </tr>
       <tr >
         <td class="padding" onclick="stat(this,'enrollYear')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          &nbsp;&nbsp;<image src="${static_base}/images/action/detail.gif"> 按入学年份
         </td>
       </tr>
	  </table>
	<td>
	<td valign="top">
     	<iframe name="statFrame" src="studentChartStatistics.do?method=${statMethod}" width="${tableWidth}" frameborder="0" scrolling="no">
	</td>
</table>
</body>
<#include "/templates/foot.ftl"/>