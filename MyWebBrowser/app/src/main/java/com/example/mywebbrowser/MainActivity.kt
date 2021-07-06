package com.example.mywebbrowser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var urlEditText: EditText = findViewById(R.id.urlEditText)
        webView = findViewById(R.id.webView)

        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }
        webView.loadUrl("http://www.google.com")

        urlEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                webView.loadUrl(urlEditText.text.toString())
                true
            } else {
                false
            }
        }

        registerForContextMenu(webView)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_google, R.id.action_home -> {
                webView.loadUrl("http://www.google.com")
                return true
            }
            R.id.action_naver -> {
                webView.loadUrl("http://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                webView.loadUrl("http://www.daum.com")
                return true
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:031-123-4567")
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("smsto:" + Uri.encode("012-3456-7890"))
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
            R.id.action_email -> {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:example@example.com")
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("text/plain")
                intent.putExtra(Intent.EXTRA_TEXT, webView.url.toString())
                val shareIntent = Intent.createChooser(intent, null)
                startActivity(shareIntent)
                return true
            }
            R.id.action_browser -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webView.url))
                startActivity(Intent.createChooser(intent, null))
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}