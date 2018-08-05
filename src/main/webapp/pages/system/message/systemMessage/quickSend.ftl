<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body>
<table id="backBar"></table>
   		<table width="100%"  class="formTable" >
   		<form name="listForm" method="post" action="" onsubmit="return false;">
		   <tr>
		   		<td  class="title" id="f_receiptors"><font color="red">*</font>发送人：</td>
		   	  	<td >${user.userName}</td>
		   </tr>
		   <tr>
		   		<td  class="title" id="f_receiptors"><font color="red">*</font>接收者</td>
		   	  	<td ><input id="receiptorIds" type="hidden" value="${RequestParameters['receiptorIds']}" name="receiptorIds">
		   	  	<#list recipients as recipient>${recipient.userName}&nbsp;</#list>
		   	  	</td>
		   </tr>
		   <tr>
		     <td class="title" id="f_title" width="20%"><font color="red">*</font>消息标题</td>
		     <td  align="left">
		      <input type="text" name="message.title" style="width:500px;" value="${message.title?default("")}" maxlength="100"></td>
		   </tr>
		   <tr>
		   	  <td class="title"><font color="red">*</font>消息类别</td>
		   	  <td  align="left">
		   	   <select name="message.type.id" style="width:150px;">
		   	      <#list messageTypes as messageType>
		   	       <option value="${messageType.id}" <#if messageType.id?string=message.type.id?string> selected</#if>><@i18nName messageType/></option>
		   	      </#list>
		   	   </select>
		   	  </td>
		   </tr>
		   <tr>
     			<td class="title" id="f_time"><font color="red">*</font>时间设置:</td>
	            <td>&nbsp;起效时间
	            <input type="text" name="message.activeOn"  style="width:80px" value="${message.activeOn?string("yyyy-MM-dd")}" style="width:200px;" onfocus="calendar()">
	              失效时间*<input type="text" name="message.invalidateOn"   style="width:80px" value ="${message.invalidateOn?string("yyyy-MM-dd")}"style="width:200px;" onfocus="calendar()">
	   	  		</td>
		   </tr>
		   <tr>
		     <td class="title" id="f_body" ><font color="red">*</font>消息内容</td>
		     <td  align="left">
		         <textarea name="message.body" rows="5" cols="60" style="font-size:10pt">${message.body?default("")}</textarea>
		     </td>
		   </tr>

		   </table>
	   </form>
	   <p>带有<font color="red">*</font>号的为必填项</p>
	<script>
	  	 var bar = new ToolBar('backBar','发送消息',null,true,true);
	  	 bar.setMessage('<@getMessage/>');
	  	 bar.addItem("发送",'doAction()','send.gif');
	  	 bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
	  	 
		function doAction(){
			var a_fields = {
	          'message.title':{'l':'消息标题', 'r':true, 't':'f_title'},
	          'message.body':{'l':'消息内容', 'r':true, 't':'f_body', 'mx':200},
	          'message.activeOn':{'l':'起效时间','r':true, 't':'f_time'},
	          'message.invalidateOn':{'l':'失效时间','r':true, 't':'f_time'}
	     	};
		     var v = new validator(document.listForm, a_fields, null);
		     if (v.exec()) {
		        document.listForm.action="systemMessage.do?method=sendTo&quickSend=true";
		        document.listForm.submit();
		     }
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>