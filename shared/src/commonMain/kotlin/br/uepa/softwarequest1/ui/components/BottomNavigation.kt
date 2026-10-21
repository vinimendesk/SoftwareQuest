package br.uepa.softwarequest1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.uepa.softwarequest1.color.JornadaColors
import org.jetbrains.compose.resources.painterResource
import softwarequest1.shared.generated.resources.Res
import softwarequest1.shared.generated.resources.checkList
import softwarequest1.shared.generated.resources.home
import softwarequest1.shared.generated.resources.locationOn
import softwarequest1.shared.generated.resources.moodHeart

@Composable
fun BottomNavigation() {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .background(JornadaColors.NavigationBackground)
                .border(
                    width = 1.dp,
                    color = JornadaColors.NavigationBorder
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            NavigationItem(
                icon = painterResource(Res.drawable.locationOn),
                label = "Explorar",
                selected = true
            )

            NavigationItem(
                icon = painterResource(Res.drawable.checkList),
                label = "Missões"
            )

            NavigationItem(
                icon = painterResource(Res.drawable.home),
                label = "Estações"
            )

            NavigationItem(
                icon = painterResource(Res.drawable.moodHeart),
                label = "Federação"
            )
        }

        HomeIndicator()
    }
}

@Composable
private fun NavigationItem(
    icon: Painter,
    label: String,
    selected: Boolean = false
) {

    Column(
        modifier = Modifier
            /*.weight(1f)*/
            .height(42.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Icon(
            painter = icon,
            contentDescription = label,
            tint = if (selected)
                JornadaColors.Cyan
            else
                JornadaColors.TextSecondary,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = label,
            color = if (selected)
                JornadaColors.Cyan
            else
                JornadaColors.TextSecondary,
            fontSize = 11.sp,
            fontWeight = if (selected)
                FontWeight.Bold
            else
                FontWeight.Medium
        )
    }
}

@Composable
private fun HomeIndicator() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .background(JornadaColors.NavigationBackground),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .width(140.dp)
                .height(5.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Color.White.copy(alpha = 0.25f))
        )
    }
}