package br.uepa.softwarequest1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.uepa.softwarequest1.color.JornadaColors

@Composable
fun StarfieldOverlay() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Halo ciano.
        Box(
            modifier = Modifier
                .offsetPx(30f, 150f)
                .size(200.dp)
                .blur(20.dp)
                .background(
                    Color(0x0A00F0FF),
                    CircleShape
                )
        )

        // Halo roxo.
        Box(
            modifier = Modifier
                .offsetPx(180f, 400f)
                .size(220.dp)
                .blur(25.dp)
                .background(
                    Color(0x0DA020F0),
                    CircleShape
                )
        )

        // Estrelas.
        Star(50f, 80f, 3f, 0.6f)
        Star(320f, 120f, 2f, 0.4f)
        Star(150f, 280f, 4f, 0.8f, JornadaColors.Cyan)
        Star(280f, 340f, 3f, 0.5f)
        Star(80f, 520f, 2f, 0.3f)
        Star(340f, 600f, 4f, 0.7f)
        Star(120f, 710f, 3f, 0.5f, JornadaColors.Cyan)
    }
}

fun Modifier.offsetPx(
    x: Float,
    y: Float
): Modifier {
    return this.offset(
        x = x.dp,
        y = y.dp
    )
}

@Composable
private fun Star(
    x: Float,
    y: Float,
    size: Float,
    alpha: Float,
    color: Color = Color.White
) {

    Box(
        modifier = Modifier
            .offsetPx(x, y)
            .size(size.dp)
            .background(
                color = color.copy(alpha = alpha),
                shape = CircleShape
            )
    )
}
