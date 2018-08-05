<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="baseCodeBar"></table>
   <table class="frameTable">
   <tr valign="top">
    <td width="20%" class="frameTable_view">
        <#include "searchForm.ftl"/>
        <table><tr height="400px"><td></td></tr></table>
    </td>
    <td  valign="top">
        <iframe src="#" id="basecodeListFrame" name="basecodeListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
   
   <script language="javascript">
   var bar = new ToolBar('baseCodeBar','<@bean.message key="page.baseCodeListFrame.label" />',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.add"/>", "add()", 'new.gif');
   bar.addItem("<@bean.message key="action.modify"/>", "edit()", 'update.gif');
   var statusMenu = bar.addMenu("批量修改状态");
   statusMenu.addItem("有效", "batchUpdateState('1')");
   statusMenu.addItem("无效", "batchUpdateState('0')");
   bar.addItem("<@bean.message key="action.export"/>", "exportData()", "excel.png");
   
    var form = document.baseCodeSearchForm;
    var action="baseCode.do";
    function getIds(){
       return(getRadioValue(basecodeListFrame.document.getElementsByName("baseCodeId")));
    }
    function searchBaseCode(pageNo,pageSize,orderBy){
       form.action=action+"?method=search";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    searchBaseCode();
     
    function add(){
       addParamsInput(form,basecodeListFrame.queryStr);
       form.action=action+"?method=edit";
       form.submit();
    }
    
    function edit(){
       var codeIds = getIds();
       if(codeIds=="")
       window.alert('<@bean.message key="common.select"/>!');
       else {
         if(isMultiId(codeIds)){alert("请仅选择一个");return;}
          addParamsInput(form,"");
         addParamsInput(form,basecodeListFrame.queryStr);
         form.action=action+"?method=edit&baseCode.id=" +codeIds;
         form.submit();
       }
    }
    
    function batchUpdateState(status) {
    	var ids = getCheckBoxValue(basecodeListFrame.document.getElementsByName("baseCodeId"));
    	if (ids == null || ids == "") {
    		alert("请选择一个或多个进行操作。");
    		return;
    	}
    	form.action = "baseCode.do?method=batchUpdateState";
    	addInput(form, "ids", ids, "hidden");
    	addInput(form, "status", status, "hidden");
    	addParamsInput(form,basecodeListFrame.queryStr);
    	form.submit();
    }
    
   function exportData(){
      form.action=action+"?method=export";
      addInput(form,"keys","code,name,engName,createAt,modifyAt,state");
      addInput(form,"titles","代码,名称,英文名,创建日期,修改日期,状态");
      form.submit();
   }
  </script>
  </body>
<#include "/templates/foot.ftl"/>