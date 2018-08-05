<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <table id="bar" ></table>
 <table class="formTable" align="center" width="80%">
   <form name="actionForm" action="" method="post" onSubmit="return false;">
      <input name="isAward" type="hidden" value="${isAward?string("1","0")}"/>
      <@searchParams/>
 	<tr>
  	    <input type="hidden" name="award.id" value="${award.id?default('')}">
  	    <input type="hidden" id="stdId" name="award.std.id"	value="${(award.std.id)?default('')}">  		    
        <td class="title" align="center" width="20%"><@msg.message key="attr.stdNo"/><font color="red">*</font>:</td>
        <td width="30%"><#if (award.std.id)?exists >${award.std.code}<#else><input type="text" name="award.std.code" maxlength="32" TABINDEX="1" value="${(award.std.code)?if_exists}" onchange="getStd()" style="width:90px"></#if></td>
        <td class="title" align="center"  width="20%"><@msg.message key="attr.personName"/>:</td>
  		<td id="name" width="30%">${(award.std.name)?if_exists}</td>
  	</tr>
  	<tr>
        <td class="title" align="center"><@msg.message key="entity.speciality"/>:</td>
  		<td id="speciality"><@i18nName (award.std.firstMajor)?if_exists/></td>
        <td class="title" align="center">班级:</td>
  		<td id="adminClass"><@getBeanListNames (award.std.adminClasses)?if_exists/></td>
  	</tr>
  	<tr>
        <td class="title" align="center"><@msg.message key="entity.studentType"/>:</td>
  		<td id="studentType"><@i18nName (award.std.type)?if_exists/></td>
        <td class="title" align="center"><@msg.message key="entity.department"/>:</td>
  		<td id="department"><@i18nName (award.std.department)?if_exists/></td>
  	</tr>
	<tr>
		<select id="stdType" name="calendar.studentType.id" style="width:100px;display:none">
			<option value=""><@bean.message key="common.all"/></option>
		</select>
		<td class="title"><@bean.message key="attr.year2year"/></td>
		<td>
			<select id="year" name="calendar.year" style="width:100px">
				<option value=""><@bean.message key="common.all"/></option>
			</select>
		</td>
		<td class="title"><@bean.message key="attr.term"/></td>
		<td>
			<select id="term" name="calendar.term" style="width:100px">
				<option value=""><@bean.message key="common.all"/></option>
			</select>
		</td>
	</tr>
  	<tr>
        <td class="title" align="center" id="f_name"><font color="red">*</font>奖励名称:</td>
  		<td><input name="award.name"value="${(award.name)?default('')}" style="width:100px"/></td>
        <td class="title" align="center" id="awardType"><font color="red">*</font>奖励类别:</td>
  		<td><@htm.i18nSelect name="award.type.id" datas=types selected=(award.type.id)?default('')?string style="width:100px"/></td>
  	</tr>
  	<tr>
        <td class="title" align="center" id="f_depart"><font color="red">*</font>颁发部门:</td>
  		<td><input name="award.depart"value="${(award.depart)?default('')}" style="width:100px" maxlength="20"/></td>
        <td class="title" align="center" id="f_presentOn"><font color="red">*</font>奖励时间:</td>
  		<td><input name="award.presentOn" value="<#if award.presentOn?exists>${award.presentOn?string("yyyy-MM-dd")}</#if>" onfocus="calendar()" style="width:100px" maxlength="10"/></td>
  	</tr>
  	<tr>
        <td class="title" align="center"><font color="red">*</font>是否有效:</td>
  		<td><@htm.radio2 value=award.isValid?default(true) name="award.isValid"/></td>
        <td class="title" align="center">撤销时间:</td>
  		<td><input name="award.withdrawOn" value="<#if award.withdrawOn?exists>${award.withdrawOn?string("yyyy-MM-dd")}</#if>" onfocus="calendar()" style="width:100px"/></td>
  	</tr>
	<tr>
	    <td class="title" align="center">备注:</td>
  		<td colspan="3"><input name="award.remark" value="${award.remark?default("")}" style="width:100px"/></td>
    </tr>
	<tr>
		<td class="darkColumn" colspan="4" align="center">
            <button accesskey="S" name="button1"  onclick="save(this.form);"><@msg.message key="action.save"/>(S)</button>
		</td>
	</tr>
  	</form>
	<#assign stdTypeNullable=false>
	<#assign yearNullable=false>
	<#assign termNullable=false>
	<#include "/templates/calendarSelect.ftl"/>
 </table>
 <br><br><br><br><br><br><br><br><br><br><br>
 </body>
 <script language="javascript" >
     var form =document.actionForm;
 // 查找学生
  function getStd(){     
     var stdCode=form['award.std.code'].value;
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
       $('studentType').innerHTML='';
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
        $('studentType').innerHTML=data['type.name'];
        $('stdType').value = data['type.id'];
        $('adminClass').innerHTML=data['adminClasses.adminClass.name'];
        if(!data.state||!data.isInSchoolStatus)$("message").innerHTML=msg_outof_shool;
        else{
           $("message").innerHTML='';
        }
     }
  }
  
  function save(form){   
      var a_fields = {
         'award.depart':{'l':'颁发部门', 'r':true, 't':'f_depart'},
         'award.presentOn':{'l':'颁发时间','r':true, 't':'f_presentOn'},
         'award.name':{'l':'获奖名称','r':true, 't':'f_name'}
      };
      var v = new validator(form, a_fields, null);
      if (v.exec()) {
          if(form['award.std.id'].value==""){
             alert("没有学生不能进行提交!");return;
          }
          form.action="awardPunish.do?method=save"
          form.submit();
      }
   }
   var bar =new ToolBar("bar","奖励记录",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@msg.message key="action.back"/>");
 </script>
<#include "/templates/foot.ftl"/>