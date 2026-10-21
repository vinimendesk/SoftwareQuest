package br.uepa.softwarequest1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import softwarequest1.shared.generated.resources.SignalCellularAlt
import softwarequest1.shared.generated.resources.batteryFull
import softwarequest1.shared.generated.resources.star
import softwarequest1.shared.generated.resources.wifi

@Composable
fun HeaderStack() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(154.dp)
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 12.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        StatusBar()

        ProfileContainer()

        LicenseProgress()
    }
}

@Composable
private fun StatusBar() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "9:41",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 18.sp
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(Res.drawable.SignalCellularAlt),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(11.dp)
            )

            Icon(
                painter = painterResource(Res.drawable.wifi),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(11.dp)
            )

            Icon(
                painter = painterResource(Res.drawable.batteryFull),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Composable
private fun ProfileContainer() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(JornadaColors.ProfileBackground)
            .border(
                width = 1.dp,
                color = JornadaColors.ProfileBorder,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        ProfileIdentity()

        ProfileBadges()
    }
}

@Composable
private fun ProfileIdentity() {

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color(0xFF18183B))
                .border(
                    width = 1.5.dp,
                    color = JornadaColors.Cyan,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            /*
             * Substitua pelo asset real do personagem.
             *
             * O CSS original utiliza uma imagem de um
             * cadete espacial futurista.
             */
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF24244D))
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {

            Text(
                text = "Cadete Alex",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 18.sp
            )

            Text(
                text = "ID: #404-ORION",
                color = JornadaColors.TextSecondary,
                fontSize = 11.sp,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
private fun ProfileBadges() {

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        XpBadge(
            value = "80 XP"
        )

        LevelBadge(
            level = "NV. 4"
        )
    }
}

@Composable
// private fun
fun XpBadge(
    value: String
) {

    Row(
        modifier = Modifier
            .height(27.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(JornadaColors.XpBackground)
            .padding(
                horizontal = 10.dp,
                vertical = 6.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(Res.drawable.star),
            contentDescription = null,
            tint = JornadaColors.Xp,
            modifier = Modifier.size(14.dp)
        )

        Text(
            text = value,
            color = JornadaColors.Xp,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun LevelBadge(
    level: String
) {

    Box(
        modifier = Modifier
            .height(22.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(Color(0x33A020F0))
            .border(
                width = 1.dp,
                color = JornadaColors.Purple,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = level,
            color = JornadaColors.Level,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun LicenseProgress() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Progresso da Licença",
                color = JornadaColors.TextSecondary,
                fontSize = 11.sp
            )

            Text(
                text = "80/100 XP para Nível 5",
                color = JornadaColors.Cyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF1EDFD))
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.274f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(10.dp))
                    .background(JornadaColors.Cyan)
            )
        }
    }
}