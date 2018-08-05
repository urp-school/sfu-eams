<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body>
 <table id="bar"></table>
 <table width="90%" class="formTable" align="center" style="padding:0px;bolder-spacing:0px;">
	<form name="alterationForm" action="studentAlteration.do?method=edit" method="post" onsubmit="return false;">
	<tr> 
   	  <td align="center" class="darkColumn"><B>学籍变动详细信息</B></td>
  	</tr>
  	<tr>
  		<td style="text-align:center;padding:0px;bolder-spacing:0px">
			<table width="100%" class="formTable" align="center" style="border-bottom-width:0px">
				<tr>
					<td class="title" width="20%" id="f_stdId"><@msg.message key="attr.stdNo"/><font color="red">*</font></td>		
					<td width="30%" >
						<input name="stdNo" id="stdNo" type="text"/>
						<input type="button" onClick="save()" value="<@bean.message key="action.next" />" name="button1" class="buttonStyle" />
					</td>
				</tr>
			</table>
  		</td>
  	</tr>
   </form>
 </table>
<script>
  var bar=new ToolBar("bar","学籍变动查询",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addBack("<@msg.message key="action.back"/>");
  function save(){
    var stdNo=document.getElementById("stdNo").value;
    if(""==stdNo){
     alert("学号必须填写!");
    }else{
      alterationForm.submit();
    }
  }
</script>
</body>
<#include "/templates/foot.ftl"/>