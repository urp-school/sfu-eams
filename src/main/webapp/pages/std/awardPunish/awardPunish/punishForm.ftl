<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <table id="bar" ></table>
 <table class="formTable" align="center" width="80%">
   <form name="actionForm" method="post" action="" onSubmit="return false;">
   <input name="isAward" type="hidden" value="${isAward?string("1","0")}"/>
   <@searchParams/>
 	<tr>
  	    <input type="hidden" name="punish.id" value="${punish.id?default('')}">
  	    <input type="hidden" id="stdId" name="punish.std.id"	value="${(punish.std.id)?default('')}">  		    
        <td class="title" align="center" width="20%"><@msg.message key="attr.stdNo"/><font color="red">*</font>:</td>
        <td width="30%"><#if (punish.std.id)?exists >${punish.std.code}<#else><input type="text" name="punish.std.code" TABINDEX="1" value="${(punish.std.code)?if_exists}" onchange="getStd()" style="width:90px" maxlength="32"/></#if></td>
        <td class="title" align="center"  width="20%"><@msg.message key="attr.personName"/>:</td>
  		<td id="name" width="30%">${(punish.std.name)?if_exists}</td>
  	</tr>
  	<tr>
        <td class="title" align="center"><@msg.message key="entity.speciality"/>:</td>
  		<td id="speciality"><@i18nName (punish.std.firstMajor)?if_exists/></td>
        <td class="title" align="center">班级:</td>
  		<td id="adminClass"><@getBeanListNames (punish.std.adminClasses)?if_exists/></td>
  	</tr>
  	<tr>
        <td class="title" align="center"><@msg.message key="entity.studentType"/>:</td>
  		<td id="stdType"><@i18nName (punish.std.type)?if_exists/></td>
        <td class="title" align="center"><@msg.message key="entity.department"/>:</td>
  		<td id="department"><@i18nName (punish.std.department)?if_exists/></td>
  	</tr>
  	<tr>
        <select id="stdType1" name="calendar.studentType.id" style="width:100px;display:none">
            <option value=""><@bean.message key="common.all"/></option>
        </select>
        <td class="title"><@bean.message key="attr.year2year"/></td>
        <td>
            <select id="year1" name="calendar.year" style="width:100px">
                <option value=""><@bean.message key="common.all"/></option>
            </select>
        </td>
        <td class="title"><@bean.message key="attr.term"/></td>
        <td>
            <select id="term1" name="calendar.term" style="width:100px">
                <option value=""><@bean.message key="common.all"/></option>
            </select>
        </td>
    </tr>
  	<tr>
        <td class="title" align="center" id="f_name"><font color="red">*</font>处分名称:</td>
  		<td><input name="punish.name"value="${(punish.name)?default('')}" style="width:100px" maxlength="10"/></td>
        <td class="title" align="center" id="punishType"><font color="red">*</font>处分类别:</td>
  		<td><@htm.i18nSelect name="punish.type.id" datas=types selected=(punish.type.id)?default('')?string style="width:100px"/></td>
  	</tr>
  	<tr>
        <td class="title" align="center" id="f_depart"><font color="red">*</font>部门:</td>
  		<td><input name="punish.depart"value="${(punish.depart)?default('')}" style="width:100px"/></td>
        <td class="title" align="center" id="f_presentOn"><font color="red">*</font>处分时间:</td>
  		<td><input name="punish.presentOn" value="<#if punish.presentOn?exists>${punish.presentOn?string("yyyy-MM-dd")}</#if>" onfocus="calendar()" style="width:100px"/></td>
  	</tr>
  	<tr>
        <td class="title" align="center"><font color="red">*</font>是否有效:</td>
  		<td><@htm.radio2 value=punish.isValid?default(true) name="punish.isValid"/></td>
        <td class="title" align="center">撤销时间:</td>
  		<td><input name="punish.withdrawOn" value="<#if punish.withdrawOn?exists>${punish.withdrawOn?string("yyyy-MM-dd")}</#if>" onfocus="calendar()" style="width:100px"/></td>
  	</tr>
    <tr>
        <td class="title" align="center">备注:</td>
  		<td colspan="3"><input name="punish.remark" maxlength="200" value="${punish.remark?default("")}" style="width:100px"/></td>
    </tr>
	<tr>
		<td class="darkColumn" colspan="4" align="center">
            <button  accesskey="S" name="button1" onclick="save(this.form);"><@msg.message key="action.save"/>(S)</button>
		</td>
	</tr>
  	</form>
  	<#assign stdTypeNullable=false>
    <#assign yearNullable=false>
    <#assign termNullable=false>
    <#include "/templates/calendarSelect1.ftl"/>
 </table>
 <br><br><br><br><br><br><br><br><br><br><br>
 </body>
 <script language="javascript" >
     var form =document.actionForm;
 // 查找学生
  function getStd(){     
     var stdCode=form['punish.std.code'].value;
     if(stdCode==""){ alert("请输入学号");clear(); }
     else{
       studentDAO.getBasicInfoName(setStdInfo,stdCode);
     }
  }
  // 清除设定的信息
  function clear(){
       $('name').innerHTML="";
       $('speciality').innerHTML="";
       $('adminClass').innerHTML="";
       $('stdType').innerHTML='';
       $('department').innerHTML="";
       $('stdId').value="";
  }
  
  var msg_outof_shool="该同学已经离开学校了.";
  var no_std="没有该学号对应的学生";
  
  function setStdInfo(data){
     if(null==data){
       clear();       
       $('message').innerHTML=no_std;
     }else{
        $('name').innerHTML=data.name;
        $('stdId').value=data.id;
        $('department').innerHTML=data['department.name'];
        $('speciality').innerHTML=data['firstMajor.name'];
        $('stdType').innerHTML=data['type.name'];
        $('adminClass').innerHTML=data['adminClasses.adminClass.name'];
        if(!data.state||!data.isInSchoolStatus)$("message").innerHTML=msg_outof_shool;
        else{
           $("message").innerHTML='';
        }
     }
  }
  
  function save(form){   
      var a_fields = {
         'punish.depart':{'l':'部门', 'r':true, 't':'f_depart'},
         'punish.presentOn':{'l':'时间','r':true, 't':'f_presentOn'},
         'punish.name':{'l':'处分名称','r':true, 't':'f_name'}
      };
      var v = new validator(form, a_fields, null);
      if (v.exec()) {
          if(form['punish.std.id'].value==""){
             alert("没有学生不能进行提交!");return;
          }
          form.action="awardPunish.do?method=save"
          form.submit();
      }
   }
   var bar =new ToolBar("bar","处分记录详细信息",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@msg.message key="action.back"/>");
 </script>
<#include "/templates/foot.ftl"/>