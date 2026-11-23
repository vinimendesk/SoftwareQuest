# SoftwareQuest — Fluxos e roteiro de verificação visual

## Rotas

```text
Aplicativo
└── Login (tela inicial)
    ├── Criar conta → Cadastro
    │   ├── Criar conta (dados válidos) → Explorar
    │   └── Já tenho conta → Login
    ├── Entrar (dados válidos) → Explorar
    └── Esqueceu sua senha? → diálogo demonstrativo

Explorar
├── Cartão de perfil ou barra de progresso → Perfil (modal em tela cheia)
│   ├── Voltar → Explorar
│   ├── Editar perfil → editar nome localmente
│   ├── Iniciar Salto → Lição Variáveis Cósmicas
│   └── Sair da conta → confirmar → Login
├── Planeta Órbitas → Seletor de Órbitas
├── Iniciar Salto → Lição Variáveis Cósmicas
└── Barra inferior
    ├── Missões
    ├── Estações
    └── Federação
```

## Checklist de teste manual

- [ ] Login exibe logotipo orbital, título, e-mail, senha, mostrar/ocultar senha, recuperação, Entrar e Criar conta.
- [ ] Tentar entrar sem e-mail/senha exibe erro, sem mudar de tela.
- [ ] Informar e-mail válido e senha não vazia leva para Explorar (somente demonstração).
- [ ] Criar conta abre Cadastro e exibe nome de cadete, e-mail, senha, termos e botão.
- [ ] Senha sem 8 caracteres/letras/números ou termos não aceitos bloqueia a transição.
- [ ] Cadastro válido abre Explorar com o nome informado.
- [ ] Tocar no cartão de perfil abre Perfil em tela cheia.
- [ ] Editar nome no Perfil atualiza o cartão da Home.
- [ ] Iniciar Salto no Perfil abre a Lição; fechar a Lição retorna à Home.
- [ ] Sair da conta solicita confirmação e volta para Login.
- [ ] Planeta Órbitas abre Seletor de Órbitas; Voltar fecha.
- [ ] Botão Iniciar Salto na Home abre a Lição.
- [ ] Barra inferior alterna entre Missões, Estações e Federação.
- [ ] Perfil é rolável em telas menores.

## Limitações conhecidas

Sem autenticação Supabase, sem persistência SQLDelight, sem políticas legais finais e sem backend para recuperação de senha. Dados de perfil, XP, recompensas, missão e inventário são demonstrativos. A tela de lição possui a primeira etapa da imagem de referência.
