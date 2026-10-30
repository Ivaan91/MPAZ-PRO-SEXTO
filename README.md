# MPAZ PRO 6.º

MPAZ PRO 6.º es una aplicación móvil educativa desarrollada para estudiantes de 6.º básico, orientada al aprendizaje, práctica y evaluación de contenidos de distintas asignaturas.

El proyecto fue desarrollado en **Android Studio utilizando Kotlin y Jetpack Compose** como parte de la asignatura Desarrollo de Aplicaciones Móviles.

---

## Integrantes

- Jose Luis Cisternas — GitHub: `JoseLuisCGX`
- Iván Rivera — GitHub: `Ivaan91`

---

## Objetivo del proyecto

MPAZ PRO 6.º busca entregar una experiencia educativa móvil en la que estudiantes, docentes y miembros de UTP interactúan con distintas funciones de acuerdo con su rol.

La aplicación implementa un flujo académico donde:

```text
Docente configura la unidad
        ↓
UTP revisa la configuración
        ↓
UTP autoriza la unidad
        ↓
Estudiante obtiene acceso
        ↓
Estudiante desarrolla su recorrido
        ↓
Docente revisa resultados e informes
```

Las unidades y usuarios utilizados en la aplicación corresponden a datos ficticios con fines académicos y de demostración.

---

# Roles de usuario

## Estudiante

El estudiante puede:

- Visualizar las asignaturas disponibles.
- Consultar su avance general.
- Acceder únicamente a unidades autorizadas por UTP.
- Revisar contenidos explicativos.
- Escuchar explicaciones mediante TextToSpeech.
- Marcar contenidos como revisados.
- Completar actividades de práctica.
- Resolver desafíos aplicados.
- Realizar pruebas finales.
- Recibir retroalimentación visual sobre sus respuestas.
- Revisar respuestas correctas e incorrectas.
- Utilizar una cantidad limitada de intentos configurada por el docente.
- Recibir recordatorios mediante notificaciones Android.
- Mantener su progreso aunque cierre la aplicación.
- Cerrar sesión y volver al inicio de sesión.

El recorrido académico está protegido para impedir que el estudiante acceda a una etapa sin haber cumplido previamente los requisitos correspondientes.

```text
Contenido
   ↓
Actividad de práctica
   ↓
Desafío aplicado
   ↓
Prueba final
   ↓
Resultado
```

---

## Docente

El docente puede:

- Consultar el avance del estudiante de demostración.
- Revisar las unidades académicas.
- Seleccionar una asignatura.
- Seleccionar el objetivo de aprendizaje disponible.
- Asignar contenido explicativo.
- Asignar el desafío correspondiente a una unidad.
- Configurar la cantidad máxima de intentos de la prueba final.
- Guardar la configuración académica de una unidad.
- Enviar una unidad a revisión UTP.
- Consultar informes pedagógicos por asignatura.
- Consultar el rendimiento obtenido por el estudiante.
- Identificar fortalezas.
- Identificar contenidos que requieren refuerzo.
- Validar informes pedagógicos.
- Descargar informes pedagógicos en formato PDF.
- Consultar la cantidad de informes pendientes y validados.
- Cerrar sesión.

Cuando el docente modifica una unidad previamente autorizada, esta vuelve automáticamente al estado **En revisión**, por lo que debe ser revisada nuevamente por UTP antes de quedar disponible para el estudiante.

---

## UTP

UTP puede:

- Revisar las unidades enviadas por el docente.
- Seleccionar una asignatura.
- Consultar el objetivo de aprendizaje seleccionado.
- Consultar la configuración de la prueba final.
- Revisar automáticamente los elementos configurados por el docente.
- Comprobar la disponibilidad de contenido.
- Comprobar la actividad de práctica.
- Comprobar el desafío aplicado.
- Comprobar la prueba final.
- Autorizar unidades correctamente configuradas.
- Impedir la autorización de unidades incompletas.
- Solicitar modificaciones al docente.
- Registrar observaciones destinadas al docente.
- Consultar el estado actual de cada unidad.
- Cerrar sesión.

Los estados de revisión disponibles son:

```text
En revisión
Autorizada
Cambios solicitados
```

Una unidad solo puede ser utilizada por el estudiante después de ser autorizada por UTP.

---

# Asignaturas disponibles

La aplicación incluye una unidad de demostración para cada una de las siguientes asignaturas:

- Lenguaje y Comunicación.
- Matemática.
- Ciencias Naturales.
- Historia, Geografía y Ciencias Sociales.

Cada unidad contiene elementos como:

- Objetivo de aprendizaje.
- Objetivo de la unidad.
- Contenidos explicativos.
- Actividad de práctica.
- Desafío aplicado.
- Prueba final.

---

# Funcionalidades principales

## Inicio de sesión

La aplicación cuenta con autenticación local mediante usuarios ficticios almacenados utilizando **Room**, biblioteca de persistencia basada en SQLite.

El sistema identifica el rol del usuario y lo dirige automáticamente a la interfaz correspondiente.

Los roles implementados son:

```text
Estudiante
Docente
UTP
```

También se incluye una opción de **Cerrar sesión** para cambiar fácilmente entre los perfiles durante la ejecución de la aplicación.

Al cerrar sesión, la pantalla principal del usuario anterior se elimina del historial de navegación para impedir regresar a una sesión cerrada utilizando el botón Atrás del dispositivo.

---

# Validación de formularios

La aplicación incorpora validaciones visuales en los formularios.

Estas validaciones incluyen:

- Campos obligatorios.
- Mensajes visuales de error.
- Iconos de advertencia.
- Retroalimentación clara.
- Estado observable.
- Validación desacoplada de la interfaz.

La lógica se administra mediante componentes como:

- ViewModel.
- StateFlow.
- Repositorios.

Las validaciones se utilizan, entre otras áreas, en:

- Inicio de sesión.
- Configuración docente.
- Cantidad máxima de intentos.
- Selección de objetivo de aprendizaje.
- Observaciones de UTP.
- Autorización de unidades.

---

# Navegación

La aplicación utiliza **Navigation Compose** para administrar el desplazamiento entre las diferentes pantallas.

Existen rutas diferenciadas según los roles y las distintas etapas del recorrido educativo.

El recorrido principal del estudiante es:

```text
Inicio
↓
Asignatura autorizada
↓
Unidad
↓
Contenido
↓
Actividad de práctica
↓
Desafío aplicado
↓
Prueba final
↓
Resultado
```

Además de los bloqueos visuales mostrados en la interfaz, la pantalla de evaluación comprueba internamente que la etapa correspondiente se encuentre desbloqueada.

Esto impide saltarse etapas mediante navegación directa.

---

# Persistencia local

La aplicación implementa persistencia local para conservar información aunque se cierre y vuelva a abrir.

Se utilizan diferentes mecanismos de almacenamiento según el tipo de información.

---

## Room / SQLite

Room se utiliza para almacenar los usuarios ficticios empleados en el inicio de sesión.

Los datos almacenados incluyen:

- Nombre.
- Iniciales.
- Rol.
- Usuario.
- Contraseña de demostración.

---

## SharedPreferences

SharedPreferences se utiliza para conservar información relacionada con el estado y progreso de la aplicación.

Entre los datos persistidos se encuentran:

- Contenidos revisados.
- Actividades realizadas.
- Desafíos realizados.
- Resultados de pruebas.
- Respuestas seleccionadas.
- Intentos utilizados.
- Límite máximo de intentos.
- Configuración académica del docente.
- Objetivo de aprendizaje seleccionado.
- Estado de revisión UTP.
- Observaciones de UTP.
- Estado de validación de informes pedagógicos.

Gracias a esta persistencia, cerrar sesión o cerrar completamente la aplicación no elimina el avance registrado.

---

# Configuración docente

El docente dispone de una pantalla para configurar las unidades académicas.

Puede seleccionar una asignatura y establecer:

- Objetivo de aprendizaje.
- Contenido explicativo.
- Desafío aplicado.
- Cantidad máxima de intentos de la prueba final.

La cantidad de intentos debe encontrarse dentro del rango:

```text
1 a 10 intentos
```

Si el valor ingresado no es válido, la aplicación muestra retroalimentación visual.

Al guardar la configuración, la unidad pasa al estado:

```text
En revisión
```

UTP debe revisarla y autorizarla antes de que el estudiante pueda acceder.

---

# Revisión UTP

La aplicación incorpora un proceso de revisión académica para UTP.

La pantalla muestra una lista de verificación dinámica basada en la configuración real realizada por el docente.

El sistema comprueba automáticamente:

- Objetivo de aprendizaje seleccionado.
- Contenido explicativo asignado.
- Actividad de práctica disponible.
- Desafío aplicado asignado.
- Prueba final configurada.
- Cantidad válida de intentos.

Si falta alguno de estos elementos, la unidad no puede ser autorizada.

UTP puede solicitar cambios al docente.

Para solicitar cambios es obligatorio ingresar una observación indicando qué elemento debe ser corregido o revisado.

---

# Acceso del estudiante

La disponibilidad de las unidades depende del estado definido por UTP.

Si una unidad se encuentra:

```text
En revisión
```

el estudiante no puede acceder.

Si UTP ha solicitado modificaciones:

```text
Cambios solicitados
```

la unidad continúa bloqueada.

Solo una unidad con estado:

```text
Autorizada
```

queda disponible para el estudiante.

---

# Gestión de progreso

La aplicación registra el progreso del estudiante por asignatura.

Se almacena:

- Contenido revisado.
- Actividad completada.
- Desafío completado.
- Intentos realizados.
- Respuestas entregadas.
- Resultados obtenidos.
- Porcentaje de rendimiento.
- Avance de cada unidad.
- Avance general.

El sistema utiliza esta información para desbloquear progresivamente las diferentes etapas.

---

# Actividades y desafíos

Las actividades y desafíos están compuestos por preguntas de selección múltiple.

El estudiante debe seleccionar una alternativa antes de continuar.

En estas etapas existe retroalimentación inmediata.

Después de comprobar una respuesta, la aplicación indica si la respuesta fue correcta.

Ejemplo:

```text
¡Correcto!
```

Si la respuesta es incorrecta se muestra:

```text
Casi lo logras
```

y posteriormente se informa la alternativa correcta.

La retroalimentación aparece mediante una animación de entrada que utiliza expansión vertical y fade.

---

# Prueba final

La prueba final utiliza un comportamiento diferente a las actividades y desafíos.

Durante la prueba:

- El estudiante selecciona sus respuestas.
- No se informa inmediatamente si una respuesta es correcta o incorrecta.
- Los resultados se muestran al finalizar.
- Se almacena cada intento realizado.
- Se respeta el máximo de intentos configurado por el docente.

Una vez alcanzado el límite de intentos, no es posible iniciar una nueva prueba.

---

# Resultados

Después de finalizar una actividad, desafío o prueba, el estudiante puede revisar sus resultados.

La aplicación permite consultar:

- Respuestas seleccionadas.
- Respuestas correctas.
- Porcentaje obtenido.
- Rendimiento de la etapa.

Estos datos también son utilizados para generar los informes pedagógicos del docente.

---

# Informe pedagógico

El docente dispone de una sección exclusiva de informes pedagógicos.

Los informes se generan utilizando los resultados reales almacenados durante el recorrido del estudiante.

Cada asignatura posee su propio informe.

El docente puede consultar:

- Rendimiento general.
- Rendimiento de la actividad.
- Rendimiento del desafío.
- Rendimiento de la prueba final.
- Fortalezas.
- Contenidos que requieren refuerzo.
- Estado del informe.

Los informes pueden encontrarse en los estados:

```text
Sin resultados
Pendiente de validar
Validado
```

El estudiante no tiene acceso al informe pedagógico.

---

# Validación de informes

El docente puede revisar un informe generado a partir del rendimiento del estudiante y posteriormente marcarlo como validado.

El estado de validación se almacena localmente para mantenerse aunque la aplicación se cierre.

La pantalla principal del docente también muestra cuántos informes se encuentran:

- Pendientes de validar.
- Validados.

---

# Descarga de informes PDF

El docente puede descargar el informe pedagógico correspondiente a una asignatura en formato PDF.

Para realizar esta acción la aplicación utiliza:

```kotlin
ActivityResultContracts.CreateDocument("application/pdf")
```

Esto abre el selector de archivos nativo de Android.

El usuario puede seleccionar la ubicación donde desea guardar el documento.

La aplicación genera el informe utilizando:

```text
PdfDocument
```

Este procedimiento no requiere solicitar acceso general al almacenamiento del dispositivo.

---

# Recursos nativos de Android

La aplicación utiliza al menos dos recursos nativos del dispositivo Android.

---

## 1. TextToSpeech

Los contenidos educativos pueden ser reproducidos mediante el motor **TextToSpeech** de Android.

La aplicación:

- Intenta utilizar una voz en español.
- Permite iniciar la lectura.
- Permite detener la lectura.
- Detiene correctamente el recurso al abandonar la pantalla.
- Informa visualmente si el servicio no se encuentra disponible.

Este recurso busca mejorar la accesibilidad de los contenidos educativos.

---

## 2. Notificaciones Android

La aplicación utiliza el sistema de notificaciones Android para generar recordatorios de estudio.

En Android 13 o superior se solicita el permiso:

```text
POST_NOTIFICATIONS
```

antes de enviar una notificación.

En versiones anteriores de Android este permiso no necesita solicitarse durante la ejecución.

La notificación utiliza información relacionada con el progreso del estudiante para recomendar continuar una asignatura disponible.

---

# Animaciones

La aplicación incorpora animaciones mediante Jetpack Compose.

Entre ellas se encuentran:

- Transiciones entre pantallas.
- Fade de entrada.
- Fade de salida.
- Desplazamiento lateral durante la navegación.
- Animaciones al avanzar entre diferentes vistas.
- Retroalimentación animada en actividades.
- Retroalimentación animada en desafíos.
- Expansión de mensajes de acierto y error.

Estas animaciones buscan mejorar la fluidez de la interfaz y entregar retroalimentación visual durante la interacción.

---

# Tecnologías utilizadas

El proyecto utiliza:

- Kotlin.
- Android Studio.
- Jetpack Compose.
- Material 3.
- Navigation Compose.
- ViewModel.
- StateFlow.
- Coroutines.
- Room.
- SQLite.
- SharedPreferences.
- TextToSpeech.
- NotificationManager.
- NotificationCompat.
- PdfDocument.
- Activity Result API.
- Git.
- GitHub.

---

# Arquitectura del proyecto

El proyecto separa las diferentes responsabilidades mediante paquetes.

```text
data/
model/
ui/
viewmodel/
util/
```

---

## data

Contiene elementos relacionados con la persistencia estructurada.

Actualmente incluye:

- Configuración de Room.
- Entidades de usuario.
- DAO.
- Base de datos local.

---

## model

Contiene elementos relacionados con los datos y lógica de dominio.

Entre ellos:

- Asignaturas.
- Unidades.
- Preguntas.
- Progreso.
- Resultados.
- Configuración académica.
- Estados UTP.
- Estados de informes.
- Repositorios.

---

## ui

Contiene las pantallas y componentes visuales desarrollados mediante Jetpack Compose.

Entre ellas:

- Login.
- Home estudiante.
- Home docente.
- Home UTP.
- Detalle de unidad.
- Contenido.
- Quiz.
- Resultado.
- Configuración docente.
- Informes.
- Lista de informes.

---

## viewmodel

Contiene los ViewModel responsables de administrar el estado y la lógica de presentación.

Actualmente incluye componentes para:

- Login.
- Configuración docente.
- Revisión UTP.
- Informe pedagógico.

---

## util

Contiene utilidades independientes utilizadas por diferentes partes de la aplicación.

Actualmente se utiliza para funcionalidades como:

- Notificaciones Android.

---

# Requisitos para ejecutar el proyecto

Se recomienda utilizar:

- Android Studio actualizado.
- Android SDK instalado.
- JDK compatible con el proyecto.
- Dispositivo Android o emulador.
- Android 7.0 o superior.

La configuración principal es:

```text
minSdk = 24
targetSdk = 36
```

---

# Cómo ejecutar el proyecto

## 1. Clonar el repositorio

```bash
git clone https://github.com/Ivaan91/MPAZ-PRO-SEXTO.git
```

---

## 2. Abrir Android Studio

Seleccionar:

```text
Open
```

y abrir la carpeta:

```text
MPAZ-PRO-SEXTO
```

---

## 3. Configurar Android SDK

Si Android Studio no detecta automáticamente el SDK, crear un archivo:

```text
local.properties
```

en la raíz del proyecto.

Ejemplo:

```properties
sdk.dir=C:/Users/USUARIO/AppData/Local/Android/Sdk
```

La ruta depende del computador donde se ejecute el proyecto.

El archivo `local.properties` no debe subirse al repositorio.

---

## 4. Sincronizar Gradle

Esperar que Android Studio complete la sincronización de dependencias.

También puede ejecutarse:

```bash
./gradlew build
```

En Windows:

```bash
gradlew.bat build
```

---

## 5. Ejecutar

Conectar un dispositivo Android mediante USB o iniciar un emulador.

Después seleccionar:

```text
Run 'app'
```

desde Android Studio.

---

# Usuarios de demostración

La aplicación incluye tres cuentas ficticias para demostrar los diferentes roles.

---

## Estudiante

```text
Usuario: sofia
Contraseña: sofia123
```

---

## Docente

```text
Usuario: javier
Contraseña: javier123
```

---

## UTP

```text
Usuario: andrea
Contraseña: andrea123
```

---

# Flujo sugerido para demostración

Para demostrar la comunicación entre los tres perfiles se recomienda realizar el siguiente recorrido.

```text
1. Iniciar sesión como Javier.

2. Abrir Configuración de unidad.

3. Seleccionar una asignatura.

4. Configurar:
   - Objetivo de aprendizaje.
   - Contenido.
   - Desafío.
   - Intentos.

5. Guardar y enviar la unidad a UTP.

6. Cerrar sesión.


7. Iniciar sesión como Andrea.

8. Seleccionar la misma asignatura.

9. Revisar la lista de verificación.

10. Autorizar la unidad.

11. Cerrar sesión.


12. Iniciar sesión como Sofía.

13. Abrir la unidad autorizada.

14. Revisar los contenidos.

15. Completar la actividad.

16. Completar el desafío.

17. Rendir la prueba final.

18. Revisar el resultado.

19. Cerrar sesión.


20. Iniciar sesión nuevamente como Javier.

21. Abrir la sección de informes.

22. Seleccionar la asignatura realizada.

23. Revisar el informe pedagógico.

24. Validar el informe.

25. Descargar el informe en formato PDF.
```

Este flujo permite demostrar que los perfiles no funcionan de forma independiente, sino que se encuentran conectados mediante el estado académico persistido localmente.

---

# Control de versiones

El proyecto utiliza Git y GitHub para el control de versiones.

Repositorio:

```text
Ivaan91/MPAZ-PRO-SEXTO
```

El desarrollo se ha realizado mediante commits distribuidos entre los integrantes del equipo.

Los commits registran tareas relacionadas con:

- Configuración inicial.
- Room.
- Login.
- Validaciones.
- Flujo estudiante.
- Contenidos.
- Actividades.
- Desafíos.
- Pruebas.
- Navegación.
- Configuración docente.
- Revisión UTP.
- Persistencia.
- TextToSpeech.
- Notificaciones.
- Animaciones.
- Informes pedagógicos.
- Descarga PDF.
- Correcciones.
- Documentación.

Antes de realizar un push se recomienda actualizar el repositorio mediante:

```bash
git pull --rebase origin master
```

y posteriormente:

```bash
git push origin master
```

---

# Estado del proyecto

MPAZ PRO 6.º corresponde a un MVP académico desarrollado para la evaluación de Desarrollo de Aplicaciones Móviles.

Actualmente la aplicación implementa:

- Tres perfiles de usuario.
- Autenticación local.
- Navegación según rol.
- Cierre de sesión.
- Formularios con validación visual.
- Validación desacoplada mediante ViewModel.
- Persistencia con Room.
- Persistencia mediante SharedPreferences.
- Navegación con Jetpack Compose.
- Flujo progresivo del estudiante.
- Bloqueo lógico de etapas.
- Retroalimentación educativa.
- Configuración docente.
- Selección de objetivo de aprendizaje.
- Configuración de intentos.
- Revisión y autorización UTP.
- Solicitudes de modificación.
- Persistencia de estados académicos.
- TextToSpeech.
- Notificaciones Android.
- Animaciones funcionales.
- Informes pedagógicos dinámicos.
- Validación de informes.
- Lista de informes por asignatura.
- Descarga de informes PDF.