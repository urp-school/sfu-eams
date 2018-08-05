<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="subjectStatBar"></table>
     <table width="100%" align="center" class="listTable" id='subjectTable'>
       <tr align="center" class="darkColumn">
  	     <td width="15%">学科门类(${categoryCount})</td>
	     <td width="15%">一级学科(${level1SubjectCount})</td>
	     <td width="15%">二级学科点代码</td>
	     <td width="20%">二级学科点名称</td>
	     <td width="15%">博士点设置时间(${doctorCount})</td>
	     <td width="15%">硕士点设置时间(${masterCount})</td>
	   </tr> 
	   
	   <#list level2Subjects?if_exists as subject>
	   <tr  class="brightStyle" align="center">  
	    <td>${(subject.level1Subject.category.name)?if_exists}</td>
	    <td>${subject?if_exists.level1Subject?if_exists.name?if_exists}<#if subject.level1Subject.forMaster><font color="red">￥</font></#if><#if subject.level1Subject.forDoctor><font color="red">#</font></#if></td>
	    <td>${subject?if_exists.speciality?if_exists.code?if_exists}</td>	    
	    <td>${subject?if_exists.speciality?if_exists.name?if_exists}</td>
	    <td>${(subject?if_exists.dateForDoctor?string("yyyy"))?if_exists}<#if subject.isSelfForDoctor><font color="red">*</font></#if></td>
	    <td>${(subject?if_exists.dateForMaster?string("yyyy"))?if_exists}<#if subject.isSelfForMaster><font color="red">*</font></#if></td>
	   </tr>
	   </#list>
     </table>
     <p>
     注：# 为博士学位授予权一级学科点。<br>&nbsp;&nbsp;￥为硕士学位授予权一级学科点。<br>&nbsp;&nbsp;*为在一级学科点内自主设置的专业。<br>
     时间为：1981年10月8日第一批；1984年1月13第二批；1986年7月28日第三批;<br> 1990年6月30日第4批；1993年12月11日第五批；1996年4月30日第六批;<br>
     1998年6月18日第七批；2000年12月29日第八批；2003年9月8日第九批，2006年1月25日第十批。
     </p>
	<script>
	    var bar = new ToolBar("subjectStatBar","学科点管理",null,true,true);
	    bar.setMessage('<@getMessage/>');
	    bar.addItem("一级学科点管理",'level1Subject.do?method=index');
	    bar.addItem("二级学科点管理",'level2Subject.do?method=index');
	    bar.addItem("<@msg.message key="action.print"/>",'print()');
	    
		function mergeTableTd(tableId,index){
			var rowsArray = document.getElementById(tableId).rows;
			if(rowsArray.length<2)return;
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
		
	    mergeTableTd('subjectTable',1);
	    mergeTableTd('subjectTable',0);
	</script>
</body>
<#include "/templates/foot.ftl"/>