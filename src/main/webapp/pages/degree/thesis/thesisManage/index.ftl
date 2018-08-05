<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','论文进度管理',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
<table width="100%" height="93%" class="frameTable">
   <tr>
   <td valign="top" class="frameTable_view" width="20%">
   	  <form name="listForm" method="post" target="displayNameFrame" action="" onsubmit="return false;">
      <table  width="100%" id ="viewTables" style="font-size:10pt">
      <tr>
	    <td colspan="2" class="infoTitle" align="left" valign="bottom">
            <img src="${static_base}/images/action/info.gif" align="top"/>
                 <B>查询条件</B>
           </td>
       </tr>
        <tr>
          <td colspan="2" style="font-size:0px">
              <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
          </td>
        </tr>
	   <tr>
	   	  <td style="width:40%"><@msg.message key="attr.stdNo"/>:</td>
		  <td ><input type="text" name="thesisManage.student.code" maxlength="32" size="10" style="width:100px"/>
		  </td>
	   </tr>
	   <tr>
	   	  <td><@msg.message key="attr.personName"/>:</td>
		  <td ><input type="text" name="thesisManage.student.name" maxlength="20" size="10" style="width:100px"/>
		  </td>
	   </tr>
	   <tr>
		  <td><@msg.message key="entity.studentType"/>:</td>
		  <td align="bottom">
			  <select id="stdTypeOfSpeciality" name="thesisManage.student.type.id" style="width:100px">
			    <option value=""><@msg.message key="common.selectPlease"/>.....</option>
			  </select>
		  </td>
	 </tr>
	   <tr>
	   	  <td  width="30%"><@msg.message key="filed.enrollYearAndSequence"/>：</td>
		  <td ><input type="text" id="erollYear" maxlength="7" name="thesisManage.student.enrollYear" style="width:100px"></td>
	   </tr>
	   <tr>
	   	  <td><@msg.message key="entity.college"/>:</td>
	      <td>
	     	<select id="department" name="thesisManage.student.department.id" style="width:100px;">
	         	<option value=""><@msg.message key="common.selectPlease"/>...</option>
	        </select>
	      </td>
	   </tr>
	   <tr>
	   		<td><@msg.message key="entity.speciality"/>:</td>
		    <td ><select id="speciality" name="thesisManage.student.firstMajor.id"  style="width:100px" >
		         	  <option value=""><@msg.message key="common.selectPlease"/>...</option>
		         </select>     
		    </td>
	   </tr>
	   <tr>
	   	  <td><@msg.message key="entity.specialityAspect"/>:</td>
		  <td ><select id="specialityAspect" name="thesisManage.student.firstAspect.id"  style="width:100px">
		         	  <option value=""><@msg.message key="common.selectPlease"/>...</option>
		       </select>     
		  </td>
	   </tr>
	    <#assign stdTypeNullable=true>
      	<#include "/templates/stdTypeDepart3Select.ftl"/>
      	<tr height="50px">
			<td colspan="2" align="center">
		    <button value="" onClick="search(1)" class="buttonStyle"><@msg.message key="system.button.query"/></button>
			</td>
		</tr>
      </table>
      </form>
   </td>
   <td valign="top">
   		<iframe name="displayNameFrame" width="100%" frameborder="0" scrolling="no"></iframe>
   </td>
  </tr>
</table>
<script>
	var form = document.listForm;
	function search(pageNo,pageSize,orderBy){
	   form.action="thesisManage.do?method=search";
	   goToPage(form,pageNo,pageSize,orderBy);
  }
  search(1);
</script>
</body>
<#include "/templates/foot.ftl"/>