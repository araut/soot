/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 Ondrej Lhotak
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

package soot.jimple.spark.solver;

import soot.G;
import soot.jimple.spark.pag.AllocDotField;
import soot.jimple.spark.pag.AllocNode;
import soot.jimple.spark.pag.FieldRefNode;
import soot.jimple.spark.pag.Node;
import soot.jimple.spark.pag.PAG;
import soot.jimple.spark.pag.VarNode;
import soot.jimple.spark.sets.P2SetVisitor;
import soot.util.LargeNumberedMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Propagates points-to sets using an on-line cycle detection algorithm based on Heintze and
 * Tardieu, PLDI 2000.
 *
 * @author Ondrej Lhotak
 */
public final class PropCycle extends Propagator {
  private final LargeNumberedMap<VarNode, Integer> varNodeToIteration;
  private PAG pag;

  /* End of public methods. */
  /* End of package methods. */
  private OnFlyCallGraph ofcg;
  private Integer currentIteration;
  public PropCycle(PAG pag) {
    this.pag = pag;
    varNodeToIteration = new LargeNumberedMap<>(pag.getVarNodeNumberer());
  }

  /**
   * Actually does the propagation.
   */
  @Override
  public final void propagate() {
    ofcg = pag.getOnFlyCallGraph();
    boolean verbose = pag.getOpts().verbose();
    Collection<VarNode> bases = new HashSet<>();
    for (FieldRefNode frn : pag.getFieldRefNodeNumberer()) {
      bases.add(frn.getBase());
    }
    bases = new ArrayList<>(bases);
    int iteration = 0;
    boolean changed;
    boolean finalIter = false;
    do {
      changed = false;
      iteration++;
      currentIteration = new Integer(iteration);
      if (verbose) {
        G.v().out.println("Iteration: " + iteration);
      }
      for (VarNode v : bases) {
        changed = computeP2Set((VarNode) v.getReplacement(), new ArrayList<VarNode>()) | changed;
      }
      if (ofcg != null) {
        throw new RuntimeException("NYI");
      }
      if (verbose) {
        G.v().out.println("Processing stores");
      }
      for (Object object : pag.storeSources()) {
        final VarNode src = (VarNode) object;
        Node[] targets = pag.storeLookup(src);
        for (Node element0 : targets) {
          final FieldRefNode target = (FieldRefNode) element0;
          changed =
              target
                  .getBase()
                  .makeP2Set()
                  .forall(
                      new P2SetVisitor() {
                        @Override
                        public final void visit(Node n) {
                          AllocDotField nDotF =
                              pag.makeAllocDotField((AllocNode) n, target.getField());
                          nDotF.makeP2Set().addAll(src.getP2Set(), null);
                        }
                      })
                  | changed;
        }
      }
      if (!changed && !finalIter) {
        finalIter = true;
        if (verbose) {
          G.v().out.println("Doing full graph");
        }
        bases = new ArrayList<>(pag.getVarNodeNumberer().size());
        for (VarNode v : pag.getVarNodeNumberer()) {
          bases.add(v);
        }
        changed = true;
      }
    } while (changed);
  }

  private boolean computeP2Set(final VarNode v, ArrayList<VarNode> path) {
    boolean ret = false;

    if (path.contains(v)) {
      // for( Iterator<VarNode> nIt = path.iterator(); nIt.hasNext(); ) {
      // final Node n = nIt.next();
      // if( n != v ) n.mergeWith( v );
      // }
      return false;
    }

    final Integer vnIteration = varNodeToIteration.get(v);
    if (currentIteration != null
        && vnIteration != null
        && currentIteration.intValue() == vnIteration.intValue()) {
      return false;
    }

    varNodeToIteration.put(v, currentIteration);

    path.add(v);
    if (v.getP2Set().isEmpty()) {
      Node[] srcs = pag.allocInvLookup(v);
      for (Node element : srcs) {
        ret = v.makeP2Set().add(element) | ret;
      }
    }
    {
      Node[] srcs = pag.simpleInvLookup(v);
      for (Node element : srcs) {
        VarNode src = (VarNode) element;
        ret = computeP2Set(src, path) | ret;
        ret = v.makeP2Set().addAll(src.getP2Set(), null) | ret;
      }
    }
    {
      Node[] srcs = pag.loadInvLookup(v);
      for (Node element : srcs) {
        final FieldRefNode src = (FieldRefNode) element;
        ret =
            src.getBase()
                .getP2Set()
                .forall(
                    new P2SetVisitor() {
                      @Override
                      public final void visit(Node n) {
                        AllocNode an = (AllocNode) n;
                        AllocDotField adf = pag.makeAllocDotField(an, src.getField());
                        returnValue = v.makeP2Set().addAll(adf.getP2Set(), null) | returnValue;
                      }
                    })
                | ret;
      }
    }
    path.remove(path.size() - 1);
    return ret;
  }
}
