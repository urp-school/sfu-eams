<#include "/templates/head.ftl"/>
<BODY >
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','统计指导工作量信息',null,true,true);
   bar.setMessage('<@getMessage/>');
</script>
<table  width="100%" height="20" class="listTable">
	<form name="infoForm" method="post" action="" onsubmit="return false;">
  <tr>
  	<td align="center" heigh="25px;" colspan="2" class="darkColumn">统计完成...</td>
  </tr>
  <tr>
  	<td colspan="2">页面将在<span id="sec">30</span>后跳转到工作量查询页面<button name="button1" onclick="directManageWorkload(true)">立即跳转</button><button name="button1" onclick="cancel()">不要跳转</button></td>
  </tr>
  </form>
</table>
<script>
	var flag=true;
	function changeSec(){
		var span = document.getElementById("sec");
		if(new Number(span.innerHTML)>0&&flag){
			span.innerHTML=new Number(span.innerHTML)-1;
		}
	}
	function directManageWorkload(value){
		if(value||flag){
			document.infoForm.action="instructWorkload.do?method=index";
			document.infoForm.submit();
		}
	}
	setInterval(changeSec, 1000);
	setTimeout(directManageWorkload, 30000);
	function cancel(){
		flag=false;
	}
</script>
</BODY>
<#include "/templates/foot.ftl"/>