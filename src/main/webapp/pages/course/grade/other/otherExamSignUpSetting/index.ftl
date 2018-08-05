<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar"></table>
  <table class="frameTable">
   <tr>
    <td style="width:160px" class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td valign="top">
	 <iframe src="#" id="contentListFrame" name="contentListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
    </td>
   </tr>
  </table>
  <script language="javascript">
    var form=document.searchForm;
    var action="otherExamSignUpSetting.do";
    var bar = new ToolBar("myBar","校内外考试报名设置",null,true,true);
    bar.addItem("<@msg.message key="action.add"/>","add()");
    bar.addItem("<@msg.message key="action.edit"/>","edit()");
    bar.addItem("<@msg.message key="action.delete"/>","remove()");
    bar.addHelp("<@msg.message key="action.help"/>");
    
    function getIds(){
      return getRadioValue(contentListFrame.document.getElementsByName("otherExamSignUpSettingId"));
    }
    function add(){
       form.action=action+"?method=edit";
       addParamsInput(form,contentListFrame.queryStr);
       form.submit();
    }
    function edit(){
       var ids= getIds();
       if (ids == "" || isMultiId(ids)) {
       	alert("请选择一个");
       	return;
       }
       form.action = action + "?method=edit&otherExamSignUpSettingId=" + ids;
       addParamsInput(form,contentListFrame.queryStr);
       form.submit();
    }
    function remove(){
       var ids= getIds();
       if (ids == "") {
       	alert("请选择一个");
       	return;
       }
       if(confirm("确认删除选定的设置吗？")){
           addParamsInput(form,contentListFrame.queryStr);
	       form.action=action+"?method=remove&otherExamSignUpSettingIds="+ids;
	       form.submit();
       }
    }
    function searchSetting(pageNo,pageSize,orderBy){
        form.action=action+"?method=search";
        goToPage(form,pageNo,pageSize,orderBy);
    }
    searchSetting();
  </script>
</body>
<#include "/templates/foot.ftl"/>