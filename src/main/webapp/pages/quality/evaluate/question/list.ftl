<#include "/templates/head.ftl"/>
<body>
  <table id="bar" width="100%"></table>
   <@table.table width="100%" id="listTable" sortable="true" headIndex="1">
     <tr align="center" class="darkColumn" onKeyDown="DWRUtil.onReturn(event, query)">
	     <td><img src="${static_base}/images/action/search.png"  align="top" onClick="query()" title="在结果中过滤"/></td>
	     <form name="searchForm" method="post" action="" onsubmit="return false;">
	     <td><input type="text" name="question.content" maxlength="100" value="${RequestParameters['question.content']?if_exists}" style="width:100%"></td>
	     <td><input type="text" name="question.department.name" maxlength="20" value="${RequestParameters['question.department.name']?if_exists}" style="width:100%"></td>
	     <td><input type="text" name="question.score" maxlength="10" value="${RequestParameters['question.score']?if_exists}" style="width:100%"></td>
	     <td><@htm.i18nSelect datas=questionTypes selected=RequestParameters['question.type.id']?default('') name="question.type.id"  style="width:100%"><option value="">...</option></@></td> 
	     <td><input type='text' name='question.priority' maxlength="5" value='${RequestParameters['question.priority']?if_exists}' style="width:100%"></td>	     
         <td><@htm.select2 name="question.state" selected="${RequestParameters['question.state']?if_exists}" hasAll=true  style="width:100%"/></td>
         </form>
     </tr>
	 <@table.thead>
	   <@table.selectAllTd id="questionId"/>
	   <@table.sortTd width="35%" id="question.content" name="field.question.questionContext"/>
	   <@table.sortTd width="15%" id="question.department.name" text="部门"/>
	   <@table.sortTd width="10%" id="question.score" name="field.question.mark"/>
	   <@table.sortTd width="14%" id="question.type.name" name="field.question.questionType"/>
	   <@table.sortTd width="10%" id="question.priority" text="优先级"/>
	   <@table.sortTd width="10%" id="question.state" text="是否可用"/>
	 </@>
	 <@table.tbody datas=questions;question>
	    <@table.selectTd id="questionId" value=question.id/>
	    <td>${question.content}</td>
	    <td><@i18nName question.department/></td>
	    <td>${question.score}</td>
	    <td><@i18nName question.type?if_exists/></td>
	    <td>${question.priority}</td>
	    <td>${question.state?string("有效","无效")}</td>
	 </@>
   </@>
 <@htm.actionForm name="actionForm" action="question.do" entity="question"/>
<script>
   var bar = new ToolBar('bar','问题列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.add"/>","add()");
   bar.addItem("<@msg.message key="action.edit"/>","edit()");
   bar.addItem("<@msg.message key="action.delete"/>","remove()");
   //bar.addItem("<@msg.message key="action.info"/>","info()");
   function query(){
      var form=document.searchForm;
      form.action="question.do?method=search";
      form.submit();
   }
</script>
 </body>
<#include "/templates/foot.ftl"/>