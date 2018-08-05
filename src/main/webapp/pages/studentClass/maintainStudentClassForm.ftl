<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body>
	<table id="bar"></table> 
  <table cellpadding="0" cellspacing="0" width="100%" border="0">       
   <tr>
    <td>         	
     <#list result.studentList?if_exists as student>
     <form name="commonForm" action="studentClassOperation.do" method="post" onsubmit="return false;">
     <table width="90%" align="center" class="listTable"> 
     	<tr class="darkColumn">
     		<td width="10%">&nbsp;<@msg.message key="attr.stdNo"/></td><td>&nbsp;${student.code}</td>
     		<td width="10%">&nbsp;<@msg.message key="attr.personName"/></td><td>&nbsp;<@i18nName student/></td>
     		<td width="10%">&nbsp;<@msg.message key="entity.studentType"/></td><td>&nbsp;<@i18nName student.type/></td>
     	</tr>     	
     	<tr>
     		<td class="grayStyle">&nbsp;班级</td>
     		<td class="brightStyle" colspan="5"><table class="listTable" width="100%" hight="100%"><tr width="100%"><td>
     		<select name="adminClass" MULTIPLE size="3" style="width:350px" onDblClick="JavaScript:removeSelectedOption(this.form['adminClass'])" >
            <#list student.adminClasses?if_exists?sort_by("code") as adminClass>
            	<option value="${adminClass['id']}"><@i18nName adminClass /></option>
            </#list>
            </select>
            <input type="hidden" value="" name="adminClassIds"/>
            </td><td width="100%" align="left" valign="middle">
            &nbsp;<input OnClick="JavaScript:removeSelectedOption(this.form['adminClass'])" type="button" value="移出" class="buttonStyle"> 
            &nbsp;所在年级<input type="text" value="${student.enrollYear?if_exists}" name="studentEnrollYear" maxlength="7" size="7"/>
            <br><br>
            &nbsp;<input type="button" value="<@msg.message key="action.add"/>" name="adminClassButton" class="buttonStyle" onClick="loadAdminClasssSelector()" />
            &nbsp;<input type="checkbox" value="Y" name="adminClassSameSpeciality" checked/>同专业
		    <script language="javascript" >	
		    	var bar = new ToolBar("bar", "班级信息维护", null, true, true);
		    	bar.addBack();
		    	
			    function setAdminClassIdAndDescriptions1(id, description){
			        var adminClassSelect=document.all['adminClass'];
			        var adminClassOption=new Option(description, id);
			        if (!hasOption(adminClassSelect, adminClassOption)){adminClassSelect.options[adminClassSelect.length]=adminClassOption}
			    }
			    
			    function loadAdminClasssSelector(){
		            var url="adminClassSelector.do?method=withSpeciality"+"&selectorId=1";
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
		        	;
		        }
		    </script>
            <br>
            </td></tr></table></td>
     	</tr>
     	<tr class="darkColumn">
	     	<td colspan="6" align="center" >
	       		<input type="hidden" name="method" value="maintainStudentClass" />
	       		<input type="hidden" name="stdId" maxlength="32" value="${student.id}" />
	       		<input type="hidden" name="params" value="${(RequestParameters['params'])?default('')}" />
	       		<input type="button" value="<@bean.message key="system.button.submit" />" name="button1" onClick="doAction(this.form)" class="buttonStyle" />&nbsp;
	       		<input type="reset" value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
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