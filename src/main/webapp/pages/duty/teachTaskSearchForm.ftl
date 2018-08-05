<div id="searchBar" style="position:relative; visibility: visible; display:block;">
<table width="${tableWidth?default('90%')}" align="center" class="listTable">
		<form name="${formName}" method="post" action="${formAction}" target="${targetValue?default('_self')}" onsubmit="return false;">
			<tr> 
       			<td colspan="4" align="center" class="darkColumn"><B>${tableTitle?default('学籍信息查询')}</B></td>
      		</tr>
			<tr>
				<td class="grayStyle" id="f_taskNo" onKeyDown="javascript:searchTaskQuery();">
					&nbsp;<@bean.message key="attr.taskNo"/>：
				</td>
				<td class="brightStyle">
					<input name="teachTask.seqNo" type="text" maxlength="12" size="15" value="${RequestParameters['teachTask.seqNo']?if_exists}" style="width:100px"/>
				</td>
				<td class="grayStyle" id="f_courseNo">
					&nbsp;<@bean.message key="attr.courseNo"/>:
				</td>
				<td class="brightStyle">
					<input name="teachTask.course.code" type="text" maxlength="12" size="15" value="${RequestParameters['teachTask.course.code']?if_exists}" style="width:100px"/>
				</td>
			</tr>
			<tr>
				<td class="grayStyle" id="f_courseName">
					&nbsp;<@bean.message key="attr.courseName"/>：
				</td>
				<td class="brightStyle">
					<input type="text" name="teachTask.course.name" maxlength="12" size="15" value="${RequestParameters['teachTask.course.name']?if_exists}" style="width:100px"/>
				</td>
				<td class="grayStyle" id="f_courseType">
					&nbsp;<@bean.message key="common.courseType"/>：
				</td>
				<td class="brightStyle">
					<select id="courseType" name="teachTask.courseType.id" value="${RequestParameters['teachTask.courseType.id']?if_exists}" style="width:100px;" OnMouseOver="changeOptionLength(this);">
		     			<option>...</option>
		     			<#list sort_byI18nName(result.courseTypeList) as courseType>
		     			<option value=${courseType.id}><@i18nName courseType/></option>
		     			</#list>
		     		</select>
				</td>
			</tr>
			<tr>
				<td class="grayStyle" width="15%" id="f_teach4Depart">
					&nbsp;<@bean.message key="attr.teach4Depart"/>：
				</td>
				<td class="brightStyle">
					<select id="studyDep" name="teachTask.teachClass.depart.id" value="${RequestParameters['teachTask.teachClass.depart.id']?if_exists}" style="width:100px;" OnMouseOver="changeOptionLength(this);">
		     			<option>...</option>
		     			<#list sort_byI18nName(result.departmentList) as depart>
                		<option value=${depart.id}><@i18nName depart/></option>
		     			</#list>
		     		</select>
				</td>
				<td class="grayStyle" width="15%" id="f_teachDepart">
	      			&nbsp;<@bean.message key="attr.teachDepart"/>：
	     		</td>
	     		<td class="brightStyle">
	         		<select id="teachDep" name="teachTask.arrangeInfo.teachDepart.id" value="${RequestParameters['teachTask.arrangeInfo.teachDepart.id']?if_exists}" style="width:100px;" OnMouseOver="changeOptionLength(this);">
			     		<option>...</option>
			     		<#list sort_byI18nName(teachDepartList) as teachDepart>
			     		<option value=${teachDepart.id}><@i18nName teachDepart/></option>
			     		</#list>
		     		</select>
         		</td>
			</tr>
			<tr>
				<td class="grayStyle">&nbsp;<@bean.message key="entity.teacher"/>:</td>
				<td class="brightStyle">
					<select id="teacher" name="teacher.name" value="${RequestParameters['teacher.name']?if_exists}" style="width:100px;" OnMouseOver="changeOptionLength(this);">
						<option value="">...</option>
						<#list sort_byI18nName(result.teacherList?if_exists) as teacher>
						<option value="${teacher.name}">${teacher.name}</option>
						</#list>
					</select>
				</td>
				<td class="grayStyle">&nbsp;<@bean.message key="attr.enrollTurn"/>:</td>
				<td class="brightStyle"><input type="text" name="task.teachClass.enrollTurn" value="" style="width:100px"></td>
	    	</tr>
			<tr>
				<td class="grayStyle" id="f_studentType">
					&nbsp;<@bean.message key="entity.studentType"/><font color='red'>*</font>：
				</td>
				<td class="brightStyle">
					<select id="stdType" name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']?if_exists}" style="width:100px;" OnMouseOver="changeOptionLength(this);">
						<option value="${RequestParameters['calendar.studentType.id']?if_exists}"></option>
					</select>
				</td>
			     </td>
			     <td class="grayStyle"  >&nbsp;<@bean.message key="attr.GP"/>:</td>
				 <td>
				 <#assign isGuaPai = RequestParameters['task.requirement.isGuaPai']?default("")/>
			        <select name="task.requirement.isGuaPai" style="width:100px">
			           <option value="" <#if isGuaPai=="">selected</#if>>...</option>
			           <option value="1" <#if isGuaPai=="1">selected</#if>><@bean.message key="common.yes"/></option>
			           <option value="0" <#if isGuaPai=="0">selected</#if>><@bean.message key="common.no"/></option>
			        </select>
				</td>
			</tr>
			<tr>
				<td class="grayStyle" width="15%" id="f_year">
					&nbsp;<@bean.message key="attr.year2year"/><font color='red'>*</font>：
				</td>
				<td class="brightStyle">
					<select id="year" name="calendar.year" value="${RequestParameters['calendar.year']?if_exists}" style="width:100px;">
						<option value="${RequestParameters['calendar.year']?if_exists}"></option>
					</select>
				</td>
				<td class="grayStyle" width="15%" id="f_term">
	      			&nbsp;<@bean.message key="attr.term"/><font color='red'>*</font>：
	     		</td>
	     		<td class="brightStyle">
	         		<select id="term" name="calendar.term" value="${RequestParameters['calendar.term']?if_exists}" style="width:100px">
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
	<#assign courseTypeNullable=true/>
	<#assign studyDepNullable=true/>
	<#assign teachDepNullable=true/>
	<#assign teacherNullable=true/>
	<#include "/templates/dutyTeachTaskSelect.ftl"/>
	<form name="resetForm" method="post" action="${formAction}">
	    <input type="hidden" name="method" value="${method}"/>
	    <input type="hidden" name="moduleName" value="${moduleName?if_exists}"/>
	    <input type="hidden" name="pageNo" value="1"/>
	    ${resetFormHiddenInput?if_exists}
	</form>&nbsp;
</div>
<script>
	function addAllParams(form){
		var params = getInputParams(form,null,false);
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
			if(obj.options[i].text==""||obj.options[i].text=="...") {
				continue;
			}
			if (obj.options[i].text.length * 13 > OptionLength) {
				OptionLength = obj.options[i].text.length * 13;
			}
			if (OptionLength > 250) {
				OptionLength = 250;
			}
		}
		if (OptionLength > oldOptionLength) {
			obj.style.width = OptionLength;
		}
	}
	
	function searchTaskQuery() {
		if (window.event.keyCode == 13) {
			search();
		}
	}
</script>