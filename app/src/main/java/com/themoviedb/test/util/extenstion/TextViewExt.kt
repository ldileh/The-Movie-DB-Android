package com.themoviedb.test.util.extenstion

import android.os.Build
import android.text.Html
import android.widget.TextView


fun TextView.html(htmlString: String){
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(
            htmlString,
            Html.FROM_HTML_MODE_LEGACY
        )
    } else {
        Html.fromHtml(htmlString)
    }
}