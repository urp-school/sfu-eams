<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body>
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','评教流程展示',null,true,true);
   bar.setMessage('<@getMessage/>');
</script>
	<div align="center"><h3>评教流程展示</h3></div>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <form name="commonForm" method="post" action="" onsubmit="return false;">
    <tr>
    <td>
     <table width="100%" align="center" class="listTable">
     	<tr align="center" class="darkColumn">
     		<td width="30%">操作步骤</td>
     		<td>备注说明</td>
     	</tr>
     	<tr>
     		<td><a href="evaluateButtonAction.do?method=doOpenAndClose"><font color="red">评教开关</font></a></td>
     		<td><b>评教开关</b>:指定完课程问卷以后,开放相关部门的评教,这里评教需要说明(开放的时候可以设置时间),达到预定的回收率以后,可以通过此功能关闭评教开关</td>
     	</tr>
     	<tr>
     		<td><a href="questionnaireRecycleRateAction.do?method=doLoadStatisticPage"><font color="red">统计回收率</font></a></td>
     		<td><b>统计回收率</b>:在评教开关开放以后查看学生的评教的具体的信息(统计完以后直接进入<a href="questionnaireRecycleRateAction.do?method=doQuery"><font color="red">查看回收率</font></a>页面,在查看页面上 点击部门进入具体的课程的回收率的统计页面)</td>
     	</tr>
     	<tr>
     		<td><a href="questionnaireStatisticAction.do?method=doQueryByStudentIdThisTerm"><font color="red">修改评教结果</font></a></td>
     		<td><b>修改评教结果</b>:在评教开关开放以后查看学生的评教的具体的信息</td>
     	</tr>
     	<tr>
     		<td><a href="questionnaireStatisticAction.do?method=doEvaluateStatistic"><font color="red">统计评教结果</font></a></td>
     		<td><b>统计评教结果</b>:在评教回收率达到一定的程度以后,统计最后的评教结果(统计成功以后页面直接跳到<a href="questionnaireStatisticAction.do?method=doDisplayQuery"><font color="red">查询评教结果</font></a>页面)</td>
     	</tr>
     	<tr align="center" class="darkColumn">
     		<td height="25px;" colspan="2">
     		</td>
     	</tr>
     </table>
    </td>
   </tr>
   <tr>
   		<td align="left">
   			注:红色的字体可以点击   		
   		</td>
   </tr>
  </form>
  </table>
</body>
<#include "/templates/foot.ftl"/>