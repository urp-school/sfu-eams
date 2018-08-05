       <#if result.student?exists&&result.student.basicInfo?exists>  
          <#if result.student.basicInfo.country?exists>
             <#assign countryId=result.student.basicInfo.country.id />
          </#if>
          
          <#if result.student.basicInfo.nation?exists>
             <#assign nationId=result.student.basicInfo.nation.id />
          </#if> 
          
          <#assign idCard=result.student.basicInfo.idCard?if_exists />
          
          <#if result.student.basicInfo.gender?exists>
             <#assign genderId=result.student.basicInfo.gender.id />
          </#if>
          <#if result.student.basicInfo.birthday?exists>
          <#assign birthday=result.student.basicInfo.birthday?string("yyyy-MM-dd") />          
          </#if>
          <#assign homeAddress=result.student.basicInfo.homeAddress?if_exists />
          <#assign postCode=result.student.basicInfo.postCode?if_exists />
          <#assign phone=result.student.basicInfo.phone?if_exists />
          <#assign ancestralAddress=result.student.basicInfo.ancestralAddress?if_exists />
          <#assign mail=result.student.basicInfo.mail?if_exists />
          
          <#if result.student.basicInfo.politicVisage?exists>
             <#assign politicVisageId=result.student.basicInfo.politicVisage.id />
          </#if>
       </#if>
       <input type="hidden" name="basicInfo.id" value="${(result.student.basicInfo.id)?default('')}">
	   <tr class="darkColumn">
	     <td align="center" colspan="2"><@bean.message key="info.studentBasicInfo"/></td>
	   </tr>
	   	<#assign userRank = user.defaultCategory.id/>
 		<#assign studentRank = userRank == 1 />
 		<#assign teacherRank = userRank == 2 />
	   	<#assign managerRank = userRank == 3 />
	   <#if (result.student.id)?exists>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_photoName">
	      &nbsp;照片：
	     </td>
	     <#assign spurl = "org.beangle.freemarker.StudentPhotoUrlMethod"?new()/>
	     <script language="javascript">
	     	function refreshPhoto(){
	     		var args = refreshPhoto.arguments;
				if(args[0]){
					setTimeout('doRefresh(\''+args[0]+'\')',1000);
				}else{
					setTimeout('doRefresh()',1000);
				}
	     		
	     	}
	     	function doRefresh(){
	     		var args = doRefresh.arguments;
	     		if(args[0]){
	     			$('studentPhoto').src=args[0];
	     		}else{
	     			$('studentPhoto').src='${spurl(result.student.code)}';
	     		}	     		
	     	}
	     </script>
	     
	     <td class="brightStyle">
	      <img id="studentPhoto" src="${spurl(result.student.code)}" width="100" height="100" style="vertical-align:top;margin-top:1px;border:0px" >
	      <#--
	      <input type="button" name="photoButton" value="更新" class="buttonStyle" style="vertical-align:middle;" onclick="popupCommonWindow('<#if managerRank>studentPhoto.do?method=uploadForm&student.id=${result.student.id?default('')}<#elseif studentRank>photoByStudent.do?method=uploadForm</#if>','studentFileImportWin', 450, 250);"/>
	      -->
	     </td>
	   </tr>
	   </#if>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_country">
	      &nbsp;<@bean.message key="entity.country"/>：
	     </td>
	     <td class="brightStyle">
	      <select name="basicInfo.country.id" style="width:100px;">
	       <option></option>
	       <#list result.countryList?if_exists?sort_by("name") as country>
	       <option value="${country.id}"><@i18nName country?if_exists/></option>
	       </#list>
	      </select>
	     </td>
	     <script language="javascript">
	     	var form = document.commonForm;
	       choiceTargetSelectorOption("${countryId?if_exists}", "basicInfo.country.id");
	       
			var OptionLength=0;
			var obj=form["basicInfo.country.id"];
			for(var i=0;i<obj.options.length;i++){
				if(obj.options[i].text.length>OptionLength){
					OptionLength=obj.options[i].text.length;
				}
			}
			obj.style.width=OptionLength*6;

	     </script>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_nation">
	      &nbsp;<@bean.message key="entity.nation"/>：
	     </td>
	     <td class="brightStyle">
	      <select name="basicInfo.nation.id" style="width:100px;">
	       <option></option>
	       <#list result.nationList?if_exists?sort_by("name") as nation>
	       <option value="${nation.id}"><@i18nName nation?if_exists/></option>
	       </#list>
	      </select>
	     </td>
	     <script language="javascript">
	       choiceTargetSelectorOption("${nationId?if_exists}", "basicInfo.nation.id");
	     </script>
	   </tr>	
	   <tr>
	     <td class="grayStyle" width="25%" id="f_idCard">
	      &nbsp;<@bean.message key="std.idCard"/>：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="basicInfo.idCard" maxlength="25" value="${idCard?if_exists}"/>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_gender">
	      &nbsp;<@bean.message key="attr.gender"/><font color="red">*</font>：
	     </td>
	     <td class="brightStyle">
	      <select name="basicInfo.gender.id" style="width:100px;">
	       <option></option>
	       <#list result.genderList?if_exists as gender>
	       <option value="${gender.id}"><@i18nName gender?if_exists/></option>
	       </#list>
	      </select>
	     </td>
	     <script language="javascript">
	       choiceTargetSelectorOption("${genderId?if_exists}", "basicInfo.gender.id");
	     </script>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_maritalStatus">
	      &nbsp;婚姻状况：
	     </td>
	     <td class="brightStyle">
	      <select name="basicInfo.maritalStatus.id" style="width:100px;">
	       <option></option>
	       <#list result.maritalStatusList?if_exists?sort_by("name") as maritalStatus>
	       <option value="${maritalStatus.id}" <#if (result.student.basicInfo.maritalStatus.id)?default(-1)==maritalStatus.id>selected</#if>><@i18nName maritalStatus?if_exists/></option>
	       </#list>
	      </select>
	     </td>	     
	   </tr>	
	   <tr>
	     <td class="grayStyle" width="25%" id="f_birthday">
	      &nbsp;<@bean.message key="attr.birthday"/>：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="basicInfo.birthday" maxlength="10" size="10" value="${birthday?if_exists}"/>
	      <input type="button" value="日期" class="buttonStyle"  style="width:40px" onclick="calendar(this.form['basicInfo.birthday'])">
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_homeAddress">
	      &nbsp;<@bean.message key="attr.familyAddress"/>：
	     </td>
	     <td class="brightStyle">
	      <textarea name="basicInfo.homeAddress" cols="40">${homeAddress?if_exists}</textarea>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_postCode">
	      &nbsp;<@bean.message key="attr.postCode"/>：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="basicInfo.postCode" maxlength="6" value="${postCode?if_exists}"/>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_phone">
	      &nbsp;<@bean.message key="attr.phoneOfHome"/>：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="basicInfo.phone" maxlength="20" value="${phone?if_exists}"/>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_phone">
	      &nbsp;移动电话：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="basicInfo.mobile" maxlength="13" value="${(result.student.basicInfo.mobile)?if_exists}"/>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_ancestralAddress">
	      &nbsp;<@bean.message key="attr.ancestralAddress"/>：
	     </td>
	     <td class="brightStyle">
	      <textarea name="basicInfo.ancestralAddress" cols="40">${ancestralAddress?if_exists}</textarea>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_parentName">
	      &nbsp;家庭成员：
	     </td>
	     <td class="brightStyle">
	      <textarea name="basicInfo.parentName" cols="40">${(result.student.basicInfo.parentName)?if_exists}</textarea>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_workAddress">
	      &nbsp;工作地址：
	     </td>
	     <td class="brightStyle">
	      <textarea name="basicInfo.workAddress" cols="40">${(result.student.basicInfo.workAddress)?if_exists}</textarea>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_workPlace">
	      &nbsp;工作单位：
	     </td>
	     <td class="brightStyle">
	      <textarea name="basicInfo.workPlace" cols="40" rows="2">${(result.student.basicInfo.workPlace)?if_exists}</textarea>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_workPlacePostCode">
	      &nbsp;工作地址<@bean.message key="attr.postCode"/>：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="basicInfo.workPlacePostCode" maxlength="16" value="${(result.student.basicInfo.workPlacePostCode)?if_exists}" />
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_workPhone">
	      &nbsp;工作单位电话：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="basicInfo.workPhone" maxlength="25" value="${(result.student.basicInfo.workPhone)?if_exists}"/>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_mail">
	      &nbsp;<@bean.message key="attr.email"/>：
	     </td>
	     <td class="brightStyle">
	      <input type="text" name="basicInfo.mail" maxlength="25" value="${mail?if_exists}"/>
	     </td>
	   </tr>
       <tr>
	     <td class="grayStyle" width="25%" id="f_politicVisage">
	      &nbsp;<@bean.message key="entity.politicVisage"/>：
	     </td>
	     <td class="brightStyle">
	      <select name="basicInfo.politicVisage.id" style="width:150px;">
	       <option></option>
	       <#list result.politicVisageList?if_exists as politicVisage>
	       <option value="${politicVisage.id}"><@i18nName politicVisage?if_exists/></option>
	       </#list>
	      </select>
	     </td>
	     <script language="javascript">
	       choiceTargetSelectorOption("${politicVisageId?if_exists}", "basicInfo.politicVisage.id");
	     </script>
	   </tr>