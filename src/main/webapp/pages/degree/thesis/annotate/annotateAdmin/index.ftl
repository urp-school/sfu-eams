<#include "/templates/head.ftl"/>
<script>
	var flag=1;
    function getIds(name){
       return(getCheckBoxValue(document.getElementsByName(name)));
    }
    function changeView(id){
     for(i=1;i<=2;i++){
       var viewDiv ="view"+i;
       var inputButton="button"+i;
       var div = document.getElementById(viewDiv);
       var buttonObject = document.getElementById(inputButton);
       if(id==viewDiv){
          div.style.display = "block";
          buttonObject.className="buttonStyle";
          flag=i;
         }
       else {
          div.style.display = "none";
          buttonObject.className="";
         }
      }
      if("view1"==id)
      	 search(1);
      else if("view2"==id){
      	document.bookListForm["student.code"].value=document.pageGoForm["student.code"].value;
      	document.bookListForm["student.name"].value=document.pageGoForm["student.name"].value;
      	bookList(1);
      }
    }
</script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onLoad="search();">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','论文评阅',null,true,true);
   bar.setMessage('<@getMessage/>');
</script>
 <table  width="100%"  class="frameTable"> 
	<tr>
		<td width="20%" class="frameTable_view" valign="top">
			<table width="100%" class="searchTable">
				<tr>
					<td align="right"><input type="button" id="button1" name="button1" value="论文评阅" class="buttonStyle" onClick="changeView('view1')"/></td>
					<td><input type="button" id="button2" name="button2" value="评阅结果" class="" onClick="changeView('view2')"/></td>
				</tr>
				<tr>
					<td colspan="2">
					<div id="view1" style="display:block;width:100%">
						<table width="100%">
						<form name="pageGoForm" method="post" target="researchHarvestFrame" onsubmit="return false;">
						<#assign stdTypeName>stdTypeOfSpeciality</#assign>
  						<#include "../../stdConditions.ftl"/>
  						<tr>
  							<td>自评表：</td>
  							<td><select name="hasWrite" style="width:100px;">
  								<option value="true">已填写</option>
  								<option value="false">未填写</option>
  							</select>
  							</td>
						</tr>
						<tr>
  							<td>论文编号：</td>
  							<td><input type="text" name="annotateBook.serial" style="width:100px;" maxlength="20"/></td>
						</tr>
						<tr>
  							<td>起始时间：</td>
  							<td><input type="text" name="finishOnStart" style="width:100px;" onfocus="calendar()" maxlength="10"/></td>
						</tr>
						<tr>
  							<td>结束时间：</td>
  							<td><input type="text" name="finishOnEnd" style="width:100px;" onfocus="calendar()" maxlength="10"/></td>
						</tr>
						<tr height="50px">
							<td colspan="2" align="center">
			       			<button name="button9" onClick="search(1)" class="buttonStyle"><@msg.message key="system.button.query"/></button> 
		     				</td>
			  			</tr>
						</form>
					</table>
				</div>
				<div id="view2" style="display:none;">
			  		<table class="searchTable">
						<form name="bookListForm" method="post" target="researchHarvestFrame">
							<#assign departName>depart2</#assign>
							<#assign specialityName>speciality2</#assign>
							<#assign aspectName>aspect2</#assign>
							<#assign stdTypeName>stdType2</#assign>
  							<#include "../../stdConditions.ftl"/>
  							<tr>
  								<td>论文编号：</td>
  								<td><input type="text" name="annotateBook.serial" style="width:100px" maxlength="20"/></td>
  							</tr>
  							<tr>
  								<td>学位水平：</td>
  								<td><select name="annotateBook.evaluateIdea.learningLevel" style="width:100px">
  									<option value="">全部</option>
  									<option value="A">优秀</option>
  									<option value="B">良好</option>
  									<option value="C">一般</option>
  									<option value="D">不及格</option>
  								</select></td>
  							</tr>
  							<tr>
  								<td>是否同意答辩：</td>
  								<td><select name="annotateBook.isReply" style="width:100px">
  									<option value="">全部</option>
  									<option value="A">同意</option>
  									<option value="B">修改后答辩</option>
  									<option value="C">不同意</option>
  									</select>
  								</td>
  							</tr>
  							<tr>
  								<td>是否有成绩：</td>
  								<td><select name="isHasMark" style="width:100px">
  									<option value="">全部</option>
  									<option value="1">有</option>
  									<option value="0">无</option>
  									</select>
  								</td>
  							</tr>
							<tr>
	  							<td>起始时间：</td>
	  							<td><input type="text" name="finishOnStart" style="width:100px;" onfocus="calendar()" maxlength="10"/></td>
							</tr>
							<tr>
	  							<td>结束时间：</td>
	  							<td><input type="text" name="finishOnEnd" style="width:100px;" onfocus="calendar()" maxlength="10"/></td>
							</tr>
  							<tr height="50px">
							<td colspan="2" align="center">
			       				<button name="button0" onClick="bookList(1)" class="buttonStyle"><@msg.message key="system.button.query" /></button> 
		     				</td>
			  			</tr>
  						</form>
  					</table>
				</div>
			 </tr>
		</table>
		</td>
		<td valign="top">
		  <iframe id="researchHarvestFrame" name="researchHarvestFrame" 
		     marginwidth="0" marginheight="0" scrolling="no"
		     frameborder="0"  height="100%" width="100%">
		  </iframe>
		</td>
	</tr>
</table> 
<#assign stdTypeNullable=true/>
<#include "/templates/stdTypeDepart3Select.ftl"/>
<script>
   var sds2 = new StdTypeDepart3Select("stdType2","depart2","speciality2","aspect2",true,true,true,true);    
   sds2.init(stdTypeArray,departArray);
   function search(pageNo,pageSize){
    	var orderString = getIds("orderString");
    	document.pageGoForm.action ="annotateAdmin.do?method=doStdList&orderString="+orderString;
    	goToPage(document.pageGoForm,pageNo,pageSize);
    }
    function bookList(pageNo,pageSize){
  	 	document.bookListForm.action="annotateAdmin.do?method=doAnnotateBooks";
  	 	goToPage(document.bookListForm,pageNo,pageSize);
  	}
</script>
</body>     
<#include "/templates/foot.ftl"/>
    