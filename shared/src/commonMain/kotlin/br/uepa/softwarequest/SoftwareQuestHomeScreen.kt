package br.uepa.softwarequest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.uepa.softwarequest.resources.Res
import br.uepa.softwarequest.resources.profile_avatar
import br.uepa.softwarequest.resources.planet_estruturas
import br.uepa.softwarequest.resources.planet_funcoes
import br.uepa.softwarequest.resources.planet_lagos
import br.uepa.softwarequest.resources.planet_logica
import br.uepa.softwarequest.resources.planet_orbitas
import br.uepa.softwarequest.resources.planet_variaveis
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * Home de demonstração incluída no ZIP para testar a abertura da modal.
 * Se já tiver uma Home própria, mantenha a sua e ligue o onClick do planeta
 * "Órbitas" ao callback `onOrbitasClick` (veja INTEGRACAO.md).
 */
@Composable
fun SoftwareQuestHomeScreen(
    onOrbitasClick: () -> Unit,
    onStartLesson: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    displayName: String = "Cadete Alex",
    selectedWorld: OrbitWorldUi? = null,
    onNavigate: (QuestTab) -> Unit = {},
) {
    Box(Modifier.fillMaxSize()) {
        QuestSpaceBackground(Modifier.fillMaxSize())
        Column(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
            ) {
                Spacer(Modifier.height(24.dp))
                HomeProfile(displayName, onProfileClick)
                Spacer(Modifier.height(12.dp))
                HomeProgress(onProfileClick)
                Spacer(Modifier.height(12.dp))
                HomePlanetMap(onOrbitasClick)
                Spacer(Modifier.height(36.dp))
                Text(
                    "Transmissão Ativa",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White,
                )
                Spacer(Modifier.height(12.dp))
                HomeTransmission(selectedWorld, onStartLesson)
                Spacer(Modifier.height(20.dp))
            }
            QuestBottomNavigation(QuestTab.EXPLORE, onNavigate)
        }
    }
}

@Composable
private fun HomeProfile(displayName: String, onProfileClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(66.dp)
            .clip(RoundedCornerShape(17.dp))
            .background(Color(0xFF101027))
            .border(1.dp, Color(0xFF44336F), RoundedCornerShape(17.dp))
            .clickable(role = Role.Button, onClickLabel = "Abrir perfil do tripulante",
                onClick = onProfileClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(Res.drawable.profile_avatar),
            contentDescription = "Avatar do tripulante",
            modifier = Modifier.size(42.dp).clip(CircleShape)
                .border(1.5.dp, QuestColors.cyan, CircleShape),
            contentScale = ContentScale.Crop,
        )
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(displayName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp,
                maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("ID: #404-ORION", color = QuestColors.muted, fontSize = 10.sp)
        }
        Box(
            Modifier.clip(CircleShape).background(Color(0xFFFFF0C2))
                .border(1.5.dp, Color(0xFFFFAE00), CircleShape)
                .padding(horizontal = 10.dp, vertical = 5.dp),
        ) {
            Text("♙ 120 XP", color = Color(0xFFFF9700), fontWeight = FontWeight.Bold, fontSize = 11.sp)
        }
        Spacer(Modifier.width(6.dp))
        Box(
            Modifier.clip(CircleShape).background(Color(0xFF371359))
                .border(1.dp, Color(0xFFB03BFF), CircleShape)
                .padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            Text("NV. 4", color = Color(0xFFDEC1FF), fontWeight = FontWeight.Bold, fontSize = 10.sp)
        }
    }
}

@Composable
private fun HomeProgress(onProfileClick: () -> Unit) {
    Column(Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        .clickable(role = Role.Button, onClickLabel = "Ver progresso do perfil",
            onClick = onProfileClick)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Progresso da Licença", color = QuestColors.muted, fontSize = 10.sp)
            Text("80/100 XP para Nível 5", color = QuestColors.cyan,
                fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(4.dp))
        Box(Modifier.fillMaxWidth().height(10.dp).clip(CircleShape)
            .background(Color(0xFFECE9FF))) {
            Box(Modifier.fillMaxWidth(.27f).height(10.dp).clip(CircleShape)
                .background(QuestColors.cyan))
        }
    }
}

@Composable
private fun HomePlanetMap(onOrbitasClick: () -> Unit) {
    BoxWithConstraints(Modifier.fillMaxWidth().height(390.dp)) {
        val mapWidth = maxWidth
        fun x(center: Float, diameter: Dp): Dp = mapWidth * (center / 402f) - diameter * .5f

        Canvas(Modifier.fillMaxSize()) {
            val scale = size.width / 402f
            val dash = PathEffect.dashPathEffect(floatArrayOf(3.dp.toPx(), 5.dp.toPx()))
            fun line(x1: Float, y1: Float, x2: Float, y2: Float) {
                drawLine(
                    QuestColors.cyan.copy(alpha = .8f),
                    Offset(x1 * scale, y1.dp.toPx()),
                    Offset(x2 * scale, y2.dp.toPx()),
                    strokeWidth = 1.5.dp.toPx(), pathEffect = dash, cap = StrokeCap.Round,
                )
            }
            line(230f, 60f, 58f, 106f)
            line(230f, 60f, 330f, 220f)
            line(58f, 106f, 177f, 209f)
            line(177f, 209f, 58f, 345f)
            line(330f, 220f, 332f, 346f)
        }

        HomePlanet(
            image = Res.drawable.planet_lagos, label = "Lagos", diameter = 46.dp,
            modifier = Modifier.offset(x = x(58f, 46.dp), y = 82.dp),
            isLocked = true,
        )
        HomePlanet(
            image = Res.drawable.planet_funcoes, label = "Funções", diameter = 46.dp,
            modifier = Modifier.offset(x = x(230f, 46.dp), y = 36.dp),
            isLocked = true,
        )
        HomePlanet(
            image = Res.drawable.planet_orbitas, label = "Órbitas", diameter = 80.dp,
            modifier = Modifier.offset(x = x(338f, 80.dp), y = 8.dp),
            isSelected = true,
            onClick = onOrbitasClick,
        )
        HomePlanet(
            image = Res.drawable.planet_variaveis, label = "Variáveis", diameter = 78.dp,
            modifier = Modifier.offset(x = x(177f, 78.dp), y = 173.dp),
            isSelected = true,
            subtitle = "EM ÓRBITA",
        )
        HomePlanet(
            image = Res.drawable.planet_estruturas, label = "Estruturas", diameter = 46.dp,
            modifier = Modifier.offset(x = x(330f, 46.dp), y = 194.dp),
            isLocked = true,
        )
        HomePlanet(
            image = Res.drawable.planet_logica, label = "Lógica", diameter = 46.dp,
            modifier = Modifier.offset(x = x(58f, 46.dp), y = 319.dp),
            isCompleted = true,
        )
        Column(
            Modifier.offset(x = x(332f, 78.dp), y = 315.dp)
                .width(78.dp).height(57.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF1D1436))
                .border(1.dp, Color(0xFFDA8A00), RoundedCornerShape(12.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("🎁", fontSize = 22.sp)
            Text("RECOMPENSA", color = QuestColors.yellow, fontSize = 8.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun HomePlanet(
    image: DrawableResource,
    label: String,
    diameter: Dp,
    modifier: Modifier = Modifier,
    isLocked: Boolean = false,
    isSelected: Boolean = false,
    isCompleted: Boolean = false,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .width(diameter + 12.dp)
            .then(
                if (onClick != null) Modifier.clickable(role = Role.Button, onClick = onClick)
                    .semantics { contentDescription = "Órbitas, abrir seletor de órbitas" }
                else Modifier
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier.size(diameter).clip(CircleShape)
                    .then(
                        if (isSelected) Modifier.border(1.5.dp, QuestColors.cyan, CircleShape)
                        else Modifier
                    ),
                contentScale = ContentScale.Crop,
            )
            if (isLocked) {
                Text("🔒", fontSize = 12.sp, modifier = Modifier.align(Alignment.BottomEnd))
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(label, color = if (isSelected || isCompleted) QuestColors.cyan else QuestColors.muted,
            fontSize = if (isSelected) 12.sp else 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1)
        if (subtitle != null) {
            Text(subtitle, color = QuestColors.cyan, fontSize = 8.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun HomeTransmission(selectedWorld: OrbitWorldUi?, onStartLesson: () -> Unit) {
    val title = if (selectedWorld == null) "Variáveis Cósmicas" else "Mundo ${selectedWorld.number.toString().padStart(2, '0')} selecionado"
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            .height(172.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFF1B173C), Color(0xFF171638))))
            .border(1.dp, QuestColors.cyan, RoundedCornerShape(20.dp))
            .padding(16.dp),
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Box(Modifier.clip(RoundedCornerShape(8.dp)).background(QuestColors.cyan)
                .padding(horizontal = 10.dp, vertical = 5.dp)) {
                Text("MÓDULO 2 · EXPLORANDO", color = Color(0xFF072339),
                    fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Text("🟡 20 bt     20 XP", color = Color(0xFFE4C2FF), fontSize = 10.sp)
        }
        Spacer(Modifier.height(13.dp))
        Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(Modifier.height(4.dp))
        Text("Aprenda a declarar containers de dados para catalogar novos elementos no espaço.",
            color = QuestColors.muted, fontSize = 13.sp, lineHeight = 18.sp, maxLines = 2,
            overflow = TextOverflow.Ellipsis)
        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text("◷ ~2 min", color = QuestColors.muted, fontSize = 11.sp)
            Box(Modifier.clip(RoundedCornerShape(10.dp)).background(QuestColors.cyan)
                .clickable(onClick = onStartLesson)
                .padding(horizontal = 15.dp, vertical = 8.dp)) {
                Text("Iniciar Salto ›", color = Color(0xFF071322),
                    fontWeight = FontWeight.Bold, fontSize = 11.sp)
            }
        }
    }
}
