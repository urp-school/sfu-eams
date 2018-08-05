<script>
<#assign stdIndex=student?if_exists.studentStatusInfo?if_exists.experienceSet?size+1>
	var index=${stdIndex};
	function addTr(){
		if(index>1){
			var textarea = document.getElementById("experience"+(index-1));
			if(/^\s*$/.test(textarea.value)){
				alert("请先填写上一条信息");
				return;
			}
		}
		var templetTr = document.getElementById("tr"+index);
		var newTr = beforeTr.cloneNode(true);
		var textarea = document.getElementById("experience"+index);
		textarea.onfocus="";
		index++;
		newTr.id="tr"+index;
		td.colspan=8;
		var newTextarea = newTr.childNodes[0].childNodes[0];
		newTextarea.id="experience"+index;
		newTextarea.name="experience"+index;
		var endTr = document.getElementById("trEnd");
		var tableId = document.getElementById("tableId");
		tableId.insertBefore(newTr,endTr);
	}
</script>
<table width="100%" border="0" id="tableId">
  <tr> 
    <td width="6%"><div align="center"><@msg.message key="entity.speciality"/></div></td>
    <td colspan="3"><div align="center">${student?if_exists.firstMajor?if_exists.name?if_exists}</div></td>
    <td width="10%"><div align="center">研究方向</div></td>
    <td colspan="3"><div align="center">${student?if_exists.firstAspect?if_exists.name?if_exists}</div></td>
    <td colspan="2" rowspan="4">照片</td>
  </tr>
  <tr> 
    <td><div align="center"><@msg.message key="attr.personName"/></div></td>
    <td colspan="3"><div align="center">${student?if_exists.name?if_exists}</div></td>
    <td><div align="center">出生日期</div></td>
    <td colspan="3"><div align="center"><#if student?if_exists.basicInfo?if_exists.birthday?exists>${student.basicInfo.birthday?string("yyyy-MM-dd")}</#if></div></td>
  </tr>
  <tr> 
    <td><div align="center">籍贯</div></td>
    <td colspan="3"><input type="text" name="basicInfo.ancestralAddress" maxlength="50" value="${student?if_exists.basicInfo?if_exists.ancestralAddress?if_exists}"></td>
    <td><div align="center">所在年级</div></td>
    <td colspan="3"><div align="center"><#if student?if_exists.studentStatusInfo?if_exists.enrollDate?exists>${student.studentStatusInfo.enrollDate?string("yyyy-MM-dd")}</#if></div></td>
  </tr>
  <tr> 
    <td><div align="center">民族</div></td>
    <td width="6%"><div align="center">${student?if_exists.basicInfo?if_exists.nation?if_exists.name?if_exists}</div></td>
    <td width="10%"><div align="center">政治面貌</div></td>
    <td width="15%"><div align="center">${student?if_exists.basicInfo?if_exists.politicVisage?if_exists.name?if_exists}</div></td>
    <td><div align="center"><@msg.message key="entity.gender"/></div></td>
    <td colspan="3"><div align="center">${student?if_exists.basicInfo?if_exists.gender?if_exists.name?if_exists}</div></td>
  </tr>
  <tr> 
    <td colspan="3"><div align="center">入学前最后学历</div></td>
    <td colspan="7"><input type="text" name="studentStatusInfo.educationBeforEnrollSpeciality" value="${student?if_exists.studentStatusInfo?if_exists.educationBeforEnrollSpeciality?if_exists}"></td>
  </tr>
  <tr> 
    <td colspan="3"><div align="center">入学前最后学位</div></td>
    <td colspan="2"><div align="center"><input type="text" name="studentStatusInfo.degreeBeforEnroll" maxlength="20" value="${student?if_exists.studentStatusInfo?if_exists.degreeBeforEnroll?if_exists}"></td>
    <td colspan="2"><div align="center">授予学位时间单位</div></td>
    <td colspan="3"><div align="center">时间:<input type="text" name="studentStatusInfo.degreeBeforEnrollDate" maxlength="10" value="<#if student?if_exists.studentStatusInfo?if_exists.degreeBeforEnrollDate?exists>${student.studentStatusInfo.degreeBeforEnrollDate?string("yyyy-MM-dd")}</#if>" style="width:100%"/>单位:<input type="text" name="studentStatusInfo.degreeBeforEnrollOrganization" value="${student?if_exists.studentStatusInfo?if_exists.degreeBeforEnrollOrganization?if_exists}" style="width:100%" maxlength="30"/></td>
  </tr>
  <tr> 
    <td colspan="3"><div align="center">工作单位地址</div></td>
    <td colspan="3"><input type="text" name="basicInfo.workAddress" maxlength="200" value="${student?if_exists.basicInfo?if_exists.workAddress?if_exists}" style="width:100%"></td>
    <td><div align="center">电话</div></td>
    <td><input type="text" name="basicInfo.workPhone" value="${student?if_exists.basicInfo?if_exists.workPhone?if_exists}" style="width:100%"></td>
    <td width="9%"><div align="center">邮编</div></td>
    <td width="13%"><input type="text" name="basicInfo.workPlacePostCode" maxlength="6" value="${student?if_exists.basicInfo?if_exists.workPlacePostCode?if_exists}" style="width:100%"></td>
  </tr>
  <tr>
    <td colspan="3"><div align="center">家庭通讯地址</div></td>
    <td colspan="3"><input type="text" name="basicInfo.homeAddress" maxlength="100" value="${student?if_exists.basicInfo?if_exists.homeAddress?if_exists}" style="width:100%"></td>
    <td> <div align="center">电话</div></td>
    <td><input type="text" name="basicInfo.phone" maxlength="20" value="${student?if_exists.basicInfo?if_exists.phone?if_exists}" style="width:100%"></td>
    <td><div align="center">邮编</div></td>
    <td><input type="text" name="basicInfo.postCode" maxlength="6" value="${student?if_exists.basicInfo?if_exists.postCode?if_exists}" style="width:100%"></td>
  </tr>
  <tr id="tr0">
    <td colspan="2" rowspan="2"><div align="center">学历和社会经历从中学起</div></td>
  </tr>
  <#list student?if_exists.studentStatusInfo?if_exists.experienceSet?if_exists  as experience>
  	<tr id="tr${experience_index+1}">
  		<td colspan="8"><input type="hidden" name="experience${experience_index+1}.id" value="${experience.id?if_exists}"><textarea name="experience${experience_index+1}.experienceDescription" rows="2" style="width:100%">${experience.experienceDescription?if_exists}</textarea></td>
  	</tr>
  </#list>
  <tr id="tr${stdIndex}">
  		<td colspan="8"><textarea id="experience${stdIndex}" name="experience${stdIndex}" rows="2" style="width:100%" onfoucs="addTr()"></textarea></td>
  </tr>
  <tr id="trEnd">
    <td colspan="2"><div align="center">何时何地受过何种奖励和处分<br>
      </div></td>
    <td colspan="8"><textarea name="studentStatusInfo.rewardsAndPunishment" rows="2" style="width:100%">${student?if_exists.studentStatusInfo?if_exists.rewardsAndPunishment?if_exists}</textarea></td>
  </tr>
</table>