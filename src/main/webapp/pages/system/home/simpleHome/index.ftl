<#include "/templates/simpleHead.ftl"/>
<style>
#BakcGroup_image_td {F
  background-repeat: repeat;
  background-position: center center;
  background-image: url(${static_base}/themes/simple/images/bgpic.bmp);
}
</style>
<body style="overflow-y:hidden;">
<table width="100%"  style="width:100%;height:78px" border="0" cellpadding="0" cellspacing="0" id="logoTable" style="" >
  <tr>
   <td width="995" ALIGN="right" VALIGN="bottom" width="521" BACKGROUND="${static_base}/themes/simple/images/head.jpg">&nbsp;</td>
   <td width="50%" ALIGN="right" VALIGN="bottom" BACKGROUND="${static_base}/themes/simple/images/000003.gif">
    <table  border="0" height="100%" >
     <tr>
       <td colspan="4" align="right" valign="top" style="font:14px;color:green" id="changeCategoryTd"><B>身份切换</B></td>
       <td align="right" valign="top" height="100%">
        <select name="security.categoryId" onchange="changeUserCategory(this.value);" id="userCategorySelect" style="display:none;width:100px" >
            <#list user.categories as category>
        	<option value="${category.id}">${category.name}</option>
        	</#list>
        </select>
        </td>
        <td align="right" valign="top" >
         <img src="${static_base}/themes/simple/images/goHome.jpg" style="cursor:pointer;" onClick="home()" alt="返回主页"/>&nbsp;
       </td>
     </tr>
    </table>
   </td>
  </tr>
</table>
 
<#assign parentCode><#if menus?exists&&(menus?size>0)>${menus?first.code?if_exists}<#else>${parentCode?if_exists}</#if></#assign>
<table id="mainTable" style="width:100%;height:84.5%" cellpadding="0" cellspacing="0" border="0">
 <tr>
  <td width="100%" colspan="3" VALIGN="TOP" height="16" id="BakcGroup_image_td" align="left"
   style="background-image: url(${static_base}/themes/simple/images/bgpic.bmp);">
  <#macro i18nNameTitle(entity)><#if localName?index_of("en")!=-1><#if entity.engTitle?if_exists?trim=="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if><#else><#if entity.title?if_exists?trim!="">${entity.title?if_exists}<#else>${entity.engTitle?if_exists}</#if></#if></#macro>      
   <#list menus?if_exists as module>
    <A style="cursor:hand" HREF="simpleHome.do?method=moduleList&parentCode=${module.code}" target="leftFrame" >
      <span CLASS="menu_blue_13px2" onclick="changeColor(this);"><@i18nNameTitle module/></span>
    </A>&nbsp;&nbsp;
   </#list>
   	欢迎您：${user.userName?if_exists}(${user.name?if_exists}) &nbsp;今天是${date}
   	<#macro displayMessage>
   <#if newMessageCount!=0 >你有${newMessageCount}条新信息.<A href="systemMessage.do?method=index" target="_blank"><U>查看消息</U></A></#if>
   </#macro>
   <@displayMessage/>
  </td>
 </tr>
 <tr>
    <td width="100%" colspan="3" bgcolor="#e1e6e9" align="center"><img src="${static_base}/themes/simple/images/up.jpg" name="imgup" onmousemove="style.cursor='hand';" onclick="verticalSwitch()"/></td>
 </tr>
 <tr>
   <td style="HEIGHT:100%;width:14%" border="0" id="leftTD">
	   <iframe HEIGHT="100%" WIDTH="100%" SCROLLING="AUTO" 
	       FRAMEBORDER="0" src="simpleHome.do?method=moduleList&parentCode=${parentCode}" name="leftFrame" >
	   </iframe>
   </td>
   <td width="0%" height="100%" bgcolor="#e1e6e9" style="cursor:w-resize;">
	   <a onClick="horizontalSwitch('left_tag')">
	      <img id="left_tag" style="cursor:hand" src="${static_base}/themes/simple/images/left.jpg" border="0" >
	   </a>
    </td>
   <td style="width:86%" id="rightTD">
	   <iframe HEIGHT="100%" WIDTH="100%" SCROLLING="auto" 
	     FRAMEBORDER="0" src="simpleHome.do?method=welcome" name="main" id="main">
	   </iframe>
   </td>
 </tr>
</table>

<div  id="msgNotificationDIV" style="display:none;width:300px;height:150px;position:absolute;bottom:0px;right:0px;border:solid;border-width:1px;background-color:#94aef3 ">
<iframe HEIGHT="100%" WIDTH="100%" SCROLLING="AUTO" 
	       FRAMEBORDER="0" src="#" name="msgFrame" >
</iframe>
</div>
</body>
<script>
	var obj = null;
	function changeColor(field){
		field.className = "menuSelected";
		if(obj != null && obj != field)obj.className = "menu_blue_13px2";
		obj = field;
		//home();
	}
  
  if( userCategorySelect.options.length>1){
     userCategorySelect.style.display="block";
     var curCategory=${RequestParameters['security.categoryId']?default(1)};
     for(var i=0;i<userCategorySelect.options.length;i++){
         if(userCategorySelect.options[i].value==curCategory){
             userCategorySelect.options[i].selected=true;
         }
     }
  }else{
     document.getElementById("changeCategoryTd").innerHTML="";
  }
  
  function home() {
      main.location="simpleHome.do?method=welcome";
  }  
  function logout() {
      self.location = 'logout.do';
  }  
  function changePassword(){
      window.open("password.do?method=changePassword");
  }
  function changeUserCategory(category){
     self.location="simpleHome.do?method=index&security.categoryId="+category;
  }
  //调整水平比例
  function horizontalSwitch(id) {
      var fullpath = document.getElementById(id).src;
      var filename = fullpath.substr(fullpath.lastIndexOf("/")+1, fullpath.length);
	  switch (filename) {
			case "left.jpg":
				document.getElementById(id).src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"right.jpg";
				break;
			case "right.jpg":
				document.getElementById(id).src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"left.jpg";
				break;	
	  }
      if(leftTD.style.width=='14%'){
        leftTD.style.width="0%";rightTD.style.width="100%";
      }else{
         leftTD.style.width="14%";rightTD.style.width="86%";
      }
   }
  //垂直调整比例
  function verticalSwitch(){
    logoTable.style.display = (logoTable.style.display == "none") ? "" : "none";
    if (mainTable.style.height=='100%') {
        mainTable.style.height='85.5%';
        logoTable.style.height='78px';
    }else{
        mainTable.style.height='100%';
        logoTable.style.height='0%';
    }
  }
  function closeMsgNotify(){
     msgNotificationDIV.style.display="none";
  }
  function openMsgNotify(){
     msgNotificationDIV.style.display="block";	
  }
  
  var firstLoadMsg=true;
  
  function reloginNotify(){
     openMsgNotify();
     document.getElementById("msgNotificationDIV").innerHTML='你已经由于超时或其他原因退出系统,请重新登录系统.';
     clearInterval(interval);
  }
  
  function refreshMsgNotification(){
    if(!firstLoadMsg){
      try{
	      if( typeof msgFrame.newMessageSize=="undefined"){
            reloginNotify();
	      }else{
	         msgFrame.location="systemMessage.do?method=newMessageNotification";
	      }
      }catch(e){
          reloginNotify();
      }
    }else{
      msgFrame.location="systemMessage.do?method=newMessageNotification";
      firstLoadMsg=false;
    }
  }
  function removeMsgNotify(){
     closeMsgNotify();
     clearInterval(interval);
  }
  var interval=setInterval('refreshMsgNotification()',${systemConfig.getConfigItemValue('msg_notify_refresh_interval')}*1000);
  
</script>
<#include "/templates/foot.ftl"/>