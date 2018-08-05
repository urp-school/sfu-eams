<div id="searchBar" style="position:relative; visibility: visible; display:block;">
<#if studentPrefix?exists&&studentPrefix!="">
	<#assign std_pre=studentPrefix+"."/>
<#else>
	<#assign std_pre=""/>
</#if>
<#if calendarPrefix?exists&&calendarPrefix!="">
	<#assign cal_pre=calendarPrefix+"."/>
<#else>
	<#assign cal_pre=""/>
</#if>
<table width="${tableWidth?default('90%')}" align="center" class="formTable">
		<form name="${formName}" method="post" action="${formAction}" target="${targetValue?default('_self')}" onsubmit="return false;">
			<tr>
       			<td colspan="4" align="center" class="darkColumn"><B>${tableTitle?default('学籍信息查询')}</B></td>
      		</tr>
			<tr>
				<td class="grayStyle" id="f_departmenty">
					&nbsp;<@msg.message key="attr.stdNo"/>：
				</td>
				<td class="brightStyle" width="30%">
					<input type="text" name="${std_pre}student.code" maxlength="32" size="15" value="${RequestParameters[std_pre + "student.code"]?if_exists}" style="width:100px"/>
				</td>
				<td class="grayStyle" id="f_studentType">
					&nbsp;<@msg.message key="attr.personName"/>:
				</td>
				<td class="brightStyle" width="30%">
					<input type="text" name="${std_pre}student.name" maxlength="20" size="15" value="${RequestParameters[std_pre + "student.name"]?if_exists}" style="width:100px"/>
				</td>
			</tr>
			<tr>
				<td class="grayStyle" id="f_belongToYear">
					&nbsp;<@bean.message key="filed.enrollYearAndSequence"/>：
				</td>
				<td class="brightStyle">
					<input type="text" name="${std_pre}student.enrollYear" maxlength="7" size="6" value="${RequestParameters[std_pre + "student.enrollYear"]?if_exists}" style="width:100px"/>
				</td>
				<#if isCalendarNeed?default(false)>
				<td class="grayStyle" id="f_studentType">
					&nbsp;<@bean.message key="entity.studentType"/><font color='red'>*</font>：
				</td>
				<td class="brightStyle">
					<select id="stdType" name="${cal_pre}calendar.studentType.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
						<option value="${RequestParameters[cal_pre + "calendar.studentType.id"]?if_exists}"></option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="grayStyle" id="f_year">
					&nbsp;<@bean.message key="attr.year2year"/><font color='red'>*</font>：
				</td>
				<td class="brightStyle">
					<select id="year" name="${cal_pre}calendar.year" style="width:100px;">
						<option value="${RequestParameters[cal_pre + "calendar.year"]?if_exists}"></option>
					</select>
				</td>
				<td class="grayStyle" id="f_term">
	      			&nbsp;<@bean.message key="attr.term"/><font color='red'>*</font>：
	     		</td>
	     		<td class="brightStyle">
	         		<select id="term" name="${cal_pre}calendar.term" style="width:100px">
	            		<option value="${RequestParameters[cal_pre + "calendar.term"]?if_exists}"></option>
	          		</select>
         		</td>
			</tr>
			<script>
				function doValidate(){
					var a_fields = {
		         		'year':{'l':'<@bean.message key="attr.year2year"/>', 'r':true, 't':'f_year'},
		         		'term':{'l':'<@bean.message key="attr.term"/>', 'r':true, 't':'f_term'},
		         		'stdType':{'l':'<@bean.message key="entity.studentType"/>', 'r':true, 't':'f_studentType'}
       				};
       				var v = new validator(document.${formName}, a_fields, null);
       				return v.exec();
       			}
       		</script>
       		<#assign stdTypeId = "${cal_pre}calendar.studentType.id"/>
			<#elseif isFirstSpecialityNeed?default(true)>
			<#assign stdTypeId = "${std_pre}student.type.id"/>
				<td class="grayStyle" id="f_studentType">
					&nbsp;<@bean.message key="entity.studentType"/>：
				</td>
				<td class="brightStyle">
					<select id="stdTypeOfSpeciality" name="${std_pre}student.type.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
						<option value="${RequestParameters[std_pre + "student.type.id"]?if_exists}">...</option>
					</select>
				</td>
				</tr>
				<script>
					function doValidate(){
						return true;
	       			}
       			</script>
       		<#else>
       			<td class="grayStyle" id="f_studentType">
					&nbsp;<@bean.message key="entity.studentType"/>：
				</td>
				<td class="brightStyle">
					<select id="stdType" name="${std_pre}student.type.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
						<option value="${RequestParameters[std_pre + "student.type.id"]?if_exists}">...</option>
					</select>
				</td>
				</tr>
				<script>
					<#if result?if_exists.stdTypeList?exists>
				    <#assign stdTypeList=result.stdTypeList>
				    </#if>
				    var defaultValues=new Object();
				    var stdTypeArray = new Array();
				    <#list stdTypeList?sort_by("code") as stdType>
				    stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
				    </#list>
				    initStdTypeSelect();
				    function initStdTypeSelect(){
				        if( null==document.getElementById('stdType')) return;
				        defaultValues['stdType"]=document.getElementById('stdType').value;
				        DWRUtil.removeAllOptions('stdType');
				        DWRUtil.addOptions('stdType',stdTypeArray,'id','name');
				        DWRUtil.addOptions('stdType',[{'id':'','name':'...'}],'id','name');
				        setSelected(document.getElementById('stdType'),defaultValues['stdType"]);
				    }
					function doValidate(){
						return true;
	       			}
       			</script>
			</#if>
			<#if isFirstSpecialityNeed?default(true)>
			<tr>
				<td class="grayStyle" id="f_departmenty">
					&nbsp;<@bean.message key="common.college"/>：
				</td>
				<td class="brightStyle">
					<select id="department" name="${std_pre}student.department.id" style="width:100px;" OnMouseOver="changeOptionLength(this);" >
						<option value="${RequestParameters[std_pre + "student.department.id"]?if_exists}">...</option>
					</select>
				</td>
				<td class="grayStyle" id="f_speciality">
					&nbsp;<@bean.message key="entity.speciality"/>：
				</td>
				<td class="brightStyle">
					<select id="speciality" name="${std_pre}student.firstMajor.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
						<option value="${RequestParameters[std_pre + "student.firstMajor.id"]?if_exists}">...</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="grayStyle" id="f_specialityAspect">
					&nbsp;<@bean.message key="entity.specialityAspect"/>：
	     		</td>
	     		<td class="brightStyle">
           			<select id="specialityAspect" name="${std_pre}student.firstAspect.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
         	  			<option value="${RequestParameters[std_pre + "student.firstAspect.id"]?if_exists}">...</option>
           			</select>
         		</td>
         		<#assign enrollYearId = "${std_pre}student.enrollYear"/>
         		<#assign departmentId = "${std_pre}student.department.id"/>
         		<#assign specialityId = "${std_pre}student.firstMajor.id"/>
         		<#assign specialityAspectId = "${std_pre}student.firstAspect.id"/>
         		<#assign adminClassId = "adminClasssId"/>
	   			<#assign adminClassDescriptions = "adminClassDescriptions"/>
	   			<#assign selectorId = 1 />
	   			<#include "/pages/selector/adminClassSelectorBarWithSpeciality.ftl"/>
         	</tr>
         	</#if>
         	<#if isSecondSpecialityNeed?default(false)>
         	<tr>
				<td class="grayStyle" id="f_secondSpeciality">
					&nbsp;双专业：
				</td>
				<td class="brightStyle">
					<select id="secondMajor" name="${std_pre}student.secondMajor.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
						<option value="${RequestParameters[std_pre + "student.secondMajor.id"]?if_exists}">...</option>
					</select>
				</td>
				<td class="grayStyle" id="f_specialityAspect">
					&nbsp;双专业方向：
	     		</td>
	     		<td class="brightStyle">
           			<select id="secondAspect" name="${std_pre}student.secondAspect.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
         	  			<option value="${RequestParameters[std_pre + "student.secondAspect.id"]?if_exists}">...</option>
           			</select>
         		</td>
			</tr>
			<#include "/templates/secondSpeciality2Select.ftl"/>
			<tr>
				<#assign enrollYearId = std_pre + "student.enrollYear"/>
         		<#assign departmentId = ""/>
         		<#assign specialityId = std_pre + "student.secondMajor.id"/>
         		<#assign specialityAspectId = std_pre + "student.secondAspect.id"/>
         		<#assign adminClassId = "adminClasssId"/>
	   			<#assign adminClassDescriptions = "adminClassDescriptions"/>
	   			<#assign colspanId = 1 />
	   			<#assign is2ndSpeciality = true />
	   			<#assign selectorId = 2 />
	   			<#assign adminClassKeyMessage>双专业班级</#assign>
	   			<#include "/pages/selector/adminClassSelectorBarWithSpeciality.ftl"/>
	   			<td class="grayStyle" id="f_isSecondMajorStudy">
					&nbsp;双专业是否就读：
	     		</td>
	     		<td class="brightStyle">
           			<select id="isSecondMajorStudy" name="${std_pre}student.isSecondMajorStudy" style="width:100px;" OnMouseOver="changeOptionLength(this);">
         	  			<option value="">...</option>
         	  			<option value="1" <#if RequestParameters[std_pre + "student.isSecondMajorStudy"]?default("")=="1">selected</#if>>就读</option>
         	  			<option value="0" <#if RequestParameters[std_pre + "student.isSecondMajorStudy"]?default("")=="0">selected</#if>>不就读</option>
           			</select>
         		</td>
			</tr>
			<tr>
				<td class="grayStyle" id="f_isSecondMajorThesisNeed">
					&nbsp;双专业是否写论文：
	     		</td>
	     		<td class="brightStyle">
           			<select name="${std_pre}student.isSecondMajorThesisNeed" style="width:100px;" OnMouseOver="changeOptionLength(this);">
         	  			<option value="">...</option>
         	  			<option value="1" <#if RequestParameters[std_pre + "student.isSecondMajorThesisNeed"]?default("")=="1">selected</#if>>需要</option>
         	  			<option value="0" <#if RequestParameters[std_pre + "student.isSecondMajorThesisNeed"]?default("")=="0">selected</#if>>不需要</option>
           			</select>
         		</td>
         		<td class="grayStyle"></td>
	     		<td class="brightStyle"></td>
			</tr>
			</#if>
			<#if stateStatusShow?default(false)>
				<tr>
				 	<td class="grayStyle" id="f_state">
				  		&nbsp;学籍有效性：
				 	</td>
				 	<td colSpan="1" class="brightStyle">
				 		<select name="${std_pre}student.active" style="width:100px;" OnMouseOver="changeOptionLength(this);">
							<option value="">...</option>
							<option value="1"<#if RequestParameters[std_pre + "student.active"]?default("-1")=="1"> selected</#if>>有效</option>
							<option value="0"<#if RequestParameters[std_pre + "student.active"]?default("-1")=="0"> selected</#if>>无效</option>
						</select>
				 	</td>
				 	<td class="grayStyle" id="f_studentStatus">
				  		&nbsp;学籍状态：
				 	</td>
				 	<td colSpan="1" class="brightStyle">
				 		<select name="${std_pre}student.state.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
							<option value="">...</option>
							<#list result.studentStateList as studentState>
								<option value="${studentState.id}"<#if RequestParameters[std_pre + "student.state.id"]?default("0")==studentState.id?string> selected</#if>/><@i18nName studentState/></option>
							</#list>
						</select>
				 	</td>
				</tr>
			</#if>
			${showInput?if_exists}
	  		<tr>
	   			<td colspan="4" align="center" class="darkColumn">
				    <input type="hidden" id="pageNo" name="pageNo" value="1"/>
				    <input type="hidden" id="method" name="method" value="${method}"/>
				    <input type="hidden" id="moduleName" name="moduleName" value="${moduleName?if_exists}"/>
				    <input type="hidden" id="searchFalg" name="searchFalg" value="${searchFalg?default('search')}"/>
				    ${hiddenInput?if_exists}
				    <input type="button" onClick="search()" value="<@bean.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;
				    <input type="reset" onClick="document.resetForm.submit()" value="<@bean.message key="system.button.reset"/>" name="reset1" class="buttonStyle"/>
       			</td>
	  		</tr>
		</form>
	<#if isCalendarNeed?default(false)>
	<#include "/templates/calendarSpecialitySelect.ftl"/>
	<#elseif isFirstSpecialityNeed?default(true)>
	<#assign stdTypeNullable=true />
	<#include "/templates/stdTypeDepart3Select.ftl"/>
	</#if>
	<form name="resetForm" method="post" action="${formAction}" onsubmit="return false;">
	    <input type="hidden" name="method" value="${resetMethod?default(method)}"/>
	    <input type="hidden" name="moduleName" value="${moduleName?if_exists}"/>
	    <input type="hidden" name="pageNo" value="1"/>
	    ${resetFormHiddenInput?if_exists}
	</form>
	</table>
</div>
<script>
	function addAllParams(form){
		var params = getInputParams(form,null,false);
       	addInput(form,"params",params);
	}
	
	<#assign defaultFunctionSearch>
	function search(){
       	if (doValidate()) {
       		var form = document.${formName};
       		form.action = "${formAction}?orderBy=student.code asc";
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
		for (var i = 0; i < obj.options.length; i++) {
			if (obj.options[i].text == ""||obj.options[i].text=="...") {
				continue;
			}
			if (obj.options[i].text.length*13 > OptionLength) {
				OptionLength=obj.options[i].text.length*13;
			}
		}
		if (OptionLength > oldOptionLength) {
			obj.style.width = OptionLength;
		}
	}
</script>