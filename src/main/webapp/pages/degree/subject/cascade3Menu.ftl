<script language="JavaScript" type="text/JavaScript">
 //下拉列表框初始化
 var subjectClassList= new Array();//学科门类下拉框数组
 var firstSubjectList= new Array();//一级学科下拉框数组
 var secondSubjectList= new Array();//二级学科下拉框数组
 var aspectList = new Array();
 var subjectSize=0;//学科门类下拉框长度
 var firstSize=0;//一级学科下拉框长度 
 var secondSize=0;//二级学科下拉框长度  
 
 //学科门类下拉框数组初始化
 <#list result.subjectClassList as subjectClass>
   subjectClassList[subjectSize]=new Array(1);
   subjectClassList[subjectSize][0]=null;
   subjectClassList[subjectSize][1]="${subjectClass.subjectCode}";
   subjectClassList[subjectSize][2]="${subjectClass.name}"; 
   
   //一级学科框数组初始化 
   <#list subjectClass.firstSubjectSet as firstSubject> 
		firstSubjectList[firstSize]=new Array(2);
		firstSubjectList[firstSize][0]="${subjectClass.subjectCode}";
		firstSubjectList[firstSize][1]="${firstSubject.firstCode}";
		firstSubjectList[firstSize][2]="${firstSubject.name}";   
		firstSize=firstSize+1;
		//二级学科数组初始化
		<#list firstSubject.secondSubjectSet as secondSubject>
			secondSubjectList[secondSize]=new Array(2);
			secondSubjectList[secondSize][0]="${firstSubject.firstCode}";
			secondSubjectList[secondSize][1]="${secondSubject.secondCode}";
			secondSubjectList[secondSize][2]="${secondSubject.name}";   
			secondSize=secondSize+1;			
		</#list>
   </#list>
   subjectSize=subjectSize+1;
 </#list>
 initSelect(subjectClassList,"subjectClass",null,"firstSubject"); 
 initSelect(firstSubjectList,"firstSubject","subjectClass","secondSubject"); 
 initSelect(secondSubjectList,"secondSubject","firstSubject",null);  
</script>