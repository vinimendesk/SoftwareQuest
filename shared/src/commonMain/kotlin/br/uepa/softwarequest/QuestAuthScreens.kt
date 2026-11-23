package br.uepa.softwarequest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Fluxo visual demonstrativo: não autentica no servidor nem armazena senhas. */
enum class QuestAuthMode { LOGIN, REGISTER }

private val AuthFieldColor = Color(0xFF14112D)
private val AuthFieldBorder = Color(0xFF40335F)
private val AuthWhite = Color(0xFFF9F9FF)

/** Login (1).png e Cadastro (1).png. Campos e ações reais de UI, autenticação simulada. */
@Composable
fun QuestAuthScreen(
    mode: QuestAuthMode,
    onSwitchMode: () -> Unit,
    onAuthenticated: (nameFromRegistration: String?) -> Unit,
) {
    val login = mode == QuestAuthMode.LOGIN
    var name by rememberSaveable(mode) { mutableStateOf("") }
    var email by rememberSaveable(mode) { mutableStateOf("") }
    // Nunca salvar a senha no estado persistível da Activity.
    var password by remember(mode) { mutableStateOf("") }
    var showPassword by rememberSaveable(mode) { mutableStateOf(false) }
    var termsAccepted by rememberSaveable(mode) { mutableStateOf(false) }
    var error by remember(mode) { mutableStateOf<String?>(null) }
    var information by remember { mutableStateOf<String?>(null) }

    fun submit() {
        val cleanEmail = email.trim()
        val emailValid = cleanEmail.contains('@') &&
            cleanEmail.substringAfter('@').contains('.') &&
            !cleanEmail.any { it.isWhitespace() } &&
            cleanEmail.substringBefore('@').isNotBlank()
        error = when {
            !login && name.trim().length < 2 -> "Informe um nome de cadete com pelo menos 2 caracteres."
            !emailValid -> "Informe um endereço de e-mail válido."
            login && password.isBlank() -> "Informe sua senha para continuar."
            !login && (password.length < 8 || !password.any { it.isLetter() } ||
                !password.any { it.isDigit() }) ->
                "A senha precisa ter 8 caracteres, incluindo letras e números."
            !login && !termsAccepted -> "Aceite os termos de uso e a política de privacidade."
            else -> null
        }
        if (error == null) onAuthenticated(if (login) null else name.trim())
    }

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val compact = maxHeight < 750.dp
        QuestSpaceBackground(Modifier.fillMaxSize())
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {
            Spacer(Modifier.height(if (login) { if (compact) 14.dp else 30.dp } else 10.dp))
            QuestOrbitLogo(
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .size(if (login) { if (compact) 126.dp else 148.dp } else 88.dp),
            )
            Spacer(Modifier.height(if (login) 15.dp else 12.dp))
            QuestBrandName(Modifier.align(Alignment.CenterHorizontally))
            Spacer(Modifier.height(if (login) { if (compact) 22.dp else 32.dp } else 25.dp))
            Text(
                if (login) "Bom ter você de volta!" else "Seu universo começa\naqui.",
                color = AuthWhite, fontSize = if (login) 26.sp else 27.sp,
                lineHeight = 33.sp, fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(11.dp))
            Text(
                if (login) "Sua próxima descoberta está em órbita. Entre para\ncontinuar sua jornada."
                else "Crie sua conta e dê o primeiro passo na exploração da\nprogramação.",
                color = QuestColors.muted, fontSize = 14.sp, lineHeight = 21.sp,
            )
            Spacer(Modifier.height(if (login) { if (compact) 23.dp else 34.dp } else 25.dp))
            if (!login) {
                QuestAuthField(
                    label = "Nome de cadete", placeholder = "Como quer ser chamado?",
                    value = name, onValueChange = { name = it; error = null },
                    icon = QuestGlyph.PERSON, keyboardType = KeyboardType.Text,
                )
                Spacer(Modifier.height(19.dp))
            }
            QuestAuthField(
                label = "E-mail", placeholder = "Seu e-mail",
                value = email, onValueChange = { email = it; error = null },
                icon = QuestGlyph.MAIL, keyboardType = KeyboardType.Email,
            )
            Spacer(Modifier.height(if (login) 20.dp else 19.dp))
            QuestAuthField(
                label = "Senha",
                placeholder = if (login) "Sua senha" else "Crie uma senha",
                value = password, onValueChange = { password = it; error = null },
                icon = QuestGlyph.LOCK, keyboardType = KeyboardType.Password,
                password = true, showPassword = showPassword,
                onTogglePassword = { showPassword = !showPassword },
            )
            if (login) {
                Spacer(Modifier.height(15.dp))
                Text(
                    "Esqueceu sua senha?", color = QuestColors.cyan,
                    fontSize = 12.sp, fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.End).clickable(role = Role.Button) {
                        information = "recovery"
                    },
                )
                Spacer(Modifier.height(if (compact) 19.dp else 28.dp))
            } else {
                Spacer(Modifier.height(7.dp))
                Text("Use pelo menos 8 caracteres, com letras e números.",
                    color = QuestColors.muted, fontSize = 11.sp, lineHeight = 16.sp)
                Spacer(Modifier.height(17.dp))
                QuestTermsRow(
                    checked = termsAccepted,
                    onCheckedChange = { termsAccepted = it; error = null },
                    onOpenTerms = { information = "terms" },
                    onOpenPrivacy = { information = "privacy" },
                )
                Spacer(Modifier.height(19.dp))
            }
            if (error != null) {
                Text(error.orEmpty(), color = Color(0xFFFFA6A6), fontSize = 12.sp,
                    lineHeight = 16.sp, modifier = Modifier.padding(bottom = 10.dp))
            }
            QuestSolidButton(
                text = if (login) "Entrar ❯" else "Criar conta ❯",
                onClick = ::submit,
                modifier = Modifier.fillMaxWidth(), height = 51.dp,
                background = QuestColors.cyan,
            )
            Spacer(Modifier.height(if (login) 32.dp else 22.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    if (login) "Ainda não tem conta? " else "Já tenho conta · ",
                    color = QuestColors.muted, fontSize = 14.sp,
                )
                Text(
                    if (login) "Criar conta" else "Entrar",
                    color = QuestColors.cyan, fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable(role = Role.Button, onClick = onSwitchMode),
                )
            }
            if (login) {
                Spacer(Modifier.height(if (compact) 26.dp else 47.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    QuestLineIcon(QuestGlyph.SPARKLE, Modifier.size(15.dp), Color(0xFFD7AEFF))
                    Spacer(Modifier.width(7.dp))
                    Text("Um universo de código espera por você.",
                        color = QuestColors.muted, fontSize = 11.sp)
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
    if (information != null) {
        val (title, body) = when (information) {
            "recovery" -> "Recuperar senha" to
                "O envio de e-mail de recuperação será disponibilizado com a integração do Supabase Auth. Nenhuma solicitação foi enviada."
            "terms" -> "Termos de uso" to
                "O documento definitivo dos termos de uso ainda não foi fornecido. Este botão é uma demonstração da navegação."
            else -> "Política de privacidade" to
                "A política de privacidade definitiva ainda não foi fornecida. Este botão é uma demonstração da navegação."
        }
        QuestHelpDialog(title, body) { information = null }
    }
}

/** Marca orbital desenhada em Compose para manter proporções em todas as plataformas. */
@Composable
internal fun QuestOrbitLogo(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = size.minDimension / 2f
        drawCircle(Color(0xFF6944AD).copy(alpha = .55f), radius * .99f, center,
            style = Stroke(1.dp.toPx()))
        drawCircle(Color(0xFF00CDE9).copy(alpha = .48f), radius * .73f, center,
            style = Stroke(1.dp.toPx()))
        drawCircle(Color(0xFF19183A), radius * .50f, center)
        drawCircle(Color(0xFF5556A1), radius * .50f, center,
            style = Stroke(1.dp.toPx()))
        val stroke = (radius * .035f).coerceAtLeast(1.4.dp.toPx())
        drawLine(QuestColors.cyan,
            Offset(center.x - radius * .12f, center.y - radius * .14f),
            Offset(center.x + radius * .02f, center.y), stroke, StrokeCap.Round)
        drawLine(QuestColors.cyan,
            Offset(center.x + radius * .02f, center.y),
            Offset(center.x - radius * .12f, center.y + radius * .14f), stroke, StrokeCap.Round)
        drawLine(QuestColors.cyan,
            Offset(center.x + radius * .01f, center.y + radius * .20f),
            Offset(center.x + radius * .21f, center.y + radius * .20f), stroke, StrokeCap.Round)
        drawCircle(QuestColors.cyan, radius * .047f,
            Offset(center.x + radius * .67f, center.y - radius * .75f))
        drawCircle(Color(0xFFFFB700), radius * .030f,
            Offset(center.x - radius * .68f, center.y + radius * .70f))
    }
}

@Composable
private fun QuestBrandName(modifier: Modifier = Modifier) {
    Text(
        buildAnnotatedString {
            withStyle(SpanStyle(color = AuthWhite)) { append("Jornada ") }
            withStyle(SpanStyle(color = QuestColors.cyan)) { append("Espacial") }
        },
        modifier = modifier, fontSize = 18.sp, fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    )
}

@Composable
internal fun QuestAuthField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: QuestGlyph,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier,
    password: Boolean = false,
    showPassword: Boolean = false,
    onTogglePassword: () -> Unit = {},
) {
    Column(modifier.fillMaxWidth()) {
        Text(label, color = AuthWhite, fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(9.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(color = AuthWhite, fontSize = 14.sp),
            cursorBrush = SolidColor(QuestColors.cyan),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = if (password) ImeAction.Done else ImeAction.Next,
            ),
            visualTransformation = if (password && !showPassword) PasswordVisualTransformation()
                else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            decorationBox = { innerTextField ->
                Row(
                    Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp))
                        .background(AuthFieldColor)
                        .border(1.dp, AuthFieldBorder, RoundedCornerShape(12.dp))
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    QuestLineIcon(icon, Modifier.size(20.dp))
                    Spacer(Modifier.width(13.dp))
                    Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        if (value.isEmpty()) {
                            Text(placeholder, color = QuestColors.muted, fontSize = 14.sp)
                        }
                        innerTextField()
                    }
                    if (password) {
                        Spacer(Modifier.width(10.dp))
                        Box(Modifier.size(30.dp).clip(CircleShape)
                            .clickable(role = Role.Button, onClickLabel =
                                if (showPassword) "Ocultar senha" else "Mostrar senha",
                                onClick = onTogglePassword),
                            contentAlignment = Alignment.Center) {
                            QuestLineIcon(
                                if (showPassword) QuestGlyph.EYE_CLOSED else QuestGlyph.EYE,
                                Modifier.size(20.dp),
                            )
                        }
                    }
                }
            },
        )
    }
}

@Composable
private fun QuestTermsRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onOpenTerms: () -> Unit,
    onOpenPrivacy: () -> Unit,
) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier.size(20.dp).clip(RoundedCornerShape(4.dp))
                .border(1.dp, Color(0xFF65577E), RoundedCornerShape(4.dp))
                .background(if (checked) QuestColors.cyan else Color.Transparent)
                .clickable(role = Role.Checkbox, onClick = { onCheckedChange(!checked) }),
            contentAlignment = Alignment.Center,
        ) {
            if (checked) Text("✓", color = Color(0xFF091527), fontSize = 15.sp,
                fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(10.dp))
        Text("Li e aceito os ", color = QuestColors.muted, fontSize = 11.sp)
        Text("Termos de uso", color = QuestColors.cyan, fontSize = 11.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable(onClick = onOpenTerms))
        Text(" e a ", color = QuestColors.muted, fontSize = 11.sp)
        Text("Política de privacidade", color = QuestColors.cyan, fontSize = 11.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable(onClick = onOpenPrivacy))
        Text(".", color = QuestColors.muted, fontSize = 11.sp)
    }
}
