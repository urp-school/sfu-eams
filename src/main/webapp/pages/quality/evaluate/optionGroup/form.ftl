<#include "/templates/head.ftl"/>
<body>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
  <table id="bar" width="100%"></table>
  <table class="formTable"  width="60%" align="center" >
    <form method="post" action="optionGroup.do?method=save" name="actionForm" onsubmit="return false;">
    <input name="optionGroup.id"  value="${optionGroup.id?default("")}" type="hidden"/>
    <tr>
     <td class="title"><font color="red">*</font>组名</td>
     <td><input name="optionGroup.name" maxlength="20" value="${optionGroup.name?default('')}" style="width:100px"/></td>
     <td class="title">制作部门</td>
     <td><@htm.i18nSelect selected=(optionGroup.depart.id)?default('')?string name="optionGroup.depart.id" datas=departmentList style="width:100px"/></td>
    </tr>
  </table>
  <@table.table  width="60%" align="center" id="listTable">
	<@table.thead>
	  <@table.selectAllTd id="optionId"/>
	  <@table.td text="选项名" width="19%"/>
	  <@table.td text="所占比重" width="19%"/>
	</@>
    <@table.tbody datas=optionGroup.options?sort_by("proportion")?reverse;option,option_index>
      <@table.selectTd id="optionId" value=option.id><input type="hidden" name="option${option_index}.id" value="${option.id}"/></@>
	  <td><input name="option${option_index}.name" maxlength="20" value="${option.name}"/></td>
	  <td><input name="option${option_index}.proportion" maxlength="10" value="${option.proportion?default(0)}"/></td>
	</@>
 </@>
 </form>
 <script>
   var bar = new ToolBar('bar','制定选项组',null,true,true);
   bar.setMessage('<@getMessage/>');

   bar.addItem("<@bean.message key="action.add"/>","add()",'new.gif');
   bar.addItem("<@bean.message key="action.save"/>","save()",'save.gif');
   bar.addItem("<@bean.message key="action.delete"/>","remove()","delete.gif");
   bar.addBack("<@bean.message key="action.back"/>");
   var optionAttrs = new Array();
   optionAttrs[0]="id";
   optionAttrs[1]="name";
   optionAttrs[2]="proportion";
   var form =document.actionForm;
   var index=${optionGroup.options?size};
   //添加
   function add(){
	    var tb = listTable;
	    var tr = document.createElement('tr');
	    var cellsNum = tb.rows[0].cells.length;
	    for(var j = 0 ; j < cellsNum ; j++){
	        var td = document.createElement('td');
	        if(j==0){
		        td.innerHTML='<input type="checkBox" name="optionId" value="">'
		        td.className="select";
		    }
		    else{
		       id="option"+index+"."+optionAttrs[j];
		       td.innerHTML ="<input type='text' style='width:100%'  name='"+ id +"' id='"+id + "'>"
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
    function remove(){
		var Ids = getCheckBoxValue(document.getElementsByName("optionId"));
		if(Ids!=""&&!confirm("<@bean.message key="prompt.deletion"/>"))return;
		var count=0;
		var rows= listTable.rows;
		// 跳过标题
		for(var i=1;i<rows.length;i++){
		   if(rows[i].firstChild.firstChild.checked){
		       listTable.tBodies[0].removeChild(rows[i--]);
		       count++;
		   }
		}
		if(count==0) {alert("<@bean.message key="common.selectPlease" />");return;}
		f_frameStyleResize(self);
	}
	// 保存既有的设置（新增的和修改的）
	function save(){
        errorStr=check();
        if(form['optionGroup.name'].value=="")
          errorStr +="组名没有填写\n";
        if(errorStr!=""){alert(errorStr);return;}
        addInput(form,"optionCount",index);
    	form.submit();
	}
    function check(){
	   var errorStr="";
	   for(var i=0;i<index;i++){
	      if(document.getElementById('option'+i+".name").value=="")errorStr+='第'+(i+1)+"行 选项名称没有填写\n";
	      if(!/^\d+\.?\d*$/.test(document.getElementById('option'+i+".proportion").value)){
	         errorStr+='第'+(i+1)+"行 比重格式不对\n";
	      }else{
	         if(new Number(document.getElementById('option'+i+".proportion").value)>1){
	            errorStr+='第'+(i+1)+"行 比重太大,要小于1\n";
	         }
	      }
	   }
	   return errorStr;
	}
 </script>
 </body>
<#include "/templates/foot.ftl"/>