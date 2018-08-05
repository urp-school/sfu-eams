<script language="JavaScript" type="text/JavaScript">
 //下拉列表框初始化
 var firstSubjectList= new Array();//一级学科下拉框数组
 var secondSubjectList= new Array();//二级学科下拉框数组
 var thirdSubjectList = new Array();//三级学科方向下拉框数组
 var depSize=0;//一级学科下拉框长度
 var speSize=0;//二级学科下拉框长度 
 var aspctSize=0;//三级学科下拉框长度
 <#if firstSubjectList?exists> 
 <#else>
 <#assign firstSubjectList = result.firstSubjectList />
 </#if>
 
 //院系下拉框数组初始化
 <#list firstSubjectList as firstSubject>
   firstSubjectList[depSize]=new Array(2);
   firstSubjectList[depSize][0]=null;
   firstSubjectList[depSize][1]="${firstSubject.firstCode}";
   firstSubjectList[depSize][2]="${firstSubject.name}"; 
   
   //专业框数组初始化 
   <#list firstSubject.secondSubjectSet as secondSubject> 
	 	secondSubjectList[speSize]=new Array(2);
		secondSubjectList[speSize][0]="${firstSubject.firstCode}";
		secondSubjectList[speSize][1]="${secondSubject.secondCode}";
		secondSubjectList[speSize][2]="${secondSubject.name}"; 
  		//专业方向下拉框数组初始化
		<#list secondSubject.thirdSubjectSet as thirdSubject>
		    thirdSubjectList[aspctSize]=new Array(2);
		    thirdSubjectList[aspctSize][0]="${secondSubject.secondCode}";
		    thirdSubjectList[aspctSize][1]="${thirdSubject.thirdCode}";
		    thirdSubjectList[aspctSize][2]="${thirdSubject.name}"; 
		    aspctSize = aspctSize + 1;
		</#list>
		speSize=speSize+1;
   </#list>
   depSize=depSize+1;
 </#list>
 initSelect(firstSubjectList,"firstSubject",null,"secondSubject"); 
 initSelect(secondSubjectList,"secondSubject","firstSubject","thirdSubject");
 initSelect(thirdSubjectList,"thirdSubject","secondSubject",null);
</script>