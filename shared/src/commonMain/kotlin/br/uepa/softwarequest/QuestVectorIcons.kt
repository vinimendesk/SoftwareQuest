package br.uepa.softwarequest

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/** Ícones vetoriais compartilhados: não exigem bibliotecas de ícones Android. */
internal enum class QuestGlyph {
    PERSON, MAIL, LOCK, EYE, EYE_CLOSED, CHEVRON_RIGHT, PENCIL,
    BELL, SHIELD, HELP, LOGOUT, MEDAL, SPARKLE, ARROW_LEFT,
}

@Composable
internal fun QuestLineIcon(
    glyph: QuestGlyph,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF9CA8C3),
) {
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val sw = 1.8.dp.toPx()
        fun p(x: Float, y: Float) = Offset(w * x, h * y)
        fun line(x1: Float, y1: Float, x2: Float, y2: Float) =
            drawLine(color, p(x1, y1), p(x2, y2), sw, StrokeCap.Round)
        fun outline(path: Path) = drawPath(path, color, style = Stroke(sw, cap = StrokeCap.Round, join = StrokeJoin.Round))
        when (glyph) {
            QuestGlyph.PERSON -> {
                drawCircle(color, w * .16f, p(.50f, .32f), style = Stroke(sw))
                drawArc(color, 202f, 136f, false, p(.17f, .47f), Size(w * .66f, h * .54f),
                    style = Stroke(sw, cap = StrokeCap.Round))
            }
            QuestGlyph.MAIL -> {
                drawRoundRect(color, p(.10f, .20f), Size(w * .80f, h * .60f),
                    CornerRadius(w * .07f), style = Stroke(sw))
                outline(Path().apply {
                    moveTo(w * .12f, h * .30f)
                    lineTo(w * .50f, h * .57f)
                    lineTo(w * .88f, h * .30f)
                })
            }
            QuestGlyph.LOCK -> {
                drawRoundRect(color, p(.17f, .43f), Size(w * .66f, h * .45f),
                    CornerRadius(w * .06f), style = Stroke(sw))
                drawArc(color, 180f, 180f, false, p(.30f, .10f), Size(w * .40f, h * .62f),
                    style = Stroke(sw, cap = StrokeCap.Round))
                drawCircle(color, w * .055f, p(.50f, .64f))
            }
            QuestGlyph.EYE, QuestGlyph.EYE_CLOSED -> {
                outline(Path().apply {
                    moveTo(w * .08f, h * .50f)
                    cubicTo(w * .30f, h * .13f, w * .70f, h * .13f, w * .92f, h * .50f)
                    cubicTo(w * .70f, h * .87f, w * .30f, h * .87f, w * .08f, h * .50f)
                    close()
                })
                drawCircle(color, w * .12f, p(.50f, .50f), style = Stroke(sw))
                if (glyph == QuestGlyph.EYE_CLOSED) line(.14f, .90f, .86f, .10f)
            }
            QuestGlyph.CHEVRON_RIGHT -> {
                line(.38f, .22f, .63f, .50f)
                line(.63f, .50f, .38f, .78f)
            }
            QuestGlyph.ARROW_LEFT -> {
                line(.80f, .50f, .18f, .50f)
                line(.18f, .50f, .45f, .24f)
                line(.18f, .50f, .45f, .76f)
            }
            QuestGlyph.PENCIL -> {
                outline(Path().apply {
                    moveTo(w * .18f, h * .66f)
                    lineTo(w * .67f, h * .17f)
                    lineTo(w * .84f, h * .34f)
                    lineTo(w * .35f, h * .83f)
                    lineTo(w * .15f, h * .86f)
                    close()
                })
                line(.58f, .27f, .75f, .44f)
            }
            QuestGlyph.BELL -> {
                outline(Path().apply {
                    moveTo(w * .17f, h * .73f)
                    lineTo(w * .28f, h * .60f)
                    lineTo(w * .28f, h * .37f)
                    cubicTo(w * .28f, h * .08f, w * .72f, h * .08f, w * .72f, h * .37f)
                    lineTo(w * .72f, h * .60f)
                    lineTo(w * .83f, h * .73f)
                    close()
                })
                drawArc(color, 0f, 180f, false, p(.40f, .69f), Size(w * .20f, h * .20f),
                    style = Stroke(sw))
            }
            QuestGlyph.SHIELD -> {
                outline(Path().apply {
                    moveTo(w * .50f, h * .08f)
                    lineTo(w * .82f, h * .20f)
                    lineTo(w * .78f, h * .61f)
                    cubicTo(w * .73f, h * .76f, w * .57f, h * .85f, w * .50f, h * .91f)
                    cubicTo(w * .43f, h * .85f, w * .27f, h * .76f, w * .22f, h * .61f)
                    lineTo(w * .18f, h * .20f)
                    close()
                })
                line(.38f, .50f, .48f, .61f)
                line(.48f, .61f, .67f, .39f)
            }
            QuestGlyph.HELP -> {
                drawCircle(color, w * .38f, p(.50f, .50f), style = Stroke(sw))
                outline(Path().apply {
                    moveTo(w * .39f, h * .40f)
                    cubicTo(w * .37f, h * .27f, w * .63f, h * .27f, w * .63f, h * .41f)
                    cubicTo(w * .63f, h * .51f, w * .49f, h * .53f, w * .49f, h * .62f)
                })
                drawCircle(color, w * .035f, p(.49f, .73f))
            }
            QuestGlyph.LOGOUT -> {
                outline(Path().apply {
                    moveTo(w * .57f, h * .20f)
                    lineTo(w * .17f, h * .20f)
                    lineTo(w * .17f, h * .80f)
                    lineTo(w * .57f, h * .80f)
                })
                line(.42f, .50f, .87f, .50f)
                line(.70f, .34f, .87f, .50f)
                line(.87f, .50f, .70f, .66f)
            }
            QuestGlyph.MEDAL -> {
                drawCircle(color, w * .25f, p(.50f, .37f), style = Stroke(sw))
                line(.36f, .58f, .32f, .94f)
                line(.32f, .94f, .50f, .80f)
                line(.50f, .80f, .68f, .94f)
                line(.68f, .94f, .64f, .58f)
            }
            QuestGlyph.SPARKLE -> {
                outline(Path().apply {
                    moveTo(w * .50f, h * .08f)
                    lineTo(w * .60f, h * .40f)
                    lineTo(w * .92f, h * .50f)
                    lineTo(w * .60f, h * .60f)
                    lineTo(w * .50f, h * .92f)
                    lineTo(w * .40f, h * .60f)
                    lineTo(w * .08f, h * .50f)
                    lineTo(w * .40f, h * .40f)
                    close()
                })
            }
        }
    }
}
