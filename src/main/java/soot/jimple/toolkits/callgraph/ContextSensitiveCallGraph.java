/* Soot - a J*va Optimization Framework
 * Copyright (C) 2005 Ondrej Lhotak
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

package soot.jimple.toolkits.callgraph;

import soot.Context;
import soot.SootMethod;
import soot.Unit;

import java.util.Iterator;

/**
 * Represents a context-sensitive call graph for querying by client analyses.
 *
 * @author Ondrej Lhotak
 */
public interface ContextSensitiveCallGraph {
  /**
   * Returns all MethodOrMethodContext's (context,method pairs) that are the source of some edge.
   */
  Iterator edgeSources();

  /**
   * Returns all ContextSensitiveEdge's in the call graph.
   */
  Iterator allEdges();

  /**
   * Returns all ContextSensitiveEdge's out of unit srcUnit in method src in context srcCtxt.
   */
  Iterator edgesOutOf(Context srcCtxt, SootMethod src, Unit srcUnit);

  /**
   * Returns all ContextSensitiveEdge's out of method src in context srcCtxt.
   */
  Iterator edgesOutOf(Context srcCtxt, SootMethod src);

  /**
   * Returns all ContextSensitiveEdge's into method tgt in context tgtCtxt.
   */
  Iterator edgesInto(Context tgtCtxt, SootMethod tgt);
}
