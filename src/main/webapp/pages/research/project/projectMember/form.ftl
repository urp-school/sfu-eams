<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
<#assign labInfo>项目信息</#assign>  
<#include "/templates/back.ftl"/>   
	<table width="90%" align="center"  class="formTable">
		<form action="projectMember.do?method=save" name="projectMemberForm" method="post" onsubmit="return false;">
		<@searchParams/>
		<input type="hidden" name="projectMember.id" value="${(projectMember.id)?default('')}"/>
		<input type="hidden" value="${teachProjectId?if_exists}" name="teachProjectId"/>
		<tr>
			<td id="f_name" align="right" class="title" width="20%"><font color="red">*</font>成员姓名</td>
			<td align="bottom" width="30%">
			<input type="text" name="projectMember.name" value="${(projectMember.name)?if_exists}" maxlength="50" style="width:100%"/>
			</td>
			<td id="f_gender" class="title" width="20%"><font color="red">*</font>成员性别:</td>
			<td width="30%">
	     	<select name="projectMember.gender.id" style="width:100%">
	     		<option value="">...</option>
		        <#list genders as gender>
			       <option value="${gender.id}"<#if (projectMember.gender.id)?default("")?string == (gender.id)?string>selected</#if>><@i18nName gender/></option>
			    </#list>
	     	</select>
			</td>
		</tr>

		<tr>
            <td class="title" width="25%" id="f_birthday">&nbsp;出生年月:</td>
	        <td>
	        <input type="text" maxlength="10" style="width:100%" name="projectMember.birthday" value="<#if (projectMember.birthday)?exists>${(projectMember.birthday)?string("yyyy-MM-dd")}</#if>" onfocus="calendar()"/>
	        </td>			
			<td id="f_teacherTitle" class="title"><font color="red">*</font>成员职称:</td>
			<td>
	     	<select name="projectMember.teacherTitle.id" style="width:100%">
		     	<option value="">...</option>
		        <#list teacherTitles as teacherTitle>
			       <option value="${teacherTitle.id}"<#if (projectMember.teacherTitle.id)?default("")?string == (teacherTitle.id)?string>selected</#if>><@i18nName teacherTitle/></option>
			    </#list>
	     	</select>
			</td>
		</tr>
		<tr>
			<td  id="f_describe" class="title"><font color="red">*</font>个人简介:</td>
			<td colspan="3">
	     		<textarea name="projectMember.resume" rows="10" style="width:100%;">${(projectMember.resume)?if_exists}</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center" class="darkColumn">
				<button onClick='save(this.form)'><@bean.message key="system.button.submit"/></button>
	     	</td>
		</tr>
		</form> 
    </table>
    <SCRIPT language=javascript> 
    function save(form){        
         var a_fields = {
         'projectMember.name':{'l':'成员姓名', 'r':true, 't':'f_name'},
         'projectMember.gender.id':{'l':'成员性别', 'r':true, 't':'f_gender'},
         'projectMember.teacherTitle.id':{'l':'成员职称', 'r':true, 't':'f_teacherTitle'},
         'projectMember.resume':{'l':'个人简介', 'r':true, 't':'f_describe','mx':'500'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }  
   </SCRIPT>
</body> 
</html>