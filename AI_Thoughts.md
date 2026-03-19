# Reflexión sobre el proceso con IA (Entregable 7)

Este documento detalla la experiencia de desarrollo utilizando inteligencia artificial para la implementación de "Ten Match".

### 1. ¿Qué prompts utilizó?
- "Crea un plan de implementación basado en el manual proporcionado."
- "Implementa las entidades de dominio (User, Report, Location) usando data classes en Kotlin."
- "Define la navegación anidada con sealed classes para las rutas Auth, Main y Game."
- "Crea la lógica para generar un tablero 4x4 donde cada carta tenga al menos una pareja que sume 10."
- "Implementa la pantalla de juego con LazyVerticalGrid e intercepta el botón de retroceso."

### 2. ¿Qué sugerencias de la IA fueron útiles?
- La estructura de **Unidirectional Data Flow (UDF)** utilizando ViewModels y `collectAsState` (o estados observables de Compose) facilitó mucho la sincronización del tablero.
- La sugerencia de usar un `delay(1000)` en una corrutina para mostrar la segunda carta antes de ocultarla fue clave para la experiencia de usuario.
- El uso de `ExposedDropdownMenuBox` para cumplir con Material Design 3 de forma profesional.

### 3. ¿Qué aspectos corrigió manualmente?
- Ajustes en el `NavGraph` para asegurar que los tipos de los argumentos coincidan (Int vs String).
- Configuración precisa del `ContentScale.Crop` y `clip` en las imágenes de perfil para que se vieran perfectamente circulares.
- Gestión del `popUpTo` en la navegación para evitar que el usuario vuelva a la pantalla de carga una vez terminado el juego.

### 4. ¿Qué aprendió del proceso?
- La importancia de la **Arquitectura Base**: Definir rutas seguras y estados inmutables desde el inicio previene muchos errores de concurrencia y navegación.
- **Compose State Management**: El uso de `mutableStateListOf` permite que la UI reaccione automáticamente a cambios internos en la lista del tablero (como cambiar el `CardState`).

### 5. ¿Cómo solucionó los errores presentados?
- **Error de Navegación**: Inicialmente el paso de argumentos de "score" fallaba por ser un Int. Se solucionó definiendo `navArgument` con `type = NavType.IntType` en el NavHost.
- **Intercepción de Back**: Se utilizó `BackHandler` de Compose para asegurar que el diálogo de confirmación pausara la salida del usuario.
- **Suma de Cartas**: Se refinó el algoritmo de generación para garantizar que nunca quedara una carta huérfana (siempre se generan de a pares).
