<#include "/templates/head.ftl"/>
<BODY >
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','统计结果信息',null,true,true);
   bar.setMessage('<@getMessage/>');
</script>
<table  width="100%" height="20" border="0" class="formTable">
	<form name="infoForm" method="post" action="" onsubmit="return false;">
  <tr>
  	<td align="center" heigh="25px;" colspan="2" class="title">统计完成信息</td>
  </tr>
  <tr>
  	<td class="title">成功信息</td>
    <td>
      <span class="contentTableTitleTextStyle">
      		<font color="green"><li>成功统计教学任务数目:${statisticTasks?default(0)?string}</li>
      		<li>成功统计教师工作量数目:${statisticSuccess?default(0)?string}</li></font>
      </span>
    </td>
  </tr>
  <tr>
  	<td class="title">失败信息</td>
    <td>
      <span class="contentTableTitleTextStyle">
      		<font color="red"><li>失败统计教师工作量数目:${(noArrangeInfo+noTeachModule)?default(0)?string}</li></font>
      		<li>失败具体原因:<#if noTeachModulus?exists>下列课程未找到教学工作量系数<br><font color="red">${noTeachModulus?if_exists}</font></#if><br><#if noArrangeInfos?exists>下列课程未找到教学任务里面课程安排情况:<br><font color="red">${noArrangeInfos?if_exists}</font></#if></li>
      </span>
    </td>
  </tr>
  <tr>
  	<td colspan="2">页面将在<span id="sec">30</span>后跳转到工作量查询页面<button name="button1" onclick="directManageWorkload(true)">立即跳转<br></button><button name="button1" onclick="cancel()">不要跳转</button></td>
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
			document.infoForm.action="teachWorkload.do?method=index";
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