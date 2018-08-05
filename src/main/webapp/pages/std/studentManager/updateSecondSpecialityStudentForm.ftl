<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
 <body>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">       
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="std.update"/></B>
    </td>
   </tr>   
   <tr>
    <td>         	
     <#list result.studentList?if_exists as student>
     <form name="commonForm" action="studentManager.do" method="post" action="" onsubmit="return false;">
     <table width="90%" align="center" class="listTable"> 
     	<tr class="darkColumn">
     		<td width="10%">&nbsp;<@msg.message key="attr.stdNo"/></td><td>&nbsp;${student.code}</td>
     		<td width="10%">&nbsp;<@msg.message key="attr.personName"/></td><td>&nbsp;<@i18nName student/></td>
     		<td width="10%">&nbsp;<@msg.message key="entity.studentType"/></td><td>&nbsp;<@i18nName student.type/></td>
     	</tr>
     	<tr>
     		<td class="grayStyle">&nbsp;一专业</td><td>&nbsp;<@i18nName student?if_exists.firstMajor?if_exists /></td>
     		<td class="grayStyle">&nbsp;一专业方向</td><td>&nbsp;<@i18nName student?if_exists.firstAspect?if_exists /></td>
     		<td class="grayStyle">&nbsp;双专业是否写论文</td><td>
 			<select name="student.isSecondMajorThesisNeed"  style="width:100px;" OnMouseOver="changeOptionLength(this);">
 	  			<option value="1" <#if student?if_exists.isSecondMajorThesisNeed?exists&&student.isSecondMajorThesisNeed>selected</#if>>需要</option>
 	  			<option value="0" <#if student?if_exists.isSecondMajorThesisNeed?exists&&!student.isSecondMajorThesisNeed>selected</#if>>不需要</option>
   			</select></td>
     	</tr>
     	<tr>
     		<td class="grayStyle">&nbsp;双专业</td><td class="brightStyle">
     		<select id="secondMajor" name="student.secondMajor.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
				<option value="${(student.secondMajor.id)?if_exists}">...</option>
			</select></td>
     		<td class="grayStyle">&nbsp;双专业方向</td><td class="brightStyle">
     		<select id="secondAspect" name="student.secondAspect.id" style="width:100px;" OnMouseOver="changeOptionLength(this);">
         		<option value="${(student.secondAspect.id)?if_exists}">...</option>
           	</select></td>
     		<td class="grayStyle">&nbsp;双专业是否就读</td><td class="brightStyle">
     		<select id="isSecondMajorStudy" name="student.isSecondMajorStudy"  style="width:100px;" OnMouseOver="changeOptionLength(this);">
     	  		<option value="1" <#if student.isSecondMajorStudy?exists&&student.isSecondMajorStudy>selected</#if>>是</option>
 	  			<option value="0" <#if student.isSecondMajorStudy?exists&&!student.isSecondMajorStudy>selected</#if>>否</option>
   			</select></td>
           	<#assign secondSpecialityNullable="false"/>
           	<#assign secondAspectNullable="false"/>
     		<#include "/templates/secondSpeciality2Select.ftl"/>
     	</tr>
     	<tr>
     		<#assign specialityId="student.secondMajor.id"/>
     		<#assign specialityAspectId="student.secondAspect.id"/>
     		<td class="grayStyle">&nbsp;双专业班级</td>
     		<td class="brightStyle" colspan="5"><table class="listTable" width="100%" hight="100%"><tr width="100%"><td>
     		<select name="adminClass" MULTIPLE size="3" style="width:350px" onDblClick="JavaScript:removeSelectedOption(this.form['adminClass'])" >
            <#list student.adminClasses?if_exists?sort_by("code") as adminClass>
            	<#if (adminClass.speciality.majorType.id)?default(1) == 2><option value="${adminClass['id']}"><@i18nName adminClass /></option></#if>
            </#list>
            </select>
            <input type="hidden" value="" name="adminClassIds"/>
            </td><td width="100%" align="left" valign="middle">
            &nbsp;<input OnClick="JavaScript:removeSelectedOption(this.form['adminClass'])" type="button" value="移出" class="buttonStyle"> 
            &nbsp;所在年级<input type="text" value="${student.enrollYear?if_exists}" name="studentEnrollYear" maxlength="7" size="7"/>
            <br><br>
            &nbsp;<input type="button" value="<@msg.message key="action.add"/>" name="adminClassButton" class="buttonStyle" onClick="loadAdminClasssSelector()"/>
            &nbsp;<input type="checkbox" value="Y" name="adminClassSameSpeciality" checked/>同专业
		    <script language="javascript" >	
			    function setAdminClassIdAndDescriptions1(id, description){
			        var adminClassSelect=document.all['adminClass'];
			        var adminClassOption=new Option(description, id);
			        if (!hasOption(adminClassSelect, adminClassOption)){adminClassSelect.options[adminClassSelect.length]=adminClassOption}
			    }
			    
			    function loadAdminClasssSelector(){
		            var url="adminClassSelector.do?method=withSpeciality"+"&selectorId=1&adminClass.speciality.majorType.id=1";
		            if(document.all['adminClassSameSpeciality'].checked==true){
		            if(document.all['studentEnrollYear'].value!=""){url+="&adminClass.enrollYear="+document.all['studentEnrollYear'].value}
		            <#if studentTypeId?exists>if(document.all['${studentTypeId}'].value!=""){url+="&adminClass.stdType.id="+document.all['${studentTypeId}'].value}</#if>
		            <#if departmentId?exists>if(document.all['${departmentId}'].value!=""){url+="&adminClass.department.id="+document.all['${departmentId}'].value}</#if>
		            <#if specialityId?exists>if(document.all['${specialityId}'].value!=""){url+="&adminClass.speciality.id="+document.all['${specialityId}'].value}</#if>
		            <#if specialityAspectId?exists>if(document.all['${specialityAspectId}'].value!=""){url+="&adminClass.aspect.id="+document.all['${specialityAspectId}'].value}</#if>
		            }
		            popupMiniCommonSelector(url);		        
		        }
		        
		        function resetAdminClasssSelector1(){
		        	
		        }
		    </script>
            <br>
            </td></tr></table></td>
     	</tr>
     	<tr class="darkColumn">
	     	<td colspan="6" align="center" >
	       		<input type="hidden" name="method" value="updateSecondSpecialityStudent"/>
	       		<input type="hidden" name="stdIds" maxlength="32" value="${student.id}"/>
	       		<input type="hidden" name="params" value="${(RequestParameters['params'])?default('')}"/>
	       		<input type="button" value="<@bean.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle"/>&nbsp;
	       		<input type="reset" value="<@bean.message key="system.button.reset"/>" name="reset1"  class="buttonStyle"/>
	     	</td>
	   	</tr>
	 </table>
	 </form>
     </#list>    	   
     
    </td>
   </tr>   
  </table>
  <script language="javascript" >
  	 function doAction(form){
  		form['adminClassIds'].value=getAllOptionValue(form.adminClass);	 	
  		form.submit();
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
 </body>
<#include "/templates/foot.ftl"/>