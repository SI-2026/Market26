
## Estado actual del proyecto (18/02/2026)

### Resumen rápido
- Rama activa: `main`
- El flujo principal de usuario ya está operativo: entrada, login, registro, consulta de ventas y creación de ventas.
- Se han integrado mejoras recientes en ventanas, selector de idioma (ComboBox) y perfil de usuario.
- Hay cambios locales sin commit en varias pantallas `gui/` y en este mismo archivo.

### Funcionalidad implementada (según código actual)

#### 1) Lanzador y navegación base
- `ApplicationLauncher` arranca `UserGUI` y conecta `BLFacade` en local/remoto.
- `MainGUI` ha sido renombrada a `UserGUI` (movimiento ya hecho en el árbol).

#### 2) Acceso de usuario
- `LoginGUI`: valida campos, autentica con `facade.isRegistered(...)` y abre `RegisteredGUI`.
- `RegisterGUI`: valida usuario + confirmación de contraseña, registra con `facade.register(...)`.
- `DataAccess`: login/registro implementados con `db.find(User.class, log)`.
- `User`: incluye `isConectedPassword(String pass)` para validar credenciales.

#### 3) Usuario autenticado
- `RegisteredGUI`: menú de usuario con acceso a:
	- Crear venta (`CreateSaleGUI`)
	- Consultar ventas (`QuerySalesGUI`)
	- Perfil (`ProfileGUI`)
- Soporte de idiomas por ComboBox (Euskara, English, Español).

#### 4) Ventas
- `CreateSaleGUI`: alta de venta con título, descripción, estado, precio, fecha e imagen.
- `QuerySalesGUI`: búsqueda de ventas publicadas por texto y fecha.
- `ShowSaleGUI`: detalle de venta con imagen y estado.
- `DataAccess.createSale(...)`: validaciones de fecha, duplicados y fichero.

### Historial reciente de commits (últimos hitos)

#### 17/02/2026 (bloque principal reciente)
- `ee82896` Implementado funcion de perfil
- `dc3e659` terminado de mejorar registered y las lamadas entre ellos
- `11ea04d`, `c284d32`, `35f03b1` mejoras/añadidos de etiquetas
- `c47c5a9` Ordenados los imports
- `bb43dea` Mejorando como funcionan las ventanas
- `4fac667`, `94593d7` cambio de selector de idioma a ComboBox
- `3cac8fd` reordenado MainGUI
- `54e9acd` creada funcion `isConectedPassword(String pass)`
- `b44d486` mejoras en login/registro con `db.find(key)`
- `5c262f6` actualización de `.gitignore`

#### 10/02/2026 - 13/02/2026
- Integración de `.mdj`, merges de rama principal y estabilización de base del proyecto.

#### Actividad por autor (histórico Git)
- `Villacus`: 33 commits
- `jon`: 29 commits
- `Jon Iturrioz`: 6 commits
- `jonPa139`: 3 commits
- `Jonpi`: 2 commits
- `ROBII-14`: 2 commits

### Cambios locales actualmente sin commit
- Modificados: `.classpath`, `ESTADO.md`
- Modificados en GUI: `ApplicationLauncher.java`, `LoginGUI.java`, `RegisterGUI.java`, `RegisteredGUI.java`, `ProfileGUI.java`, `CreateSaleGUI.java`, `QuerySalesGUI.java`, `ShowSaleGUI.java`
- Renombrado: `MainGUI.java` -> `UserGUI.java`
- Nuevo no trackeado: carpeta `.obsidian/`

### Puntos pendientes / mejora detectados
- `ProfileGUI` aún muestra descripción placeholder (`"aaaaaaaa..."`) y no datos reales del usuario.
- Faltaría consolidar naming (`mail`/`username`) para evitar confusiones en parámetros.
- Sería recomendable limpiar warnings y pequeños restos de código temporal en GUI.
- Conviene cerrar bloque funcional con pruebas y un commit de estabilización.

### Siguiente paso recomendado
1. Confirmar que el renombrado `MainGUI -> UserGUI` compila limpio en todo el proyecto.
2. Cerrar `ProfileGUI` con datos reales.
3. Commit único de estabilización de GUI + estado.
4. Revisión rápida de i18n (etiquetas faltantes).