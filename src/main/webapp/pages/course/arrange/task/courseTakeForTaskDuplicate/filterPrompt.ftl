  <div id="filterPrompt" style="display:none;width:500px;height:180px;position:absolute;top:30px;left:100px;border:solid;border-width:1px;background-color:white ">
     <table class="formTable" width="100%" height="100%" border="1" >
      <tr>
       <td width="20%">筛选规则</td>
       <td>
        1)随机筛选用于<font color="red">选课人数超过上限</font>的教学任务.<br>
	    2)对于待筛选的任务,算出超出上限名额数量,<font color="red">随机删除</font>选课结果中在"最大轮次"选中的学生.
	    <select id="selectId" name="selectId"  width="30px;">
	       <option value="default">默认选择最大轮次</option>
	       <option value="1">1</option>
	       <option value="2">2</option>
	       <option value="3">3</option>
	       <option value="4">4</option>
	       <option value="5">5</option>
	       <option value="6">6</option>
	       <option value="7">7</option>
	       <option value="8">8</option>
	       <option value="9">9</option>
	       <option value="10">10</option>
	    </select>
	    <br>
	    3)保留修读类别为"指定"的上课名单<br>
	   </td>
	   </tr>
	   <tr>
	   <td colspan="2" align="center">
	     <button class="buttonStyle" onclick="filter()" accesskey="F">确定筛选(<U>F</U>)</button>&nbsp;
	     <button class="buttonStyle" onclick="filterPrompt.style.display='none'" accesskey="C">取消(<U>C</U>)</button>
	   </td>
	   </tr>
     </table>
  </div>
  <script>
   function displayPrompt(){
       filterPrompt.style.display="block";
       f_frameStyleResize(self);
   }
  </script>