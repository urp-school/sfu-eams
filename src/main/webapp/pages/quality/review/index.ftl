<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script> 
<script language="JavaScript" type="text/JavaScript" src="scripts/Selector.js"></script>
<script language="JavaScript" src="<@bean.message key="menu.js.url"/>"></script>
 <style  type="text/css">
</style>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="teachCheckDetailBar"></table>
<table align="center" class="formTable">
<tr class="darkColumn" align="center" >
<td width="10%"><input type="checkbox" name="checkAll" onclick="toggleCheckBox(document.getElementsByName('teachCheckListId'),event);"></input></td>
<td width="10%">序号</td>
<td width="10%">学风</td>
<td width="10%">教风</td>
<td width="10%">课程建设</td>
<td width="10%">教学文档</td>
<td width="10%">毕业论文情况</td>
<td width="10%">教学大纲</td>
<td width="10%">其他</td>
<td width="10%"></td>
</tr>
<#list teachCheckDetailList as teachCheckList>
  	  <#if teachCheckList_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if teachCheckList_index%2==0 ><#assign class="brightStyle" ></#if>
      <tr class="${class}" align="center" onmouseover="swapOverTR(this,this.className)"
		onmouseout="swapOutTR(this)" onclick="onRowChange()">
	  <td class="select"><input type="checkbox" value="${teachCheckList.id}" name="teachCheckListId"></td>	
      <td width="8%">${teachCheckList_index+1}</td>
      <td width="10%">${teachCheckList.studyStyle?if_exists}</td>
      <td width="10%">${teachCheckList.teachStyle?if_exists}</td>
      <td width="20%">${teachCheckList.courseBuild?if_exists}</td>
      <td width="10%">${teachCheckList.teachDoc?if_exists}</td>
      <td width="10%">${teachCheckList.graduteDoc?if_exists}</td>
      <td width="8%">${teachCheckList.teachOutline?if_exists}</td>
      <td width="8%">${teachCheckList.other?if_exists}</td>
      </tr>
</#list>
<#include "/templates/newPageBar.ftl"/>
</table>
<script language="javascript">
 var bar=new ToolBar('teachCheckDetailBar','教学情况检查列表',null,true,true);
 bar.setMessage('<@getMessage/>');
 bar.addItem("<@msg.message key="action.new"/>","add()");
 bar.addItem("<@msg.message key="action.edit"/>","edit()");
 bar.addItem("<@msg.message key="action.delete"/>","remove()");
 bar.addBack("<@bean.message key="action.back"/>");
 function add(){
 	self.location="teachReview.do?method=add";
 }
 function remove(){
 	 var id=getSelectIds("teachCheckListId");
 	 if(id==""){alert("请选择要删除的记录");return;}
 	 else{
 	 if(window.confirm("确定要删除吗?"))
 	 self.location="teachReview.do?method=remove&teachCheck.id="+id;
 	 }
 }
 function edit(){
 var id = getSelectIds("teachCheckListId");
 if(id==""){alert("请选择一条记录修改");return;}
 else if(isMultiId(id)){
    alert("请选择一条记录修改");return;
 }else
    self.location="teachReview.do?method=edit&teachCheckListId.id="+id;
 }
</script>
 <#include "/templates/foot.ftl"/>