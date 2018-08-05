<#include "/templates/head.ftl"/>
 <table id="myBar" width="100%" ></table>
 <table  class="infoTable" style="width:100%">
   	<tr>
 		<td class="title" width="18%" id="f_stdId"><@bean.message key="attr.stdNo"/>：</td>
 		<td width="50%">${(std.code)?default('')}</td>
		<td class="title" id="f_studentPhoto" colspan="2" style="text-align:left"><@msg.message key="std.photo"/>:&nbsp;<!--<button style="vertical-align:middle;" onclick="popupCommonWindow('photoByStudent.do?method=uploadForm&student.id=${std.id}','studentFileImportWin', 450, 250);"><@msg.message key="action.update"/></button>--></td>
   	</tr>
   	<#assign spurl = "org.beangle.freemarker.StudentPhotoUrlMethod"?new()/>
	 <script language="javascript">
	 	function refreshPhoto(){
	 	   $('studentPhoto').src='${spurl(std.code)}';
	 	}
	 </script>
   	<tr>
     	<td class="title" id="f_name"><@bean.message key="attr.personName"/>：</td>
     	<td>${std.name?if_exists}</td>
 		<td align="center" rowspan="6" width="120px" colspan="2">
 	       <img id="studentPhoto" src="${spurl(std.code)}" 
 	            width="120"  style="heignt:100%;vertical-align:top;margin-top:2px;border:2px" >
 		</td>
   	</tr>
   	<tr>
     	<td class="title"><@bean.message key="attr.engName"/>：</td>
     	<td>${std.engName?if_exists}</td>
   	</tr>	
   	<tr>
     	<td class="title"><@bean.message key="std.enrollYear"/>：</td>
     	<td>${std.enrollYear?if_exists}</td>
   	</tr>
   	<tr>
     	<td class="title"><@msg.message key="std.eduLength"/>：</td>
     	<td>${std.schoolingLength?if_exists}</td>
   	</tr>
   	<tr>
     	<td class="title"><@bean.message key="entity.studentType"/>：</td>
     	<td><@i18nName std.type?if_exists/></td>
   	</tr>
   	<tr>
     	<td class="title"><@bean.message key="entity.college"/>：</td>
     	<td><@i18nName std.department/></td>
   	</tr>
   	<tr>
     	<td class="title"><@bean.message key="entity.speciality"/>：</td>
     	<td ><@i18nName std.firstMajor?if_exists/></td>
     	<td class="title" ><@bean.message key="entity.specialityAspect"/>：</td>
     	<td><@i18nName std.firstAspect?if_exists/></td>
   	</tr>
   	<tr>
 		<td class="title"><@bean.message key="entity.adminClass"/>：</td>
 		<td><#list std.adminClasses?if_exists?sort_by("code") as adminClass><#if adminClass_has_next ><@i18nName adminClass />(${adminClass["code"]})<#if (adminClass_index+1)%2==1>,</#if><#if (adminClass_index+1)%2==0><br></#if><#else><@i18nName adminClass />(${adminClass["code"]})</#if></#list></td>
   		<td class="title"><@msg.message key="common.tutor"/>：</td>
 		<td><@i18nName std.teacher?if_exists/><#if couldEdit?default(false)><button style="vertical-align:middle;" onclick="edit();"><@msg.message key="action.edit"/></button></#if></td>
   	</tr>
   	<tr>
     	<td class="title"><@bean.message key="entity.schoolDistrict"/>：</td>
     	<td><@i18nName (std.schoolDistrict)?if_exists /></td>
     	<td class="title"><@bean.message key="entity.isStudentStatusAvailable"/>：</td>
     	<td>
     	<#if std.active?default(false)><@bean.message key="entity.available"/><#elseif !std.active><@bean.message key="entity.unavailable"/></#if>
 		</td>
   	</tr>
   	<tr>
     	<td class="title"><@bean.message key="attr.remark"/>：</td>
     	<td style="word-warp:break-word;word-break:break-all">${std.remark?if_exists}</td>
        <td class="title"><@bean.message key="common.languageAbility"/>：</td>
     	<td><@i18nName (std.languageAbility)?if_exists /></td>
   	</tr>
 </table>
 <script>
    var bar =new ToolBar("myBar","<@msg.message key="std.title"/>",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addPrint("<@msg.message key="action.print"/>");
    bar.addBack("<@msg.message key="action.back"/>");
    function edit(){
       self.location="stdDetail.do?method=editStdInfo";
    }
 </script>
<#include "/templates/foot.ftl"/>