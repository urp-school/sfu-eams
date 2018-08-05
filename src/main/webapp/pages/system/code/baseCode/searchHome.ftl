<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="baseCodeBar"></table>
   <table class="frameTable">
   <tr>
    <td  style="width:160px"class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td  valign="top">
        <iframe  src="#" 
        id="basecodeListFrame" name="basecodeListFrame" 
           marginwidth="0" marginheight="0"  scrolling="no"
              frameborder="0"  height="100%" width="100%">
        </iframe>
    </td>
   </tr>
  </table>
   
   <script language="javascript">
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
         addParamsInput(form,basecodeListFrame.queryStr);
         form.action=action+"?method=edit&baseCode.id=" +codeIds;
       	 form.submit();
       }
    }
   function exportData(){
      form.action=action+"?method=export";
      addInput(form,"keys","code,name,engName,createAt,modifyAt,state");
      addInput(form,"titles","代码,名称,英文名,创建日期,修改日期,状态");
      form.submit();
   }
   var bar = new ToolBar('baseCodeBar','基础代码查询',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.export"/>",exportData,"excel.png");
   bar.addBack("<@bean.message key="action.back"/>");    
  </script>
  </body>
<#include "/templates/foot.ftl"/>