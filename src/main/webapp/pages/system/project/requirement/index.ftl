<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="requireBar"></table>
  <table class="frameTable">
   <tr>
    <td style="width:160px" class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td valign="top">
	 <iframe  src="requirement.do?method=search" 
	     id="requireListFrame" name="requireListFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="100%" width="100%">
	  </iframe> 
    </td>
   </tr>
  </table>
  <script language="javascript">
   	var bar = new ToolBar('requireBar','系统需求管理',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	var barMenu = bar.addMenu("<@bean.message key="action.print"/>","printRequires()","print.gif");
   	barMenu.addItem("<@bean.message key="action.preview"/>(全部)","previewRequires()","print.gif");
   	bar.addItem("工作量",workload);
   	bar.addItem("<@bean.message key="action.add"/>","add()",'new.gif');
   	bar.addItem("<@bean.message key="action.modify"/>","edit()",'update.gif');
   	bar.addItem("<@bean.message key="action.delete"/>","remove()",'delete.gif');
   	bar.addBack("<@bean.message key="action.back"/>");
   
    function getIds(){
       return(getRadioValue(requireListFrame.document.getElementsByName("requireId")));
    }
    function setParams(form){
       form['params'].value="";
       var params = getInputParams(form,null);
       form['params'].value=params;
    }
    function pageGoWithSize(pageNo,pageSize,orderBy){
       var form = document.requireSearchForm;
       form.action="requirement.do?method=search";
       form.target = "requireListFrame";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    function goFirstPage(){
      pageGoWithSize(1);
    }
    function add(){
       var form = document.requireSearchForm;
       setParams(form);
       form.action="requirement.do?method=edit";
       form.target = "requireListFrame";
       form.submit();
    }
    function edit(){
       var requirementIds = getIds();
       if(requirementIds==""){
          window.alert('<@bean.message key="common.select"/>!');
          return;
       }
       if(isMultiId(requirementIds)){alert("请仅选择一个");return;}
       else {
         var form = document.requireSearchForm;
         setParams(form);
         form.action="requirement.do?method=edit&requirement.id=" +requirementIds;
       	 form.target = "requireListFrame";
       	 form.submit();
       }
    }
   	function remove(){ 
       var requirementIds = getIds();
       if(requirementIds=="")
       window.alert('<@bean.message key="common.select"/>!');
       if(confirm("确定删除?")){
         var form = document.requireSearchForm;
         setParams(form);
         form.action="requirement.do?method=remove&requireIds=" +requirementIds;
       	 form.target = "requireListFrame";
       	 form.submit();
       }
   	}
   	function exportData(){
        var form = document.requireSearchForm;
        addInput(form,"keys","module,content,fromUser,developers,priority,status,type,background,workload,planCompleteOn");
        addInput(form,"titles","模块,内容,建议人,开发者,优先级,状态,类型,背景,工作量,计划完成时间");
        form.action="requirement.do?method=export";
       	form.target = "requireListFrame";
        form.submit();
   	}
   	function workload(){
       var requirementIds = getIds();
       if(requirementIds==""){
	       window.alert('<@bean.message key="common.select"/>!');
	       return;
       }
       var form = document.requireSearchForm;
       form.action="requirement.do?method=workload&requireIds=" +requirementIds;
       form.target = "requireListFrame";
       form.submit();
   	}
   	function printRequires(){
       var requirementIds = getIds();
       if(requirementIds==""){
	       if(!confirm("没有选择具体需求,是否预览打印查询条件内的需求?"))return;
       }
       var form = document.requireSearchForm;
       form.action="requirement.do?method=report&requireIds=" +requirementIds;
       form.target = "requireListFrame";
       form.submit();
   	}
   	function previewRequires() {
       	var form = document.requireSearchForm;
       	form.action="requirement.do?method=preview";
       	form.target = "_blank";
       	form.submit();
   	}
  </script>  
  </body>
<#include "/templates/foot.ftl"/>