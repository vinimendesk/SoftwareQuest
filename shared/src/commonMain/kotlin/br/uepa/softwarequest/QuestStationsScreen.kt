package br.uepa.softwarequest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.uepa.softwarequest.resources.Res
import br.uepa.softwarequest.resources.station_aurora
import br.uepa.softwarequest.resources.station_cadet
import br.uepa.softwarequest.resources.station_hangar
import br.uepa.softwarequest.resources.station_orion
import br.uepa.softwarequest.resources.station_sentinel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/** Reprodução de Estações.png. Compras/equipamentos simulados apenas em memória. */
@Composable
fun QuestStationsScreen(
    onNavigate: (QuestTab) -> Unit,
    onBack: () -> Unit,
) {
    var helpVisible by rememberSaveable { mutableStateOf(false) }
    var filter by rememberSaveable { mutableStateOf("Todos") }
    var character by rememberSaveable { mutableStateOf("Cadete Alex") }
    var ship by rememberSaveable { mutableStateOf("Órion") }
    var auroraOwned by rememberSaveable { mutableStateOf(false) }
    var notice by rememberSaveable { mutableStateOf<String?>(null) }

    val showOwned = filter != "Para obter"
    val showUnowned = filter != "Minha coleção"

    Box(Modifier.fillMaxSize()) {
        QuestSpaceBackground(Modifier.fillMaxSize())
        Column(Modifier.fillMaxSize()) {
            Column(Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(26.dp))
                QuestPageHeader("ESTAÇÕES", onBack, { helpVisible = true },
                    Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(10.dp))
                Text("Personalize sua jornada com personagens e naves.",
                    color = QuestColors.muted, fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                Spacer(Modifier.height(17.dp))
                QuestHangarCard(ship, character, Modifier.padding(horizontal = 20.dp))
                Spacer(Modifier.height(19.dp))
                Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("Todos", "Minha coleção", "Para obter").forEach { option ->
                        QuestOutlineButton(option, { filter = option }, Modifier.weight(1f),
                            selected = filter == option, height = 35.dp)
                    }
                }
                Spacer(Modifier.height(18.dp))
                QuestStationSectionHeader("Personagens", "2 visuais")
                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (showOwned) {
                        QuestStationItemCard(
                            "Cadete Alex", "Na sua coleção", Res.drawable.station_cadet,
                            buttonText = if (character == "Cadete Alex") "✓  Equipado" else "Equipar",
                            isEquipped = character == "Cadete Alex", modifier = Modifier.weight(1f),
                        ) { character = "Cadete Alex" }
                    }
                    if (showOwned) {
                        QuestStationItemCard(
                            "Sentinela Lunar", "Obtido · Na coleção", Res.drawable.station_sentinel,
                            buttonText = if (character == "Sentinela Lunar") "✓  Equipado" else "Equipar",
                            isEquipped = character == "Sentinela Lunar", modifier = Modifier.weight(1f),
                        ) { character = "Sentinela Lunar" }
                    }
                    if (!showOwned) {
                        Text("Todos os personagens estão na sua coleção.", color = QuestColors.muted,
                            fontSize = 12.sp, modifier = Modifier.fillMaxWidth().padding(vertical = 22.dp))
                    }
                }
                Spacer(Modifier.height(19.dp))
                QuestStationSectionHeader("Naves", "2 visuais")
                Spacer(Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (showOwned) {
                        QuestStationItemCard(
                            "Órion", "Na sua coleção", Res.drawable.station_orion,
                            buttonText = if (ship == "Órion") "✓  Equipado" else "Equipar",
                            isEquipped = ship == "Órion", modifier = Modifier.weight(1f),
                        ) { ship = "Órion" }
                    }
                    if (showUnowned || auroraOwned) {
                        QuestStationItemCard(
                            "Aurora Violeta", if (auroraOwned) "Na sua coleção" else "🪙 160 Moedas",
                            Res.drawable.station_aurora,
                            buttonText = when { ship == "Aurora Violeta" -> "✓  Equipado"; auroraOwned -> "Equipar"; else -> "Obter" },
                            isEquipped = ship == "Aurora Violeta",
                            isPurchase = !auroraOwned,
                            modifier = Modifier.weight(1f),
                        ) {
                            if (!auroraOwned) {
                                auroraOwned = true
                                notice = "Aurora Violeta adquirida (simulação local)."
                            } else ship = "Aurora Violeta"
                        }
                    }
                    if (!showOwned && !showUnowned && !auroraOwned) Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(19.dp))
                Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(16.dp)).background(Color(0xFF191638))
                    .border(1.dp, QuestBorder, RoundedCornerShape(16.dp))
                    .padding(horizontal = 15.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("🪙", color = QuestOrange, fontSize = 19.sp)
                    Spacer(Modifier.width(10.dp))
                    Text("Conquiste moedas nas missões para obter novos visuais.",
                        color = QuestColors.muted, fontSize = 12.sp, lineHeight = 18.sp)
                }
                Spacer(Modifier.height(20.dp))
            }
            QuestBottomNavigation(QuestTab.STATIONS, onNavigate)
        }
    }
    if (helpVisible) QuestHelpDialog("Estações",
        "Escolha personagens e naves para personalizar sua jornada. As alterações desta demonstração são locais.") {
        helpVisible = false
    }
    if (notice != null) QuestHelpDialog("Estação", notice ?: "", onDismiss = { notice = null })
}

@Composable
private fun QuestHangarCard(ship: String, character: String, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth().height(235.dp).clip(RoundedCornerShape(20.dp))
        .background(QuestPanel).border(1.dp, Color(0xFF4A327C), RoundedCornerShape(20.dp))) {
        Box(Modifier.fillMaxWidth().weight(1f)) {
            Image(painterResource(Res.drawable.station_hangar), contentDescription = "Nave Órion no hangar",
                modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            // As etiquetas da arte de referência são redesenhadas em Compose, para manter a interface editável.
            Text("SEU HANGAR", color = Color(0xFFDEC8FF), fontSize = 10.sp,
                fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.TopStart)
                    .padding(start = 13.dp, top = 14.dp))
            Box(Modifier.align(Alignment.TopEnd).padding(end = 14.dp, top = 12.dp)
                .clip(RoundedCornerShape(16.dp)).background(Color(0xFF07172A))
                .border(1.dp, QuestColors.cyan, RoundedCornerShape(16.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)) {
                Text("✓  Equipado", color = QuestColors.cyan, fontSize = 11.sp,
                    fontWeight = FontWeight.Bold)
            }
        }
        Row(Modifier.fillMaxWidth().height(59.dp).padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(ship, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                Text("Nave inicial · Visual atual", color = QuestColors.muted, fontSize = 11.sp)
            }
            Text(character, color = Color(0xFFCEB5E9), fontSize = 11.sp)
        }
    }
}

@Composable
private fun QuestStationSectionHeader(title: String, count: String) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(count, color = QuestColors.muted, fontSize = 11.sp)
    }
}

@Composable
private fun QuestStationItemCard(
    title: String,
    description: String,
    artwork: DrawableResource,
    buttonText: String,
    isEquipped: Boolean,
    modifier: Modifier = Modifier,
    isPurchase: Boolean = false,
    onAction: () -> Unit,
) {
    Column(modifier.height(197.dp).clip(RoundedCornerShape(18.dp))
        .background(QuestPanel)
        .border(1.dp, if (isEquipped) Color(0xFF503778) else QuestBorder,
            RoundedCornerShape(18.dp)).padding(9.dp)) {
        Image(painterResource(artwork), contentDescription = title,
            modifier = Modifier.fillMaxWidth().height(92.dp).clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop)
        Spacer(Modifier.height(6.dp))
        Text(title, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold,
            maxLines = 1, overflow = TextOverflow.Ellipsis)
        Spacer(Modifier.height(2.dp))
        Text(description, color = if (isPurchase) QuestOrange else QuestColors.muted,
            fontSize = 10.sp, maxLines = 1)
        Spacer(Modifier.weight(1f))
        if (isEquipped) {
            QuestSolidButton(buttonText, onAction, Modifier.fillMaxWidth(),
                background = Color(0xFF0E253D), foreground = QuestColors.cyan, height = 33.dp)
        } else if (isPurchase) {
            QuestSolidButton(buttonText, onAction, Modifier.fillMaxWidth(), height = 33.dp)
        } else {
            QuestOutlineButton(buttonText, onAction, Modifier.fillMaxWidth(), height = 33.dp,
                selected = true)
        }
    }
}
