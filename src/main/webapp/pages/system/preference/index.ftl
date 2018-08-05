<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/Menu.js"></script> 
 <body>
 <style>
.settingTableStyle {
	border-collapse: collapse;
    border:solid;
	border-width:1px;
    border-color:#006CB2;
	text-align: left;
  	font-style: normal; 
	font-size: 10pt; 
	width:100%;
}
table.settingTableStyle td{
	border:solid;
	border-width:0px;
	border-color:#006CB2;
	height:30px;
}
 </style>
 <table id="userBar" width="100%"></table>
 <table class="settingTableStyle" style="background-color: #E1ECFF">
 <form name="settingForm" method="post" action="" onsubmit="return false;">
   <tr>
   <td>&nbsp;&nbsp;提示</td>
   <td style="width:50px"></td>
   <td>偏好设置依赖于存储在你计算机上的cookie.如果你的浏览器不支持cookie,偏好设置将不起效果。<br>
       保存后需重新登录，才能生效。
   </td>
   </tr>
   <tr>
     <td colspan="6"  style="height:5px;font-size:0px;" >
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
     </td>
   </tr>
   <tr>
   <tr>
	   <td>&nbsp;&nbsp;主界面风格：</td>
	   <td style="width:50px"></td>
	   <td>
	     <select name="system.facade">
	       <option value="default" <#if !facade?exists || facade="default"> selected</#if>>缺省</option>
	       <option value="simple" <#if facade?exists && facade="simple"> selected</#if>>简约</option>
	     </select>
	   </td>
   </tr>
   <tr>
     <td colspan="6"  style="height:5px;font-size:0px;" >
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
     </td>
   </tr>
   <tr>
	   <td>&nbsp;&nbsp;语言设置：</td>
	   <td style="width:50px"></td>
	   <td>
	     <select name="language" disabled>
	       <option value="en_US" <#if language="en_US"> selected</#if>>english</option>
	       <option value="cn_ZH" <#if language="cn_ZH"> selected</#if>>中文</option>
	     </select>
	   </td>
   </tr>
   <tr>
     <td colspan="6"  style="height:5px;font-size:0px;" >
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
     </td>
   </tr>
   <tr>
   <td>&nbsp;&nbsp;最大页长：</td>
   <td style="width:50px"> </td>
   <td>每页<select  name="pageSize" >
	         <option value="10" <#if pageSize="10">selected</#if>>10</option>
	         <option value="15" <#if pageSize="15">selected</#if>>15</option>
	         <option value="20" <#if pageSize="20">selected</#if>>20</option>
  	         <option value="25" <#if pageSize="25">selected</#if>>25</option>
	         <option value="30" <#if pageSize="30">selected</#if>>30</option>
	         <option value="50" <#if pageSize="50">selected</#if>>50</option>
	         <option value="70" <#if pageSize="70">selected</#if>>70</option>
	         <option value="90" <#if pageSize="90">selected</#if>>90</option>
	         <option value="100" <#if pageSize="100">selected</#if>>100</option>
	         <option value="150" <#if pageSize="150">selected</#if>>150</option>
	         <option value="300" <#if pageSize="300">selected</#if>>300</option>
	        </select>条记录</td>
   </tr>
   <tr>
     <td colspan="6"  style="height:5px;font-size:0px;" >
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
     </td>
   </tr>
   <tr>
   <td>&nbsp;&nbsp;记住我：</td>
   <td style="width:50px"> </td>
   <td><@htm.radio2 value=rememberMe name="rememberMe" /></td>
   </tr>
   <tr>
     <td colspan="6"  style="height:5px;font-size:0px;" >
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
     </td>
   </tr>
   <tr>
   <td>&nbsp;&nbsp;教学日历：</td>
   <td style="width:50px"> </td>
   <td>
      经常使用的学年学期：<br>
      <@bean.message key="entity.studentType"/>
      <select id="stdType" name="calendar.studentType.id" style="width:100px;">               
        <option value="${studentType.id}"></option>
      </select>
      <@bean.message key="attr.year2year"/>:
      <select id="year" name="calendar.year"  style="width:100px;">                
        <option value="${calendar.year}"></option>
      </select>
      <@bean.message key="attr.term"/>:
      <select id="term" name="calendar.term" style="width:80px;">
        <option value="${calendar.term}"></option>
      </select>
   </td>
   </tr>
   <tr>
     <td colspan="6"  style="height:5px;font-size:0px;" >
       <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
     </td>
   </tr>
   <tr>
     <td>&nbsp;&nbsp;会话超时时间：</td>
     <td style="width:50px"> </td>
     <td>${maxInactiveInterval}s</td>
   </tr>
   <tr>
      <td  colspan="4" align="center">
      <button onclick="saveSetting()" accessKey="S"><@msg.message key="action.save"/>(<U>S</U>)</button>
      &nbsp;&nbsp;
      <button  onclick="this.form.reset()">重置</button></td>
   </tr>
   <#assign stdTypeList=stdTypes?sort_by("code")>
   <#include "/templates/calendarSelect.ftl"/>
 </table>
 </form>
<script>
   var bar = new ToolBar('userBar','&nbsp;偏好设置',null,true,true);
   bar.setMessage('<@getMessage/>');  
   bar.addItem("我的权限",resourceList,'list.gif');
   bar.addBack("<@bean.message key="action.back"/>"); 
   var form = document.settingForm;
   function resourceList(){
     self.location="preference.do?method=menuList"
   }
   function saveSetting(){
       if(!(/^[\+\-]?\d*$/.test(form['pageSize'].value))){
          alert("请输入正整数");return;
       }
       form.action="preference.do?method=save"
       form.submit();
   }
</script>
<#include "/templates/foot.ftl"/>