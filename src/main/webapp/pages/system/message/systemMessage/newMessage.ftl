<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script language="javascript" type="text/javascript" src="${static_base}/scripts/fckeditor/fckeditor.js"></script>
<script language="javascript" type="text/javascript" src="${static_base}/scripts/fckeditor/fckTextArea.js"></script>
<#assign textAreaId = "bodyMessage"/>
<body>
<table id="backBar"></table>
     <table width="100%">       
   		<tr>
   		<td valign="top" width="40%">
   		<table width="100%"  class="formTable">
   		<form name="listForm" method="post" action="">
   		   <input type="hidden" name="who" value="${RequestParameters['who']}">
		   <tr>
		     <td class="title" id="f_title">&nbsp;发送者</td><td  align="left">${sender.name}</td>
		   </tr>
		   <tr>
		   	 <td  class="title" id="f_receiptors">&nbsp;接收者</td>
		   	 <td >
		   	     <select id="showText" onchange="changeText();">
		   	        <option value="choice">手工选择账号</option>
		   	        <option value="input">手工输入账号</option>
		   	     </select>
		   	   <div id="show">
		   	  	<input id="receiptorIds" type="hidden" name="receiptorIds">
		   	  	<textarea id="receiptorNames" name="receiptorNames" rows="2" cols="60" readonly style="font-size:10pt"></textarea>
		   	     <button onclick="receiptorIds.value='';receiptorNames.value='';return false;">清空</button>
		   	   </div>  
		   	 </td>
		   </tr>
		   <tr>
		     <td class="title" id="f_title">&nbsp;消息标题</td><td  align="left"><input type="text" name="message.title" style="width:500px;"></td>
		   </tr>
		   <tr>
		   	  <td class="title">&nbsp;消息类别</td>
		   	  <td  align="left">
		   	   <select name="message.type.id" style="width:150px;">
		   	      <#list messageTypes as messageType>
		   	       <option value="${messageType.id}"><@i18nName messageType/></option>
		   	      </#list>
		   	   </select>
		   	  </td>
		   </tr>
		   <tr>
		     <td class="title" id="f_body">&nbsp;消息内容</td>
		     <td align="left"><textarea id="${textAreaId}" name="message.body" rows="10" cols="50"></textArea></td>
		   </tr>
		   <tr class="title">
     			<td id="f_time"  align="center" colspan="2">&nbsp;起效时间*
	            <input type="text" name="message.activeOn" maxlength="10" style="width:80px" value="${message.activeOn?string("yyyy-MM-dd")}" style="width:200px;" onfocus="calendar()">
	              失效时间*<input type="text" name="message.invalidateOn" maxlength="10" style="width:80px" value ="${message.invalidateOn?string("yyyy-MM-dd")}"style="width:200px;" onfocus="calendar()">
	   	  		</td>
		   </tr>
		   </table>
	   </form>
	  </td>
	  </tr>
	  <tr>
	  <td valign="top">
	  		<iframe name="displayFrame" src="systemMessage.do?method=${RequestParameters['who']}List" width="100%" frameborder="0" scrolling="no"></iframe>
	  </td>
	  </tr>
	  </table>
	  <form method="post" action="" name="actionForm"></form>
	<script>
	  	var bar = new ToolBar('backBar','发送消息',null,true,true);
	  	bar.setMessage('<@getMessage/>');
	  	bar.addItem("发送",'doAction()','send.gif');
	  	
	  	initFCK("${textAreaId}", "100%", "");
	  	
		function doAction(){
			var a_fields = {
	          'message.title':{'l':'消息标题', 'r':true, 't':'f_title'},
	          'receiptorIds':{'l':'接收者','r':true, 't':'f_receiptors'},
	          'message.activeOn':{'l':'起效时间','r':true, 't':'f_time'},
	          'message.invalidateOn':{'l':'失效时间','r':true, 't':'f_time'}
	     	};
	     var v = new validator(document.listForm, a_fields, null);
	     if (v.exec()) {
	     	if (FCKeditorAPI.GetInstance("${textAreaId}").GetXHTML(true) == "" ) {
	     		alert("消息内容没有填写。");
	     		return;
	     	}
	        document.listForm.action="systemMessage.do?method=sendTo";
	        document.listForm.submit();
	     }
		}
		function addValue(id,name){
			if(id!=""){
	   		    var inputIds = document.getElementById("receiptorIds");
				addInputValue(id,inputIds);
			}
			if(name!=""){
	   		   var inputNames = document.getElementById("receiptorNames");
				addInputValue(name,inputNames);
			}
		}
	    function removeValue(id,name){
			if(id!=""){
	   		    var inputIds = document.getElementById("receiptorIds");
				removeInputValue(id,inputIds);
			}
			if(name!=""){
	   		   var inputNames = document.getElementById("receiptorNames");
				removeInputValue(name,inputNames);
			}
	    }
		function removeInputValue(value,input){
		   if(input.value.indexOf(value)!=-1){
		      var index = input.value.indexOf(value);
		      input.value=input.value.substr(0,index)+input.value.substr(index+value.length+1);
		   }
		}
		function addInputValue(value,input){
			if(input.value.indexOf(value)==-1){
	           input.value+=value+",";
	        }
		}
		function cleanChoiceReceiptor(){
		    document.getElementById("receiptorIds").value='';
		    document.getElementById("receiptorNames").value='';
			return false;
		} 
		function cleanInputReceiptor(){
		    document.getElementById("receiptorIds").value='';
			return false;
		} 
		function changeText(){
		  var text=document.getElementById("showText").value;
		  var choiceText='<input id="receiptorIds" type="hidden" name="receiptorIds">';
		  choiceText+='<textarea id="receiptorNames" name="receiptorNames" rows="2" cols="60" readonly  style="font-size:10pt"></textarea>';
		  choiceText+='<button onclick="cleanChoiceReceiptor();">清空</button>';
		  var inputText='<textarea id="receiptorIds" name="receiptorIds" rows="2" cols="60"  style="font-size:10pt"></textarea>';
		  inputText+='<button onclick="cleanInputReceiptor();">清空</button>';
		  if(text=='choice'){
		    document.getElementById("show").innerHTML=choiceText;
		  }else{
		    document.getElementById("show").innerHTML=inputText;
		  }
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>