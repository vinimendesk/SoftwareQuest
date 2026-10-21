package br.uepa.softwarequest1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.uepa.softwarequest1.color.JornadaColors
import org.jetbrains.compose.resources.painterResource
import softwarequest1.shared.generated.resources.Res
import softwarequest1.shared.generated.resources.playCircle
import softwarequest1.shared.generated.resources.schedule

@Composable
fun MissionBriefing() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(225.dp)
    ) {

        Text(
            text = "Transmissão Ativa",
            modifier = Modifier.offset(
                x = 20.dp,
                y = 0.dp
            ),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        MissionCard()
    }
}

@Composable
private fun MissionCard() {

    Column(
        modifier = Modifier
            .offset(
                x = 20.dp,
                y = 22.dp
            )
            .width(362.dp)
            .height(173.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(JornadaColors.CardBackground)
            .border(
                width = 1.dp,
                color = JornadaColors.Cyan,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        MissionHeader()

        MissionDetails()

        MissionFooter()
    }
}

@Composable
private fun MissionHeader() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(27.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .height(21.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(JornadaColors.Cyan)
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "ATIVIDADE #01",
                color = Color(0xFF475569),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }

        XpBadge("20 XP")
    }
}

@Composable
private fun MissionDetails() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(59.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Text(
            text = "Complete sua primeira missão",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 19.sp
        )

        Text(
            text = "Explore o mapa espacial e descubra novos caminhos.",
            color = JornadaColors.TextSecondary,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun MissionFooter() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(31.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(Res.drawable.schedule),
                contentDescription = null,
                tint = Color(0xFF64748B),
                modifier = Modifier.size(14.dp)
            )

            Text(
                text = "10 min",
                color = JornadaColors.TextSecondary,
                fontSize = 12.sp
            )
        }

        Button(
            onClick = {
                // Iniciar missão
            },
            modifier = Modifier.height(31.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = JornadaColors.Cyan
            ),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            )
        ) {

            Text(
                text = "INICIAR",
                color = JornadaColors.TextDark,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.width(4.dp)
            )

            Icon(
                painter = painterResource(Res.drawable.playCircle),
                contentDescription = null,
                tint = JornadaColors.TextDark,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}