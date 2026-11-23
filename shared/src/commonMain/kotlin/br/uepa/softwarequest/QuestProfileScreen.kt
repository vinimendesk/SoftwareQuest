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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.uepa.softwarequest.resources.Res
import br.uepa.softwarequest.resources.profile_avatar
import org.jetbrains.compose.resources.painterResource

/** Tela modal em tela cheia baseada em Perfil.png. Abre pelo cartão de perfil da Home. */
@Composable
fun QuestProfileScreen(
    displayName: String,
    onDismiss: () -> Unit,
    onStartLesson: () -> Unit,
    onUpdateName: (String) -> Unit,
    onLogout: () -> Unit,
) {
    var editNameOpen by rememberSaveable { mutableStateOf(false) }
    var newName by remember { mutableStateOf(displayName) }
    var settingsDialog by rememberSaveable { mutableStateOf<String?>(null) }
    var confirmLogout by rememberSaveable { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        QuestSpaceBackground(Modifier.fillMaxSize())
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Spacer(Modifier.height(8.dp))
            Box(Modifier.fillMaxWidth().height(42.dp).padding(horizontal = 20.dp)) {
                QuestHeaderControl("Voltar para Explorar", onDismiss,
                    modifier = Modifier.align(Alignment.CenterStart)) {
                    QuestLineIcon(QuestGlyph.ARROW_LEFT, Modifier.size(20.dp), Color.White)
                }
                Text("PERFIL", color = Color.White, fontSize = 15.sp,
                    fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center))
            }
            Spacer(Modifier.height(24.dp))
            QuestProfileCard(displayName, Modifier.padding(horizontal = 20.dp)) {
                newName = displayName
                editNameOpen = true
            }
            Spacer(Modifier.height(24.dp))
            QuestProfileSectionTitle("Sua jornada")
            Spacer(Modifier.height(13.dp))
            QuestProfileJourneyCard(onStartLesson, Modifier.padding(horizontal = 20.dp))
            Spacer(Modifier.height(24.dp))
            QuestProfileSectionTitle("Conquistas")
            Spacer(Modifier.height(13.dp))
            QuestAchievementCard(Modifier.padding(horizontal = 20.dp))
            Spacer(Modifier.height(24.dp))
            QuestProfileSectionTitle("Configurações")
            Spacer(Modifier.height(13.dp))
            QuestProfileSettings(
                onNotifications = { settingsDialog = "Notificações" },
                onPrivacy = { settingsDialog = "Privacidade" },
                onHelp = { settingsDialog = "Ajuda" },
                onLogout = { confirmLogout = true },
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Spacer(Modifier.height(24.dp))
        }
    }

    if (editNameOpen) {
        AlertDialog(
            onDismissRequest = { editNameOpen = false },
            containerColor = QuestPanel,
            title = { Text("Editar perfil", color = Color.White, fontWeight = FontWeight.Bold) },
            text = {
                QuestAuthField(
                    label = "Nome de cadete", placeholder = "Seu nome",
                    value = newName, onValueChange = { newName = it },
                    icon = QuestGlyph.PERSON,
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newName.trim().length >= 2) {
                        onUpdateName(newName.trim())
                        editNameOpen = false
                    }
                }) { Text("Salvar", color = QuestColors.cyan) }
            },
            dismissButton = {
                TextButton(onClick = { editNameOpen = false }) {
                    Text("Cancelar", color = QuestColors.muted)
                }
            },
        )
    }
    if (settingsDialog != null) {
        val description = when (settingsDialog) {
            "Notificações" -> "As preferências de notificações serão configuradas quando o serviço estiver integrado."
            "Privacidade" -> "Os controles e documentos de privacidade serão disponibilizados na integração com os serviços do aplicativo."
            else -> "Acompanhe sua jornada, avance pelos planetas e conclua missões para receber XP e bits."
        }
        QuestHelpDialog(settingsDialog.orEmpty(), description) { settingsDialog = null }
    }
    if (confirmLogout) {
        AlertDialog(
            onDismissRequest = { confirmLogout = false },
            containerColor = QuestPanel,
            title = { Text("Sair da conta?", color = Color.White, fontWeight = FontWeight.Bold) },
            text = { Text("Você retornará à tela de login. Os dados de demonstração não são armazenados no servidor.",
                color = QuestColors.muted) },
            confirmButton = {
                TextButton(onClick = { confirmLogout = false; onLogout() }) {
                    Text("Sair", color = Color(0xFFE9B7FF))
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmLogout = false }) {
                    Text("Cancelar", color = QuestColors.muted)
                }
            },
        )
    }
}

@Composable
private fun QuestProfileCard(
    displayName: String,
    modifier: Modifier = Modifier,
    onEdit: () -> Unit,
) {
    Column(
        modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF13112D))
            .border(1.dp, QuestBorder, RoundedCornerShape(20.dp))
            .padding(17.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painterResource(Res.drawable.profile_avatar),
                contentDescription = "Avatar do tripulante",
                modifier = Modifier.size(70.dp).clip(CircleShape)
                    .border(1.5.dp, QuestColors.cyan, CircleShape),
                contentScale = ContentScale.Crop,
            )
            Spacer(Modifier.width(14.dp))
            Column {
                Text(displayName, color = Color.White, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(3.dp))
                Text("ID #404-ORION", color = QuestColors.muted, fontSize = 11.sp)
                Spacer(Modifier.height(7.dp))
                Box(Modifier.clip(CircleShape).background(Color(0xFF261139))
                    .border(1.dp, Color(0xFFB03BFF), CircleShape)
                    .padding(horizontal = 8.dp, vertical = 3.dp)) {
                    Text("NV. 4", color = Color(0xFFE2C1FF), fontSize = 10.sp,
                        fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Saldo de XP", color = QuestColors.muted, fontSize = 12.sp)
            Box(Modifier.clip(CircleShape).background(Color(0xFF2B211A))
                .border(1.dp, Color(0xFF6C5012), CircleShape)
                .padding(horizontal = 11.dp, vertical = 6.dp)) {
                Text("♙ 120 XP", color = QuestOrange, fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold)
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Para Nível 5", color = QuestColors.muted, fontSize = 12.sp)
            Text("80/100 XP", color = QuestColors.cyan, fontSize = 12.sp,
                fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(9.dp))
        QuestBar(.8f, height = 8.dp, track = Color(0xFF26203E))
        Spacer(Modifier.height(16.dp))
        Row(
            Modifier.fillMaxWidth().height(38.dp)
                .clip(RoundedCornerShape(11.dp))
                .border(1.dp, Color(0xFF44316C), RoundedCornerShape(11.dp))
                .clickable(role = Role.Button, onClick = onEdit),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            QuestLineIcon(QuestGlyph.PENCIL, Modifier.size(16.dp), QuestColors.cyan)
            Spacer(Modifier.width(7.dp))
            Text("Editar perfil", color = QuestColors.cyan,
                fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@Composable
private fun QuestProfileSectionTitle(title: String) {
    Text(title, modifier = Modifier.padding(horizontal = 20.dp),
        color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
}

@Composable
private fun QuestProfileJourneyCard(onStartLesson: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxWidth().height(173.dp).clip(RoundedCornerShape(20.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFF19163D), Color(0xFF171638))))
            .border(1.dp, QuestColors.cyan, RoundedCornerShape(20.dp))
            .padding(15.dp),
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.clip(RoundedCornerShape(8.dp)).background(QuestColors.cyan)
                .padding(horizontal = 10.dp, vertical = 5.dp)) {
                Text("MÓDULO 2 · EXPLORANDO", color = Color(0xFF052534),
                    fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.weight(1f))
            QuestRewardPill("♙ 20 bt", QuestOrange, Color(0xFF2E2119))
            Spacer(Modifier.width(5.dp))
            QuestRewardPill("♙ 20 XP", Color(0xFFDBAAFF), Color(0xFF241138))
        }
        Spacer(Modifier.height(14.dp))
        Text("Variáveis Cósmicas", color = Color.White, fontWeight = FontWeight.Bold,
            fontSize = 16.sp)
        Spacer(Modifier.height(5.dp))
        Text("Aprenda a declarar containers de dados para\ncatalogar novos elementos no espaço.",
            color = QuestColors.muted, fontSize = 13.sp, lineHeight = 18.sp,
            maxLines = 2)
        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text("◷ ~2 min", color = QuestColors.muted, fontSize = 11.sp)
            QuestSolidButton("Iniciar Salto ⊙", onStartLesson,
                modifier = Modifier.width(119.dp), height = 33.dp)
        }
    }
}

@Composable
private fun QuestRewardPill(text: String, accent: Color, fill: Color) {
    Box(Modifier.clip(CircleShape).background(fill).border(1.dp, accent, CircleShape)
        .padding(horizontal = 7.dp, vertical = 5.dp)) {
        Text(text, color = accent, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun QuestAchievementCard(modifier: Modifier = Modifier) {
    Row(
        modifier.fillMaxWidth().height(75.dp).clip(RoundedCornerShape(17.dp))
            .background(Brush.horizontalGradient(listOf(Color(0xFF211B22), Color(0xFF171719))))
            .border(1.dp, Color(0xFF60430D), RoundedCornerShape(17.dp))
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(46.dp).clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF32251D)), contentAlignment = Alignment.Center) {
            QuestLineIcon(QuestGlyph.MEDAL, Modifier.size(27.dp), QuestOrange)
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text("Base de lógica", color = Color.White,
                fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Spacer(Modifier.height(4.dp))
            Text("Lógica concluída. Pronto para explorar!", color = QuestColors.muted,
                fontSize = 11.sp)
        }
    }
}

@Composable
private fun QuestProfileSettings(
    onNotifications: () -> Unit,
    onPrivacy: () -> Unit,
    onHelp: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
        .background(Color(0xFF14112E))
        .border(1.dp, QuestBorder, RoundedCornerShape(16.dp))) {
        QuestSettingsItem("Notificações", QuestGlyph.BELL, onNotifications)
        Box(Modifier.fillMaxWidth().height(1.dp).background(QuestBorder))
        QuestSettingsItem("Privacidade", QuestGlyph.SHIELD, onPrivacy)
        Box(Modifier.fillMaxWidth().height(1.dp).background(QuestBorder))
        QuestSettingsItem("Ajuda", QuestGlyph.HELP, onHelp)
        Box(Modifier.fillMaxWidth().height(1.dp).background(QuestBorder))
        QuestSettingsItem("Sair da conta", QuestGlyph.LOGOUT, onLogout, Color(0xFFE5B4FF))
    }
}

@Composable
private fun QuestSettingsItem(
    title: String,
    icon: QuestGlyph,
    onClick: () -> Unit,
    tint: Color = QuestColors.muted,
) {
    Row(Modifier.fillMaxWidth().height(49.dp).clickable(role = Role.Button, onClick = onClick)
        .padding(horizontal = 15.dp), verticalAlignment = Alignment.CenterVertically) {
        QuestLineIcon(icon, Modifier.size(20.dp), tint)
        Spacer(Modifier.width(13.dp))
        Text(title, color = if (icon == QuestGlyph.LOGOUT) tint else Color.White,
            fontSize = 13.sp)
        Spacer(Modifier.weight(1f))
        QuestLineIcon(QuestGlyph.CHEVRON_RIGHT, Modifier.size(15.dp), QuestColors.muted)
    }
}
