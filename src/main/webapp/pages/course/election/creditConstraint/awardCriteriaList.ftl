<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<table id="creditAwardBar"></table>
	<table  width="100%" border="0" class="listTable" id="criteriaTable">
	<form name="awardCreditCriteriaForm" method="post" action="" onsubmit="return false;">
 	 <tr class="darkColumn" align="center">
      <td align="center" width="3%">
        <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('criteriaId'),event);"/>
      </td>
      <td width="6%"><@bean.message key="attr.index" /></td>
      <td width="10%"><@bean.message key="attr.floorAvgGrade"/>(包含)</td>
      <td width="10%"><@bean.message key="attr.ceilAvgGrade"/>(不包含)</td>
      <td width="10%"><@bean.message key="attr.awardCredit"/></td>
      </tr>
	 <#list creditAwardCriteriaList?sort_by("floorAvgGrade") as criteria>
	 <tr class="grayStyle">
	     <td width="2%" class="select">
	        <input type="checkBox" name="criteriaId" value="${criteria.id}">
	     </td>
	     <td>${criteria.id}</td>
		 <td>${criteria.floorAvgGrade?string("##.###")}</td> 
		 <td>${criteria.ceilAvgGrade?string("###.###")}</td> 
         <td>${criteria.awardCredits?string("##.###")}</td>
	 </tr>
     </#list>    
    </table>
    </form>
 <script>
    var newOrmodifies=0;
    var deletedIds="";
    var critriaAttrs = new Array();
    critriaAttrs[0]="id";
    critriaAttrs[1]="floorAvgGrade";
    critriaAttrs[2]="ceilAvgGrade";
    critriaAttrs[3]="awardCredits";
    
    // 新创建一个奖励标准
	function newCriteria(){
	    var tb = criteriaTable;
    	newOrmodifies++;
	    var tr = document.createElement('tr');
	    var cellsNum = tb.rows[0].cells.length;
	    for(var j = 0 ; j < cellsNum ; j++){
	        var td = document.createElement('td');
	        if(j==0){
		        td.innerHTML='<input type="checkBox" name="criteriaId" value=""/>'
		        td.className="select";
		    } else if(j!=1) {
		       td.innerHTML ="<input type='text' style='width:100%' name='creditAwardCriteria'"+ newOrmodifies +"." + critriaAttrs[j-1] + "/>";
		    }
	        tr.appendChild(td);
	    }
	    tr.className="grayStyle";
	    tr.align="center";
	    tb.tBodies[0].appendChild(tr);
	    f_frameStyleResize(self);
	}
	//删除具体某表的选中的行（该表第一列是复选框）
    function deleteCriteria(){
		var Ids = getCheckBoxValue(document.getElementsByName("criteriaId"));
		if(Ids!=""&&!confirm("<@bean.message key="prompt.deletion"/>"))return;
		var count=0;
		var rows= criteriaTable.rows;
		// 跳过标题
		for(var i=1;i<rows.length;i++){
		   if(rows[i].firstChild.firstChild.checked){
		       if(rows[i].childNodes[1].innerHTML!="") {
		           if(rows[i].childNodes[1].innerHTML.indexOf(".")==-1){
		               deletedIds+=","+ rows[i].childNodes[1].innerHTML;
		           }
		           else{
		             deletedIds+=","+ rows[i].childNodes[1].firstChild.value;
		           }
		       }
		       else newOrmodifies--;
		       criteriaTable.tBodies[0].removeChild(rows[i--]);
		       count++;
		   }
		}
		if(count==0) {alert("<@bean.message key="common.selectPlease"/>");return;}
		else{
		  if(confirm("<@bean.message key="prompt.saveDeletion"/>"))saveCriteria();
		}
		//alert("newOrmodifies:" + newOrmodifies);
		f_frameStyleResize(self);
	}
	/**
	 * 更新已有的学生范围（包括学号段），新建的不再响应之列
	 */
	function updateCriteria(){
		var rows =criteriaTable.tBodies[0].childNodes;
		var origCount = newOrmodifies;
		rows =criteriaTable.tBodies[0].childNodes;
		cols = criteriaTable.rows[0].cells.length;		
		for(var i=1;i<rows.length;i++){
		    // 对尚未修改的记录进行更换面目
			if(rows[i].childNodes[0].firstChild.checked&&rows[i].childNodes[1].innerHTML!=""&&rows[i].childNodes[1].innerHTML.indexOf(".")==-1){
			    newOrmodifies++; 
			    rows[i].childNodes[1].innerHTML ="<input type='hidden' value='" + rows[i].childNodes[1].innerHTML
			             +"' name='creditAwardCriteria"+newOrmodifies +".id'/>" + rows[i].childNodes[1].innerHTML;
			    for(var j=2;j<cols;j++){
			    	rows[i].childNodes[j].innerHTML="<input type='text'  value= '" + rows[i].childNodes[j].innerHTML
			    	+"' name='creditAwardCriteria"+newOrmodifies +"." +critriaAttrs[j-1] +"'/>";
			    }
			    //alert(rows[i].outerHTML);
			}
		}
		if(origCount == newOrmodifies) alert("<@bean.message key="info.selectSaved"/>");
	}
	
	// 保存既有的设置（新增的和修改的）
	function saveCriteria(){
	    if(newOrmodifies==0&&deletedIds=="") {
	    	alert("<@bean.message key="info.saveNotNeeded"/>");
	    } else {
	        errorStr=check();
	        if (errorStr != "") {
	        	alert(errorStr);
	        	return;
	        }
	    	var form = document.awardCreditCriteriaForm;
	    	form.action="creditConstraint.do?method=saveAwardCriteria"
	    	form.action+="&newOrModifies=" + newOrmodifies + "&deletedIds=" + deletedIds;
	    	form.submit();
	    }
	}
	
	function check(){
	   var rows =criteriaTable.tBodies[0].childNodes;
       rows =criteriaTable.tBodies[0].childNodes;
	   cols = criteriaTable.rows[0].cells.length;
	   var errorStr="";	
	   var awards = new Array();
       <#assign row>i</#assign>
	   for(var i=1;i<rows.length;i++){
	       //如果该单元格没有被修改
	       if(rows[i].childNodes[1].innerHTML=="" || rows[i].childNodes[1].innerHTML.indexOf(".")!=-1){
		       if(!/^\d+\.?\d*$/.test(rows[i].childNodes[2].firstChild.value))errorStr +=<@bean.message key="error.gradeMaxFormat"  arg0="${row}"/>+"\n";
	           if(!/^\d+\.?\d*$/.test(rows[i].childNodes[3].firstChild.value))errorStr +=<@bean.message key="error.gradeMinFormat"  arg0="${row}"/>+"\n";
	           if(!/^\d+\.?\d*$/.test(rows[i].childNodes[4].firstChild.value))errorStr +=<@bean.message key="error.creditFormat"  arg0="${row}"/>+"\n";
	           awards[i-1] = new Array();
	           awards[i-1][0]=new Number(rows[i].childNodes[2].firstChild.value);
	           awards[i-1][1]=new Number(rows[i].childNodes[3].firstChild.value);
	           if(awards[i-1][0]>awards[i-1][1]) {
	           	errorStr+="第"+i+"行绩点上限小于下限\n";
	           }
	       }
	       else{
	           awards[i-1] = new Array();
	           awards[i-1][0]=rows[i].childNodes[2].innerHTML;
	           awards[i-1][1]=rows[i].childNodes[3].innerHTML;
	       }
	   }	 
	   if(errorStr==""){
	       awards.sort();
	       var find=false;
	       for(var i=1;i<awards.length;i++){
	           if(awards[i][0]<awards[i-1][1]) {
	           	find=true;
           		}
	       }
	       if(find) {
	           errorStr+="<@bean.message key="error.areaOverlap"/>";
	       }
	   }
	    return errorStr;
	}
   var bar = new ToolBar('creditAwardBar','<@bean.message key="action.modify"/> <@bean.message key="entity.creditAwardCriteria"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.delete"/>",deleteCriteria,"delete.gif");
   bar.addItem("<@bean.message key="action.modify"/>",updateCriteria,'update.gif');
   bar.addItem("<@bean.message key="action.add"/>",newCriteria,'new.gif');
   bar.addItem("<@bean.message key="action.save"/>",saveCriteria,'save.gif');
   bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
 </script>
</body>
<#include "/templates/foot.ftl"/>