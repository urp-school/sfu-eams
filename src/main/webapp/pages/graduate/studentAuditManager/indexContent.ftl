<#assign formName="pageGoForm" />
<#assign formAction="studentAuditManager.do" />
<#assign tableTitle="学籍查询项" />
<#assign isCalendarNeed=false />
<#assign showInput>

<#assign formElementWidth = "width:100px"/>

	<#assign stateStatusShow=true />
  	<tr valign="top">
 		<td id="f_auditStatus"><@bean.message key="attr.graduate.auditStatus"/></td>
 		<td>
			<#if result.studenGraduateAuditStatus?exists && result.studenGraduateAuditStatus != "">
		 		<#assign studentGraduateAuditStatus = result.studenGraduateAuditStatus />
		 	<#else>
		 		<#assign studentGraduateAuditStatus = ""/>
		 	</#if>
	   		<select name="std.graduateAuditStatus" style="${formElementWidth}" OnMouseOver="changeOptionLength(this);">
	   			<option value="">...</option>
				<option value="1" <#if (studentGraduateAuditStatus?index_of(",1,") >= 0)>selected</#if>><@bean.message key="attr.graduate.outsideExam.auditPass"/></option>
				<option value="0" <#if (studentGraduateAuditStatus?index_of(",0,") >= 0)>selected</#if>><@bean.message key="attr.graduate.outsideExam.noAuditPass"/></option>
				<option value="null" <#if (studentGraduateAuditStatus?index_of(",null,") >= 0)>selected</#if>><@bean.message key="attr.graduate.outsideExam.nullAuditPass"/></option>
			</select>
 		</td>
  	</tr>

</#assign>
<#assign hiddenInput>
<input type="hidden" id="studentGraduateAuditStatus" name="studentGraduateAuditStatus"/>
<input type="hidden" id="stdId" name="stdId"/>
<input type="hidden" id="status" name="status"/>
</#assign>
<#assign resetFormHiddenInput>
<input type="hidden" name="firstMethod" value="${RequestParameters.method?if_exists}"/>
</#assign>
<#assign functionSearch>
function search(){
	var form=document.${formName};
    form.action = "${formAction}?method=search&orderBy=std.code asc";
	submit${formName}();
}
function submit${formName}(){
	document.pageGoForm.target="pageIFrame";
	document.${formName}.submit();
}
</#assign>