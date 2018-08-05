<#assign formElementWidth = "width:100px"/>
	<table width="100%" class="searchTable">
		<form name="stdCreditStatForm" method="post" action="stdCreditStat.do" onsubmit="return false;">
			<tr class="infoTitle" valign="top" style="font-size:8pt">
       			<td colspan="2" style="text-align:left;text-valign:bottom;font-weight:bold"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;学籍信息查询</td>
      		</tr>
      		<tr>
      			<td style="font-size:0pt" colspan="2"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/></td>
      		</tr>
			<tr>
				<td id="f_studentType"><@bean.message key="entity.studentType"/></td>
				<td>
					<select id="stdTypeOfSpeciality" name="courseTake.student.type.id" style="${formElementWidth}">
						<option value="${RequestParameters['courseTake.student.type.id']?if_exists}">...</option>						
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
					<select id="department" name="courseTake.student.department.id" style="${formElementWidth}">
						<option value="${RequestParameters['courseTake.student.department.id']?if_exists}">...</option>
					</select>
				</td>
			</tr>
			<tr>
				<td id="f_speciality"><@bean.message key="entity.speciality"/></td>
				<td>
					<select id="speciality" name="courseTake.student.firstMajor.id" style="${formElementWidth}">
						<option value="${RequestParameters['courseTake.student.firstMajor.id']?if_exists}">...</option>
					</select>
				</td>
			</tr>
			<tr>
				<td id="f_specialityAspect"><@bean.message key="entity.specialityAspect"/></td>
	     		<td>
           			<select id="specialityAspect" name="courseTake.student.firstAspect.id" style="${formElementWidth}">
         	  			<option value="${RequestParameters['courseTake.student.firstAspect.id']?if_exists}">...</option>
           			</select>
         		</td>
			</tr>
			<tr>
				<td width="38%"><@msg.message key="attr.stdNo"/></td>
				<td>
					<input type="text" name="courseTake.student.code" maxlength="32" value="${RequestParameters['courseTake.student.code']?if_exists}" style="${formElementWidth}">
				</td>
			</tr>
			<tr>
				<td><@msg.message key="attr.personName"/></td>
				<td>
					<input type="text" name="courseTake.student.name" maxlength="20" value="${RequestParameters['courseTake.student.name']?if_exists}" style="${formElementWidth}">
				</td>
			</tr>
			<tr>
				<td id="f_belongToYear"><@bean.message key="filed.enrollYearAndSequence"/></td>
				<td>
					<input type="text" name="courseTake.student.enrollYear" maxlength="7" value="${RequestParameters['courseTake.student.enrollYear']?if_exists}" style="${formElementWidth}">
				</td>
			</tr>
			<tr>
         		<#assign enrollYearId = "courseTake.student.enrollYear"/>
         		<#assign departmentId = "courseTake.student.department.id"/>
         		<#assign specialityId = "courseTake.student.firstMajor.id"/>
         		<#assign specialityAspectId = "courseTake.student.firstAspect.id"/>
         		<#assign adminClassId = "adminClasssId"/>
	   			<#assign adminClassDescriptions = "adminClassDescriptions"/>
	   			<#assign selectorId = 1 />
	   			<#include "/pages/graduate/adminClassSelectorBarWithSpeciality.ftl"/>
         	</tr>
			<tr>
			 	<td id="f_active">学籍是否有效</td>
			 	<td>
			 		<select name="courseTake.student.active" style="${formElementWidth}" OnMouseOver="changeOptionLength(this);">
						<option value="">...</option>
						<option value="1" <#if RequestParameters['courseTake.student.active']?default("1")=="1">selected</#if>>有效</option>
						<option value="0" <#if RequestParameters['courseTake.student.active']?default("-1")=="0">selected</#if>>无效</option>
					</select>
			 	</td>
			</tr>
			<tr>
			 	<td id="f_inSchool">学生是否在校</td>
			 	<td>
			 		<select name="courseTake.student.inSchool" style="${formElementWidth}" OnMouseOver="changeOptionLength(this);">
						<option value="">...</option>
						<option value="1" <#if RequestParameters['courseTake.student.inSchool']?default("1")=="1">selected</#if>>有效</option>
						<option value="0" <#if RequestParameters['courseTake.student.inSchool']?default("-1")=="0">selected</#if>>无效</option>
					</select>
			 	</td>
			</tr>			
			<tr>
			 	<td id="f_studentStatus">学籍状态</td>
			 	<td>
			 		<select name="courseTake.student.state.id" style="${formElementWidth}" OnMouseOver="changeOptionLength(this);">
						<option value="">...</option>
						<#list result.studentStateList as studentState>
							<option value="${studentState.id}" <#if RequestParameters['courseTake.student.state.id']?default("0")==studentState.id?string >selected</#if>  /><@i18nName studentState /></option>
						</#list>
					</select>
			 	</td>
			</tr>
	  		<tr>
	   			<td colspan="2" align="center">
				    <input type="hidden" id="pageNo" name="pageNo" value="1"/>
				    <input type="hidden" id="method" name="method" value="search"/>				    
				    <input type="hidden" id="moduleName" name="moduleName" value="${moduleName?if_exists}"/>
				    <input type="hidden" id="searchFalg" name="searchFalg" value="${searchFalg?default('search')}"/>
				    ${hiddenInput?if_exists}
				    <input type="button" onClick="search()" value="<@bean.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;
				    <input type="reset" onClick="this.form.reset();" value="<@bean.message key="system.button.reset"/>" name="reset1"  class="buttonStyle"/>				    
       			</td>
	  		</tr>
		</form>
	</table>
<script>

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
       		var form = document.stdCreditStatForm;
       		form.action = "stdCreditStat.do";
        	addAllParams(form);
        	form.target = "pageIFrame";
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