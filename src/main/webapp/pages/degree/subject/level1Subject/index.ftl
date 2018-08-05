<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="leve1SubjectBar"></table>
     <@table.table width="100%" id="listTable">
     <form name='level1SubjectForm' method="post" action="" onsubmit="return false;">
       <tr align="center" class="darkColumn" bgcolor="#ffffff" onKeyDown="javascript:enterQuery()">
	     <td><img src="${static_base}/images/action/search.png"  align="top" onClick="query()" alt="在结果中过滤"/></td>
	     <td><input type="text" name="level1Subject.category.name" maxlength="30" value="" style="width:100%"/></td>
	     <td><input type="text" name="level1Subject.name" maxlength="30" value="" style="width:100%"/></td>
	     <td><input type="text" name="level1Subject.code" maxlength="32" value="" style="width:100%"/></td>
	     <td>
	        <select name="level1Subject.forDoctor" style="width:100%">
	      	  <option value="">...</option>
	      	  <option value="0">无</option>
	      	  <option value="1">有</option>
		    </select>
		 </td>	     
         <td>
	        <select name="level1Subject.forMaster" style="width:100%">
	      	  <option value="">...</option>
	      	  <option value="0">无</option>
	      	  <option value="1">有</option>
		    </select>
         </td>
         <td></td>
	   </tr>
	   </form>
       <@table.thead>
	     <@table.selectAllTd id="subjectId"/>
  	     <@table.td width="20%" text="学科门类"/>
  	     <@table.td width="20%" text="名称"/>
  	     <@table.td width="10%" text="代码"/>
  	     <@table.td text="博士学位授予权"/>
  	     <@table.td text="硕士学位授予权"/>
  	     <@table.td width="14%" text="批准时间"/>
	   </@> 
	   <@table.tbody datas=level1Subjects;subject>
    	<@table.selectTd id="subjectId" value=subject.id/>
	    <td><@i18nName subject.category?if_exists/></td>
	    <td><a href="level1Subject.do?method=info&level1Subject.id=${subject.id}">&nbsp;${subject.name}</a></td>
	    <td>${subject.code}</td>
	    <td>${subject.forDoctor?string("有","无")}</td>
	    <td>${subject.forMaster?string("有","无")}</td>
	    <td>${subject.ratifyTime?if_exists}</td>
	   </tr>
	   </@>
	</@>
	<script>
	    var bar = new ToolBar("leve1SubjectBar","一级学科管理",null,true,true);
	    bar.setMessage('<@getMessage/>');
	    bar.addItem("二级学科点管理","second()");
	    bar.addItem("<@msg.message key="action.new"/>","add()");
	    bar.addItem("<@msg.message key="action.edit"/>","edit()");
	    bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
	    
	    var form = document.level1SubjectForm;
	    function add(){
	      form.action="level1Subject.do?method=edit";
	      form.submit();
	    }
	    function edit(){
	       submitId(form, "subjectId", false, "level1Subject.do?method=edit");
	    }
	    function second(){
	        form.action = "level2Subject.do?method=index";
	    	form.submit();
	    }
	    function enterQuery() {
	       if (window.event.keyCode == 13) {
	           query();
           }
	    }
	    function query(){
	        form.action = "level1Subject.do?method=index";
	    	form.submit();
	    }
	</script> 
</body>
<#include "/templates/foot.ftl"/>