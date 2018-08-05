<#include "/templates/head.ftl"/>
<BODY topmargin=0 leftmargin=0>	
  <table id="backBar" width="100%"></table>
	<script>
	   var bar = new ToolBar('backBar','学生科研成果管理',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addItem('统计学生科研信息','statistic()');
	   bar.addItem('导出单个学生科研情况表','lookFor()');
   </script>
	<table width="100%" class="frameTable"> 
		<tr>
			<td width="18%" class="frameTable_view" valign="top">
				<table width="100%">
					<form name="searchForm" method="post" target="displayFrame" onsubmit="return false;">
				    <tr>
				      <td colspan="2" class="infoTitle" align="left" valign="bottom">
				       <img src="${static_base}/images/action/info.gif" align="top"/>
				          <B>成果查询</B>
				      </td>
				    <tr>
				      <td colspan="2" style="font-size:0px">
				          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
				      </td>
				    </tr>
					<tr>
						<td width="40%"><@msg.message key="attr.stdNo"/>：</td>
						<td><input name="studyProduct.student.code" maxlength="32" value="" style="width:100px"/></td>
					</tr>
					<tr>
						<td><@msg.message key="attr.personName"/>：</td>
						<td><input name="studyProduct.student.name" maxlength="20" value="" style="width:100px"/></td>
					</tr>
					<tr>
						<td><@msg.message key="attr.enrollTurn"/>：</td>
						<td><input name="studyProduct.student.enrollYear" maxlength="7" value="" style="width:100px"/></td>
					</tr>
					<tr>
						<td>成果名称：</td>
						<td><input name="studyProduct.name" value="" style="width:100px" maxlength="20"/></td>
					</tr>
					<tr>
						<td><@msg.message key="entity.studentType"/>：</td>
						<td><@htm.i18nSelect datas=stdTypeList selected="" name="studyProduct.student.type.id" style="width:100px">
								<option value=""><@bean.message key="common.all"/></option>
							</@>
						</td>
					</tr>
					<tr>
						<td>院系部门：</td>
						<td><@htm.i18nSelect datas=departmentList selected="" name="studyProduct.student.department.id" style="width:100px">
						     <option value=""><@bean.message key="common.all"/></option>
						     </@>
						</td>
					</tr>
					<tr>
						<td>所属专业：</td>
						<td><@htm.i18nSelect datas=specialityList selected="" name="studyProduct.student.firstMajor.id" style="width:100px">
						     <option value=""><@bean.message key="common.all"/></option>
						     </@></td>
					</tr>
					<tr>
						<td>科研类别：</td>
						<td>
							<select name="productType" style="width:100px">
								<option value="studyThesis">论文</option>
								<option value="literature">著作</option>
								<option value="project">项目</option>
								<option value="studyMeeting">会议</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>是否获奖：</td>
						<td>
							<select name="isAwarded" style="width:100px">
								<option value="">全部</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>审核通过：</td>
						<td><@htm.select2 name="studyProduct.isPassCheck" hasAll=true selected="" style="width:100px"/></td>
					</tr>
					<tr height="50px">
						<td align="center" colspan="2">
							<input type="submit" onClick="search(1)" class="buttonStyle" value="<@bean.message key="system.button.query"/>"/>
						</td>
					</tr>
				</table>
				</form>
			</td>
			<td valign="top">
				<iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
			</td>
		</tr>
	</table>
	<script language="javascript">
		var form = document.searchForm;
		var action="studyProduct.do";
		function search(pageNo,pageSize,orderBy){
		    form.action=action+"?method=search";
		    goToPage(form,pageNo,pageSize,orderBy);
		}
	    search(1);
	    function statistic(){
	    	var info="";
	    	var enrollYear = form["studyProduct.student.enrollYear"].value;
	    	if(""==enrollYear&&!/^\d{4}\-\d/.test(enrollYear)){
	    		alert("所在年级必填,并且所在年级格式为yyyy-mm");
	    		return;
	    	}else{
	    		enrollYear = enrollYear.substring(0,enrollYear.indexOf("-"));
	    		addInput(form,"enrollYear",enrollYear);
	    		info+="\n所在年级为:"+form["studyProduct.student.enrollYear"].value;
	    	}
	    	if(""!=form["studyProduct.student.code"].value){
	    		info+="\n学生学号为:"+form["studyProduct.student.code"].value;
	    	}
	    	if(""!=form["studyProduct.student.name"].value){
	    		info+="\n学生姓名为:"+form["studyProduct.student.name"].value;
	    	}
	    	if(""!=form["studyProduct.student.type.id"].value){
	    		info+="\n学生类别为:"+DWRUtil.getText(form["studyProduct.student.type.id"]);
	    	}
	    	if(""!=form["studyProduct.student.department.id"].value){
	    		info+="\学生所在部门为:"+DWRUtil.getText(form["studyProduct.student.department.id"]);
	    	}
	    	if(""!=form["studyProduct.isPassCheck"].value){
	    		info+="\n审核结果为:"+DWRUtil.getText(form["studyProduct.isPassCheck"]);
	    	}
	    	info="你统计的条件是"+info+"\n你确定要统计吗?"
	    	if(confirm(info)){
	    		form.action=action+"?method=statisticProducts";
	    		form.submit();
	    	}
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>
