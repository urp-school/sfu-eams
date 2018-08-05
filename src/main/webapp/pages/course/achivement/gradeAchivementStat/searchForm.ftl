<#assign formElementWidth = "width:100px"/>
<table width="100%" class="searchTable">
	<tr class="infoTitle" valign="top" style="font-size:8pt">
		<td colspan="2" style="text-align:left;text-valign:bottom;font-weight:bold"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;学籍信息查询</td>
    </tr>
    <tr>
      	<td style="font-size:0pt" colspan="2"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/></td>
    </tr>
    <tr>
		<td class="infoTitle">学号:</td>
		<td>
		<input type="text" name="gradeAchivement.std.code"  style="width:100px;"/>
	 	</td>
	</tr>
	<tr>
		<td class="infoTitle">姓名:</td>
		<td>
		<input type="text" name="gradeAchivement.std.name"  style="width:100px;"/>
	 	</td>
	</tr>
    <tr>
		<td class="infoTitle">年级:</td>
		<td>
		<input type="text" name="gradeAchivement.grade"  style="width:100px;"/>
	 	</td>
	</tr>
	<tr>
		<td id="f_studentType"><@bean.message key="entity.studentType"/></td>
		<td>
			<select id="stdTypeOfSpeciality" name="gradeAchivement.std.type.id" style="${formElementWidth}">
				<option value="${RequestParameters['gradeAchivement.std.type.id']?if_exists}">...</option>
			</select> 
		</td>
	</tr>
	<tr>
		<td id="f_departmenty"><@bean.message key="common.college"/></td>
		<td>
			<select id="department" name="gradeAchivement.department.id" style="${formElementWidth}">
				<option value="${RequestParameters['gradeAchivement.department.id']?if_exists}">...</option>
			</select>
		</td>
	</tr>
	<tr>
		<td id="f_speciality"><@bean.message key="entity.speciality"/></td>
		<td>
			<select id="speciality" name="gradeAchivement.major.id" style="${formElementWidth}">
				<option value="${RequestParameters['student.firstMajor.id']?if_exists}">...</option>
			</select>
		</td>
	</tr>
	<tr style="display:none">
		<td id="f_specialityAspect"><@bean.message key="entity.specialityAspect"/></td>
	    <td>
        	<select id="specialityAspect" name="gradeAchivement.aspect.id" style="${formElementWidth}">
         		<option value="${RequestParameters['gradeAchivement.aspect.id']?if_exists}">...</option>
           	</select>
        </td>
	</tr>
	<tr>
		<td >班级</td>
	    <td>
        	<input type="text" name="gradeAchivement.adminClass.name"  style="width:100px;"/>
        </td>
	</tr>
    <tr>
	     <td class="infoTitle">德育分数:</td>
	     <td>
	     	<input name="moralScore1" type="text" value="${RequestParameters["score1"]?if_exists}" style="width:40px" maxlength="32"/>
	     	-
	     	<input name="moralScore2" type="text" value="${RequestParameters["score2"]?if_exists}" style="width:40px" maxlength="32"/>
	     </td>
	</tr>
	<tr> 
	     <td class="infoTitle">智育分数:</td>
	     <td>
	     	<input name="ieScore1" type="text" value="${RequestParameters["ieScore1"]?if_exists}" style="width:40px" maxlength="32"/>
	     	-
	     	<input name="ieScore2" type="text" value="${RequestParameters["ieScore2"]?if_exists}" style="width:40px" maxlength="32"/>
	     </td>
	</tr>
	<tr> 
	     <td class="infoTitle">体育分数:</td>
	     <td>
	     	<input name="peScore1" type="text" value="${RequestParameters["peScore1"]?if_exists}" style="width:40px" maxlength="32"/>
	     	-
	     	<input name="peScore2" type="text" value="${RequestParameters["peScore2"]?if_exists}" style="width:40px" maxlength="32"/>
	     </td>
	</tr>
	<tr> 
	     <td class="infoTitle">综合分数:</td>
	     <td>
	     	<input name="score1" type="text" value="${RequestParameters["score1"]?if_exists}" style="width:40px" maxlength="32"/>
	     	-
	     	<input name="score2" type="text" value="${RequestParameters["score2"]?if_exists}" style="width:40px" maxlength="32"/>
	     </td>
	</tr>
	<tr>
		<td class="infoTitle">全部及格:</td>
		<td>
		<select id="gradePassed" name="gradePassed"  style="width:100px;">
           <option value="">...</option>
           <option value="1">是</option>
           <option value="0">否</option>
      	</select>
	 	</td>
	</tr>
	<tr>
		<td class="infoTitle">英语四级:</td>
		<td>
		<select id="cetPassed" name="gradeAchivement.cet4Passed"  style="width:100px;">
           <option value="">...</option>
           <option value="1">通过</option>
           <option value="0">不通过</option>
      	</select>
	 	</td>
	</tr>
	<tr align="center" height="50px">
		<td colspan="2">
		    <button onClick="search()" accesskey="Q" class="buttonStyle" style="width:60px">
		       <@bean.message key="action.query"/>(<U>Q</U>)
		 	</button>
	    </td>
	</tr>
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