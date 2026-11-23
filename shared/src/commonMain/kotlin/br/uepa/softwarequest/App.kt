package br.uepa.softwarequest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * Roteador compartilhado Android/iOS/desktop/web.
 * Autenticação, XP, compras e perfil são demonstrativos e vivem em memória.
 * Não há credenciais de Supabase nem SQLDelight nesta versão visual.
 */
@Composable
@Preview
fun App() {
    var authenticated by rememberSaveable { mutableStateOf(false) }
    var authMode by rememberSaveable { mutableStateOf(QuestAuthMode.LOGIN.name) }
    var displayName by rememberSaveable { mutableStateOf("Cadete Alex") }
    var currentTab by rememberSaveable { mutableStateOf(QuestTab.EXPLORE.name) }
    var orbitSelectorVisible by rememberSaveable { mutableStateOf(false) }
    var lessonVisible by rememberSaveable { mutableStateOf(false) }
    var profileVisible by rememberSaveable { mutableStateOf(false) }
    var selectedWorld by rememberSaveable { mutableStateOf<Int?>(null) }

    fun navigate(tab: QuestTab) {
        currentTab = tab.name
        orbitSelectorVisible = false
        lessonVisible = false
        profileVisible = false
    }

    fun logout() {
        authenticated = false
        authMode = QuestAuthMode.LOGIN.name
        displayName = "Cadete Alex"
        currentTab = QuestTab.EXPLORE.name
        orbitSelectorVisible = false
        lessonVisible = false
        profileVisible = false
        selectedWorld = null
    }

    MaterialTheme {
        Box(Modifier.fillMaxSize().background(QuestColors.bottom).safeDrawingPadding()) {
            if (!authenticated) {
                QuestAuthScreen(
                    mode = QuestAuthMode.valueOf(authMode),
                    onSwitchMode = {
                        authMode = if (authMode == QuestAuthMode.LOGIN.name)
                            QuestAuthMode.REGISTER.name else QuestAuthMode.LOGIN.name
                    },
                    onAuthenticated = { nameFromRegistration ->
                        displayName = nameFromRegistration ?: "Cadete Alex"
                        authenticated = true
                        navigate(QuestTab.EXPLORE)
                    },
                )
            } else {
                when (QuestTab.valueOf(currentTab)) {
                    QuestTab.EXPLORE -> SoftwareQuestHomeScreen(
                        onOrbitasClick = { orbitSelectorVisible = true },
                        onStartLesson = { lessonVisible = true },
                        onProfileClick = { profileVisible = true },
                        displayName = displayName,
                        selectedWorld = selectedWorld?.let { number ->
                            softwareQuestDemoWorlds().firstOrNull { it.number == number }
                        },
                        onNavigate = ::navigate,
                    )
                    QuestTab.MISSIONS -> QuestMissionsScreen(
                        onNavigate = ::navigate,
                        onBack = { navigate(QuestTab.EXPLORE) },
                    )
                    QuestTab.STATIONS -> QuestStationsScreen(
                        onNavigate = ::navigate,
                        onBack = { navigate(QuestTab.EXPLORE) },
                    )
                    QuestTab.FEDERATION -> QuestFederationScreen(
                        onNavigate = ::navigate,
                        onBack = { navigate(QuestTab.EXPLORE) },
                    )
                }
                if (orbitSelectorVisible) {
                    OrbitSelectorModal(
                        onDismiss = { orbitSelectorVisible = false },
                        onExploreWorld = { world ->
                            selectedWorld = world.number
                            orbitSelectorVisible = false
                        },
                        onNavigate = ::navigate,
                    )
                }
                if (profileVisible) {
                    QuestProfileScreen(
                        displayName = displayName,
                        onDismiss = { profileVisible = false },
                        onStartLesson = {
                            profileVisible = false
                            lessonVisible = true
                        },
                        onUpdateName = { displayName = it },
                        onLogout = ::logout,
                    )
                }
                if (lessonVisible) {
                    QuestLessonModal(
                        onDismiss = { lessonVisible = false },
                        onComplete = { lessonVisible = false },
                    )
                }
            }
        }
    }
}
