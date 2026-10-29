# MPAZ PRO 6.º

Aplicación móvil educativa desarrollada para estudiantes de 6.º básico, orientada al aprendizaje, práctica y evaluación de contenidos correspondientes a distintas asignaturas.

El proyecto fue desarrollado en **Android Studio utilizando Kotlin y Jetpack Compose**.

## Integrantes

- Jose Luis Cisternas — GitHub: `JoseLuisCGX`
- Iván Rivera — GitHub: `Ivaan91`

## Objetivo del proyecto

MPAZ PRO 6.º busca entregar una experiencia educativa móvil en la que estudiantes, docentes y miembros de UTP puedan interactuar con distintas funciones según su rol.

La aplicación permite que los estudiantes revisen contenidos educativos, desarrollen actividades y rindan evaluaciones, mientras que los docentes pueden configurar unidades y revisar resultados. UTP puede revisar y autorizar unidades antes de su utilización.

## Roles de usuario

### Estudiante

El estudiante puede:

- Visualizar las asignaturas disponibles.
- Consultar su avance general.
- Ingresar a las unidades educativas.
- Revisar contenidos explicativos.
- Escuchar explicaciones mediante TextToSpeech.
- Completar actividades de práctica.
- Resolver desafíos aplicados.
- Realizar pruebas finales.
- Recibir retroalimentación sobre sus respuestas.
- Revisar resultados obtenidos.
- Utilizar recordatorios mediante notificaciones Android.

### Docente

El docente puede:

- Consultar información general del curso.
- Revisar unidades académicas.
- Configurar la cantidad máxima de intentos de una prueba final.
- Revisar informes pedagógicos.
- Consultar el rendimiento obtenido por el estudiante.
- Identificar fortalezas y contenidos por reforzar.
- Validar informes pedagógicos.

### UTP

UTP puede:

- Revisar las unidades enviadas por el equipo docente.
- Consultar información académica de cada unidad.
- Revisar una lista de verificación.
- Autorizar unidades.
- Solicitar modificaciones.
- Registrar observaciones destinadas al docente.
- Consultar el estado de revisión de una unidad.

## Asignaturas disponibles

La aplicación incluye unidades correspondientes a:

- Lenguaje y Comunicación.
- Matemática.
- Ciencias Naturales.
- Historia, Geografía y Ciencias Sociales.

## Funcionalidades principales

### Inicio de sesión

La aplicación cuenta con autenticación local de usuarios.

Los usuarios son almacenados utilizando **Room**, biblioteca que trabaja sobre SQLite en Android.

Dependiendo del rol del usuario, la aplicación dirige automáticamente a la interfaz correspondiente.

### Validación de formularios

Los formularios incluyen:

- Validación de campos obligatorios.
- Mensajes visuales de error.
- Iconos de advertencia.
- Validación desacoplada de la interfaz mediante ViewModel.

Esto se utiliza, por ejemplo, en:

- Inicio de sesión.
- Configuración de intentos.
- Observaciones de UTP.

### Navegación

La aplicación utiliza **Navigation Compose** para administrar el desplazamiento entre pantallas.

El recorrido del estudiante incluye:

```text
Inicio
↓
Asignatura
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

### Gestión de progreso

Durante la ejecución de la aplicación se registra:

- Contenido revisado.
- Actividades completadas.
- Desafíos realizados.
- Resultados de pruebas.
- Cantidad de intentos utilizados.
- Avance de cada asignatura.
- Avance general del estudiante.

### Configuración docente

El docente puede seleccionar una asignatura y configurar la cantidad máxima de intentos permitidos para la prueba final.

El sistema valida que el número ingresado se encuentre dentro del rango permitido.

### Informe pedagógico

El informe pedagógico utiliza los resultados obtenidos por el estudiante para calcular:

- Rendimiento general.
- Rendimiento en actividad.
- Rendimiento en desafío.
- Rendimiento en prueba final.
- Fortalezas.
- Contenidos que requieren refuerzo.

Los resultados del informe no están escritos de forma estática, sino que se calculan utilizando las respuestas registradas durante el recorrido del estudiante.

### Revisión UTP

UTP puede revisar las unidades disponibles y asignar uno de los siguientes estados:

- En revisión.
- Autorizada.
- Cambios solicitados.

Cuando se solicitan cambios, se exige ingresar una observación antes de registrar la solicitud.

## Recursos nativos de Android

La aplicación utiliza recursos propios del dispositivo Android.

### TextToSpeech

Los contenidos educativos pueden ser reproducidos mediante el motor TextToSpeech de Android.

El sistema intenta utilizar una voz en español y permite detener la lectura al cambiar de contenido o abandonar la pantalla.

### Notificaciones

La aplicación utiliza el sistema de notificaciones de Android para mostrar recordatorios de estudio.

En Android 13 o superior se solicita al usuario el permiso correspondiente antes de mostrar notificaciones.

## Animaciones

La aplicación incorpora animaciones de interfaz mediante Jetpack Compose, entre ellas:

- Transiciones entre pantallas.
- Fade de entrada y salida.
- Desplazamiento durante la navegación.
- Retroalimentación animada en validaciones y mensajes.

## Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Navigation Compose
- ViewModel
- StateFlow
- Room
- SQLite
- Coroutines
- TextToSpeech
- NotificationManager
- Git
- GitHub

## Arquitectura del proyecto

El proyecto separa distintas responsabilidades utilizando paquetes como:

```text
data/
model/
ui/
viewmodel/
util/
```

De esta manera se busca evitar concentrar toda la lógica dentro de las pantallas.

Los ViewModel administran el estado y parte de la lógica de presentación, mientras que los repositorios concentran información y operaciones utilizadas por distintas pantallas.

## Requisitos para ejecutar el proyecto

Se recomienda utilizar:

- Android Studio actualizado.
- Android SDK instalado.
- JDK compatible con el proyecto.
- Dispositivo Android o emulador.
- Android 7.0 o superior.

La aplicación utiliza:

```text
minSdk = 24
targetSdk = 36
```

## Cómo ejecutar el proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/Ivaan91/MPAZ-PRO-SEXTO.git
```

### 2. Abrir Android Studio

Seleccionar:

```text
Open
```

y abrir la carpeta:

```text
MPAZ-PRO-SEXTO
```

### 3. Configurar Android SDK

Si Android Studio no detecta automáticamente el SDK, crear un archivo:

```text
local.properties
```

en la raíz del proyecto e indicar la ruta del SDK local.

Ejemplo:

```properties
sdk.dir=C:/Users/USUARIO/AppData/Local/Android/Sdk
```

La ruta depende del computador donde se ejecute el proyecto.

### 4. Sincronizar Gradle

Esperar que Android Studio complete la sincronización de dependencias.

También puede utilizarse:

```bash
./gradlew build
```

En Windows:

```bash
gradlew.bat build
```

### 5. Ejecutar

Conectar un dispositivo Android mediante USB o iniciar un emulador.

Después seleccionar:

```text
Run 'app'
```

en Android Studio.

## Usuarios de demostración

La aplicación incluye cuentas de demostración para acceder a los distintos roles.

### Estudiante

```text
Usuario: sofia
Contraseña: sofia123
```

### Docente

```text
Usuario: javier
Contraseña: javier123
```

### UTP

```text
Usuario: andrea
Contraseña: andrea123
```

## Control de versiones

El proyecto utiliza Git y GitHub para el control de versiones.

Durante el desarrollo se realizaron commits distribuidos para registrar distintas funcionalidades, correcciones e integraciones realizadas por los integrantes del equipo.

Para actualizar el repositorio se recomienda utilizar:

```bash
git pull --rebase origin master
```

antes de realizar un `push`, con el objetivo de reducir commits de merge innecesarios.

## Estado del proyecto

Proyecto correspondiente a la evaluación de Desarrollo de Aplicaciones Móviles.

Las funcionalidades continúan siendo revisadas y probadas antes de la entrega final.