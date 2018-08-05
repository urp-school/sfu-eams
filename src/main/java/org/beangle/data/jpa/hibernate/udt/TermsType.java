package org.beangle.data.jpa.hibernate.udt;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import com.shufe.model.course.plan.Terms;

public class TermsType implements UserType {

  public int[] sqlTypes() {
    return new int[] { Types.INTEGER };
  }

  /**
   * @Override
   */
  public Class returnedClass() {
    return Terms.class;
  }

  /**
   * @Override
   */
  public boolean equals(Object x, Object y) {
    if (x instanceof Terms && y instanceof Terms) {
      return ((Terms) x).value == ((Terms) y).value;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode(Object x) {
    return x.hashCode();
  }

  @Override
  public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
      throws HibernateException, SQLException {
    short value = resultSet.getShort(names[0]);
    if (resultSet.wasNull()) return null;
    else return new Terms(value);
  }

  @Override
  public void nullSafeSet(PreparedStatement statement, Object value, int index)
      throws HibernateException, SQLException {
    if (value == null) {
      statement.setNull(index, Types.INTEGER);
    } else {
      statement.setInt(index, ((Terms) value).value);
    }
  }

  /**
   * @Override
   */
  public Object deepCopy(Object value) {
    return value;
  }

  /**
   * @Override
   */
  public boolean isMutable() {
    return false;
  }

  /**
   * @Override
   */
  public Serializable disassemble(Object value) {
    return (Serializable) value;
  }

  /**
   * @Override
   */
  public Object assemble(Serializable cached, Object owner) {
    return cached;
  }

  /**
   * @Override
   */
  public Object replace(Object original, Object target, Object owner) {
    return original;
  }
}
