/* Soot - a J*va Optimization Framework
 * Copyright (C) 2004 Navindra Umanee <navindra@cs.mcgill.ca>
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

package soot.shimple;

import soot.Body;
import soot.PointsToAnalysis;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.pointer.SideEffectAnalysis;
import soot.jimple.toolkits.scalar.UnreachableCodeEliminator;
import soot.shimple.toolkits.graph.GlobalValueNumberer;
import soot.shimple.toolkits.graph.SimpleGlobalValueNumberer;
import soot.toolkits.graph.Block;
import soot.toolkits.graph.BlockGraph;
import soot.toolkits.graph.BlockGraphConverter;
import soot.toolkits.graph.CytronDominanceFrontier;
import soot.toolkits.graph.DominanceFrontier;
import soot.toolkits.graph.DominatorTree;
import soot.toolkits.graph.DominatorsFinder;
import soot.toolkits.graph.ExceptionalBlockGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.HashReversibleGraph;
import soot.toolkits.graph.ReversibleGraph;
import soot.toolkits.graph.SimpleDominatorsFinder;
import soot.toolkits.graph.UnitGraph;

/**
 * @author Navindra Umanee
 */
public class DefaultShimpleFactory implements ShimpleFactory {
  protected final Body body;
  protected BlockGraph bg;
  protected UnitGraph ug;
  protected DominatorsFinder<Block> dFinder;
  protected DominatorTree<Block> dTree;
  protected DominanceFrontier<Block> dFrontier;
  protected PointsToAnalysis pta;
  protected CallGraph cg;
  protected SideEffectAnalysis sea;
  protected GlobalValueNumberer gvn;

  protected ReversibleGraph<Block> rbg;
  protected DominatorTree<Block> rdTree;
  protected DominanceFrontier<Block> rdFrontier;
  protected DominatorsFinder<Block> rdFinder;

  public DefaultShimpleFactory(Body body) {
    this.body = body;
  }

  @Override
  public void clearCache() {
    bg = null;
    ug = null;
    dFinder = null;
    dTree = null;
    dFrontier = null;
    pta = null;
    cg = null;
    sea = null;
    gvn = null;
    rbg = null;
    rdTree = null;
    rdFinder = null;
    rdFrontier = null;
  }

  public Body getBody() {
    if (body == null) {
      throw new RuntimeException("Assertion failed: Call setBody() first.");
    }

    return body;
  }

  @Override
  public ReversibleGraph<Block> getReverseBlockGraph() {
    if (rbg != null) {
      return rbg;
    }

    BlockGraph bg = getBlockGraph();
    rbg = new HashReversibleGraph<>(bg);
    rbg.reverse();
    return rbg;
  }

  @Override
  public DominatorsFinder<Block> getReverseDominatorsFinder() {
    if (rdFinder != null) {
      return rdFinder;
    }

    rdFinder = new SimpleDominatorsFinder<>(getReverseBlockGraph());
    return rdFinder;
  }

  @Override
  public DominatorTree<Block> getReverseDominatorTree() {
    if (rdTree != null) {
      return rdTree;
    }

    rdTree = new DominatorTree<>(getReverseDominatorsFinder());
    return rdTree;
  }

  @Override
  public DominanceFrontier<Block> getReverseDominanceFrontier() {
    if (rdFrontier != null) {
      return rdFrontier;
    }

    rdFrontier = new CytronDominanceFrontier<>(getReverseDominatorTree());
    return rdFrontier;
  }

  @Override
  public BlockGraph getBlockGraph() {
    if (bg != null) {
      return bg;
    }

    bg = new ExceptionalBlockGraph((ExceptionalUnitGraph) getUnitGraph());
    BlockGraphConverter.addStartStopNodesTo(bg);
    return bg;
  }

  @Override
  public UnitGraph getUnitGraph() {
    if (ug != null) {
      return ug;
    }

    UnreachableCodeEliminator.v().transform(getBody());

    ug = new ExceptionalUnitGraph(getBody());
    return ug;
  }

  @Override
  public DominatorsFinder<Block> getDominatorsFinder() {
    if (dFinder != null) {
      return dFinder;
    }

    dFinder = new SimpleDominatorsFinder<>(getBlockGraph());
    return dFinder;
  }

  @Override
  public DominatorTree<Block> getDominatorTree() {
    if (dTree != null) {
      return dTree;
    }

    dTree = new DominatorTree<>(getDominatorsFinder());
    return dTree;
  }

  @Override
  public DominanceFrontier<Block> getDominanceFrontier() {
    if (dFrontier != null) {
      return dFrontier;
    }

    dFrontier = new CytronDominanceFrontier<>(getDominatorTree());
    return dFrontier;
  }

  @Override
  public GlobalValueNumberer getGlobalValueNumberer() {
    if (gvn != null) {
      return gvn;
    }

    gvn = new SimpleGlobalValueNumberer(getBlockGraph());
    return gvn;
  }
}
