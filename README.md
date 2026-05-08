# Cosas Por Hacer

- [ ] `addToCartSD`: cambiar de pasar `sale` a `saleNumber`.
- [ ] Añadir caso de uso y SD de `clearCart`.
- [ ] Hacer GUI para añadir al carrito, ver carrito y pagar carrito.
- [ ] Ajustar disposicion de botones de añadir al carrito.

## Cambios recientes

### Demandas

- En el menu principal se anade "Consultar Demandas" para invitados y usuarios registrados.
- Desde "Consultar Demandas" puedes buscar y ver demandas activas.
- En la misma pantalla hay un boton "Nueva Demanda" (solo usuarios) para publicar una demanda.
- Al hacer doble click sobre una demanda se abre el detalle:
	- Si eres el demandante, puedes ver ofertas recibidas y aceptar o rechazar.
	- Si no eres el demandante, puedes crear una oferta de producto (producto, descripcion, precio).
	- Las ofertas de usuarios suscritos aparecen arriba con un prefijo "+".

### Suscripcion (Plan Plus)

- Es una entidad propia en el dominio (Subscription).
- Precio: 200 euros, pago unico.
- Usuarios no suscritos: maximo 4 ofertas diarias (ventas y demandas).
- Usuarios suscritos: ofertas diarias ilimitadas.
- Ofertas de suscritos aparecen destacadas con "+" en ventas y demandas.
- Cancelacion con reembolso permitido hasta 30 dias desde la compra.
- Reclamaciones: 7 dias para no suscritos, 30 dias para suscritos.
- Compras aceptadas:
	- Se cobra un 10% extra al comprador (va a fondos del sistema).
	- Se devuelve un 5% al comprador si esta suscrito (sale de los fondos).
- Para cualquier accion con dinero, se valida saldo suficiente.

### Nota sobre fondos del sistema

- Los fondos del sistema se gestionan como variable global en DataAccess.
