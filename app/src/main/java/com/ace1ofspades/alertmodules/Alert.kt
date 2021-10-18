package com.ace1ofspades.alertmodule

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Placeholder
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class AlertActivity(val context: Context?) {
  fun alert(title: String, message: String) {
    var newMessage = message
    newMessage = newMessage.replace("&times;","")
    context?.let {
      val mDialogView = LayoutInflater.from(context).inflate(R.layout.alert_alert, null)
      val mBuilder = AlertDialog.Builder(context).setView(mDialogView).setTitle("").show()

      mBuilder.window?.setBackgroundDrawableResource(android.R.color.transparent)

      val titleText = mBuilder.findViewById<TextView>(R.id.alert_check_title)
      val messageText = mBuilder.findViewById<TextView>(R.id.alert_check_message)
      val okButton = mBuilder.findViewById<Button>(R.id.alert_check_ok_button)

      titleText.text = title
      messageText.text = newMessage

      okButton.setOnClickListener { mBuilder.dismiss() }
    }
  }
  fun alert(title: String, message: String, handler: () -> Unit) {
    var newMessage = message
    newMessage = newMessage.replace("&times;","")
    context?.let {
      val mDialogView = LayoutInflater.from(context).inflate(R.layout.alert_alert, null)
      val mBuilder = AlertDialog.Builder(context).setView(mDialogView).setTitle("").show()

      mBuilder.window?.setBackgroundDrawableResource(android.R.color.transparent)

      val titleText = mBuilder.findViewById<TextView>(R.id.alert_check_title)
      val messageText = mBuilder.findViewById<TextView>(R.id.alert_check_message)
      val okButton = mBuilder.findViewById<Button>(R.id.alert_check_ok_button)

      titleText.text = title
      messageText.text = newMessage

      mBuilder.setOnDismissListener { handler() }
      okButton.setOnClickListener {
        mBuilder.dismiss()
        // handler()
      }
    }
  }
  fun confirm(title: String, message: String, handler: (result: Boolean) -> Unit) {
    var newMessage = message
    newMessage = newMessage.replace("&times;","")
    context?.let {
      val mDialogView = LayoutInflater.from(context).inflate(R.layout.confirm_alert, null)
      val mBuilder = AlertDialog.Builder(context).setView(mDialogView).setTitle("").show()

      mBuilder.window?.setBackgroundDrawableResource(android.R.color.transparent)

      val titleText = mBuilder.findViewById<TextView>(R.id.alert_check_title)
      val messageText = mBuilder.findViewById<TextView>(R.id.alert_check_message)
      val okButton = mBuilder.findViewById<Button>(R.id.alert_check_ok_button)
      val cancelButton = mBuilder.findViewById<Button>(R.id.alert_check_cancel_button)

      titleText.text = title
      messageText.text = newMessage
      
      mBuilder.setOnDismissListener {
        handler(false)
      }

      okButton.setOnClickListener {
        handler(true)
        mBuilder.dismiss()
      }
      cancelButton.setOnClickListener {
        handler(false)
        mBuilder.dismiss()
      }
    }
  }
  fun promt(title: String, message: String, hint: String, inputType: Int, handler: (result: String) -> Unit) {
    var newMessage = message
    newMessage = newMessage.replace("&times;","")
    context?.let {
      val mDialogView = LayoutInflater.from(context).inflate(R.layout.promt_alert, null)
      val mBuilder = AlertDialog.Builder(context).setView(mDialogView).setTitle("").show()

      mBuilder.window?.setBackgroundDrawableResource(android.R.color.transparent)

      val titleText = mBuilder.findViewById<TextView>(R.id.alert_promt_title)
      val messageText = mBuilder.findViewById<TextView>(R.id.alert_promt_message)
      val okButton = mBuilder.findViewById<Button>(R.id.alert_promt_ok_button)
      val cancelButton = mBuilder.findViewById<Button>(R.id.alert_promt_cancel_button)
      val edittext = mBuilder.findViewById<EditText>(R.id.alert_promt_text_field)

      edittext.hint = hint
      edittext.inputType = inputType
      
      titleText.text = title
      messageText.text = newMessage

      okButton.setOnClickListener {
        handler(edittext.text.toString())
        mBuilder.dismiss()
      }
      cancelButton.setOnClickListener {
        handler("")
        mBuilder.dismiss()
      }
    }
  }
  fun html(htmlstring: String, message: String, buttonConfig: (button1:Button,button2:Button) -> Unit?, handler: (a: Boolean) -> Unit) {
    var newMessage = message
    newMessage = newMessage.replace("&times;","")
    context?.let {
      val mDialogView = LayoutInflater.from(context).inflate(R.layout.html_alert, null)
      val mBuilder = AlertDialog.Builder(context).setView(mDialogView).setTitle("").show()
      
      mBuilder.window?.setBackgroundDrawableResource(android.R.color.transparent)
      
      val webview = mBuilder.findViewById<WebView>(R.id.html_alert_webview)
      val messageText = mBuilder.findViewById<TextView>(R.id.html_alert_text)
      val button1 = mBuilder.findViewById<Button>(R.id.html_alert_done)
      val button2 = mBuilder.findViewById<Button>(R.id.html_alert_cancel)
      
      webview.loadData(htmlstring, "text/html; charset=utf-8", "UTF-8")
      messageText.text = newMessage
      buttonConfig(button1, button2)
      if (!button1.hasOnClickListeners()) {
        button1.setOnClickListener {
          mBuilder.dismiss()
          handler(true)
        }
      }
      if (!button2.hasOnClickListeners()) {
        button2.setOnClickListener {
          handler(false)
          mBuilder.dismiss()
        }
      }
    }
  }
  fun image(url: String, placeholder: Drawable?) {
    context?.let {
      val mDialogView = LayoutInflater.from(context).inflate(R.layout.image_alert, null)
      val mBuilder = AlertDialog.Builder(context).setView(mDialogView).setTitle("").show()
      mBuilder.window?.setBackgroundDrawableResource(android.R.color.transparent)
      
      val image = mBuilder.findViewById<ImageView>(R.id.image_image)
      val okButton = mBuilder.findViewById<CardView>(R.id.image_close)
      context.let {
        Glide.with(it)
          .load(url)
          .apply(RequestOptions().placeholder(placeholder))
          .into(image)
      }
      okButton.setOnClickListener { mBuilder.dismiss() }
    }
  }
  fun noConnection(title: String, message: String, handler: (cancel:Boolean) -> Unit) {
    var newMessage = message
    newMessage = newMessage.replace("&times;","")
    context?.let {
      val mDialogView = LayoutInflater.from(context).inflate(R.layout.alert_alert, null)
      val mBuilder = AlertDialog.Builder(context).setView(mDialogView).setTitle("").show()
      mBuilder.window?.setBackgroundDrawableResource(android.R.color.transparent)

      val titleText = mBuilder.findViewById<TextView>(R.id.alert_check_title)
      val messageText = mBuilder.findViewById<TextView>(R.id.alert_check_message)
      val line = mBuilder.findViewById<ConstraintLayout>(R.id.constraintLayout)
      val okButton = mBuilder.findViewById<Button>(R.id.alert_check_ok_button)

      titleText.text = title
      messageText.text = newMessage
      okButton.text = "Yenile"

      line.setBackgroundColor(context.resources.getColor(R.color.ky_normal))
      titleText.setTextColor(context.resources.getColor(R.color.ky_normal))
      messageText.setTextColor(context.resources.getColor(R.color.ky_normal))
      var back = false
      mBuilder.setOnKeyListener { dialogInterface, i, event ->
        if (i == KeyEvent.KEYCODE_BACK &&
            event.action == KeyEvent.ACTION_UP &&
            !event.isCanceled
        ) {
              back = true
            dialogInterface.cancel()
            return@setOnKeyListener true
          }
        return@setOnKeyListener false
      }
      mBuilder.setOnDismissListener { handler(back) }
      okButton.setOnClickListener {
        mBuilder.dismiss()
        // handler()
      }
    }
  }
}
