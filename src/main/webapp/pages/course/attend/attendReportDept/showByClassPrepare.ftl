<#include "/templates/head.ftl"/>
<body>
<table id="bar"></table>
<table class="frameTable_title">
        <tr >
          <form name="actionForm" target="pageIFrame" method="post" action="?method=showByClassPrepare" onsubmit="return false;">
          <td style="width:35%;"></td>
          <#include "/pages/course/calendar.ftl"/>
          <td style="width:10%;"></td>
        </tr>
</table>
	<table class="frameTable" width="100%">
		<tr valign="top">
			<td class="frameTable_view" width="20%">
				<table width="100%">
					<tr class="infoTitle" style="text-valign:top;font-size:9pt;text-align:left;font-weight:bold">
						<td colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;详细查询(模糊输入)</td>
					</tr>
					<tr class="font-size:0pt">
						<td colspan="2"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="texttop"/></td>
					</tr>
					<#include "/pages/components/initAspectSelectData.ftl"/>
					<tr>
						<td width="40%"><@bean.message key="attr.enrollTurn"/>:</td>
						<td><input name="adminClass.enrollYear" type="text" value="" style="width:100px" maxlength="7"/></td>
					</tr>
					<tr>
						<td><@bean.message key="attr.name"/>:</td>
						<td><input name="adminClass.name" type="text" value="" style="width:100px" maxlength="20"/></td>
					</tr>
					<tr>
						<td><@bean.message key="entity.studentType"/>:</td>
						<td>
							<select name="adminClass.stdType.id" id="class_stdTypeOfSpeciality" style="width:100px;">
					         	<option value=""><@bean.message key="common.selectPlease"/></option>
					        </select>
						</td>
					</tr>
					<tr>
						<td><@bean.message key="entity.college"/>:</td>
						<td>
					        <select id="class_department" name="adminClass.department.id" style="width:100px;">
					           <option value=""><@bean.message key="common.selectPlease"/></option>
					        </select>
						</td>
					</tr>
					<tr>
						<td><@bean.message key="entity.speciality"/>:</td>
						<td>
					        <select id="class_speciality" name="adminClass.speciality.id" style="width:100px;">
				           		<option value=""><@bean.message key="common.selectPlease"/></option>
				        	</select>
						</td>
					</tr>
					<tr>
						<td><@bean.message key="entity.specialityAspect"/>:</td>
						<td>
					        <select id="class_specialityAspect" name="adminClass.aspect.id" style="width:100px;">
				         		<option value=""><@bean.message key="common.selectPlease"/></option>
				        	</select>
						</td>
					</tr>
					<tr>
						<td>专业类别:</td>
						<td>
					        <select name="majorTypeId" onchange="changeClassSpecialityType(event)" style="width:100px;">
						        <option value="1">第一专业</option>
						        <option value="2">第二专业</option>
				      		</select>
						</td>
					</tr>
					<tr>
						<td><@msg.message key="attr.state"/>:</td>
						<td>
							<select  name="adminClass.state" style="width:100px;">
						   		<option value="1"><@bean.message key="common.enabled"/></option>
						   		<option value="0"><@bean.message key="common.disabled" /></option>
					   		</select>
					   	</td>
					</tr>
				   	<tr height="50px">
				   		<td colspan="2" align="center">
				   			<button onclick="showByClass();"><@bean.message key="action.query"/></button>
				   			<button onclick="this.form.reset()"><@bean.message key="action.reset"/></button>
				   		</td>
				   	</tr>
				</table>
                <table><tr height="250px"><td></td></tr></table>
		    </td>
			</form>
			<td><iframe id="pageIFrame" name="pageIFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe></td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "班级信息查询", null, true, true);
		
		var classSelect = new StdTypeDepart3Select("class_stdTypeOfSpeciality","class_department","class_speciality","class_specialityAspect",true,true,true,true);
		classSelect.init(stdTypeArray,departArray);
		classSelect.firstSpeciality=1;
		function changeClassSpecialityType(event){
		       var select = getEventTarget(event);
		       classSelect.firstSpeciality=select.value;
		       fireChange($("class_department"));
    	}
		
		var form = document.actionForm;
		function showByClass() {
			form.action = "attendReportDept.do?method=showByClass";
			form.target = "pageIFrame";
			form.submit();
		}
    	showByClass();
    	
    	
    	
	</script>
</body>
<#include "/templates/foot.ftl"/>