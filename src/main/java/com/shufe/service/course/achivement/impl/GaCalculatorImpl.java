package com.shufe.service.course.achivement.impl;

import com.shufe.model.course.achivement.GradeAchivement;
import com.shufe.service.course.achivement.GaCalculator;

public class GaCalculatorImpl implements GaCalculator {

  /**
   * 德育×20%+智育×70%+体育×10%
   */
  @Override
  public Float calc(GradeAchivement ga) {
    float score = 0;
    if (null != ga.getMoralScore()) score += ga.getMoralScore() * 0.20;
    if (null != ga.getIeScore()) score += ga.getIeScore() * 0.70;
    if (null != ga.getPeScore()) score += ga.getPeScore() * 0.10;
    return score;
  }

}
