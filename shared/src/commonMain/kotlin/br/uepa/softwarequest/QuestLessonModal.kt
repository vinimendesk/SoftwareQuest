package br.uepa.softwarequest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Modal em tela cheia baseada em Lição · Variáveis Cósmicas.png. */
@Composable
fun QuestLessonModal(
    onDismiss: () -> Unit,
    onComplete: () -> Unit = onDismiss,
) {
    var selectedAnswer by rememberSaveable { mutableStateOf<Int?>(null) }
    var hintVisible by rememberSaveable { mutableStateOf(false) }
    var feedback by rememberSaveable { mutableStateOf<String?>(null) }
    var solved by rememberSaveable { mutableStateOf(false) }

    Box(Modifier.fillMaxSize().background(Color(0xFF080918))) {
        QuestSpaceBackground(Modifier.fillMaxSize())
        Column(Modifier.fillMaxSize()) {
            Column(Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(18.dp))
                Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    QuestHeaderControl("Fechar lição", onDismiss) {
                        Text("×", fontSize = 26.sp, color = QuestColors.muted, lineHeight = 26.sp)
                    }
                    Spacer(Modifier.weight(1f))
                    Text("Etapa 1 de 4", fontSize = 12.sp, color = QuestColors.muted)
                    Spacer(Modifier.weight(1f))
                    Box(Modifier.clip(RoundedCornerShape(18.dp)).background(Color(0xFF1B1429))
                        .border(1.dp, Color(0xFF694517), RoundedCornerShape(18.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)) {
                        Text("♙ +20 XP", color = QuestOrange, fontSize = 12.sp,
                            fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.height(14.dp))
                QuestBar(.27f, modifier = Modifier.padding(horizontal = 20.dp), height = 8.dp,
                    track = Color(0xFF232041))
                Spacer(Modifier.height(19.dp))
                Text("MÓDULO 2 · EXPLORANDO", color = Color(0xFFDDC6FF),
                    fontSize = 10.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(6.dp))
                Text("Variáveis Cósmicas", color = Color.White, fontSize = 25.sp,
                    fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(9.dp))
                Row(Modifier.padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("•  EM ÓRBITA", color = QuestColors.cyan, fontSize = 10.sp,
                        fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(16.dp))
                    Text("◷ ~2 min", color = QuestColors.muted, fontSize = 11.sp)
                }
                Spacer(Modifier.height(20.dp))
                Text(buildAnnotatedString {
                    append("Uma ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = Color.White)) {
                        append("variável")
                    }
                    append(" guarda uma informação da nave. Dê um nome a ela e use = para guardar um valor.")
                }, color = QuestColors.muted, fontSize = 14.sp, lineHeight = 22.sp,
                    modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(14.dp))
                QuestCodeExample(Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(21.dp))
                Row(Modifier.padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(26.dp).clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF20113B))
                        .border(1.dp, QuestBorder, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center) {
                        Text("›_", color = Color(0xFFDAA8FF), fontSize = 15.sp)
                    }
                    Spacer(Modifier.width(9.dp))
                    Text("Sua vez, cadete", color = Color.White, fontSize = 15.sp,
                        fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(12.dp))
                Text(buildAnnotatedString {
                    append("A nave precisa de ")
                    withStyle(SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
                        append("100 unidades de combustível.")
                    }
                    append(" Complete a variável com o valor correto.")
                }, color = QuestColors.muted, fontSize = 14.sp, lineHeight = 21.sp,
                    modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(13.dp))
                QuestCodeAnswer(selectedAnswer, Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(12.dp))
                Text("Toque em uma opção para completar", color = QuestColors.muted,
                    fontSize = 11.sp, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(10, 100, 1000).forEach { number ->
                        Box(Modifier.weight(1f).height(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selectedAnswer == number) Color(0xFF093143) else QuestPanel)
                            .border(1.dp, if (selectedAnswer == number) QuestColors.cyan
                                else Color(0xFF665582), RoundedCornerShape(12.dp))
                            .clickable { selectedAnswer = number; feedback = null },
                            contentAlignment = Alignment.Center) {
                            Text(number.toString(), color = if (selectedAnswer == number)
                                QuestColors.cyan else Color.White, fontSize = 15.sp,
                                fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(Modifier.height(20.dp))
                Text("♧  Precisa de uma dica?  ›", color = Color(0xFFD5B7FF), fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                        .clickable { hintVisible = !hintVisible })
                if (hintVisible) {
                    Spacer(Modifier.height(10.dp))
                    Text("Dica: use a quantidade de combustível indicada no enunciado.",
                        color = QuestColors.cyan, fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 20.dp))
                }
                if (feedback != null) {
                    Spacer(Modifier.height(12.dp))
                    Text(feedback ?: "", color = if (solved) QuestColors.cyan else QuestOrange,
                        fontSize = 13.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 20.dp))
                }
                Spacer(Modifier.height(20.dp))
            }
            Column(Modifier.fillMaxWidth().background(Color(0xFF070D1D))) {
                Box(Modifier.fillMaxWidth().height(1.dp).background(QuestBorder))
                Spacer(Modifier.height(13.dp))
                QuestSolidButton(
                    text = if (solved) "Continuar" else "Verificar resposta",
                    onClick = {
                        if (solved) onComplete()
                        else if (selectedAnswer == 100) {
                            solved = true
                            feedback = "Resposta correta! +20 XP"
                        } else feedback = "Ainda não! Observe a quantidade de combustível."
                    },
                    enabled = selectedAnswer != null,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    height = 48.dp,
                )
                Spacer(Modifier.height(8.dp))
                Text(if (selectedAnswer == null) "Selecione uma opção para verificar"
                    else if (solved) "Etapa concluída · demonstração visual"
                    else "Toque para verificar sua resposta",
                    color = QuestColors.muted, fontSize = 10.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun QuestCodeExample(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth().height(104.dp)
        .clip(RoundedCornerShape(13.dp)).background(QuestPanel)
        .border(1.dp, QuestBorder, RoundedCornerShape(13.dp))) {
        Row(Modifier.fillMaxWidth().height(33.dp).background(Color(0xFF1B173E))
            .padding(horizontal = 13.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("‹/›  COMPUTADOR DE BORDO", color = Color(0xFFDDC8FF), fontSize = 10.sp)
            Spacer(Modifier.weight(1f))
            Text("Python", color = QuestColors.muted, fontSize = 10.sp,
                fontFamily = FontFamily.Monospace)
        }
        Spacer(Modifier.height(14.dp))
        Text(buildAnnotatedString {
            withStyle(SpanStyle(color = Color(0xFFE6B9FF))) { append("escudos") }
            append(" = ")
            withStyle(SpanStyle(color = QuestOrange, fontWeight = FontWeight.Bold)) { append("80") }
        }, color = Color.White, fontSize = 13.sp, fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(horizontal = 13.dp))
        Spacer(Modifier.height(8.dp))
        Text("# escudos guarda o valor 80", color = QuestColors.muted,
            fontSize = 11.sp, fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(horizontal = 13.dp))
    }
}

@Composable
private fun QuestCodeAnswer(selected: Int?, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth().height(71.dp).clip(RoundedCornerShape(13.dp))
        .background(Color(0xFF0B0D24)).border(1.dp, QuestBorder, RoundedCornerShape(13.dp))
        .padding(horizontal = 14.dp), verticalAlignment = Alignment.CenterVertically) {
        Text("1", color = QuestColors.muted, fontSize = 11.sp,
            fontFamily = FontFamily.Monospace)
        Spacer(Modifier.width(13.dp))
        Text("combustivel =", color = Color(0xFFE4B5FF), fontSize = 14.sp,
            fontFamily = FontFamily.Monospace)
        Spacer(Modifier.width(9.dp))
        Box(Modifier.width(75.dp).height(37.dp).clip(RoundedCornerShape(7.dp))
            .background(Color(0xFF26223D))
            .border(1.dp, if (selected == null) Color(0xFF665582) else QuestColors.cyan,
                RoundedCornerShape(7.dp)), contentAlignment = Alignment.Center) {
            Text(selected?.toString() ?: "", color = QuestColors.cyan,
                fontFamily = FontFamily.Monospace, fontSize = 14.sp)
        }
    }
}
