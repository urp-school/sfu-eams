<table width="100%" align="center" class="listTable">
    <caption>
      <B>专家对论文学术水平的总体评价意见</B>
    </caption>
    <tr>
      <td colspan="5"><textarea name="thesisAnnotate.evaluateIdea.evaluateIdea" cols="50" rows="6" style="width:100%;border:1 solid #000000;">${result.thesisAnnotate?if_exists.evaluateIdea?if_exists.evaluateIdea?if_exists}</textarea></td>
    </tr>
    <tr align="center">
      <td rowspan="2" class="darkColumn" width="20%">论文是否达到授予博士学位的学术水平</td>
      <td>优秀</td>
      <td>良好</td>
      <td>一般</td>
      <td>不合格</td>
    </tr>
    <tr align="center">
      <#assign learningLevel = result.thesisAnnotate?if_exists.evaluateIdea?if_exists.learningLevel?if_exists?string>
      <td><input type="radio" value="A" name='thesisAnnotate.evaluateIdea.learningLevel' <#if learningLevel?exists && learningLevel=='A'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="B" name='thesisAnnotate.evaluateIdea.learningLevel' <#if learningLevel?exists && learningLevel=='B'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="C" name='thesisAnnotate.evaluateIdea.learningLevel' <#if learningLevel?exists && learningLevel=='C'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="D" name='thesisAnnotate.evaluateIdea.learningLevel' <#if learningLevel?exists && learningLevel=='D'>checked</#if>>&nbsp;</td>
    </tr>
    <tr align="center">
      <td rowspan="2" class="darkColumn">对作者可否参加论文答辩的意见</td>
      <td>同意答辩</td>
      <td colspan="2">修改后答辩</td>
      <td>不同意答辩</td>
    </tr>
    <tr align="center">
      <#assign idea = result.thesisAnnotate?if_exists.evaluateIdea?if_exists.idea?if_exists?string>
      <td><input type="radio" value="1" name='thesisAnnotate.evaluateIdea.idea' <#if idea?exists && idea=='1'>checked</#if>>&nbsp;</td>
      <td colspan="2"><input type="radio" value="2" name='thesisAnnotate.evaluateIdea.idea' <#if idea?exists && idea=='2'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="3" name='thesisAnnotate.evaluateIdea.idea' <#if idea?exists && idea=='3'>checked</#if>>&nbsp;</td>
    </tr>    
  </table> 