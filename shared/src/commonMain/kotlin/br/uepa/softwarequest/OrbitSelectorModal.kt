package br.uepa.softwarequest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.uepa.softwarequest.resources.Res
import br.uepa.softwarequest.resources.orbit_world_01
import br.uepa.softwarequest.resources.orbit_world_02
import br.uepa.softwarequest.resources.orbit_world_03
import br.uepa.softwarequest.resources.orbit_world_04
import br.uepa.softwarequest.resources.orbit_world_05
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/** Informações de apresentação; futuramente podem vir de um ViewModel/Supabase. */
data class OrbitWorldUi(
    val number: Int,
    val title: String,
    val description: String,
    val missions: Int,
    val rewardXp: Int,
    val difficulty: String,
    val completionPercent: Int,
    val artwork: DrawableResource,
    // O protótipo mostra 15% no texto, mas a barra tem cerca de 30% de preenchimento.
    // Mantido separadamente para reproduzir a imagem com fidelidade.
    val previewBarFraction: Float = completionPercent / 100f,
)

/**
 * Reprodução da tela `canvas-container.png`.
 *
 * Use dentro de um Box acima da Home, condicionalmente a um estado booleano.
 * Não cria uma Activity, não depende de APIs Android e funciona no commonMain.
 * A área é modal: cobre toda a Home enquanto estiver visível.
 */
@Composable
fun OrbitSelectorModal(
    onDismiss: () -> Unit,
    onExploreWorld: (OrbitWorldUi) -> Unit,
    modifier: Modifier = Modifier,
    initialIndex: Int = 2,
    worlds: List<OrbitWorldUi> = softwareQuestDemoWorlds(),
    onNavigate: (QuestTab) -> Unit = {},
) {
    require(worlds.isNotEmpty()) { "A seleção de órbitas precisa de pelo menos um mundo." }
    var selectedIndex by rememberSaveable { mutableIntStateOf(initialIndex.coerceIn(worlds.indices)) }
    var showHelp by remember { mutableStateOf(false) }
    val selected = worlds[selectedIndex.coerceIn(worlds.indices)]

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(QuestColors.background),
    ) {
        QuestSpaceBackground(Modifier.fillMaxSize())

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.height(32.dp))
            OrbitSelectorHeader(
                onBack = onDismiss,
                onHelp = { showHelp = true },
            )

            // Em telas pequenas, só o conteúdo central rola; o CTA e a barra ficam fixos.
            Box(Modifier.weight(1f).fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(Modifier.height(16.dp))
                    OrbitHero(
                        world = selected,
                        onPrevious = { selectedIndex = (selectedIndex - 1 + worlds.size) % worlds.size },
                        onNext = { selectedIndex = (selectedIndex + 1) % worlds.size },
                    )
                    Spacer(Modifier.height(22.dp))
                    OrbitDots(
                        count = worlds.size,
                        selectedIndex = selectedIndex,
                        onSelect = { selectedIndex = it },
                    )
                    Spacer(Modifier.height(30.dp))
                    OrbitWorldCard(
                        world = selected,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    )
                    Spacer(Modifier.height(20.dp))
                }
            }

            OrbitExploreAction(
                worldNumber = selected.number,
                onClick = { onExploreWorld(selected) },
            )
            QuestBottomNavigation(
                selected = QuestTab.EXPLORE,
                onClick = { tab ->
                    if (tab != QuestTab.EXPLORE) onNavigate(tab)
                    onDismiss()
                },
            )
        }
    }

    if (showHelp) {
        AlertDialog(
            onDismissRequest = { showHelp = false },
            title = { Text("Seletor de Órbitas") },
            text = { Text("Use as setas para escolher um mundo e toque em Explorar para continuar a jornada.") },
            confirmButton = {
                TextButton(onClick = { showHelp = false }) { Text("Entendi") }
            },
        )
    }
}

@Composable
private fun OrbitSelectorHeader(onBack: () -> Unit, onHelp: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(36.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF101026))
                .border(1.dp, Color(0xFF262142), RoundedCornerShape(12.dp))
                .clickable(role = Role.Button, onClick = onBack)
                .semantics { contentDescription = "Voltar à exploração" },
            contentAlignment = Alignment.Center,
        ) {
            Canvas(Modifier.size(20.dp)) {
                val stroke = 2.1.dp.toPx()
                drawLine(Color.White, Offset(size.width * .77f, size.height * .5f),
                    Offset(size.width * .22f, size.height * .5f), stroke, StrokeCap.Round)
                drawLine(Color.White, Offset(size.width * .22f, size.height * .5f),
                    Offset(size.width * .49f, size.height * .23f), stroke, StrokeCap.Round)
                drawLine(Color.White, Offset(size.width * .22f, size.height * .5f),
                    Offset(size.width * .49f, size.height * .77f), stroke, StrokeCap.Round)
            }
        }

        Text(
            text = "SELETOR DE ÓRBITAS",
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = .15.sp,
            maxLines = 1,
        )

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF101026))
                .border(1.dp, Color(0xFF262142), RoundedCornerShape(12.dp))
                .clickable(role = Role.Button, onClick = onHelp)
                .semantics { contentDescription = "Ajuda do seletor de órbitas" },
            contentAlignment = Alignment.Center,
        ) {
            Box(
                Modifier.size(16.dp).border(1.7.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text("?", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun OrbitHero(
    world: OrbitWorldUi,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
) {
    Box(Modifier.fillMaxWidth().height(160.dp)) {
        Image(
            painter = painterResource(world.artwork),
            contentDescription = "Ilustração do Mundo ${world.number.toString().padStart(2, '0')}",
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.Center)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        OrbitChevron(
            isPrevious = true,
            onClick = onPrevious,
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 24.dp),
        )
        OrbitChevron(
            isPrevious = false,
            onClick = onNext,
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 24.dp),
        )
    }
}

@Composable
private fun OrbitChevron(isPrevious: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(37.dp)
            .clip(CircleShape)
            .background(Color(0xFF1A1B2E))
            .clickable(role = Role.Button, onClick = onClick)
            .semantics {
                contentDescription = if (isPrevious) "Órbita anterior" else "Próxima órbita"
            },
        contentAlignment = Alignment.Center,
    ) {
        Canvas(Modifier.size(18.dp)) {
            val x1 = if (isPrevious) .63f else .37f
            val x2 = if (isPrevious) .33f else .67f
            val color = if (isPrevious) Color(0xFF97A0B7) else Color.White
            drawLine(color, Offset(size.width * x1, size.height * .2f),
                Offset(size.width * x2, size.height * .5f), 2.6.dp.toPx(), StrokeCap.Round)
            drawLine(color, Offset(size.width * x2, size.height * .5f),
                Offset(size.width * x1, size.height * .8f), 2.6.dp.toPx(), StrokeCap.Round)
        }
    }
}

@Composable
private fun OrbitDots(count: Int, selectedIndex: Int, onSelect: (Int) -> Unit) {
    Row(
        modifier = Modifier.height(8.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(count) { index ->
            Box(
                modifier = Modifier
                    .width(if (index == selectedIndex) 21.dp else 13.dp)
                    .height(18.dp)
                    .clickable(role = Role.Button) { onSelect(index) }
                    .semantics { contentDescription = "Selecionar Mundo ${index + 1}" },
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    Modifier
                        .size(
                            width = if (index == selectedIndex) 16.dp else 6.dp,
                            height = 6.dp,
                        )
                        .clip(CircleShape)
                        .background(if (index == selectedIndex) QuestColors.cyan else Color(0xFF4A536D)),
                )
            }
        }
    }
}

@Composable
private fun OrbitWorldCard(world: OrbitWorldUi, modifier: Modifier = Modifier) {
    val cardShape = RoundedCornerShape(24.dp)
    Box(
        modifier = modifier
            .heightIn(min = 272.dp)
            .clip(cardShape)
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF100F27), Color(0xFF171231), Color(0xFF111029)),
                ),
            )
            .border(1.dp, QuestColors.cyan, cardShape),
    ) {
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(Color(0xFF69647F).copy(alpha = .45f), radius = 1.4.dp.toPx(),
                center = Offset(size.width * .723f, 13.dp.toPx()))
        }
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 19.dp, vertical = 19.dp)) {
            Text(
                text = "MUNDO ${world.number.toString().padStart(2, '0')}",
                color = QuestColors.cyan,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                lineHeight = 16.sp,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = world.title,
                color = Color.White,
                fontSize = 23.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 28.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(7.dp))
            Text(
                text = world.description,
                color = QuestColors.muted,
                fontSize = 13.sp,
                lineHeight = 20.sp,
                minLines = 3,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(11.dp))
            QuestDivider()
            Spacer(Modifier.height(9.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                WorldStat("Desafios", "${world.missions} Missões", Color.White, Modifier.weight(1.12f))
                WorldStat("Recompensa", "${world.rewardXp} XP", QuestColors.yellow, Modifier.weight(1f))
                WorldStat("Dificuldade", world.difficulty, QuestColors.cyan, Modifier.weight(.98f))
            }
            Spacer(Modifier.height(8.dp))
            QuestDivider()
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Progresso Geral do Mundo", color = QuestColors.muted,
                    fontSize = 12.5.sp, maxLines = 1)
                Text("${world.completionPercent}% concluído", color = QuestColors.muted,
                    fontSize = 12.5.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            }
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(Color(0xFFEDEBFB)),
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(world.previewBarFraction.coerceIn(0f, 1f))
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(7.dp))
                        .background(QuestColors.cyan),
                )
            }
        }
    }
}

@Composable
private fun WorldStat(label: String, value: String, valueColor: Color, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(label, color = QuestColors.muted, fontSize = 11.sp, lineHeight = 15.sp)
        Spacer(Modifier.height(1.dp))
        Text(value, color = valueColor, fontSize = 14.sp, fontWeight = FontWeight.Bold,
            lineHeight = 18.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun QuestDivider() {
    Box(Modifier.fillMaxWidth().height(1.dp).background(Color(0xFF292440)))
}

@Composable
private fun OrbitExploreAction(worldNumber: Int, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(94.dp)
            .background(QuestColors.bottom),
    ) {
        Spacer(Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(QuestColors.cyan)
                .clickable(role = Role.Button, onClick = onClick),
            contentAlignment = Alignment.Center,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Explorar Mundo ${worldNumber.toString().padStart(2, '0')}",
                    color = Color(0xFF08101C), fontSize = 14.sp, fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.width(4.dp))
                Canvas(Modifier.size(12.dp)) {
                    drawLine(Color(0xFF08101C), Offset(size.width * .25f, size.height * .15f),
                        Offset(size.width * .70f, size.height * .5f), 2.dp.toPx(), StrokeCap.Round)
                    drawLine(Color(0xFF08101C), Offset(size.width * .70f, size.height * .5f),
                        Offset(size.width * .25f, size.height * .85f), 2.dp.toPx(), StrokeCap.Round)
                }
            }
        }
    }
}

internal object QuestColors {
    val background = Color(0xFF080818)
    val bottom = Color(0xFF100F29)
    val cyan = Color(0xFF00E5F2)
    val muted = Color(0xFF98A0B9)
    val yellow = Color(0xFFFFBE00)
}

/** Estrelas e nébulas vetoriais; nenhum bitmap de tela inteira é usado. */
@Composable
internal fun QuestSpaceBackground(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        drawRect(
            brush = Brush.verticalGradient(
                0f to Color(0xFF080817),
                .50f to Color(0xFF090D1E),
                1f to Color(0xFF0C0920),
            ),
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF103849).copy(alpha = .38f), Color.Transparent),
                center = Offset(size.width * .33f, size.height * .23f),
                radius = size.width * .58f,
            ),
            radius = size.width * .58f,
            center = Offset(size.width * .33f, size.height * .23f),
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF291243).copy(alpha = .32f), Color.Transparent),
                center = Offset(size.width * .51f, size.height * .52f),
                radius = size.width * .76f,
            ),
            radius = size.width * .76f,
            center = Offset(size.width * .51f, size.height * .52f),
        )
        // Frações extraídas da posição das estrelas do protótipo de 402 × 918.
        val stars = listOf(
            Triple(.800f, .110f, Color(0xFF8790A8)),
            Triple(.377f, .299f, QuestColors.cyan),
            Triple(.701f, .372f, Color(0xFF88879D)),
            Triple(.701f, .394f, Color(0xFF54556F)),
            Triple(.851f, .659f, Color(0xFF9C9BB0)),
            Triple(.304f, .804f, QuestColors.cyan),
            Triple(.130f, .073f, Color(0xFF4A516A)),
        )
        stars.forEach { (x, y, color) ->
            drawCircle(color = color, radius = 1.35.dp.toPx(),
                center = Offset(size.width * x, size.height * y))
        }
    }
}

/** Barra inferior compartilhada com a Home. */
enum class QuestTab { EXPLORE, MISSIONS, STATIONS, FEDERATION }

@Composable
internal fun QuestBottomNavigation(selected: QuestTab, onClick: (QuestTab) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(73.dp)
            .background(QuestColors.bottom),
    ) {
        Box(Modifier.fillMaxWidth().height(1.dp).background(Color(0xFF25203D)))
        Row(Modifier.fillMaxSize()) {
            val entries = listOf(
                QuestTab.EXPLORE to "Explorar",
                QuestTab.MISSIONS to "Missões",
                QuestTab.STATIONS to "Estações",
                QuestTab.FEDERATION to "Federação",
            )
            entries.forEach { (tab, title) ->
                val active = tab == selected
                val color = if (active) QuestColors.cyan else Color(0xFF9CA6BE)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(role = Role.Tab) { onClick(tab) },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    QuestNavIcon(tab, color, Modifier.size(24.dp))
                    Spacer(Modifier.height(3.dp))
                    Text(title, color = color, fontSize = 11.sp,
                        fontWeight = if (active) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@Composable
private fun QuestNavIcon(tab: QuestTab, color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val sw = 2.dp.toPx()
        fun point(x: Float, y: Float) = Offset(size.width * x, size.height * y)
        when (tab) {
            QuestTab.EXPLORE -> {
                val p = Path().apply {
                    moveTo(size.width * .5f, size.height * .94f)
                    cubicTo(size.width * .40f, size.height * .80f, size.width * .17f, size.height * .56f,
                        size.width * .17f, size.height * .39f)
                    cubicTo(size.width * .17f, size.height * .12f, size.width * .33f, size.height * .07f,
                        size.width * .5f, size.height * .07f)
                    cubicTo(size.width * .72f, size.height * .07f, size.width * .84f, size.height * .20f,
                        size.width * .84f, size.height * .39f)
                    cubicTo(size.width * .84f, size.height * .56f, size.width * .61f, size.height * .80f,
                        size.width * .5f, size.height * .94f)
                    close()
                }
                drawPath(p, color, style = Stroke(sw, cap = StrokeCap.Round, join = StrokeJoin.Round))
                drawCircle(color, radius = size.width * .115f, center = point(.5f, .38f),
                    style = Stroke(sw))
            }
            QuestTab.MISSIONS -> {
                drawLine(color, point(.16f, .22f), point(.80f, .22f), sw, StrokeCap.Round)
                drawLine(color, point(.16f, .48f), point(.70f, .48f), sw, StrokeCap.Round)
                drawLine(color, point(.16f, .75f), point(.50f, .75f), sw, StrokeCap.Round)
                drawLine(color, point(.60f, .73f), point(.73f, .86f), sw, StrokeCap.Round)
                drawLine(color, point(.73f, .86f), point(.94f, .61f), sw, StrokeCap.Round)
            }
            QuestTab.STATIONS -> {
                val p = Path().apply {
                    moveTo(size.width * .13f, size.height * .45f)
                    lineTo(size.width * .50f, size.height * .12f)
                    lineTo(size.width * .87f, size.height * .45f)
                    lineTo(size.width * .87f, size.height * .88f)
                    lineTo(size.width * .13f, size.height * .88f)
                    close()
                }
                drawPath(p, color, style = Stroke(sw, join = StrokeJoin.Round, cap = StrokeCap.Round))
                drawLine(color, point(.42f, .88f), point(.42f, .56f), sw)
                drawLine(color, point(.42f, .56f), point(.59f, .56f), sw)
                drawLine(color, point(.59f, .56f), point(.59f, .88f), sw)
            }
            QuestTab.FEDERATION -> {
                drawCircle(color, radius = size.width * .26f, center = point(.5f, .35f),
                    style = Stroke(sw))
                drawLine(color, point(.31f, .53f), point(.26f, .94f), sw, StrokeCap.Round)
                drawLine(color, point(.26f, .94f), point(.50f, .78f), sw, StrokeCap.Round)
                drawLine(color, point(.50f, .78f), point(.74f, .94f), sw, StrokeCap.Round)
                drawLine(color, point(.74f, .94f), point(.69f, .53f), sw, StrokeCap.Round)
            }
        }
    }
}

/**
 * Somente o Mundo 03 possui dados completos no arquivo canvas-container.png.
 * Os demais mundos são exemplos editáveis para demonstrar a troca pelas setas.
 */
fun softwareQuestDemoWorlds(): List<OrbitWorldUi> = listOf(
    OrbitWorldUi(
        number = 1, title = "Lógica de Programação",
        description = "Introdução à lógica, raciocínio e resolução de desafios na sua jornada espacial.",
        missions = 8, rewardXp = 200, difficulty = "Iniciante", completionPercent = 60,
        artwork = Res.drawable.orbit_world_01,
    ),
    OrbitWorldUi(
        number = 2, title = "Funções",
        description = "Organize comandos e descubra como reutilizar operações em suas missões.",
        missions = 10, rewardXp = 260, difficulty = "Iniciante", completionPercent = 40,
        artwork = Res.drawable.orbit_world_02,
    ),
    OrbitWorldUi(
        number = 3, title = "Variáveis & Tipos de Dados",
        description = "Aprenda a lidar com as memórias de naves estelares alocando strings, inteiros, booleanos e catalogando dados espaciais.",
        missions = 12, rewardXp = 320, difficulty = "Intermediário", completionPercent = 15,
        artwork = Res.drawable.orbit_world_03,
        previewBarFraction = .30f,
    ),
    OrbitWorldUi(
        number = 4, title = "Estruturas",
        description = "Explore diferentes estruturas para organizar informações durante sua viagem.",
        missions = 10, rewardXp = 300, difficulty = "Intermediário", completionPercent = 0,
        artwork = Res.drawable.orbit_world_04,
    ),
    OrbitWorldUi(
        number = 5, title = "Órbitas",
        description = "Continue sua expedição espacial e descubra novos caminhos de aprendizagem.",
        missions = 14, rewardXp = 400, difficulty = "Avançado", completionPercent = 0,
        artwork = Res.drawable.orbit_world_05,
    ),
)
