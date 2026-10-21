package br.uepa.softwarequest1.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.uepa.softwarequest1.color.JornadaColors
import org.jetbrains.compose.resources.painterResource
import softwarequest1.shared.generated.resources.Res
import softwarequest1.shared.generated.resources.check
import softwarequest1.shared.generated.resources.lock
import softwarequest1.shared.generated.resources.playCircle
import softwarequest1.shared.generated.resources.redeem
import softwarequest1.shared.generated.resources.star
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

@Composable
fun SpaceMap() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(440.dp)
    ) {

        SpaceConnections()

        SpaceNode(
            x = 149.dp,
            y = 192.dp,
            label = "Variáveis",
            active = true
        )

        SpaceNode(
            x = 38.dp,
            y = 97.dp,
            label = "Laços",
            locked = true
        )

        SpaceNode(
            x = 203.dp,
            y = 49.dp,
            label = "Funções",
            locked = true
        )

        LogicNode()

        RewardNode()

        PlayButton()
    }
}

@Composable
private fun SpaceConnections() {

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        drawDashedLine(
            start = Offset(74f, 247f),
            end = Offset(
                74f + 121.66f * cos((-51.34).toRadians().toFloat()),
                247f + 121.66f * sin((-51.34).toRadians().toFloat())
            )
        )

        drawDashedLine(
            start = Offset(80f, 153f),
            end = Offset(
                80f + 75.8f * cos((30.96).toRadians().toFloat()),
                153f + 75.8f * sin((30.96).toRadians().toFloat())
            )
        )

        drawDashedLine(
            start = Offset(257f, 88f),
            end = Offset(
                257f + 142.89f * cos((-119.79).toRadians().toFloat()),
                88f + 142.89f * sin((-119.79).toRadians().toFloat())
            )
        )

        drawDashedLine(
            start = Offset(333f, 266f),
            end = Offset(
                333f + 63f * cos((-90.0).toFloat()),
                266f + 63f * sin((-90.0).toFloat())
            )
        )

        drawDashedLine(
            start = Offset(80f, 74f),
            end = Offset(
                80f + 124.91f * cos((165.63).toFloat()),
                74f + 124.91f * sin((165.63).toFloat())
            )
        )
    }
}

private fun DrawScope.drawDashedLine(
    start: Offset,
    end: Offset
) {

    drawLine(
        color = JornadaColors.Cyan.copy(alpha = 0.6f),
        start = start,
        end = end,
        strokeWidth = 2f,
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(6f, 6f),
            0f
        )
    )
}

@Composable
private fun SpaceNode(
    x: Dp,
    y: Dp,
    label: String,
    locked: Boolean = false,
    active: Boolean = false
) {

    Column(
        modifier = Modifier
            .offset(
                x = x,
                y = y
            )
            .width(57.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color(0x9912122C))
                .border(
                    1.dp,
                    if (active)
                        JornadaColors.Cyan
                    else
                        JornadaColors.NodeBorder,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            /*
             * Aqui entra a imagem do planeta/estação.
             */
            Box(
                modifier = Modifier
                    .size(37.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF292052))
            )

            if (locked) {
                Icon(
                    painter = painterResource(Res.drawable.lock),
                    contentDescription = null,
                    tint = JornadaColors.TextSecondary,
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        Text(
            text = label,
            color = if (active)
                JornadaColors.Success
            else
                JornadaColors.TextSecondary,
            fontSize = 11.sp,
            fontWeight = if (active)
                FontWeight.Bold
            else
                FontWeight.Normal
        )
    }
}

@Composable
private fun LogicNode() {

    Column(
        modifier = Modifier.offset(
            x = 35.dp,
            y = 330.dp
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(JornadaColors.SuccessBackground)
                .border(
                    2.dp,
                    JornadaColors.Success,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                painter = painterResource(Res.drawable.check),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }

        Text(
            text = "Lógica",
            color = JornadaColors.Success,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 0.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            modifier = Modifier.padding(top = 4.dp)
        ) {

            repeat(3) {
                Icon(
                    painter = painterResource(Res.drawable.star),
                    contentDescription = null,
                    tint = JornadaColors.Reward,
                    modifier = Modifier.size(10.dp)
                )
            }
        }
    }
}

@Composable
private fun PlayButton() {

    Box(
        modifier = Modifier
            .offset(
                x = 197.dp,
                y = 195.dp
            )
            .size(18.dp),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(Res.drawable.playCircle),
            contentDescription = "Iniciar",
            tint = JornadaColors.Cyan,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun RewardNode() {

    Column(
        modifier = Modifier
            .offset(
                x = 293.dp,
                y = 329.dp
            )
            .size(
                width = 79.dp,
                height = 56.dp
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x992A1A4E))
            .border(
                width = 1.dp,
                color = Color(0x80FFB800),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Icon(
            painter = painterResource(Res.drawable.redeem),
            contentDescription = null,
            tint = JornadaColors.Reward,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = "RECOMPENSA",
            color = Color(0xFFFFD700),
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

fun Double.toRadians(): Double = this * (PI / 180.0)