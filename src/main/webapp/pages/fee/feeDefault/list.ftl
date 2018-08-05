<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 	<script language="javascript">
    function getIds(){
       return getSelectIds("feeDefaultId");
    }
    function edit(){
    	var ids = getIds();
    	if(ids!=""&&ids.indexOf(",")==-1){
    		document.listForm.action ="feeDefault.do?method=edit&feeDefault.id="+ids;
    		document.listForm.target = "";
    		document.listForm.submit();
    	}else{
    		alert("<@bean.message key="field.evaluate.selectSingle"/>");
    		return;
    	}
    }
	function remove(){
    	var ids = getIds();
    	if(ids==""){
    		alert("<@bean.message key="field.evaluate.selectOne"/>");
    	}
    	else{
    	    if(!confirm("确定删除选定的记录吗?"))return;
    		document.listForm.action ="feeDefault.do?method=remove&feeDefaultIds="+ids;
    		document.listForm.target = "";
    		document.listForm.submit();
    	}
    }
    function add(){
      self.location="feeDefault.do?method=edit";
    }
    
    function print() {
        var form = document.listForm;
        form.action = "feeDefault.do?method=printReview";
        form.target = "_blank";
        form.submit();
    }
    </script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="feeDefaultBar"></table>
  <script>
   var bar = new ToolBar("feeDefaultBar",'配置收费默认值',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.add"/>","add()");
   bar.addItem("<@msg.message key="action.edit"/>","edit()");
   bar.addItem("<@msg.message key="action.delete"/>","remove()");
   bar.addItem("打印预览", "print()");
  </script>
    <@table.table width="100%" sortable="true" id="listTable">
	   <@table.thead>
	     <@table.selectAllTd id="feeDefaultId"/>
	     <@table.sortTd name="entity.studentType" id="default.studentType.name"/>
	     <@table.td name="entity.department" />
	     <@table.td name="entity.speciality" />
	     <@table.td text="收费类别"/>
	     <@table.td text="默认金额"/>
	     <@table.td text="备注"/>
	   </@>
	   <@table.tbody datas=feeDefaults;feeDefault>
	     <@table.selectTd id="feeDefaultId" value=feeDefault.id/>
	     <td>&nbsp;${feeDefault.studentType?if_exists.name?if_exists}</td>
	     <td>&nbsp;<@i18nName feeDefault.department/></td>
	     <td>&nbsp;<@i18nName feeDefault.speciality?if_exists/></td>
	     <td>&nbsp;<@i18nName feeDefault.type/></td>
	     <td>&nbsp;${feeDefault.value?if_exists}</td>
	     <td>&nbsp;${feeDefault.remark?if_exists}</td>
	   </@>
	 </@>
  </table>
  <form name="listForm" method="post"></form>
  </body>
<#include "/templates/foot.ftl"/>