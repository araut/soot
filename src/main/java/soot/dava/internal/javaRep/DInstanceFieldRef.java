/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 Jerome Miecznikowski
 * Copyright (C) 2004 Ondrej Lhotak
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

package soot.dava.internal.javaRep;

import soot.SootFieldRef;
import soot.UnitPrinter;
import soot.Value;
import soot.grimp.internal.GInstanceFieldRef;

import java.util.HashSet;

public class DInstanceFieldRef extends GInstanceFieldRef {
  private HashSet<Object> thisLocals;

  public DInstanceFieldRef(Value base, SootFieldRef fieldRef, HashSet<Object> thisLocals) {
    super(base, fieldRef);

    this.thisLocals = thisLocals;
  }

  @Override
  public void toString(UnitPrinter up) {
    if (thisLocals.contains(getBase())) {
      up.fieldRef(fieldRef);
    } else {
      super.toString(up);
    }
  }

  @Override
  public String toString() {
    if (thisLocals.contains(getBase())) {
      return fieldRef.name();
    }

    return super.toString();
  }

  @Override
  public Object clone() {
    return new DInstanceFieldRef(getBase(), fieldRef, thisLocals);
  }
}
