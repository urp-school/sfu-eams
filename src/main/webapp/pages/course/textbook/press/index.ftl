<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="baseCodeBar"></table>
   <table class="frameTable">
   <tr>
    <td  style="width:160px"class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td  valign="top">
        <iframe  src="#" 
        id="contentFrame" name="contentFrame" 
           marginwidth="0" marginheight="0"  scrolling="no"
              frameborder="0"  height="100%" width="100%">
        </iframe>
    </td>
   </tr>
  </table>
   
   <script language="javascript">
    var form = document.pressForm;
    var action="press.do";
    function getIds(){
       return(getRadioValue(contentFrame.document.getElementsByName("pressId")));
    }
    function searchPress(pageNo,pageSize,orderBy){
       form.action=action+"?method=search";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    searchPress();
     
    function add(){
       addParamsInput(form,contentFrame.queryStr);
       form.action=action+"?method=edit";
       form.submit();
    }
    function edit(){
       var codeIds = getIds();
       if(codeIds=="")
       window.alert('<@bean.message key="common.select"/>!');
       else {
         if(isMultiId(codeIds)){alert("请仅选择一个");return;}
         addParamsInput(form,contentFrame.queryStr);
         form.action=action+"?method=edit&pressId=" +codeIds;
       	 form.submit();
       }
    }
  function exportData(){
    if(!confirm("是否导出查询条件内的所有数据?")) return;
    form.action=action+"?method=export";
    addInput(form,"keys","code,name,engName,createAt,modifyAt,state");
    addInput(form,"titles","代码,名称,英文名,创建日期,修改日期,状态");
    addParamsInput(form,contentFrame.queryStr);
    form.submit();
  }
   var bar = new ToolBar('baseCodeBar','出版社信息管理',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.add"/>",add,'new.gif');
   bar.addItem("<@bean.message key="action.modify"/>",edit,'update.gif');
   bar.addItem("<@bean.message key="action.export"/>",exportData,"excel.png");
   bar.addHelp("<@bean.message key="action.help"/>");
  </script>
  </body>
<#include "/templates/foot.ftl"/>