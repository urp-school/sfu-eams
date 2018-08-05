<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<#assign labInfo><@bean.message key="info.courseTable.query"/></#assign>  
<#include "/templates/help.ftl"/> 
<script language="JavaScript" type="text/JavaScript" src="scripts/common/ieemu.js"></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script>
    <table class="frameTable_title">
     <tr>
      <td id="viewTD1" class="transfer">
      	  <font color="blue"><@bean.message key="action.advancedQuery" /></font>
      </td> 
      <td>|</td>  
      <form name="courseTableForm" method="post" action="?method=${RequestParameters['method']?default("index")}" onsubmit="return false;">
      <td class="infoTitle">
         <select id="courseTableType" name="courseTableType" onChange="searchResource()" style="width:120px">
             <option value="class"><@bean.message key="entity.courseTable.adminClass"/></option> 
             <option value="teacher"><@bean.message key="entity.courseTable.teacher"/></option>
             <option value="room"><@bean.message key="entity.courseTable.room"/></option>
             <option value="std"><@bean.message key="entity.courseTable.std"/></option>
         </select>
      </td>
      <#include "/pages/course/calendar.ftl"/>
      </tr>
   </form>
  </table>
 <#assign stdTypeList=calendarStdTypes/>
 <#include "/pages/components/initAspectSelectData.ftl"/>
 
  <table width="100%" class="frameTable">
    <tr>
     <td class="frameTable_view" style="width:17%">
     <#include "searchForm.ftl">
     </td>
     <td valign="top">
	     <iframe  src="#"
	     id="contentListFrame" name="contentListFrame"
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0" height="100%" width="100%">
	     </iframe>
     </td>
  </tr>
</table>
 <script>
 var viewMap = new Object();
 viewMap['class']="view1";
 viewMap['teacher']="view2";
 viewMap['room']="view3";
 viewMap['std']="view4";
 // 显示具体的查询窗口
 function displayView(divId){
     for(i=1;i<=4;i++){
       var viewDiv ="view"+i;
       var div = document.getElementById(viewDiv);
       if(divId==viewDiv)
          div.style.display = "block";
       else 
          div.style.display = "none";
     }
  }
  var searchForm=document.adminClassSearchForm;
  
  /**
   * 查找资源
   * 1)显示查找视图 2)进行默认条件查找
   */
  function searchResource(){
        var select = document.getElementById('courseTableType');
	    var courseTableForm =document.courseTableForm;
        displayView(viewMap[select.value]);
	    if(select.value=="class"){
	    	searchForm = document.adminClassSearchForm;
	    }
	    else if(select.value=="std"){
            searchForm = document.stdSearchForm;
        }else if(select.value=="teacher"){
        	searchForm = document.teacherSearchForm;
        }else if(select.value="room"){
        	searchForm = document.roomSearchForm;
        	addInput(searchForm,"classroom.schoolDistrict.id",$('district').value);
        	addInput(searchForm,"classroom.building.id",$('building').value);
        }else{
            alert("unsuported course table category!")
        }
	    search(1,null,"null");
   }

   function search(pageNo,pageSize,orderBy) {
        transferParams(document.courseTableForm,searchForm);
        searchForm.action="?method=list";
        searchForm.target="contentListFrame";
        goToPage(searchForm,pageNo,pageSize,orderBy);
   }
 </script>
 <#include "/templates/districtBuildingSelect.ftl"/>
 <script>
    searchResource();
 </script>
</body>
<#include "/templates/foot.ftl"/> 