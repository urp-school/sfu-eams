<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body> 
<table id="bar"></table>
<table width="100%" align="center" class="formTable">
  <form name="conditionForm" method="post" action="" onsubmit="return false;">
    <input name="graduatePractice.calendar.id" type="hidden" value="${RequestParameters['graduatePractice.calendar.id']}"/>
    <input name="stdIds" type="hidden" value="${RequestParameters['stdIds']}"/>
	   <tr class="darkColumn" align="center">
	     <td colspan="4"><b>批量指定</b></td>
	   </tr>
	  <tr>
	     <td class="title" id="f_company"><font color="red">*</font>实习单位:</td>
	     <td><input type="text" name="graduatePractice.practiceCompany" maxlength="30" value=""/>           
	     </td>
	   </tr>
	   <tr>
	     <td class="title"  id="f_practiceSource"><font color="red">*</font>实习方式:</td>
	     <td><@htm.i18nSelect datas=practiceSources selected="${(graduatePractice.practiceSource.id)?default('')?string}" name="graduatePractice.practiceSource.id" style="width:300px;"><option value="">请选择...</option></@></td>
	   </tr>
	   <tr>
	     <td class="title" id="f_isBase"><font color="red">*</font>是否实习基地:</td>
	     <td><@htm.radio2 name="graduatePractice.isPractictBase" value=false/></td>
	   </tr>
	   <tr>
	     <td class="title" id="f_practiceDesc">实习描述：</td>
	     <td><textarea  name="graduatePractice.practiceDesc" colspan="40" rowspan="3" style="width:300px;"></textarea>
	     </td>
	   </tr>		   	   
	   <tr class="darkColumn" align="center">
	     <td colspan="2">
		   <button name="button1" onClick="doAction(this.form,null)" class="buttonStyle">保存</button>
	     </td>
	   </tr>
	   </form>
   </table>
 <script language="javascript" >
 	var bar = new ToolBar("bar", "填写批量实习信息(2)", null, true, true);
 	bar.addBack("<@msg.message key="action.back"/>");
     function doAction(form,isNext){
      var a_fields = {
         'graduatePractice.practiceCompany':{'l':'实习单位', 'r':true, 't':'f_company','mx':100},
         'graduatePractice.practiceSource.id':{'l':'实习方式', 'r':true, 't':'f_practiceSource'},
         'graduatePractice.isPractictBase':{'l':'是否实习基地', 'r':true, 't':'f_isBase'},
         'graduatePractice.practiceDesc':{'l':'实习描述', 'r':false, 't':'f_practiceDesc','mx':500}         
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
     	var url="graduatePractice.do?method=batchAdd";
     	if(null!=isNext){
     	   url+="&isNext=true";
     	}
        setSearchParams(parent.document.searchForm,form);
     	form.action=url;
        form.submit();
     } 
    }
 </script>
</body>
<#include "/templates/foot.ftl"/>