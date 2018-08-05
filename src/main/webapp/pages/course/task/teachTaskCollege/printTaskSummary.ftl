<#include "/templates/head.ftl"/>
<body>
	<table id="bar" width="100%"></table>
	<div id="PrintA">
	<table width="100%">
        <tr>
            <td colspan="10" align="center">
                <h2>${systemConfig.school.name}${calendar.year}${("(" + calendar.term + ")学期")?replace("学期)学期", "学期)")}教学任务汇总表</h2>
            </td>
        </tr>
	</table>
	<table id="teachTask" width="100%" class="listTable" style="text-align:center">
		<tr>
			<td width="15%" align="center"><@msg.message key="attr.teachDepart"/></td>
			<td width="10%" align="center"><@msg.message key="attr.courseNo"/></td>
			<td width="15%" align="center"><@msg.message key="course.titleName"/></td>
			<td width="10%" align="center">课程性质</td>
			<td align="center" width="3%"><@msg.message key="attr.credit"/></td>
			<td align="center" width="5%">上课年级</td>
			<td align="center">上课学院</td>
			<td align="center">上课专业（方向）</td>
			<td width="12%" align="center">教学班</td>
			<td width="5%" align="center">班级数</td>
			<td align="center">选课人数</td>
		</tr>
		<#assign departId = ""/>
		<#assign departSum = 0/>
		<#assign courseId = ""/>
		<#assign adminClassCount = 0/>
		<#assign adminClassSum = 0/>
		<#assign adminClassStdCount = 0/>
		<#assign adminClassStdSum = 0/>
		<#list tasks as task>
			<tr>
				<#assign departSum = departSum + 1/>
				<#assign departId = task.arrangeInfo.teachDepart.id/>
				<td><@i18nName task.arrangeInfo.teachDepart/></td>
				<td>${task.course.code}</td>
				<#assign courseId = task.course.id/>
				<td align="left"><@i18nName task.course/></td>
				<td><@i18nName (task.courseType)?if_exists/></td>
				<td>${(task.course.credits)?default(0)?string("0.##")}</td>
				<td>${(task.teachClass.enrollTurn)?if_exists}</td>
				<td><@i18nName (task.teachClass.depart)?if_exists/></td>
				<td><#assign aspectName><@i18nName (task.teachClass.aspect)?if_exists/></#assign>${(task.teachClass.speciality.name)?if_exists}<#if aspectName != "">(${aspectName})</#if></td>
				<td>${(task.teachClass.name?html)?replace(",", ", ")}</td>
				<#assign adminClassCount = (task.teachClass.adminClasses?size)?default(0)/>
				<#assign adminClassSum = adminClassSum + adminClassCount/>
				<td>${adminClassCount}</td>
				<#assign adminClassStdCount = (task.teachClass.planStdCount)?default(0)/>
				<#assign adminClassStdSum = adminClassStdSum + adminClassStdCount/>
				<td>${adminClassStdCount}</td>
			</tr>
			<#if tasks?size == task_index + 1 || tasks[task_index + 1].course.id != courseId>
				<tr style="font-weight:bold;color:blue">
					<td></td>
					<td>${task.course.code}&nbsp;总汇</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>${adminClassSum}</td>
					<td>${adminClassStdSum}</td>
					<#assign adminClassCount = 0/>
					<#assign adminClassSum = 0/>
					<#assign adminClassStdCount = 0/>
					<#assign adminClassStdSum = 0/>
				</tr>
			</#if>
			<#if tasks?size == task_index + 1 || tasks[task_index + 1].arrangeInfo.teachDepart.id != departId>
				<tr style="font-weight:bold;color:green">
					<td><@i18nName task.arrangeInfo.teachDepart/>&nbsp;计数</td>
					<td>${departSum}</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<#assign departSum = 0/>
				</tr>
			</#if>
		</#list>
	</table>
	<#list 1..5 as i><br></#list>
	<table width="100%">
		<tr>
			<td>备注：</td>
		</tr>
		<tr>
			<td>1.凡对排课有特殊要求，需经二级学院院长同意签名附情况说明，才算酌情考虑。<td>
		</tr>
		<tr>
			<td>2.教室要求指多媒体、机房等。<td>
		</tr>
		<tr>
			<td>3.申报表签名有效。<td>
		</tr>
	</table>
	</div>
    <script>
        var bar = new ToolBar("bar", "教学任务汇总表", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("以Excel形式查看", "AllAreaExcel()", "excel.png");
        bar.addPrint("<@msg.message key="action.print"/>");
        bar.addClose("<@msg.message key="action.close"/>");
        
        //指定页面区域内容导入Excel
        function AllAreaExcel(){
          var oXL= newActiveX("Excel.Application");
          if(null==oXL) {
            return;
          } 
          var oWB = oXL.Workbooks.Add(); 
          var oSheet = oWB.ActiveSheet;  
          var sel=document.body.createTextRange();
          sel.moveToElementText(PrintA);
          sel.select();
          sel.execCommand("Copy");
          oSheet.Paste();
          oXL.Visible = true;
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>