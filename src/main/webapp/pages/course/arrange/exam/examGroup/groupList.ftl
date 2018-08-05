<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table  width="100%" id="groupListBar"></table>
 <@table.table width="100%" id="groupListTable">
   <@table.thead>
      <@table.selectAllTd id="groupId"/>
      <td width="40%"><@bean.message key="attr.groupName" /></td>
      <td width="20%">任务数</td>
      <td width="20%">是否发布</td>
      </@>
    <@table.tbody datas=groups;group>
      <@table.selectTd id="groupId" value=group[0]/>
      <td class="padding"><A href="#" onclick="info(${group[0]})">${group[1]}</A></td>
      <td class="padding">${group[2]}</td>
      <td class="padding">${group[3]?default(false)?string("已发布","未发布")}</td>
    </@>
 </@>
    <form name="taskGroupListForm"  method="post" action="" onsubmit="return false;">
        <input type="hidden" name="calendar.id" value=""/>
    </form>
<script>
    var bar=new ToolBar("groupListBar","排考组列表",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("查看任务","info()", "detail.gif");
    bar.addItem("<@msg.message key="action.new"/>","newGroup()");
    bar.addItem("<@msg.message key="action.delete"/>","removeGroup()");
    bar.addItem("发布考试结果","publish(1)");
    bar.addItem("取消发布考试结果","publish(0)");
    bar.addHelp("<@msg.message key="action.help"/>");
    
    var form =parent.document.examGroupForm;
    function info(groupId){
	    if (null == groupId || "" == groupId) {
	      var groupIds = getSelectIds("groupId");
	      if ("" == groupIds) {
	        alert("请选择排考组");
	        return;
	      }
	      if (isMultiId(groupIds)) {
	      	alert("请仅选择一个排考组");
	      	return;
	      }
	      form.action="examGroup.do?method=info&examGroup.id="+groupIds;
	      form.submit();
	    } else {
	      form.action="examGroup.do?method=info&examGroup.id="+groupId;
	      form.submit();
	    }
    }
    
    function newGroup(){
       form.action="examGroup.do?method=edit";
       form.submit();
    }
    function removeGroup(){
       var form = parent.document.examGroupForm;
       var groupIds = getSelectIds("groupId");
       if(""==groupIds){
          alert("请选择排考组,进行删除");
          return;
       }
       if(confirm("确定删除?")){
         form.action="examGroup.do?method=remove&examGroupIds="+groupIds;
         form.submit();
       }
    }
    function publish(status){
       var form = parent.document.examGroupForm;
       var groupIds = getSelectIds("groupId");
       if ("" == groupIds) {
          alert("请选择排考组,进行发布排考结果");
          return;
       }
       if (confirm("确定发布?")) {
         form.action = "examGroup.do?method=publish&examGroupIds=" + groupIds + "&status=" + status;
         form.submit();
       }
    }
</script>
</body>
<#include "/templates/foot.ftl"/> 