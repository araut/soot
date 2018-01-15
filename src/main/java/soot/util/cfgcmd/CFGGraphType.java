/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 John Jorgensen
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

package soot.util.cfgcmd;

import soot.Body;
import soot.G;
import soot.toolkits.graph.ArrayRefBlockGraph;
import soot.toolkits.graph.BriefBlockGraph;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.ClassicCompleteBlockGraph;
import soot.toolkits.graph.ClassicCompleteUnitGraph;
import soot.toolkits.graph.CompleteBlockGraph;
import soot.toolkits.graph.CompleteUnitGraph;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalBlockGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.TrapUnitGraph;
import soot.toolkits.graph.ZonedBlockGraph;
import soot.util.dot.DotGraph;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * An enumeration type for representing the varieties of control flow graph available, for use in
 * tools that compare or display CFGs.
 */
public abstract class CFGGraphType extends CFGOptionMatcher.CFGOption {

  public static final CFGGraphType BRIEF_UNIT_GRAPH =
      new CFGGraphType("BriefUnitGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return new BriefUnitGraph(b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  public static final CFGGraphType EXCEPTIONAL_UNIT_GRAPH =
      new CFGGraphType("ExceptionalUnitGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return new ExceptionalUnitGraph(b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG((ExceptionalUnitGraph) g);
        }
      };
  public static final CFGGraphType COMPLETE_UNIT_GRAPH =
      new CFGGraphType("CompleteUnitGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return new CompleteUnitGraph(b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG((CompleteUnitGraph) g);
        }
      };
  public static final CFGGraphType TRAP_UNIT_GRAPH =
      new CFGGraphType("TrapUnitGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return new TrapUnitGraph(b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  public static final CFGGraphType CLASSIC_COMPLETE_UNIT_GRAPH =
      new CFGGraphType("ClassicCompleteUnitGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return new ClassicCompleteUnitGraph(b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  public static final CFGGraphType BRIEF_BLOCK_GRAPH =
      new CFGGraphType("BriefBlockGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return new BriefBlockGraph(b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  public static final CFGGraphType EXCEPTIONAL_BLOCK_GRAPH =
      new CFGGraphType("ExceptionalBlockGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return new ExceptionalBlockGraph(b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG((ExceptionalBlockGraph) g);
        }
      };
  public static final CFGGraphType COMPLETE_BLOCK_GRAPH =
      new CFGGraphType("CompleteBlockGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return new CompleteBlockGraph(b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  public static final CFGGraphType CLASSIC_COMPLETE_BLOCK_GRAPH =
      new CFGGraphType("ClassicCompleteBlockGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return new ClassicCompleteBlockGraph(b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  public static final CFGGraphType ARRAY_REF_BLOCK_GRAPH =
      new CFGGraphType("ArrayRefBlockGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return new ArrayRefBlockGraph(b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  public static final CFGGraphType ZONED_BLOCK_GRAPH =
      new CFGGraphType("ZonedBlockGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return new ZonedBlockGraph(b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  private static final boolean DEBUG = true;
  public static final CFGGraphType ALT_BRIEF_UNIT_GRAPH =
      new CFGGraphType("AltBriefUnitGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return loadAltGraph("soot.toolkits.graph.BriefUnitGraph", b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  public static final CFGGraphType ALT_COMPLETE_UNIT_GRAPH =
      new CFGGraphType("AltCompleteUnitGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return loadAltGraph("soot.toolkits.graph.CompleteUnitGraph", b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  public static final CFGGraphType ALT_TRAP_UNIT_GRAPH =
      new CFGGraphType("AltTrapUnitGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return loadAltGraph("soot.toolkits.graph.TrapUnitGraph", b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  public static final CFGGraphType ALT_ARRAY_REF_BLOCK_GRAPH =
      new CFGGraphType("AltArrayRefBlockGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return loadAltGraph("soot.toolkits.graph.ArrayRefBlockGraph", b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  public static final CFGGraphType ALT_BRIEF_BLOCK_GRAPH =
      new CFGGraphType("AltBriefBlockGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return loadAltGraph("soot.toolkits.graph.BriefBlockGraph", b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  public static final CFGGraphType ALT_COMPLETE_BLOCK_GRAPH =
      new CFGGraphType("AltCompleteBlockGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return loadAltGraph("soot.toolkits.graph.CompleteBlockGraph", b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  public static final CFGGraphType ALT_ZONED_BLOCK_GRAPH =
      new CFGGraphType("AltZonedBlockGraph") {
        @Override
        public DirectedGraph buildGraph(Body b) {
          return loadAltGraph("soot.toolkits.graph.ZonedBlockGraph", b);
        }

        @Override
        public DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b) {
          return drawer.drawCFG(g, b);
        }
      };
  private static final CFGOptionMatcher graphTypeOptions =
      new CFGOptionMatcher(
          new CFGGraphType[] {
              BRIEF_UNIT_GRAPH,
              EXCEPTIONAL_UNIT_GRAPH,
              COMPLETE_UNIT_GRAPH,
              TRAP_UNIT_GRAPH,
              CLASSIC_COMPLETE_UNIT_GRAPH,
              BRIEF_BLOCK_GRAPH,
              EXCEPTIONAL_BLOCK_GRAPH,
              COMPLETE_BLOCK_GRAPH,
              CLASSIC_COMPLETE_BLOCK_GRAPH,
              ARRAY_REF_BLOCK_GRAPH,
              ZONED_BLOCK_GRAPH,
              ALT_ARRAY_REF_BLOCK_GRAPH,
              ALT_BRIEF_UNIT_GRAPH,
              ALT_COMPLETE_UNIT_GRAPH,
              ALT_TRAP_UNIT_GRAPH,
              ALT_BRIEF_BLOCK_GRAPH,
              ALT_COMPLETE_BLOCK_GRAPH,
              ALT_ZONED_BLOCK_GRAPH,
          });

  private CFGGraphType(String name) {
    super(name);
  }

  /**
   * Returns the <code>CFGGraphType</code> identified by the passed name.
   *
   * @param name A {@link String} identifying the graph type.
   * @return A {@link CFGGraphType} object whose {@link #buildGraph()} method will create the
   * desired sort of control flow graph and whose {@link #drawGraph} method will produce a
   * {@link DotGraph} corresponding to the graph.
   */
  public static CFGGraphType getGraphType(String option) {
    return (CFGGraphType) graphTypeOptions.match(option);
  }

  /**
   * Returns a string containing the names of all the available {@link CFGGraphType}s, separated by
   * '|' characters.
   *
   * @param initialIndent The number of blank spaces to insert at the beginning of the returned
   *                      string. Ignored if negative.
   * @param rightMargin   If positive, newlines will be inserted to try to keep the length of each
   *                      line in the returned string less than or equal to <code>rightMargin</code>.
   * @param hangingIndent If positive, this number of spaces will be inserted immediately after each
   *                      newline inserted to respect the <code>rightMargin</code>.
   */
  public static String help(int initialIndent, int rightMargin, int hangingIndent) {
    return graphTypeOptions.help(initialIndent, rightMargin, hangingIndent);
  }

  private static DirectedGraph loadAltGraph(String className, Body b) {
    try {
      Class<?> graphClass = AltClassLoader.v().loadClass(className);
      Class<?>[] paramTypes = new Class[] {Body.class};
      Constructor constructor = graphClass.getConstructor(paramTypes);
      DirectedGraph result = (DirectedGraph) constructor.newInstance(new Object[] {b});
      return result;
    }
    // Turn class loading exceptions into RuntimeExceptions, so callers
    // don't need to declare them:  perhaps a shoddy tactic.
    catch (ClassNotFoundException e) {
      if (DEBUG) {
        e.printStackTrace(G.v().out);
      }
      throw new IllegalArgumentException(
          "Unable to find " + className + " in alternate classpath: " + e.getMessage());
    } catch (NoSuchMethodException e) {
      if (DEBUG) {
        e.printStackTrace(G.v().out);
      }
      throw new IllegalArgumentException(
          "There is no " + className + "(Body) constructor: " + e.getMessage());
    } catch (InstantiationException e) {
      if (DEBUG) {
        e.printStackTrace(G.v().out);
      }
      throw new IllegalArgumentException(
          "Unable to instantiate " + className + " in alternate classpath: " + e.getMessage());
    } catch (IllegalAccessException e) {
      if (DEBUG) {
        e.printStackTrace(G.v().out);
      }
      throw new IllegalArgumentException(
          "Unable to access " + className + "(Body) in alternate classpath: " + e.getMessage());
    } catch (InvocationTargetException e) {
      if (DEBUG) {
        e.printStackTrace(G.v().out);
      }
      throw new IllegalArgumentException(
          "Unable to invoke " + className + "(Body) in alternate classpath: " + e.getMessage());
    }
  }

  /**
   * Method that will build a graph of this type.
   *
   * @param b The method <code>Body</code> from which to build the graph.
   * @return The control flow graph corresponding to <code>b</code>
   */
  public abstract DirectedGraph buildGraph(Body b);

  /**
   * Method that will draw a {@link DotGraph} representation of the control flow in this type of
   * graph. This method is intended for use within {@link soot.tools.CFGViewer CFGViewer}.
   *
   * @param drawer The {@link soot.util.cfgcmd.CFGToDotGraph} object that will draw the graph.
   * @param g      The graph to draw.
   * @param b      The body associated with the graph, <code>g</code>.
   * @return a <code>DotGraph</code> visualizing the control flow in <code>g</code>.
   */
  public abstract DotGraph drawGraph(CFGToDotGraph drawer, DirectedGraph g, Body b);
}
