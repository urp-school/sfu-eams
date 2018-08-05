/**
 * 初始化排序表格<br>
 * 此函数主要是向已经待排序表格的列头1)添加鼠标事件响应和显示效果.
 * 2)负责将事件传递到用户定义的函数中.
 *
 * 凡是要排序的列,请注名排序单元格的id 和class.
 * 其中id是排序要传递的字段,class为定值tableHeaderSort.
 * 除此之外,用户(使用该方法的人)需要自定义一个钩子函数"sortBy(what)",以备调用.
 * @param tableId 待排序表格的id
 * @param rowIndex 标题行的位置 
 * @param orderBy orderBy 字句
 * 用法:
 *    <table>
 */
function initSortTable(tableId,rowIndex,orderBy){
  var table= document.getElementById(tableId);
  if(null==rowIndex)
     rowIndex=0;
  var head=table.tBodies[0].rows[rowIndex];
  if(null==head){
    alert("sortTable ["+tableId+"] with out thead"); 
    return;
  }
  //alert(head.innerHTML);
  for(var i=0;i<head.cells.length;i++){
     if(head.cells[i].className=="tableHeaderSort"){
       if(head.cells[i].id){
           head.cells[i].onclick=sort;
           head.cells[i].onmouseover=overSortTableHeader;
           head.cells[i].onmouseout=outSortTableHeader;
           if (isEmpty(head.cells[i].title)) {
               head.cells[i].title="点击按 ["+head.cells[i].innerHTML+"] 排序";
           } else {
               head.cells[i].title="点击按 ["+head.cells[i].title+"] 排序";
           }
           if(head.cells[i].id!=null){
               var desc=head.cells[i].id.replace(/\,/g," desc,")+" desc";
               if(typeof head.cells[i].desc !="undefined"){
                  desc=head.cells[i].desc;
               }
               if(orderBy.indexOf(desc)!=-1){
	              head.cells[i].className="tableHeaderSortDesc";
	              head.cells[i].innerHTML=head.cells[i].innerHTML+'<img src="images/action/sortDesc.gif"  style="border:0"  alt="Arrow" />'
	              continue;
	           }
               var asc=head.cells[i].id+" asc";
               if(typeof head.cells[i].asc !="undefined"){
                  asc=head.cells[i].asc;
               }
               if(orderBy==asc){
	              head.cells[i].className="tableHeaderSortAsc"
	              head.cells[i].innerHTML=head.cells[i].innerHTML+'<img src="images/action/sortAsc.gif"  style="border:0"  alt="Arrow" />'
	              continue;
	           }
           }
       }
     }
  }
  //alert("complete init sortTable");
}
//鼠标经过和移出排序表格的表头时
function overSortTableHeader(){
    this.style.color='white';
    this.style.backgroundColor ='green'
}
function outSortTableHeader(){
   this.style.borderColor='';
   this.style.color='';
   this.style.backgroundColor ='';
}
function sort(){
   if(typeof orderBy !="function" ){
      alert("you haven't define orderBy function:\n function orderBy(what){\n...}");
      return;
   }
   var orderByStr=null;
   if(this.className=="tableHeaderSort"){
      if(typeof this.asc!="undefined"){
         orderByStr=this.asc;
      }
      else{
         orderByStr=this.id+" asc";
      }
   }else if(this.className=="tableHeaderSortAsc"){
      if(typeof this.desc!="undefined"){
         orderByStr=this.desc;
      }
      else{
         orderByStr=this.id.replace(/\,/g," desc,")+" desc";
      }
   }else{
      orderByStr="null";
   }
   //alert(orderByStr);
   orderBy(orderByStr);
}
