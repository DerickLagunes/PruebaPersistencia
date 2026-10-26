# Cuestionario y Práctica de Laboratorio: SharedPreferences vs Room

> [!NOTE]
> **Objetivo:** Analizar, experimentar y contrastar el comportamiento de persistencia de datos en Android utilizando **SharedPreferences** (almacenamiento Clave-Valor) y **Room** (Base de datos relacional SQLite).

---

## Parte 1: Experimentos de Comportamiento (Laboratorio)

Realiza los siguientes experimentos en tu emulador o dispositivo físico, anota tus observaciones y responde lo que se pide.

### Experimento A: Cierre forzoso y apertura reciente
1. Abre la aplicación.
2. Guarda un texto en **SharedPreferences** y agrega 3 nombres distintos en **Room**.
3. Cierra la aplicación desde el panel de aplicaciones recientes (deslizando la tarjeta hacia arriba).
4. Vuelve a abrir la app.
   * **Pregunta 1.1:** ¿Qué sucedió con el valor guardado en SharedPreferences? ¿Sigue ahí?
   * **Pregunta 1.2:** ¿Qué sucedió con los nombres guardados en la lista de Room? ¿Se conservaron todos?

### Experimento B: "Borrar Caché" vs "Borrar Almacenamiento (Datos)"
1. Agrega varios nombres en Room y guarda un valor en SharedPreferences.
2. Ve a los Ajustes de Android de tu dispositivo o emulador:
   * **Ajustes > Aplicaciones > PruebaSharedPreferences > Almacenamiento**.
3. Primero, pulsa **"Borrar Caché" (Clear Cache)**. Regresa a la app y revísala.
   * **Pregunta 2.1:** ¿Se perdieron los datos de SharedPreferences o de Room al borrar la caché? ¿Por qué?
4. Ahora, regresa a Ajustes y pulsa **"Borrar Almacenamiento" o "Borrar Datos" (Clear Storage / Clear Data)**. Abre la app nuevamente.
   * **Pregunta 2.2:** ¿Qué ocurrió con SharedPreferences y con Room después de borrar los datos de la app? ¿Dónde almacena Android físicamente ambos tipos de información en el sistema de archivos interno?

### Experimento C: Desinstalación y Reinstalación
1. Con datos existentes en ambas pantallas, **desinstala** la aplicación por completo del dispositivo.
2. Vuelve a compilar e instalar la app desde Android Studio.
   * **Pregunta 3.1:** ¿Se mantiene algún dato persistente después de la reinstalación? Explica por qué ocurre esto en términos de seguridad y almacenamiento en Android.

---

## Parte 2: Preguntas de Análisis sobre el Código

### 1. Reactividad y Actualización de UI
* **Pregunta 4.1:** En `PruebaRoomScreen`, cuando insertas un nuevo nombre, la `LazyColumn` se actualiza automáticamente en pantalla sin que hayas tenido que programar un método manual para recargar los datos. ¿Qué elemento de Room y Kotlin hace posible este comportamiento reactivo y cómo interviene Compose (`collectAsState`)?
* **Pregunta 4.2:** En contraste, ¿cómo se actualiza la variable `nombreGuardado` en `SPScreen` cuando guardas un nuevo valor en SharedPreferences? ¿Por qué SharedPreferences no actúa de manera reactiva por defecto como Room?

### 2. Estructura y Modelado de Datos
* **Pregunta 5.1:** En `models/juancho.kt`, la entidad `Usuario` está anotada con `@Entity(tableName = "usuarios")` y usa `@PrimaryKey(autoGenerate = true)`. ¿Qué ventaja representa esto frente a almacenar un solo valor estático (como hacíamos antes con `id = 1`) cuando queremos manejar listas de elementos?
* **Pregunta 5.2:** ¿Para qué sirve la anotación `@Dao` y por qué las operaciones de escritura (`insertUsuario`, `deleteAllUsuarios`) deben ejecutarse con la palabra clave `suspend` dentro de una corrutina?

### 3. Casos de Uso en el Mundo Real
* **Pregunta 6.1:** Imagina que estás desarrollando una aplicación de notas y tareas (To-Do List). ¿Cuál de las dos tecnologías (SharedPreferences o Room) elegirías para guardar la lista de tareas del usuario y por qué?
* **Pregunta 6.2:** ¿En qué escenario exacto de una aplicación móvil preferirías usar SharedPreferences (o su sucesor moderno DataStore) en lugar de implementar una base de datos Room?

---

## Parte 3: Conclusión del Alumno
* Redacta un párrafo final explicando con tus propias palabras cuál es la diferencia principal entre un almacenamiento **Clave-Valor** y una **Base de Datos Relacional**, y cuándo considerarías usar cada uno en tus futuros proyectos profesionales de Android.
