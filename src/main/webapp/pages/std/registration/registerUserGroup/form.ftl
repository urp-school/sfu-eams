<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <table id="bar" ></table>
<body> 
<table width="100%" align="center" class="formTable">
  <form name="thisForm" method="post" action="" onsubmit="return false;">
  		<input name="registerUserGroup.id" type="hidden" value="${(registerUserGroup.id)?default('')}"/>
  	  	<tr>
        <td class="title" align="center" id="f_name"><font color="red">*</font>组名:</td>
  		<td><input name="registerUserGroup.name"value="${(registerUserGroup.name)?default('')}" style="width:100px"/></td>
  	  	</tr>
	   <tr>
        <td class="title" align="center" id="f_presentOn"><font color="red">*</font>开始时间:</td>
  		<td><input name="registerUserGroup.beginAt" value="<#if registerUserGroup.beginAt?exists>${registerUserGroup.beginAt?string("yyyy-MM-dd")}</#if>" onfocus="calendar()" style="width:100px" maxlength="10"/>
  		<select name="beginHour"  size="1%">
  		<#list 0..24 as i>
  		<option value="${i}">${i}</option>
  		</#list>
  		</select>时
  		<select name="beginMinute"  size="1%">
  		<#list 0..59 as i>
  		<option value="${i}">${i}</option>
  		</#list>
  		</select>分
  		</td>
  	   </tr>
	   	<tr>
        <td class="title" align="center" id="f_presentOn"><font color="red">*</font>结束时间:</td>
  	    <td><input name="registerUserGroup.endAt" value="<#if registerUserGroup.endAt?exists>${registerUserGroup.endAt?string("yyyy-MM-dd")}</#if>" onfocus="calendar()" style="width:100px" maxlength="10"/>
  	    <select name="endHour"  size="1%">
  		<#list 0..24 as i>
  		<option value="${i}">${i}</option>
  		</#list>
  		</select>时
  		<select name="endMinute"  size="1%">
  		<#list 0..59 as i>
  		<option value="${i}">${i}</option>
  		</#list>
  		</select>分
  	    </td>
  	   </tr>
	   <tr>
		<td class="darkColumn" colspan="4" align="center">
            <button accesskey="S" name="button1"  onclick="save(this.form);"><@msg.message key="action.save"/>(S)</button>
		</td>
	</tr>		   	   
	   </form>
   </table>
   <br><br><br><br><br><br><br><br><br><br><br>
 </body>
 <script language="javascript" >
   var bar =new ToolBar("bar","组信息",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@msg.message key="action.back"/>");
  function save(form){
  	form.action="registerUserGroup.do?method=save";
  	form.submit();
  }
 </script>
<#include "/templates/foot.ftl"/>
