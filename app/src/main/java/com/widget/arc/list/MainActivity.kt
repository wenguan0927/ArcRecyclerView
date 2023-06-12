package com.widget.arc.list

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var mArcAdapter: ArcListAdapter? = null
    private var mRVList: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRVList = findViewById(R.id.mRVList)

        mRVList?.setLayoutManager(
            LinearLayoutManager(
                baseContext,
                RecyclerView.HORIZONTAL,
                false
            )
        )

        mArcAdapter = ArcListAdapter(object: AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if (position > 1 && position < (mArcAdapter?.list?.size ?:0 - 2)) {
                    syncShow(position - 2)
                }
            }
        })
        mArcAdapter?.list?.add("")
        mArcAdapter?.list?.add("")
        mArcAdapter?.list?.add("1")
        mArcAdapter?.list?.add("2")
        mArcAdapter?.list?.add("3")
        mArcAdapter?.list?.add("4")
        mArcAdapter?.list?.add("5")
        mArcAdapter?.list?.add("6")
        mArcAdapter?.list?.add("7")
        mArcAdapter?.list?.add("8")
        mArcAdapter?.list?.add("9")
        mArcAdapter?.list?.add("10")
        mArcAdapter?.list?.add("11")
        mArcAdapter?.list?.add("12")
        mArcAdapter?.list?.add("13")
        mArcAdapter?.list?.add("14")
        mArcAdapter?.list?.add("15")
        mArcAdapter?.list?.add("")
        mArcAdapter?.list?.add("")
        mRVList?.adapter = mArcAdapter

        // 显示5个项目
        val display = resources.displayMetrics
        val widthPixel = display.widthPixels
        val itemWidth = mArcAdapter?.dp2px(40f) ?:0
        val spaceWidth = (widthPixel - itemWidth * 5) / 10
        mRVList?.addItemDecoration(ArcItemDecoration(spaceWidth))

        mRVList?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var isManualScroll = false
            override fun onScrollStateChanged(@NonNull recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        if (isManualScroll) {
                            var manager = recyclerView.layoutManager as LinearLayoutManager
                            var firstVisibleItemPosition = manager.findFirstVisibleItemPosition()

                            syncShow(firstVisibleItemPosition)
                        }
                        isManualScroll = false
                    }
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        isManualScroll = true
                    }
                }
            }

            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                for (i in 0 until recyclerView.childCount) {
                    val paddingTop: Int = calculateMargin(recyclerView.getChildAt(i).left)
                    recyclerView.getChildAt(i).setPadding(0, paddingTop, 0, 0)
                }
            }
        })
    }

    private fun calculateMargin(marginLeft: Int): Int {
        val display = resources.displayMetrics
        val widthPixel = display.widthPixels.toDouble()
        val halfItemWidth = mArcAdapter?.dp2px(20f) ?:0
        // 偏离中点距离
        val offset = Math.abs((widthPixel / 2).minus(marginLeft.plus(halfItemWidth)))
        // 直角三角形一条直角边
        val height = Math.sqrt(Math.pow(widthPixel * 2, 2.0) - Math.pow(offset, 2.0))
        val marginTop = widthPixel * 2 - height
        return marginTop.toInt()
    }

    private fun syncShow(position: Int) {
        mRVList?.smoothSnapToPosition(position)
        mArcAdapter?.firstVisiblePosition = position
        mArcAdapter?.notifyDataSetChanged()
    }

    fun RecyclerView.smoothSnapToPosition(
        position: Int,
        snapMode: Int = LinearSmoothScroller.SNAP_TO_START
    ) {
        val smoothScroller = object : LinearSmoothScroller(this.context) {
            override fun getVerticalSnapPreference(): Int = snapMode
            override fun getHorizontalSnapPreference(): Int = snapMode
            override fun calculateTimeForScrolling(dx: Int): Int {
                return 250
            }
        }
        smoothScroller.targetPosition = position
        layoutManager?.startSmoothScroll(smoothScroller)
    }

}
