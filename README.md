# Rota Rápida

Widget Android em português para os atalhos Casa, Trabalho, Favoritos, abrir o Waze e pesquisar. Compatível com launchers Android e concebido para ser adicionado à área personalizável do Dashline.

## Widget

![Widget Rota Rápida em formato horizontal](docs/screenshots/widget-cropped.png)

## Usar

1. Instale `dist/Rota-Rapida-1.0.0.apk` e abra Rota Rápida uma vez.
2. Instale o Waze. Os atalhos Casa e Trabalho usam os favoritos guardados na conta Waze.
3. No Dashline, abra **Settings → Customize home screen**, adicione um widget e escolha **Rota Rápida**. Ajuste o tamanho para mostrar os cinco atalhos.

O widget tem dois modos de apresentação: compacto, com os atalhos em linhas, e largo, com os cinco botões numa linha. Também pode ser adicionado a partir do seletor de widgets de outros launchers Android.

## Botão Favoritos e pesquisa

O botão **Favoritos** abre o Waze e indica para abrir **Guardados**. O Waze não publica um endereço direto para a lista completa de favoritos. Casa e Trabalho abrem os respetivos favoritos diretamente para iniciar a navegação.

O botão **Pesquisar** abre o Waze e indica para tocar na lupa. Não é usado um URL de pesquisa não documentado. A aplicação não lê nem guarda destinos e não pede localização, rede ou outros acessos.

## Compilar e validar

Requer JDK 17 ou superior e Android SDK Platform 37. O Gradle Wrapper incluído pode ser usado no Windows:

```powershell
.\gradlew.bat testDebugUnitTest lint assembleDebug assembleRelease
```

O APK final está em `dist/Rota-Rapida-1.0.0.apk`. O código de produção e os testes estão em `app/src/`. Para reproduzir a assinatura local do APK, defina `ROTA_RAPIDA_KEYSTORE`, `ROTA_RAPIDA_STORE_PASSWORD`, `ROTA_RAPIDA_KEY_ALIAS` e `ROTA_RAPIDA_KEY_PASSWORD` no ambiente antes de compilar. Neste computador, a chave está fora do projeto em `%LOCALAPPDATA%\RotaRapidaSigning`; a palavra-passe está protegida pelo Windows para a conta atual. A chave privada não está incluída no projeto nem no arquivo de fontes.

## Artefactos e validação

Veja [docs/VALIDACAO.md](docs/VALIDACAO.md) para os resultados de compilação, instalação e verificação no emulador. Capturas de ecrã estão em `docs/screenshots/`.
