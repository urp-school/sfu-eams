<script language="JavaScript" type="text/JavaScript">
 //下拉列表框初始化
 var stdTypeList= new Array();//学生类别下拉框数组
 var yearList= new Array();//学年度下拉框数组
 var termList = new Array();//学期下拉框数组
 var stdTypeSize=0;//学生类别下拉框长度
 var yearSize=0;//学年度下拉框长度 
 var termSize=0;//学期下拉框长度
 var calendarList = new Array();
 var calendarSize=0;

 var yearExists = false;
 <#if stdTypeList?exists> 
 <#else>
 <#assign stdTypeList = result.stdTypeList />
 </#if>
 //学生类别下拉框数组初始化
 <#list stdTypeList as stdType>
   // 没有学年度学期的学生类别将使用父类别的学年度学期设置
   <#assign superType=stdType>
   <#list 1..4 as i>

   <#if superType.teachCalendars?size==0>
	       <#if superType.superType?exists>
		       <#assign superType=superType.superType>
			   <#if superType.teachCalendars?if_exists?size!=0>
			   <#break>
			   </#if>
	       <#else>
	           
	       </#if>
   <#else>
    <#break>
   </#if>
   </#list>

   //教学日历数组初始化
   <#if superType?if_exists.teachCalendars?size!=0>
   	   stdTypeList[stdTypeSize]=new Array(2);
	   stdTypeList[stdTypeSize][0]=null;
	   stdTypeList[stdTypeSize][1]="${stdType.id}";
	   stdTypeList[stdTypeSize][2]="<@i18nName stdType/>";
	   <#list superType.teachCalendars as calendar> 
	   calendarList[calendarSize] = new Array(4);
	   calendarList[calendarSize][0] = "${stdType.id}";
	   calendarList[calendarSize][1] = "${calendar.year}";
	   calendarList[calendarSize][2] = "${calendar.year}";
	   calendarList[calendarSize][3] = "${calendar.term}";
	   calendarList[calendarSize][4] = "${calendar.term}";
	   calendarSize = calendarSize +1;
	   </#list>
	   stdTypeSize=stdTypeSize+1;
   </#if>
 </#list>
 
 for(i=0;i<calendarList.length;i++){ 
  yearExists = false;
  termExists = false;
  // check the academic year is already exists
  for(j=0;j<yearSize;j++){  
   if( (calendarList[i][0]==yearList[j][0]) && (calendarList[i][1]==yearList[j][1]) )
    yearExists = true;
  }
  for(j=0;j<termSize;j++){  
   if( (calendarList[i][1]==termList[j][0]) && (calendarList[i][3]==termList[j][1]) )
   termExists=true;
  }
  // add year
  if(!yearExists){
    yearList[yearSize]=new Array(3);
    yearList[yearSize][0]=calendarList[i][0];
    yearList[yearSize][1]=calendarList[i][1];
    yearList[yearSize][2]=calendarList[i][2];
    yearSize++;
  }
  // add term
  if(!termExists){
    termList[termSize]=new Array(3);
    termList[termSize][0]=calendarList[i][1];
    termList[termSize][1]=calendarList[i][3];
    termList[termSize][2]=calendarList[i][4];
    termSize++;
  }
 }
 initSelect(stdTypeList,"stdType",null,"year"); 
 initSelect(yearList,"year","stdType","term");
 initSelect(termList,"term","year",null);
 tip="";
</script>