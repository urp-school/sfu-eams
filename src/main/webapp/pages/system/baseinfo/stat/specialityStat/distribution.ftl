<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="myBar" width="100%"></table>
  <@table.table width="100%"  id="listTable">
    <@table.thead>
       <@table.td width="15%" name="entity.department"/>
       <@table.td width="20%" name="attr.infoname" id="speciality.name"/>
       <@table.td width="10%" name="attr.code" id="speciality.code"/>
       <@table.td width="15%" text="所属学科门类" />    
    </@>
    <@table.tbody datas=specialities;speciality,speciality_index>
       <td><@i18nName speciality.department/></td>
       <td><a href="speciality.do?method=info&speciality.id=${speciality.id}"><@i18nName speciality/></a></td>
       <td>${speciality.code}</td>
       <td><@i18nName (speciality.subjectCategory)?if_exists/></td>
    </@>
  </@>
 <script>
    var bar = new ToolBar("myBar","专业设置列表",null,true,true);
    bar.addPrint("<@msg.message key="action.print"/>");
	function mergeTableTd(tableId,index){
		var rowsArray = document.getElementById(tableId).rows;
		var value=rowsArray[1].cells[index];
		for(var i=2;i<rowsArray.length;i++){
			nextTd=rowsArray[i].cells[index];
			if(nextTd.innerHTML==value.innerHTML){
				rowsArray[i].removeChild(nextTd);
				var rowspanValue= new Number(value.rowSpan);
				rowspanValue++;
				value.rowSpan=rowspanValue;
			}else{
				value=nextTd;
			}
		}
   }
   mergeTableTd('listTable',0);
 </script>  
  </body>
<#include "/templates/foot.ftl"/>