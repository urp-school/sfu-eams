<#if !moduleName?exists>
<#assign moduleName="null"/>
</#if>
<#assign formName="pageGoForm"/>
<#assign formAction="secondSpecialityStudentAuditManager.do"/>
<#assign tableTitle="学籍查询项" />
<#assign isCalendarNeed=false />
<#assign isSecondSpecialityNeed=true />
<#assign isFirstSpecialityNeed=false />
<#assign showInput>

<#assign formElementWidth = "width:100px"/>
<#if moduleName=="graduate.secondSpecialityStudentAuditManager">
  	<tr valign="top">
 		<td id="f_auditStatus">一专业审核状态</td>
     	<#if result.studentGraduateAuditStatus?exists>
     		<#assign studentGraduateAuditStatus = result.studentGraduateAuditStatus/>
     	<#elseif RequestParameters.studentGraduateAuditStatus?exists>
     		<#assign studentGraduateAuditStatus = RequestParameters.studentGraduateAuditStatus/>
     	<#else>
     		<#assign studentGraduateAuditStatus = ""/>
     	</#if>
 		<td>
 			<select name="std.graduateAuditStatus" style="${formElementWidth}" OnMouseOver="changeOptionLength(this);">
 					<option value="">...</option>
					<option value="1" <#if (studentGraduateAuditStatus?index_of(",1,") >= 0)>selected</#if>><@bean.message key="attr.graduate.outsideExam.auditPass"/></option>
					<option value="0" <#if (studentGraduateAuditStatus?index_of(",0,") >= 0)>selected</#if>><@bean.message key="attr.graduate.outsideExam.noAuditPass"/></option>
					<option value="null" <#if (studentGraduateAuditStatus?index_of(",null,") >= 0)>selected</#if>><@bean.message key="attr.graduate.outsideExam.nullAuditPass"/></option>
			</select>
 		</td>
 	</tr>
 	<tr valign="top">
 		<td id="f_auditStatus">双专业审核状态</td>
     	<#if result.secondGraduateAuditStatus?exists>
     		<#assign secondGraduateAuditStatus = result.secondGraduateAuditStatus/>
     	<#else>
     		<#assign secondGraduateAuditStatus = ""/>
     	</#if>
 		<td>
 			<select name="std.secondGraduateAuditStatus" style="${formElementWidth}" OnMouseOver="changeOptionLength(this);">
 					<option value="">...</option>
					<option value="1" <#if (studentGraduateAuditStatus?index_of(",1,") >= 0)>selected</#if>><@bean.message key="attr.graduate.outsideExam.auditPass"/></option>
					<option value="0" <#if (studentGraduateAuditStatus?index_of(",0,") >= 0)>selected</#if>><@bean.message key="attr.graduate.outsideExam.noAuditPass"/></option>
					<option value="null" <#if (studentGraduateAuditStatus?index_of(",null,") >= 0)>selected</#if>><@bean.message key="attr.graduate.outsideExam.nullAuditPass"/></option>
			</select>
 		</td>
  	</tr>
</#if>
</#assign>
<#assign hiddenInput>
<input type="hidden" name="studentGraduateAuditStatus"/>
<input type="hidden" name="secondGraduateAuditStatus"/>
<input type="hidden" name="stdId"/>
<input type="hidden" name="status"/>
</#assign>
<#assign resetFormHiddenInput>
<input type="hidden" name="firstMethod" value="${RequestParameters.method?if_exists}"/>
</#assign>
<#assign functionSearch>
function search(){
	var form=document.${formName};
	form.action="${formAction}?method=search&orderBy=std.code asc";
	submit${formName}();
}
function submit${formName}(){
	document.pageGoForm.target="pageIFrame";
	document.${formName}.submit();
}
</#assign>