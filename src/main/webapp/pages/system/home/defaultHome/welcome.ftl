<#include "/templates/simpleHead.ftl"/>
<link href="${static_base}/themes/default/css/panel.css" rel="stylesheet" type="text/css">
<body >
<script>
   function _wi_tm(moudleId){
       var id= document.getElementById(moudleId);   
	   if(id.className=="module collapsed"){
	     id.className="module expanded";
	   }else{
	     id.className="module collapsed";
	   }
  }
  function getMessageInfo(id){
     window.open("systemMessage.do?method=info&systemMessage.id="+id, '', 'scrollbars=yes,left=0,top=0,width=600,height=350,status=yes');
  }
  function getNoticeInfo(id){
     window.open("notice.do?method=info&notice.id="+id, '', 'scrollbars=yes,left=0,top=0,width=600,height=350,status=yes,resizable=yes');
  }
  function changePassword(){
      var url = "password.do?method=changePassword";
      var selector= window.open(url, 'selector', 'scrollbars=yes,status=yes,width=1,height=1,left=1000,top=1000');
	  selector.moveTo(200,200);
	  selector.resizeTo(300,250);
  }
</script>
<#macro displayMessage>
   <#if newMessageCount!=0 >你有${newMessageCount}条新信息.<A href="systemMessage.do?method=index" target="_blank"><U>查看消息</U></A></#if>
</#macro>
<#if student?exists>
   <div id="student" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('student');"><@msg.message key="common.welcomeTitle"/></a>
     </h2>
     <div class="modulebody">
     	<@bean.message key="common.welcome" arg0=student.name?if_exists/>
        <table style="font-size:10pt" class="listTable">
           <tr>
           	<td width="15%"><@msg.message key="attr.stdNo"/></td>
           	<td>${student.code}</td>
           	<td width="15%"><@msg.message key="entity.studentType"/></td>
           	<td><@i18nName student.type/></td>
           </tr>
           <tr>
           	<td><@msg.message key="attr.enrollTurn"/></td>
           	<td>${student.enrollYear}</td>
           	<td><@msg.message key="entity.department"/></td>
           	<td><@i18nName student.department/></td>
           </tr>
           <tr>
           	<td><@msg.message key="entity.speciality"/></td>
           	<td><@i18nName student.firstMajor?if_exists/></td>
           	<td><@msg.message key="attr.specialityAspect"/></td>
           	<td><@i18nName student.firstAspect?if_exists/></td>
           </tr>
           <tr>
           	<td><@msg.message key="common.adminClass"/></td>
           	<td><#list student.adminClasses as adminClass>${adminClass.name}&nbsp;</#list></td>
           	<td><@msg.message key="entity.educationMode"/></td>
           	<td><@i18nName (student.studentStatusInfo.educationMode)?if_exists/></td>
           </tr>
        </table>
        <@bean.message key="common.welcomeState" arg0=date?string("yyyy-MM-dd HH:mm:ss") arg1=onlineUserCount?string/>
        <br><@displayMessage/>
     </div>
    </div>
<#else>
   <div id="user" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('user');">欢迎信息</a>
     </h2>
     <#assign welcomeMessage0>${user.userName?if_exists}&nbsp;</#assign>
     <#assign welcomeMessage1>${date}</#assign>
     <#assign welcomeMessage2>${onlineUserCount}</#assign>
     <#assign welcomeMessage3><@displayMessage/></#assign>
     <div class="modulebody"><@bean.message key="home.admin.welcome.message" arg0=welcomeMessage0 arg1=welcomeMessage1 arg2=welcomeMessage2 arg3=welcomeMessage3/></div>
   </div>
</#if>


<#if downloadFileList?exists>
  <#assign extMap={"xls":'xls.gif',"doc","doc.gif","pdf":"pdf.gif","zip":"zip.gif","":"generic.gif"}>
   <div id="downloadFileList" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('downloadFileList');"><@msg.message key="attr.documentationDownload"/></a>
     </h2>
     <div class="modulebody">
     <table style="font-size:10pt" width="90%">
	   <tr>	     
	     <td width="80%"><@msg.message key="attr.documentationTitle"/></td>
	     <td width="20%"><@msg.message key="attr.documentationDeployDate"/></td>
	   </tr>
      <#list downloadFileList as file>
	   <tr>
	    <td><image src="${static_base}/images/file/${extMap[file.fileExt]?default("generic.gif")}">&nbsp;<A style="color:blue" href="download.do?method=download&document.id=${file.id}">${file.name}</A></td>
	    <td>${file.uploadOn?string("yyyy-MM-dd")}</td>
	   </tr>
	   </#list>
	  </table>
	  <a href="download.do?method=index"/>&nbsp;<@msg.message key="common.more"/></A>
   </div>    
   </div>
  </#if>
 <#if notices?exists>
  <#if notices?size!=0 >
   <div id="notice" class="module expanded">
     <h2 class="header">
       <a href="#" class="toggle" onclick="_wi_tm('notice');"><@msg.message key="entity.notice"/></a>
     </h2>
     <div class="modulebody">
       <table  style="font-size:10pt" width="90%">
	   <tr >	     
	     <td width="50%"><@msg.message key="notice.title"/></td>
	     <td width="20%"><@msg.message key="notice.dateDeployed"/></td>
	   </tr>
       <#list notices as notice>
	   <tr >
	    <td><A style="color:blue" href="#"  alt="查看详情" onclick="getNoticeInfo('${notice.id}');">${notice.title}</A></td>
        <td>${notice.modifyAt}</td>
	   </tr>
	   </#list>
	  </table>
      </div>
   </div>
  </#if>
  </#if>
   
</body>
<#include "/templates/foot.ftl"/>