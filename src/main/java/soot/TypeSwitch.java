/* Soot - a J*va Optimization Framework
 * Copyright (C) 1997-1999 Raja Vallee-Rai
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/*
 * Modified by the Sable Research Group and others 1997-1999.
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */

package soot;

/**
 * Implements Switchable on base Java types.
 */
public class TypeSwitch implements ITypeSwitch {
  Object result;

  @Override
  public void caseArrayType(ArrayType t) {
    defaultCase(t);
  }

  @Override
  public void caseBooleanType(BooleanType t) {
    defaultCase(t);
  }

  @Override
  public void caseByteType(ByteType t) {
    defaultCase(t);
  }

  @Override
  public void caseCharType(CharType t) {
    defaultCase(t);
  }

  @Override
  public void caseDoubleType(DoubleType t) {
    defaultCase(t);
  }

  @Override
  public void caseFloatType(FloatType t) {
    defaultCase(t);
  }

  @Override
  public void caseIntType(IntType t) {
    defaultCase(t);
  }

  @Override
  public void caseLongType(LongType t) {
    defaultCase(t);
  }

  @Override
  public void caseRefType(RefType t) {
    defaultCase(t);
  }

  @Override
  public void caseShortType(ShortType t) {
    defaultCase(t);
  }

  @Override
  public void caseStmtAddressType(StmtAddressType t) {
    defaultCase(t);
  }

  @Override
  public void caseUnknownType(UnknownType t) {
    defaultCase(t);
  }

  @Override
  public void caseVoidType(VoidType t) {
    defaultCase(t);
  }

  @Override
  public void caseAnySubType(AnySubType t) {
    defaultCase(t);
  }

  @Override
  public void caseNullType(NullType t) {
    defaultCase(t);
  }

  @Override
  public void caseErroneousType(ErroneousType t) {
    defaultCase(t);
  }

  public void defaultCase(Type t) {
  }

  /**
   * @see #defaultCase(Type) *
   * @deprecated Replaced by defaultCase(Type)
   */
  @Override
  @Deprecated
  public void caseDefault(Type t) {
  }

  public Object getResult() {
    return this.result;
  }

  public void setResult(Object result) {
    this.result = result;
  }
}
