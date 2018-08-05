<#assign formElementWidth = "width:100px"/>
<table width="100%" class="searchTable">
	<form name="${formName}" method="post" action="${formAction}" target="${targetValue?default('_self')}" onsubmit="return false;">
		<tr class="infoTitle" valign="top" style="font-size:8pt">
   			<td colspan="2" style="text-align:left;text-valign:bottom;font-weight:bold"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;学籍信息查询</td>
  		</tr>
  		<tr>
  			<td style="font-size:0pt" colspan="2"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/></td>
  		</tr>
		<tr>
   			<td id="f_studentType"><@bean.message key="entity.studentType"/></td>
			<td>
				<select id="stdType" name="std.type.id" style="${formElementWidth}">
					<option value="${RequestParameters['std.type.id']?if_exists}">...</option>						
				</select>
			</td>
		</tr>
     	<tr>
			<td id="f_secondSpeciality">双专业</td>
			<td>
				<select id="secondMajor" name="std.secondMajor.id" style="${formElementWidth}">
					<option value="${RequestParameters['std.secondMajor.id']?if_exists}">...</option>
				</select>
			</td>
		</tr>
     	<tr>
			<td id="f_specialityAspect">双专业方向</td>
     		<td>
       			<select id="secondAspect" name="std.secondAspect.id" style="${formElementWidth}">
     	  			<option value="${RequestParameters['std.secondAspect.id']?if_exists}">...</option>
       			</select>
     		</td>
		</tr>
		<#include "/templates/secondSpeciality2Select.ftl"/>
		<tr>
			<td width="40%"><@msg.message key="attr.stdNo"/></td>
			<td>
				<input type="text" name="std.code" maxlength="32" value="${RequestParameters['std.code']?if_exists}" style="${formElementWidth}">
			</td>
  		</tr>
		<tr>
			<td><@msg.message key="attr.personName"/></td>
			<td>
				<input type="text" name="std.name" maxlength="20" value="${RequestParameters['std.name']?if_exists}" style="${formElementWidth}">
			</td>
		</tr>
		<tr>
			<td id="f_belongToYear"><@bean.message key="filed.enrollYearAndSequence"/></td>
			<td>
				<input type="text" name="std.enrollYear" maxlength="7" value="${RequestParameters['std.enrollYear']?if_exists}" style="${formElementWidth}">
			</td>
		</tr>
		<tr>
			<#assign enrollYearId = "std.enrollYear"/>
     		<#assign departmentId = ""/>
     		<#assign specialityId = "std.secondMajor.id"/>
     		<#assign specialityAspectId = "std.secondAspect.id"/>
     		<#assign adminClassId = "adminClasssId"/>
   			<#assign adminClassDescriptions = "adminClassDescriptions"/>
   			<#assign colspanId = 1 />
   			<#assign majorTypeId = 2 />
   			<#assign selectorId = 2 />
   			<td id="f_className">班级名称</td>
   			<td>
   				<input type="text" name="adminClassName" maxlength="7" value="${RequestParameters['adminClassName']?if_exists}" style="${formElementWidth}">
     		</td>
		</tr>
		<tr>
   			<td id="f_isSecondMajorStudy">是否就读</td>
     		<td>
       			<select id="isSecondMajorStudy" name="std.isSecondMajorStudy" style="${formElementWidth}">
     	  			<option value="">...</option>
     	  			<option value="1" <#if RequestParameters['std.isSecondMajorStudy']?default("")=="1">selected</#if>>就读</option>
     	  			<option value="0" <#if RequestParameters['std.isSecondMajorStudy']?default("")=="0">selected</#if>>不就读</option>
       			</select>
     		</td>
		</tr>
		<tr>
			<td id="f_isSecondMajorThesisNeed">是否写论文</td>
     		<td>
       			<select name="std.isSecondMajorThesisNeed" style="${formElementWidth}">
     	  			<option value="">...</option>
     	  			<option value="1" <#if RequestParameters['std.isSecondMajorThesisNeed']?default("")=="1">selected</#if>>需要</option>
     	  			<option value="0" <#if RequestParameters['std.isSecondMajorThesisNeed']?default("")=="0">selected</#if>>不需要</option>
       			</select>
     		</td>
     		<td></td>
     		<td></td>
		</tr>
		${showInput?if_exists}			
	  	<tr>
	  		<td colspan="2">
	  			<fieldSet align="left">
	  				<legend style="font-weight:bold">毕业审核标准</legend>
		  				<table width="100%">
		  					<tr>
					     		<td>审核标准</td>
					     		<td>
						     		<select id="f_auditStandard" name="auditStandardId" style="width:90px;">
								     	<#list result.auditStandardList?if_exists as auditStandard >
								     		<option value="${auditStandard.id}" <#if RequestParameters['auditStandardId']?exists&&(RequestParameters['auditStandardId']?string==auditStandard.id?string)>selected</#if>><@i18nName auditStandard /></option>
								     	</#list>
							        </select>
					     		</td>
				     		</tr>
				     		</tr>
								<td id="f_auditTerm">指定学期审核</td>
						     	<td>
						      		<input type="text" id="auditTerm" title="输入数据格式为1-6或1,2,3" style="width:90px" name="auditTerm" value="${(RequestParameters['auditTerm'])?default('')}" maxlength="20"/>
						     	</td>
					     	</tr>
		  				</table>
	  			</fieldSet>
	  		</td>
  		</tr>
  		<tr>
   			<td align="center" colspan="2">
			    <input type="hidden" id="pageNo" name="pageNo" value="1"/>
			    <input type="hidden" id="majorTypeId" name="majorTypeId" value="2"/>
			    <input type="hidden" id="moduleName" name="moduleName" value="${moduleName?if_exists}"/>
			    <input type="hidden" id="searchFalg" name="searchFalg" value="${searchFalg?default('search')}"/>
			    ${hiddenInput?if_exists}
			    <input type="button" onClick="search()" value="<@bean.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;
			    <input type="reset" onClick="form.reset()" value="<@bean.message key="system.button.reset"/>" name="reset1"  class="buttonStyle"/>				    
   			</td>
  		</tr>
	</form>
<form name="resetForm" method="post" action="${formAction}" onsubmit="return false;">
    <input type="hidden" name="method" value="${resetMethod?default("")}"/>
    <input type="hidden" name="moduleName" value="${moduleName?if_exists}"/>
    <input type="hidden" name="pageNo" value="1"/>
    ${resetFormHiddenInput?if_exists}
</form>
</table>
<script>
    var defaultValues=new Object();
    var stdTypeArray = new Array();
    <#list stdTypeList?sort_by("code") as stdType>
    stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    </#list>
	initStdTypeSelect();
	function initStdTypeSelect(){
		if( null==document.getElementById('stdType')) return;
		defaultValues['stdType']=document.getElementById('stdType').value;        
		DWRUtil.removeAllOptions('stdType');
		DWRUtil.addOptions('stdType',stdTypeArray,'id','name');
		DWRUtil.addOptions('stdType',[{'id':'','name':'...'}],'id','name');
		setSelected(document.getElementById('stdType'),defaultValues['stdType']);
	}
	${functionSearch?if_exists}
	function doValidate(){
		return true;
	}
	function addAllParams(form){
		var params = getInputParams(form,null,false);
       	addInput(form,"params",params);
	}
	
	function changeOptionLength(obj){
		var OptionLength=obj.style.width;
		var OptionLengthArray = OptionLength.split("px");
		var oldOptionLength = OptionLengthArray[0];
		OptionLength=oldOptionLength;
		for(var i=0;i<obj.options.length;i++){
			if(obj.options[i].text==""||obj.options[i].text=="...")continue;
			if(obj.options[i].text.length*13>OptionLength){OptionLength=obj.options[i].text.length*13;}
		}
		if(OptionLength>oldOptionLength)obj.style.width=OptionLength;
	}
 	var auditStandardArray = new Array(${result.auditStandardList?size});
 	<#list result.auditStandardList as auditStandard>
 		auditStandardArray[${auditStandard_index}]=new Array();
 		auditStandardArray[${auditStandard_index}][0]="${auditStandard.id?string}";
 		auditStandardArray[${auditStandard_index}][1]="${(auditStandard.code)?default('')}";
 		auditStandardArray[${auditStandard_index}][2]="<@i18nName auditStandard />";
 		auditStandardArray[${auditStandard_index}][3]="<@i18nName auditStandard.studentType?if_exists />";
 		<#assign courseTypeDescriptionsValue = "" />
 		<#list auditStandard.disauditCourseTypeSet?if_exists as courseType> 		
	   	<#if courseType_has_next>	     	
	     	<#if courseType.name?exists||courseType.engName?exists>
	     	<#assign courseTypeDescriptionsValue>${courseTypeDescriptionsValue}<@i18nName courseType />,</#assign>
	     	</#if>
	   	<#else>	   	 	
	     	<#if courseType.name?exists||courseType.engName?exists>
	     	<#assign courseTypeDescriptionsValue>${courseTypeDescriptionsValue}<@i18nName courseType /></#assign>
	     	</#if>
	   	</#if>
	   	</#list>
 		auditStandardArray[${auditStandard_index}][4]="${courseTypeDescriptionsValue}";
 		<#assign courseTypeDescriptionsValue = "" />
 		<#list auditStandard.convertableCourseTypeSet?if_exists as courseType> 		
	   	<#if courseType_has_next>	     	
	     	<#if courseType.name?exists||courseType.engName?exists>
	     	<#assign courseTypeDescriptionsValue>${courseTypeDescriptionsValue}<@i18nName courseType />,</#assign>
	     	</#if>
	   	<#else>	   	 	
	     	<#if courseType.name?exists||courseType.engName?exists>
	     	<#assign courseTypeDescriptionsValue>${courseTypeDescriptionsValue}<@i18nName courseType /></#assign>
	     	</#if>
	   	</#if>
	   	</#list>
	   	auditStandardArray[${auditStandard_index}][5]="${courseTypeDescriptionsValue}";
 		auditStandardArray[${auditStandard_index}][6]="${auditStandard.remark?if_exists}";
 	</#list>
</script>