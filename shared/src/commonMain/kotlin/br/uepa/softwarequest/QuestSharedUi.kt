package br.uepa.softwarequest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal val QuestPanel = Color(0xFF111129)
internal val QuestPanelDark = Color(0xFF0D0D24)
internal val QuestBorder = Color(0xFF282142)
internal val QuestPurple = Color(0xFF6E3EF6)
internal val QuestOrange = Color(0xFFFFB900)

@Composable
internal fun QuestPageHeader(
    title: String,
    onBack: () -> Unit,
    onHelp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxWidth().height(42.dp)) {
        QuestHeaderControl("Voltar", onBack, Modifier.align(Alignment.CenterStart)) {
            Canvas(Modifier.size(19.dp)) {
                val stroke = 2.dp.toPx()
                drawLine(Color.White, Offset(size.width * .80f, size.height * .5f),
                    Offset(size.width * .22f, size.height * .5f), stroke, StrokeCap.Round)
                drawLine(Color.White, Offset(size.width * .22f, size.height * .5f),
                    Offset(size.width * .47f, size.height * .24f), stroke, StrokeCap.Round)
                drawLine(Color.White, Offset(size.width * .22f, size.height * .5f),
                    Offset(size.width * .47f, size.height * .76f), stroke, StrokeCap.Round)
            }
        }
        Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center))
        QuestHeaderControl("Ajuda", onHelp, Modifier.align(Alignment.CenterEnd)) {
            Box(Modifier.size(14.dp).border(1.6.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center) {
                Text("?", fontSize = 10.sp, lineHeight = 11.sp, fontWeight = FontWeight.Bold,
                    color = Color.White)
            }
        }
    }
}

@Composable
internal fun QuestHeaderControl(
    label: String, onClick: () -> Unit, modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier.size(36.dp).clip(RoundedCornerShape(11.dp))
        .background(Color(0xFF121129))
        .border(1.dp, QuestBorder, RoundedCornerShape(11.dp))
        .clickable(role = Role.Button, onClickLabel = label, onClick = onClick),
        contentAlignment = Alignment.Center, content = content)
}

@Composable
internal fun QuestBar(
    fraction: Float,
    modifier: Modifier = Modifier,
    height: Dp = 9.dp,
    color: Color = QuestColors.cyan,
    track: Color = Color(0xFFECE7FF),
) {
    Box(modifier.fillMaxWidth().height(height).clip(CircleShape).background(track)) {
        Box(Modifier.fillMaxWidth(fraction.coerceIn(0f, 1f)).fillMaxHeight()
            .clip(CircleShape).background(color))
    }
}

@Composable
internal fun QuestSolidButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    background: Color = QuestColors.cyan,
    foreground: Color = Color(0xFF06131E),
    height: Dp = 43.dp,
) {
    Box(modifier.height(height).clip(RoundedCornerShape(10.dp))
        .background(if (enabled) background else Color(0xFF232538))
        .then(if (enabled) Modifier.clickable(role = Role.Button, onClick = onClick) else Modifier),
        contentAlignment = Alignment.Center) {
        Text(text, fontSize = 13.sp, fontWeight = FontWeight.Bold,
            color = if (enabled) foreground else Color(0xFF7D849C),
            textAlign = TextAlign.Center)
    }
}

@Composable
internal fun QuestOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    height: Dp = 36.dp,
) {
    Box(modifier.height(height).clip(RoundedCornerShape(10.dp))
        .background(if (selected) Color(0xFF09212E) else Color(0xFF12112B))
        .border(1.dp, if (selected) QuestColors.cyan else QuestBorder, RoundedCornerShape(10.dp))
        .clickable(role = Role.Button, onClick = onClick), contentAlignment = Alignment.Center) {
        Text(text, color = if (selected) QuestColors.cyan else QuestColors.muted,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal, fontSize = 12.sp)
    }
}

@Composable
internal fun QuestHelpDialog(title: String, message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = QuestPanel,
        title = { Text(title, color = Color.White, fontWeight = FontWeight.Bold) },
        text = { Text(message, color = QuestColors.muted) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Entendi", color = QuestColors.cyan)
            }
        },
    )
}

@Composable
internal fun QuestLockIcon(modifier: Modifier = Modifier, color: Color = Color(0xFF68718D)) {
    Canvas(modifier) {
        val sw = 1.8.dp.toPx()
        drawArc(color, 180f, 180f, useCenter = false,
            topLeft = Offset(size.width * .27f, size.height * .10f),
            size = androidx.compose.ui.geometry.Size(size.width * .46f, size.height * .52f),
            style = Stroke(sw, cap = StrokeCap.Round))
        drawRoundRect(color, topLeft = Offset(size.width * .17f, size.height * .45f),
            size = androidx.compose.ui.geometry.Size(size.width * .66f, size.height * .45f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.dp.toPx()),
            style = Stroke(sw))
    }
}
