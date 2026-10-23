# Validação — Rota Rápida 1.0.1

## Compilação e testes

- `testDebugUnitTest`: passou; inclui os links Casa/Trabalho, cinco ações únicas e a escolha do layout do widget.
- `lint`: passou sem erros.
- `assembleDebug` e `assembleRelease`: passaram com Android SDK Platform 37.
- Instalação e arranque do APK assinado 1.0.1: verificados no emulador Android API 34, em ecrã horizontal 1920 × 720; o pacote reporta `versionCode 2` e `versionName 1.0.1`.
- O Android Package Manager reconheceu `QuickActionsWidgetProvider` como fornecedor de widget de ecrã inicial.
- Os modos compacto e largo foram renderizados e inspecionados no emulador. Ambos apresentam os cinco atalhos com ícones e rótulos centrados, cartões uniformes e a nova marca de navegação ciano.
- Captura recortada da vista RemoteViews do widget: [widget-cropped.png](screenshots/widget-cropped.png).

## Limites do ensaio

O emulador não tinha o Waze nem o Dashline instalados. Por isso, foi possível verificar o estado de Waze ausente e a deteção do fornecedor de widget pelo Android, mas não iniciar uma navegação real, adicionar o widget dentro do Dashline nem confirmar a ligação a favoritos guardados numa conta Waze. A imagem mostra o layout RemoteViews renderizado no Android; não mostra o widget colocado dentro do Dashline.

O APK distribuído foi assinado com uma chave local de instalação. A chave privada está guardada fora do projeto em `%LOCALAPPDATA%\RotaRapidaSigning` e não faz parte do pacote de fontes. Para publicar atualizações sobre esta instalação, preserve uma cópia segura da chave usada.
