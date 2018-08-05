<table width="100%" align="center" class="listTable">
    <caption>
      <B>博士学位论文专家评价意见表</B>
    </caption>
    <tr align="center" class="darkColumn">
      <td width="20%" align="left">评价项目</td>
      <td width="50%">评价要素</td>
      <td>优秀(A)</td>
      <td>良好(B)</td>
      <td>一般(C)</td>
      <td>不合格(D)</td>
    </tr>
    <tr align="center">
      <td align="left" class="darkColumn" id="f_topicMeaning${idSeq}">选题意义<#if flag?exists&&flag=="book"><font color="red">*</font></#if>:</td>
      <td align="left">选题对学科发展,经济建设，科技进步和社会发展的理论意义和实用价值；</td>
      <td><input type="radio" value="A" name='annotateBook${idSeq}.evaluateIdea.topicMeaning' <#if annotateBook?if_exists.evaluateIdea?if_exists.topicMeaning?exists && annotateBook?if_exists.evaluateIdea?if_exists.topicMeaning?string=='A'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="B" name='annotateBook${idSeq}.evaluateIdea.topicMeaning' <#if annotateBook?if_exists.evaluateIdea?if_exists.topicMeaning?exists && annotateBook?if_exists.evaluateIdea?if_exists.topicMeaning?string=='B'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="C" name='annotateBook${idSeq}.evaluateIdea.topicMeaning' <#if annotateBook?if_exists.evaluateIdea?if_exists.topicMeaning?exists && annotateBook?if_exists.evaluateIdea?if_exists.topicMeaning?string=='C'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="D" name='annotateBook${idSeq}.evaluateIdea.topicMeaning' <#if annotateBook?if_exists.evaluateIdea?if_exists.topicMeaning?exists && annotateBook?if_exists.evaluateIdea?if_exists.topicMeaning?string=='D'>checked</#if>>&nbsp;</td>
    </tr>
    <tr align="center">
      <td align="left" class="darkColumn" id="f_lteratureSumUp${idSeq}">文献综述<#if flag?exists&&flag=="book"><font color="red">*</font></#if>:</td>
      <td align="left">对本研究领域文献资料的研究是否全面，评述是否恰当；</td>
      <td><input type="radio" value="A" name='annotateBook${idSeq}.evaluateIdea.lteratureSumUp' <#if annotateBook?if_exists.evaluateIdea?if_exists.lteratureSumUp?exists &&annotateBook?if_exists.evaluateIdea?if_exists.lteratureSumUp?string=='A'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="B" name='annotateBook${idSeq}.evaluateIdea.lteratureSumUp' <#if annotateBook?if_exists.evaluateIdea?if_exists.lteratureSumUp?exists &&annotateBook?if_exists.evaluateIdea?if_exists.lteratureSumUp?string=='B'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="C" name='annotateBook${idSeq}.evaluateIdea.lteratureSumUp' <#if annotateBook?if_exists.evaluateIdea?if_exists.lteratureSumUp?exists &&annotateBook?if_exists.evaluateIdea?if_exists.lteratureSumUp?string=='C'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="D" name='annotateBook${idSeq}.evaluateIdea.lteratureSumUp' <#if annotateBook?if_exists.evaluateIdea?if_exists.lteratureSumUp?exists &&annotateBook?if_exists.evaluateIdea?if_exists.lteratureSumUp?string=='D'>checked</#if>>&nbsp;</td>
    </tr>
    <tr align="center">
      <td align="left" class="darkColumn" id="f_researchHarvest${idSeq}">研究成果<#if flag?exists&&flag=="book"><font color="red">*</font></#if>:</td>
      <td align="left">运用新视角，新方法进行探索，研究，有独到见解；<br>在某一方面达到国内或国际领先水平；<br>研究成果的理论意义或使用价值；<br>论文的难易程度及工作量；</td>
      <td><input type="radio" value="A" name='annotateBook${idSeq}.evaluateIdea.researchHarvest' <#if annotateBook?if_exists.evaluateIdea?if_exists.researchHarvest?exists && annotateBook?if_exists.evaluateIdea?if_exists.researchHarvest?string=='A'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="B" name='annotateBook${idSeq}.evaluateIdea.researchHarvest' <#if annotateBook?if_exists.evaluateIdea?if_exists.researchHarvest?exists && annotateBook?if_exists.evaluateIdea?if_exists.researchHarvest?string=='B'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="C" name='annotateBook${idSeq}.evaluateIdea.researchHarvest' <#if annotateBook?if_exists.evaluateIdea?if_exists.researchHarvest?exists && annotateBook?if_exists.evaluateIdea?if_exists.researchHarvest?string=='C'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="D" name='annotateBook${idSeq}.evaluateIdea.researchHarvest' <#if annotateBook?if_exists.evaluateIdea?if_exists.researchHarvest?exists && annotateBook?if_exists.evaluateIdea?if_exists.researchHarvest?string=='D'>checked</#if>>&nbsp;</td>
    </tr>
    <tr align="center">
      <td align="left" class="darkColumn" id="f_operationLevel${idSeq}">业务水平<#if flag?exists&&flag=="book"><font color="red">*</font></#if>:</td>
      <td align="left">研究方法科学，体现出作者对本学科及相关领域坚实，宽广的理论基础与系统深入的专门知识，具有较强的独立从事科学研究的能力；</td>
      <td><input type="radio" value="A" name='annotateBook${idSeq}.evaluateIdea.operationLevel' <#if annotateBook?if_exists.evaluateIdea?if_exists.operationLevel?exists && annotateBook?if_exists.evaluateIdea?if_exists.operationLevel?string=='A'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="B" name='annotateBook${idSeq}.evaluateIdea.operationLevel' <#if annotateBook?if_exists.evaluateIdea?if_exists.operationLevel?exists && annotateBook?if_exists.evaluateIdea?if_exists.operationLevel?string=='B'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="C" name='annotateBook${idSeq}.evaluateIdea.operationLevel' <#if annotateBook?if_exists.evaluateIdea?if_exists.operationLevel?exists && annotateBook?if_exists.evaluateIdea?if_exists.operationLevel?string=='C'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="D" name='annotateBook${idSeq}.evaluateIdea.operationLevel' <#if annotateBook?if_exists.evaluateIdea?if_exists.operationLevel?exists && annotateBook?if_exists.evaluateIdea?if_exists.operationLevel?string=='D'>checked</#if>>&nbsp;</td>
    </tr>
    <tr align="center">
      <td align="left" class="darkColumn" id="f_composeAbility${idSeq}">写作能力与学风<#if flag?exists&&flag=="book"><font color="red">*</font></#if>:</td>
      <td align="left">材料翔实，结构严谨，推理严密，结论正确；文字表达准确，流畅；图表规范；学风严谨；</td>
      <td><input type="radio" value="A" name='annotateBook${idSeq}.evaluateIdea.composeAbility' <#if annotateBook?if_exists.evaluateIdea?if_exists.composeAbility?exists &&annotateBook?if_exists.evaluateIdea?if_exists.composeAbility?string=='A'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="B" name='annotateBook${idSeq}.evaluateIdea.composeAbility' <#if annotateBook?if_exists.evaluateIdea?if_exists.composeAbility?exists &&annotateBook?if_exists.evaluateIdea?if_exists.composeAbility?string=='B'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="C" name='annotateBook${idSeq}.evaluateIdea.composeAbility' <#if annotateBook?if_exists.evaluateIdea?if_exists.composeAbility?exists &&annotateBook?if_exists.evaluateIdea?if_exists.composeAbility?string=='C'>checked</#if>>&nbsp;</td>
      <td><input type="radio" value="D" name='annotateBook${idSeq}.evaluateIdea.composeAbility' <#if annotateBook?if_exists.evaluateIdea?if_exists.composeAbility?exists &&annotateBook?if_exists.evaluateIdea?if_exists.composeAbility?string=='D'>checked</#if>>&nbsp;</td>
    </tr>
    <tr align="center">
      <td align="left" class="darkColumn" id="f_thesisMark${idSeq}">论文分数<#if flag?exists&&flag=="book"><font color="red">*</font></#if>:</td>
      <td align="left">总评分---百分制<br>100-90为优秀；89-75为良好；74-60为一般；60分以下不合格。</td>
      <td colspan="4"><input type="text" name="annotateBook${idSeq}.evaluateIdea.mark" style="width:100%;border:1 solid #000000;" value="${annotateBook?if_exists.evaluateIdea?if_exists.mark?if_exists}"></td>
    </tr>
  </table>