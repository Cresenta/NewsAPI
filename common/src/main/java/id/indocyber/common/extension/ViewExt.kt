package id.indocyber.common.extension

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.setGone() {
    this.visibility = View.GONE
}

fun ImageView.loadImage(link: String?) {
    link?.let {
        Glide.with(this)
            .load(link)
            .into(this)
    }
}