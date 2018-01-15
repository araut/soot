package soot.jbco.bafTransformations;

import soot.Body;
import soot.BodyTransformer;
import soot.Local;
import soot.PatchingChain;
import soot.Trap;
import soot.Unit;
import soot.baf.PushInst;
import soot.baf.StoreInst;
import soot.jbco.IJbcoTransform;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.util.Chain;

import java.util.Iterator;
import java.util.Map;

/**
 * @author Michael Batchelder
 * <p>Created on 16-Jun-2006
 */
public class RemoveRedundantPushStores extends BodyTransformer implements IJbcoTransform {

  public static String dependancies[] = new String[] {"bb.jbco_rrps"};
  public static String name = "bb.jbco_rrps";

  @Override
  public String[] getDependancies() {
    return dependancies;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void outputSummary() {
  }

  @Override
  protected void internalTransform(Body b, String phaseName, Map<String, String> options) {
    // removes all redundant load-stores
    boolean changed = true;
    PatchingChain<Unit> units = b.getUnits();
    while (changed) {
      changed = false;
      Unit prevprevprev = null, prevprev = null, prev = null;
      ExceptionalUnitGraph eug = new ExceptionalUnitGraph(b);
      Iterator<Unit> it = units.snapshotIterator();
      while (it.hasNext()) {
        Unit u = it.next();
        if (prev != null && prev instanceof PushInst && u instanceof StoreInst) {
          if (prevprev != null
              && prevprev instanceof StoreInst
              && prevprevprev != null
              && prevprevprev instanceof PushInst) {
            Local lprev = ((StoreInst) prevprev).getLocal();
            Local l = ((StoreInst) u).getLocal();
            if (l == lprev
                && eug.getSuccsOf(prevprevprev).size() == 1
                && eug.getSuccsOf(prevprev).size() == 1) {
              fixJumps(prevprevprev, prev, b.getTraps());
              fixJumps(prevprev, u, b.getTraps());
              units.remove(prevprevprev);
              units.remove(prevprev);
              changed = true;
              break;
            }
          }
        }
        prevprevprev = prevprev;
        prevprev = prev;
        prev = u;
      }
    } // end while changes have been made
  }

  private void fixJumps(Unit from, Unit to, Chain<Trap> t) {
    from.redirectJumpsToThisTo(to);
    for (Trap trap : t) {
      if (trap.getBeginUnit() == from) {
        trap.setBeginUnit(to);
      }
      if (trap.getEndUnit() == from) {
        trap.setEndUnit(to);
      }
      if (trap.getHandlerUnit() == from) {
        trap.setHandlerUnit(to);
      }
    }
  }
}
