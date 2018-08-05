  /**
   * 基础信息管理的辅助js.
   * 每一个基础信息管理类，需要在引入该脚本之前定义
   * 1.type 什么基础类型
   * 2.defaultOrderBy
   * 3.keys导出的属性
   * 4.titles导出列的标题
   */
  var form = document.searchForm;
  var bar=null;
  function getAction(){
     return type+".do";
  }
  function getIds(){
     return(getCheckBoxValue(contentListFrame.document.getElementsByName(type+"Id")));
  }
  function search(pageNo,pageSize,orderBy){
     form.action=getAction()+"?method=search";
     goToPage(form,pageNo,pageSize,orderBy);
  }
  function add(){
     form.action=getAction()+"?method=edit";
     addParamsInput(form,contentListFrame.queryStr);
     form.submit();
  }
  function edit(){
     var baseInfoIds = getIds();
     if(baseInfoIds=="")
     window.alert('请选择一个数据');
     else {
       if(isMultiId(baseInfoIds)){alert("请仅选择一个数据");return;}
       addParamsInput(form,contentListFrame.queryStr);
       form.action=getAction()+"?method=edit&" + type + "Id=" + baseInfoIds;
       form.submit();
     }
  }
  function exportData(format){
    if(null==format){
      format="excel";
    }
    if(!confirm("是否导出查询条件内的所有数据?")) return;
    form.action=getAction()+"?method=export";
    addInput(form,"keys",keys);
    addInput(form,"titles",titles);
    addInput(form,"format",format);
    form.submit();
  }
  function remove(){
     var baseInfoIds = getIds();
     if(baseInfoIds=="")
     window.alert('请选择一个数据');
     else {
      //if(isMultiId(baseInfoIds)){alert("请仅选择一个数据");return;}
      if(!confirm("确认删除操作?")){return;}
       addParamsInput(form,contentListFrame.queryStr);
       form.action=getAction()+"?method=remove&type=" + type + "&id=" + baseInfoIds;
       form.submit();
     }
  }
  function importAttendDevice(){
      form.action="?method=importForm&templateDocumentId=26";
	   addInput(form,"importTitle","考勤监控信息上传");
	   form.submit();
   }
  function initBaseInfoBar2(){
    bar = new ToolBar('myBar',labelInfo,null,true,true);
    if(typeof initToolBarHook=="function"){
       initToolBarHook(bar);
    }
    bar.addItem("添加",add,'new.gif');
    bar.addItem("修改",edit,'update.gif');
    bar.addItem("删除","remove()","delete.gif");
    var mm=bar.addMenu("导出","exportData('excel')","excel.png");
    mm.addItem("文本导出","exportData('txt')","excel.png");
    bar.addItem('导入', 'importAttendDevice()');
  }
