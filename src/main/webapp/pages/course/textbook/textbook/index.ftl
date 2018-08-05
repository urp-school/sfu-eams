<#include "/templates/head.ftl"/>

<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="textbookBar"></table> 
   <table class="frameTable">
   <tr>
    <td style="width:160px" class="frameTable_view">
      <form name="searchForm" action="textbook.do?method=search" target="textbookListFrame" method="post" onsubmit="return false;">
      <#include "searchForm.ftl"/>
      </form>
    </td>
    <td valign="top">
	  <iframe  src="#" 
	     id="textbookListFrame" name="textbookListFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="100%" width="100%">
	  </iframe>   
    </td>
   </tr>
  </table>


  <script language="javascript">
    function getIds(){
       return(getRadioValue(textbookListFrame.document.getElementsByName("textbookId")));
    }
    var form = document.searchForm;
    var action="textbook.do";
    function search(pageNo,pageSize,orderBy){
       form.action=action+"?method=search";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    search();
    function add(){
       form.action=action+"?method=edit&textbookId=";
       form.submit();
    }
    function edit(){
       var ids = getIds();
       if(ids==""||isMultiId(ids)) {
         alert('请选择一个');
       } else {
         form.action= action + "?method=edit&textbookId=" + ids;
         form.submit();
       }
   }
    function remove() {
       var ids = getIds();
       if (ids=="" || isMultiId(ids)) {
         alert('请选择一个');
         return;
       }
       if (confirm("确定要删除吗？")) {
         form.action= action + "?method=remove&textbookId=" + ids;
         form.submit();
       }
   }
   function exportData(){
      addInput(form,"keys","code,name,auth,press.name,version,price,ISBN,description,remark,bookType.name,publishedOn,awardLevel.name");
      addInput(form,"titles","<@msg.message key="attr.code"/>,<@msg.message key="attr.name"/>,<@msg.message key="textbook.author"/>,<@msg.message key="entity.press"/>,<@msg.message key="textbook.version"/>,价格,ISBN,<@msg.message key="attr.description"/>,<@msg.message key="attr.remark"/>,类别,出版年月,<@msg.message key="entity.textbookAwardLevel"/>");
      form.action=action+"?method=export";
      form.submit();
   }
   function pressList(){
      window.open("press.do?method=index");
   }
   var bar = new ToolBar('textbookBar','<@msg.message key="textbook.management"/>',null,true,true);    
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.add"/>", "add()");
   bar.addItem("<@bean.message key="action.modify"/>", "edit()");
   bar.addItem("删除", "remove()");
   bar.addItem("<@bean.message key="action.export"/>", "exportData()");
   bar.addItem("<@msg.message key="entity.press"/>", "pressList()");
   bar.addHelp("<@msg.message key="action.help"/>");
  </script>
</body>
<#include "/templates/foot.ftl"/>