package com.example.grapher

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grapher.recyclerview.RecyclerViewAdapter
import com.example.grapher.recyclerview.RvItem
import com.softmoore.android.graphlib.Graph
import com.softmoore.android.graphlib.Point
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_view_item_list.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //empty graph
        graph_view.setGraph(Graph.Builder().build())
        //recycler view
        rvEquation.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<RvItem>()
        data.add(RvItem(0,"(2,3)"))
       // data.add(RvItem(0,"y = x"))
        val adapter = RecyclerViewAdapter(data)
        rvEquation.adapter = adapter
        /*var graph = Graph.Builder().build()
        graph_view.setGraph(graph)
        title = "EMPTY GRAPH"

        var graph1 = Graph.Builder().addFunction { x -> x * x }.build()
        graph_view.setGraph(graph1)
        var graph2 = Graph.Builder().addFunction { x -> x * x }
                //how much of graph to show
            .setWorldCoordinates(-5.0, 5.0, -2.0, 40.0)
            //marking on graph
            .setXTicks(arrayListOf(-4.0, -3.0, -2.0, -1.0, 1.0, 2.0, 3.0, 4.0))
            .setYTicks(arrayListOf(2.0,4.0,6.0,8.0,10.0, 12.0, 14.0, 16.0))
            .build()
        graph_view.setGraph(graph2)

        var graph4 = Graph.Builder()
            .setWorldCoordinates(-1.0, 10.0, 100.0, 700.0)
                //starting point??
            .setAxes(0.0, 130.0)
            .setXTicks(doubleArrayOf(2.0, 4.0, 6.0, 8.0))
            .setYTicks(doubleArrayOf(200.0, 300.0, 400.0, 500.0, 600.0))
            .addFunction { x -> 83 * x - 38 }
                //points on graph
            .addPoints(
                arrayOf(
                    Point(2.0, 150.0), Point(4.0, 287.0),
                    Point(6.0, 408.0), Point(8.0, 662.0)
                ), Color.RED
            )
            .build()
        graph_view.setGraph(graph4)

        //add line graph
        val points1 = arrayListOf(
             Point(-10.0, 3.0),  Point(-8.0, 4.0),   Point(5.0, 2.0),
             Point(0.0, 0.0),    Point(2.0, -6.0),   Point(3.0,3.0),
             Point(7.0,5.0),     Point(9.0, 9.0),    Point(12.0, 6.0)
        )
        val graph5 = Graph.Builder()
            .addLineGraph(points1,Color.RED)
            .build()
        graph_view.setGraph(graph5)


        val points2 = arrayOf(
            Point(1.0, 178.0), Point(2.0, 179.0), Point(3.0, 179.0),
            Point(4.0, 181.0), Point(5.0, 180.0), Point(6.0, 182.0),
            Point(7.0, 182.0), Point(8.0, 184.0), Point(9.0, 183.0),
            Point(10.0, 185.0), Point(11.0, 185.0), Point(12.0, 186.0)
        )
        val xLabels = arrayOf(
            Label(1.0, "J"), Label(2.0, "F"), Label(3.0, "M"),
            Label(4.0, "A"), Label(5.0, "M"), Label(6.0, "J"),
            Label(7.0, "J"), Label(8.0, "A"), Label(9.0, "S"),
            Label(10.0, "O"), Label(11.0, "N"), Label(12.0, "D")
        )
        val graph6 = Graph.Builder()
            .setWorldCoordinates(-2.0, 13.0, 165.0, 191.0)
            .setAxes(0.0, 167.0)
            .setXLabels(xLabels)
            .setYTicks(doubleArrayOf(170.0, 175.0, 180.0, 185.0, 190.0))
            .addFunction(Function { x: Double -> 170.0 }, Color.GREEN)
            .addLineGraph(points2, Color.RED)
            .build()
        graph_view.setGraph(graph6)*/

       /* val patterns = arrayListOf(
            //points
            Regex("\\([0-9]+,[0-9]+\\)")
        )
        if(patterns[0].matches("(2,3)")){
            val point = "(2,3)"
            val n1  = point.substring(1,point.indexOf(",")).toDouble()
            val n2 = point.substring(point.indexOf(",") + 1,point.length - 1).toDouble()
            val x = Graph.Builder().addPoints(arrayOf(Point(n1,n2))).build()
            graph_view.setGraph(x)
        }*/
        class PlotGraph(){
            val patterns = arrayListOf(
                //points
                Regex("\\([0-9]+,[0-9]+\\)")
            )
            fun type() {
                if(patterns[0].matches("(2,3)")){
                    addPoints("(2,3)")
                }
            }
            fun addPoints(point: String) {
                val n1  = point.substring(1,point.indexOf(",")).toDouble()
                val n2 = point.substring(point.indexOf(",") + 1,point.length - 1).toDouble()
                graph_view.setGraph(Graph.Builder().addPoints(arrayOf(Point(n1,n2))).build())
            }
        }
        PlotGraph().type()
    }

}
