package br.uepa.softwarequest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.uepa.softwarequest.resources.Res
import br.uepa.softwarequest.resources.mission_event
import org.jetbrains.compose.resources.painterResource

/** Reprodução de missoes.png, com navegação e ações de demonstração. */
@Composable
fun QuestMissionsScreen(
    onNavigate: (QuestTab) -> Unit,
    onBack: () -> Unit,
) {
    var helpVisible by remember { mutableStateOf(false) }
    var selectedMission by remember { mutableStateOf<String?>(null) }

    Box(Modifier.fillMaxSize()) {
        QuestSpaceBackground(Modifier.fillMaxSize())
        Column(Modifier.fillMaxSize()) {
            Column(Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(26.dp))
                QuestPageHeader("MISSÕES", onBack, { helpVisible = true },
                    Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(15.dp))
                QuestStreak(Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(16.dp))
                QuestFeaturedMission(Modifier.padding(horizontal = 20.dp)) {
                    selectedMission = "Compilador de Plasma"
                }
                Spacer(Modifier.height(17.dp))
                Text("Missões Diárias", color = Color.White, fontWeight = FontWeight.Bold,
                    fontSize = 16.sp, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(11.dp))
                QuestDailyMission("Declarar Arrays Interestelares", "Objetivo: 0 / 3 · ", "+50 XP",
                    MissionState.AVAILABLE, Modifier.padding(horizontal = 20.dp)) {
                    selectedMission = "Declarar Arrays Interestelares"
                }
                Spacer(Modifier.height(10.dp))
                QuestDailyMission("Ajustar Sensores de Órbita", "Concluído · ", "+30 XP",
                    MissionState.DONE, Modifier.padding(horizontal = 20.dp)) { }
                Spacer(Modifier.height(10.dp))
                QuestDailyMission("Interpretar Sinais de Rádio", "Desbloqueia no Nível 4 de Variáveis", "",
                    MissionState.LOCKED, Modifier.padding(horizontal = 20.dp)) { }
                Spacer(Modifier.height(14.dp))
                QuestLimitedEvent(Modifier.padding(horizontal = 20.dp)) {
                    selectedMission = "Invasão Binária"
                }
                Spacer(Modifier.height(16.dp))
            }
            QuestBottomNavigation(QuestTab.MISSIONS, onNavigate)
        }
    }
    if (helpVisible) QuestHelpDialog("Missões",
        "Complete desafios para ganhar XP, moedas e recompensas. Algumas missões exigem progresso anterior.") {
        helpVisible = false
    }
    if (selectedMission != null) QuestHelpDialog(selectedMission ?: "Missão",
        "Esta é uma demonstração visual. O desafio será conectado ao conteúdo cadastrado pelos especialistas.") {
        selectedMission = null
    }
}

@Composable
private fun QuestStreak(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth().height(99.dp)
        .clip(RoundedCornerShape(20.dp)).background(QuestPanel)
        .border(1.dp, QuestBorder, RoundedCornerShape(20.dp))
        .padding(horizontal = 15.dp, vertical = 14.dp)) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("ϟ", color = QuestOrange, fontSize = 29.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(10.dp))
            Text("Sequência: 5 Dias", color = Color.White, fontSize = 15.sp,
                fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))
            Text("Próxima em 14h", color = QuestColors.muted, fontSize = 12.sp)
        }
        Spacer(Modifier.height(7.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            repeat(7) { day ->
                val done = day < 5
                val current = day == 5
                Box(Modifier.size(34.dp).clip(CircleShape)
                    .background(when { done -> Color(0xFF092B3A); current -> Color(0xFF5C3BF5); else -> Color(0xFF1F2039) })
                    .border(if (done) 1.dp else 0.dp,
                        if (done) QuestColors.cyan else Color.Transparent, CircleShape),
                    contentAlignment = Alignment.Center) {
                    Text(when { done -> "✓"; current -> "S"; else -> "D" },
                        fontSize = 13.sp, fontWeight = FontWeight.Bold,
                        color = if (done) QuestColors.cyan else if (current) Color.White else QuestColors.muted)
                }
            }
        }
    }
}

@Composable
private fun QuestFeaturedMission(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier.fillMaxWidth().height(216.dp)
        .clip(RoundedCornerShape(20.dp)).background(QuestPanel)
        .border(1.dp, QuestColors.cyan, RoundedCornerShape(20.dp))
        .clickable(onClick = onClick).padding(19.dp)) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.clip(RoundedCornerShape(8.dp)).background(Color(0xFF0A2935))
                .padding(horizontal = 11.dp, vertical = 5.dp)) {
                Text("PRINCIPAL", color = QuestColors.cyan, fontWeight = FontWeight.Bold, fontSize = 10.sp)
            }
            Spacer(Modifier.weight(1f))
            Text("ϟ EXPIRA EM 4D", color = QuestOrange, fontWeight = FontWeight.Bold, fontSize = 11.sp)
        }
        Spacer(Modifier.height(14.dp))
        Text("Compilador de Plasma", fontWeight = FontWeight.Bold, fontSize = 19.sp, color = Color.White)
        Spacer(Modifier.height(5.dp))
        Text("Escreva 5 funções de alocação de memória dinâmicas usando variáveis estelares puras.",
            color = QuestColors.muted, fontSize = 12.sp, lineHeight = 18.sp, maxLines = 3)
        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Progresso", fontSize = 12.sp, color = QuestColors.muted)
            Text("3 / 5 funções", fontSize = 12.sp, color = QuestColors.cyan, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(7.dp))
        QuestBar(.32f)
        Spacer(Modifier.height(13.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("☆ 150 XP", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("◉ 40 Moedas", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text("⬡ Baú Cósmico", color = Color(0xFFFF8A00), fontSize = 12.sp,
                fontWeight = FontWeight.Bold, maxLines = 1)
        }
    }
}

private enum class MissionState { AVAILABLE, DONE, LOCKED }

@Composable
private fun QuestDailyMission(
    title: String, description: String, reward: String, state: MissionState,
    modifier: Modifier = Modifier, onClick: () -> Unit,
) {
    val faded = state != MissionState.AVAILABLE
    val opacity = if (faded) .45f else 1f
    Row(modifier.fillMaxWidth().height(68.dp)
        .clip(RoundedCornerShape(17.dp)).background(QuestPanel.copy(alpha = if (faded) .55f else 1f))
        .border(1.dp, QuestBorder.copy(alpha = if (faded) .5f else 1f), RoundedCornerShape(17.dp))
        .then(if (state == MissionState.AVAILABLE) Modifier.clickable(onClick = onClick) else Modifier)
        .padding(horizontal = 13.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(40.dp).clip(RoundedCornerShape(10.dp))
            .background(if (state == MissionState.AVAILABLE) Color(0xFF1C174B) else Color(0xFF092D31).copy(alpha = opacity)),
            contentAlignment = Alignment.Center) {
            when (state) {
                MissionState.AVAILABLE -> Text("›_", fontSize = 20.sp, color = QuestColors.cyan, fontWeight = FontWeight.Bold)
                MissionState.DONE -> Text("✓", fontSize = 22.sp, color = Color(0xFF00CDAF).copy(alpha = opacity))
                MissionState.LOCKED -> QuestLockIcon(Modifier.size(23.dp), QuestColors.muted.copy(alpha = opacity))
            }
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(title, color = if (faded) QuestColors.muted.copy(alpha = opacity) else Color.White,
                fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(3.dp))
            Row {
                Text(description, color = QuestColors.muted.copy(alpha = opacity), fontSize = 10.sp)
                if (reward.isNotEmpty()) Text(reward,
                    color = if (state == MissionState.DONE) Color(0xFF00BEA3).copy(alpha = opacity) else QuestOrange,
                    fontSize = 10.sp)
            }
        }
        when (state) {
            MissionState.AVAILABLE -> Box(Modifier.size(17.dp).border(1.5.dp, QuestColors.cyan, CircleShape),
                contentAlignment = Alignment.Center) { Text("▶", fontSize = 9.sp, color = QuestColors.cyan) }
            MissionState.DONE -> Text("✓", fontSize = 18.sp, color = QuestColors.cyan.copy(alpha = opacity))
            MissionState.LOCKED -> QuestLockIcon(Modifier.size(22.dp), QuestColors.muted.copy(alpha = opacity))
        }
    }
}

@Composable
private fun QuestLimitedEvent(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(modifier.fillMaxWidth().height(79.dp).clip(RoundedCornerShape(17.dp))
        .background(Color(0xFF090D21))
        .border(1.dp, QuestOrange, RoundedCornerShape(17.dp))
        .padding(horizontal = 15.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(painterResource(Res.drawable.mission_event), contentDescription = "Invasão Binária",
            modifier = Modifier.size(46.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text("EVENTO LIMITADO", color = QuestOrange, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("Invasão Binária", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
        QuestSolidButton("ENTRAR", onClick, modifier = Modifier.width(70.dp),
            background = QuestOrange, height = 28.dp)
    }
}
