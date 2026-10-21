package br.uepa.softwarequest1.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import br.uepa.softwarequest1.color.JornadaColors
import br.uepa.softwarequest1.ui.components.BottomNavigation
import br.uepa.softwarequest1.ui.components.HeaderStack
import br.uepa.softwarequest1.ui.components.MissionBriefing
import br.uepa.softwarequest1.ui.components.SpaceMap
import br.uepa.softwarequest1.ui.components.StarfieldOverlay

@Composable
fun JornadaEspacialScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        JornadaColors.BackgroundStart,
                        JornadaColors.BackgroundCenter,
                        JornadaColors.BackgroundEnd
                    )
                )
            )
    ) {

        // Camada espacial.
        StarfieldOverlay()

        // Conteúdo principal.
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            HeaderStack()

            SpaceMap()

            MissionBriefing()

            BottomNavigation()
        }
    }
}