<#include "/templates/head.ftl"/>
<body>
	<table id="myBar" width="100%" ></table>
	<table width="85%" align="center">
		<tr>
			<td>
				<table class="infoTable">
					<tr>
						<th colspan="5"><@msg.message key="info.studentRecordBasicInfo"/></th>
					</tr>
				</table>
				<#include "info/stdDetail/stdInfoContents.ftl"/>
				<#if std.studentStatusInfo?exists>
				<table  class="infoTable">
					<tr>
						<th colspan="5"><@msg.message key="std.detail"/></th>
					</tr>
				</table>
				  <#include "info/stdDetail/statusInfoContents.ftl"/>
                </#if>
				<table class="infoTable">
					<tr>
						<th colspan="4"><@msg.message key="info.studentBasicInfo"/></th>
					</tr>
				</table>
				<#include "info/stdDetail/basicInfoContents.ftl"/>
				<#if std.abroadStudentInfo?exists>
					<table class="infoTable">
						<tr>
							<th colspan="4">留学信息</th>
						</tr>
					</table>
					<#include "info/stdDetail/abroadInfoContents.ftl"/>
				</#if>
			</td>
		</tr>
	</table>
	<br><br>
	<script>
	    var bar =new ToolBar("myBar","信息概况",null,true,true);
	    bar.addPrint("<@msg.message key="action.print"/>");
	    bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
	</script>
<body>
<#include "/templates/foot.ftl"/>