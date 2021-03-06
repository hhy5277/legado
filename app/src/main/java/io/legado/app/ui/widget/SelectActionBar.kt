package io.legado.app.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.Menu
import android.view.View
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.PopupMenu
import io.legado.app.R
import io.legado.app.utils.dp
import io.legado.app.utils.visible
import kotlinx.android.synthetic.main.view_select_action_bar.view.*
import org.jetbrains.anko.sdk27.listeners.onClick

class SelectActionBar(context: Context, attrs: AttributeSet?) : LinearLayoutCompat(context, attrs) {
    private var callBack: CallBack? = null
    private var selMenu: PopupMenu? = null

    init {
        setBackgroundResource(R.color.background_menu)
        setPadding(16.dp, 6.dp, 16.dp, 6.dp)
        gravity = Gravity.CENTER_VERTICAL
        View.inflate(context, R.layout.view_select_action_bar, this)
        cb_selected_all.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                callBack?.selectAll(isChecked)
            }
        }
        btn_revert_selection.onClick { callBack?.revertSelection() }
        btn_select_action_main.onClick { callBack?.onClickMainAction() }
        iv_menu_more.onClick { selMenu?.show() }
    }

    fun setMainActionText(text: String) {
        btn_select_action_main.text = text
        btn_select_action_main.visible()
    }

    fun setMainActionText(@StringRes id: Int) {
        btn_select_action_main.setText(id)
        btn_select_action_main.visible()
    }

    fun inflateMenu(@MenuRes resId: Int): Menu? {
        selMenu = PopupMenu(context, iv_menu_more)
        selMenu?.inflate(resId)
        iv_menu_more.visible()
        return selMenu?.menu
    }

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }

    fun setOnMenuItemClickListener(listener: PopupMenu.OnMenuItemClickListener) {
        selMenu?.setOnMenuItemClickListener(listener)
    }

    fun upCountView(selectCount: Int, allCount: Int) {
        if (selectCount == 0) {
            cb_selected_all.isChecked = false
        } else {
            cb_selected_all.isChecked = selectCount >= allCount
        }

        //重置全选的文字
        if (cb_selected_all.isChecked) {
            cb_selected_all.text = context.getString(
                R.string.select_cancel_count,
                selectCount,
                allCount
            )
        } else {
            cb_selected_all.text = context.getString(
                R.string.select_all_count,
                selectCount,
                allCount
            )
        }
        setMenuClickable(selectCount > 0)
    }

    private fun setMenuClickable(isClickable: Boolean) {
        btn_revert_selection.isEnabled = isClickable
        btn_revert_selection.isClickable = isClickable
        btn_select_action_main.isEnabled = isClickable
        btn_select_action_main.isClickable = isClickable
        iv_menu_more.isEnabled = isClickable
        iv_menu_more.isClickable = isClickable
    }

    interface CallBack {

        fun selectAll(selectAll: Boolean)

        fun revertSelection()

        fun onClickMainAction() {}
    }
}