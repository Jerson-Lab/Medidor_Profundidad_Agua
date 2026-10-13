Evaluación: Evaluación Sumativa Unidad 2 Asignatura: Aplicaciones Móviles para IoT (TI3042) Institución: INACAP Sede Chillán Alumno: Jerson Concha

Arquitectura de Roles: Permite configurar el dispositivo como Emisor (simula la captura de datos del sensor) o Receptor (pantalla de monitoreo para el operador).
Sincronización en Tiempo Real: Utiliza Firebase Realtime Database para actualizar el nivel del agua instantáneamente sin necesidad de recargar la pantalla.
Seguridad (Norma ISO 27400): Implementación de Firebase Authentication para control de acceso seguro, garantizando el cifrado de credenciales y el uso de conexiones seguras (HTTPS/TLS) bajo estándares de la industria OT.
Tecnologías Utilizadas Lenguaje: Kotlin Entorno: Android Studio Backend as a Service: Firebase (Auth & Realtime Database) Persistencia Local: SharedPreferences (para guardar el rol del dispositivo)

Cómo probar la aplicación (Simulación) Para probar la aplicación con un solo dispositivo o emulador:

Ejecutar la aplicación e iniciar sesión (o registrar un nuevo usuario).
En el cuadro de diálogo inicial, seleccionar el rol de Receptor.
Abrir la consola web de Firebase Realtime Database.
Modificar manualmente el valor numérico en el nodo estadoActual.
La pantalla de la aplicación se actualizará en tiempo real reflejando la nueva profundidad.
