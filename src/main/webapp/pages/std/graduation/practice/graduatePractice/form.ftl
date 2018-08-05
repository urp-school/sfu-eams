<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<body> 
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script>
   function addTeacher(){
       var teacher = document.getElementById('teacher');
       if(document.conditionForm['graduatePractice.practiceTeacher.id'].value.length>0){
       		alert("只能选择单个老师");
       		return;
       }else{
       		document.conditionForm['graduatePractice.practiceTeacher.id'].value+=teacher.value;
       		document.conditionForm['graduatePractice.practiceTeacher.name'].value+=DWRUtil.getText('teacher');
       }
   }
   // 查找学生
    function getStd(){
     var stdCode=document.conditionForm['graduatePractice.student.code'].value;
     if(stdCode==""){ alert("请输入学号"); }
     else{
       studentDAO.getBasicInfoName(setStdInfo,stdCode);
     }
    }
    function setStdInfo(data){
     if(null==data){
       document.getElementById("stdName").innerHTML="没有该学生";
     }else{
        $('stdName').innerHTML=data.name;
     }
  }
</script>
<#assign labInfo><#if graduatePractice.id?exists>修改实习基本信息<#else>添加实习基本信息</#if></#assign>
<#include "/templates/back.ftl"/>
<table width="100%" align="center" class="formTable">
  <form name="conditionForm" method="post" action="" onsubmit="return false;">
	   <tr class="darkColumn" align="center">
	     <td colspan="4">${labInfo?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_code">学生学号<font color="red">*</font>：
	     </td>
	     <td><#if graduatePractice.id?exists><input type="hidden" name="graduatePractice.student.code" value="${(graduatePractice.student.code)?if_exists}">${(graduatePractice.student.code)?if_exists}学生姓名:${(graduatePractice.student.name)?if_exists}<#else><input type="text" name="graduatePractice.student.code" value="${(graduatePractice.student.code)?if_exists}" onblur="getStd()"/>学生姓名:<span id="stdName"></span></#if>
	     </td>
	  </tr>
	  <tr>
	  	<td class="title"><@msg.message key="std.specialityType"/>：</td>
		<td>
			<select name="graduatePractice.majorType.id" style="width:120px">
				<option value="1"><@msg.message key="entity.firstSpeciality"/></option>
				<option value="2"><@msg.message key="entity.secondSpeciality"/></option>
			</select>
		</td>
	  </tr>
	  <tr>
	     <td class="title" id="f_company">实习单位<font color="red">*</font>:</td>
	     <td><input type="text" name="graduatePractice.practiceCompany" maxlength="20" value="${(graduatePractice.practiceCompany)?if_exists}"/>           
	     </td>
	   </tr>
	   <tr>
	     <td class="title"  id="f_practiceSource">实习方式<font color="red">*</font>:</td>
	     <td><@htm.i18nSelect datas=practiceSources selected="${(graduatePractice.practiceSource.id)?default('')?string}" name="graduatePractice.practiceSource.id" style="width:300px;"><option value="">请选择...</option></@></td>
	   </tr>
	   <tr>
	     <td class="title" id="f_isBase">是否实习基地<font color="red">*</font>：
	     </td>
	     <td><select name="graduatePractice.isPractictBase" style="width:150px;">
	     	 <option value="">请选择...</option>
	     	 <option value="1" <#if graduatePractice.isPractictBase?exists&&(graduatePractice.isPractictBase)?if_exists=true>selected</#if>>是</option>
	     	 <option value="0" <#if graduatePractice.isPractictBase?exists&&(graduatePractice.isPractictBase)?if_exists=false>selected</#if>>否</option>
	     </select>
	     </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_teacher">指导教师:</td>
		 <td align="left">
				<input type="hidden" name="graduatePractice.practiceTeacher.id" value="${(graduatePractice.practiceTeacher.id)?if_exists}">
				<input type="text" name="graduatePractice.practiceTeacher.name" style="width:300px;" value="${(graduatePractice.practiceTeacher.name)?if_exists}" readonly="true"><br>
	       		<select id="teachDepartment" name="teachDepartment" style="width:100px">
   	         		<option value="${RequestParameters['teachDepartment']?if_exists}"></option>
	       		</select>
   	       		<select id="teacher" name="teacherId" style="width:90px">
   	         		<option value="${RequestParameters['teacherId']?if_exists}"></option>
	       		</select>
               <input type="button" class="buttonStyle" value="<@bean.message key="action.add"/>" onClick="addTeacher();"/>
   	       	   <input type="button" class="buttonStyle" value="<@bean.message key="action.clear"/>" onClick="this.form['graduatePractice.practiceTeacher.id'].value='';this.form['graduatePractice.practiceTeacher.name'].value='';"/> 
			</td>
	   </tr>
	   <tr>
	     <td class="title" id="f_practiceDesc">实习描述<font color="red">*</font>：
	     </td>
	     <td><textarea name="graduatePractice.practiceDesc" colspan="40" rowspan="3" style="width:300px;">${(graduatePractice.practiceDesc)?if_exists}</textarea>
	     </td>
	   </tr>		   	   
	   <tr class="darkColumn" align="center">
	     <td colspan="2">
	       <input type="hidden" name="graduatePractice.id" value="${(graduatePractice.id)?if_exists}">
	       <input type="hidden" name="graduatePractice.teachCalendar.id" value="<#if calendar?exists>${calendar.id}<#else>${graduatePractice.teachCalendar.id}</#if>">
		   <button name="button1" onClick="doAction(this.form,null)" class="buttonStyle">保存</button>
		   <#if !graduatePractice.id?exists><button name="button1" onClick="doAction(this.form,'next')" class="buttonStyle">保存并且添加下一个</button></#if>
	     </td>
	   </tr>
	   </form>
   </table>
 </body>
 <script language="javascript" >
     function doAction(form,isNext){
      var a_fields = {
         'graduatePractice.student.code':{'l':'学生学号', 'r':true, 't':'f_code','mx':20},
         'graduatePractice.practiceCompany':{'l':'实习单位', 'r':true, 't':'f_company','mx':100},
         'graduatePractice.practiceSource.id':{'l':'实习方式', 'r':true, 't':'f_practiceSource'},
         'graduatePractice.isPractictBase':{'l':'是否实习基地', 'r':true, 't':'f_isBase'},
         'graduatePractice.practiceDesc':{'l':'实习描述', 'r':true, 't':'f_practiceDesc','mx':500}         
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
     	var url="graduatePractice.do?method=save";
     	if(null!=isNext){
     	   url+="&isNext=true";
     	}
        setSearchParams(parent.document.searchForm,form);
     	form.action=url;
        form.submit();
     } 
    }
 </script>
<#include "/templates/departTeacher2Select.ftl"/>
<#include "/templates/foot.ftl"/>