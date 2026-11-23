# SoftwareQuest — Protótipo completo em Kotlin Multiplatform + Compose Multiplatform

Projeto visual baseado nos protótipos fornecidos. A versão atual reúne **Login**, **Cadastro**, **Explorar (Home)**, **Seletor de Órbitas**, **Lição: Variáveis Cósmicas**, **Missões**, **Estações**, **Federação** e **Perfil** em uma única aplicação KMP.

## Testar o fluxo visual

A aplicação **inicia em Login**. Como o Supabase Auth ainda não está conectado, use um e-mail sintaticamente válido e qualquer senha não vazia, por exemplo:

- E-mail: `cadete@softwarequest.dev`
- Senha: `Orion1234`

Não existe conta de verdade nem consulta ao servidor: os dados acima são apenas um exemplo de entrada que passa pela validação local.

1. Em **Login**, clique em **Criar conta** para abrir **Cadastro**. Preencha nome, e-mail, senha com 8+ caracteres, letras e números, aceite os termos e clique em **Criar conta**.
2. Após entrar, abre a tela **Explorar**. Toque no **cartão de perfil** (avatar/nome/XP/nível) ou no progresso abaixo dele para abrir **Perfil**.
3. Em **Perfil**, use **Editar perfil** para alterar o nome visualizado na Home, **Iniciar Salto** para abrir a lição ou **Sair da conta** para retornar ao Login (após confirmação).
4. Na Home, toque no planeta **Órbitas** para abrir o **Seletor de Órbitas** ou em **Iniciar Salto** para abrir **Variáveis Cósmicas**.
5. Na barra inferior, acesse **Missões**, **Estações** e **Federação**.

Os botões **Esqueceu sua senha?**, **Termos de uso**, **Política de privacidade**, **Notificações**, **Privacidade** e **Ajuda** exibem diálogos demonstrativos. Não há envio de e-mails, termos legais publicados ou preferências persistidas.

## Código compartilhado

`shared/src/commonMain/kotlin/br/uepa/softwarequest/`

| Arquivo | Responsabilidade |
| --- | --- |
| `App.kt` | Estado de sessão visual, navegação entre abas e modais |
| `QuestAuthScreens.kt` | Login e Cadastro, campos, validação local e marca orbital |
| `QuestProfileScreen.kt` | Perfil em tela cheia, edição do nome, conquistas, configurações e saída |
| `QuestVectorIcons.kt` | Ícones vetoriais de campos e perfil |
| `SoftwareQuestHomeScreen.kt` | Explorar, perfil clicável, planeta Órbitas e Transmissão Ativa |
| `OrbitSelectorModal.kt` | Seletor de Órbitas |
| `QuestLessonModal.kt` | Lição Variáveis Cósmicas |
| `QuestMissionsScreen.kt` | Missões |
| `QuestStationsScreen.kt` | Estações |
| `QuestFederationScreen.kt` | Federação |
| `QuestSharedUi.kt` | Componentes visuais compartilhados |

As imagens recortadas dos protótipos estão em `shared/src/commonMain/composeResources/drawable/`. A marca, os campos, os cartões e os botões novos são desenhados com Compose.

## Recursos do Compose e erro `resources`

O arquivo `shared/build.gradle.kts` contém:

```kotlin
compose.resources {
    packageOfResClass = "br.uepa.softwarequest.resources"
    publicResClass = true
}
```

Os imports `br.uepa.softwarequest.resources.Res` e `br.uepa.softwarequest.resources.*` são gerados durante o Gradle Sync/build. Não crie `Res` manualmente. Se aparecer `Unresolved reference: resources`, confira se os PNGs estão em `shared/src/commonMain/composeResources/drawable/` e se o Gradle Sync terminou com sucesso.

## Limitações desta etapa

**Esta é uma implementação visual/navegável, não um sistema de autenticação real.** O aplicativo aceita entradas locais para testar a transição de telas, não cria usuários, não verifica senhas em servidor, não envia e-mails e não persiste o estado em banco. O nome alterado no perfil é armazenado somente no estado da interface. O estado pode se perder ao encerrar o processo do aplicativo. As telas anteriores também utilizam dados e ações demonstrativas.

A integração futura com **Supabase Auth**, **SQLDelight**, regras de acesso e progressão deve substituir o estado de demonstração em `App.kt` e as validações de interface. Não envie credenciais reais enquanto a autenticação estiver simulada.

**Verificação:** a estrutura e os arquivos do ZIP foram validados. Não foi possível concluir uma compilação Gradle neste ambiente devido à indisponibilidade do download da distribuição Gradle 9.5.1; execute o build no Android Studio para verificar na sua máquina.
