<#include "/templates/head.ftl"/>
<body>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
  <table id="bar" width="100%"></table>
  <table class="formTable" width="60%" align="center" >
    <form method="post" action="evaluationCriteria.do?method=save" name="actionForm" onsubmit="return false;">
    <input name="evaluationCriteria.id"  value="${evaluationCriteria.id?default("")}" type="hidden"/>
    <tr>
     <td class="title"><font color="red">*</font>名称</td>
     <td><input name="evaluationCriteria.name" maxlength="20" value="${evaluationCriteria.name?default('')}" style="width:100px"/></td>
     <td class="title">制作部门</td>
     <td><@htm.i18nSelect selected=(evaluationCriteria.depart.id)?default('')?string name="evaluationCriteria.depart.id" datas=departmentList style="width:100px"/></td>
    </tr>
  </table>
  <@table.table  width="60%" align="center" id="listTable">
	 <@table.thead>
	  <@table.selectAllTd id="criteriaItemId"/>
	  <@table.td text="评价名称" width="30%"/>
	  <@table.td text="最小分数(包含)" width="30%"/>
	  <@table.td text="最大分数(不包含)" width="30%"/>
	 </@>
    <@table.tbody datas=evaluationCriteria.criteriaItems?sort_by("min");criteriaItem,criteriaItem_index>
        <@table.selectTd id="criteriaItemId" value=criteriaItem.id><input type="hidden" name="criteriaItem${criteriaItem_index}.id" value="${criteriaItem.id}"/></@>
	    <td><input id="criteriaItem${criteriaItem_index}.name" name="criteriaItem${criteriaItem_index}.name" maxlength="20" value="${criteriaItem.name}" style="width:100%"/></td>
	    <td><input id="criteriaItem${criteriaItem_index}.min" name="criteriaItem${criteriaItem_index}.min" maxlength="5" value="${criteriaItem.min?default(0)}" style="width:100%"/></td>
	    <td><input id="criteriaItem${criteriaItem_index}.max" name="criteriaItem${criteriaItem_index}.max" maxlength="5" value="${criteriaItem.max?default(0)}" style="width:100%"/></td>
	</@>
 </@>
 </form>
 <script>
   var bar = new ToolBar('bar','制定评价标准项',null,true,true);
   bar.setMessage('<@getMessage/>');

   bar.addItem("<@bean.message key="action.add"/>","add()",'new.gif');
   bar.addItem("<@bean.message key="action.save"/>","save()",'save.gif');
   bar.addItem("<@bean.message key="action.delete"/>","remove()","delete.gif");
   bar.addBack("<@bean.message key="action.back"/>");
   var criteriaItemAttrs = new Array();
   criteriaItemAttrs[0]="id";
   criteriaItemAttrs[1]="name";
   criteriaItemAttrs[2]="min";
   criteriaItemAttrs[3]="max";
   var form =document.actionForm;
   var index=${evaluationCriteria.criteriaItems?size};
   //添加
   function add(){
	    var tb = listTable;
	    var tr = document.createElement('tr');
	    var cellsNum = tb.rows[0].cells.length;
	    for(var j = 0 ; j < cellsNum ; j++){
	        var td = document.createElement('td');
	        if(j==0){
		        td.innerHTML='<input type="checkBox" name="criteriaItemId" value="">'
		        td.className="select";
		    }
		    else{
		       id="criteriaItem"+index+"."+criteriaItemAttrs[j];
		       td.innerHTML ="<input type='text' style='width:100%' maxlength='5' name='"+ id +"' id='"+id + "'>";
		    }
	        tr.appendChild(td);
	    }
	    tr.className="grayStyle";
	    tr.align="center";
	    tb.tBodies[0].appendChild(tr);
	    index++;
	    f_frameStyleResize(self);
	}
	
	//删除具体某表的选中的行（该表第一列是复选框）
	//tBodies[1]
    function remove(){
		var Ids = getCheckBoxValue(document.getElementsByName("criteriaItemId"));
		if(Ids!=""&&!confirm("<@bean.message key="prompt.deletion"/>"))return;
		var count=0;
		var rows= listTable.rows;
		// 跳过标题
		for(var i=1;i<rows.length;i++){
		   if(rows[i].firstChild.firstChild.checked){
		       listTable.tBodies[1].removeChild(rows[i--]);
		       count++;
		   }
		}
		if(count==0) {alert("<@bean.message key="common.selectPlease" />");return;}
		f_frameStyleResize(self);
	}
	// 保存既有的设置（新增的和修改的）
	function save(){
	    errorStr=check();
	    if(form['evaluationCriteria.name'].value=="")
	      errorStr +="名称没有填写\n";
	    if(errorStr!=""){alert(errorStr);return;}
	    addInput(form,"criteriaItemCount",index);
		form.submit();
	}
    function check(){
	   var errorStr="";
	   var datas=new Array();
	   for(var i=0;i<index;i++){
	      if(!document.getElementById('criteriaItem'+i+".name")){
	        continue;
	      }
	      if(document.getElementById('criteriaItem'+i+".name").value=="")errorStr+='第'+(i+1)+"行 选项名称没有填写\n";
	      if(!/^\d+\.?\d*$/.test(document.getElementById('criteriaItem'+i+".min").value))errorStr+='第'+(i+1)+"行最小值不对\n";
	      else{
	         datas.push(parseFloat(document.getElementById('criteriaItem'+i+".min").value));
	      }
	      if(!/^\d+\.?\d*$/.test(document.getElementById('criteriaItem'+i+".max").value))errorStr+='第'+(i+1)+"行最大值不对\n";
	      else{
	         datas.push(parseFloat(document.getElementById('criteriaItem'+i+".max").value));
	      }
	   }
	   for(var i=0;i<datas.length;i+=2){
        if(datas[i+1]<datas[i]){
           errorStr+=datas[i+1]+":"+datas[i]+" 范围错误\n";
        }
        if(i!=0&&datas[i-1]>datas[i]){
           errorStr+=datas[i-1]+":"+datas[i]+" 范围错误\n";
        }
	   }
	   return errorStr;
	}
 </script>
 </body>
<#include "/templates/foot.ftl"/>