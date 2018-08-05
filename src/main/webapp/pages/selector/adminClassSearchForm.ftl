<div id="searchBar" style="position:relative; visibility: visible; display:block;"> 
<table width="${tableWidth?default('90%')}" align="center" class="formTable">
		<form name="${formName}" method="post" action="${formAction}" target="${targetValue?default('_self')}" onsubmit="return false;">
			<tr> 
       			<td colspan="4" align="center" class="darkColumn"><B>${tableTitle?default('学籍信息查询')}</B></td>
      		</tr>
			<tr>
				<td class="grayStyle" id="f_departmenty" width="20%">
					&nbsp;班级代码：
				</td>
				<td class="brightStyle" width="30%">
					<input type="text" name="adminClass.code" maxlength="32" size="15" value="${RequestParameters['adminClass.code']?if_exists}" style="width:100px"/>
				</td>
				<td class="grayStyle" id="f_studentType" width="20%">
					&nbsp;班级名称:
				</td>
				<td class="brightStyle" width="30%">
					<input type="text" name="adminClass.name" maxlength="20" size="15" value="${RequestParameters['adminClass.name']?if_exists}" style="width:100px"/>
				</td>
			</tr>
			<tr>
				<td class="grayStyle" id="f_belongToYear">
					&nbsp;<@bean.message key="filed.enrollYearAndSequence"/>：
				</td>
				<td class="brightStyle">
					<input type="text" name="adminClass.enrollYear" maxlength="7" size="6" value="${RequestParameters['adminClass.enrollYear']?if_exists}" style="width:100px"/>
				</td>
				<#if isCalendarNeed?default(false)>
				<td class="grayStyle" id="f_studentType">
					&nbsp;<@bean.message key="entity.studentType"/><font color='red'>*</font>：
				</td>
				<td class="brightStyle">
					<select id="stdType" name="calendar.studentType.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
						<option value="${RequestParameters['calendar.studentType.id']?if_exists}"></option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="grayStyle" id="f_year">
					&nbsp;<@bean.message key="attr.year2year"/><font color='red'>*</font>：
				</td>
				<td class="brightStyle">
					<select id="year" name="calendar.year"  style="width:100px;" style="width:100px">
						<option value="${RequestParameters['calendar.year']?if_exists}"></option>
					</select>
				</td>
				<td class="grayStyle" id="f_term">
	      			&nbsp;<@bean.message key="attr.term"/><font color='red'>*</font>：
	     		</td>
	     		<td class="brightStyle">
	         		<select id="term" name="calendar.term" style="width:100px">
	            		<option value="${RequestParameters['calendar.term']?if_exists}"></option>
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
       		<#assign stdTypeId = "calendar.studentType.id"/>
			<#elseif isFirstSpecialityNeed?default(true)>
			<#assign stdTypeId = "adminClass.stdType.id"/>
				<td class="grayStyle" id="f_studentType">
					&nbsp;<@bean.message key="entity.studentType"/>：
				</td>
				<td class="brightStyle">
					<select id="stdTypeOfSpeciality" name="adminClass.stdType.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
						<option value="${RequestParameters['adminClass.stdType.id']?if_exists}">...</option>
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
					<select id="stdType" name="adminClass.stdType.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
						<option value="${RequestParameters['adminClass.stdType.id']?if_exists}">...</option>
					</select>
				</td>
				</tr>
				<script>
					<#if result?if_exists.stdTypeList?exists>
				    <#assign stdTypeList=result.stdTypeList>
				    </#if>
				    var defaultValues=new Object();
				    var stdTypeArray = new Array();
				    <#list stdTypeList as stdType>
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
					<select id="department" name="adminClass.department.id" style="width:100px;" OnMouseOver="changeOptionLength(this);" >
						<option value="${RequestParameters['adminClass.department.id']?if_exists}">...</option>
					</select>
				</td>
				<td class="grayStyle" id="f_speciality">
					&nbsp;<@bean.message key="entity.speciality"/>：
				</td>
				<td class="brightStyle">
					<select id="speciality" name="adminClass.speciality.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
						<option value="${RequestParameters['adminClass.speciality.id']?if_exists}">...</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="grayStyle" id="f_specialityAspect">
					&nbsp;<@bean.message key="entity.specialityAspect"/>：
	     		</td>
	     		<td class="brightStyle">
           			<select id="specialityAspect" name="adminClass.aspect.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
         	  			<option value="${RequestParameters['adminClass.aspect.id']?if_exists}">...</option>
           			</select>
         		</td>
         		<#if showInputTD?exists>
         		${showInputTD}
         		<#else>
         		<td class="brightStyle" colSpan="2"/>
         		</#if>
         	</tr>
         	</#if>
         	
			${showInput?if_exists}
	  		<tr>
	   			<td colspan="4" align="center" class="darkColumn">
				    <input type="hidden" name="pageNo" value="1"/>
				    <input type="hidden" name="method" value="${method}"/>
				    <input type="hidden" name="moduleName" value="${moduleName?if_exists}"/>
				    <input type="hidden" name="searchFalg" value="${searchFalg?default('search')}"/>
				    ${hiddenInput?if_exists}
				    <input type="button" onClick="search()" value="<@bean.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;
				    <input type="reset" onClick="document.resetForm.submit()" value="<@bean.message key="system.button.reset"/>" name="reset1"  class="buttonStyle"/>
       			</td>
	  		</tr>
		</form>
	</table>
	<#if isCalendarNeed?default(false)>
	<#include "/templates/calendarSpecialitySelect.ftl"/>
	<#elseif isFirstSpecialityNeed?default(true)>
	<#assign stdTypeNullable=true/>
	<#include "/templates/stdTypeDepart3Select.ftl"/>
	</#if>
	<form name="resetForm" method="post" action="${formAction}"> 
	    <input type="hidden" name="method" value="${resetMethod?default(method)}"/>
	    <input type="hidden" name="moduleName" value="${moduleName?if_exists}"/>
	    <input type="hidden" name="pageNo" value="1"/>
	    ${resetFormHiddenInput?if_exists}
	</form>&nbsp;
</div>
<script>
	function addAllParams(form){
		var params = getInputParams(form,null);
       	addInput(form,"params",params);
	}
	<#assign defaultFunctionSearch>
	function search(){
       	if (doValidate()) {
       		var form=document.${formName};
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