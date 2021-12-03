package com.example.knowledgetree

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*

import androidx.compose.material.*

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.knowledgetree.ui.theme.lightYellow2
import com.example.knowledgetree.ui.theme.lightYellow3
import com.google.accompanist.pager.*
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Sample(screenWidth: Int) {

}


@Composable
internal fun PagerSampleItem(
) {
    RichText(
        modifier = Modifier.padding(16.dp)
    ) {
        Column() {

            Markdown(
                """
            # 这是大标题
            ## 这是二级的标题
                var s = "code blocks use monospace font";
                alert(s);
                """.trimIndent()
            )
            Image(
                painter = rememberImagePainter(
                    data = "https://hbimg.huabanimg.com/2412e4db74e63ea77e84d3f7c5bcc1b7e2a5e0d077cf-9bCCdD_fw658/format/webp",
                    onExecute = ImagePainter.ExecuteCallback { _, _ -> true },
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.demo)
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )

        }
    }
}


@ExperimentalFoundationApi
@Composable
fun BaseBox() {
    val colorOnPrimary = Color(255, 255, 255, 100)
    Box(
        modifier = Modifier
            .background(colorOnPrimary)

            //.background(color = Color.Red)
            .fillMaxSize()
    )
}