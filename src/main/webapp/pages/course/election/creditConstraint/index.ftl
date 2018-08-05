<#include "/templates/head.ftl"/>
<script src='dwr/interface/specialityDAO.js'></script>
<script src='dwr/interface/specialityAspectDAO.js'></script>
<script src='dwr/interface/calendarDAO.js'></script>
<script src='scripts/common/CalendarSelect.js'></script>
<script src='scripts/stdTypeDepart3Select.js'></script>
<BODY>
<table id="creditBar"></table>
    <table class="frameTable_title" width="100%">
     <tr>
      <form name="creditForm" method="post" action="creditConstraint.do?method=index" onsubmit="return false;">
      <td class="infoTitle">
         学分分类:<select name="isMajorConstraint"  id="isMajorConstraint" onChange="changeCreditKind()" style="width:100px">
             <option value="0"><@bean.message key="entity.stdCreditConstraint"/></option>
             <option value="1"><@bean.message key="entity.specialityCreditConstraint"/></option>
         </select>
      </td>
      <#include "/pages/course/calendar.ftl"/>
      </form>
      </tr>
  </table>

  <table class="frameTable" height="85%">
    <tr>     
     <td class="frameTable_view" style="width:160px">
     <#include "searchForm.ftl">
     
     </td>
     <td class="frameTable_content">
	     <iframe src="#"
	     id="creditListFrame" name="creditListFrame"
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0" height="100%" width="100%">
	     </iframe>
     </td>
  </tr>
</table>
<script>
   	var bar = new ToolBar('creditBar','<@bean.message key="info.credit.management"/>',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	bar.addItem("选课学分初始化","goAction('initSpecialityCreditPrompt&calendar.id=${calendar.id}')",'update.gif');
   	bar.addItem("奖励学分标准","goAction('awardCriteriaList')",'update.gif');
    var stdTypeArray = new Array();
    var departArray = new Array();
    <#list departmentList as depart>
    departArray[${depart_index}]={'id':'${depart.id?if_exists}','name':'<@i18nName depart/>'};
    </#list>
    DWREngine.setAsync(false);
   <#list calendarStdTypes as stdType>
	   stdTypeArray[stdTypeArray.length]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
   </#list>
    var d1 = new StdTypeDepart3Select("stdType1","department1","speciality1","specialityAspect1",true,true,true,true);
    d1.init(stdTypeArray,departArray);
    var d2 = new StdTypeDepart3Select("stdType2","department2","speciality2","specialityAspect2",true,true,true,true);
    d2.init(stdTypeArray,departArray);
    DWREngine.setAsync(true);
    
   function changeCreditKind(){
        var form;
        var select = document.getElementById('isMajorConstraint');
        if(select.value=="0"){
           form=document.stdCreditForm;
        }else{
           form=document.majorCreditForm;
        }
        // 显示响应的选择输入form
        if(document.getElementById("view1").style.display=="block"&&select.value=="0"){
            document.getElementById("view1").style.display="none";
            document.getElementById("view0").style.display="block";
        }
        if(document.getElementById("view0").style.display=="block"&&select.value=="1"){
        	document.getElementById("view0").style.display="none";
        	document.getElementById("view1").style.display="block";
        }
	    search(form);
   }
   
   function search(form,pageNo,pageSize,orderBy){
        form.action="creditConstraint.do?method=creditConstraintList";
        form.target="creditListFrame";
        goToPage(form,pageNo,pageSize,orderBy);
   }
   
   function goAction(url){
      var location="creditConstraint.do?method="+url;
      location +="&calendar.studentType.id="+document.creditForm['calendar.studentType.id'].value;
      creditListFrame.location=location;
   }
   
   changeCreditKind();
 </script>
</body>
<#include "/templates/foot.ftl"/> 