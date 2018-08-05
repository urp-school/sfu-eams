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
					<select id="stdTypeOfSpeciality" name="std.type.id" style="${formElementWidth}">
						<option value="${RequestParameters['std.type.id']?if_exists}">...</option>
					</select> 
				</td>
			</tr>
			<script>
				function doValidate(){
					return true;
       			}
   			</script>
			<tr>
				<td id="f_departmenty"><@bean.message key="common.college"/></td>
				<td>
					<select id="department" name="std.department.id" style="${formElementWidth}">
						<option value="${RequestParameters['std.department.id']?if_exists}">...</option>
					</select>
				</td>
			</tr>
			<tr>
				<td id="f_speciality"><@bean.message key="entity.speciality"/></td>
				<td>
					<select id="speciality" name="std.firstMajor.id" style="${formElementWidth}">
						<option value="${RequestParameters['std.firstMajor.id']?if_exists}">...</option>
					</select>
				</td>
			</tr>
			<tr>
				<td id="f_specialityAspect"><@bean.message key="entity.specialityAspect"/></td>
	     		<td>
           			<select id="specialityAspect" name="std.firstAspect.id" style="${formElementWidth}">
         	  			<option value="${RequestParameters['std.firstAspect.id']?if_exists}">...</option>
           			</select>
         		</td>
			</tr>
			<tr>
				<td width="38%"><@msg.message key="attr.stdNo"/></td>
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
         		<#assign departmentId = "std.department.id"/>
         		<#assign specialityId = "std.firstMajor.id"/>
         		<#assign specialityAspectId = "std.firstAspect.id"/>
         		<#assign adminClassId = "adminClasssId"/>
	   			<#assign adminClassDescriptions = "adminClassDescriptions"/>
	   			<#assign selectorId = 1 />
	   			<td id="f_className">班级名称</td>
	   			<td>
	   				<input type="text" name="adminClassName" maxlength="7" value="${RequestParameters['adminClassName']?if_exists}" style="${formElementWidth}">
	     		</td>
         	</tr>
			<tr>
			 	<td id="f_state">学籍有效性</td>
			 	<td>
			 		<select name="std.active" style="${formElementWidth}" OnMouseOver="changeOptionLength(this);">
						<option value="">...</option>
						<option value="1" <#if RequestParameters['std.active']?default("1")=="1">selected</#if>>有效</option>
						<option value="0" <#if RequestParameters['std.active']?default("-1")=="0">selected</#if>>无效</option>
					</select>
			 	</td>
			</tr>
			<tr>
			 	<td>是否在校</td>
			 	<td>
			 		<select name="std.inSchool" style="${formElementWidth}">
						<option value="">...</option>
						<option value="1" <#if RequestParameters['std.inSchool']?default("1")=="1">selected</#if>>在校</option>
						<option value="0" <#if RequestParameters['std.inSchool']?default("-1")=="0">selected</#if>>不在校</option>
					</select>
			 	</td>
			</tr>
			<tr>
			 	<td id="f_studentStatus">学籍状态</td>
			 	<td>
			 		<select name="std.state.id" style="${formElementWidth}" OnMouseOver="changeOptionLength(this);">
						<option value="">...</option>
						<#list result.studentStateList as studentState>
							<option value="${studentState.id}" <#if RequestParameters['std.state.id']?default("0")==studentState.id?string >selected</#if>  /><@i18nName studentState /></option>
						</#list>
					</select>
			 	</td>
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
									     		<option value="${auditStandard.id}"<#if RequestParameters['auditStandardId']?exists&&(RequestParameters['auditStandardId']?string==auditStandard.id?string)> selected</#if>><@i18nName auditStandard /></option>
									     	</#list>
								        </select>
						     		</td>
						  		</tr>
			  					<tr>
									<td id="f_auditTerm">指定学期审核</td>
							     	<td>
							      		<input type="text" id="auditTerm" title="输入数据格式为1-6或1,2,3" style="width:90px;" name="auditTerm" value="${(RequestParameters['auditTerm'])?default('')}" maxlength="20"/>
							     	</td>
						  		</tr>
			  				</table>
		  			</fieldSet>
		  		</td>
  			</tr>
	  		<tr>
	   			<td colspan="2" align="center">
				    <input type="hidden" id="pageNo" name="pageNo" value="1"/>
				    <input type="hidden" id="majorTypeId" name="majorTypeId" value="1"/>
				    <input type="hidden" id="moduleName" name="moduleName" value="${moduleName?if_exists}"/>
				    <input type="hidden" id="searchFalg" name="searchFalg" value="${searchFalg?default('search')}"/>
				    ${hiddenInput?if_exists}
				    <input type="button" onClick="search()" value="<@bean.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;
				    <input type="reset" onClick="this.form.reset();" value="<@bean.message key="system.button.reset"/>" name="reset1"  class="buttonStyle"/>				    
       			</td>
	  		</tr>
		</form>
	<form name="resetForm" method="post" action="${formAction}" onsubmit="return false;">
	    <input type="hidden" name="moduleName" value="${moduleName?if_exists}"/>
	    <input type="hidden" name="pageNo" value="1"/>
	    ${resetFormHiddenInput?if_exists}
	</form>
	</table>
<script>
 	var auditStandardArray = new Array(${result.auditStandardList?size});
 	<#list result.auditStandardList as auditStandard>
 		auditStandardArray[${auditStandard_index}]=new Array();
 		auditStandardArray[${auditStandard_index}][0]="${auditStandard.id?string}";
 		auditStandardArray[${auditStandard_index}][1]="${(auditStandard.code)?default('')}";
 		auditStandardArray[${auditStandard_index}][2]="<@i18nName auditStandard />";
 		auditStandardArray[${auditStandard_index}][3]="<@i18nName auditStandard.studentType?if_exists/>";
 		<#assign courseTypeDescriptionsValue = ""/>
 		<#list auditStandard.disauditCourseTypeSet?if_exists as courseType>
	   	<#if courseType_has_next>
	     	<#if courseType.name?exists||courseType.engName?exists>
	     	<#assign courseTypeDescriptionsValue>${courseTypeDescriptionsValue}<@i18nName courseType/>,</#assign>
	     	</#if>
	   	<#else>
	     	<#if courseType.name?exists||courseType.engName?exists>
	     	<#assign courseTypeDescriptionsValue>${courseTypeDescriptionsValue}<@i18nName courseType/></#assign>
	     	</#if>
	   	</#if>
	   	</#list>
 		auditStandardArray[${auditStandard_index}][4]="${courseTypeDescriptionsValue}";
 		<#assign courseTypeDescriptionsValue = ""/>
 		<#list auditStandard.convertableCourseTypeSet?if_exists as courseType>
	   	<#if courseType_has_next>
	     	<#if courseType.name?exists||courseType.engName?exists>
	     	<#assign courseTypeDescriptionsValue>${courseTypeDescriptionsValue}<@i18nName courseType/>,</#assign>
	     	</#if>
	   	<#else>
	     	<#if courseType.name?exists||courseType.engName?exists>
	     	<#assign courseTypeDescriptionsValue>${courseTypeDescriptionsValue}<@i18nName courseType/></#assign>
	     	</#if>
	   	</#if>
	   	</#list>
	   	auditStandardArray[${auditStandard_index}][5]="${courseTypeDescriptionsValue}";
 		auditStandardArray[${auditStandard_index}][6]="${auditStandard.remark?if_exists}";
 	</#list>

    var sds = new StdTypeDepart3Select("stdTypeOfSpeciality","department","speciality","specialityAspect", true, true, true, true);    
    sds.init(stdTypeArray,departArray);
    sds.firstSpeciality=1;
    function changeSpecialityType(event){
       var select = getEventTarget(event);
       sds.firstSpeciality=select.value;
       fireChange($("department"));
    }
	function addAllParams(form){
		var params = getInputParams(form,null,false);
       	addInput(form,"params",params);
	}
	
	<#assign defaultFunctionSearch>
	function search(){
       	if (doValidate()) {
       		var form = document.${formName};
       		form.action = "${formAction}";
        	addAllParams(form);
          	form.submit();
        }
    }
    </#assign>
	${functionSearch?default(defaultFunctionSearch)}
	
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
</script>