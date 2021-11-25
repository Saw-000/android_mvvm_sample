package com.example.mvvm_sample.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.DialogFragment
import com.example.mvvm_sample.R
import com.example.mvvm_sample.mvvm.uidata.home.AddCommentDialogCallbackData
import java.lang.Exception

class AddCommentDialog: DialogFragment() {

    private lateinit var editText: EditText
    private lateinit var imageContainer: LinearLayoutCompat
    private lateinit var mapView: View
    private lateinit var addResourceButton: ImageButton

    // コールバックリスナー オブジェクト
    private lateinit var listener: CallbackListener

    // コールバックリスナー
    interface CallbackListener {
        fun finishAddCommentDialog(data: AddCommentDialogCallbackData?)
    }

    companion object {
        // インスタンス取得
        fun newIntent() : AddCommentDialog {
            val args = Bundle().apply {

            }
            return AddCommentDialog().apply {
                arguments = args
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // コンテンツエリアのビュー作成
            val inflater = requireActivity().layoutInflater
            val rootView = inflater.inflate(R.layout.dialog_add_comment, null)
            builder.setView(rootView)

            // ダイアログ自体のボタンとタイトル設定
            builder
                .setTitle(R.string.add_comment_dialog_title)
                .setPositiveButton(R.string.add_comment_dialog_add_button_text) { dialog, id ->
                    val text = editText.text.toString()
                    val bmps = emptyList<Bitmap>()
                    listener.finishAddCommentDialog(
                        AddCommentDialogCallbackData(
                            text = text,
                            images = bmps
                        )
                    )
                }
                .setNegativeButton(R.string.add_comment_dialog_cancel_button_text) { dialog, id ->
                    listener.finishAddCommentDialog(null)
                }

            return builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            when {
                context is CallbackListener -> context
                parentFragment is CallbackListener -> parentFragment as CallbackListener
                else -> { throw Exception() }
            }
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }
}