package com.voidx.spectable.widget.roundmenu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.voidx.spectable.R

class RoundMenu @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val ivMenuIcon
        get() = findViewById<ImageView>(R.id.menu_icon)

    private val tvMenuTitle
        get() = findViewById<TextView>(R.id.menu_title)

    private val tvMenuDescription
        get() = findViewById<TextView>(R.id.menu_description)

    init {
        LayoutInflater.from(context).inflate(R.layout.widget_round_menu,this, true)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundMenu, defStyleAttr, 0)

        ivMenuIcon.setImageDrawable(typedArray.getDrawable(R.styleable.RoundMenu_app_menuIcon))

        tvMenuTitle.text = typedArray.getText(R.styleable.RoundMenu_app_menuTitle)
        tvMenuDescription.text = typedArray.getText(R.styleable.RoundMenu_app_menuDescription)

        typedArray.recycle()
    }
}
