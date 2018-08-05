<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<#include "/pages/components/initAspectSelectData.ftl"/>
<table width="100%" class="frameTable"> 
	<tr>
		<td width="20%" class="frameTable_view" valign="top">
			<table width="100%" class="searchTable">
				<form name="listForm" method="post" target="thesisForm" action="" onSubmit="return false;">
				<#assign stdTypeName></#assign>
        <tr>
            <td align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>查询条件</B></td>
       </tr>
        <tr>
            <td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/></td>
        </tr>
	   <tr>
	   	  <td width="40%"><@msg.message key="attr.stdNo"/>：</td>
		  <td><input type="text" name="std.code" maxlength="32" size="10" style="width:100px"/></td>
	   </tr>
	   <tr>
	   	  <td><@msg.message key="attr.personName"/>：</td>
		  <td><input type="text" name="std.name" size="10" maxlength="20" style="width:100px"/></td>
	   </tr>
	   <tr>
		  <td><@msg.message key="entity.studentType"/>：</td>
		  <td align="bottom"  width="40%" >
			  <select id="std_stdTypeOfSpeciality" name="std.type.id" style="width:100px">
			  </select>
		  </td>
	 </tr>
	   <tr>
	   	  <td  width="30%"><@msg.message key="filed.enrollYearAndSequence"/>：</td>
		  <td ><input type="text" id="erollYear" maxlength="7" name="std.enrollYear" style="width:100px"/></td>
	   </tr>
	   <tr>
	   	  <td><@msg.message key="entity.college"/>：</td>
	      <td>
	     	<select id="std_department" name="department.id" style="width:100px;">
	        </select>
	      </td>
	   </tr>
	   <tr>
	   		<td><@msg.message key="entity.speciality"/>：</td>
		    <td ><select id="std_speciality" name="speciality.id" style="width:100px">
		         </select>
		    </td>
	   </tr>
	   <tr>
	   	  <td><@msg.message key="entity.specialityAspect"/>：</td>
		  <td ><select id="std_specialityAspect" name="specialityAspect.id" style="width:100px">
		       </select>
		  </td>
	   </tr>
	   <tr>
	   	  <td>是否确认：</td>
		  <td ><select name="thesis.affirm" style="width:100px">
		  			<option value="">全部</option>
		  			<option value="true">确认</option>
		  			<option value="false">未确认</option>
		       </select>
		  </td>
	   </tr>
	   <tr>
	   	  <td>专业类别：</td>
		  <td><select name="thesis.thesisManage.majorType.id" onchange ="changeSpecialityType(event);" style="width:100px">
		  			<option value="1">第一专业</option>
		  			<option value="2">第二专业</option>
		       </select>
		  </td>
	   </tr>
	   <tr height="50px">
	   <td colspan="2" align="center">
			<button onClick="search(1)" class="buttonStyle"><@msg.message key="system.button.query"/></button>&nbsp;
	   </td>
	   </tr>
			</form>
			</table>
		</td>
	    <td valign="top">
			<iframe name="thesisForm" marginwidth="0" marginheight="0" scrolling="no"
		     frameborder="0" height="100%" width="100%">
		  	</iframe>
		</td>
		</tr>
	</table>
	<script>
	   var bar = new ToolBar('backBar','学生论文',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addItem("导入","importData()");
	   bar.addItem("下载数据模板","downloadTemplate()",'download.gif');
	
		var form =document.listForm;
		var action ="thesisAdmin.do";
		function search(pageNo,pageSize,orderBy){
		    form.action=action+"?method=search";
		    goToPage(form,pageNo,pageSize,orderBy);
		}
	    search(1);
	    function downloadTemplate(){
	      self.location="dataTemplate.do?method=download&document.id=9";
	    }
	    function importData(){
	       form.action=action+"?method=importForm&templateDocumentId=9";
	       addInput(form,"importTitle","学生论文数据上传")
	       form.submit();
	    }
	    var sds = new StdTypeDepart3Select("std_stdTypeOfSpeciality","std_department","std_speciality","std_specialityAspect",true,true,true,true);
	    sds.init(stdTypeArray,departArray);
	    sds.firstSpeciality=1;
	    function changeSpecialityType(event){
	       var select = getEventTarget(event);
	       sds.firstSpeciality=select.value;
	       fireChange($("std_department"));
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>