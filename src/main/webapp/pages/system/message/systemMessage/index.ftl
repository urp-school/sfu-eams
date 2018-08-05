<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','消息管理',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
   function displayMessageList(td,kind){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      messageListFrame.location="systemMessage.do?orderBy=message.activeOn desc&method="+kind;
   }
   function newMessage(td,toWho){
      //clearSelected(viewTables,td);
      //setSelectedRow(viewTables,td);
      messageListFrame.location="systemMessage.do?method=newMessage&who="+toWho;
      //self.location="systemMessage.do?method=newMessage&who="+toWho;
   }
</script>
<table width="100%" height="93%" class="frameTable">
   <tr>
   <td valign="top" class="frameTable_view" width="17%" style="font-size:10pt">
      <table  width="100%" id ="viewTables" style="font-size:10pt">
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>消息分类</B>      
	      </td>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>    
       <tr>
         <td class="padding"  onclick="displayMessageList(this,'newly')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/inbox.gif"> 新消息
         </td>
       </tr>
       <tr >
         <td class="padding" onclick="displayMessageList(this,'readed')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          &nbsp;&nbsp;<image src="${static_base}/images/action/readedMessage.gif">已读消息
         </td>
       </tr>
       <tr>
         <td class="padding"  onclick="displayMessageList(this,'sendbox')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
           &nbsp;&nbsp;<image src="${static_base}/images/action/sentbox.gif">已发送的
         </td>
       </tr>
       <tr>
         <td class="padding" onclick="displayMessageList(this,'trash')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          &nbsp;&nbsp;<image src="${static_base}/images/action/trash.gif"> 垃圾箱
         </td>
       </tr>
       <#if isAdmin?exists&&isAdmin>
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>发送消息</B>      
	      </td>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>    
       <tr>
         <td class="padding"  onclick="newMessage(this,'std')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/new.gif">发送到学生
         </td>
       </tr>
       <tr>
         <td class="padding" onclick="newMessage(this,'teacher')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          &nbsp;&nbsp;<image src="${static_base}/images/action/new.gif">发送到教师
         </td>
       </tr>
       <tr>
         <td class="padding"  onclick="newMessage(this,'user')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
           &nbsp;&nbsp;<image src="${static_base}/images/action/new.gif">发送到管理账户
         </td>
       </tr>
       </#if>
	  </table>
	<td>
	<td valign="top">
     	<iframe name="messageListFrame" src="systemMessage.do?method=newly" width="100%" frameborder="0" scrolling="no">
	</td>
</table>
<#include "/templates/foot.ftl"/>