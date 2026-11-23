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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.uepa.softwarequest.resources.Res
import br.uepa.softwarequest.resources.federation_astronaut
import br.uepa.softwarequest.resources.federation_emblem
import br.uepa.softwarequest.resources.federation_luna
import br.uepa.softwarequest.resources.federation_robot
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/** Reprodução de federacao.png. Contribuição ilustrativa, sem servidor. */
@Composable
fun QuestFederationScreen(
    onNavigate: (QuestTab) -> Unit,
    onBack: () -> Unit,
) {
    var helpVisible by remember { mutableStateOf(false) }
    var showRanking by remember { mutableStateOf(false) }
    var contribution by rememberSaveable { mutableIntStateOf(120) }
    var showContribution by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        QuestSpaceBackground(Modifier.fillMaxSize())
        Column(Modifier.fillMaxSize()) {
            Column(Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(26.dp))
                QuestPageHeader("FEDERAÇÃO", onBack, { helpVisible = true },
                    Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(15.dp))
                QuestAlliancePanel(Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(16.dp))
                QuestCollectiveEvent(contribution, Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(17.dp))
                Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Tripulação Hyperion", fontSize = 16.sp, fontWeight = FontWeight.Bold,
                        color = Color.White)
                    Text("Ver ranking global", fontSize = 11.sp, fontWeight = FontWeight.Bold,
                        color = QuestColors.cyan,
                        modifier = Modifier.clickable { showRanking = true })
                }
                Spacer(Modifier.height(15.dp))
                QuestCrewMember("1º", "Astronauta (Você)", "Oficial de Voo", "420 XP",
                    Res.drawable.federation_astronaut, first = true, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(9.dp))
                QuestCrewMember("2º", "Luna Estelar", "Navegadora", "350 XP",
                    Res.drawable.federation_luna, first = false, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(9.dp))
                QuestCrewMember("3º", "Robo_K9", "Engenheiro", "290 XP",
                    Res.drawable.federation_robot, first = false, modifier = Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(24.dp))
                QuestSolidButton("Contribuir Recurso ›", { showContribution = true },
                    Modifier.fillMaxWidth().padding(horizontal = 20.dp), height = 43.dp)
                Spacer(Modifier.height(16.dp))
            }
            QuestBottomNavigation(QuestTab.FEDERATION, onNavigate)
        }
    }
    if (helpVisible) QuestHelpDialog("Federação",
        "A Aliança Hyperion reúne tripulantes em missões coletivas. O progresso exibido é demonstrativo.") {
        helpVisible = false
    }
    if (showRanking) QuestHelpDialog("Ranking global",
        "O ranking global será disponibilizado quando a integração com os dados da federação estiver pronta.") {
        showRanking = false
    }
    if (showContribution) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showContribution = false },
            containerColor = QuestPanel,
            title = { Text("Contribuir Recurso", color = Color.White) },
            text = { Text("Enviar 10 XP de demonstração para o Escudo Defletor?", color = QuestColors.muted) },
            confirmButton = {
                androidx.compose.material3.TextButton(onClick = {
                    contribution = (contribution + 10).coerceAtMost(250)
                    showContribution = false
                }) { Text("Contribuir +10 XP", color = QuestColors.cyan) }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(onClick = { showContribution = false }) {
                    Text("Cancelar", color = QuestColors.muted)
                }
            },
        )
    }
}

@Composable
private fun QuestAlliancePanel(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth().height(239.dp).clip(RoundedCornerShape(21.dp))
        .background(QuestPanel).border(1.dp, QuestBorder, RoundedCornerShape(21.dp))
        .padding(horizontal = 17.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painterResource(Res.drawable.federation_emblem),
            contentDescription = "Emblema da Aliança Hyperion",
            modifier = Modifier.size(86.dp).clip(CircleShape), contentScale = ContentScale.Crop)
        Spacer(Modifier.height(11.dp))
        Text("Aliança Hyperion", color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
        Text("Liga de Ouro · ID #49281", color = QuestColors.muted, fontSize = 12.sp)
        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Nível Coletivo: 8", color = QuestColors.muted, fontSize = 11.sp)
            Text("450 / 1000 XP", color = QuestColors.cyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(7.dp))
        QuestBar(.32f, color = QuestPurple)
    }
}

@Composable
private fun QuestCollectiveEvent(contribution: Int, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth().height(184.dp).clip(RoundedCornerShape(20.dp))
        .background(QuestPanel).border(1.dp, QuestColors.cyan, RoundedCornerShape(20.dp))
        .padding(horizontal = 15.dp, vertical = 15.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("EVENTO COLETIVO", color = QuestColors.cyan, fontSize = 13.sp,
                fontWeight = FontWeight.Bold)
            Text("23h restantes", color = QuestOrange, fontSize = 11.sp)
        }
        Spacer(Modifier.height(12.dp))
        Text("Construir Escudo Defletor", color = Color.White, fontSize = 16.sp,
            fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(11.dp))
        Text("Toda a equipe deve acumular 2.000 XP em missões de strings. Sua contribuição direta ajuda a destravar o baú lendário para todos.",
            color = QuestColors.muted, fontSize = 12.sp, lineHeight = 17.sp)
        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Sua contribuição", color = QuestColors.muted, fontSize = 11.sp)
            Text("$contribution / 250 XP", color = QuestOrange, fontSize = 12.sp,
                fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(5.dp))
        QuestBar(contribution / 500f, color = QuestOrange)
    }
}

@Composable
private fun QuestCrewMember(
    place: String, name: String, role: String, xp: String, image: DrawableResource,
    first: Boolean, modifier: Modifier = Modifier,
) {
    Row(modifier.fillMaxWidth().height(55.dp).clip(RoundedCornerShape(15.dp))
        .background(QuestPanel)
        .border(1.dp, if (first) QuestColors.cyan else QuestBorder, RoundedCornerShape(15.dp))
        .padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(place, color = if (first) QuestColors.cyan else QuestColors.muted,
            fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(14.dp))
        Image(painterResource(image), contentDescription = name,
            modifier = Modifier.size(33.dp).clip(CircleShape), contentScale = ContentScale.Crop)
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(name, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Text(role, color = QuestColors.muted, fontSize = 11.sp)
        }
        Text(xp, color = if (first) QuestOrange else QuestColors.muted,
            fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}
