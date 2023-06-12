package com.widget.arc.list

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ArcListAdapter(var onItemClickListener: OnItemClickListener?) : RecyclerView.Adapter<ArcListAdapter.ItemViewHolder>() {

    var firstVisiblePosition = 0

    var list: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_arc, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val mv = holder.itemView as RelativeLayout
        val marginTop = getMarginTop(position)
        mv.setPadding(0, marginTop, 0, 0)
        holder.mTvTitle.setText(list.get(position))
    }

    private fun getMarginTop(position: Int): Int {
        when(position - firstVisiblePosition) {
            0, 1, 2, 3, 4 -> {
                return calculateMarginTop(position - firstVisiblePosition)
            }
        }
        return calculateMarginTop( 5)
    }

    private fun calculateMarginTop(index: Int): Int {
        val display = Resources.getSystem().displayMetrics
        val widthPixel = display.widthPixels.toDouble()
        val itemWidth = dp2px(40f)
        val spaceWidth = (widthPixel - itemWidth * 5) / 10
        var marginLeft = spaceWidth * (1 + index * 2) + itemWidth * index
        // 偏离中点距离
        val offset = Math.abs((widthPixel / 2).minus(marginLeft.plus(itemWidth / 2)))
        // 直角三角形一条直角边
        val height = Math.sqrt(Math.pow(widthPixel * 2, 2.0) - Math.pow(offset, 2.0))
        val marginTop = widthPixel * 2 - height
        return marginTop.toInt()
    }

    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val mTvTitle: TextView = itemView.findViewById(R.id.mTvTitle)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onItemClickListener?.onItemClick(null, view, getAdapterPosition(), (view?.id ?:0).toLong());
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}