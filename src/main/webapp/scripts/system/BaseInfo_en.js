  var form = document.searchForm;
  var bar=null;
  function getAction(){
     return type+".do";
  }
  function getIds(){
     return(getRadioValue(contentListFrame.document.getElementsByName(type+"Id")));
  }
  function search(pageNo,pageSize,orderBy){
     form.action=getAction()+"?method=search";
     goToPage(form,pageNo,pageSize,orderBy);
  }
  function add(){
     form.action=getAction()+"?method=edit";
     setSearchParams(form,form,null);
     form.submit();
  }
  function edit(){
     var baseInfoIds = getIds();
     if(baseInfoIds=="")
     window.alert('Please Select One Item!');
     else {
       if(isMultiId(baseInfoIds)){alert("Please Select Only One Item!");return;}
       setSearchParams(form,form,null);
       form.action=getAction()+"?method=edit&" + type + "Id=" + baseInfoIds;
       form.submit();
     }
  }
  function exportData(){
    if(!confirm("Do you want to export ALL datas in query?")) return;
    form.action=getAction()+"?method=export";
    addInput(form,"keys",keys);
    addInput(form,"titles",titles);
    form.submit();
  }
  function remove(){
     var baseInfoIds = getIds();
     if(baseInfoIds=="")
     window.alert('Please Select One Item!');
     else {
      if(isMultiId(baseInfoIds)){alert("Please Select Only One Item!");return;}
      if(!confirm("Confirm Delete Operation?")){return;}
       setSearchParams(form,form,null);
       form.action=getAction()+"?method=remove&type=" + type + "&id=" + baseInfoIds;
       form.submit();
     }
  }
  function initBaseInfoBar(){
    bar = new ToolBar('myBar',labelInfo,null,true,true);
    if(typeof initToolBarHook=="function"){
       initToolBarHook(bar);
    }
    bar.addItem("New",add,'new.gif');
    bar.addItem("Edit",edit,'update.gif');
    bar.addItem("Export",exportData,"excel.png");
    bar.addItem("Delete","remove()","delete.gif");
    bar.addBack("Back");
  }
