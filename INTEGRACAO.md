# SoftwareQuest — Seletor de Órbitas

Referência visual: `canvas-container.png` (402 × 918 px).

## Conteúdo do projeto

- `shared/src/commonMain/kotlin/br/uepa/softwarequest/OrbitSelectorModal.kt`: tela modal completa, feita em Compose Multiplatform.
- `shared/src/commonMain/composeResources/drawable/orbit_world_01.png` a `orbit_world_05.png`: recortes das ilustrações dos protótipos. O Mundo 03 usa a ilustração de `canvas-container.png`.
- `shared/src/commonMain/kotlin/br/uepa/softwarequest/SoftwareQuestHomeScreen.kt`: Home de demonstração, com o planeta Órbitas clicável.
- `shared/src/commonMain/kotlin/br/uepa/softwarequest/App.kt`: controla a abertura e o fechamento da modal.

## Como integrar ao SEU projeto atual, preservando sua Home anterior

1. Copie **apenas** `OrbitSelectorModal.kt` para `shared/src/commonMain/kotlin/br/uepa/softwarequest/`.
2. Copie os cinco arquivos `orbit_world_*.png` para `shared/src/commonMain/composeResources/drawable/`.
3. No `shared/build.gradle.kts`, confirme que `commonMain` tem `implementation(libs.compose.components.resources)` e adicione, caso ainda não exista:

```kotlin
compose.resources {
    packageOfResClass = "br.uepa.softwarequest.resources"
    publicResClass = true
}
```

4. Na função `SoftwareQuestHomeScreen` **que você já usa**, adicione um callback à assinatura (ou aproveite seu callback atual):

```kotlin
@Composable
fun SoftwareQuestHomeScreen(
    onOrbitasClick: () -> Unit = {},
) {
    // ... seu código existente ...
}
```

5. No nó/botão de planeta com texto **"Órbitas"**, conecte o clique:

```kotlin
Modifier.clickable { onOrbitasClick() }
```

Se o componente do planeta já possuir um `onClick`, use `onClick = onOrbitasClick` em vez de duplicar o `Modifier.clickable`.

6. Em `App.kt`, mantenha a Home que você já criou, e acrescente o estado de visibilidade e a modal sobre ela:

```kotlin
@Composable
fun App() {
    var orbitSelectorVisible by rememberSaveable { mutableStateOf(false) }

    MaterialTheme {
        Box(Modifier.fillMaxSize()) {
            SoftwareQuestHomeScreen(
                onOrbitasClick = { orbitSelectorVisible = true },
            )
            if (orbitSelectorVisible) {
                OrbitSelectorModal(
                    onDismiss = { orbitSelectorVisible = false },
                    onExploreWorld = { world ->
                        orbitSelectorVisible = false
                        // TODO: navegar para o planeta selecionado (world.number)
                    },
                )
            }
        }
    }
}
```

Imports adicionais do exemplo: `androidx.compose.foundation.layout.Box`, `androidx.compose.foundation.layout.fillMaxSize`, `androidx.compose.runtime.*`, `androidx.compose.runtime.saveable.rememberSaveable`, `androidx.compose.ui.Modifier`, `androidx.compose.material3.MaterialTheme`.

**IMPORTANTE:** o ZIP completo contém uma Home de demonstração e um `App.kt` novo. Se você já modificou esses arquivos no seu projeto, **não os sobrescreva**. Prefira copiar só o componente modal e os recursos, aplicando os passos acima.

## Interação implementada

- Tocar em **Órbitas** na Home abre o seletor em tela cheia.
- Botão **voltar** fecha o seletor.
- Setas laterais e indicadores selecionam outro mundo (5 posições).
- **Explorar Mundo 03** chama `onExploreWorld` com o mundo escolhido.
- **?** mostra uma pequena explicação.
- A barra inferior permanece visualmente presente na modal.

## Fidelidade e dados

- Mundo 03: título, descrição, 12 Missões, 320 XP, dificuldade Intermediário e 15% concluído são transcritos do protótipo.
- O protótipo exibe 15% de conclusão, mas o preenchimento visual da barra parece ter ~30%; a implementação reproduz esse detalhe com `previewBarFraction = .30f`.
- Os Mundos 01, 02, 04 e 05 usam **dados demonstrativos**, pois a imagem fornecida não revela suas descrições, recompensas ou dificuldades.
- A arte do Mundo 03 foi extraída do arquivo `canvas-container.png`. As demais imagens de planetas são recortes da tela inicial, com resolução menor.
- A UI não acessa SQLDelight nem Supabase nesta etapa.

## Build

Abra a raiz `softwarequest` no Android Studio, sincronize o Gradle e rode o módulo `androidApp`. Se o Android Studio indicar `Unresolved reference: resources`, verifique os PNGs em `composeResources/drawable`, a configuração de `compose.resources` e sincronize novamente.

O projeto foi verificado quanto à estrutura e aos recursos, mas **não foi possível compilar neste ambiente**: o Gradle 9.5.1 não pôde ser baixado por falta de acesso a `services.gradle.org`.
