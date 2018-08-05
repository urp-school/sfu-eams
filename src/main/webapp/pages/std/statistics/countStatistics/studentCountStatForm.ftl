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
<#assign formAction="studentCountStatistics.do"/>
<#assign tableTitle="统计人数范围项"/>
<#assign isCalendarNeed=false/>
<#assign isSecondSpecialityNeed=true/>
<#assign isFirstSpecialityNeed=true/>
<#assign targetName="statFrame"/>
<#assign tableWidth="95%"/>
<#assign statMethod="studentCountStat"/>
<#assign showInput>
		<#if moduleName=="StudentStatistics">
	  		<tr>
	     		<td class="grayStyle" id="f_auditStatus">
	      			&nbsp;一专业审核状态：
	     		</td>
	     		<td colSpan="1" class="brightStyle">
		     	<select name="graduateAuditStatus"  style="width:100px;" OnMouseOver="changeOptionLength(this);">
					<option value="">...</option>
					<option value="1" <#if RequestParameters['graduateAuditStatus']?default("-1")=="1">selected</#if>><@bean.message key="attr.graduate.outsideExam.auditPass"/></option>
					<option value="0" <#if RequestParameters['graduateAuditStatus']?default("-1")=="0">selected</#if>><@bean.message key="attr.graduate.outsideExam.noAuditPass"/></option>
					<option value="null" <#if RequestParameters['graduateAuditStatus']?default("-1")=="null">selected</#if>><@bean.message key="attr.graduate.outsideExam.nullAuditPass"/></option>
				</select>
	     		</td>	  		
	     		<td class="grayStyle" id="f_secondStatus">
	      			&nbsp;双专业审核状态：
	     		</td>
	     		<td colSpan="1" class="brightStyle">
		     	<select name="secondGraduateAuditStatus" style="width:100px;" OnMouseOver="changeOptionLength(this);">
					<option value="">...</option>
					<option value="1" <#if RequestParameters['secondGraduateAuditStatus']?default("-1")=="1">selected</#if>><@bean.message key="attr.graduate.outsideExam.auditPass"/></option>
					<option value="0" <#if RequestParameters['secondGraduateAuditStatus']?default("-1")=="0">selected</#if>><@bean.message key="attr.graduate.outsideExam.noAuditPass"/></option>
					<option value="null" <#if RequestParameters['secondGraduateAuditStatus']?default("-1")=="null">selected</#if>><@bean.message key="attr.graduate.outsideExam.nullAuditPass"/></option>
				</select>
	     		</td>
	  		</tr>
	  		<#assign stateStatusShow=true/>
	  		<tr> 
       			<td colspan="4" align="center" class="darkColumn"><B>统计项</B></td>
      		</tr>
      		<tr>
	     		<td class="grayStyle">&nbsp;<@bean.message key="attr.enrollTurn"/></td>
	     		<td class="brightStyle" colspan="3"><input id="enrollTurnInputs" type="text" value="" style="width:100px;"/></td>
	   		</tr>
	  	</#if>
</#assign>
<#assign hiddenInput>
<input type="hidden" name="studentGraduateAuditStatus"/>
<input type="hidden" name="secondGraduateAuditStatus"/>
<input type="hidden" name="stdId"/>
<input type="hidden" name="status"/>
<input type="hidden" name="enrollTurns"/>
<input type="hidden" name="stdTypeIds"/>
</#assign>
<#assign resetFormHiddenInput>
<input type="hidden" name="firstMethod" value="${statMethod}"/>
</#assign>
<#assign method=statMethod/>
<#assign resetMethod="studentCountStatForm"/>
<#assign functionSearch>
function search(){
	var form=document.${formName};
	form.action="${formAction}";
	form.method.value="${statMethod}";
	stat();
}
function submit${formName}(){
	document.${formName}.target="_blank";
	document.${formName}.submit();
}
</#assign>
<table id="studentStatBar" align="center" width="${tableWidth}"></table>
<script>
   var bar = new ToolBar('studentStatBar','学生人数统计查询',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("搜索栏","MM_changeSearchBarStyle('searchBar');","search.gif");
   function stat(){
      document['${formName}']['enrollTurns'].value=DWRUtil.getValue('enrollTurnInputs');
      //document['${formName}']['stdTypeIds'].value=DWRUtil.getValue('stdTypeIdInput');
      submit${formName}();
   }
</script>
<#include "/pages/selector/stdSearchForm.ftl"/>
<#include "/templates/foot.ftl"/>