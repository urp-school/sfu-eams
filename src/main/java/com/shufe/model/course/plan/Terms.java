package com.shufe.model.course.plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Terms implements Serializable, Comparable<Terms> {
  private static final long serialVersionUID = -8846980902784025935L;

  private static int valuesOf(String terms) {
    int result = 0;
    for (String t : StringUtils.split(terms, ",")) {
      result |= (1 << Integer.parseInt(t));
    }
    return result;
  }

  public final int value;

  public Terms(int value) {
    super();
    this.value = value;
  }

  public Terms(String values) {
    this(valuesOf(values));
  }

  public boolean contains(int term) {
    return (value & (1 << term)) > 0;
  }

  public List<Integer> getTermList() {
    String str = Integer.toBinaryString(value);
    List<Integer> termList = new ArrayList<Integer>();
    if (value > 0) {
      for (int i = str.length() - 1; i >= 0; i--) {
        if (str.charAt(i) == '1') termList.add(str.length() - i - 1);
      }
      return termList;
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    List<Integer> terms = getTermList();
    for (Integer a : terms) {
      sb.append(a).append(',');
    }
    if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  @Override
  public int compareTo(Terms o) {
    return this.value - o.value;
  }

}
