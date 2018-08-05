<#include "/templates/head.ftl"/>
<table id="myBar"></table>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script type='text/javascript' src='dwr/interface/studentManager.js'></script>
 <body>      	
     <table width="90%" align="center" class="formTable"> 
     <form name="commonForm" action="speciality2ndStd.do?method=save" method="post" onsubmit="return false;">
     <@searchParams/>
	    <tr>
	     <td align="center" colspan="4" class="darkColumn">
	      <B>辅修专业学生信息</B>
	     </td>
	    </tr>   
     	<tr>
     		<td id="f_stdCode" class="title"><font color="red">*</font><@msg.message key="attr.stdNo"/></td>
     		<td>
     		<input type="hidden" name="student.id" value="${(student.id)?if_exists}"/>
     		<#if (student.id)?exists>
            ${student.code}
            <#else>
     		<input type="text" value="${(student.code)?if_exists}" name="student.code" maxlength="32" size="12" onKeyDown="javascript:searchByStdCode();"/>
     		<input type="button" name="enterstdId" value="确定" class="buttonStyle" onclick="getByStdCode();"/>
            </#if>
     		</td>
     		<td class="title"><@msg.message key="attr.personName"/></td><td id="student.name"><@i18nName student?if_exists/></td>
     	</tr>
        <tr>
          <td class="title"><@msg.message key="entity.studentType"/></td><td id="student.type.name"><@i18nName (student.type)?if_exists /></td>
          <td class="title"><@msg.message key="entity.department"/></td><td id="student.department.name"><@i18nName (student.department)?if_exists /></td>
        </tr>
     	<tr>
     		<td class="title">一专业</td><td id="student.firstMajor.name"><@i18nName (student.firstMajor)?if_exists /></td>
     		<td class="title">一专业方向</td><td id="student.firstAspect.name"><@i18nName (student.firstAspect)?if_exists /></td>
     	</tr>
     	<tr>
     		<td class="title" id="f_secondMajor"><font color="red">*</font>辅修专业</td>
     		<td>
     			<select id="secondMajor" name="student.secondMajor.id" style="width:150px;">
				 	<option value="${(student.secondMajor.id)?if_exists}">...</option>
				</select>
			</td>
     		<td class="title">辅修专业方向</td>
     		<td>
 				<select id="secondAspect" name="student.secondAspect.id" style="width:150px;">
     	  			<option value="${(student.secondAspect.id)?if_exists}">...</option>
       			</select>
           	</td>
     		<#include "/templates/secondSpeciality2Select.ftl"/>
     	</tr>
        <tr>
     		<td class="title">是否写论文</td>
     		<td><@htm.radio2 name="student.isSecondMajorThesisNeed" value=(student.isSecondMajorThesisNeed)?default(false) /></td>
     		<td class="title">是否就读</td>
     		<td><@htm.radio2 name="student.isSecondMajorStudy" value=(student.isSecondMajorStudy)?default(true) /></td>
        </tr>
     	<tr>
     		<td class="title">辅修专业班级</td>
     		<td colspan="5" style="padding:0px">
     		
     		 <table class="listTable" width="100%" hight="100%">
     		  <tr>
     		   <td>
	     		<select name="adminClass" MULTIPLE size="3" style="width:200px" onDblClick="JavaScript:removeSelectedOption(this.form['adminClass'])" >
	            <#list (student.adminClasses)?if_exists?sort_by("code") as adminClass>
	            	<#if (adminClass.speciality.majorType.id)?default(1) == 2><option value="${adminClass['id']}"><@i18nName adminClass /></option></#if>
	            </#list>
	            </select>
                <input type="hidden" value="" name="adminClassIds"/>
              </td>
              	<td align="left" valign="middle">
               		<button onclick="JavaScript:removeSelectedOption(this.form['adminClass'])">移出</button> <br><br>
               		<input type="checkbox" value="Y" name="adminClassSameSpeciality" checked/>同专业&nbsp;<button onclick="loadAdminClasssSelector()"><@msg.message key="action.add"/></button>
               	</td>
               	<td id="f_studentEnrollYear" class="title"><@msg.message key="attr.enrollTurn"/></td>
               	<td><input type="text" value="${(student.enrollYear)?if_exists}" name="studentEnrollYear" maxlength="7" size="7"/></td>
             </td>
            </tr>
            </table>
          </td>
     	</tr>
     </form>
	 </table>
  <script language="javascript" >
    var bar=new ToolBar("myBar","辅修专业学生信息",null,true,true);
    bar.addItem("<@msg.message key="action.save"/>","save()","save.gif");
    bar.addBack("<@msg.message key="action.back"/>");
    var form=document.commonForm;
    
  	 function searchByStdCode(){
		if (window.event.keyCode == 13){getByStdCode();}
	}

	function getByStdCode(){
		var params = {'record.student.code':DWRUtil.getValue('student.code')};
		studentManager.getStdBeanMap(params,setStdForAdd);
	}
	
	function setStdForAdd(stdMap){		
	    var find=false;
	    for(var key in stdMap){
			find=true;
			for(var innerkey in stdMap[key]){
				setStdMap(stdMap[key],innerkey);
			}
		}
		if(!find){alert("查无该生，请检查输入的学号！");}
	}
	function setStdMap(stdMap,key){
	    if(key=="student.id"){form['student.id'].value=stdMap[key];return;}
	    if(key=="student.code")return;
		if(document.getElementById(key)){
		   if(stdMap[key]){
		     //if(key=="student.type.name"){
		     //  alert(document.getElementById(key).outerHTML);
		     //}
		     document.getElementById(key).innerHTML=stdMap[key];
		   }else{
		      if(key.indexOf('engName')==-1&&key.indexOf('name')!=-1){
		        var enfName=key.replace('name','engName');
		        if(stdMap[enfName]){
		         document.getElementById(key).innerHTML=stdMap[enfName];
		        }
		      }
		      document.getElementById(key).innerHTML="";
		  }
	   }
	}
    function setAdminClassIdAndDescriptions1(id, description){
        var adminClassSelect=document.all['adminClass'];
        var adminClassOption=new Option(description, id);
        if (!hasOption(adminClassSelect, adminClassOption)){adminClassSelect.options[adminClassSelect.length]=adminClassOption}
    }
    
    function loadAdminClasssSelector(){
        var url="adminClassSelector.do?method=withSpeciality"+"&selectorId=1&adminClass.speciality.is2ndSpeciality=1";
        if(form['studentEnrollYear'].value!=""){url+="&adminClass.enrollYear="+document.all['studentEnrollYear'].value}
        if(form['adminClassSameSpeciality'].checked==true){
           if(form['student.secondMajor.id'].value!=""){url+="&adminClass.speciality.id="+form['student.secondMajor.id'].value}
        }
        popupMiniCommonSelector(url);
    }
    
    function resetAdminClasssSelector1(){
    	
    }
  	 function save() {
  	   	if (form['student.id'].value == "") {
  	     	alert("请输入学号，找到学生");
  	   	} else {
  	   		var a_fields = {
  	   			'studentEnrollYear':{'l':'<@msg.message key="attr.enrollTurn"/>', 'r':false, 't':'f_studentEnrollYear', 'f':'yearMonth'},
  	   			'student.secondMajor.id':{'l':'第二专业', 'r':true, 't':'f_secondMajor'}
  	   		};
  	   		
  	   		var v = new validator(form, a_fields, null);
  	   		if (v.exec()) {
	     	 	form['adminClassIds'].value = getAllOptionValue(form.adminClass);	 	
	         	form.submit();
         	}
       	}
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>